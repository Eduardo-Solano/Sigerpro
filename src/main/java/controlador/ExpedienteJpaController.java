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
import java.util.ArrayList;
import java.util.Collection;
import entidad.Documento;
import entidad.Expediente;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author es982
 */
public class ExpedienteJpaController implements Serializable {

    public ExpedienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public ExpedienteJpaController(){
        emf=Persistence.createEntityManagerFactory("SigerproMVC");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Expediente expediente) {
        if (expediente.getEstudianteCollection() == null) {
            expediente.setEstudianteCollection(new ArrayList<Estudiante>());
        }
        if (expediente.getDocumentoCollection() == null) {
            expediente.setDocumentoCollection(new ArrayList<Documento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Estudiante> attachedEstudianteCollection = new ArrayList<Estudiante>();
            for (Estudiante estudianteCollectionEstudianteToAttach : expediente.getEstudianteCollection()) {
                estudianteCollectionEstudianteToAttach = em.getReference(estudianteCollectionEstudianteToAttach.getClass(), estudianteCollectionEstudianteToAttach.getNumControl());
                attachedEstudianteCollection.add(estudianteCollectionEstudianteToAttach);
            }
            expediente.setEstudianteCollection(attachedEstudianteCollection);
            Collection<Documento> attachedDocumentoCollection = new ArrayList<Documento>();
            for (Documento documentoCollectionDocumentoToAttach : expediente.getDocumentoCollection()) {
                documentoCollectionDocumentoToAttach = em.getReference(documentoCollectionDocumentoToAttach.getClass(), documentoCollectionDocumentoToAttach.getIdDocumento());
                attachedDocumentoCollection.add(documentoCollectionDocumentoToAttach);
            }
            expediente.setDocumentoCollection(attachedDocumentoCollection);
            em.persist(expediente);
            for (Estudiante estudianteCollectionEstudiante : expediente.getEstudianteCollection()) {
                Expediente oldIdExpedienteOfEstudianteCollectionEstudiante = estudianteCollectionEstudiante.getIdExpediente();
                estudianteCollectionEstudiante.setIdExpediente(expediente);
                estudianteCollectionEstudiante = em.merge(estudianteCollectionEstudiante);
                if (oldIdExpedienteOfEstudianteCollectionEstudiante != null) {
                    oldIdExpedienteOfEstudianteCollectionEstudiante.getEstudianteCollection().remove(estudianteCollectionEstudiante);
                    oldIdExpedienteOfEstudianteCollectionEstudiante = em.merge(oldIdExpedienteOfEstudianteCollectionEstudiante);
                }
            }
            for (Documento documentoCollectionDocumento : expediente.getDocumentoCollection()) {
                Expediente oldIdExpedienteOfDocumentoCollectionDocumento = documentoCollectionDocumento.getIdExpediente();
                documentoCollectionDocumento.setIdExpediente(expediente);
                documentoCollectionDocumento = em.merge(documentoCollectionDocumento);
                if (oldIdExpedienteOfDocumentoCollectionDocumento != null) {
                    oldIdExpedienteOfDocumentoCollectionDocumento.getDocumentoCollection().remove(documentoCollectionDocumento);
                    oldIdExpedienteOfDocumentoCollectionDocumento = em.merge(oldIdExpedienteOfDocumentoCollectionDocumento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Expediente expediente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Expediente persistentExpediente = em.find(Expediente.class, expediente.getIdExpediente());
            Collection<Estudiante> estudianteCollectionOld = persistentExpediente.getEstudianteCollection();
            Collection<Estudiante> estudianteCollectionNew = expediente.getEstudianteCollection();
            Collection<Documento> documentoCollectionOld = persistentExpediente.getDocumentoCollection();
            Collection<Documento> documentoCollectionNew = expediente.getDocumentoCollection();
            List<String> illegalOrphanMessages = null;
            for (Estudiante estudianteCollectionOldEstudiante : estudianteCollectionOld) {
                if (!estudianteCollectionNew.contains(estudianteCollectionOldEstudiante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Estudiante " + estudianteCollectionOldEstudiante + " since its idExpediente field is not nullable.");
                }
            }
            for (Documento documentoCollectionOldDocumento : documentoCollectionOld) {
                if (!documentoCollectionNew.contains(documentoCollectionOldDocumento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Documento " + documentoCollectionOldDocumento + " since its idExpediente field is not nullable.");
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
            expediente.setEstudianteCollection(estudianteCollectionNew);
            Collection<Documento> attachedDocumentoCollectionNew = new ArrayList<Documento>();
            for (Documento documentoCollectionNewDocumentoToAttach : documentoCollectionNew) {
                documentoCollectionNewDocumentoToAttach = em.getReference(documentoCollectionNewDocumentoToAttach.getClass(), documentoCollectionNewDocumentoToAttach.getIdDocumento());
                attachedDocumentoCollectionNew.add(documentoCollectionNewDocumentoToAttach);
            }
            documentoCollectionNew = attachedDocumentoCollectionNew;
            expediente.setDocumentoCollection(documentoCollectionNew);
            expediente = em.merge(expediente);
            for (Estudiante estudianteCollectionNewEstudiante : estudianteCollectionNew) {
                if (!estudianteCollectionOld.contains(estudianteCollectionNewEstudiante)) {
                    Expediente oldIdExpedienteOfEstudianteCollectionNewEstudiante = estudianteCollectionNewEstudiante.getIdExpediente();
                    estudianteCollectionNewEstudiante.setIdExpediente(expediente);
                    estudianteCollectionNewEstudiante = em.merge(estudianteCollectionNewEstudiante);
                    if (oldIdExpedienteOfEstudianteCollectionNewEstudiante != null && !oldIdExpedienteOfEstudianteCollectionNewEstudiante.equals(expediente)) {
                        oldIdExpedienteOfEstudianteCollectionNewEstudiante.getEstudianteCollection().remove(estudianteCollectionNewEstudiante);
                        oldIdExpedienteOfEstudianteCollectionNewEstudiante = em.merge(oldIdExpedienteOfEstudianteCollectionNewEstudiante);
                    }
                }
            }
            for (Documento documentoCollectionNewDocumento : documentoCollectionNew) {
                if (!documentoCollectionOld.contains(documentoCollectionNewDocumento)) {
                    Expediente oldIdExpedienteOfDocumentoCollectionNewDocumento = documentoCollectionNewDocumento.getIdExpediente();
                    documentoCollectionNewDocumento.setIdExpediente(expediente);
                    documentoCollectionNewDocumento = em.merge(documentoCollectionNewDocumento);
                    if (oldIdExpedienteOfDocumentoCollectionNewDocumento != null && !oldIdExpedienteOfDocumentoCollectionNewDocumento.equals(expediente)) {
                        oldIdExpedienteOfDocumentoCollectionNewDocumento.getDocumentoCollection().remove(documentoCollectionNewDocumento);
                        oldIdExpedienteOfDocumentoCollectionNewDocumento = em.merge(oldIdExpedienteOfDocumentoCollectionNewDocumento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = expediente.getIdExpediente();
                if (findExpediente(id) == null) {
                    throw new NonexistentEntityException("The expediente with id " + id + " no longer exists.");
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
            Expediente expediente;
            try {
                expediente = em.getReference(Expediente.class, id);
                expediente.getIdExpediente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The expediente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Estudiante> estudianteCollectionOrphanCheck = expediente.getEstudianteCollection();
            for (Estudiante estudianteCollectionOrphanCheckEstudiante : estudianteCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Expediente (" + expediente + ") cannot be destroyed since the Estudiante " + estudianteCollectionOrphanCheckEstudiante + " in its estudianteCollection field has a non-nullable idExpediente field.");
            }
            Collection<Documento> documentoCollectionOrphanCheck = expediente.getDocumentoCollection();
            for (Documento documentoCollectionOrphanCheckDocumento : documentoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Expediente (" + expediente + ") cannot be destroyed since the Documento " + documentoCollectionOrphanCheckDocumento + " in its documentoCollection field has a non-nullable idExpediente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(expediente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Expediente> findExpedienteEntities() {
        return findExpedienteEntities(true, -1, -1);
    }

    public List<Expediente> findExpedienteEntities(int maxResults, int firstResult) {
        return findExpedienteEntities(false, maxResults, firstResult);
    }

    private List<Expediente> findExpedienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Expediente.class));
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

    public Expediente findExpediente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Expediente.class, id);
        } finally {
            em.close();
        }
    }

    public int getExpedienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Expediente> rt = cq.from(Expediente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
