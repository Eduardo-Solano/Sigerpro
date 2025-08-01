/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import entidad.Departamento;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidad.Estudiante;
import java.util.ArrayList;
import java.util.Collection;
import entidad.Docente;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author es982
 */
public class DepartamentoJpaController implements Serializable {

    public DepartamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public DepartamentoJpaController(){
        emf=Persistence.createEntityManagerFactory("SigerproMVC");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Departamento departamento) {
        if (departamento.getEstudianteCollection() == null) {
            departamento.setEstudianteCollection(new ArrayList<Estudiante>());
        }
        if (departamento.getDocenteCollection() == null) {
            departamento.setDocenteCollection(new ArrayList<Docente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Estudiante> attachedEstudianteCollection = new ArrayList<Estudiante>();
            for (Estudiante estudianteCollectionEstudianteToAttach : departamento.getEstudianteCollection()) {
                estudianteCollectionEstudianteToAttach = em.getReference(estudianteCollectionEstudianteToAttach.getClass(), estudianteCollectionEstudianteToAttach.getNumControl());
                attachedEstudianteCollection.add(estudianteCollectionEstudianteToAttach);
            }
            departamento.setEstudianteCollection(attachedEstudianteCollection);
            Collection<Docente> attachedDocenteCollection = new ArrayList<Docente>();
            for (Docente docenteCollectionDocenteToAttach : departamento.getDocenteCollection()) {
                docenteCollectionDocenteToAttach = em.getReference(docenteCollectionDocenteToAttach.getClass(), docenteCollectionDocenteToAttach.getNoTarjeta());
                attachedDocenteCollection.add(docenteCollectionDocenteToAttach);
            }
            departamento.setDocenteCollection(attachedDocenteCollection);
            em.persist(departamento);
            for (Estudiante estudianteCollectionEstudiante : departamento.getEstudianteCollection()) {
                Departamento oldIdDepartamentoOfEstudianteCollectionEstudiante = estudianteCollectionEstudiante.getIdDepartamento();
                estudianteCollectionEstudiante.setIdDepartamento(departamento);
                estudianteCollectionEstudiante = em.merge(estudianteCollectionEstudiante);
                if (oldIdDepartamentoOfEstudianteCollectionEstudiante != null) {
                    oldIdDepartamentoOfEstudianteCollectionEstudiante.getEstudianteCollection().remove(estudianteCollectionEstudiante);
                    oldIdDepartamentoOfEstudianteCollectionEstudiante = em.merge(oldIdDepartamentoOfEstudianteCollectionEstudiante);
                }
            }
            for (Docente docenteCollectionDocente : departamento.getDocenteCollection()) {
                Departamento oldIdDepartamentoOfDocenteCollectionDocente = docenteCollectionDocente.getIdDepartamento();
                docenteCollectionDocente.setIdDepartamento(departamento);
                docenteCollectionDocente = em.merge(docenteCollectionDocente);
                if (oldIdDepartamentoOfDocenteCollectionDocente != null) {
                    oldIdDepartamentoOfDocenteCollectionDocente.getDocenteCollection().remove(docenteCollectionDocente);
                    oldIdDepartamentoOfDocenteCollectionDocente = em.merge(oldIdDepartamentoOfDocenteCollectionDocente);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Departamento departamento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento persistentDepartamento = em.find(Departamento.class, departamento.getIdDepartamento());
            Collection<Estudiante> estudianteCollectionOld = persistentDepartamento.getEstudianteCollection();
            Collection<Estudiante> estudianteCollectionNew = departamento.getEstudianteCollection();
            Collection<Docente> docenteCollectionOld = persistentDepartamento.getDocenteCollection();
            Collection<Docente> docenteCollectionNew = departamento.getDocenteCollection();
            List<String> illegalOrphanMessages = null;
            for (Estudiante estudianteCollectionOldEstudiante : estudianteCollectionOld) {
                if (!estudianteCollectionNew.contains(estudianteCollectionOldEstudiante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Estudiante " + estudianteCollectionOldEstudiante + " since its idDepartamento field is not nullable.");
                }
            }
            for (Docente docenteCollectionOldDocente : docenteCollectionOld) {
                if (!docenteCollectionNew.contains(docenteCollectionOldDocente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Docente " + docenteCollectionOldDocente + " since its idDepartamento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Estudiante> attachedEstudianteCollectionNew = new ArrayList<Estudiante>();
            for (Estudiante estudianteCollectionNewEstudianteToAttach : estudianteCollectionNew) {
                estudianteCollectionNewEstudianteToAttach = em.getReference(estudianteCollectionNewEstudianteToAttach.getClass(), estudianteCollectionNewEstudianteToAttach.getNumControl());
                attachedEstudianteCollectionNew.add(estudianteCollectionNewEstudianteToAttach);
            }
            estudianteCollectionNew = attachedEstudianteCollectionNew;
            departamento.setEstudianteCollection(estudianteCollectionNew);
            Collection<Docente> attachedDocenteCollectionNew = new ArrayList<Docente>();
            for (Docente docenteCollectionNewDocenteToAttach : docenteCollectionNew) {
                docenteCollectionNewDocenteToAttach = em.getReference(docenteCollectionNewDocenteToAttach.getClass(), docenteCollectionNewDocenteToAttach.getNoTarjeta());
                attachedDocenteCollectionNew.add(docenteCollectionNewDocenteToAttach);
            }
            docenteCollectionNew = attachedDocenteCollectionNew;
            departamento.setDocenteCollection(docenteCollectionNew);
            departamento = em.merge(departamento);
            for (Estudiante estudianteCollectionNewEstudiante : estudianteCollectionNew) {
                if (!estudianteCollectionOld.contains(estudianteCollectionNewEstudiante)) {
                    Departamento oldIdDepartamentoOfEstudianteCollectionNewEstudiante = estudianteCollectionNewEstudiante.getIdDepartamento();
                    estudianteCollectionNewEstudiante.setIdDepartamento(departamento);
                    estudianteCollectionNewEstudiante = em.merge(estudianteCollectionNewEstudiante);
                    if (oldIdDepartamentoOfEstudianteCollectionNewEstudiante != null && !oldIdDepartamentoOfEstudianteCollectionNewEstudiante.equals(departamento)) {
                        oldIdDepartamentoOfEstudianteCollectionNewEstudiante.getEstudianteCollection().remove(estudianteCollectionNewEstudiante);
                        oldIdDepartamentoOfEstudianteCollectionNewEstudiante = em.merge(oldIdDepartamentoOfEstudianteCollectionNewEstudiante);
                    }
                }
            }
            for (Docente docenteCollectionNewDocente : docenteCollectionNew) {
                if (!docenteCollectionOld.contains(docenteCollectionNewDocente)) {
                    Departamento oldIdDepartamentoOfDocenteCollectionNewDocente = docenteCollectionNewDocente.getIdDepartamento();
                    docenteCollectionNewDocente.setIdDepartamento(departamento);
                    docenteCollectionNewDocente = em.merge(docenteCollectionNewDocente);
                    if (oldIdDepartamentoOfDocenteCollectionNewDocente != null && !oldIdDepartamentoOfDocenteCollectionNewDocente.equals(departamento)) {
                        oldIdDepartamentoOfDocenteCollectionNewDocente.getDocenteCollection().remove(docenteCollectionNewDocente);
                        oldIdDepartamentoOfDocenteCollectionNewDocente = em.merge(oldIdDepartamentoOfDocenteCollectionNewDocente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = departamento.getIdDepartamento();
                if (findDepartamento(id) == null) {
                    throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.");
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
            Departamento departamento;
            try {
                departamento = em.getReference(Departamento.class, id);
                departamento.getIdDepartamento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Estudiante> estudianteCollectionOrphanCheck = departamento.getEstudianteCollection();
            for (Estudiante estudianteCollectionOrphanCheckEstudiante : estudianteCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Departamento (" + departamento + ") cannot be destroyed since the Estudiante " + estudianteCollectionOrphanCheckEstudiante + " in its estudianteCollection field has a non-nullable idDepartamento field.");
            }
            Collection<Docente> docenteCollectionOrphanCheck = departamento.getDocenteCollection();
            for (Docente docenteCollectionOrphanCheckDocente : docenteCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Departamento (" + departamento + ") cannot be destroyed since the Docente " + docenteCollectionOrphanCheckDocente + " in its docenteCollection field has a non-nullable idDepartamento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(departamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Departamento> findDepartamentoEntities() {
        return findDepartamentoEntities(true, -1, -1);
    }

    public List<Departamento> findDepartamentoEntities(int maxResults, int firstResult) {
        return findDepartamentoEntities(false, maxResults, firstResult);
    }

    private List<Departamento> findDepartamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Departamento.class));
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

    public Departamento findDepartamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Departamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getDepartamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Departamento> rt = cq.from(Departamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
