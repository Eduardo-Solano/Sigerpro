/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import controlador.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidad.Departamento;
import entidad.Docente;
import entidad.Persona;
import entidad.DocenteProyecto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author es982
 */
public class DocenteJpaController implements Serializable {

    public DocenteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public DocenteJpaController(){
        emf=Persistence.createEntityManagerFactory("SigerproMVC");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Docente docente) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (docente.getDocenteProyectoCollection() == null) {
            docente.setDocenteProyectoCollection(new ArrayList<DocenteProyecto>());
        }
        List<String> illegalOrphanMessages = null;
        Persona idPersonaOrphanCheck = docente.getIdPersona();
        if (idPersonaOrphanCheck != null) {
            Docente oldDocenteOfIdPersona = idPersonaOrphanCheck.getDocente();
            if (oldDocenteOfIdPersona != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Persona " + idPersonaOrphanCheck + " already has an item of type Docente whose idPersona column cannot be null. Please make another selection for the idPersona field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento idDepartamento = docente.getIdDepartamento();
            if (idDepartamento != null) {
                idDepartamento = em.getReference(idDepartamento.getClass(), idDepartamento.getIdDepartamento());
                docente.setIdDepartamento(idDepartamento);
            }
            Persona idPersona = docente.getIdPersona();
            if (idPersona != null) {
                idPersona = em.getReference(idPersona.getClass(), idPersona.getIdPersona());
                docente.setIdPersona(idPersona);
            }
            Collection<DocenteProyecto> attachedDocenteProyectoCollection = new ArrayList<DocenteProyecto>();
            for (DocenteProyecto docenteProyectoCollectionDocenteProyectoToAttach : docente.getDocenteProyectoCollection()) {
                docenteProyectoCollectionDocenteProyectoToAttach = em.getReference(docenteProyectoCollectionDocenteProyectoToAttach.getClass(), docenteProyectoCollectionDocenteProyectoToAttach.getDocenteProyectoPK());
                attachedDocenteProyectoCollection.add(docenteProyectoCollectionDocenteProyectoToAttach);
            }
            docente.setDocenteProyectoCollection(attachedDocenteProyectoCollection);
            em.persist(docente);
            if (idDepartamento != null) {
                idDepartamento.getDocenteCollection().add(docente);
                idDepartamento = em.merge(idDepartamento);
            }
            if (idPersona != null) {
                idPersona.setDocente(docente);
                idPersona = em.merge(idPersona);
            }
            for (DocenteProyecto docenteProyectoCollectionDocenteProyecto : docente.getDocenteProyectoCollection()) {
                Docente oldDocenteOfDocenteProyectoCollectionDocenteProyecto = docenteProyectoCollectionDocenteProyecto.getDocente();
                docenteProyectoCollectionDocenteProyecto.setDocente(docente);
                docenteProyectoCollectionDocenteProyecto = em.merge(docenteProyectoCollectionDocenteProyecto);
                if (oldDocenteOfDocenteProyectoCollectionDocenteProyecto != null) {
                    oldDocenteOfDocenteProyectoCollectionDocenteProyecto.getDocenteProyectoCollection().remove(docenteProyectoCollectionDocenteProyecto);
                    oldDocenteOfDocenteProyectoCollectionDocenteProyecto = em.merge(oldDocenteOfDocenteProyectoCollectionDocenteProyecto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDocente(docente.getNoTarjeta()) != null) {
                throw new PreexistingEntityException("Docente " + docente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Docente docente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Docente persistentDocente = em.find(Docente.class, docente.getNoTarjeta());
            Departamento idDepartamentoOld = persistentDocente.getIdDepartamento();
            Departamento idDepartamentoNew = docente.getIdDepartamento();
            Persona idPersonaOld = persistentDocente.getIdPersona();
            Persona idPersonaNew = docente.getIdPersona();
            Collection<DocenteProyecto> docenteProyectoCollectionOld = persistentDocente.getDocenteProyectoCollection();
            Collection<DocenteProyecto> docenteProyectoCollectionNew = docente.getDocenteProyectoCollection();
            List<String> illegalOrphanMessages = null;
            if (idPersonaNew != null && !idPersonaNew.equals(idPersonaOld)) {
                Docente oldDocenteOfIdPersona = idPersonaNew.getDocente();
                if (oldDocenteOfIdPersona != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Persona " + idPersonaNew + " already has an item of type Docente whose idPersona column cannot be null. Please make another selection for the idPersona field.");
                }
            }
            for (DocenteProyecto docenteProyectoCollectionOldDocenteProyecto : docenteProyectoCollectionOld) {
                if (!docenteProyectoCollectionNew.contains(docenteProyectoCollectionOldDocenteProyecto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DocenteProyecto " + docenteProyectoCollectionOldDocenteProyecto + " since its docente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idDepartamentoNew != null) {
                idDepartamentoNew = em.getReference(idDepartamentoNew.getClass(), idDepartamentoNew.getIdDepartamento());
                docente.setIdDepartamento(idDepartamentoNew);
            }
            if (idPersonaNew != null) {
                idPersonaNew = em.getReference(idPersonaNew.getClass(), idPersonaNew.getIdPersona());
                docente.setIdPersona(idPersonaNew);
            }
            Collection<DocenteProyecto> attachedDocenteProyectoCollectionNew = new ArrayList<DocenteProyecto>();
            for (DocenteProyecto docenteProyectoCollectionNewDocenteProyectoToAttach : docenteProyectoCollectionNew) {
                docenteProyectoCollectionNewDocenteProyectoToAttach = em.getReference(docenteProyectoCollectionNewDocenteProyectoToAttach.getClass(), docenteProyectoCollectionNewDocenteProyectoToAttach.getDocenteProyectoPK());
                attachedDocenteProyectoCollectionNew.add(docenteProyectoCollectionNewDocenteProyectoToAttach);
            }
            docenteProyectoCollectionNew = attachedDocenteProyectoCollectionNew;
            docente.setDocenteProyectoCollection(docenteProyectoCollectionNew);
            docente = em.merge(docente);
            if (idDepartamentoOld != null && !idDepartamentoOld.equals(idDepartamentoNew)) {
                idDepartamentoOld.getDocenteCollection().remove(docente);
                idDepartamentoOld = em.merge(idDepartamentoOld);
            }
            if (idDepartamentoNew != null && !idDepartamentoNew.equals(idDepartamentoOld)) {
                idDepartamentoNew.getDocenteCollection().add(docente);
                idDepartamentoNew = em.merge(idDepartamentoNew);
            }
            if (idPersonaOld != null && !idPersonaOld.equals(idPersonaNew)) {
                idPersonaOld.setDocente(null);
                idPersonaOld = em.merge(idPersonaOld);
            }
            if (idPersonaNew != null && !idPersonaNew.equals(idPersonaOld)) {
                idPersonaNew.setDocente(docente);
                idPersonaNew = em.merge(idPersonaNew);
            }
            for (DocenteProyecto docenteProyectoCollectionNewDocenteProyecto : docenteProyectoCollectionNew) {
                if (!docenteProyectoCollectionOld.contains(docenteProyectoCollectionNewDocenteProyecto)) {
                    Docente oldDocenteOfDocenteProyectoCollectionNewDocenteProyecto = docenteProyectoCollectionNewDocenteProyecto.getDocente();
                    docenteProyectoCollectionNewDocenteProyecto.setDocente(docente);
                    docenteProyectoCollectionNewDocenteProyecto = em.merge(docenteProyectoCollectionNewDocenteProyecto);
                    if (oldDocenteOfDocenteProyectoCollectionNewDocenteProyecto != null && !oldDocenteOfDocenteProyectoCollectionNewDocenteProyecto.equals(docente)) {
                        oldDocenteOfDocenteProyectoCollectionNewDocenteProyecto.getDocenteProyectoCollection().remove(docenteProyectoCollectionNewDocenteProyecto);
                        oldDocenteOfDocenteProyectoCollectionNewDocenteProyecto = em.merge(oldDocenteOfDocenteProyectoCollectionNewDocenteProyecto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = docente.getNoTarjeta();
                if (findDocente(id) == null) {
                    throw new NonexistentEntityException("The docente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Docente docente;
            try {
                docente = em.getReference(Docente.class, id);
                docente.getNoTarjeta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The docente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<DocenteProyecto> docenteProyectoCollectionOrphanCheck = docente.getDocenteProyectoCollection();
            for (DocenteProyecto docenteProyectoCollectionOrphanCheckDocenteProyecto : docenteProyectoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Docente (" + docente + ") cannot be destroyed since the DocenteProyecto " + docenteProyectoCollectionOrphanCheckDocenteProyecto + " in its docenteProyectoCollection field has a non-nullable docente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Departamento idDepartamento = docente.getIdDepartamento();
            if (idDepartamento != null) {
                idDepartamento.getDocenteCollection().remove(docente);
                idDepartamento = em.merge(idDepartamento);
            }
            Persona idPersona = docente.getIdPersona();
            if (idPersona != null) {
                idPersona.setDocente(null);
                idPersona = em.merge(idPersona);
            }
            em.remove(docente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Docente> findDocenteEntities() {
        return findDocenteEntities(true, -1, -1);
    }

    public List<Docente> findDocenteEntities(int maxResults, int firstResult) {
        return findDocenteEntities(false, maxResults, firstResult);
    }

    private List<Docente> findDocenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Docente.class));
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

    public Docente findDocente(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Docente.class, id);
        } finally {
            em.close();
        }
    }

    public int getDocenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Docente> rt = cq.from(Docente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
