/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.NoSuchProviderException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author es982
 */


public class ControladorEmails {
    public static String codigo;
    private String emailFrom = "sigerprotecnm@gmail.com";
    private String password = "ankaropzbhnaeuat";
    private String asunto;
    private String contenido;

    private final Properties mProperties;
    private Session mSession;
    private MimeMessage mCorreo;

    public ControladorEmails() {
        mProperties = new Properties();
    }

    public void crearCorreoCodigoRecuperacion(String emailTo) {
        codigo = generarToken();
        asunto = "Codigo de Recuperacion";
        contenido = "Su codigo de recuperacion es: " + codigo;

        mProperties.setProperty("mail.smtp.host", "smtp.gmail.com");
        mProperties.setProperty("mail.smtp.starttls.enable", "true"); // CORREGIDO
        mProperties.setProperty("mail.smtp.port", "587");
        mProperties.setProperty("mail.smtp.user", emailFrom);
        mProperties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        mProperties.setProperty("mail.smtp.auth", "true");

        mSession = Session.getInstance(mProperties);

        mCorreo = new MimeMessage(mSession);
        try {
            mCorreo.setFrom(new InternetAddress(emailFrom));
            mCorreo.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            mCorreo.setSubject(asunto);
            mCorreo.setText(contenido, "UTF-8"); // O usa setContent si es HTML
        } catch (MessagingException ex) {
            Logger.getLogger(ControladorEmails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enviarEmail() {
        try {
            Transport mTransport = mSession.getTransport("smtp");
            mTransport.connect(emailFrom, password);
            mTransport.sendMessage(mCorreo, mCorreo.getRecipients(Message.RecipientType.TO));
            mTransport.close();
            JOptionPane.showMessageDialog(null,"Correo Enviado");
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(ControladorEmails.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(ControladorEmails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String generarToken() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String codigo = "";
        for (int i = 0; i < 6; i++) {
            int aux = (int) (Math.random() * caracteres.length());
            codigo += caracteres.charAt(aux);
        }
        System.out.println("Codigo: " + codigo);
        return codigo;
    }
}
