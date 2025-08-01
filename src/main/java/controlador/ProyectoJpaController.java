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
import entidad.Empresa;
import entidad.Estudiante;
import java.util.ArrayList;
import java.util.Collection;
import entidad.DocenteProyecto;
import entidad.Proyecto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author es982
 */
public class ProyectoJpaController implements Serializable {

    public ProyectoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public ProyectoJpaController(){
        emf=Persistence.createEntityManagerFactory("SigerproMVC");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proyecto proyecto) {
        if (proyecto.getEstudianteCollection() == null) {
            proyecto.setEstudianteCollection(new ArrayList<Estudiante>());
        }
        if (proyecto.getDocenteProyectoCollection() == null) {
            proyecto.setDocenteProyectoCollection(new ArrayList<DocenteProyecto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa idEmpresa = proyecto.getIdEmpresa();
            if (idEmpresa != null) {
                idEmpresa = em.getReference(idEmpresa.getClass(), idEmpresa.getIdEmpresa());
                proyecto.setIdEmpresa(idEmpresa);
            }
            Collection<Estudiante> attachedEstudianteCollection = new ArrayList<Estudiante>();
            for (Estudiante estudianteCollectionEstudianteToAttach : proyecto.getEstudianteCollection()) {
                estudianteCollectionEstudianteToAttach = em.getReference(estudianteCollectionEstudianteToAttach.getClass(), estudianteCollectionEstudianteToAttach.getNumControl());
                attachedEstudianteCollection.add(estudianteCollectionEstudianteToAttach);
            }
            proyecto.setEstudianteCollection(attachedEstudianteCollection);
            Collection<DocenteProyecto> attachedDocenteProyectoCollection = new ArrayList<DocenteProyecto>();
            for (DocenteProyecto docenteProyectoCollectionDocenteProyectoToAttach : proyecto.getDocenteProyectoCollection()) {
                docenteProyectoCollectionDocenteProyectoToAttach = em.getReference(docenteProyectoCollectionDocenteProyectoToAttach.getClass(), docenteProyectoCollectionDocenteProyectoToAttach.getDocenteProyectoPK());
                attachedDocenteProyectoCollection.add(docenteProyectoCollectionDocenteProyectoToAttach);
            }
            proyecto.setDocenteProyectoCollection(attachedDocenteProyectoCollection);
            em.persist(proyecto);
            if (idEmpresa != null) {
                idEmpresa.getProyectoCollection().add(proyecto);
                idEmpresa = em.merge(idEmpresa);
            }
            for (Estudiante estudianteCollectionEstudiante : proyecto.getEstudianteCollection()) {
                Proyecto oldIdProyectoOfEstudianteCollectionEstudiante = estudianteCollectionEstudiante.getIdProyecto();
                estudianteCollectionEstudiante.setIdProyecto(proyecto);
                estudianteCollectionEstudiante = em.merge(estudianteCollectionEstudiante);
                if (oldIdProyectoOfEstudianteCollectionEstudiante != null) {
                    oldIdProyectoOfEstudianteCollectionEstudiante.getEstudianteCollection().remove(estudianteCollectionEstudiante);
                    oldIdProyectoOfEstudianteCollectionEstudiante = em.merge(oldIdProyectoOfEstudianteCollectionEstudiante);
                }
            }
            for (DocenteProyecto docenteProyectoCollectionDocenteProyecto : proyecto.getDocenteProyectoCollection()) {
                Proyecto oldProyectoOfDocenteProyectoCollectionDocenteProyecto = docenteProyectoCollectionDocenteProyecto.getProyecto();
                docenteProyectoCollectionDocenteProyecto.setProyecto(proyecto);
                docenteProyectoCollectionDocenteProyecto = em.merge(docenteProyectoCollectionDocenteProyecto);
                if (oldProyectoOfDocenteProyectoCollectionDocenteProyecto != null) {
                    oldProyectoOfDocenteProyectoCollectionDocenteProyecto.getDocenteProyectoCollection().remove(docenteProyectoCollectionDocenteProyecto);
                    oldProyectoOfDocenteProyectoCollectionDocenteProyecto = em.merge(oldProyectoOfDocenteProyectoCollectionDocenteProyecto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proyecto proyecto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proyecto persistentProyecto = em.find(Proyecto.class, proyecto.getIdProyecto());
            Empresa idEmpresaOld = persistentProyecto.getIdEmpresa();
            Empresa idEmpresaNew = proyecto.getIdEmpresa();
            Collection<Estudiante> estudianteCollectionOld = persistentProyecto.getEstudianteCollection();
            Collection<Estudiante> estudianteCollectionNew = proyecto.getEstudianteCollection();
            Collection<DocenteProyecto> docenteProyectoCollectionOld = persistentProyecto.getDocenteProyectoCollection();
            Collection<DocenteProyecto> docenteProyectoCollectionNew = proyecto.getDocenteProyectoCollection();
            List<String> illegalOrphanMessages = null;
            for (DocenteProyecto docenteProyectoCollectionOldDocenteProyecto : docenteProyectoCollectionOld) {
                if (!docenteProyectoCollectionNew.contains(docenteProyectoCollectionOldDocenteProyecto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DocenteProyecto " + docenteProyectoCollectionOldDocenteProyecto + " since its proyecto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idEmpresaNew != null) {
                idEmpresaNew = em.getReference(idEmpresaNew.getClass(), idEmpresaNew.getIdEmpresa());
                proyecto.setIdEmpresa(idEmpresaNew);
            }
            Collection<Estudiante> attachedEstudianteCollectionNew = new ArrayList<Estudiante>();
            for (Estudiante estudianteCollectionNewEstudianteToAttach : estudianteCollectionNew) {
                estudianteCollectionNewEstudianteToAttach = em.getReference(estudianteCollectionNewEstudianteToAttach.getClass(), estudianteCollectionNewEstudianteToAttach.getNumControl());
                attachedEstudianteCollectionNew.add(estudianteCollectionNewEstudianteToAttach);
            }
            estudianteCollectionNew = attachedEstudianteCollectionNew;
            proyecto.setEstudianteCollection(estudianteCollectionNew);
            Collection<DocenteProyecto> attachedDocenteProyectoCollectionNew = new ArrayList<DocenteProyecto>();
            for (DocenteProyecto docenteProyectoCollectionNewDocenteProyectoToAttach : docenteProyectoCollectionNew) {
                docenteProyectoCollectionNewDocenteProyectoToAttach = em.getReference(docenteProyectoCollectionNewDocenteProyectoToAttach.getClass(), docenteProyectoCollectionNewDocenteProyectoToAttach.getDocenteProyectoPK());
                attachedDocenteProyectoCollectionNew.add(docenteProyectoCollectionNewDocenteProyectoToAttach);
            }
            docenteProyectoCollectionNew = attachedDocenteProyectoCollectionNew;
            proyecto.setDocenteProyectoCollection(docenteProyectoCollectionNew);
            proyecto = em.merge(proyecto);
            if (idEmpresaOld != null && !idEmpresaOld.equals(idEmpresaNew)) {
                idEmpresaOld.getProyectoCollection().remove(proyecto);
                idEmpresaOld = em.merge(idEmpresaOld);
            }
            if (idEmpresaNew != null && !idEmpresaNew.equals(idEmpresaOld)) {
                idEmpresaNew.getProyectoCollection().add(proyecto);
                idEmpresaNew = em.merge(idEmpresaNew);
            }
            for (Estudiante estudianteCollectionOldEstudiante : estudianteCollectionOld) {
                if (!estudianteCollectionNew.contains(estudianteCollectionOldEstudiante)) {
                    estudianteCollectionOldEstudiante.setIdProyecto(null);
                    estudianteCollectionOldEstudiante = em.merge(estudianteCollectionOldEstudiante);
                }
            }
            for (Estudiante estudianteCollectionNewEstudiante : estudianteCollectionNew) {
                if (!estudianteCollectionOld.contains(estudianteCollectionNewEstudiante)) {
                    Proyecto oldIdProyectoOfEstudianteCollectionNewEstudiante = estudianteCollectionNewEstudiante.getIdProyecto();
                    estudianteCollectionNewEstudiante.setIdProyecto(proyecto);
                    estudianteCollectionNewEstudiante = em.merge(estudianteCollectionNewEstudiante);
                    if (oldIdProyectoOfEstudianteCollectionNewEstudiante != null && !oldIdProyectoOfEstudianteCollectionNewEstudiante.equals(proyecto)) {
                        oldIdProyectoOfEstudianteCollectionNewEstudiante.getEstudianteCollection().remove(estudianteCollectionNewEstudiante);
                        oldIdProyectoOfEstudianteCollectionNewEstudiante = em.merge(oldIdProyectoOfEstudianteCollectionNewEstudiante);
                    }
                }
            }
            for (DocenteProyecto docenteProyectoCollectionNewDocenteProyecto : docenteProyectoCollectionNew) {
                if (!docenteProyectoCollectionOld.contains(docenteProyectoCollectionNewDocenteProyecto)) {
                    Proyecto oldProyectoOfDocenteProyectoCollectionNewDocenteProyecto = docenteProyectoCollectionNewDocenteProyecto.getProyecto();
                    docenteProyectoCollectionNewDocenteProyecto.setProyecto(proyecto);
                    docenteProyectoCollectionNewDocenteProyecto = em.merge(docenteProyectoCollectionNewDocenteProyecto);
                    if (oldProyectoOfDocenteProyectoCollectionNewDocenteProyecto != null && !oldProyectoOfDocenteProyectoCollectionNewDocenteProyecto.equals(proyecto)) {
                        oldProyectoOfDocenteProyectoCollectionNewDocenteProyecto.getDocenteProyectoCollection().remove(docenteProyectoCollectionNewDocenteProyecto);
                        oldProyectoOfDocenteProyectoCollectionNewDocenteProyecto = em.merge(oldProyectoOfDocenteProyectoCollectionNewDocenteProyecto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = proyecto.getIdProyecto();
                if (findProyecto(id) == null) {
                    throw new NonexistentEntityException("The proyecto with id " + id + " no longer exists.");
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
            Proyecto proyecto;
            try {
                proyecto = em.getReference(Proyecto.class, id);
                proyecto.getIdProyecto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proyecto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<DocenteProyecto> docenteProyectoCollectionOrphanCheck = proyecto.getDocenteProyectoCollection();
            for (DocenteProyecto docenteProyectoCollectionOrphanCheckDocenteProyecto : docenteProyectoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proyecto (" + proyecto + ") cannot be destroyed since the DocenteProyecto " + docenteProyectoCollectionOrphanCheckDocenteProyecto + " in its docenteProyectoCollection field has a non-nullable proyecto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Empresa idEmpresa = proyecto.getIdEmpresa();
            if (idEmpresa != null) {
                idEmpresa.getProyectoCollection().remove(proyecto);
                idEmpresa = em.merge(idEmpresa);
            }
            Collection<Estudiante> estudianteCollection = proyecto.getEstudianteCollection();
            for (Estudiante estudianteCollectionEstudiante : estudianteCollection) {
                estudianteCollectionEstudiante.setIdProyecto(null);
                estudianteCollectionEstudiante = em.merge(estudianteCollectionEstudiante);
            }
            em.remove(proyecto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proyecto> findProyectoEntities() {
        return findProyectoEntities(true, -1, -1);
    }

    public List<Proyecto> findProyectoEntities(int maxResults, int firstResult) {
        return findProyectoEntities(false, maxResults, firstResult);
    }

    private List<Proyecto> findProyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proyecto.class));
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

    public Proyecto findProyecto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proyecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proyecto> rt = cq.from(Proyecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
