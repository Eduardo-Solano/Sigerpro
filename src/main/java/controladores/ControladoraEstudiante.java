/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import entidad.Departamento;
import entidad.Estudiante;
import entidad.Expediente;
import entidad.Persona;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import persistencia.ControladoraPersistencia;

/**
 *
 * @author es982
 */
public class ControladoraEstudiante {

    ControladoraPersistencia controlPersis = new ControladoraPersistencia();
    ControladoraPersona controlP = new ControladoraPersona();
    ControladoraDepartamento controlDp = new ControladoraDepartamento();
    String depa = "";

    public void guardarEstudiante(Estudiante estu) {
        controlPersis.guardarEstudiante(estu);
    }

    public void eliminarEstudiante(String nControl) {
        controlPersis.eliminarEstudiante(nControl);
    }

    public void editarEstudiante(Estudiante estu) {
        controlPersis.editarEstudiante(estu);
    }

    public Estudiante buscarPorNumeroControl(String nControl) {
        return controlPersis.buscarPorNumeroControl(nControl);
    }

    public ArrayList<Estudiante> listarEstudiantes() {
        return controlPersis.listarEstudiantes();
    }

    public Estudiante buscarEstudiantePorExpediente(Expediente exp) {
        return controlPersis.buscarEstudiantePorExpediente(exp);
    }

    public void guardarEstudiantesCSV(ArrayList<Persona> arrayPersona, ArrayList<Estudiante> arrayEstudiante) {
    if (arrayPersona.size() != arrayEstudiante.size()) {
        JOptionPane.showMessageDialog(null, "Error: El número de personas y estudiantes no coincide.");
        return;
    }

    int registrosDuplicados = 0;
    int registrosCreados = 0;

    for (int i = 0; i < arrayPersona.size(); i++) {
        Persona persona = arrayPersona.get(i);
        Estudiante estudiante = arrayEstudiante.get(i);

        if (persona == null || estudiante == null) {
            registrosDuplicados++;
            continue;
        }

        if (!controlP.existePersona(persona)) {
            controlP.crearPersona(persona);
            String numControl = estudiante.getNumControl();

            // Obtener departamento desde el CSV
            depa = CargarCSV.departamento;
            Departamento dep = controlDp.buscarDepartamentoPorNombre(depa);
            if (dep == null) {
                dep = new Departamento();
                dep.setNombreDepartamento(depa);
                controlDp.guardarDepartamento(dep);
            }

            // Crear expediente
            Expediente expediente = new Expediente();
            expediente.setNombreExpediente(numControl);
            expediente.setFechaCreacion(new java.util.Date());
            controlPersis.guardarExpediente(expediente);

            // Asociar datos al estudiante
            Estudiante est2 = new Estudiante();
            est2.setNumControl(numControl);
            est2.setIdDepartamento(dep);
            est2.setSemestre(estudiante.getSemestre());
            est2.setIdPersona(persona);
            est2.setIdExpediente(expediente);

            guardarEstudiante(est2);
            registrosCreados++;
        } else {
            registrosDuplicados++;
        }
    }

    if (registrosCreados > 0) {
        JOptionPane.showMessageDialog(null, "Registros guardados exitosamente: " + registrosCreados);
    }

    if (registrosDuplicados > 0) {
        JOptionPane.showMessageDialog(null, "Datos omitidos por duplicado: " + registrosDuplicados);
    }
}

    public boolean existeEstudiante(String numControl) {
        ArrayList<Estudiante> lista = listarEstudiantes(); // Asegúrate de tener este método

        for (Estudiante e : lista) {
            if (e.getNumControl().equalsIgnoreCase(numControl)) {
                return true;
            }
        }
        return false;
    }

    public void guardarEstudianteFormulario(String txtNombre, String txtApellidoP, String txtApellidoM, String txtNumC, String semestre, String carrera, String correo) {
        if (txtNombre == null || txtApellidoP == null || txtNumC == null || semestre == null || correo == null) {
            JOptionPane.showMessageDialog(null, "Rellene todos los campos");
        } else {
            boolean v1 = tieneTresCaracteresRepetidos(txtNombre);
            boolean v2 = tieneTresCaracteresRepetidos(txtApellidoP);
            boolean v3 = tieneTresCaracteresRepetidos(txtApellidoM);
            if (txtApellidoP.length() <= 3 || txtApellidoM.length() <= 3) {
                JOptionPane.showMessageDialog(null, "Ingrese un apellido válido ");
            } else if (!v1 || !v2 || !v3) {
                JOptionPane.showMessageDialog(null, "No debe haber 3 caracteres repetidos en ningún campo");
            } else {
                int semestreI = Integer.parseInt(semestre);

                // Crear persona
                Persona pers = new Persona();
                pers.setNombre(txtNombre);
                pers.setApellidoP(txtApellidoP);
                pers.setApellidoM(txtApellidoM);
                pers.setCorreo(correo);
                controlP.crearPersona(pers);

                // Buscar o crear departamento
                Departamento dep = controlDp.buscarDepartamentoPorNombre(carrera);
                if (dep == null) {
                    dep = new Departamento();
                    dep.setNombreDepartamento(carrera);
                    controlDp.guardarDepartamento(dep);
                }

                // Crear y guardar estudiante
                Estudiante estudiante = new Estudiante();
                estudiante.setNumControl(txtNumC);
                estudiante.setIdDepartamento(dep);
                estudiante.setSemestre(semestreI);
                estudiante.setIdPersona(pers);
                guardarEstudiante(estudiante);

                JOptionPane.showMessageDialog(null, "Estudiante guardado exitosamente");
            }
        }
    }

    public void actualizarEstudianteFormulario(String numControl, String nombre, String apP, String apM, String correo, String semestre) {
    Estudiante est = buscarPorNumeroControl(numControl);
    if (est == null || est.getIdPersona() == null) {
        JOptionPane.showMessageDialog(null, "Estudiante no encontrado.");
        return;
    }

    if (nombre.trim().isEmpty() || apP.trim().isEmpty() || apM.trim().isEmpty() || correo.trim().isEmpty() || semestre.trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
        return;
    }

    if (apP.length() <= 3 || apM.length() <= 3) {
        JOptionPane.showMessageDialog(null, "Los apellidos deben tener más de 3 caracteres.");
        return;
    }

    if (tieneTresCaracteresRepetidos(nombre) || tieneTresCaracteresRepetidos(apP) || tieneTresCaracteresRepetidos(apM)) {
        JOptionPane.showMessageDialog(null, "No debe haber 3 caracteres repetidos consecutivos.");
        return;
    }

    if (!correo.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
        JOptionPane.showMessageDialog(null, "Correo electrónico no válido.");
        return;
    }

    try {
        Persona persona = est.getIdPersona();
        persona.setNombre(nombre);
        persona.setApellidoP(apP);
        persona.setApellidoM(apM);
        persona.setCorreo(correo);

        controlP.editarPersona(persona); // Actualiza la persona
        est.setSemestre(Integer.parseInt(semestre)); // Actualiza semestre
        editarEstudiante(est); // Actualiza estudiante

        JOptionPane.showMessageDialog(null, "Estudiante actualizado correctamente.");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al actualizar el estudiante: " + e.getMessage());
    }
}
    
    public static boolean tieneTresCaracteresRepetidos(String texto) {
        int contador = 1;
        for (int i = 1; i < texto.length(); i++) {
            if (texto.charAt(i) == texto.charAt(i - 1)) {
                contador++;
                if (contador >= 3) {
                    return true; // Hay tres o más caracteres repetidos seguidos
                }
            } else {
                contador = 1; // Reinicia el contador si cambia el carácter
            }
        }
        return false;
    }

    public boolean actualizarDesdeTabla(DefaultTableModel modelo, List<Persona> personas, List<Estudiante> estudiantes) {
        personas.clear();
        estudiantes.clear();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            String nombre = (String) modelo.getValueAt(i, 0);
            String apPaterno = (String) modelo.getValueAt(i, 1);
            String apMaterno = (String) modelo.getValueAt(i, 2);
            String numControl = (String) modelo.getValueAt(i, 3);
            String carrera = (String) modelo.getValueAt(i, 4);
            String semestre = String.valueOf(modelo.getValueAt(i, 5));

            if (!ValidadorCampos.validarEstudiante(nombre, apPaterno, apMaterno, numControl, carrera, semestre)) {
                JOptionPane.showMessageDialog(null, "Error en la fila " + (i + 1), "Validación fallida", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            Persona persona = new Persona();
            persona.setNombre(nombre);
            persona.setApellidoP(apPaterno);
            persona.setApellidoM(apMaterno);
            Departamento dep = new Departamento();
            dep.setNombreDepartamento(carrera);
            Estudiante estudiante = new Estudiante();
            estudiante.setNumControl(numControl);
            estudiante.setIdDepartamento(dep);
            estudiante.setSemestre(Integer.parseInt(semestre));
            estudiante.setIdPersona(persona);

            personas.add(persona);
            estudiantes.add(estudiante);
        }

        return true;
    }

    public void actualizarEstudianteCSV(ArrayList<Persona> arrayPersona, ArrayList<Estudiante> arrayEstudiante) {
    if (arrayPersona.size() != arrayEstudiante.size()) {
        JOptionPane.showMessageDialog(null, "Error: El número de personas y estudiantes no coincide.");
        return;
    }

    int registrosEditados = 0;
    int expedientesCreados = 0;

    for (int i = 0; i < arrayPersona.size(); i++) {
        Persona persona = arrayPersona.get(i);
        Estudiante estudiante = arrayEstudiante.get(i);

        if (persona == null || estudiante == null) {
            continue;
        }

        Estudiante estExistente = buscarPorNumeroControl(estudiante.getNumControl());

        if (estExistente != null && estExistente.getIdPersona() != null) {
            // Editar persona
            Persona pExistente = estExistente.getIdPersona();
            pExistente.setNombre(persona.getNombre());
            pExistente.setApellidoP(persona.getApellidoP());
            pExistente.setApellidoM(persona.getApellidoM());
            pExistente.setCorreo(persona.getCorreo());
            controlP.editarPersona(pExistente);

            // Verificar y crear expediente si no existe
            if (estExistente.getIdExpediente() == null) {
                Expediente nuevoExp = new Expediente();
                nuevoExp.setNombreExpediente(estudiante.getNumControl());
                nuevoExp.setFechaCreacion(new java.util.Date());
                controlPersis.guardarExpediente(nuevoExp);
                estExistente.setIdExpediente(nuevoExp);
                expedientesCreados++;
            }

            // Departamento
            Departamento dep = estudiante.getIdDepartamento();
            if (dep == null || dep.getNombreDepartamento() == null) {
                dep = controlDp.buscarDepartamentoPorNombre(CargarCSV.departamento);
                if (dep == null) {
                    dep = new Departamento();
                    dep.setNombreDepartamento(CargarCSV.departamento);
                    controlDp.guardarDepartamento(dep);
                }
            }

            // Actualizar campos
            estExistente.setSemestre(estudiante.getSemestre());
            estExistente.setIdDepartamento(dep);

            editarEstudiante(estExistente);
            registrosEditados++;
        }
    }

    if (registrosEditados == 0) {
        JOptionPane.showMessageDialog(null, "No se han editado registros.");
    } else {
        JOptionPane.showMessageDialog(null, "Registros editados: " + registrosEditados +
                (expedientesCreados > 0 ? " (Se crearon " + expedientesCreados + " expedientes)" : ""));
    }
}


    public boolean proyectoAsignado(String numControl) {
        ArrayList<Estudiante> lista = listarEstudiantes(); // Asegúrate de tener este método

        for (Estudiante e : lista) {
            if (e.getNumControl().equalsIgnoreCase(numControl) && e.getIdProyecto() != null) {
                return true;
            }
        }
        return false;
    }

}
