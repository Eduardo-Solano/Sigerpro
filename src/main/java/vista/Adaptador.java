/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author jc389
 */
public class Adaptador {
     private static final Map<JLabel, Timer> timers = new HashMap<>();
    public Adaptador(){
        
    }
     public void ajustarImagenALabel(JLabel label, String rutaRelativa) {
        URL recurso = getClass().getResource("/" + rutaRelativa);

        if (recurso != null) {
            ImageIcon icono = new ImageIcon(recurso);
            Image imagenOriginal = icono.getImage();
            Image imagenEscalada = imagenOriginal.getScaledInstance(
                    label.getWidth(),
                    label.getHeight(),
                    Image.SCALE_SMOOTH
            );
            label.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("❌ No se encontró la imagen: /" + rutaRelativa);
        }
    }
     public void animarEntrada(JFrame frame) {
    frame.setOpacity(0.0f); // Inicia en invisible
    javax.swing.Timer timer = new javax.swing.Timer(10, new java.awt.event.ActionListener() {
        float opacity = 0.0f;

        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            opacity += 0.05f;
            if (opacity >= 1.0f) {
                opacity = 1.0f;
                ((javax.swing.Timer) e.getSource()).stop();
            }
            frame.setOpacity(opacity);
        }
    });
    timer.start();
}
     public void animarSalida(JFrame frame) {
    javax.swing.Timer timer = new javax.swing.Timer(10, new java.awt.event.ActionListener() {
        float opacity = 1.0f;

        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            opacity -= 0.05f;
            if (opacity <= 0.0f) {
                opacity = 0.0f;
                ((javax.swing.Timer) e.getSource()).stop();
                frame.dispose(); // Opcional: cierra el frame después de la animación
            }
            frame.setOpacity(opacity);
        }
    });
    timer.start();
}
     public void slidePanelRight(JPanel panel, int distance, int speed) {
    Timer timer = new Timer(5, null);
    Point start = panel.getLocation();
    final int targetX = start.x + distance;

    timer.addActionListener(new ActionListener() {
        int currentX = start.x;

        @Override
        public void actionPerformed(ActionEvent e) {
            currentX += speed;
            if (currentX >= targetX) {
                currentX = targetX;
                timer.stop();
            }
            panel.setLocation(currentX, start.y);
            panel.getParent().repaint();
        }
    });
    timer.start();
}
     public void slidePanelLeft(JPanel panel, int distance, int speed) {
    Timer timer = new Timer(10, null); // intervalo más largo = animación más fluida
    Point start = panel.getLocation();
    final int targetX = start.x - distance;
    

    timer.addActionListener(new ActionListener() {
        int currentX = start.x;

        @Override
        public void actionPerformed(ActionEvent e) {
            currentX -= speed;
            if (currentX <= targetX) {
                currentX = targetX;
                timer.stop();
            }
            panel.setLocation(currentX, start.y);
            panel.getParent().repaint();
        }
    });
    timer.start();
}
       private static void stopExistingTimer(JLabel label) {
    Timer existing = timers.get(label);
    if (existing != null && existing.isRunning()) {
        existing.stop();
        timers.remove(label);
    }
}
public static void expandLabel(JLabel label) {
        stopExistingTimer(label);

        // Guardar tamaño original solo una vez
        if (label.getClientProperty("originalBounds") == null) {
            label.putClientProperty("originalBounds", label.getBounds());
        }

        Rectangle original = (Rectangle) label.getClientProperty("originalBounds");

        int targetW = original.width + 50;
        int targetH = original.height + 20;
        int targetX = original.x - (targetW - original.width) / 2;
        int targetY = original.y - (targetH - original.height) / 2;

        Timer timer = new Timer(2, null);
        timers.put(label, timer);

        timer.addActionListener(new ActionListener() {
            int w = label.getWidth();
            int h = label.getHeight();

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean done = true;

                if (w < targetW) {
                    w += 8;
                    done = false;
                }
                if (h < targetH) {
                    h += 4;
                    done = false;
                }

                if (w > targetW) w = targetW;
                if (h > targetH) h = targetH;

                int newX = original.x - (w - original.width) / 2;
                int newY = original.y - (h - original.height) / 2;
                label.setBounds(newX, newY, w, h);

                if (done) {
                    timer.stop();
                    timers.remove(label);
                }
            }
        });

        timer.start();
    }
         
    public static void shrinkLabel(JLabel label) {
        stopExistingTimer(label);

        Rectangle original = (Rectangle) label.getClientProperty("originalBounds");
        if (original == null) return; // seguridad por si no se ha expandido nunca

        Timer timer = new Timer(2, null);
        timers.put(label, timer);

        timer.addActionListener(new ActionListener() {
            int w = label.getWidth();
            int h = label.getHeight();

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean done = true;

                if (w > original.width) {
                    w -= 8;
                    done = false;
                }
                if (h > original.height) {
                    h -= 4;
                    done = false;
                }

                if (w < original.width) w = original.width;
                if (h < original.height) h = original.height;

                int newX = original.x - (w - original.width) / 2;
                int newY = original.y - (h - original.height) / 2;
                label.setBounds(newX, newY, w, h);

                if (done) {
                    timer.stop();
                    timers.remove(label);
                }
            }
        });

        timer.start();
    }
  public void slidePanelDown(JPanel panel, int distance, int speed) {
    Timer timer = new Timer(10, null); // cuanto menor el valor, más fluida la animación
    Point start = panel.getLocation();
    final int targetY = start.y + distance;

    timer.addActionListener(new ActionListener() {
        int currentY = start.y;

        @Override
        public void actionPerformed(ActionEvent e) {
            currentY += speed;
            if (currentY >= targetY) {
                currentY = targetY;
                timer.stop();
            }
            panel.setLocation(start.x, currentY);
            panel.getParent().repaint();
        }
    });

    timer.start();
}

public void slidePanelUp(JPanel panel, int distance, int speed) {
    Timer timer = new Timer(10, null); // menor valor = animación más fluida
    Point start = panel.getLocation();
    final int targetY = start.y - distance;

    timer.addActionListener(new ActionListener() {
        int currentY = start.y;

        @Override
        public void actionPerformed(ActionEvent e) {
            currentY -= speed;
            if (currentY <= targetY) {
                currentY = targetY;
                timer.stop();
            }
            panel.setLocation(start.x, currentY);
            panel.getParent().repaint();
        }
    });

    timer.start();
}


}
