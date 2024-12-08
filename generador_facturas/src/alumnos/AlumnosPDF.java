/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package alumnos;

import padres.*;
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
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.Pfm2afm;
import java.awt.Canvas;
import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.swingx.JXLabel.TextAlignment;

/**
 *
 * @author ar275
 */
public class AlumnosPDF {

    public String curp;
    public String rfc_padre;
    public String nombres;
    public String apellido_paterno;
    public String apellido_materno;
    public String fecha_nacimiento;
    public String nivel_escolaridad;
    public String grado_escolar;

    public void PdfTodosLosAlumnos(List<AlumnosPDF> listaPreescolar, List<AlumnosPDF>listaPrimaria, List<AlumnosPDF>listaSecundaria, String ruta) throws FileNotFoundException, DocumentException, BadElementException, IOException{
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
        String rutaArchivo = ruta+File.separator+"Registro alumnos"+fechaActual.toString()+".pdf";
        //Crar documento
        Document documento = new Document(PageSize.A4.rotate());
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
                
                PdfPCell fechaHoraGenerado = new PdfPCell(new Phrase("Reporte generado el "+fechaFormateada+" a las "+horaFormateada, fuentePie));
                fechaHoraGenerado.setBorder(PdfPCell.NO_BORDER);
                
                fechaHoraGenerado.setHorizontalAlignment(Element.ALIGN_CENTER);
                pieTable.addCell(fechaHoraGenerado);
                // Crear celdas con el contenido
                PdfPCell celda1 = new PdfPCell(new Phrase("Instituto Andres Manuel Lopez Obrador Calzada Pie De La Cuesta NO 12 Calle Vista Al Mar C.P. 398000, ACAPULCO, GRO. Tel 3-90-18-23", fuentePie));
                celda1.setBorder(PdfPCell.NO_BORDER); // Sin borde
                celda1.setHorizontalAlignment(Element.ALIGN_CENTER); // Alinear al centro
                pieTable.addCell(celda1); // Agregar la celda a la tabla

                PdfPCell celda2 = new PdfPCell(new Phrase("Instituto12020@gmail.com                                 nombredirector@gmail.com", fuenteCorreos));
                celda2.setBorder(PdfPCell.NO_BORDER); // Sin borde
                celda2.setHorizontalAlignment(Element.ALIGN_CENTER); // Alinear al centro
                pieTable.addCell(celda2); // Agregar la celda a la tabla

                PdfPCell celda3 = new PdfPCell(new Phrase("Página: " + document.getPageNumber(), fuentePie));
                celda3.setBorder(PdfPCell.NO_BORDER); // Sin borde
                celda3.setHorizontalAlignment(Element.ALIGN_CENTER); // Alinear al centro
                pieTable.addCell(celda3); // Agregar la celda a la tabla

                // Posicionar la tabla en el documento
                pieTable.writeSelectedRows(0, -1, document.leftMargin(), document.bottom() + 25, writer.getDirectContent());
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
             //Genera la imagen
            InputStream is = getClass().getResourceAsStream("/img/logo_escuela.png");
            Image logo = Image.getInstance(is.readAllBytes());
            is.close(); // Cierra el InputStream
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
            Logger.getLogger(AlumnosPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
              
        //Parrafo Titulo
        Paragraph titulo = new Paragraph("Registro de alumnos por nivel escolar en el Sistema de facturación "
                + "del Instituto Andres Manuel Lopez Obrador\n",FontFactory.getFont("arial",18,Font.BOLD,BaseColor.BLACK));
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        //Añadir parrafo al doc
        documento.add(titulo);
        
//        Paragraph fechaHora = new Paragraph("\nReporte generado el "+fechaFormateada+" a las "+horaFormateada+"\n", FontFactory.getFont("Arial",12,Font.NORMAL,BaseColor.BLACK));
//        documento.add(fechaHora);
        
        //salto de linea al doc
        //Crear tabla para alumnos de preescolar y titulos de las columnas
        float[] relacionColumnas= {2f, 2f, 1f,1f,1f,1f,1f,1f};
        PdfPTable tablaPreescolar = new PdfPTable(8);
        tablaPreescolar.setWidthPercentage(100);
        tablaPreescolar.setWidths(relacionColumnas);
        
        //Tabla para alumnos
        PdfPTable tablaPrimaria = new PdfPTable(8);
        tablaPrimaria.setWidthPercentage(100);
        tablaPrimaria.setWidths(relacionColumnas);
        
        PdfPTable tablaSecundaria = new PdfPTable(8);
        tablaSecundaria.setWidthPercentage(100);
        tablaSecundaria.setWidths(relacionColumnas);
        
        //celdas con estilo
        //celda para la CURP
        PdfPCell celda_curp = new PdfPCell(new Phrase("CURP",fuenteArialBlanca));
        celda_curp.setBackgroundColor(colorCabeceraTabla);
        celda_curp.setFixedHeight(20f);//Altura de las cabeceras
        //
        PdfPCell celda_rfc_padre = new PdfPCell(new Phrase("RFC del padre",fuenteArialBlanca));
        celda_rfc_padre.setBackgroundColor(colorCabeceraTabla);
        celda_rfc_padre.setFixedHeight(20f);//Altura de las cabeceras
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
        //Titulo nivel escolar
        PdfPCell celda_NivelEscolar= new PdfPCell(new Phrase("Nivel Escolar",fuenteArialBlanca));
        celda_NivelEscolar.setBackgroundColor(colorCabeceraTabla);
        //Titulo grado Escolar
        PdfPCell celda_GradoEscolar= new PdfPCell(new Phrase("Grado Escolar",fuenteArialBlanca));
        celda_GradoEscolar.setBackgroundColor(colorCabeceraTabla);
        
        
        //agregar celdas a la tabla
        tablaPreescolar.addCell(celda_curp);
        tablaPreescolar.addCell(celda_rfc_padre);
        tablaPreescolar.addCell(celda_nombres);
        tablaPreescolar.addCell(celda_apellidoPaterno);
        tablaPreescolar.addCell(celda_apellidoPaterno);
        tablaPreescolar.addCell(celda_fechaNacimiento);
        tablaPreescolar.addCell(celda_NivelEscolar);
        tablaPreescolar.addCell(celda_GradoEscolar);
        
        //Enacabezados para la tabla de primaria
        tablaPrimaria.addCell(celda_curp);
        tablaPrimaria.addCell(celda_rfc_padre);
        tablaPrimaria.addCell(celda_nombres);
        tablaPrimaria.addCell(celda_apellidoPaterno);
        tablaPrimaria.addCell(celda_apellidoPaterno);
        tablaPrimaria.addCell(celda_fechaNacimiento);
        tablaPrimaria.addCell(celda_NivelEscolar);
        tablaPrimaria.addCell(celda_GradoEscolar);
        
        //Encabezados para la tabla de secundaria
        tablaSecundaria.addCell(celda_curp);
        tablaSecundaria.addCell(celda_rfc_padre);
        tablaSecundaria.addCell(celda_nombres);
        tablaSecundaria.addCell(celda_apellidoPaterno);
        tablaSecundaria.addCell(celda_apellidoPaterno);
        tablaSecundaria.addCell(celda_fechaNacimiento);
        tablaSecundaria.addCell(celda_NivelEscolar);
        tablaSecundaria.addCell(celda_GradoEscolar);
        
        //lista para la tabla preescolar 
        for(int i=0; i<listaPreescolar.size(); i++){
            tablaPreescolar.addCell(listaPreescolar.get(i).curp);
            tablaPreescolar.addCell(listaPreescolar.get(i).rfc_padre);
            tablaPreescolar.addCell(listaPreescolar.get(i).nombres);
            tablaPreescolar.addCell(listaPreescolar.get(i).apellido_paterno);
            tablaPreescolar.addCell(listaPreescolar.get(i).apellido_materno);
            tablaPreescolar.addCell(listaPreescolar.get(i).fecha_nacimiento);
            tablaPreescolar.addCell(listaPreescolar.get(i).nivel_escolaridad);
            tablaPreescolar.addCell(listaPreescolar.get(i).grado_escolar);
        }
        
        //Lista para la tabla primaria
        for(int i=0; i<listaPrimaria.size(); i++){
            tablaPrimaria.addCell(listaPrimaria.get(i).curp);
            tablaPrimaria.addCell(listaPrimaria.get(i).rfc_padre);
            tablaPrimaria.addCell(listaPrimaria.get(i).nombres);
            tablaPrimaria.addCell(listaPrimaria.get(i).apellido_paterno);
            tablaPrimaria.addCell(listaPrimaria.get(i).apellido_materno);
            tablaPrimaria.addCell(listaPrimaria.get(i).fecha_nacimiento);
            tablaPrimaria.addCell(listaPrimaria.get(i).nivel_escolaridad);
            tablaPrimaria.addCell(listaPrimaria.get(i).grado_escolar);
        }
        
        //Lista para la tabla secundaria
        for(int i=0; i<listaSecundaria.size(); i++){
            tablaSecundaria.addCell(listaSecundaria.get(i).curp);
            tablaSecundaria.addCell(listaSecundaria.get(i).rfc_padre);
            tablaSecundaria.addCell(listaSecundaria.get(i).nombres);
            tablaSecundaria.addCell(listaSecundaria.get(i).apellido_paterno);
            tablaSecundaria.addCell(listaSecundaria.get(i).apellido_materno);
            tablaSecundaria.addCell(listaSecundaria.get(i).fecha_nacimiento);
            tablaSecundaria.addCell(listaSecundaria.get(i).nivel_escolaridad);
            tablaSecundaria.addCell(listaSecundaria.get(i).grado_escolar);
        }
        
        //Titulo para alumnos en nivel preescolar
        Paragraph tituloPreescolar = new Paragraph("\nAlumnos registrados en nivel preescolar en el Sistema de facturación "
                + "del Instituto Andres Manuel Lopez Obrador\n\n",FontFactory.getFont("arial",16,Font.BOLD,BaseColor.BLACK));
        tituloPreescolar.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(tituloPreescolar);

        //agregar tabla al documento
        documento.add(tablaPreescolar);
        
        //crear nueva pagina para el registro de alumnos de primaria
        documento.newPage();
        
        Image logoPagina = Image.getInstance("src/img/logo_escuela.png");
        logoPagina.scaleToFit(100, 100);
          
        documento.add(encabezado);
        
        //Titulo para alumnos en nivel primaria
        Paragraph tituloPrimaria = new Paragraph("\nAlumnos registrados en nivel primaria en el Sistema de facturación "
                + "del Instituto Andres Manuel Lopez Obrador\n\n",FontFactory.getFont("arial",16,Font.BOLD,BaseColor.BLACK));
        tituloPrimaria.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(tituloPrimaria);
        documento.add(tablaPrimaria);
        
        
        //crear nueva pagina para el registro de alumnos de secundaria
        documento.newPage();
        documento.add(encabezado);
        //Titulo para alumnos en nivel secundaria
        Paragraph tituloSecundaria = new Paragraph("\nAlumnos registrados en nivel secundaria en el Sistema de facturación "
                + "del Instituto Andres Manuel Lopez Obrador\n\n",FontFactory.getFont("arial",16,Font.BOLD,BaseColor.BLACK));
        tituloSecundaria.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(tituloSecundaria);
        documento.add(tablaSecundaria);

        //Cerrar doc
        documento.close();       
        //Abre el archivo generado
        File path = new File(rutaArchivo);
        try {
            Desktop.getDesktop().open(path);
        } catch (IOException ex) {
            Logger.getLogger(AlumnosPDF.class.getName()).log(Level.SEVERE, null, ex);
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

    public AlumnosPDF(String curp, String rfc_padre, String nombres, String apellido_paterno, String apellido_materno, String fecha_nacimiento, String nivel_escolaridad, String grado_escolar) {
        this.curp = curp;
        this.rfc_padre = rfc_padre;
        this.nombres = nombres;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.fecha_nacimiento = fecha_nacimiento;
        this.nivel_escolaridad = nivel_escolaridad;
        this.grado_escolar = grado_escolar;
    }    
}
