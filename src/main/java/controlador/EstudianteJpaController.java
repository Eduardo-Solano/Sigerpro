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
import entidad.Estudiante;
import entidad.Expediente;
import entidad.Persona;
import entidad.Proyecto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;

/**
 *
 * @author es982
 */
public class EstudianteJpaController implements Serializable {

    public EstudianteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public EstudianteJpaController(){
        emf=Persistence.createEntityManagerFactory("SigerproMVC");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estudiante estudiante) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Persona idPersonaOrphanCheck = estudiante.getIdPersona();
        if (idPersonaOrphanCheck != null) {
            Estudiante oldEstudianteOfIdPersona = idPersonaOrphanCheck.getEstudiante();
            if (oldEstudianteOfIdPersona != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Persona " + idPersonaOrphanCheck + " already has an item of type Estudiante whose idPersona column cannot be null. Please make another selection for the idPersona field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento idDepartamento = estudiante.getIdDepartamento();
            if (idDepartamento != null) {
                idDepartamento = em.getReference(idDepartamento.getClass(), idDepartamento.getIdDepartamento());
                estudiante.setIdDepartamento(idDepartamento);
            }
            Expediente idExpediente = estudiante.getIdExpediente();
            if (idExpediente != null) {
                idExpediente = em.getReference(idExpediente.getClass(), idExpediente.getIdExpediente());
                estudiante.setIdExpediente(idExpediente);
            }
            Persona idPersona = estudiante.getIdPersona();
            if (idPersona != null) {
                idPersona = em.getReference(idPersona.getClass(), idPersona.getIdPersona());
                estudiante.setIdPersona(idPersona);
            }
            Proyecto idProyecto = estudiante.getIdProyecto();
            if (idProyecto != null) {
                idProyecto = em.getReference(idProyecto.getClass(), idProyecto.getIdProyecto());
                estudiante.setIdProyecto(idProyecto);
            }
            em.persist(estudiante);
            if (idDepartamento != null) {
                idDepartamento.getEstudianteCollection().add(estudiante);
                idDepartamento = em.merge(idDepartamento);
            }
            if (idExpediente != null) {
                idExpediente.getEstudianteCollection().add(estudiante);
                idExpediente = em.merge(idExpediente);
            }
            if (idPersona != null) {
                idPersona.setEstudiante(estudiante);
                idPersona = em.merge(idPersona);
            }
            if (idProyecto != null) {
                idProyecto.getEstudianteCollection().add(estudiante);
                idProyecto = em.merge(idProyecto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstudiante(estudiante.getNumControl()) != null) {
                throw new PreexistingEntityException("Estudiante " + estudiante + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estudiante estudiante) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiante persistentEstudiante = em.find(Estudiante.class, estudiante.getNumControl());
            Departamento idDepartamentoOld = persistentEstudiante.getIdDepartamento();
            Departamento idDepartamentoNew = estudiante.getIdDepartamento();
            Expediente idExpedienteOld = persistentEstudiante.getIdExpediente();
            Expediente idExpedienteNew = estudiante.getIdExpediente();
            Persona idPersonaOld = persistentEstudiante.getIdPersona();
            Persona idPersonaNew = estudiante.getIdPersona();
            Proyecto idProyectoOld = persistentEstudiante.getIdProyecto();
            Proyecto idProyectoNew = estudiante.getIdProyecto();
            List<String> illegalOrphanMessages = null;
            if (idPersonaNew != null && !idPersonaNew.equals(idPersonaOld)) {
                Estudiante oldEstudianteOfIdPersona = idPersonaNew.getEstudiante();
                if (oldEstudianteOfIdPersona != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Persona " + idPersonaNew + " already has an item of type Estudiante whose idPersona column cannot be null. Please make another selection for the idPersona field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idDepartamentoNew != null) {
                idDepartamentoNew = em.getReference(idDepartamentoNew.getClass(), idDepartamentoNew.getIdDepartamento());
                estudiante.setIdDepartamento(idDepartamentoNew);
            }
            if (idExpedienteNew != null) {
                idExpedienteNew = em.getReference(idExpedienteNew.getClass(), idExpedienteNew.getIdExpediente());
                estudiante.setIdExpediente(idExpedienteNew);
            }
            if (idPersonaNew != null) {
                idPersonaNew = em.getReference(idPersonaNew.getClass(), idPersonaNew.getIdPersona());
                estudiante.setIdPersona(idPersonaNew);
            }
            if (idProyectoNew != null) {
                idProyectoNew = em.getReference(idProyectoNew.getClass(), idProyectoNew.getIdProyecto());
                estudiante.setIdProyecto(idProyectoNew);
            }
            estudiante = em.merge(estudiante);
            if (idDepartamentoOld != null && !idDepartamentoOld.equals(idDepartamentoNew)) {
                idDepartamentoOld.getEstudianteCollection().remove(estudiante);
                idDepartamentoOld = em.merge(idDepartamentoOld);
            }
            if (idDepartamentoNew != null && !idDepartamentoNew.equals(idDepartamentoOld)) {
                idDepartamentoNew.getEstudianteCollection().add(estudiante);
                idDepartamentoNew = em.merge(idDepartamentoNew);
            }
            if (idExpedienteOld != null && !idExpedienteOld.equals(idExpedienteNew)) {
                idExpedienteOld.getEstudianteCollection().remove(estudiante);
                idExpedienteOld = em.merge(idExpedienteOld);
            }
            if (idExpedienteNew != null && !idExpedienteNew.equals(idExpedienteOld)) {
                idExpedienteNew.getEstudianteCollection().add(estudiante);
                idExpedienteNew = em.merge(idExpedienteNew);
            }
            if (idPersonaOld != null && !idPersonaOld.equals(idPersonaNew)) {
                idPersonaOld.setEstudiante(null);
                idPersonaOld = em.merge(idPersonaOld);
            }
            if (idPersonaNew != null && !idPersonaNew.equals(idPersonaOld)) {
                idPersonaNew.setEstudiante(estudiante);
                idPersonaNew = em.merge(idPersonaNew);
            }
            if (idProyectoOld != null && !idProyectoOld.equals(idProyectoNew)) {
                idProyectoOld.getEstudianteCollection().remove(estudiante);
                idProyectoOld = em.merge(idProyectoOld);
            }
            if (idProyectoNew != null && !idProyectoNew.equals(idProyectoOld)) {
                idProyectoNew.getEstudianteCollection().add(estudiante);
                idProyectoNew = em.merge(idProyectoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = estudiante.getNumControl();
                if (findEstudiante(id) == null) {
                    throw new NonexistentEntityException("The estudiante with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiante estudiante;
            try {
                estudiante = em.getReference(Estudiante.class, id);
                estudiante.getNumControl();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estudiante with id " + id + " no longer exists.", enfe);
            }
            Departamento idDepartamento = estudiante.getIdDepartamento();
            if (idDepartamento != null) {
                idDepartamento.getEstudianteCollection().remove(estudiante);
                idDepartamento = em.merge(idDepartamento);
            }
            Expediente idExpediente = estudiante.getIdExpediente();
            if (idExpediente != null) {
                idExpediente.getEstudianteCollection().remove(estudiante);
                idExpediente = em.merge(idExpediente);
            }
            Persona idPersona = estudiante.getIdPersona();
            if (idPersona != null) {
                idPersona.setEstudiante(null);
                idPersona = em.merge(idPersona);
            }
            Proyecto idProyecto = estudiante.getIdProyecto();
            if (idProyecto != null) {
                idProyecto.getEstudianteCollection().remove(estudiante);
                idProyecto = em.merge(idProyecto);
            }
            em.remove(estudiante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estudiante> findEstudianteEntities() {
        return findEstudianteEntities(true, -1, -1);
    }

    public List<Estudiante> findEstudianteEntities(int maxResults, int firstResult) {
        return findEstudianteEntities(false, maxResults, firstResult);
    }

    private List<Estudiante> findEstudianteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estudiante.class));
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

    public Estudiante findEstudiante(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estudiante.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstudianteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estudiante> rt = cq.from(Estudiante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Estudiante findEstudianteByExpediente(Expediente exp) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Estudiante.findEstudianteByExpediente");
            q.setParameter("idExpediente", exp);
            return (Estudiante) q.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            return null; // Si no se encuentra o hay más de una con el mismo nombre
        } finally {
            em.close();
        }
    }
    
}
