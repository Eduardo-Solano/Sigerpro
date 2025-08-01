/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import entidad.Docente;
import entidad.Estudiante;
import entidad.Persona;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author es982
 */
public class CargarCSV {

    public static String departamento = "";

    public CargarCSV() {

    }

    public void subirCSVDocentes(ArrayList<Persona> arrayPersona, ArrayList<Docente> arrayDocente, DefaultTableModel modeloTabla) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos CSV", "csv"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setDialogTitle("Seleccionar archivo CSV");
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        int resultado = fileChooser.showOpenDialog(null);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            if (!archivo.getName().toLowerCase().endsWith(".csv") || !archivo.isFile()) {
                JOptionPane.showMessageDialog(null, "Solo se permiten archivos con extensión .csv", "Archivo no válido", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int registrosExitosos = 0;
            List<String> errores = new ArrayList<>();
            Set<String> tarjetasUnicas = new HashSet<>();

            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                int lineaNum = 0;

                while ((linea = br.readLine()) != null) {
                    lineaNum++;
                    if (linea.trim().isEmpty()) {
                        continue;
                    }

                    String[] datos = linea.split(",");
                    if (datos.length != 6) {
                        errores.add("Línea " + lineaNum + ": Se esperaban 6 columnas (tarjeta, nombre, apP, apM, correo, departamento)");
                        continue;
                    }

                    String numTarjeta = datos[0].trim();
                    String nombre = datos[1].trim();
                    String apPaterno = datos[2].trim();
                    String apMaterno = datos[3].trim();
                    String correo = datos[4].trim();
                    String depart = datos[5].trim();

                    obtenerDepartamento(depart);

                    if (numTarjeta.isEmpty() || nombre.isEmpty() || apPaterno.isEmpty() || apMaterno.isEmpty() || correo.isEmpty()) {
                        errores.add("Línea " + lineaNum + ": Campos vacíos.");
                        continue;
                    }

                    if (tarjetasUnicas.contains(numTarjeta)) {
                        errores.add("Línea " + lineaNum + ": Número de tarjeta duplicado.");
                        continue;
                    }

                    tarjetasUnicas.add(numTarjeta);

                    Persona auxPersona = new Persona();
                    auxPersona.setNombre(nombre);
                    auxPersona.setApellidoP(apPaterno);
                    auxPersona.setApellidoM(apMaterno);
                    auxPersona.setCorreo(correo);
                    arrayPersona.add(auxPersona);

                    Docente auxDocente = new Docente();
                    auxDocente.setNoTarjeta(numTarjeta);
                    arrayDocente.add(auxDocente);

                    modeloTabla.addRow(new Object[]{
                        numTarjeta, nombre, apPaterno, apMaterno, correo, depart
                    });

                    registrosExitosos++;
                }

                JOptionPane.showMessageDialog(null, "Docentes cargados correctamente: " + registrosExitosos);

                if (!errores.isEmpty()) {
                    String mensajeError = String.join("\n", errores);
                    JOptionPane.showMessageDialog(null, "Errores encontrados:\n" + mensajeError, "Errores en el archivo", JOptionPane.ERROR_MESSAGE);
                }

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + ex.getMessage());
            }
        }
    }

    public void subirCSVEstudiante(ArrayList<Persona> arrayPersona, ArrayList<Estudiante> arrayEstudiante, DefaultTableModel modeloTablaResidentes) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos CSV", "csv"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setDialogTitle("Seleccionar archivo CSV");
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        int resultado = fileChooser.showOpenDialog(null);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            if (!archivo.getName().toLowerCase().endsWith(".csv") || !archivo.isFile()) {
                JOptionPane.showMessageDialog(null, "Solo se permiten archivos con extensión .csv", "Archivo no válido", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int registrosExitosos = 0;
            List<String> errores = new ArrayList<>();
            Set<String> controlesUnicos = new HashSet<>();

            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                int lineaNum = 0;

                while ((linea = br.readLine()) != null) {
                    lineaNum++;
                    if (linea.trim().isEmpty()) {
                        continue;
                    }

                    String[] datos = linea.split(",");
                    if (datos.length != 7) {
                        errores.add("Línea " + lineaNum + ": Se esperaban 7 columnas (control, nombre, apP, apM, correo, semestre, departamento)");
                        continue;
                    }

                    String numControl = datos[0].trim();
                    String nombre = datos[1].trim();
                    String apPaterno = datos[2].trim();
                    String apMaterno = datos[3].trim();
                    String correo = datos[4].trim();
                    String semestre = datos[5].trim();
                    String carrera = datos[6].trim();

                    obtenerDepartamento(carrera);

                    if (numControl.isEmpty() || nombre.isEmpty() || apPaterno.isEmpty() || apMaterno.isEmpty() || correo.isEmpty() || semestre.isEmpty() || carrera.isEmpty()) {
                        errores.add("Línea " + lineaNum + ": Campos vacíos.");
                        continue;
                    }

                    if (controlesUnicos.contains(numControl)) {
                        errores.add("Línea " + lineaNum + ": Número de control duplicado.");
                        continue;
                    }

                    controlesUnicos.add(numControl);

                    Persona auxPersona = new Persona();
                    auxPersona.setNombre(nombre);
                    auxPersona.setApellidoP(apPaterno);
                    auxPersona.setApellidoM(apMaterno);
                    auxPersona.setCorreo(correo);
                    arrayPersona.add(auxPersona);

                    int auxSemestre = Integer.parseInt(semestre);
                    Estudiante auxEstudiante = new Estudiante();
                    auxEstudiante.setNumControl(numControl);
                    auxEstudiante.setSemestre(auxSemestre);
                    arrayEstudiante.add(auxEstudiante);

                    modeloTablaResidentes.addRow(new Object[]{
                        numControl, nombre, apPaterno, apMaterno, correo, semestre, carrera
                    });

                    registrosExitosos++;
                }

                JOptionPane.showMessageDialog(null, "Estudiantes cargados correctamente: " + registrosExitosos);

                if (!errores.isEmpty()) {
                    String mensajeError = String.join("\n", errores);
                    JOptionPane.showMessageDialog(null, "Errores encontrados:\n" + mensajeError, "Errores en el archivo", JOptionPane.ERROR_MESSAGE);
                }

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + ex.getMessage());
            }
        }
    }

    public void obtenerDepartamento(String dp) {
        this.departamento = dp;
    }

}
