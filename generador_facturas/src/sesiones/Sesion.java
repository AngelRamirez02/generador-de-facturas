/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sesiones;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import com.itextpdf.text.Image; // Correcto
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import java.awt.Canvas;
import java.awt.Desktop;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    public void generarPdf(List<Sesion> listaSesiones, String ruta) throws FileNotFoundException, DocumentException, BadElementException, IOException{
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
        //Instancia del doc
        //PdfWriter.getInstance(documento,ficheroPdf);

        // Crear el escritor de PDF
        PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);

        // Definir el pie de página utilizando el evento `onEndPage`
        writer.setPageEvent(new PdfPageEventHelper() {
            Font fuentePie = FontFactory.getFont("Arial", 10, Font.NORMAL, BaseColor.BLACK);
            Font fuenteCorreos = FontFactory.getFont("Arial", 10, Font.NORMAL, BaseColor.BLUE);

            @Override
            public void onEndPage(PdfWriter writer, Document document) {
                PdfContentByte cb = writer.getDirectContent();

                // Crear una tabla para el pie de página
                PdfPTable pieTable = new PdfPTable(1); // Una columna
                pieTable.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin()); // Ancho total de la tabla
                pieTable.setLockedWidth(true); // Bloquear el ancho

                // Crear celdas con el contenido
                PdfPCell celda1 = new PdfPCell(new Phrase("Instituto Andres Manuel Lopez Obrador Calzada Pie De La Cuesta NO 12 Calle Vista Al Mar C.P. 398000, ACAPULCO, GRO. Tel 3-90-18-23", fuentePie));
                celda1.setBorder(PdfPCell.NO_BORDER); // Sin borde
                celda1.setHorizontalAlignment(Element.ALIGN_CENTER); // Alinear al centro
                pieTable.addCell(celda1); // Agregar la celda a la tabla

                PdfPCell celda2 = new PdfPCell(new Phrase("\ninstituto12020@gmail.com                                 nombredirector@gmail.com", fuenteCorreos));
                celda2.setBorder(PdfPCell.NO_BORDER); // Sin borde
                celda2.setHorizontalAlignment(Element.ALIGN_CENTER); // Alinear al centro
                pieTable.addCell(celda2); // Agregar la celda a la tabla

                PdfPCell celda3 = new PdfPCell(new Phrase("\nPágina: " + document.getPageNumber(), fuentePie));
                celda3.setBorder(PdfPCell.NO_BORDER); // Sin borde
                celda3.setHorizontalAlignment(Element.ALIGN_CENTER); // Alinear al centro
                pieTable.addCell(celda3); // Agregar la celda a la tabla

                // Posicionar la tabla en el documento
                pieTable.writeSelectedRows(0, -1, document.leftMargin(), document.bottom() + 50, writer.getDirectContent());
            }
        });

        //Abrir documento
        documento.open();
        PdfPTable encabezado= new PdfPTable(2);
        encabezado.setWidthPercentage(100);
        try {
            float[] anchosColumnas = { 20f, 80f }; 
            encabezado.setWidths(anchosColumnas); // Asignar los anchos a las columnas
            //Creacion de la imagen
            Image logo = Image.getInstance("src/img/logo_escuela.png");
            logo.scaleToFit(100, 100);
            //Agrega la imagen a la celda
            PdfPCell celdaLogo = new PdfPCell(logo);
            celdaLogo.setBorder(PdfPCell.NO_BORDER);
            celdaLogo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            
            //Creacion del parrafo 
            Paragraph infoColegio = new Paragraph();
            // Añadir texto con el nombre del colegio
            Font fuenteArial = FontFactory.getFont("Arial", 18, Font.BOLD);
            Chunk titulo = new Chunk("INSTITUTO ANDRES MANUEL LOPEZ OBRADOR", fuenteArial);
            infoColegio.add(titulo);
            
            //añadir texto con texto de secciones
            Font fuenteArial2 = FontFactory.getFont("Arial", 10, Font.NORMAL);
            Chunk secciones = new Chunk("\n\n    SECCIÓN                       SECCIÓN                      SECCIÓN",fuenteArial2);
            infoColegio.add(secciones);
           
            //Niveles escolares            
            Chunk niveles = new Chunk("\nJARDIN DE NIÑOS                PRIMARIA                  SECUNDARIA",fuenteArial2);
            infoColegio.add(niveles);
            
            //Claves de los niveles
            Chunk claves = new Chunk("\n       CLAVE: 123456789            CLAVE: 12345678           CLAVE: 123456789",fuenteArial2);
            infoColegio.add(claves);
            
            //Añade texto con el eslogan
            Chunk eslogan = new Chunk("\nAQUI DEBE IR UN ESLOGAN BIEN CHINGÓN XD",fuenteArial2);
            infoColegio.add(eslogan);
            
            // Añadir texto con direccion
            Chunk direccion = new Chunk("\nCALZADA PIE DE LA CUESTA NO 12 CALLE VISTA AL MAR C.P. 39800 TEL 3-90-18-23, ACAPULCO, GRO.", fuenteArial2);
            infoColegio.add(direccion);
            
            //Celda con la info del colegio
            PdfPCell celdaInfoColegio = new PdfPCell(infoColegio);
            celdaInfoColegio.setBorder(PdfPCell.NO_BORDER);
            celdaInfoColegio.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            
            //Añadir celdas a tablas
            encabezado.addCell(celdaLogo);
            encabezado.addCell(celdaInfoColegio);           
            //Añadir tabla a documento
            documento.add(encabezado);
        } catch (IOException ex) {
            Logger.getLogger(Sesion.class.getName()).log(Level.SEVERE, null, ex);
        }
              
        //Parrafo Titulo
        Paragraph titulo = new Paragraph("\nHistorial de inicio de sesión del Sistema de facturación "
                + "del Instituto Andres Manuel Lopez Obrador\n\n",FontFactory.getFont("arial",18,Font.BOLD,BaseColor.BLACK));
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        //Añadir parrafo al doc
        documento.add(titulo);
        
        //salto de linea al doc
        //Crear tabla y titulos de las columnas
        PdfPTable tabla = new PdfPTable(6);
        tabla.setWidthPercentage(100);
        
        //celdas con estilo
        PdfPCell celda_id = new PdfPCell(new Phrase("No sesión",fuenteArialBlanca));
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
        PdfPCell celda_horaSalida = new PdfPCell(new Phrase("Hora salida",fuenteArialBlanca));
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
        
        //Pie de pagina para el doc

        //Cerrar doc
        documento.close();       
        //Abre el archivo generado
        File path = new File(rutaArchivo);
        try {
            Desktop.getDesktop().open(path);
        } catch (IOException ex) {
            Logger.getLogger(Sesion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
