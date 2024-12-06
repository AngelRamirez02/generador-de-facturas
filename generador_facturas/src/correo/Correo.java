/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package correo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
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

    private String correoDeOrigen;
    private String correoDeDestino;
    private String asunto;
    private String contraseña16Digitos;
    private String rutaPdf;

    public Correo(String destino, String rutaPDF) {
        this.correoDeOrigen = "amlob.instituto@gmail.com";
        this.correoDeDestino = destino;
        this.contraseña16Digitos = "vvfh vofj yjku htas";
        this.rutaPdf = rutaPDF;
    }

    public void envioDeCorreos(String apellido, String mensaje) {
        envioDeMensajes(apellido, mensaje);
    }

    private void envioDeMensajes(String apellido, String msj) {
        try {
            // Formatear la fecha en el formato "dd/MM/yyyy"
            LocalDate fechaActual = LocalDate.now();
            // Crear un formato con localización en español
            // Formato para mostrar el mes en letras
            DateTimeFormatter formatoMes = DateTimeFormatter.ofPattern("MMMM");
            String mesEnLetras = fechaActual.format(formatoMes);
            // Obtiene el año actual
            int anioActual = fechaActual.getYear();

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
            titulo.setText("Factura correspondiente a " + mesEnLetras + " del " + anioActual);
            this.asunto = "Factura correspondiente a " + mesEnLetras + " del " + anioActual;
            BodyPart contenido = new MimeBodyPart();
            contenido.setContent(
                    "<html>"
                    + "<body style='margin: 0; padding: 0; background-color: #f4f4f4;'>"
                    + "<table style='width: 100%; background-color: #f4f4f4; padding: 20px;' cellpadding='0' cellspacing='0'>"
                    + "    <tr>"
                    + "        <td align='center'>"
                    + "            <table style='max-width: 600px; background-color: #ffffff; padding: 20px; border: 1px solid #ddd; border-radius: 8px;' cellpadding='0' cellspacing='0'>"
                    + "                <tr>"
                    + "                    <td style='text-align: center;'>"
                    + "                        <h1 style='font-family: Arial, sans-serif; font-size: 24px; color: #2c3e50; margin: 0;'>Factura correspondiente a " + mesEnLetras + " del " + anioActual + "</h1>"
                    + "                    </td>"
                    + "                </tr>"
                    + "                <tr>"
                    + "                    <td style='padding-top: 20px;'>"
                    + "                        <h2 style='font-family: Arial, sans-serif; font-size: 18px; color: #34495e; margin: 0;'>Estimado/a Sr./Sra. " + apellido + ":</h2>"
                    + "                    </td>"
                    + "                </tr>"
                    + "                <tr>"
                    + "                    <td style='padding-top: 10px;'>"
                    + "                        <p style='font-family: Arial, sans-serif; font-size: 16px; color: #333; line-height: 1.6; text-align: justify; margin: 0;'>"
                    + "                            Por este medio, le enviamos la factura correspondiente a "
                    + "                            <span style='font-weight: bold; color: #16a085;'>\"" + msj + mesEnLetras + "\"</span> "
                    + "                        </p>"
                    + "                    </td>"
                    + "                </tr>"
                    + "                <tr>"
                    + "                    <td style='padding-top: 10px;'>"
                    + "                        <p style='font-family: Arial, sans-serif; font-size: 16px; color: #333; line-height: 1.6; text-align: justify; margin: 0;'>"
                    + "                            Adjuntamos a este correo el archivo de la factura en formato PDF para su revisión y resguardo."
                    + "                        </p>"
                    + "                    </td>"
                    + "                </tr>"
                    + "                <tr>"
                    + "                    <td style='padding-top: 10px;'>"
                    + "                        <p style='font-family: Arial, sans-serif; font-size: 16px; color: #333; line-height: 1.6; text-align: justify; margin: 0;'>"
                    + "                            Si tiene alguna pregunta o requiere alguna aclaración sobre este documento, no dude en comunicarse con nosotros respondiendo a este correo."
                    + "                        </p>"
                    + "                    </td>"
                    + "                </tr>"
                    + "                <tr>"
                    + "                    <td style='padding-top: 10px;'>"
                    + "                        <p style='font-family: Arial, sans-serif; font-size: 16px; color: #333; line-height: 1.6; text-align: justify; margin: 0;'>"
                    + "                            Agradecemos su puntual atención y quedamos a su disposición para cualquier duda."
                    + "                        </p>"
                    + "                    </td>"
                    + "                </tr>"
                    + "                <tr>"
                    + "                    <td style='padding-top: 20px; text-align: center;'>"
                    + "                        <footer style='font-family: Arial, sans-serif; font-size: 14px; color: #555;'>"
                    + "                            <p style='margin: 0;'>Atentamente:</p>"
                    + "                            <p style='margin: 0;'>Servicio Administrativo del:</p>"
                    + "                            <p style='margin: 0; font-weight: bold;'>Instituto Andres Manuel Lopez Obrador</p>"
                    + "                        </footer>"
                    + "                    </td>"
                    + "                </tr>"
                    + "            </table>"
                    + "        </td>"
                    + "    </tr>"
                    + "</table>"
                    + "</body>"
                    + "</html>",
                    "text/html"
            );

            //Adjuntar el pdf
            BodyPart PdfAdjunto = new MimeBodyPart();
            PdfAdjunto.setDataHandler(new DataHandler(new FileDataSource(rutaPdf)));
            PdfAdjunto.setFileName("Factura_" + mesEnLetras + "_" + anioActual + ".pdf");

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
}
