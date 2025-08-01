/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import entidad.Documento;
import entidad.Estudiante;
import entidad.Expediente;
import entidad.Persona;
import entidad.Proyecto;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import persistencia.ControladoraPersistencia;

/**
 *
 * @author es982
 */
public class ControladoraDocumento {

    ControladoraPersistencia controlP = new ControladoraPersistencia();
    ControladoraExpediente controlExp = new ControladoraExpediente();
    ControladoraEstudiante controlEst = new ControladoraEstudiante();
    ControladoraProyecto controlProy = new ControladoraProyecto();

    public void guardarDocumento(Documento exp) {
        controlP.guardarDocumento(exp);
    }

    public void editarDocumento(Documento exp) {
        controlP.editarDocumento(exp);
    }

    public void eliminarDocumento(Integer idE) {
        controlP.eliminarDocumento(idE);
    }

    public Documento buscarDocumentoPorId(Integer idE) {
        return controlP.buscarDocumentoPorId(idE);
    }

    public ArrayList<Documento> listaDocumentos() {
        return controlP.listarDocumento();
    }
    
    public boolean existeDocumento(String doc){
         ArrayList<Documento> lista = listaDocumentos();
        for (Documento d : lista) {
            if (d.getNombreDocumento() != null && d.getNombreDocumento().equals(doc)) {
                return true;
            }
        }
        return false;
    }

    public void asignarDocumentoExpediente(DefaultTableModel modelo, int idDocumento, int id) {
        Documento doc = buscarDocumentoPorId(idDocumento);        
        Expediente exp = controlExp.buscarExpedientePorId(id);
        Estudiante est = controlEst.buscarEstudiantePorExpediente(exp);       
        Proyecto proy = est.getIdProyecto();
         if (doc == null || exp == null || est == null || proy == null) {
            JOptionPane.showMessageDialog(null, "Datos no encontrados, revise los IDs.");
            return;
        }
        String mensaje = "Nombre_Expediente: " + exp.getNombreExpediente() + "\n"
                + "Fecha de Creacion: " + exp.getFechaCreacion();

        // Confirmación
        int confirmacion = JOptionPane.showConfirmDialog(
                null,
                mensaje + "\n\n¿Desea asignar este documento al expediente?",
                "Confirmar asignación",
                JOptionPane.YES_NO_OPTION
        );
        // Si se da clic en "Sí"
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (!expedienteAsignado(exp)) {
                doc.setIdExpediente(exp);
                editarDocumento(doc);
                //Modificar fecha final
                Date fechaActual = new Date(); // Fecha actual
                Calendar cal = Calendar.getInstance();
                cal.setTime(fechaActual);
                cal.add(Calendar.MONTH, 2); // Sumar 2 meses
                Date fechaNueva = cal.getTime();
                proy.setFechaFin(fechaNueva);
                controlProy.editarProyecto(proy);
            } else {
                JOptionPane.showMessageDialog(null, "El Documento ya fue asignado a un proyecto");
            }
        } else {
            // Si da clic en "No" o "Cancelar"
            JOptionPane.showMessageDialog(null, "Asignación cancelada", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public boolean expedienteAsignado(Expediente expediente) {
        ArrayList<Documento> lista = listaDocumentos();
        for (Documento d : lista) {
            if (d.getIdExpediente() != null && d.getIdExpediente().getIdExpediente().equals(expediente.getIdExpediente())) {
                return true;
            }
        }
        return false;
    }

}
