/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import controladores.ControladoraEmpresa;
import entidad.Empresa;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ivqz
 */
public class controlEmpresas extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(controlEmpresas.class.getName());
    ControladoraEmpresa controlEmpresa = new ControladoraEmpresa();

    private boolean modoEdicion = false;
    private Empresa empresaEditando = null;

    /**
     * Creates new form controlEmpresas
     */
    public controlEmpresas() {
        initComponents();
        inicializarFormulario();
        configurarValidacionesCampos();
    }

    private void inicializarFormulario() {
        cargarTablaEmpresas();

        txtBuscar.setText("Buscar empresa...");
        txtBuscar.setForeground(Color.GRAY);
        txtBuscar.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (txtBuscar.getText().equals("Buscar empresa...")) {
                    txtBuscar.setText("");
                    txtBuscar.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (txtBuscar.getText().isEmpty()) {
                    txtBuscar.setForeground(Color.GRAY);
                    txtBuscar.setText("Buscar empresa...");
                }
            }
        });
    }

    private void cargarTablaEmpresas() {
        DefaultTableModel modelo = (DefaultTableModel) tableEmpresas.getModel();
        modelo.setRowCount(0);
        List<Empresa> lista = controlEmpresa.listarEmpresas();
        for (Empresa e : lista) {
            modelo.addRow(new Object[]{e.getRfc(), e.getNombreEmpresa(), e.getDescripcion(), e.getNumeroTelefonico()});
        }
    }

    private void guardarEmpresaFormulario() {
        String rfc = txtRFC.getText().trim().replaceAll("[^A-ZÑ&\\d]", "").toUpperCase();
        String nombre = txtNombre.getText().trim().replaceAll("[^\\p{L}\\p{N}\\s]", "");
        String descripcion = txtDescripcion.getText().trim().replaceAll("[^\\p{L}\\p{N}\\s.,\\-()]", "");
        String telefono = txtNumero.getText().trim().replaceAll("[^\\d]", "");

        if (rfc.length() > 13) {
            rfc = rfc.substring(0, 13);
        }
        if (nombre.length() > 100) {
            nombre = nombre.substring(0, 100);
        }
        if (descripcion.length() > 255) {
            descripcion = descripcion.substring(0, 255);
        }
        if (telefono.length() > 10) {
            telefono = telefono.substring(0, 10);
        }

        txtRFC.setText(rfc);
        txtNombre.setText(nombre);
        txtDescripcion.setText(descripcion);
        txtNumero.setText(telefono);

        if (rfc.isEmpty() || nombre.isEmpty() || descripcion.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos.");
            return;
        }

        if (!rfc.matches("^[A-ZÑ&]{3,4}\\d{6}[A-Z0-9]{2,3}$")) {
            JOptionPane.showMessageDialog(this, "El RFC no tiene un formato válido. Ej: ABC123456XYZ");
            return;
        }

        if (!telefono.matches("^\\d{10}$")) {
            JOptionPane.showMessageDialog(this, "El número telefónico debe tener exactamente 10 dígitos.");
            return;
        }

        if (modoEdicion) {
            // Actualizar empresa existente
            empresaEditando.setNombreEmpresa(nombre);
            empresaEditando.setDescripcion(descripcion);
            empresaEditando.setNumeroTelefonico(telefono);

            controlEmpresa.editarEmpresa(empresaEditando);
            JOptionPane.showMessageDialog(this, "Empresa actualizada exitosamente.");

            modoEdicion = false;
            empresaEditando = null;
            txtRFC.setEnabled(true);
        } else {
            // Validaciones para nuevo registro
            if (controlEmpresa.buscarEmpresaPorNombre(nombre) != null) {
                JOptionPane.showMessageDialog(this, "El nombre de la empresa ya está registrado.");
                return;
            }

            Empresa eRFC = new Empresa();
            eRFC.setRfc(rfc);
            if (controlEmpresa.existeEmpresa(eRFC)) {
                JOptionPane.showMessageDialog(this, "El RFC ya está registrado.");
                return;
            }

            if (controlEmpresa.existeNumeroDeTelefono(telefono)) {
                JOptionPane.showMessageDialog(this, "El número telefónico ya está registrado.");
                return;
            }

            Empresa nueva = new Empresa();
            nueva.setRfc(rfc);
            nueva.setNombreEmpresa(nombre);
            nueva.setDescripcion(descripcion);
            nueva.setNumeroTelefonico(telefono);

            controlEmpresa.guardarEmpresa(nueva);
            JOptionPane.showMessageDialog(this, "Empresa registrada exitosamente.");
        }

        cargarTablaEmpresas();
        limpiarFormulario();
    }

    private void configurarValidacionesCampos() {
        txtRFC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetterOrDigit(c) && c != '&' && c != 'Ñ' && c != 'ñ' && !Character.isISOControl(c)) {
                    e.consume();
                }
                if (txtRFC.getText().length() >= 13 && !Character.isISOControl(c)) {
                    e.consume();
                }
            }
        });
        txtRFC.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                String original = txtRFC.getText();
                String limpio = original.trim().replaceAll("[^A-ZÑ&\\d]", "").toUpperCase();
                if (!original.equals(limpio)) {
                    JOptionPane.showMessageDialog(null, "Se eliminaron caracteres no válidos del campo RFC.");
                }
                if (limpio.length() > 13) {
                    limpio = limpio.substring(0, 13);
                    JOptionPane.showMessageDialog(null, "El RFC ha sido recortado a 13 caracteres.");
                }
                txtRFC.setText(limpio);
            }
        });

        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetterOrDigit(c) && !Character.isSpaceChar(c) && !Character.isISOControl(c)) {
                    e.consume();
                }
                if (txtNombre.getText().length() >= 100 && !Character.isISOControl(c)) {
                    e.consume();
                }
            }
        });
        txtNombre.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                String original = txtNombre.getText();
                String limpio = original.trim().replaceAll("[^\\p{L}\\p{N}\\s]", "");
                if (!original.equals(limpio)) {
                    JOptionPane.showMessageDialog(null, "Se eliminaron caracteres no válidos del campo Nombre.");
                }
                if (limpio.length() > 100) {
                    limpio = limpio.substring(0, 100);
                    JOptionPane.showMessageDialog(null, "El nombre ha sido recortado a 100 caracteres.");
                }
                txtNombre.setText(limpio);
            }
        });

        txtDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetterOrDigit(c) && !Character.isSpaceChar(c)
                        && c != '.' && c != ',' && c != '-' && c != '(' && c != ')' && !Character.isISOControl(c)) {
                    e.consume();
                }
                if (txtDescripcion.getText().length() >= 255 && !Character.isISOControl(c)) {
                    e.consume();
                }
            }
        });
        txtDescripcion.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                String original = txtDescripcion.getText();
                String limpio = original.trim().replaceAll("[^\\p{L}\\p{N}\\s.,\\-()]", "");
                if (!original.equals(limpio)) {
                    JOptionPane.showMessageDialog(null, "Se eliminaron caracteres no válidos del campo Descripción.");
                }
                if (limpio.length() > 255) {
                    limpio = limpio.substring(0, 255);
                    JOptionPane.showMessageDialog(null, "La descripción ha sido recortada a 255 caracteres.");
                }
                txtDescripcion.setText(limpio);
            }
        });

        txtNumero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && !Character.isISOControl(c)) {
                    e.consume();
                }
                if (txtNumero.getText().length() >= 10 && !Character.isISOControl(c)) {
                    e.consume();
                }
            }
        });
        txtNumero.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                String original = txtNumero.getText();
                String limpio = original.trim().replaceAll("[^\\d]", "");
                if (!original.equals(limpio)) {
                    JOptionPane.showMessageDialog(null, "Se eliminaron caracteres no válidos del campo Teléfono.");
                }
                if (limpio.length() > 10) {
                    limpio = limpio.substring(0, 10);
                    JOptionPane.showMessageDialog(null, "El número ha sido recortado a 10 dígitos.");
                }
                txtNumero.setText(limpio);
            }
        });
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
        lblVolver = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableEmpresas = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtRFC = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtNumero = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        btnRegistrar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(161, 161, 161));

        jPanel2.setBackground(new java.awt.Color(27, 23, 18));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 52)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(250, 124, 14));
        jLabel1.setText("CONTROL DE EMPRESAS");

        lblVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8-cuadro-movimiento-izquierda-100.png"))); // NOI18N
        lblVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblVolverMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblVolver)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lblVolver)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        txtBuscar.setBackground(new java.awt.Color(255, 255, 255));
        txtBuscar.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(102, 102, 102));
        txtBuscar.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 0, new java.awt.Color(102, 102, 102)), javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createMatteBorder(0, 12, 0, 0, new java.awt.Color(250, 124, 14)))));

        btnBuscar.setBackground(new java.awt.Color(255, 0, 51));
        btnBuscar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8-buscar-50.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        tableEmpresas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "RFC", "Nombre", "Descripcion", "Numero Telefonico"
            }
        ));
        jScrollPane1.setViewportView(tableEmpresas);

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));

        btnGuardar.setBackground(new java.awt.Color(0, 30, 132));
        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(27, 23, 18));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(250, 124, 14));
        jLabel3.setText("PANEL DE DATOS");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("RFC:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Nombre:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Descripcion:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Numero Telefonico:");

        txtRFC.setBackground(new java.awt.Color(255, 255, 255));
        txtRFC.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txtRFC.setForeground(new java.awt.Color(102, 102, 102));
        txtRFC.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 0, new java.awt.Color(102, 102, 102)), javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createMatteBorder(0, 12, 0, 0, new java.awt.Color(250, 124, 14)))));

        txtNombre.setBackground(new java.awt.Color(255, 255, 255));
        txtNombre.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txtNombre.setForeground(new java.awt.Color(102, 102, 102));
        txtNombre.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 0, new java.awt.Color(102, 102, 102)), javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createMatteBorder(0, 12, 0, 0, new java.awt.Color(250, 124, 14)))));

        txtNumero.setBackground(new java.awt.Color(255, 255, 255));
        txtNumero.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txtNumero.setForeground(new java.awt.Color(102, 102, 102));
        txtNumero.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 0, new java.awt.Color(102, 102, 102)), javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createMatteBorder(0, 12, 0, 0, new java.awt.Color(250, 124, 14)))));

        btnCancelar.setBackground(new java.awt.Color(255, 0, 51));
        btnCancelar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        txtDescripcion.setBackground(new java.awt.Color(255, 255, 255));
        txtDescripcion.setColumns(20);
        txtDescripcion.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txtDescripcion.setForeground(new java.awt.Color(102, 102, 102));
        txtDescripcion.setRows(5);
        txtDescripcion.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 0, new java.awt.Color(102, 102, 102)), javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createMatteBorder(0, 12, 0, 0, new java.awt.Color(250, 124, 14)))));
        jScrollPane2.setViewportView(txtDescripcion);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                    .addComponent(txtNumero)
                    .addComponent(txtRFC)
                    .addComponent(jScrollPane2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRFC, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnCancelar))
                .addContainerGap())
        );

        btnRegistrar.setBackground(new java.awt.Color(0, 30, 132));
        btnRegistrar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnRegistrar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8-empresa-50.png"))); // NOI18N
        btnRegistrar.setText("REGISTRAR");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        btnEditar.setBackground(new java.awt.Color(0, 30, 132));
        btnEditar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(255, 255, 255));
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8-editar-50.png"))); // NOI18N
        btnEditar.setText("EDITAR");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnActualizar.setBackground(new java.awt.Color(0, 30, 132));
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8-actualizar-25.png"))); // NOI18N
        btnActualizar.setText("ACTUALIZAR TABLA");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 182, Short.MAX_VALUE)
                                .addComponent(btnRegistrar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEditar))
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnActualizar)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtBuscar))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnRegistrar)
                                .addComponent(btnEditar)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnActualizar))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        String texto = txtBuscar.getText().trim().toLowerCase();
        if (texto.isEmpty() || texto.equals("Buscar empresa...")) {
            JOptionPane.showMessageDialog(this, "Ingrese un nombre o RFC para buscar.");
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) tableEmpresas.getModel();
        modelo.setRowCount(0);

        for (Empresa e : controlEmpresa.listarEmpresas()) {
            if (e.getNombreEmpresa().toLowerCase().contains(texto) || e.getRfc().toLowerCase().contains(texto)) {
                modelo.addRow(new Object[]{e.getRfc(), e.getNombreEmpresa(), e.getDescripcion(), e.getNumeroTelefonico()});
            }
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        guardarEmpresaFormulario();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        limpiarFormulario();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        // TODO add your handling code here:
        limpiarFormulario();
        JOptionPane.showMessageDialog(this, "Rellene el formulario y presione Guardar para registrar la nueva empresa.");
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        int fila = tableEmpresas.getSelectedRow();
        if (fila >= 0) {
            String rfcSeleccionado = tableEmpresas.getValueAt(fila, 0).toString();
            for (Empresa e : controlEmpresa.listarEmpresas()) {
                if (e.getRfc().equals(rfcSeleccionado)) {
                    empresaEditando = e;
                    break;
                }
            }

            if (empresaEditando != null) {
                txtRFC.setText(empresaEditando.getRfc());
                txtNombre.setText(empresaEditando.getNombreEmpresa());
                txtDescripcion.setText(empresaEditando.getDescripcion());
                txtNumero.setText(empresaEditando.getNumeroTelefonico());
                txtRFC.setEnabled(false);
                modoEdicion = true;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una empresa de la tabla antes de editar.");
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        cargarTablaEmpresas();
        JOptionPane.showMessageDialog(this, "Tabla actualizada correctamente.");
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void lblVolverMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblVolverMouseClicked
        // TODO add your handling code here:
        PanelPrincipal p = new PanelPrincipal();
        p.estadoFrame = false;
        this.dispose();
    }//GEN-LAST:event_lblVolverMouseClicked

    private void limpiarFormulario() {
        txtRFC.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtNumero.setText("");
        txtRFC.setEnabled(true);
        modoEdicion = false;
        empresaEditando = null;
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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new controlEmpresas().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblVolver;
    private javax.swing.JTable tableEmpresas;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JTextField txtRFC;
    // End of variables declaration//GEN-END:variables
}
