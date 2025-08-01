/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import controladores.ControladoraDocumento;
import controladores.ControladoraEstudiante;
import controladores.ControladoraPersona;
import controladores.ControladoraUsuario;
import controladores.ExportadorPDF;
import entidad.Documento;
import entidad.Estudiante;
import entidad.Expediente;
import entidad.Persona;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author jc389
 */
public class ControlExpedientes extends javax.swing.JFrame {

    DefaultTableModel esquema;
    String[] datos;
    ControladoraEstudiante cE = new ControladoraEstudiante();
    ControladoraPersona cP = new ControladoraPersona();
    ControladoraDocumento cD = new ControladoraDocumento();
    String rutas[] = new String[8];
    public String contrasena="";

    /**
     * Creates new form ControlExpedientes
     */
    public ControlExpedientes() {
        this.setUndecorated(true);
        initComponents();
        iniciar();
    }
   public void iniciar() {
    panelDatos.setVisible(false);
    iniciarTabla();
    adaptarImagenes();

    // Cargar la tabla en segundo plano para no trabar la UI
    new javax.swing.SwingWorker<Void, Void>() {
        @Override
        protected Void doInBackground() throws Exception {
            llenarTabla();
            return null;
        }
        @Override
        protected void done() {
            // Puedes poner un mensaje, spinner o simplemente nada
        }
    }.execute();
}


    public String guardarArchivoDesdeExplorador() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona un archivo para guardar");

        int resultado = fileChooser.showOpenDialog(null); // null = centrado

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();

            // Carpeta destino fija
            File carpetaDestino = new File("expedientes");
            if (!carpetaDestino.exists()) {
                carpetaDestino.mkdirs(); // Crear si no existe
            }

            // Archivo destino: misma carpeta + mismo nombre
            File archivoDestino = new File(carpetaDestino, archivoSeleccionado.getName());

            try {
                Files.copy(archivoSeleccionado.toPath(), archivoDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                JOptionPane.showMessageDialog(null, "✅ Archivo guardado:\n" + archivoDestino.getAbsolutePath());
                return archivoDestino.getAbsolutePath(); // <<< Retornar la ruta final
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "❌ Error al guardar archivo:\n" + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        return null; // Si se cancela o hay error
    }

    private void llenarTabla() {
        esquema.setRowCount(0); // Limpiar la tabla

        ControladoraEstudiante controlA = new ControladoraEstudiante();
        ControladoraPersona controlP = new ControladoraPersona();
        ArrayList<Estudiante> listaAlumnos = controlA.listarEstudiantes();

        if (!listaAlumnos.isEmpty()) {
            for (Estudiante e : listaAlumnos) {
                String numero = e.getNumControl();
                Persona p = e.getIdPersona();
                Persona persona = controlP.buscarPorId(p.getIdPersona());
                String nombre = persona.getNombre() + " " + persona.getApellidoP() + " " + persona.getApellidoM();

                // Obtener ID del expediente
                Expediente expe = e.getIdExpediente();
                int idExpediente = expe.getIdExpediente();

                // Obtener documentos asociados a ese expediente
                List<String[]> documentos = obtenerNombreYFechaPorExpediente(idExpediente);

                // Documentos esperados
                String[] esperados = {
                    "Solicitud de residencia",
                    "Carta compromiso",
                    "Carta de aceptación",
                    "Carta de terminación",
                    "Informe final",
                    "Acta de calificación",
                    "Primer reporte",
                    "Segundo reporte"
                };

                // Mapear nombres obtenidos
                Set<String> nombresObtenidos = new HashSet<>();
                for (String[] doc : documentos) {
                    String nombreDoc = doc[0];
                    if (nombreDoc != null && !nombreDoc.equalsIgnoreCase("No almacenado") && !nombreDoc.equalsIgnoreCase("No asignado")) {
                        nombresObtenidos.add(nombreDoc.trim());
                    }
                }

                // Determinar documentos faltantes
                List<String> faltantes = new ArrayList<>();
                for (String esperado : esperados) {
                    if (!nombresObtenidos.contains(esperado)) {
                        faltantes.add(esperado);
                    }
                }

                // Estado y resumen de faltantes
                String estado = faltantes.isEmpty() ? "Completo" : "Incompleto";
                String resumenFaltantes = faltantes.isEmpty() ? "Ninguno" : String.join(", ", faltantes);

                // Agregar fila
                esquema.addRow(new Object[]{numero, nombre, estado, resumenFaltantes});
            }
        } else {
            JOptionPane.showMessageDialog(null, "Registra un Residente primero");
        }
    }

    public List<String> obtenerNombresPorExpediente(int idExpediente) {
        List<String> nombresFiltrados = new ArrayList<>();

        ArrayList<Documento> documentos = cD.listaDocumentos();

        for (Documento doc : documentos) {
            if (doc.getIdExpediente().getIdExpediente() == idExpediente) {
                nombresFiltrados.add(doc.getNombreDocumento());
            }
        }

        return nombresFiltrados;
    }

    public List<String[]> obtenerNombreYFechaPorExpediente(int idExpediente) {
        List<String[]> resultado = new ArrayList<>();

        ArrayList<Documento> documentos = cD.listaDocumentos();

        for (Documento doc : documentos) {
            if (doc.getIdExpediente().getIdExpediente() == idExpediente) {
                String nombre = doc.getNombreDocumento();
                String fecha = (doc.getFechaDeCarga() != null) ? formatearFecha(doc.getFechaDeCarga()) : "Sin asignar";
                String idDocumento = String.valueOf(doc.getIdDocumento()); // 🔹 ID agregado
                resultado.add(new String[]{nombre, fecha, idDocumento});
            }
        }

        return resultado;
    }

    public void iniciarTabla() {
        String[] celdas = {"Número de control", "Nombre del alumo", "Estado de expediente", "Faltantes"};
        esquema = new DefaultTableModel(celdas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // ❌ Ninguna celda se puede editar
            }
        };
        JTableHeader header = tablaAlumnos.getTableHeader();
        header.setBackground(new Color(81, 81, 81));

        header.setForeground(Color.WHITE);
        tablaAlumnos.setShowVerticalLines(true);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaAlumnos.setModel(esquema);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Titulo = new javax.swing.JLabel();
        Logo = new javax.swing.JLabel();
        FondoAzul = new javax.swing.JLabel();
        btnAbrir = new javax.swing.JButton();
        btnGenerarInforme = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        panelDatos = new javax.swing.JPanel();
        Semestre = new javax.swing.JLabel();
        Nombre = new javax.swing.JLabel();
        NumControl = new javax.swing.JLabel();
        logoEXP = new javax.swing.JLabel();
        Fecha = new javax.swing.JLabel();
        Header = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        icon1 = new javax.swing.JLabel();
        icon2 = new javax.swing.JLabel();
        icon3 = new javax.swing.JLabel();
        icon4 = new javax.swing.JLabel();
        icon5 = new javax.swing.JLabel();
        icon6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        fecha1 = new javax.swing.JTextField();
        C1 = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        C2 = new javax.swing.JCheckBox();
        fecha2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        C3 = new javax.swing.JCheckBox();
        fecha3 = new javax.swing.JTextField();
        fecha4 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        C4 = new javax.swing.JCheckBox();
        C5 = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        fecha5 = new javax.swing.JTextField();
        C6 = new javax.swing.JCheckBox();
        fecha6 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnActualizar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        icon7 = new javax.swing.JLabel();
        icon8 = new javax.swing.JLabel();
        C7 = new javax.swing.JCheckBox();
        jLabel12 = new javax.swing.JLabel();
        C8 = new javax.swing.JCheckBox();
        fecha7 = new javax.swing.JTextField();
        fecha8 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaAlumnos = new javax.swing.JTable();
        Expediente = new javax.swing.JLabel();
        PDF = new javax.swing.JLabel();
        FondoBlanco = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(1366, 768));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Titulo.setFont(new java.awt.Font("Tw Cen MT", 1, 48)); // NOI18N
        Titulo.setForeground(new java.awt.Color(255, 255, 255));
        Titulo.setText("CONTROL DE EXPEDIENTES");
        getContentPane().add(Titulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 35, 570, 60));
        getContentPane().add(Logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 30, 80, 80));

        FondoAzul.setBackground(new java.awt.Color(0, 0, 51));
        FondoAzul.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FondoAzul.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        FondoAzul.setOpaque(true);
        getContentPane().add(FondoAzul, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1366, 130));

        btnAbrir.setBackground(new java.awt.Color(255, 255, 255));
        btnAbrir.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnAbrir.setForeground(new java.awt.Color(0, 0, 0));
        btnAbrir.setText("Abrir Expediente");
        btnAbrir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 51)));
        btnAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirActionPerformed(evt);
            }
        });
        getContentPane().add(btnAbrir, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 670, 160, 40));

        btnGenerarInforme.setBackground(new java.awt.Color(255, 255, 255));
        btnGenerarInforme.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnGenerarInforme.setForeground(new java.awt.Color(0, 0, 0));
        btnGenerarInforme.setText("Generar informe");
        btnGenerarInforme.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 51)));
        btnGenerarInforme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarInformeActionPerformed(evt);
            }
        });
        getContentPane().add(btnGenerarInforme, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 670, 160, 40));

        btnSalir.setBackground(new java.awt.Color(255, 0, 0));
        btnSalir.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 670, 110, 40));

        jButton2.setBackground(new java.awt.Color(51, 51, 51));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Número de control");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setFocusable(false);
        jButton2.setOpaque(true);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 160, 30));

        jButton3.setBackground(new java.awt.Color(51, 51, 51));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Documentos faltantes");
        jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.setFocusPainted(false);
        jButton3.setFocusable(false);
        jButton3.setOpaque(true);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 160, 160, 30));

        jButton4.setBackground(new java.awt.Color(51, 51, 51));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Nombre del alumno");
        jButton4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jButton4.setBorderPainted(false);
        jButton4.setContentAreaFilled(false);
        jButton4.setFocusPainted(false);
        jButton4.setFocusable(false);
        jButton4.setOpaque(true);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 160, 160, 30));

        jButton5.setBackground(new java.awt.Color(51, 51, 51));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Estado del expediente");
        jButton5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jButton5.setBorderPainted(false);
        jButton5.setContentAreaFilled(false);
        jButton5.setFocusPainted(false);
        jButton5.setFocusable(false);
        jButton5.setOpaque(true);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 160, 160, 30));

        panelDatos.setBackground(new java.awt.Color(204, 204, 204));
        panelDatos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Semestre.setBackground(new java.awt.Color(0, 0, 0));
        Semestre.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Semestre.setForeground(new java.awt.Color(255, 255, 255));
        Semestre.setText("7°");
        panelDatos.add(Semestre, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, -1, -1));

        Nombre.setBackground(new java.awt.Color(0, 0, 0));
        Nombre.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Nombre.setForeground(new java.awt.Color(255, 255, 255));
        Nombre.setText("Mendoza Chavez Jesus Abraham");
        panelDatos.add(Nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        NumControl.setBackground(new java.awt.Color(0, 0, 0));
        NumControl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        NumControl.setForeground(new java.awt.Color(255, 255, 255));
        NumControl.setText("22121668");
        panelDatos.add(NumControl, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));
        panelDatos.add(logoEXP, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 20, 50, 50));

        Fecha.setBackground(new java.awt.Color(0, 0, 0));
        Fecha.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Fecha.setForeground(new java.awt.Color(255, 255, 255));
        panelDatos.add(Fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 240, -1));

        Header.setBackground(new java.awt.Color(27, 26, 45));
        Header.setOpaque(true);
        panelDatos.add(Header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 670, 80));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText(" ");
        panelDatos.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));
        panelDatos.add(icon1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 245, 30, 30));
        panelDatos.add(icon2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 145, 30, 30));
        panelDatos.add(icon3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 295, 30, 30));
        panelDatos.add(icon4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 345, 30, 30));
        panelDatos.add(icon5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 495, 30, 30));
        panelDatos.add(icon6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 195, 30, 30));

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Solicitud de residencia");
        panelDatos.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, -1, 20));

        fecha1.setBackground(new java.awt.Color(255, 255, 255));
        fecha1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        fecha1.setForeground(new java.awt.Color(102, 102, 102));
        fecha1.setText("Sin entregar");
        fecha1.setFocusable(false);
        fecha1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fecha1ActionPerformed(evt);
            }
        });
        panelDatos.add(fecha1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 140, 130, 26));

        C1.setBackground(new java.awt.Color(255, 255, 255));
        C1.setMaximumSize(new java.awt.Dimension(30, 30));
        C1.setMinimumSize(new java.awt.Dimension(30, 30));
        C1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C1ActionPerformed(evt);
            }
        });
        panelDatos.add(C1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 140, 30, 30));

        jLabel2.setBackground(new java.awt.Color(102, 0, 0));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 51));
        jLabel2.setText("Fecha de entrega");
        panelDatos.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 100, 150, 30));

        jLabel3.setBackground(new java.awt.Color(102, 0, 0));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 51));
        jLabel3.setText("¿Entregado?");
        panelDatos.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, -1, 30));

        jLabel4.setBackground(new java.awt.Color(102, 0, 0));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 51));
        jLabel4.setText("Documento");
        panelDatos.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 120, 30));

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Carta compromiso");
        panelDatos.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, -1, -1));

        C2.setBackground(new java.awt.Color(255, 255, 255));
        C2.setMaximumSize(new java.awt.Dimension(30, 30));
        C2.setMinimumSize(new java.awt.Dimension(30, 30));
        panelDatos.add(C2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 190, 30, 30));

        fecha2.setBackground(new java.awt.Color(255, 255, 255));
        fecha2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        fecha2.setForeground(new java.awt.Color(102, 102, 102));
        fecha2.setText("Sin entregar");
        fecha2.setFocusable(false);
        fecha2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fecha2ActionPerformed(evt);
            }
        });
        panelDatos.add(fecha2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 190, 130, -1));

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Carta de aceptación");
        panelDatos.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 240, -1, -1));

        C3.setBackground(new java.awt.Color(255, 255, 255));
        C3.setMaximumSize(new java.awt.Dimension(30, 30));
        C3.setMinimumSize(new java.awt.Dimension(30, 30));
        panelDatos.add(C3, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 240, 30, 30));

        fecha3.setBackground(new java.awt.Color(255, 255, 255));
        fecha3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        fecha3.setForeground(new java.awt.Color(102, 102, 102));
        fecha3.setText("Sin entregar");
        fecha3.setFocusable(false);
        panelDatos.add(fecha3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 240, 130, -1));

        fecha4.setBackground(new java.awt.Color(255, 255, 255));
        fecha4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        fecha4.setForeground(new java.awt.Color(102, 102, 102));
        fecha4.setText("Sin entregar");
        fecha4.setFocusable(false);
        panelDatos.add(fecha4, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 290, 130, -1));

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Carta de terminación");
        panelDatos.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 290, -1, -1));

        C4.setBackground(new java.awt.Color(255, 255, 255));
        C4.setMaximumSize(new java.awt.Dimension(30, 30));
        C4.setMinimumSize(new java.awt.Dimension(30, 30));
        panelDatos.add(C4, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 290, 30, 30));

        C5.setBackground(new java.awt.Color(255, 255, 255));
        C5.setMaximumSize(new java.awt.Dimension(30, 30));
        C5.setMinimumSize(new java.awt.Dimension(30, 30));
        panelDatos.add(C5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 440, 30, 30));

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Informe final");
        panelDatos.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 440, -1, -1));

        fecha5.setBackground(new java.awt.Color(255, 255, 255));
        fecha5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        fecha5.setForeground(new java.awt.Color(102, 102, 102));
        fecha5.setText("Sin entregar");
        fecha5.setFocusable(false);
        panelDatos.add(fecha5, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 440, 130, -1));

        C6.setBackground(new java.awt.Color(255, 255, 255));
        C6.setMaximumSize(new java.awt.Dimension(30, 30));
        C6.setMinimumSize(new java.awt.Dimension(30, 30));
        panelDatos.add(C6, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 490, 30, 30));

        fecha6.setBackground(new java.awt.Color(255, 255, 255));
        fecha6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        fecha6.setForeground(new java.awt.Color(102, 102, 102));
        fecha6.setText("Sin entregar");
        fecha6.setFocusable(false);
        panelDatos.add(fecha6, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 490, 130, -1));

        jLabel10.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Acta de calificación");
        panelDatos.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 490, -1, -1));

        btnActualizar.setBackground(new java.awt.Color(0, 0, 255));
        btnActualizar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setText("Actualizar expediente");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        panelDatos.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 530, -1, -1));

        btnCancelar.setBackground(new java.awt.Color(255, 0, 0));
        btnCancelar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        panelDatos.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 530, -1, -1));

        jLabel11.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Primer reporte");
        panelDatos.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 340, -1, -1));
        panelDatos.add(icon7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 395, 30, 30));
        panelDatos.add(icon8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 445, 30, 30));

        C7.setBackground(new java.awt.Color(255, 255, 255));
        C7.setMaximumSize(new java.awt.Dimension(30, 30));
        C7.setMinimumSize(new java.awt.Dimension(30, 30));
        panelDatos.add(C7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 340, 30, 30));

        jLabel12.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Segundo reporte");
        panelDatos.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 390, -1, -1));

        C8.setBackground(new java.awt.Color(255, 255, 255));
        C8.setMaximumSize(new java.awt.Dimension(30, 30));
        C8.setMinimumSize(new java.awt.Dimension(30, 30));
        panelDatos.add(C8, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 390, 30, 30));

        fecha7.setBackground(new java.awt.Color(255, 255, 255));
        fecha7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        fecha7.setForeground(new java.awt.Color(102, 102, 102));
        fecha7.setText("Sin entregar");
        fecha7.setFocusable(false);
        panelDatos.add(fecha7, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 340, 130, -1));

        fecha8.setBackground(new java.awt.Color(255, 255, 255));
        fecha8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        fecha8.setForeground(new java.awt.Color(102, 102, 102));
        fecha8.setText("Sin entregar");
        fecha8.setFocusable(false);
        panelDatos.add(fecha8, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 390, 130, -1));

        getContentPane().add(panelDatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 140, 690, 580));

        tablaAlumnos.setBackground(new java.awt.Color(204, 204, 255));
        tablaAlumnos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tablaAlumnos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Tittle 4"
            }
        ));
        tablaAlumnos.setSelectionBackground(new java.awt.Color(153, 153, 153));
        tablaAlumnos.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tablaAlumnos.setShowGrid(true);
        jScrollPane1.setViewportView(tablaAlumnos);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 640, 370));
        getContentPane().add(Expediente, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 670, 40, 40));
        getContentPane().add(PDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 670, 40, 40));

        FondoBlanco.setBackground(new java.awt.Color(255, 255, 255));
        FondoBlanco.setForeground(new java.awt.Color(255, 255, 255));
        FondoBlanco.setOpaque(true);
        getContentPane().add(FondoBlanco, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1366, 768));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirActionPerformed
        int filaSeleccionada = tablaAlumnos.getSelectedRow();

        if (filaSeleccionada != -1) {
            // Obtener el valor de la columna 0 (primer dato de la fila)
            Object valor = tablaAlumnos.getValueAt(filaSeleccionada, 0);
            String matricula = valor.toString();
            datos = leer(matricula);
            panelDatos.setVisible(true);
            Nombre.setText(datos[0]);
            NumControl.setText(datos[1]);
            Semestre.setText(datos[2]);
            Fecha.setText("Creado: " + datos[3]);
            marcarCheckboxDocumentosExistentes(Integer.parseInt(datos[4]));
        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún dato");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAbrirActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        String contraseña = JOptionPane.showInputDialog(null, "Ingrese su contraseña:", "Autenticación", JOptionPane.PLAIN_MESSAGE);
        
        if (contraseña != null && contraseña.equals(contrasena)) {
            ActualizarExpediente();
            llenarTabla();
            JOptionPane.showMessageDialog(null, "Datos actualizados");

        } else {
            JOptionPane.showMessageDialog(null, "Permiso denegado");
            marcarCheckboxDocumentosExistentes(Integer.parseInt(datos[4]));

        }

    }//GEN-LAST:event_btnActualizarActionPerformed

// Método mejorado para ActualizarExpediente incluyendo almacenamiento de rutas en el atributo url
    public void ActualizarExpediente() {
        String[] nombres = {
            "Solicitud de residencia",
            "Carta compromiso",
            "Carta de aceptación",
            "Carta de terminación",
            "Informe final",
            "Acta de calificación",
            "Primer reporte",
            "Segundo reporte"
        };

        Date fecha = new Date();

        JCheckBox[] checks = {C1, C2, C3, C4, C5, C6, C7, C8};
        JTextField[] camposFecha = {fecha1, fecha2, fecha3, fecha4, fecha5, fecha6, fecha7, fecha8};

        // Obtener estudiante y expediente
        Estudiante e = cE.buscarPorNumeroControl(datos[1]);
        int idExpediente = e.getIdExpediente().getIdExpediente();

        // Obtener todos los documentos existentes como objetos
        List<Documento> docsExistentes = cD.listaDocumentos();

        // Mapa para detectar por nombre y por URL, y eliminar duplicados de la base
        Map<String, Documento> unicoPorNombre = new HashMap<>();
        Map<String, Documento> unicoPorUrl = new HashMap<>();
        Set<Integer> docsRepetidos = new HashSet<>();

        // Primero filtra SOLO los de este expediente y elimina duplicados de nombre y url
        for (Documento doc : docsExistentes) {
            if (doc.getIdExpediente() != null && doc.getIdExpediente().getIdExpediente() == idExpediente) {
                String nombre = doc.getNombreDocumento();
                String url = doc.getUrl();
                String claveNombre = nombre != null ? nombre : "";
                String claveUrl = (url != null && !url.isEmpty()) ? url : null;
                // Si ya existe este nombre, este doc es duplicado
                if (unicoPorNombre.containsKey(claveNombre)) {
                    docsRepetidos.add(doc.getIdDocumento());
                } else {
                    unicoPorNombre.put(claveNombre, doc);
                }
                // Si ya existe esta URL, este doc es duplicado por URL
                if (claveUrl != null) {
                    if (unicoPorUrl.containsKey(claveUrl)) {
                        docsRepetidos.add(doc.getIdDocumento());
                    } else {
                        unicoPorUrl.put(claveUrl, doc);
                    }
                }
            }
        }
        // Elimina duplicados de la base
        for (Integer idRepetido : docsRepetidos) {
            cD.eliminarDocumento(idRepetido);
        }

        // Vuelve a leer los documentos
        docsExistentes = cD.listaDocumentos();
        unicoPorNombre.clear();
        unicoPorUrl.clear();
        for (Documento doc : docsExistentes) {
            if (doc.getIdExpediente() != null && doc.getIdExpediente().getIdExpediente() == idExpediente) {
                unicoPorNombre.put(doc.getNombreDocumento(), doc);
                String url = doc.getUrl();
                if (url != null && !url.isEmpty()) {
                    unicoPorUrl.put(url, doc);
                }
            }
        }

        // === ACTUALIZACIÓN ===
        for (int i = 0; i < nombres.length; i++) {
            String nombreDoc = nombres[i];
            boolean estaSeleccionado = checks[i].isSelected();
            String urlDoc = (rutas != null && rutas.length > i) ? rutas[i] : null;

            Documento doc = unicoPorNombre.get(nombreDoc);

            if (doc != null) {
                // Ya existe documento con ese nombre
                if (!estaSeleccionado) {
                    doc.setNombreDocumento("No almacenado");
                    doc.setFechaDeCarga(null);
                    doc.setUrl(null);
                    cD.editarDocumento(doc);
                    camposFecha[i].setText("Sin asignar");
                } else {
                    // Actualiza URL solo si es nueva y no usada en otro
                    boolean urlDisponible = (urlDoc == null) || !unicoPorUrl.containsKey(urlDoc) || unicoPorUrl.get(urlDoc).getIdDocumento() == doc.getIdDocumento();
                    if (urlDoc != null && !urlDoc.isEmpty() && urlDisponible) {
                        doc.setUrl(urlDoc);
                        doc.setFechaDeCarga(fecha);
                        cD.editarDocumento(doc);
                        camposFecha[i].setText(formatearFecha(fecha));
                        unicoPorUrl.put(urlDoc, doc); // Marcar como usada
                    }
                }
            } else {
                if (estaSeleccionado) {
                    // Solo inserta si no existe ese nombre y no existe esa URL
                    boolean urlDisponible = (urlDoc == null) || !unicoPorUrl.containsKey(urlDoc);
                    if (urlDisponible) {
                        Documento nuevo = new Documento();
                        nuevo.setIdExpediente(e.getIdExpediente());
                        nuevo.setNombreDocumento(nombreDoc);
                        nuevo.setFechaDeCarga(fecha);
                        nuevo.setUrl(urlDoc);
                        cD.guardarDocumento(nuevo);
                        camposFecha[i].setText(formatearFecha(fecha));
                        unicoPorNombre.put(nombreDoc, nuevo);
                        if (urlDoc != null && !urlDoc.isEmpty()) {
                            unicoPorUrl.put(urlDoc, nuevo);
                        }
                    }
                } else {
                    // Solo inserta "No almacenado" si no existe
                    if (!unicoPorNombre.containsKey("No almacenado")) {
                        Documento vacio = new Documento();
                        vacio.setIdExpediente(e.getIdExpediente());
                        vacio.setNombreDocumento("No almacenado");
                        vacio.setFechaDeCarga(null);
                        vacio.setUrl(null);
                        cD.guardarDocumento(vacio);
                        camposFecha[i].setText("Sin asignar");
                        unicoPorNombre.put("No almacenado", vacio);
                    }
                }
            }
        }
    }

    public String obtenerUrlDocumentoPorOpcion(int opcion, List<Documento> documentosExistentes) {
        String[] nombres = {
            "Solicitud de residencia",
            "Carta compromiso",
            "Carta de aceptación",
            "Carta de terminación",
            "Informe final",
            "Acta de calificación",
            "Primer reporte",
            "Segundo reporte"
        };

        if (opcion < 1 || opcion > nombres.length) {
            JOptionPane.showMessageDialog(null, "Opción inválida.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        String nombreDoc = nombres[opcion - 1];

        // Busca en la lista de documentos que ya tienes cargada
        for (Documento doc : documentosExistentes) {
            if (nombreDoc.equals(doc.getNombreDocumento())) {
                String url = doc.getUrl();
                if (url != null && !url.isEmpty()) {
                    return url;
                }
                break;
            }
        }
        JOptionPane.showMessageDialog(null, "El archivo para '" + nombreDoc + "' no se ha cargado.", "Archivo no encontrado", JOptionPane.INFORMATION_MESSAGE);
        return null;
    }


    private void C1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C1ActionPerformed

        System.out.println("holaaaa");        // TODO add your handling code here:
    }//GEN-LAST:event_C1ActionPerformed

    private void fecha2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fecha2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fecha2ActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        PanelPrincipal p1 = new PanelPrincipal();
        p1.estadoFrame = false;
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void fecha1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fecha1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fecha1ActionPerformed

    private void btnGenerarInformeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarInformeActionPerformed
// Preguntar si desea usar el nombre por defecto o personalizarlo
        int opcion = JOptionPane.showConfirmDialog(null, "¿Deseas guardar el PDF como 'ReporteExpediente.pdf'?", "Nombre del archivo", JOptionPane.YES_NO_OPTION);

        String nombreArchivo;
        if (opcion == JOptionPane.YES_OPTION) {
            nombreArchivo = "ReporteExpediente";
        } else {
            nombreArchivo = JOptionPane.showInputDialog(null, "Ingresa el nombre del archivo:", "Nombre personalizado", JOptionPane.PLAIN_MESSAGE);

            if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nombre inválido. Se usará el nombre por defecto.");
                nombreArchivo = "ReporteExpediente";
            }
        }

// Generar el PDF con el nombre elegido (sin extensión .pdf para que ExportadorPDF lo maneje)
        ExportadorPDF e = new ExportadorPDF();
        e.generarPDFDesdeTabla(
                tablaAlumnos,
                nombreArchivo,
                "C:\\Users\\es982\\Downloads\\Sigerpro\\src\\main\\resources\\Imagenes\\ITO.png", // logo escuela
                "C:\\Users\\es982\\Downloads\\Sigerpro\\src\\main\\resources\\Imagenes\\Imagen1.png" // logo sistema
        );


    }//GEN-LAST:event_btnGenerarInformeActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        panelDatos.setVisible(false);        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    public void abrirPDFPorRuta(String ruta) {
        if (ruta == null || ruta.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay ruta asignada para este documento.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            File pdfFile = new File(ruta);
            if (pdfFile.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    JOptionPane.showMessageDialog(null, "No se puede abrir el archivo automáticamente en este sistema.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "El archivo no existe en la ruta registrada:\n" + ruta);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al intentar abrir el archivo:\n" + ex.getMessage());
        }
    }
    public String[] leer(String numero) {
        ControladoraEstudiante cE = new ControladoraEstudiante();
        ControladoraPersona cP = new ControladoraPersona();

        Estudiante e = cE.buscarPorNumeroControl(numero);
        Persona p = e.getIdPersona();
        Expediente exp = e.getIdExpediente();
        String fecha = formatearFecha(exp.getFechaCreacion());
        String nombre = p.getNombre() + " " + p.getApellidoP() + " " + p.getApellidoM();
        String numeroControl = e.getNumControl();
        int semestre = e.getSemestre();
        String[] datos = {nombre, numeroControl, "" + semestre, fecha, "" + exp.getIdExpediente()};
        return datos;

    }

    public void marcarCheckboxDocumentosExistentes(int idExpediente) {

        String[] nombres = {
            "Solicitud de residencia",
            "Carta compromiso",
            "Carta de aceptación",
            "Carta de terminación",
            "Informe final",
            "Acta de calificación",
            "Primer reporte",
            "Segundo reporte"
        };

        JCheckBox[] checks = {C1, C2, C3, C4, C5, C6, C7, C8};
        JTextField[] camposFecha = {fecha1, fecha2, fecha3, fecha4, fecha5, fecha6, fecha7, fecha8};

// Formatos aceptados (puedes agregar más si necesitas)
        String[] formatosAceptados = {
            "yyyy-MM-dd",
            "dd/MM/yyyy",
            "MM/dd/yyyy",
            "dd-MM-yyyy",
            "yyyy/MM/dd"
        };

// Obtener documentos de la base de datos
        List<String[]> documentos = obtenerNombreYFechaPorExpediente(idExpediente);

// Crear mapa para facilitar búsqueda por nombre
        Map<String, String> mapNombreFecha = new HashMap<>();
        for (String[] doc : documentos) {
            String nombre = doc[0];
            String fecha = doc[1];
            String fechaFormateada = "Fecha inválida";

            if (fecha != null && !fecha.equalsIgnoreCase("null")) {
                for (String formato : formatosAceptados) {
                    try {
                        SimpleDateFormat sdfEntrada = new SimpleDateFormat(formato);
                        sdfEntrada.setLenient(false);
                        Date fechaDate = sdfEntrada.parse(fecha);

                        // Formato de salida fijo
                        SimpleDateFormat sdfSalida = new SimpleDateFormat("dd/MM/yyyy");
                        fechaFormateada = sdfSalida.format(fechaDate);
                        break; // si entra aquí, ya no probamos más formatos
                    } catch (ParseException e) {
                        // Intentar con el siguiente formato
                    }
                }
            }

            mapNombreFecha.put(nombre, fechaFormateada);
        }

// Recorrer todos los documentos esperados
        for (int i = 0; i < nombres.length; i++) {
            String nombre = nombres[i];
            JCheckBox check = checks[i];
            JTextField campoFecha = camposFecha[i];

            if (mapNombreFecha.containsKey(nombre)) {
                String fecha = mapNombreFecha.get(nombre);
                if (nombre.equals("No almacenado")) {
                    check.setSelected(false);
                    campoFecha.setText("Sin asignar");
                } else {
                    check.setSelected(true);
                    campoFecha.setText(fecha != null ? fecha : "Sin asignar");
                }
            } else {
                check.setSelected(false);
                campoFecha.setText("Sin asignar");
            }
        }

    }

    public String[] obtenerUrlsDeDocumentos(int idExpediente) {
        String[] nombres = {
            "Solicitud de residencia",
            "Carta compromiso",
            "Carta de aceptación",
            "Carta de terminación",
            "Informe final",
            "Acta de calificación",
            "Primer reporte",
            "Segundo reporte"
        };

        String[] urls = new String[nombres.length];
        // Inicializa todas en null
        for (int i = 0; i < urls.length; i++) {
            urls[i] = null;
        }

        List<Documento> listaDoc = cD.listaDocumentos(); // Lista completa
        for (Documento doc : listaDoc) {
            if (doc.getIdExpediente() != null && doc.getIdExpediente().getIdExpediente() == idExpediente) {
                String nombreDoc = doc.getNombreDocumento();
                String urlDoc = doc.getUrl();
                // Busca el índice según el nombre
                for (int i = 0; i < nombres.length; i++) {
                    if (nombres[i].equals(nombreDoc)) {
                        urls[i] = (urlDoc != null && !urlDoc.isEmpty()) ? urlDoc : null;
                    }
                }
            }
        }
        return urls;
    }

    public static String formatearFecha(Date fecha) {
        if (fecha == null) {
            return "—"; // por si viene nulo de la BD
        }
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.format(fecha);
    }

    public void adaptarImagenes() {
        Adaptador a = new Adaptador();
        a.ajustarImagenALabel(Logo, "Imagenes/LogoExpediente.png");
        a.ajustarImagenALabel(logoEXP, "Imagenes/exp.png");
        a.ajustarImagenALabel(icon1, "Imagenes/Document.png");
        a.ajustarImagenALabel(icon2, "Imagenes/Document.png");
        a.ajustarImagenALabel(icon3, "Imagenes/Document.png");
        a.ajustarImagenALabel(icon4, "Imagenes/Document.png");
        a.ajustarImagenALabel(icon5, "Imagenes/Document.png");
        a.ajustarImagenALabel(icon6, "Imagenes/Document.png");
        a.ajustarImagenALabel(icon7, "Imagenes/Document.png");

        a.ajustarImagenALabel(icon8, "Imagenes/Document.png");

        a.ajustarImagenALabel(Expediente, "Imagenes/exp.png");
        a.ajustarImagenALabel(PDF, "Imagenes/pdf.png");

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ControlExpedientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ControlExpedientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ControlExpedientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ControlExpedientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ControlExpedientes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox C1;
    private javax.swing.JCheckBox C2;
    private javax.swing.JCheckBox C3;
    private javax.swing.JCheckBox C4;
    private javax.swing.JCheckBox C5;
    private javax.swing.JCheckBox C6;
    private javax.swing.JCheckBox C7;
    private javax.swing.JCheckBox C8;
    private javax.swing.JLabel Expediente;
    private javax.swing.JLabel Fecha;
    private javax.swing.JLabel FondoAzul;
    private javax.swing.JLabel FondoBlanco;
    private javax.swing.JLabel Header;
    private javax.swing.JLabel Logo;
    private javax.swing.JLabel Nombre;
    private javax.swing.JLabel NumControl;
    private javax.swing.JLabel PDF;
    private javax.swing.JLabel Semestre;
    private javax.swing.JLabel Titulo;
    private javax.swing.JButton btnAbrir;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGenerarInforme;
    private javax.swing.JButton btnSalir;
    private javax.swing.JTextField fecha1;
    private javax.swing.JTextField fecha2;
    private javax.swing.JTextField fecha3;
    private javax.swing.JTextField fecha4;
    private javax.swing.JTextField fecha5;
    private javax.swing.JTextField fecha6;
    private javax.swing.JTextField fecha7;
    private javax.swing.JTextField fecha8;
    private javax.swing.JLabel icon1;
    private javax.swing.JLabel icon2;
    private javax.swing.JLabel icon3;
    private javax.swing.JLabel icon4;
    private javax.swing.JLabel icon5;
    private javax.swing.JLabel icon6;
    private javax.swing.JLabel icon7;
    private javax.swing.JLabel icon8;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel logoEXP;
    private javax.swing.JPanel panelDatos;
    private javax.swing.JTable tablaAlumnos;
    // End of variables declaration//GEN-END:variables
}
