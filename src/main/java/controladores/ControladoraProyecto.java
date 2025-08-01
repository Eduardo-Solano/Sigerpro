/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import entidad.Empresa;
import entidad.Estudiante;
import entidad.Persona;
import entidad.Proyecto;
import java.util.ArrayList;
import persistencia.ControladoraPersistencia;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author es982
 */
public class ControladoraProyecto {

    ControladoraPersistencia controlPersis = new ControladoraPersistencia();
    ControladoraEmpresa controlEmp = new ControladoraEmpresa();
    ControladoraEstudiante controlE = new ControladoraEstudiante();
    ControladoraPersona controlP = new ControladoraPersona();

    public void guardarProyecto(Proyecto proy) {
        controlPersis.guardarProyecto(proy);
    }

    public void editarProyecto(Proyecto py) {
        controlPersis.editarProyecto(py);
    }

    public void eliminiarProyecto(Integer id) {
        controlPersis.eliminarProyecto(id);
    }

    public Proyecto buscarProyectoPorId(Integer id) {
        return controlPersis.buscarProyectoPorId(id);
    }

    public ArrayList<Proyecto> listarProyectos() {
        return controlPersis.listarProyectos();
    }

    public List<Empresa> obtenerTodasLasEmpresas() {
        return controlEmp.listarEmpresas();
    }

    public void asignarProyectoAEstudiante(DefaultTableModel modelo, String numControl, int id) {
        if (numControl.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingresa el numero de control del estudiante");
            return;
        }
        

        ValidadorCampos validador = new ValidadorCampos();
        boolean error = validador.validarNumero(numControl);

        if (!error) {
            JOptionPane.showMessageDialog(null, "Solo debe contener 8 Digitos Numericos", "Errores encontrados", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Estudiante est = controlE.buscarPorNumeroControl(numControl);
        if (est == null) {
            JOptionPane.showMessageDialog(null, "Estudiante no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Persona idP = est.getIdPersona();
        String mensaje = "Num_Control: " + est.getNumControl() + "\n"
                + "Nombre: " + idP.getNombre() + "\n"
                + "Apellido Paterno: " + idP.getApellidoP() + "\n"
                + "Apellido Materno: " + idP.getApellidoM();

        // Confirmación
        int confirmacion = JOptionPane.showConfirmDialog(
                null,
                mensaje + "\n\n¿Asignar este proyecto al estudiante?",
                "Confirmar asignación",
                JOptionPane.YES_NO_OPTION
        );

        // Si se da clic en "Sí"
        if (confirmacion == JOptionPane.YES_OPTION) {
            Proyecto proy = buscarProyectoPorId(id);
            if (!controlE.proyectoAsignado(numControl)) {
                if (verificarLimiteAlumno(id)) {
                    est.setIdProyecto(proy);
                    controlE.editarEstudiante(est);

                    JOptionPane.showMessageDialog(null, "Se registró el proyecto al estudiante:\n" + mensaje);

                    // Sincroniza la colección de estudiantes antes de editar el proyecto
                    //proy.setEstudianteCollection(controlE.listarEstudiantesPorProyecto(id));
                    proy.setEstudianteCollection(buscarProyectoPorId(id).getEstudianteCollection());
                    proy.setEstado("Asignado");
                    editarProyecto(proy);
                } else {
                    JOptionPane.showMessageDialog(null, "El número de alumnos sugeridos para este proyecto ya ha sido alcanzado");
                }
            } else {
                JOptionPane.showMessageDialog(null, "El Estudiante ya tiene un proyecto asignado");
            }
        } else {
            // Si da clic en "No" o "Cancelar"
            JOptionPane.showMessageDialog(null, "Asignación cancelada", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public boolean verificarLimiteAlumno(Integer idProyecto) {
        Proyecto proy = buscarProyectoPorId(idProyecto);
        ArrayList<Estudiante> listaEst = controlE.listarEstudiantes();
        int alum_sug = proy.getNumAlumnosSug();
        int contador = 0;

        for (Estudiante e : listaEst) {
            if (e.getIdProyecto() != null && e.getIdProyecto().equals(proy)) {
                contador++;
            }
        }

        return contador < alum_sug;
    }
    
    public boolean habilitarProrroga(String idProyecto){
        ArrayList<Proyecto> listaProyecto=listarProyectos();
        if(listaProyecto.isEmpty())
            return false;
        for(Proyecto p: listaProyecto){
            if(p.getNombreProyecto().equals(idProyecto) && p.getEstado().equals("Aprobado")){
                return true;
            }
        }
        return false;
    }
    
}
