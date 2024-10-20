/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sesiones;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import com.itextpdf.text.Image; // Correcto
import org.jdesktop.swingx.painter.AbstractLayoutPainter.HorizontalAlignment;


/**
 *
 * @author ar275
 */
public class Sesion{
    public String id_sesion;
    public String usuario;
    public String fecha_entrada;
    public String horario_entrada;
    public String fecha_salida;
    public String horario_salida;
    
    public void generarPdf(List<Sesion> listaSesiones, String ruta) throws FileNotFoundException, DocumentException{
        LocalDate fechaACtual = LocalDate.now();
        LocalTime horaActual = LocalTime.now();
        BaseColor colorCabeceraTabla = new BaseColor(201, 69, 69); // Usando RGB
        com.itextpdf.text.Font fuenteArialBlanca = FontFactory.getFont("Arial", 12, Font.BOLD, BaseColor.WHITE);
            
        //Ruta para guardar el documento
        String rutaArchivo = ruta+File.separator+"Historial de sesiones"+fechaACtual.toString()+".pdf";
        //Crar documento
        Document documento = new Document();
        //Nombre del archivo
        FileOutputStream ficheroPdf = new FileOutputStream(rutaArchivo);
        //
        PdfWriter.getInstance(documento,ficheroPdf);
        //Abrir documento
        documento.open();
        //salto de linea al doc

        Image logo = null;
        try{
            logo = Image.getInstance("src/img/logo_escuela.png");
            logo.scaleAbsolute(150, 120);
            logo.setAlignment(Image.ALIGN_CENTER); // Centrar la imagen
            documento.add(logo);
        }catch(Exception e){   
            System.out.println(e);
        }
        
        //Parrafo Titulo
        Paragraph titulo = new Paragraph("Historial de inicio de sesion del Sistema de facturación "
                + "del Instituo Andres Manuel Lopez Obrador",FontFactory.getFont("arial",18,Font.BOLD,BaseColor.BLACK));
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        //Añadir parrafo al doc
        documento.add(titulo);
        
        //salto de linea al doc
        documento.add(Chunk.NEWLINE);
        //Crear tabla y titulos de las columnas
        PdfPTable tabla = new PdfPTable(6);
        tabla.setWidthPercentage(100);
        
        //celdas con estilo
        PdfPCell celda_id = new PdfPCell(new Phrase("ID sesión",fuenteArialBlanca));
        celda_id.setBackgroundColor(colorCabeceraTabla);
        celda_id.setFixedHeight(20f);//Altura de las cabeceras
        //Titulo usuario
        PdfPCell celda_user = new PdfPCell(new Phrase("Usuario",fuenteArialBlanca));
        celda_user.setBackgroundColor(colorCabeceraTabla);
        //Titulo fecha y hora ingreso
        PdfPCell celda_fechaIngreso = new PdfPCell(new Phrase("Fecha ingreso",fuenteArialBlanca));
        celda_fechaIngreso.setBackgroundColor(colorCabeceraTabla);
        PdfPCell celda_horaIngreso = new PdfPCell(new Phrase("Hora ingreso",fuenteArialBlanca));
        celda_horaIngreso.setBackgroundColor(colorCabeceraTabla);
        //Titulo fecha y hora salida
        PdfPCell celda_fechaSalida = new PdfPCell(new Phrase("Fecha salida",fuenteArialBlanca));
        celda_fechaSalida.setBackgroundColor(colorCabeceraTabla);
        PdfPCell celda_horaSalida = new PdfPCell(new Phrase("Hora ingreso",fuenteArialBlanca));
        celda_horaSalida.setBackgroundColor(colorCabeceraTabla);
        
        tabla.addCell(celda_id);
        tabla.addCell(celda_user);
        tabla.addCell(celda_fechaIngreso);
        tabla.addCell(celda_horaIngreso);
        tabla.addCell(celda_fechaSalida);
        tabla.addCell(celda_horaSalida);
        //agregar todos los valores de la seccion
        for(int i=0; i<listaSesiones.size(); i++){
            tabla.addCell(listaSesiones.get(i).id_sesion);
            tabla.addCell(listaSesiones.get(i).usuario);
            tabla.addCell(listaSesiones.get(i).fecha_entrada);
            tabla.addCell(listaSesiones.get(i).horario_entrada);
            tabla.addCell(listaSesiones.get(i).fecha_salida);
            tabla.addCell(listaSesiones.get(i).horario_salida);
        }
        //agregar tabla al documento
        documento.add(tabla);
        
        
        //cerrar doc
        documento.close();
    }

    public Sesion(String id_sesion, String usuario, String fecha_entrada, String horario_entrada, String fecha_salida, String horario_salida) {
        this.id_sesion = id_sesion;
        this.usuario = usuario;
        this.fecha_entrada = fecha_entrada;
        this.horario_entrada = horario_entrada;
        this.fecha_salida = fecha_salida;
        this.horario_salida = horario_salida;
    }
}
