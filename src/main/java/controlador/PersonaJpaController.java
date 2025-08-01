/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidad.Estudiante;
import entidad.Docente;
import entidad.Persona;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author es982
 */
public class PersonaJpaController implements Serializable {

    public PersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public PersonaJpaController(){
        emf=Persistence.createEntityManagerFactory("SigerproMVC");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiante estudiante = persona.getEstudiante();
            if (estudiante != null) {
                estudiante = em.getReference(estudiante.getClass(), estudiante.getNumControl());
                persona.setEstudiante(estudiante);
            }
            Docente docente = persona.getDocente();
            if (docente != null) {
                docente = em.getReference(docente.getClass(), docente.getNoTarjeta());
                persona.setDocente(docente);
            }
            em.persist(persona);
            if (estudiante != null) {
                Persona oldIdPersonaOfEstudiante = estudiante.getIdPersona();
                if (oldIdPersonaOfEstudiante != null) {
                    oldIdPersonaOfEstudiante.setEstudiante(null);
                    oldIdPersonaOfEstudiante = em.merge(oldIdPersonaOfEstudiante);
                }
                estudiante.setIdPersona(persona);
                estudiante = em.merge(estudiante);
            }
            if (docente != null) {
                Persona oldIdPersonaOfDocente = docente.getIdPersona();
                if (oldIdPersonaOfDocente != null) {
                    oldIdPersonaOfDocente.setDocente(null);
                    oldIdPersonaOfDocente = em.merge(oldIdPersonaOfDocente);
                }
                docente.setIdPersona(persona);
                docente = em.merge(docente);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persistentPersona = em.find(Persona.class, persona.getIdPersona());
            Estudiante estudianteOld = persistentPersona.getEstudiante();
            Estudiante estudianteNew = persona.getEstudiante();
            Docente docenteOld = persistentPersona.getDocente();
            Docente docenteNew = persona.getDocente();
            List<String> illegalOrphanMessages = null;
            if (estudianteOld != null && !estudianteOld.equals(estudianteNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Estudiante " + estudianteOld + " since its idPersona field is not nullable.");
            }
            if (docenteOld != null && !docenteOld.equals(docenteNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Docente " + docenteOld + " since its idPersona field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (estudianteNew != null) {
                estudianteNew = em.getReference(estudianteNew.getClass(), estudianteNew.getNumControl());
                persona.setEstudiante(estudianteNew);
            }
            if (docenteNew != null) {
                docenteNew = em.getReference(docenteNew.getClass(), docenteNew.getNoTarjeta());
                persona.setDocente(docenteNew);
            }
            persona = em.merge(persona);
            if (estudianteNew != null && !estudianteNew.equals(estudianteOld)) {
                Persona oldIdPersonaOfEstudiante = estudianteNew.getIdPersona();
                if (oldIdPersonaOfEstudiante != null) {
                    oldIdPersonaOfEstudiante.setEstudiante(null);
                    oldIdPersonaOfEstudiante = em.merge(oldIdPersonaOfEstudiante);
                }
                estudianteNew.setIdPersona(persona);
                estudianteNew = em.merge(estudianteNew);
            }
            if (docenteNew != null && !docenteNew.equals(docenteOld)) {
                Persona oldIdPersonaOfDocente = docenteNew.getIdPersona();
                if (oldIdPersonaOfDocente != null) {
                    oldIdPersonaOfDocente.setDocente(null);
                    oldIdPersonaOfDocente = em.merge(oldIdPersonaOfDocente);
                }
                docenteNew.setIdPersona(persona);
                docenteNew = em.merge(docenteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = persona.getIdPersona();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getIdPersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Estudiante estudianteOrphanCheck = persona.getEstudiante();
            if (estudianteOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Estudiante " + estudianteOrphanCheck + " in its estudiante field has a non-nullable idPersona field.");
            }
            Docente docenteOrphanCheck = persona.getDocente();
            if (docenteOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Docente " + docenteOrphanCheck + " in its docente field has a non-nullable idPersona field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(persona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
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

    public Persona findPersona(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
