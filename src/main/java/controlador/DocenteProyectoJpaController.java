/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.exceptions.NonexistentEntityException;
import controlador.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidad.Docente;
import entidad.DocenteProyecto;
import entidad.DocenteProyectoPK;
import entidad.Proyecto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author es982
 */
public class DocenteProyectoJpaController implements Serializable {

    public DocenteProyectoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public DocenteProyectoJpaController(){
        emf=Persistence.createEntityManagerFactory("SigerproMVC");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DocenteProyecto docenteProyecto) throws PreexistingEntityException, Exception {
        if (docenteProyecto.getDocenteProyectoPK() == null) {
            docenteProyecto.setDocenteProyectoPK(new DocenteProyectoPK());
        }
        docenteProyecto.getDocenteProyectoPK().setNoTarjeta(docenteProyecto.getDocente().getNoTarjeta());
        docenteProyecto.getDocenteProyectoPK().setIdProyecto(docenteProyecto.getProyecto().getIdProyecto());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Docente docente = docenteProyecto.getDocente();
            if (docente != null) {
                docente = em.getReference(docente.getClass(), docente.getNoTarjeta());
                docenteProyecto.setDocente(docente);
            }
            Proyecto proyecto = docenteProyecto.getProyecto();
            if (proyecto != null) {
                proyecto = em.getReference(proyecto.getClass(), proyecto.getIdProyecto());
                docenteProyecto.setProyecto(proyecto);
            }
            em.persist(docenteProyecto);
            if (docente != null) {
                docente.getDocenteProyectoCollection().add(docenteProyecto);
                docente = em.merge(docente);
            }
            if (proyecto != null) {
                proyecto.getDocenteProyectoCollection().add(docenteProyecto);
                proyecto = em.merge(proyecto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDocenteProyecto(docenteProyecto.getDocenteProyectoPK()) != null) {
                throw new PreexistingEntityException("DocenteProyecto " + docenteProyecto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DocenteProyecto docenteProyecto) throws NonexistentEntityException, Exception {
        docenteProyecto.getDocenteProyectoPK().setNoTarjeta(docenteProyecto.getDocente().getNoTarjeta());
        docenteProyecto.getDocenteProyectoPK().setIdProyecto(docenteProyecto.getProyecto().getIdProyecto());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DocenteProyecto persistentDocenteProyecto = em.find(DocenteProyecto.class, docenteProyecto.getDocenteProyectoPK());
            Docente docenteOld = persistentDocenteProyecto.getDocente();
            Docente docenteNew = docenteProyecto.getDocente();
            Proyecto proyectoOld = persistentDocenteProyecto.getProyecto();
            Proyecto proyectoNew = docenteProyecto.getProyecto();
            if (docenteNew != null) {
                docenteNew = em.getReference(docenteNew.getClass(), docenteNew.getNoTarjeta());
                docenteProyecto.setDocente(docenteNew);
            }
            if (proyectoNew != null) {
                proyectoNew = em.getReference(proyectoNew.getClass(), proyectoNew.getIdProyecto());
                docenteProyecto.setProyecto(proyectoNew);
            }
            docenteProyecto = em.merge(docenteProyecto);
            if (docenteOld != null && !docenteOld.equals(docenteNew)) {
                docenteOld.getDocenteProyectoCollection().remove(docenteProyecto);
                docenteOld = em.merge(docenteOld);
            }
            if (docenteNew != null && !docenteNew.equals(docenteOld)) {
                docenteNew.getDocenteProyectoCollection().add(docenteProyecto);
                docenteNew = em.merge(docenteNew);
            }
            if (proyectoOld != null && !proyectoOld.equals(proyectoNew)) {
                proyectoOld.getDocenteProyectoCollection().remove(docenteProyecto);
                proyectoOld = em.merge(proyectoOld);
            }
            if (proyectoNew != null && !proyectoNew.equals(proyectoOld)) {
                proyectoNew.getDocenteProyectoCollection().add(docenteProyecto);
                proyectoNew = em.merge(proyectoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                DocenteProyectoPK id = docenteProyecto.getDocenteProyectoPK();
                if (findDocenteProyecto(id) == null) {
                    throw new NonexistentEntityException("The docenteProyecto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(DocenteProyectoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DocenteProyecto docenteProyecto;
            try {
                docenteProyecto = em.getReference(DocenteProyecto.class, id);
                docenteProyecto.getDocenteProyectoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The docenteProyecto with id " + id + " no longer exists.", enfe);
            }
            Docente docente = docenteProyecto.getDocente();
            if (docente != null) {
                docente.getDocenteProyectoCollection().remove(docenteProyecto);
                docente = em.merge(docente);
            }
            Proyecto proyecto = docenteProyecto.getProyecto();
            if (proyecto != null) {
                proyecto.getDocenteProyectoCollection().remove(docenteProyecto);
                proyecto = em.merge(proyecto);
            }
            em.remove(docenteProyecto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DocenteProyecto> findDocenteProyectoEntities() {
        return findDocenteProyectoEntities(true, -1, -1);
    }

    public List<DocenteProyecto> findDocenteProyectoEntities(int maxResults, int firstResult) {
        return findDocenteProyectoEntities(false, maxResults, firstResult);
    }

    private List<DocenteProyecto> findDocenteProyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DocenteProyecto.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public DocenteProyecto findDocenteProyecto(DocenteProyectoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DocenteProyecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getDocenteProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DocenteProyecto> rt = cq.from(DocenteProyecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
