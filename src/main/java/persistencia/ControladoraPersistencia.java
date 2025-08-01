/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import controlador.DepartamentoJpaController;
import controlador.DocenteJpaController;
import controlador.DocenteProyectoJpaController;
import controlador.DocumentoJpaController;
import controlador.EmpresaJpaController; 
import controlador.EstudianteJpaController;
import controlador.ExpedienteJpaController;
import controlador.PersonaJpaController;
import controlador.ProyectoJpaController;
import controlador.UsuarioJpaController;
import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import controlador.exceptions.PreexistingEntityException;
import entidad.Departamento;
import entidad.Docente;
import entidad.DocenteProyecto;
import entidad.DocenteProyectoPK;
import entidad.Documento;
import entidad.Empresa;
import entidad.Estudiante;
import entidad.Expediente;
import entidad.Persona;
import entidad.Proyecto;
import entidad.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author es982
 */
public class ControladoraPersistencia {
    PersonaJpaController controlP=new PersonaJpaController();
    UsuarioJpaController controlU=new UsuarioJpaController();
    EstudianteJpaController controlE=new EstudianteJpaController();
    ExpedienteJpaController controlExp=new ExpedienteJpaController();
    DocenteJpaController controlD=new DocenteJpaController();
    DepartamentoJpaController controlDp=new DepartamentoJpaController();
    EmpresaJpaController controlEmp=new EmpresaJpaController();
    ProyectoJpaController controlProy=new ProyectoJpaController();
    DocenteProyectoJpaController controlDocPr=new DocenteProyectoJpaController();
    DocumentoJpaController controlDoc=new DocumentoJpaController();

    // ----1.-PERSONA-----------

    //Metodo crearPersona
     public void crearPersona(Persona pers) {
         controlP.create(pers);
     }

    //Metodo eliminarPersona
         public void eliminarPersona(Integer id) {
         try {
             controlP.destroy(id);
         } catch (IllegalOrphanException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         } catch (NonexistentEntityException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
         }

         //Metodo editarPersona
         public void editarPersona(Persona pers) {
             try {
                 controlP.edit(pers);
             } catch (Exception ex) {
                 Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
             }
         }

         //Metodo de buscarPersonaPorID
         public Persona buscarPersonaPorId(Integer id) {
             return controlP.findPersona(id);
         }

         //Metodo listarPersonas
         public ArrayList<Persona> listarPersonas() {
             List<Persona> listaPersona=controlP.findPersonaEntities();
             ArrayList <Persona> arrayPersona= new ArrayList<>(listaPersona);
             return arrayPersona;
         }

           //-----------2.-USUARIO---------------
     //Metodo para crear usuario
     public void crearUsuario(Usuario user) {
         controlU.create(user);
     }

     //Metodo para eliminar usuario
     public void eliminarUsuario(Integer id) {
         try {
             controlU.destroy(id);
         } catch (NonexistentEntityException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
     }

     //Metodo para editar usuario
     public void editarUsuario(Usuario user) {
         try {
             controlU.edit(user);
         } catch (NonexistentEntityException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         } catch (Exception ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
     }

     //metodo buscar usuario por id
     public Usuario buscarUsuarioPorId(Integer id) {
         return controlU.findUsuario(id);
     }

     //Metodo listarUsuario
     public ArrayList<Usuario> listarUsuario() {
         List<Usuario> listaUser=controlU.findUsuarioEntities();
         ArrayList<Usuario> arrayUser=new ArrayList<>(listaUser);
         return arrayUser;
     }

           //-----------3.-ESTUDIANTE---------------

     public void guardarEstudiante(Estudiante estu) {
         try {
             controlE.create(estu);
         } catch (PreexistingEntityException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         } catch (Exception ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
     }

     public void eliminarEstudiante(String id) {
         try {
             controlE.destroy(id);
         } catch (NonexistentEntityException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
     }

     public void editarEstudiante(Estudiante estu) {
         try {
             controlE.edit(estu);
         } catch (NonexistentEntityException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         } catch (Exception ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
     }

     public Estudiante buscarPorNumeroControl(String nControl) {
         return controlE.findEstudiante(nControl);
     }

     public ArrayList<Estudiante> listarEstudiantes() {
         List<Estudiante> listaEstudiante=controlE.findEstudianteEntities();
         ArrayList<Estudiante> arrayEstudiantes=new ArrayList<>(listaEstudiante);
         return arrayEstudiantes;
     }
     // --------4.-DOCENTE----------------

     //
     public void guardarDocente(Docente doce) {
         try {
             controlD.create(doce);
         } catch (PreexistingEntityException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         } catch (Exception ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
     }

     public void eliminarDocente(String nTarjeta) {
         try {
             controlD.destroy(nTarjeta);
         } catch (NonexistentEntityException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IllegalOrphanException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
     }

     public void editarDocente(Docente doce) {
         try {
             controlD.edit(doce);
         } catch (NonexistentEntityException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         } catch (Exception ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
     }

     public Docente buscarPorNumeroTarjeta(String nTarjeta) {
         return controlD.findDocente(nTarjeta);
     }

     public ArrayList<Docente> listarDocentes() {
         List<Docente> listaDocente=controlD.findDocenteEntities();
         ArrayList<Docente> arrayDocente=new ArrayList<>(listaDocente);
         return arrayDocente;
     }

     // ------5.- DEPARTAMENTO--------------

     public void guardarDepartamento(Departamento depart){
         controlDp.create(depart);
     }

     public void eliminarDepartamento(Integer id){
         try {
             controlDp.destroy(id);
         } catch (IllegalOrphanException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         } catch (NonexistentEntityException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
     }

     public void editarDepartamento(Departamento depart){
         try {
             controlDp.edit(depart);
         } catch (NonexistentEntityException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         } catch (Exception ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
     }

     public Departamento buscarDepartamentoPorID(Integer id){
        return controlDp.findDepartamento(id);
     }

     public ArrayList<Departamento> listarDepartamentos(){
         List<Departamento> listaDepartamento=controlDp.findDepartamentoEntities();
         ArrayList<Departamento> arrayDepartamento=new ArrayList<>(listaDepartamento);
         return arrayDepartamento;
     }

     // -------6.- PROYECTO--------

     public void guardarProyecto(Proyecto proy){
         controlProy.create(proy);
     }

     public void eliminarProyecto(Integer id){
         try {
             controlProy.destroy(id);
         } catch (IllegalOrphanException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         } catch (NonexistentEntityException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
     }

     public void editarProyecto(Proyecto proy){
         try {
             controlProy.edit(proy);
         } catch (NonexistentEntityException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         } catch (Exception ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
     }

     public Proyecto buscarProyectoPorId(Integer id){
        return controlProy.findProyecto(id);
     }

     public ArrayList<Proyecto>listarProyectos(){
         List<Proyecto> listaProyecto=controlProy.findProyectoEntities();
         ArrayList<Proyecto> arrayProyecto=new ArrayList<>(listaProyecto);
         return arrayProyecto;
     }

     // ------7.-EMPRESA------------------

     public void guardarEmpresa(Empresa emp){
         controlEmp.create(emp);
     }

     public void eliminarEmpresa(Integer id){
         try {
             controlEmp.destroy(id);
         } catch (IllegalOrphanException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         } catch (NonexistentEntityException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
     }

     public void editarEmpresa(Empresa emp){
         try {
             controlEmp.edit(emp);
         } catch (NonexistentEntityException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         } catch (Exception ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
     }

     public Empresa buscarEmpresaPorId(Integer id){
        return controlEmp.findEmpresa(id);
     }

     public ArrayList<Empresa> listarEmpresas(){
         List<Empresa> listaEmpresa=controlEmp.findEmpresaEntities();
         ArrayList<Empresa> arrayEmpresa=new ArrayList<>(listaEmpresa);
         return arrayEmpresa;
     }

     // NUEVO MÉTODO PARA BUSCAR EMPRESA POR NOMBRE
     public Empresa buscarEmpresaPorNombre(String nombre) {
         return controlEmp.findEmpresaByNombre(nombre);
     }
     
     
     // ProyectoDocente
     public void guardarProyectoDocente(DocenteProyecto docenteProyecto){
        try {
            controlDocPr.create(docenteProyecto);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     
     public void editarProyectoDocente(DocenteProyecto docenteP){
        try {
            controlDocPr.edit(docenteP);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     
     public void eliminarProyectoDocente(DocenteProyectoPK docentePK){
        try {
            controlDocPr.destroy(docentePK);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     
     public DocenteProyecto buscarDocenteProyectoPorPK(DocenteProyectoPK idPK){
         return controlDocPr.findDocenteProyecto(idPK);
     }
     
     public ArrayList <DocenteProyecto> listarDocenteProyectos(){
         List<DocenteProyecto> listaDocenteProyectos=controlDocPr.findDocenteProyectoEntities();
         ArrayList<DocenteProyecto> arrayProyectoDoc=new ArrayList<>(listaDocenteProyectos);
         return arrayProyectoDoc;
     }

    public void guardarExpediente(Expediente exp) {
        controlExp.create(exp);
    }

    public void editarExpediente(Expediente exp) {
        try {
            controlExp.edit(exp);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarExpediente(Integer idE) {
        try {
            controlExp.destroy(idE);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Expediente buscarExpedientePorId(Integer idE) {
        return controlExp.findExpediente(idE);
    }

    public ArrayList<Expediente> listarExpediente() {
       List<Expediente> listaExpedientes=controlExp.findExpedienteEntities();
         ArrayList<Expediente> arrayExpediente=new ArrayList<>(listaExpedientes);
         return arrayExpediente;
    }
    
    public void guardarDocumento(Documento exp) {
        controlDoc.create(exp);
    }

    public void editarDocumento(Documento exp) {
        try {
            controlDoc.edit(exp);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarDocumento(Integer idE) {
        try {
            controlDoc.destroy(idE);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Documento buscarDocumentoPorId(Integer idE) {
        return controlDoc.findDocumento(idE);
    }

    public ArrayList<Documento> listarDocumento() {
       List<Documento> listaDocumentos=controlDoc.findDocumentoEntities();
         ArrayList<Documento> arrayDocumento=new ArrayList<>(listaDocumentos);
         return arrayDocumento;
    }
    
    public Estudiante buscarEstudiantePorExpediente(Expediente exp){
        return controlE.findEstudianteByExpediente(exp);
    }
    
}