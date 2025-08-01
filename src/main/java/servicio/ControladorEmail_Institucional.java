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

public class ControladorEmail_Institucional {
    public static String codigo;
    private String emailFrom = "sigerprotecnm@gmail.com"; // Tu cuenta de correo
    private String password = "ankaropzbhnaeuat"; // Tu contraseña
    private String asunto;
    private String contenido;

    private final Properties mProperties;
    private Session mSession;
    private MimeMessage mCorreo;

    // Cambia el host y el puerto dependiendo de la institución
    private String emailHost = "smtp.itoaxaca.edu.mx";  // Aquí pones el host SMTP de la institución
    private String emailPort = "587";  // Puerto para TLS (puede cambiar dependiendo de la configuración)

    public ControladorEmail_Institucional() {
        mProperties = new Properties();
    }

    public void crearCorreoNotifiacion(String emailTo) {
        codigo = generarToken();
        asunto = "Codigo de Recuperacion";
        contenido = "Su codigo de recuperacion es: " + codigo;

        // Configuración del servidor SMTP de la institución
        mProperties.setProperty("mail.smtp.host", emailHost);  // Usar el host SMTP institucional
        mProperties.setProperty("mail.smtp.starttls.enable", "true");
        mProperties.setProperty("mail.smtp.port", emailPort);
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
            mTransport.connect(emailFrom, password); // Asegúrate de que las credenciales sean correctas
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
