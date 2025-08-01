/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author jc389
 */
public class PanelPrincipal extends javax.swing.JFrame {

    // === VARIABLES DE CONTROL PARA SUBMENÚS ===
    Adaptador a = new Adaptador();
    private final Map<String, JFrame> ventanasAbiertas = new HashMap<>();
    private BancoDeProyectos bancoProyectos;
    private controlEstudiantes controlEstudiantes;
    private Oficios oficios;
    private ActaCalificacion actaCalificacion;
    private controlEmpresas ventanaEmpresas;
    private ControlExpedientes ventanaExp;
    private controlDocentes ventanaDocentes;
    boolean pResidentes, pDocentes, pEmpresas, pProyectos, pCronogramas, pExpedientes;
    boolean dResidentes, dDocentes, dEmpresas, dProyectos, dCronogramas, dExpedientes;
    static boolean  estadoFrame;
    public String contrasena = "";

    /**
     * Creates new form PanelPrincipal
     */
    public PanelPrincipal() {
        this.setSize(1366, 768);
        this.setMinimumSize(new Dimension(1366, 768));
        initComponents();
        lblError.setVisible(false);

        opcionesExpediente.setVisible(false);
        panelExpedientes.setVisible(false);
        adaptarImagenes();
        desactivarPaneles();

    }
    
    public void abrirVentanaUnica(String clave, JFrame ventana) {
    if (!ventanasAbiertas.containsKey(clave)) {
        ventanasAbiertas.put(clave, ventana);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);

        ventana.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                ventanasAbiertas.remove(clave);
            }

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                ventanasAbiertas.remove(clave);
            }
        });
    } else {
        JFrame v = ventanasAbiertas.get(clave);
        v.setExtendedState(JFrame.NORMAL); // Restaurar si está minimizada
        v.toFront();
        v.requestFocus();
    }
}



    public void deslizarPaneles(int option) {
        switch (option) {
            case 1:
                dResidentes = true;

                if (!dDocentes) {
                    a.slidePanelDown(opcionesDocente, 80, 15);
                } else if (dDocentes) {
                    panelDocentes.setVisible(false);
                    pDocentes = false;
                    a.slidePanelDown(opcionesDocente, 80, 15);
                    dDocentes = false;
                    return;
                }

                if (!dEmpresas) {
                    a.slidePanelDown(opcionesEmpresas, 80, 15);
                } else if (dEmpresas) {
                    panelEmpresas.setVisible(false);
                    pEmpresas = false;
                    a.slidePanelDown(opcionesDocente, 80, 15);
                    a.slidePanelDown(opcionesEmpresas, 80, 15);
                    dEmpresas = false;
                    return;
                }

                if (!dProyectos) {
                    a.slidePanelDown(opcionesBanco, 80, 15);
                } else if (dProyectos) {
                    pProyectos = false;
                    panelProyectos.setVisible(false);
                    a.slidePanelDown(opcionesBanco, 80, 15);
                    a.slidePanelDown(opcionesDocente, 80, 15);
                    a.slidePanelDown(opcionesEmpresas, 80, 15);
                    dProyectos = false;
                    return;
                }

                if (!dCronogramas) {
                    a.slidePanelDown(opcionesCronogramas, 80, 15);
                } else if (dCronogramas) {
                    pCronogramas = false;
                    panelGenerarReportes.setVisible(false);
                    a.slidePanelDown(opcionesCronogramas, 80, 15);
                    a.slidePanelDown(opcionesBanco, 80, 15);
                    a.slidePanelDown(opcionesDocente, 80, 15);
                    a.slidePanelDown(opcionesEmpresas, 80, 15);
                    dCronogramas = false;
                    return;
                }

                if (!dExpedientes) {
                    a.slidePanelDown(opcionesExpediente, 80, 15);
                } else if (dExpedientes) {
                    pExpedientes = false;
                    panelExpedientes.setVisible(false);
                    a.slidePanelDown(opcionesExpediente, 80, 15);
                    a.slidePanelDown(opcionesBanco, 80, 15);
                    a.slidePanelDown(opcionesDocente, 80, 15);
                    a.slidePanelDown(opcionesEmpresas, 80, 15);
                    dExpedientes = false;
                    return;
                }
                break;

            case 2:
                dDocentes = true;

                if (dResidentes) {
                    pResidentes = false;
                    panelResidentes.setVisible(false);
                    a.slidePanelUp(opcionesDocente, 80, 15);
                    dResidentes = false;
                    return;
                }

                if (!dEmpresas) {
                    a.slidePanelDown(opcionesEmpresas, 80, 15);
                } else if (dEmpresas) {
                    pEmpresas = false;
                    panelEmpresas.setVisible(false);
                    a.slidePanelDown(opcionesEmpresas, 80, 15);
                    dEmpresas = false;
                    return;
                }

                if (!dProyectos) {
                    a.slidePanelDown(opcionesBanco, 80, 15);
                } else if (dProyectos) {
                    panelProyectos.setVisible(false);
                    pProyectos = false;
                    a.slidePanelDown(opcionesBanco, 80, 15);
                    a.slidePanelDown(opcionesEmpresas, 80, 15);
                    dProyectos = false;
                    return;
                }

                if (!dCronogramas) {
                    a.slidePanelDown(opcionesCronogramas, 80, 15);
                    dCronogramas = true;
                } else if (dCronogramas) {
                    panelGenerarReportes.setVisible(false);
                    pCronogramas = false;
                    a.slidePanelDown(opcionesCronogramas, 80, 15);
                    a.slidePanelDown(opcionesBanco, 80, 15);
                    a.slidePanelDown(opcionesEmpresas, 80, 15);
                    dCronogramas = false;
                    return;
                }

                if (!dExpedientes) {
                    a.slidePanelDown(opcionesExpediente, 80, 15);
                } else if (dExpedientes) {
                    panelExpedientes.setVisible(false);
                    pExpedientes = false;
                    a.slidePanelDown(opcionesExpediente, 80, 15);
                    a.slidePanelDown(opcionesCronogramas, 80, 15);
                    a.slidePanelDown(opcionesBanco, 80, 15);
                    a.slidePanelDown(opcionesEmpresas, 80, 15);
                    dExpedientes = false;
                    return;
                }
                break;

            case 3:
                dEmpresas = true;

                if (dResidentes) {
                    panelResidentes.setVisible(false);
                    pResidentes = false;
                    a.slidePanelUp(opcionesEmpresas, 80, 15);
                    a.slidePanelUp(opcionesDocente, 80, 15);
                    dResidentes = false;
                    return;
                } else if (dDocentes) {
                    panelDocentes.setVisible(false);
                    pDocentes = false;
                    a.slidePanelUp(opcionesEmpresas, 80, 15);
                    dDocentes = false;
                    return;
                }

                if (!dProyectos) {
                    a.slidePanelDown(opcionesBanco, 80, 15);
                } else if (dProyectos) {
                    pProyectos = false;
                    panelProyectos.setVisible(false);
                    a.slidePanelDown(opcionesBanco, 80, 15);
                    dProyectos = false;
                    return;
                }

                if (!dCronogramas) {
                    a.slidePanelDown(opcionesCronogramas, 80, 15);
                } else if (dCronogramas) {
                    pCronogramas = false;
                    panelGenerarReportes.setVisible(false);
                    a.slidePanelDown(opcionesBanco, 80, 15);
                    a.slidePanelDown(opcionesCronogramas, 80, 15);
                    dCronogramas = false;
                    return;
                }

                if (!dExpedientes) {
                    a.slidePanelDown(opcionesExpediente, 80, 15);
                } else if (dExpedientes) {
                    pExpedientes = false;
                    panelExpedientes.setVisible(false);
                    a.slidePanelDown(opcionesExpediente, 80, 15);
                    a.slidePanelDown(opcionesBanco, 80, 15);
                    a.slidePanelDown(opcionesCronogramas, 80, 15);
                    dExpedientes = false;
                }
                break;

            case 4:
                dProyectos = true;

                if (dResidentes) {
                    pResidentes = false;
                    panelResidentes.setVisible(false);
                    a.slidePanelUp(opcionesEmpresas, 80, 15);
                    a.slidePanelUp(opcionesDocente, 80, 15);
                    a.slidePanelUp(opcionesBanco, 80, 15);
                    dResidentes = false;
                    return;
                } else if (dDocentes) {
                    pDocentes = false;
                    panelDocentes.setVisible(false);
                    a.slidePanelUp(opcionesEmpresas, 80, 15);
                    a.slidePanelUp(opcionesBanco, 80, 15);
                    dDocentes = false;
                    return;
                } else if (dEmpresas) {
                    pEmpresas = false;
                    panelEmpresas.setVisible(false);
                    a.slidePanelUp(opcionesBanco, 80, 15);
                    dEmpresas = false;
                    return;
                }

                if (!dCronogramas) {
                    a.slidePanelDown(opcionesCronogramas, 80, 15);
                } else if (dCronogramas) {
                    pCronogramas = false;
                    panelGenerarReportes.setVisible(false);
                    a.slidePanelDown(opcionesCronogramas, 80, 15);
                    dCronogramas = false;
                    return;
                }

                if (!dExpedientes) {
                    a.slidePanelDown(opcionesExpediente, 80, 15);
                } else if (dExpedientes) {
                    pExpedientes = false;
                    panelExpedientes.setVisible(false);
                    a.slidePanelDown(opcionesCronogramas, 80, 15);
                    a.slidePanelDown(opcionesExpediente, 80, 15);
                    dExpedientes = false;
                    return;
                }
                break;

            case 5:
                dCronogramas = true;

                if (dResidentes) {
                    pResidentes = false;
                    panelResidentes.setVisible(false);
                    a.slidePanelUp(opcionesEmpresas, 80, 15);
                    a.slidePanelUp(opcionesDocente, 80, 15);
                    a.slidePanelUp(opcionesBanco, 80, 15);
                    a.slidePanelUp(opcionesCronogramas, 80, 15);
                    dResidentes = false;
                    return;
                } else if (dDocentes) {
                    pDocentes = false;
                    panelDocentes.setVisible(false);
                    a.slidePanelUp(opcionesEmpresas, 80, 15);
                    a.slidePanelUp(opcionesBanco, 80, 15);
                    a.slidePanelUp(opcionesCronogramas, 80, 15);
                    dDocentes = false;
                    return;
                } else if (dEmpresas) {
                    pEmpresas = false;
                    panelEmpresas.setVisible(false);
                    a.slidePanelUp(opcionesBanco, 80, 15);
                    a.slidePanelUp(opcionesCronogramas, 80, 15);
                    dEmpresas = false;
                    return;
                } else if (dProyectos) {
                    pProyectos = false;
                    panelProyectos.setVisible(false);
                    a.slidePanelUp(opcionesCronogramas, 80, 15);
                    dProyectos = false;
                    return;
                }

                if (!dExpedientes) {
                    pExpedientes = false;
                    panelExpedientes.setVisible(false);
                    a.slidePanelDown(opcionesExpediente, 80, 15);
                    dExpedientes = false;
                    return;
                }
                break;
        }
    }

    public void acomodarPaneles(int option) {

        switch (option) {
            case 1:
                a.slidePanelUp(opcionesDocente, 80, 15);
                a.slidePanelUp(opcionesEmpresas, 80, 15);
                a.slidePanelUp(opcionesBanco, 80, 15);
                a.slidePanelUp(opcionesCronogramas, 80, 15);
                pDocentes = false;
                pEmpresas = false;
                pProyectos = false;
                pCronogramas = false;
                dDocentes = false;
                dEmpresas = false;
                dProyectos = false;
                dCronogramas = false;
                pResidentes = false;
                dResidentes = false;
                break;
            case 2:
                a.slidePanelUp(opcionesEmpresas, 80, 15);
                a.slidePanelUp(opcionesBanco, 80, 15);
                a.slidePanelUp(opcionesCronogramas, 80, 15);
                pEmpresas = false;
                pProyectos = false;
                pCronogramas = false;
                dEmpresas = false;
                dProyectos = false;
                dCronogramas = false;
                pDocentes = false;
                dDocentes = false;

                break;
            case 3:
                a.slidePanelUp(opcionesBanco, 80, 15);
                a.slidePanelUp(opcionesCronogramas, 80, 15);
                pProyectos = false;
                dEmpresas = false;
                pEmpresas = false;

                pCronogramas = false;
                dProyectos = false;
                dCronogramas = false;
                break;
            case 4:
                a.slidePanelUp(opcionesCronogramas, 80, 15);
                pCronogramas = false;
                dCronogramas = false;
                dProyectos = false;
                pProyectos = false;

                break;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelOpciones = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        lblOpciones = new javax.swing.JLabel();
        Icon1 = new javax.swing.JLabel();
        OpcionesResidente = new javax.swing.JPanel();
        Icon2 = new javax.swing.JLabel();
        lblResidentes = new javax.swing.JLabel();
        opcionesDocente = new javax.swing.JPanel();
        Icon3 = new javax.swing.JLabel();
        lblDocentes = new javax.swing.JLabel();
        opcionesEmpresas = new javax.swing.JPanel();
        Icon4 = new javax.swing.JLabel();
        lblEmpresas = new javax.swing.JLabel();
        opcionesBanco = new javax.swing.JPanel();
        Icon5 = new javax.swing.JLabel();
        lblProyectos = new javax.swing.JLabel();
        opcionesCronogramas = new javax.swing.JPanel();
        lblCronogramas = new javax.swing.JLabel();
        Icon6 = new javax.swing.JLabel();
        opcionesExpediente = new javax.swing.JPanel();
        lblExpedientes = new javax.swing.JLabel();
        Icon7 = new javax.swing.JLabel();
        panelExpedientes = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        panelGenerarReportes = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabelOficios = new javax.swing.JLabel();
        panelProyectos = new javax.swing.JPanel();
        lblGestionarProyectos = new javax.swing.JLabel();
        panelEmpresas = new javax.swing.JPanel();
        lblControlEmpresas = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        panelDocentes = new javax.swing.JPanel();
        lblControlDocentes = new javax.swing.JLabel();
        panelResidentes = new javax.swing.JPanel();
        lblControlEstudiantes1 = new javax.swing.JLabel();
        lblControlEstudiantes = new javax.swing.JLabel();
        lblDesplegar = new javax.swing.JLabel();
        lblInicio = new javax.swing.JLabel();
        btnCerrarSesion = new javax.swing.JButton();
        lblCerrarSesion = new javax.swing.JLabel();
        fondoGris = new javax.swing.JLabel();
        textoBienvenido1 = new javax.swing.JLabel();
        logoITO = new javax.swing.JLabel();
        textoBienvenido = new javax.swing.JLabel();
        logoITO1 = new javax.swing.JLabel();
        lblMenu = new javax.swing.JLabel();
        lblError = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        logoSistema = new javax.swing.JLabel();
        SIGREPRO = new javax.swing.JLabel();
        fondoNaranja = new javax.swing.JLabel();
        fondoBlanco = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().setLayout(null);

        panelOpciones.setLayout(null);

        jSeparator1.setBackground(new java.awt.Color(55, 55, 55));
        jSeparator1.setForeground(new java.awt.Color(55, 55, 55));
        jSeparator1.setOpaque(true);
        panelOpciones.add(jSeparator1);
        jSeparator1.setBounds(0, 62, 390, 3);

        jSeparator2.setBackground(new java.awt.Color(55, 55, 55));
        jSeparator2.setForeground(new java.awt.Color(55, 55, 55));
        jSeparator2.setOpaque(true);
        panelOpciones.add(jSeparator2);
        jSeparator2.setBounds(0, 122, 390, 3);

        lblOpciones.setBackground(new java.awt.Color(255, 255, 255));
        lblOpciones.setFont(new java.awt.Font("MS UI Gothic", 3, 38)); // NOI18N
        lblOpciones.setForeground(new java.awt.Color(184, 183, 183));
        lblOpciones.setText("Menú de opciones");
        panelOpciones.add(lblOpciones);
        lblOpciones.setBounds(60, 80, 310, 39);
        panelOpciones.add(Icon1);
        Icon1.setBounds(25, 170, 40, 40);

        OpcionesResidente.setBackground(new java.awt.Color(51, 51, 51));
        OpcionesResidente.setMinimumSize(new java.awt.Dimension(190, 60));
        OpcionesResidente.setPreferredSize(new java.awt.Dimension(190, 60));
        OpcionesResidente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                OpcionesResidenteMouseEntered(evt);
            }
        });
        OpcionesResidente.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        OpcionesResidente.add(Icon2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 40, 40));

        lblResidentes.setBackground(new java.awt.Color(90, 90, 90));
        lblResidentes.setFont(new java.awt.Font("MS UI Gothic", 0, 25)); // NOI18N
        lblResidentes.setForeground(new java.awt.Color(255, 255, 255));
        lblResidentes.setText("Residentes");
        lblResidentes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblResidentesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblResidentesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblResidentesMouseExited(evt);
            }
        });
        OpcionesResidente.add(lblResidentes, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 10, 140, 40));

        panelOpciones.add(OpcionesResidente);
        OpcionesResidente.setBounds(25, 220, 240, 60);

        opcionesDocente.setBackground(new java.awt.Color(51, 51, 51));
        opcionesDocente.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        opcionesDocente.add(Icon3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 40, 40));

        lblDocentes.setBackground(new java.awt.Color(90, 90, 90));
        lblDocentes.setFont(new java.awt.Font("MS UI Gothic", 0, 25)); // NOI18N
        lblDocentes.setForeground(new java.awt.Color(255, 255, 255));
        lblDocentes.setText("Docentes");
        lblDocentes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDocentesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblDocentesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblDocentesMouseExited(evt);
            }
        });
        opcionesDocente.add(lblDocentes, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 10, 140, 40));

        panelOpciones.add(opcionesDocente);
        opcionesDocente.setBounds(25, 280, 240, 60);

        opcionesEmpresas.setBackground(new java.awt.Color(51, 51, 51));
        opcionesEmpresas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                opcionesEmpresasMouseEntered(evt);
            }
        });
        opcionesEmpresas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        opcionesEmpresas.add(Icon4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 40, 40));

        lblEmpresas.setBackground(new java.awt.Color(90, 90, 90));
        lblEmpresas.setFont(new java.awt.Font("MS UI Gothic", 0, 25)); // NOI18N
        lblEmpresas.setForeground(new java.awt.Color(255, 255, 255));
        lblEmpresas.setText("Empresas");
        lblEmpresas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEmpresasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblEmpresasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblEmpresasMouseExited(evt);
            }
        });
        opcionesEmpresas.add(lblEmpresas, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 10, 140, 40));

        panelOpciones.add(opcionesEmpresas);
        opcionesEmpresas.setBounds(25, 340, 240, 60);

        opcionesBanco.setBackground(new java.awt.Color(51, 51, 51));
        opcionesBanco.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        opcionesBanco.add(Icon5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 40, 40));

        lblProyectos.setBackground(new java.awt.Color(90, 90, 90));
        lblProyectos.setFont(new java.awt.Font("MS UI Gothic", 0, 25)); // NOI18N
        lblProyectos.setForeground(new java.awt.Color(255, 255, 255));
        lblProyectos.setText("Proyectos");
        lblProyectos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblProyectosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblProyectosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblProyectosMouseExited(evt);
            }
        });
        opcionesBanco.add(lblProyectos, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 10, 220, 40));

        panelOpciones.add(opcionesBanco);
        opcionesBanco.setBounds(25, 400, 280, 60);

        opcionesCronogramas.setBackground(new java.awt.Color(51, 51, 51));
        opcionesCronogramas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCronogramas.setBackground(new java.awt.Color(90, 90, 90));
        lblCronogramas.setFont(new java.awt.Font("MS UI Gothic", 0, 25)); // NOI18N
        lblCronogramas.setForeground(new java.awt.Color(255, 255, 255));
        lblCronogramas.setText("Generar PDF");
        lblCronogramas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCronogramasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCronogramasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCronogramasMouseExited(evt);
            }
        });
        opcionesCronogramas.add(lblCronogramas, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 10, 150, 40));
        opcionesCronogramas.add(Icon6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 40, 40));

        panelOpciones.add(opcionesCronogramas);
        opcionesCronogramas.setBounds(25, 460, 240, 60);

        opcionesExpediente.setBackground(new java.awt.Color(51, 51, 51));
        opcionesExpediente.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblExpedientes.setBackground(new java.awt.Color(90, 90, 90));
        lblExpedientes.setFont(new java.awt.Font("MS UI Gothic", 0, 25)); // NOI18N
        lblExpedientes.setForeground(new java.awt.Color(255, 255, 255));
        lblExpedientes.setText("Expedientes");
        lblExpedientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblExpedientesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblExpedientesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblExpedientesMouseExited(evt);
            }
        });
        opcionesExpediente.add(lblExpedientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 10, 140, 40));
        opcionesExpediente.add(Icon7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 40, 40));

        panelOpciones.add(opcionesExpediente);
        opcionesExpediente.setBounds(25, 520, 200, 80);

        panelExpedientes.setBackground(new java.awt.Color(102, 102, 102));
        panelExpedientes.setPreferredSize(new java.awt.Dimension(221, 81));
        panelExpedientes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setToolTipText("");
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });
        panelExpedientes.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 210, 40));

        jLabel17.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 51));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setToolTipText("");
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
        });
        panelExpedientes.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 210, 40));

        panelOpciones.add(panelExpedientes);
        panelExpedientes.setBounds(65, 580, 200, 80);

        panelGenerarReportes.setBackground(new java.awt.Color(102, 102, 102));
        panelGenerarReportes.setPreferredSize(new java.awt.Dimension(221, 81));
        panelGenerarReportes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Generar Acta de Calificacion");
        jLabel14.setToolTipText("");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });
        panelGenerarReportes.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 210, 40));

        jLabelOficios.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabelOficios.setForeground(new java.awt.Color(51, 51, 51));
        jLabelOficios.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelOficios.setText("Generar Oficios Docentes");
        jLabelOficios.setToolTipText("");
        jLabelOficios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelOficiosMouseClicked(evt);
            }
        });
        panelGenerarReportes.add(jLabelOficios, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 210, 40));

        panelOpciones.add(panelGenerarReportes);
        panelGenerarReportes.setBounds(65, 520, 220, 80);

        panelProyectos.setBackground(new java.awt.Color(102, 102, 102));
        panelProyectos.setPreferredSize(new java.awt.Dimension(221, 81));
        panelProyectos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblGestionarProyectos.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblGestionarProyectos.setForeground(new java.awt.Color(51, 51, 51));
        lblGestionarProyectos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGestionarProyectos.setText("Gestionar Proyectos");
        lblGestionarProyectos.setToolTipText("");
        lblGestionarProyectos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblGestionarProyectosMouseClicked(evt);
            }
        });
        panelProyectos.add(lblGestionarProyectos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 210, 40));

        panelOpciones.add(panelProyectos);
        panelProyectos.setBounds(65, 460, 200, 80);

        panelEmpresas.setBackground(new java.awt.Color(102, 102, 102));
        panelEmpresas.setPreferredSize(new java.awt.Dimension(221, 81));
        panelEmpresas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblControlEmpresas.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblControlEmpresas.setForeground(new java.awt.Color(51, 51, 51));
        lblControlEmpresas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblControlEmpresas.setText("Control de Empresas");
        lblControlEmpresas.setToolTipText("");
        lblControlEmpresas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblControlEmpresasMouseClicked(evt);
            }
        });
        panelEmpresas.add(lblControlEmpresas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 40));

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setToolTipText("");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        panelEmpresas.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 210, 40));

        panelOpciones.add(panelEmpresas);
        panelEmpresas.setBounds(65, 400, 200, 80);

        panelDocentes.setBackground(new java.awt.Color(102, 102, 102));
        panelDocentes.setPreferredSize(new java.awt.Dimension(221, 81));
        panelDocentes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblControlDocentes.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblControlDocentes.setForeground(new java.awt.Color(51, 51, 51));
        lblControlDocentes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblControlDocentes.setText("Control de Docentes");
        lblControlDocentes.setToolTipText("");
        lblControlDocentes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblControlDocentesMouseClicked(evt);
            }
        });
        panelDocentes.add(lblControlDocentes, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 40));

        panelOpciones.add(panelDocentes);
        panelDocentes.setBounds(65, 340, 200, 80);

        panelResidentes.setBackground(new java.awt.Color(102, 102, 102));
        panelResidentes.setPreferredSize(new java.awt.Dimension(221, 81));
        panelResidentes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelResidentesMouseExited(evt);
            }
        });
        panelResidentes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblControlEstudiantes1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblControlEstudiantes1.setForeground(new java.awt.Color(51, 51, 51));
        lblControlEstudiantes1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblControlEstudiantes1.setText("Control de Expedientes");
        lblControlEstudiantes1.setToolTipText("");
        lblControlEstudiantes1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblControlEstudiantes1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblControlEstudiantes1MouseEntered(evt);
            }
        });
        panelResidentes.add(lblControlEstudiantes1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 200, 40));

        lblControlEstudiantes.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblControlEstudiantes.setForeground(new java.awt.Color(51, 51, 51));
        lblControlEstudiantes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblControlEstudiantes.setText("Control de Residentes");
        lblControlEstudiantes.setToolTipText("");
        lblControlEstudiantes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblControlEstudiantesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblControlEstudiantesMouseEntered(evt);
            }
        });
        panelResidentes.add(lblControlEstudiantes, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 40));

        panelOpciones.add(panelResidentes);
        panelResidentes.setBounds(65, 280, 200, 79);

        lblDesplegar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/menu.png"))); // NOI18N
        lblDesplegar.setEnabled(false);
        lblDesplegar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDesplegarMouseClicked(evt);
            }
        });
        panelOpciones.add(lblDesplegar);
        lblDesplegar.setBounds(10, 12, 50, 50);

        lblInicio.setBackground(new java.awt.Color(90, 90, 90));
        lblInicio.setFont(new java.awt.Font("MS UI Gothic", 0, 25)); // NOI18N
        lblInicio.setForeground(new java.awt.Color(255, 255, 255));
        lblInicio.setText("Inicio");
        lblInicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblInicioMouseEntered(evt);
            }
        });
        panelOpciones.add(lblInicio);
        lblInicio.setBounds(100, 170, 140, 40);

        btnCerrarSesion.setBackground(new java.awt.Color(204, 0, 0));
        btnCerrarSesion.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        btnCerrarSesion.setForeground(new java.awt.Color(255, 255, 255));
        btnCerrarSesion.setText("Cerrar Sesión.");
        btnCerrarSesion.setBorder(null);
        btnCerrarSesion.setFocusable(false);
        btnCerrarSesion.setOpaque(true);
        btnCerrarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCerrarSesionMouseEntered(evt);
            }
        });
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });
        panelOpciones.add(btnCerrarSesion);
        btnCerrarSesion.setBounds(50, 630, 250, 50);

        lblCerrarSesion.setBackground(new java.awt.Color(204, 0, 0));
        lblCerrarSesion.setFont(new java.awt.Font("Segoe UI Black", 1, 48)); // NOI18N
        lblCerrarSesion.setForeground(new java.awt.Color(255, 255, 255));
        lblCerrarSesion.setOpaque(true);
        panelOpciones.add(lblCerrarSesion);
        lblCerrarSesion.setBounds(320, 630, 50, 50);

        fondoGris.setBackground(new java.awt.Color(51, 51, 51));
        fondoGris.setOpaque(true);
        fondoGris.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fondoGrisMouseClicked(evt);
            }
        });
        panelOpciones.add(fondoGris);
        fondoGris.setBounds(0, 20, 400, 673);

        getContentPane().add(panelOpciones);
        panelOpciones.setBounds(-380, 0, 380, 760);

        textoBienvenido1.setFont(new java.awt.Font("Microsoft Himalaya", 1, 65)); // NOI18N
        textoBienvenido1.setText("Bienvenido al Sistema de Gestión ");
        getContentPane().add(textoBienvenido1);
        textoBienvenido1.setBounds(330, 60, 660, 66);
        getContentPane().add(logoITO);
        logoITO.setBounds(470, 230, 300, 300);

        textoBienvenido.setFont(new java.awt.Font("Microsoft Himalaya", 1, 65)); // NOI18N
        textoBienvenido.setText("de Residencias Profesionales");
        getContentPane().add(textoBienvenido);
        textoBienvenido.setBounds(370, 110, 570, 66);
        getContentPane().add(logoITO1);
        logoITO1.setBounds(735, 240, 300, 300);

        lblMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/menu.png"))); // NOI18N
        lblMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMenuMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblMenuMouseEntered(evt);
            }
        });
        getContentPane().add(lblMenu);
        lblMenu.setBounds(10, 20, 50, 50);

        lblError.setBackground(new java.awt.Color(204, 204, 204));
        lblError.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblError.setForeground(new java.awt.Color(255, 0, 0));
        lblError.setText("Termine su actividad o cancele para acceder a otra");
        getContentPane().add(lblError);
        lblError.setBounds(10, 660, 590, 50);

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setForeground(new java.awt.Color(28, 27, 26));
        jLabel3.setOpaque(true);
        getContentPane().add(jLabel3);
        jLabel3.setBounds(0, 0, 0, 0);
        getContentPane().add(logoSistema);
        logoSistema.setBounds(1200, 590, 120, 100);

        SIGREPRO.setBackground(new java.awt.Color(255, 255, 255));
        SIGREPRO.setFont(new java.awt.Font("Microsoft Tai Le", 3, 38)); // NOI18N
        SIGREPRO.setText("SIGERPRO");
        getContentPane().add(SIGREPRO);
        SIGREPRO.setBounds(970, 620, 210, 40);

        fondoNaranja.setBackground(new java.awt.Color(255, 153, 51));
        fondoNaranja.setOpaque(true);
        getContentPane().add(fondoNaranja);
        fondoNaranja.setBounds(950, 580, 400, 110);

        fondoBlanco.setBackground(new java.awt.Color(204, 204, 204));
        fondoBlanco.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        fondoBlanco.setOpaque(true);
        fondoBlanco.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fondoBlancoMouseClicked(evt);
            }
        });
        getContentPane().add(fondoBlanco);
        fondoBlanco.setBounds(0, -2, 1366, 770);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        this.dispose();
        IniciarSesion iniS = new IniciarSesion();
        iniS.setVisible(true);
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    public void desactivarPaneles() {
        panelDocentes.setVisible(false);
        panelResidentes.setVisible(false);
        panelEmpresas.setVisible(false);
        panelProyectos.setVisible(false);
        panelGenerarReportes.setVisible(false);
        panelExpedientes.setVisible(false);
    }


    private void btnCerrarSesionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCerrarSesionMouseEntered
        btnCerrarSesion.setFocusable(true);        // TODO add your handling code here:
    }//GEN-LAST:event_btnCerrarSesionMouseEntered

    private void fondoBlancoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fondoBlancoMouseClicked
    }//GEN-LAST:event_fondoBlancoMouseClicked

    private void fondoGrisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fondoGrisMouseClicked

    }//GEN-LAST:event_fondoGrisMouseClicked

    private void lblMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMenuMouseClicked
        if (!estadoFrame) {
            a.slidePanelRight(panelOpciones, 380, 10);
        } else {
            lblError.setVisible(true); // Mostrar el label

            // Temporizador que lo oculta después de 10 segundos
            Timer timer = new Timer(10000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    lblError.setVisible(false); // Ocultar
                }
            });

            timer.setRepeats(false); // Solo una vez
            timer.start();
        }
    }//GEN-LAST:event_lblMenuMouseClicked

    private void lblDesplegarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDesplegarMouseClicked
        a.slidePanelLeft(panelOpciones, 380, 10);        // TODO add your handling code here:
    }//GEN-LAST:event_lblDesplegarMouseClicked

    private void lblInicioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInicioMouseEntered

    }//GEN-LAST:event_lblInicioMouseEntered

    private void lblResidentesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblResidentesMouseEntered
        animart(lblResidentes);
    }//GEN-LAST:event_lblResidentesMouseEntered

    private void lblResidentesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblResidentesMouseExited
        desanimart(lblResidentes);


    }//GEN-LAST:event_lblResidentesMouseExited

    private void lblDocentesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDocentesMouseEntered

        animart(lblDocentes);

    }//GEN-LAST:event_lblDocentesMouseEntered

    private void lblDocentesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDocentesMouseExited
        desanimart(lblDocentes);


    }//GEN-LAST:event_lblDocentesMouseExited

    public void clicked(JPanel panel) {
        panel.setVisible(true);
    }

    public void clicked2(JPanel panel) {
        panel.setVisible(false);
    }
    private void lblResidentesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblResidentesMouseClicked
        if (!pResidentes) {
            clicked(panelResidentes); //visible
            pResidentes = true;
            deslizarPaneles(1);
        } else {
            clicked2(panelResidentes);
            pResidentes = false;
            acomodarPaneles(1);
        }
    }//GEN-LAST:event_lblResidentesMouseClicked

    private void lblDocentesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDocentesMouseClicked
        if (!pDocentes) {
            clicked(panelDocentes);
            pDocentes = true;
            deslizarPaneles(2);
        } else {
            clicked2(panelDocentes);
            pDocentes = false;
            acomodarPaneles(2);
        }
    }//GEN-LAST:event_lblDocentesMouseClicked

    private void lblEmpresasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEmpresasMouseClicked
        if (!pEmpresas) {
            clicked(panelEmpresas);
            pEmpresas = true;
            deslizarPaneles(3);
        } else {
            clicked2(panelEmpresas);
            pEmpresas = false;
            acomodarPaneles(3);
        }
    }//GEN-LAST:event_lblEmpresasMouseClicked

    private void lblEmpresasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEmpresasMouseEntered
        // TODO add your handling code here:

        animart(lblEmpresas);
    }//GEN-LAST:event_lblEmpresasMouseEntered
    public void animart(JLabel label) {
        a.expandLabel(label);
        label.setOpaque(true);

    }

    public void desanimart(JLabel label) {
        a.shrinkLabel(label);
        label.setOpaque(false);
    }
    private void lblEmpresasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEmpresasMouseExited

        desanimart(lblEmpresas);


    }//GEN-LAST:event_lblEmpresasMouseExited

    private void lblProyectosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProyectosMouseClicked
        if (!pProyectos) {
            clicked(panelProyectos);
            pProyectos = true;
            deslizarPaneles(4);
        } else {
            clicked2(panelProyectos);
            pProyectos = false;
            acomodarPaneles(4);
        }
    }//GEN-LAST:event_lblProyectosMouseClicked

    private void lblProyectosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProyectosMouseEntered

    }//GEN-LAST:event_lblProyectosMouseEntered

    private void lblProyectosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProyectosMouseExited
        // TODO add your handling code here:


    }//GEN-LAST:event_lblProyectosMouseExited

    private void lblCronogramasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCronogramasMouseClicked
        if (!pCronogramas) {
            clicked(panelGenerarReportes);
            pCronogramas = true;
            deslizarPaneles(5);
        } else {
            clicked2(panelGenerarReportes);
            pCronogramas = false;
            acomodarPaneles(5);
        }
    }//GEN-LAST:event_lblCronogramasMouseClicked

    private void lblCronogramasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCronogramasMouseEntered
        // TODO add your handling code here:


    }//GEN-LAST:event_lblCronogramasMouseEntered

    private void lblCronogramasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCronogramasMouseExited
        // TODO add your handling code here:


    }//GEN-LAST:event_lblCronogramasMouseExited

    private void lblExpedientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExpedientesMouseClicked
        // TODO add your handling code here:
//        panelExpedientes.setVisible(true);
//        deslizarPaneles(6);
//        pExpedientes=true;
    }//GEN-LAST:event_lblExpedientesMouseClicked

    private void lblExpedientesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExpedientesMouseEntered
        // TODO add your handling code here:


    }//GEN-LAST:event_lblExpedientesMouseEntered

    private void lblExpedientesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExpedientesMouseExited
        // TODO add your handling code here:


    }//GEN-LAST:event_lblExpedientesMouseExited

    private void lblControlEstudiantes1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblControlEstudiantes1MouseClicked
        // TODO add your handling code here:
        if (ventanaExp == null || !ventanaExp.isDisplayable()) {
        ventanaExp = new ControlExpedientes();
    }
    abrirVentanaUnica("controlExpedientes", ventanaExp);
    ventanaExp.contrasena=contrasena;
    }//GEN-LAST:event_lblControlEstudiantes1MouseClicked

    private void lblControlDocentesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblControlDocentesMouseClicked
        // TODO add your handling code here:
        if (ventanaDocentes == null || !ventanaDocentes.isDisplayable()) {
        ventanaDocentes = new controlDocentes();
    }
    abrirVentanaUnica("controlDocentes", ventanaDocentes);
    }//GEN-LAST:event_lblControlDocentesMouseClicked

    private void lblControlEstudiantes1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblControlEstudiantes1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblControlEstudiantes1MouseEntered

    private void OpcionesResidenteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OpcionesResidenteMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_OpcionesResidenteMouseEntered

    private void opcionesEmpresasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_opcionesEmpresasMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_opcionesEmpresasMouseEntered

    private void panelResidentesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelResidentesMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_panelResidentesMouseExited

    private void lblControlEmpresasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblControlEmpresasMouseClicked
        // TODO add your handling code here:
        if (ventanaEmpresas == null || !ventanaEmpresas.isDisplayable()) {
        ventanaEmpresas = new controlEmpresas();
    }
    abrirVentanaUnica("controlEmpresas", ventanaEmpresas);
    }//GEN-LAST:event_lblControlEmpresasMouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        // TODO add your handling code here:
        if (actaCalificacion == null || !actaCalificacion.isDisplayable()) {
        actaCalificacion = new ActaCalificacion();
    }
    abrirVentanaUnica("actaCalificacion", actaCalificacion);
    }//GEN-LAST:event_jLabel14MouseClicked

    private void jLabelOficiosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelOficiosMouseClicked
        if (oficios == null || !oficios.isDisplayable()) {
        oficios = new Oficios();
    }
    abrirVentanaUnica("oficios", oficios);
    oficios.setLocation(233, 90);
    }//GEN-LAST:event_jLabelOficiosMouseClicked

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel16MouseClicked

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel17MouseClicked

    private void lblGestionarProyectosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGestionarProyectosMouseClicked
        if (bancoProyectos == null || !bancoProyectos.isDisplayable()) {
        bancoProyectos = new BancoDeProyectos();
    }
    abrirVentanaUnica("bancoProyectos", bancoProyectos);
       bancoProyectos.setLocation(0,15);
    }//GEN-LAST:event_lblGestionarProyectosMouseClicked

    private void lblMenuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMenuMouseEntered
        if (!estadoFrame) {
            lblMenu.setEnabled(true);// TODO add your handling code here:
        }
    }//GEN-LAST:event_lblMenuMouseEntered

    private void lblControlEstudiantesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblControlEstudiantesMouseClicked
        // TODO add your handling code here:
        if (controlEstudiantes == null || !controlEstudiantes.isDisplayable()) {
        controlEstudiantes = new controlEstudiantes();
    }
    abrirVentanaUnica("controlEstudiantes", controlEstudiantes);
    }//GEN-LAST:event_lblControlEstudiantesMouseClicked

    private void lblControlEstudiantesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblControlEstudiantesMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblControlEstudiantesMouseEntered

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
            java.util.logging.Logger.getLogger(PanelPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PanelPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PanelPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PanelPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PanelPrincipal().setVisible(true);
            }
        });
    }

    public void Instancias(JFrame frame){
        a.animarEntrada(frame);
        frame.setVisible(true);
        estadoFrame = true;
        a.slidePanelLeft(panelOpciones, 380, 15);
        lblMenu.setEnabled(false);
    }
    public void adaptarImagenes() {
        JLabel[] labels = {Icon1, Icon2, Icon3, Icon4, Icon5, Icon6, Icon7, logoITO, logoSistema, lblCerrarSesion, fondoBlanco};
        String[] rutas = {"Imagenes/inicio.png", "Imagenes/residente.png", "Imagenes/docente.png", "Imagenes/proyectos.png",
            "Imagenes/empresa.png", "Imagenes/cronograma.png", "Imagenes/expediente.png", "Imagenes/ITO.png",
            "Imagenes/Imagen1.png", "Imagenes/cerrarSesion.png", "Imagenes/FONDOPRINCIPAL.jpg"};

        for (int i = 0; i < labels.length; i++) {
            a.ajustarImagenALabel(labels[i], rutas[i]);

        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Icon1;
    private javax.swing.JLabel Icon2;
    private javax.swing.JLabel Icon3;
    private javax.swing.JLabel Icon4;
    private javax.swing.JLabel Icon5;
    private javax.swing.JLabel Icon6;
    private javax.swing.JLabel Icon7;
    private javax.swing.JPanel OpcionesResidente;
    private javax.swing.JLabel SIGREPRO;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JLabel fondoBlanco;
    private javax.swing.JLabel fondoGris;
    private javax.swing.JLabel fondoNaranja;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelOficios;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblCerrarSesion;
    private javax.swing.JLabel lblControlDocentes;
    private javax.swing.JLabel lblControlEmpresas;
    private javax.swing.JLabel lblControlEstudiantes;
    private javax.swing.JLabel lblControlEstudiantes1;
    private javax.swing.JLabel lblCronogramas;
    private javax.swing.JLabel lblDesplegar;
    private javax.swing.JLabel lblDocentes;
    private javax.swing.JLabel lblEmpresas;
    private javax.swing.JLabel lblError;
    private javax.swing.JLabel lblExpedientes;
    private javax.swing.JLabel lblGestionarProyectos;
    private javax.swing.JLabel lblInicio;
    private javax.swing.JLabel lblMenu;
    private javax.swing.JLabel lblOpciones;
    private javax.swing.JLabel lblProyectos;
    private javax.swing.JLabel lblResidentes;
    private javax.swing.JLabel logoITO;
    private javax.swing.JLabel logoITO1;
    private javax.swing.JLabel logoSistema;
    private javax.swing.JPanel opcionesBanco;
    private javax.swing.JPanel opcionesCronogramas;
    private javax.swing.JPanel opcionesDocente;
    private javax.swing.JPanel opcionesEmpresas;
    private javax.swing.JPanel opcionesExpediente;
    private javax.swing.JPanel panelDocentes;
    private javax.swing.JPanel panelEmpresas;
    private javax.swing.JPanel panelExpedientes;
    private javax.swing.JPanel panelGenerarReportes;
    private javax.swing.JPanel panelOpciones;
    private javax.swing.JPanel panelProyectos;
    private javax.swing.JPanel panelResidentes;
    private javax.swing.JLabel textoBienvenido;
    private javax.swing.JLabel textoBienvenido1;
    // End of variables declaration//GEN-END:variables

}
