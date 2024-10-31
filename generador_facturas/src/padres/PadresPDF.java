/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package padres;

import sesiones.*;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import com.itextpdf.text.Image; // Correcto
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import java.awt.Desktop;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ar275
 */
public class PadresPDF{
    public String rfc;
    public String nombres;
    public String apellido_paterno;
    public String apellido_materno;
    public String fecha_nacimiento;
    public String correo_electronico;
    public String domicilio_fiscal;
    public String estado;
    public String municipio;
    public String colonia;
    public String num_exterior;
    public String num_interior;
    public String regimen;
    
    public void generarPdf(List<PadresPDF> listaSesiones, String ruta) throws FileNotFoundException, DocumentException, BadElementException, IOException{
        //Fecha
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatoEspanol = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        String fechaFormateada = fechaActual.format(formatoEspanol);
        
        LocalTime horaActual = LocalTime.now();
         // Formatear la hora en el formato "HH:mm:ss"
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("hh:mm:ss a");
        String horaFormateada = horaActual.format(formatoHora);
        
        BaseColor colorCabeceraTabla = new BaseColor(201, 69, 69); // Usando RGB
        BaseColor colortTitulo = new BaseColor(134, 10, 10); // Usando RGB
        BaseColor colortLinea = new BaseColor(161, 145, 89); // Usando RGB
        com.itextpdf.text.Font fuenteArialBlanca = FontFactory.getFont("Arial", 12, Font.NORMAL, BaseColor.WHITE);
            
        //Ruta para guardar el documento
        String rutaArchivo = ruta+File.separator+"Registro padres"+fechaActual.toString()+".pdf";
        //Crar documento
        Document documento = new Document(PageSize.A3.rotate());
        //Nombre del archivo
        FileOutputStream ficheroPdf = new FileOutputStream(rutaArchivo);
        //Instancia del doc
        //PdfWriter.getInstance(documento,ficheroPdf);

        // Crear el escritor de PDF
        PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);

        // Definir el pie de página utilizando el evento `onEndPage`
        writer.setPageEvent(new PdfPageEventHelper() {
        Font fuentePie = FontFactory.getFont("Roboto", 10, Font.BOLD, BaseColor.BLACK);
        Font fuenteCorreos = FontFactory.getFont("Roboto", 10, Font.NORMAL, BaseColor.BLUE); 

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
                pieTable.writeSelectedRows(0, -1, document.leftMargin(), document.bottom() + 30, writer.getDirectContent());
            }
        });

        //Abrir documento
        documento.open();
         // Dibujar línea de separación
        PdfContentByte cb = writer.getDirectContent();
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
            Font fuenteTitutlo = FontFactory.getFont("Roboto", 16, Font.BOLD,colortTitulo);
            Chunk titulo = new Chunk("INSTITUTO ANDRES MANUEL LOPEZ OBRADOR", fuenteTitutlo);
            infoColegio.add(titulo);
            
            //Añade texto con el eslogan
            Font fuentEslogan = FontFactory.getFont("Roboto Light", 14, Font.BOLD, BaseColor.BLACK);
            Chunk eslogan = new Chunk("\n“Educando hoy, transformando el mañana.”",fuentEslogan);
            infoColegio.add(eslogan);
            
             // Añadir texto con direccion
            Font fuenteRoboto8 = FontFactory.getFont("Roboto", 8, Font.BOLD, BaseColor.BLACK);
            Chunk direccion = new Chunk("\n\nCalzada pie de la cuesta No 12 calle vista al mar C.P. 39800 Tel 3-90-18-23, \nAcapulco, Gro.", fuenteRoboto8);
            infoColegio.add(direccion);
            
            //Añadir linea
            Font fuenteLinea = FontFactory.getFont("Arial", 11,Font.BOLD, colortLinea);
            Chunk linea = new Chunk("\n_______________________________________________________________\n",fuenteLinea);
            infoColegio.add(linea);
            
            //añadir texto con texto de secciones
            Chunk niveles = new Chunk("\n\nSECCIÓN JARDIN DE NIÑOS           SECCIÓN PRIMARIA             SECCIÓN SECUNDARIA",fuenteRoboto8);
            infoColegio.add(niveles);
            
            //Claves de los niveles
            Chunk claves = new Chunk("\n   CLAVE: 123456789                       CLAVE: 12345678                    CLAVE: 123456789",fuenteRoboto8);
            infoColegio.add(claves);
                     
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
            Logger.getLogger(PadresPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
              
        //Parrafo Titulo
        Paragraph titulo = new Paragraph("\nRegistro de padres en el Sistema de facturación "
                + "del Instituto Andres Manuel Lopez Obrador\n",FontFactory.getFont("arial",18,Font.BOLD,BaseColor.BLACK));
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        //Añadir parrafo al doc
        documento.add(titulo);
        
        Paragraph fechaHora = new Paragraph("\nReporte generado el "+fechaFormateada+" a las "+horaFormateada+"\n\n", FontFactory.getFont("Arial",12,Font.NORMAL,BaseColor.BLACK));
        documento.add(fechaHora);
        
        //salto de linea al doc
        //Crear tabla y titulos de las columnas
        float[] columnWidths = {2f, 1f, 1f,1f,1f,2f,1f,1f,1f,1f,1f,1f,1f};
        PdfPTable tabla = new PdfPTable(13);
        tabla.setWidthPercentage(100);
        tabla.setWidths(columnWidths);
        
        //celdas con estilo
        //celda para el RFC
        PdfPCell celda_rfc = new PdfPCell(new Phrase("RFC",fuenteArialBlanca));
        celda_rfc.setBackgroundColor(colorCabeceraTabla);
        celda_rfc.setFixedHeight(20f);//Altura de las cabeceras
        //Titulo nombres
        PdfPCell celda_nombres = new PdfPCell(new Phrase("Nombre",fuenteArialBlanca));
        celda_nombres.setBackgroundColor(colorCabeceraTabla);
        //Titulo apellidos paterno y materno
        PdfPCell celda_apellidoPaterno = new PdfPCell(new Phrase("Apellido Paterno",fuenteArialBlanca));
        celda_apellidoPaterno.setBackgroundColor(colorCabeceraTabla);
        //
        PdfPCell celda_apellidoMaterno = new PdfPCell(new Phrase("Apellido Materno",fuenteArialBlanca));
        celda_apellidoPaterno.setBackgroundColor(colorCabeceraTabla);
        //Titulo fecha nacimiento
        PdfPCell celda_fechaNacimiento= new PdfPCell(new Phrase("Fecha de Nacimiento",fuenteArialBlanca));
        celda_fechaNacimiento.setBackgroundColor(colorCabeceraTabla);
        //Titulo correo electronico
        PdfPCell celda_correo = new PdfPCell(new Phrase("Correo electronico",fuenteArialBlanca));
        celda_correo.setBackgroundColor(colorCabeceraTabla);
        //Titulo domicilio fiscal
        PdfPCell celda_domicilioFiscal= new PdfPCell(new Phrase("Domicilio Fiscal",fuenteArialBlanca));
        celda_domicilioFiscal.setBackgroundColor(colorCabeceraTabla);
        //Titulo estado
        PdfPCell celda_estado = new PdfPCell(new Phrase("Estado",fuenteArialBlanca));
        celda_estado.setBackgroundColor(colorCabeceraTabla);
        //Celda muncipio
        PdfPCell celda_municipio = new PdfPCell(new Phrase("Municipio",fuenteArialBlanca));
        celda_municipio.setBackgroundColor(colorCabeceraTabla);
        //Titulo colonia
        PdfPCell celda_colonia = new PdfPCell(new Phrase("Colonia",fuenteArialBlanca));
        celda_colonia.setBackgroundColor(colorCabeceraTabla);
        //Titulo no exterior
        PdfPCell celda_noExterior = new PdfPCell(new Phrase("No Exterior",fuenteArialBlanca));
        celda_noExterior.setBackgroundColor(colorCabeceraTabla);
        //Titulo numero interior
        PdfPCell celda_noInterior = new PdfPCell(new Phrase("No Interior",fuenteArialBlanca));
        celda_noInterior.setBackgroundColor(colorCabeceraTabla);
        //Titulo regimen
        PdfPCell celda_regimen = new PdfPCell(new Phrase("Regimen",fuenteArialBlanca));
        celda_regimen.setBackgroundColor(colorCabeceraTabla);
        
        //agregar celdas a la tabla
        tabla.addCell(celda_rfc);
        tabla.addCell(celda_nombres);
        tabla.addCell(celda_apellidoPaterno);
        tabla.addCell(celda_apellidoPaterno);
        tabla.addCell(celda_fechaNacimiento);
        tabla.addCell(celda_correo);
        tabla.addCell(celda_domicilioFiscal);
        tabla.addCell(celda_estado);
        tabla.addCell(celda_municipio);
        tabla.addCell(celda_colonia);
        tabla.addCell(celda_noExterior);
        tabla.addCell(celda_noInterior);
        tabla.addCell(celda_regimen);
        
        //agregar todos los valores de la seccion
        for(int i=0; i<listaSesiones.size(); i++){
            tabla.addCell(listaSesiones.get(i).rfc);
            tabla.addCell(listaSesiones.get(i).nombres);
            tabla.addCell(listaSesiones.get(i).apellido_paterno);
            tabla.addCell(listaSesiones.get(i).apellido_materno);
            tabla.addCell(listaSesiones.get(i).fecha_nacimiento);
            tabla.addCell(listaSesiones.get(i).correo_electronico);
            tabla.addCell(listaSesiones.get(i).domicilio_fiscal);
            tabla.addCell(listaSesiones.get(i).estado);
            tabla.addCell(listaSesiones.get(i).municipio);
            tabla.addCell(listaSesiones.get(i).colonia);
            tabla.addCell(listaSesiones.get(i).num_exterior);
            tabla.addCell(listaSesiones.get(i).num_interior);
            tabla.addCell(listaSesiones.get(i).regimen);
        }
        //agregar tabla al documento
        documento.add(tabla);

        //Cerrar doc
        documento.close();       
        //Abre el archivo generado
        File path = new File(rutaArchivo);
        try {
            Desktop.getDesktop().open(path);
        } catch (IOException ex) {
            Logger.getLogger(PadresPDF.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

// Método para dibujar una línea de separación
    private void dibujarLineaSeparacion(PdfContentByte cb, Document document) {
        cb.setLineWidth(1f); // Grosor de la línea
        cb.setColorStroke(BaseColor.BLACK); // Color de la línea
        cb.moveTo(document.leftMargin(), document.getPageSize().getHeight() - document.topMargin() - 20);
        cb.lineTo(document.rightMargin(), document.getPageSize().getHeight() - document.topMargin() - 20);
        cb.stroke();
    }

    public PadresPDF(String rfc, String nombres, String apellido_paterno, String apellido_materno, String fecha_nacimiento, String correo_electronico, String domicilio_fiscal, String estado, String municipio, String colonia, String num_exterior, String num_interior, String regimen) {
        this.rfc = rfc;
        this.nombres = nombres;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.fecha_nacimiento = fecha_nacimiento;
        this.correo_electronico = correo_electronico;
        this.domicilio_fiscal = domicilio_fiscal;
        this.estado = estado;
        this.municipio = municipio;
        this.colonia = colonia;
        this.num_exterior = num_exterior;
        this.num_interior = num_interior;
        this.regimen = regimen;
    }

    
}
