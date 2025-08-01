/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import entidad.Departamento;
import entidad.Docente;
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
public class ControladoraDocente {

    ControladoraPersistencia controlPersis = new ControladoraPersistencia();
    ControladoraPersona controlP = new ControladoraPersona();
    ControladoraDepartamento controlDp = new ControladoraDepartamento();
    String depa = "";

    public void guardarDocente(Docente doce) {
        controlPersis.guardarDocente(doce);
    }

    public void eliminarDocente(String nTarjeta) {
        controlPersis.eliminarDocente(nTarjeta);
    }

    public void editarDocente(Docente doce) {
        controlPersis.editarDocente(doce);
    }

    public Docente buscarPorNumeroTarjeta(String nTarjeta) {
        return controlPersis.buscarPorNumeroTarjeta(nTarjeta);
    }

    public ArrayList<Docente> listarDocentes() {
        return controlPersis.listarDocentes();
    }
    
    //este metodo se manda a llamar en la clase CargaCSV y se le pasa el arraylist creado ahi
    public void guardarDocentesCSV(ArrayList<Persona> arrayPersona, ArrayList<Docente> arrayDocente) {
        if (arrayPersona.size() != arrayDocente.size()) {
            JOptionPane.showMessageDialog(null, "Error: El número de personas y docentes no coincide.");
            return;
        }

        int registrosDuplicados = 0;
        ControladoraPersona controlP = new ControladoraPersona();

        for (int i = 0; i < arrayPersona.size(); i++) {
            Persona persona = arrayPersona.get(i);
            Docente docente = arrayDocente.get(i);

            if (persona == null || docente == null) {
                registrosDuplicados++;
                continue;
            }

            // Solo si la persona NO existe, se registra persona y docente
            if (!controlP.existePersona(persona)) {
                controlP.crearPersona(persona);
                String numTarjeta = docente.getNoTarjeta();
                
                //Parametro del nombre del departamento que se envia desde la clase CargaCSV
                depa=CargarCSV.departamento;
                 // Busca el departamento en la BD y lo asigna en depa
                Departamento dep = controlDp.buscarDepartamentoPorNombre(depa);
                if (dep == null) {
                    //Si no existe lo crea y lo guarda en la base de datos para generar la PK
                    dep = new Departamento();
                    dep.setNombreDepartamento(depa);
                    controlDp.guardarDepartamento(dep); 
                }

                Docente doc = new Docente();
                doc.setNoTarjeta(numTarjeta);
                doc.setIdPersona(persona);
                doc.setIdDepartamento(dep);
                guardarDocente(doc);

            } else {
                registrosDuplicados++;
            }
        }

        if (registrosDuplicados == 0) {
            JOptionPane.showMessageDialog(null, "Registros guardados exitosamente");
        } else {
            JOptionPane.showMessageDialog(null, "Algunos datos ya estaban registrados y fueron omitidos (" + registrosDuplicados + ")");
        }
    }

    public boolean existeDocente(String numTarjeta) {
        ArrayList<Docente> lista = listarDocentes(); // Asume que ya tienes este método

        for (Docente d : lista) {
            if (d.getNoTarjeta().equals(numTarjeta)) {
                return true;
            }
        }
        return false;
    }

    public void guardarDocenteFormulario(String nombre, String apellidoP, String apellidoM, String tarjeta, String correo, String departamento) {
        // Validar campos vacíos
        if (nombre == null || apellidoP == null || apellidoM == null || tarjeta == null || correo == null
                || nombre.trim().isEmpty() || apellidoP.trim().isEmpty() || apellidoM.trim().isEmpty()
                || tarjeta.trim().isEmpty() || correo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
            return;
        }

        // Validaciones de longitud y caracteres repetidos
        if (apellidoP.length() <= 3 || apellidoM.length() <= 3) {
            JOptionPane.showMessageDialog(null, "Los apellidos deben tener más de 3 caracteres.");
            return;
        }

        if (tieneTresCaracteresRepetidos(nombre) || tieneTresCaracteresRepetidos(apellidoP) || tieneTresCaracteresRepetidos(apellidoM)) {
            JOptionPane.showMessageDialog(null, "No debe haber 3 caracteres repetidos consecutivos.");
            return;
        }

        if (!correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(null, "Correo electrónico no válido.");
            return;
        }

        // Crear persona
        Persona persona = new Persona();
        persona.setNombre(nombre.trim());
        persona.setApellidoP(apellidoP.trim());
        persona.setApellidoM(apellidoM.trim());
        persona.setCorreo(correo.trim());

        controlP.crearPersona(persona); // Guardar en base de datos

        // Buscar o crear departamento
        Departamento dep = controlDp.buscarDepartamentoPorNombre(departamento.trim());
        if (dep == null) {
            dep = new Departamento();
            dep.setNombreDepartamento(departamento.trim());
            controlDp.guardarDepartamento(dep);
        }

        // Crear docente
        Docente docente = new Docente();
        docente.setNoTarjeta(tarjeta.trim());
        docente.setIdPersona(persona);
        docente.setIdDepartamento(dep);

        guardarDocente(docente);

        // Mensaje final
        JOptionPane.showMessageDialog(null, "Docente " + nombre + " " + apellidoP + " registrado correctamente.");
    }

    public void actualizarDocenteFormulario(String tarjeta, String nombre, String apP, String apM, String correo) {
    Docente docente = buscarPorNumeroTarjeta(tarjeta);
    if (docente == null || docente.getIdPersona() == null) {
        JOptionPane.showMessageDialog(null, "Docente no encontrado.");
        return;
    }

    if (nombre.trim().isEmpty() || apP.trim().isEmpty() || apM.trim().isEmpty() || correo.trim().isEmpty()) {
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
        Persona persona = docente.getIdPersona();
        persona.setNombre(nombre.trim());
        persona.setApellidoP(apP.trim());
        persona.setApellidoM(apM.trim());
        persona.setCorreo(correo.trim());

        controlP.editarPersona(persona);
        JOptionPane.showMessageDialog(null, "Docente actualizado correctamente.");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al actualizar el docente: " + e.getMessage());
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

    public boolean actualizarDesdeTabla(DefaultTableModel modelo, List<Persona> personas, List<Docente> docentes) {
        personas.clear();
        docentes.clear();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            String nombre = (String) modelo.getValueAt(i, 0);
            String apPaterno = (String) modelo.getValueAt(i, 1);
            String apMaterno = (String) modelo.getValueAt(i, 2);
            String tarjeta = (String) modelo.getValueAt(i, 3);
            if (!ValidadorCampos.validarDocente(nombre, apPaterno, apMaterno, tarjeta)) {
                return false;
            }
            Persona p = new Persona();
            p.setNombre(nombre);
            p.setApellidoP(apPaterno);
            p.setApellidoM(apMaterno);
            Docente d = new Docente(tarjeta);
            d.setIdPersona(p);
            personas.add(p);
            docentes.add(d);
        }
        return true;
    }
}
