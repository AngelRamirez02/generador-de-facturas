/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package correo;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

/**
 *
 * @author Erik Jose
 */
public class Correo {

    private String correoDeOrigen ;
    private String correoDeDestino;
    private String asunto;
    private String mensajeDeTexto;
    private String contraseña16Digitos;
    private String rutaPdf;

    public Correo(String destino, String rutaPDF) {
        this.correoDeOrigen = "";
        this.correoDeDestino = destino;
        this.asunto = "Factura del mes";
        this.mensajeDeTexto = "Factura del mes";
        this.contraseña16Digitos = "";
        this.rutaPdf = rutaPDF;
    }

    public void envioDeCorreos() {
        envioDeMensajes();
    }

private void envioDeMensajes() {
    try {
        Properties p = new Properties();
        p.put("mail.smtp.host", "smtp.gmail.com");
        p.setProperty("mail.smtp.starttls.enable", "true");
        p.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        p.setProperty("mail.smtp.port", "587");
        p.setProperty("mail.smtp.user", correoDeOrigen);
        p.setProperty("mail.smtp.auth", "true");
        p.setProperty("mail.smtp.ssl.protocols", "TLSv1.2"); // Forzar TLS 1.2

        Session s = Session.getInstance(p); // Cambiado a getInstance
        
        //Cuerpo del mensaje
        BodyPart titulo = new MimeBodyPart();
        titulo.setText("Titulo del correo");
        
        BodyPart contenido = new MimeBodyPart();
        contenido.setText("Este será el cuerpo del mensaje");

        //Adjuntar el pdf
        BodyPart PdfAdjunto = new MimeBodyPart();
        PdfAdjunto.setDataHandler(new DataHandler(new FileDataSource(rutaPdf)));
        PdfAdjunto.setFileName("Factura.pdf");
        
        //Crear el cuerpo con el mensaje y archivo
        MimeMultipart m = new MimeMultipart();
        m.addBodyPart(titulo);
        m.addBodyPart(contenido);
        m.addBodyPart(PdfAdjunto);
        
        MimeMessage mensaje = new MimeMessage(s);
        mensaje.setFrom(new InternetAddress(correoDeOrigen));
        mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(correoDeDestino));
        mensaje.setSubject(asunto);
        mensaje.setContent(m);

        Transport t = s.getTransport("smtp");
        t.connect(correoDeOrigen, contraseña16Digitos);
        t.sendMessage(mensaje, mensaje.getAllRecipients());
        t.close();

        JOptionPane.showMessageDialog(null, "Mensaje enviado");
    } catch (MessagingException e) {
        e.printStackTrace();
    }
}

    public static void main(String[] args) {
        Correo prueba = new Correo("","");
        prueba.envioDeCorreos();
    }
}
