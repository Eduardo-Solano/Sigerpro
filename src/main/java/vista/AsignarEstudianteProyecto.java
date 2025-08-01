/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import controladores.ControladoraEmpresa;
import controladores.ControladoraEstudiante;
import controladores.ControladoraPersona;
import controladores.ControladoraProyecto;
import controladores.ValidadorCampos;
import entidad.Empresa;
import entidad.Estudiante;
import entidad.Persona;
import entidad.Proyecto;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
//import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author iigna
 */
public class AsignarEstudianteProyecto extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AsignarEstudianteProyecto.class.getName());
    DefaultTableModel modeloTabla;
    ControladoraProyecto controlP = new ControladoraProyecto();
    ControladoraPersona controlPer = new ControladoraPersona();
    ControladoraEmpresa controlE = new ControladoraEmpresa();
    ControladoraEstudiante controlEs = new ControladoraEstudiante();
    BancoDeProyectos bp = new BancoDeProyectos();

    /**
     * Creates new form asignarARaProyecto
     */
    public AsignarEstudianteProyecto() {
        this.setUndecorated(true);
        initComponents();
        this.setVisible(true);
        this.setLocation(0, 0);
        this.setSize(1366, 768);
        //cargarTablaProyectos();
        //  this.setUndecorated(false);
        String[] columnas = {"ID", "Nombre", "Empresa", "Descripcion", "Estado", "Fecha de Registro", "URL", "Numero de Alumnos"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tableProyectos.setModel(modeloTabla);
        llenarTabla();
        // txtNumControl.enable(false);
        cargarComboBox();
    }

    private void limpiarTabla() {
        int rowCount = modeloTabla.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            modeloTabla.removeRow(i);
        }
    }

    private void llenarTabla() {
        ArrayList<Proyecto> listaProyecto = controlP.listarProyectos();
        if (!listaProyecto.isEmpty()) {
            for (Proyecto p : listaProyecto) {
                String nombre = p.getNombreProyecto();
                Integer id = p.getIdProyecto();
                String descripcion = p.getDescripcion();
                Date fecha = p.getFechaRegistro();
                String estado = p.getEstado();
                String url = p.getUrlDocumento();
                Integer numAlumnos = p.getNumAlumnosSug();
                Empresa idE = p.getIdEmpresa();
                Empresa emp = controlE.buscarEmpresaPorId(idE.getIdEmpresa());
                String empresa = emp.getNombreEmpresa();
                modeloTabla.addRow(new Object[]{id, nombre, empresa, descripcion, estado, fecha, url, numAlumnos});
            }
        } else {
            JOptionPane.showMessageDialog(null, "Registra un proyecto primero");
        }
    }

    private void cargarComboBox() {
        List<Estudiante> estudiante = controlEs.listarEstudiantes();

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        if (estudiante != null && !estudiante.isEmpty()) {
            for (Estudiante e : estudiante) {
                //if (e.getIdProyecto() == null) {
                    model.addElement(e.getNumControl());
//                } else if (model.getElementAt(0) == null) {
//                    model.addElement("No hay estudiantes disponibles");
//                    //continue;
//                }

            }
            cboNumControl.setModel(model);
            cboNumControl.setEnabled(true);

            // ¡¡¡Agrega el listener DESPUÉS de setModel siempre!!!
            cboNumControl.addItemListener(new java.awt.event.ItemListener() {
                public void itemStateChanged(java.awt.event.ItemEvent evt) {
                    cboNumControlItemStateChanged(evt);
                }
            });

            if (cboNumControl.getItemCount() > 0) {
                cboNumControl.setSelectedIndex(0);
            }
        } else {
            DefaultComboBoxModel<String> emptyModel = new DefaultComboBoxModel<>();
            emptyModel.addElement("No hay estudiantes registrados");

            cboNumControl.setModel(emptyModel);
            cboNumControl.setEnabled(false);
        }

    }

    private void cargarSugeridosEnComboBox() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (int i = 1; i <= 4; i++) {
            model.addElement(String.valueOf(i));
        }
        cboNumControl.setModel(model);
        cboNumControl.setSelectedIndex(0);
    }

    private void limpiarCampos() {
        txtNumControl.setText("");
        if (cboNumControl.getItemCount() > 0) {
            cboNumControl.setSelectedIndex(0);
        }
        // txtNumeroContacto.setText("");
    }

    private void validar() {
        ValidadorCampos validador = new ValidadorCampos();

        String numC = txtNumControl.getText();
        boolean error = validador.validarNumero(numC);

        if (!error) {
            JOptionPane.showMessageDialog(null, "Solo debe contener 8 Digitos Numericos", "Errores encontrados", JOptionPane.ERROR_MESSAGE);
        } else {

        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblRegresar = new javax.swing.JLabel();
        btnAsignarAutomaticamente = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableProyectos = new javax.swing.JTable();
        txtNumControl = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cboNumControl = new javax.swing.JComboBox<>();
        txtxNombre = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(161, 161, 161));

        jPanel2.setBackground(new java.awt.Color(27, 23, 18));

        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 1, 50)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(250, 124, 14));
        jLabel1.setText("BANCO DE PROYECTOS");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8-volver-50 (2).png"))); // NOI18N

        lblRegresar.setBackground(new java.awt.Color(255, 255, 255));
        lblRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/regresar (reg_estudiante).png"))); // NOI18N
        lblRegresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRegresarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(lblRegresar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 298, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(209, 209, 209))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblRegresar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnAsignarAutomaticamente.setBackground(new java.awt.Color(250, 124, 14));
        btnAsignarAutomaticamente.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnAsignarAutomaticamente.setForeground(new java.awt.Color(255, 255, 255));
        btnAsignarAutomaticamente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8-usuario-50.png"))); // NOI18N
        btnAsignarAutomaticamente.setText("ASIGNAR ESTUDIANTE");
        btnAsignarAutomaticamente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsignarAutomaticamenteActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tw Cen MT", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(27, 23, 18));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("PROYECTOS REGISTRADOS");

        tableProyectos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Nombre", "Estado", "URL", "Alumnos", "Empresa", "Asesor", "Revisor"
            }
        ));
        tableProyectos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableProyectosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableProyectos);

        txtNumControl.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        txtNumControl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumControlActionPerformed(evt);
            }
        });
        txtNumControl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumControlKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("No. Control:");

        cboNumControl.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        cboNumControl.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboNumControlItemStateChanged(evt);
            }
        });
        cboNumControl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboNumControlMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cboNumControlMouseEntered(evt);
            }
        });

        txtxNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtxNombreActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(255, 255, 153));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Datos del Estudiante:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(259, 259, 259))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(btnAsignarAutomaticamente)
                        .addGap(160, 160, 160)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNumControl, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtxNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cboNumControl, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAsignarAutomaticamente)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNumControl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtxNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboNumControl, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAsignarAutomaticamenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignarAutomaticamenteActionPerformed
        // TODO add your handling code here:
        int fila = tableProyectos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un proyecto.");
            return;
        }
        //int idProyecto = (int) tableProyectos.getValueAt(fila, 0);
        Integer id = (Integer) modeloTabla.getValueAt(fila, 0);
        controlP.asignarProyectoAEstudiante(modeloTabla, txtNumControl.getText(), id);
        limpiarTabla();

        bp.cargarTablaProyectos();
        llenarTabla();
        //ancoDeProyectos.btnResidente.setEnabled(true);
    }//GEN-LAST:event_btnAsignarAutomaticamenteActionPerformed

    private void tableProyectosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableProyectosMouseClicked
        int fila = tableProyectos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar una fila para asigar.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
            );
        } else {
            txtNumControl.enable(true);
        }
    }//GEN-LAST:event_tableProyectosMouseClicked

    private void txtNumControlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumControlActionPerformed
        // TODO add your handling code here:
        //BancoDeProyectos.btnResidente.setEnabled(true);
    }//GEN-LAST:event_txtNumControlActionPerformed

    private void lblRegresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRegresarMouseClicked
        bp.cargarTablaProyectos();        // TODO add your handling code here:
        limpiarCampos();
        this.dispose();
    }//GEN-LAST:event_lblRegresarMouseClicked

    private void txtNumControlKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumControlKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        String textoActual = txtNumControl.getText();
        // Permitir la lera 'c' o 'C' SOLO si el campo está vacío (posición inicial)
        if ((c == 'c' || c == 'C' || c == 'D' || c == 'd' || c == 'm' || c == 'M') && textoActual.length() == 0) {
            return; // se permite
        }

        // Permitir solo dígitos del 0 al 9
        if (!Character.isDigit(c) | txtNumControl.getText().length() > 8) {
            evt.consume(); // se bloquea
        }
    }//GEN-LAST:event_txtNumControlKeyTyped

    private void cboNumControlMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboNumControlMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_cboNumControlMouseClicked

    private void cboNumControlMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboNumControlMouseEntered
        // TODO add your handling code here:

    }//GEN-LAST:event_cboNumControlMouseEntered

    private void cboNumControlItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboNumControlItemStateChanged
//        // TODO add your handling code here:
//        String numC = (String) cboNumControl.getSelectedItem();
//        JOptionPane.showMessageDialog(null, "Num control: " + numC);
//        //if(numC.equals()){
//        //Estudiante est1=controlEs.buscarPorNumeroControl(numC);
//        txtNumControl.setText(numC);
//        //}
     if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
        String numC = (String) cboNumControl.getSelectedItem();
        System.out.println("Seleccionado: " + numC); // Quita después de depurar
        if (numC != null && !numC.contains("No hay")) {
            txtNumControl.setText(numC);
        } else {
            txtNumControl.setText("");
        }
    }
    }//GEN-LAST:event_cboNumControlItemStateChanged

    private void txtxNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtxNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtxNombreActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String numC=(String)cboNumControl.getSelectedItem();
        if(numC==null){
            JOptionPane.showMessageDialog(null, "Error al buscar estudiante");
            return;
        }
        Estudiante est1=controlEs.buscarPorNumeroControl(numC);       
        if(est1==null){
            JOptionPane.showMessageDialog(null, "No existe estudiante");
            return;
        }
        Persona p1=est1.getIdPersona();
        String nombre=p1.getNombre()+" "+p1.getApellidoP()+" "+p1.getApellidoM();
        txtNumControl.setText(numC);
        txtxNombre.setText(nombre);
    }//GEN-LAST:event_jButton1ActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new AsignarEstudianteProyecto().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAsignarAutomaticamente;
    private javax.swing.JComboBox<String> cboNumControl;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblRegresar;
    private javax.swing.JTable tableProyectos;
    private javax.swing.JTextField txtNumControl;
    private javax.swing.JTextField txtxNombre;
    // End of variables declaration//GEN-END:variables
}
