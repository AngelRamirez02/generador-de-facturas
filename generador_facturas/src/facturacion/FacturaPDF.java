package facturacion;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FacturaPDF {

    public void generarFacturaPDF(String ruta) throws FileNotFoundException, DocumentException, IOException {
        // Obtener la fecha y hora actual
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        // Definir el formato deseado
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a");
        // Aplicar el formato a la fecha y hora actual
        String fechaHoraFormateada = fechaHoraActual.format(formato);

        // Ruta para guardar el documento
        String rutaArchivo = ruta + File.separator + "Factura" + ".pdf";

        //color rojo personalizado de la escuela
        BaseColor rojoPersonalizado = new BaseColor(201, 69, 69); // Usando RGB
        
        // Crear documento
        Document documento = new Document();
        FileOutputStream ficheroPdf = new FileOutputStream(rutaArchivo);
        PdfWriter.getInstance(documento, ficheroPdf);

        // Abrir el documento
        documento.open();

        // Crear tabla para el encabezado
        PdfPTable encabezado = new PdfPTable(3);
        encabezado.setWidthPercentage(100);
        float[] anchosColumnas = {20f, 50f, 30f};
        encabezado.setWidths(anchosColumnas);

        // Agregar imagen
        Image logo = Image.getInstance("src/img/logo_escuela.png");
        logo.scaleToFit(80, 80);
        PdfPCell celdaLogo = new PdfPCell(logo);
        celdaLogo.setBorder(PdfPCell.NO_BORDER);
        celdaLogo.setHorizontalAlignment(Element.ALIGN_LEFT);
        encabezado.addCell(celdaLogo);

        // Información del emisor
        Paragraph datosEmisor = new Paragraph(10);
        Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK);
        datosEmisor.add(new Chunk("CAROLINA ASTUDILLO HERNANDEZ", fuenteTitulo));
        datosEmisor.add(new Chunk("\n\nRFC: AUHC670504QA1\n", FontFactory.getFont(FontFactory.HELVETICA, 7)));
        datosEmisor.add(new Chunk("Régimen fiscal: 612 Personas Físicas con Actividades Empresariales y Profesionales", FontFactory.getFont(FontFactory.HELVETICA, 7)));

        PdfPCell celdaEmisor = new PdfPCell();
        celdaEmisor.addElement(datosEmisor);
        celdaEmisor.setBorder(PdfPCell.NO_BORDER);
        celdaEmisor.setHorizontalAlignment(Element.ALIGN_CENTER);
        encabezado.addCell(celdaEmisor);
        
        //Tipografia para el numero de factura
        Font fuenteNoFactura = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 7, rojoPersonalizado);
        PdfPTable noFactura = new PdfPTable(2);//tabla para el numero de factura
        
        //celda para el texto FACTURA
        PdfPCell encabezadoFactura = new PdfPCell();
        Paragraph txtFactura = new Paragraph("FACTURA",fuenteNoFactura);
        txtFactura.setAlignment(Element.ALIGN_LEFT);
        encabezadoFactura.addElement(txtFactura);
        encabezadoFactura.setBorder(PdfPCell.NO_BORDER);
        
        //Celda para el numero de factura
        PdfPCell celdaNoFactura = new PdfPCell();
        Paragraph txtnofactura = new Paragraph("1",fuenteNoFactura);
        txtnofactura.setAlignment(Element.ALIGN_RIGHT);
        celdaNoFactura.addElement(txtnofactura);
        celdaNoFactura.setBorder(PdfPCell.NO_BORDER);
        
        //agrega los datos a la tabla de numeros de factura
        noFactura.addCell(encabezadoFactura);
        noFactura.addCell(celdaNoFactura);
       
        //elimna los bordes de la tabla de numeracion de la factura
        PdfPCell celdaNumeracionFactura = new PdfPCell(noFactura);
        celdaNumeracionFactura.setBorder(PdfPCell.NO_BORDER);

        //celda para el encabezado de la fecha y hora de emision
        Font fuenteBlanca = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 7, BaseColor.WHITE);
        PdfPCell celdaEncabezadoFechaHoraEmision = new PdfPCell();
        Paragraph encabezadoFechaHoeraEmision = new Paragraph(6);
        encabezadoFechaHoeraEmision.add(new Chunk("Fecha y hora de emisión: ",fuenteBlanca));
        celdaEncabezadoFechaHoraEmision.addElement(encabezadoFechaHoeraEmision);
        celdaEncabezadoFechaHoraEmision.setBorder(PdfPCell.NO_BORDER);
        celdaEncabezadoFechaHoraEmision.setBackgroundColor(rojoPersonalizado);
        
        //celda con la fecha y hora de emisision
        PdfPCell celdaFechaHoraEmision = new PdfPCell();
        Paragraph FechaHoraEmision = new Paragraph(6);
        FechaHoraEmision.add(new Chunk(fechaHoraFormateada,FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 7)));
        celdaFechaHoraEmision.addElement(FechaHoraEmision);
        celdaFechaHoraEmision.setBorder(PdfPCell.NO_BORDER);
        
        //celda para el encabezado de la fecha y hora de certificacion
        PdfPCell celdaEncabezadoFechaHoraCertificacion = new PdfPCell();
        Paragraph encabezadoFechaHoraCertificacion = new Paragraph(6);
        encabezadoFechaHoraCertificacion.add(new Chunk("Fecha y hora de certificación: ",fuenteBlanca));
        celdaEncabezadoFechaHoraCertificacion.addElement(encabezadoFechaHoraCertificacion);
        celdaEncabezadoFechaHoraCertificacion.setBorder(PdfPCell.NO_BORDER);
        celdaEncabezadoFechaHoraCertificacion.setBackgroundColor(rojoPersonalizado);
        
        //celda con la fecha y hora de certificacion
        PdfPCell celdaFechaHoraCertificacion = new PdfPCell();
        Paragraph FechaHoraCertifacion = new Paragraph(6);
        FechaHoraCertifacion.add(new Chunk(fechaHoraFormateada,FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 7)));
        celdaFechaHoraCertificacion.addElement(FechaHoraCertifacion);
        celdaFechaHoraCertificacion.setBorder(PdfPCell.NO_BORDER);
        
        //celda para el encabezado del cp de expedicion
        PdfPCell celdaEncabezadoCpExpedicion = new PdfPCell();
        Paragraph encabezadoCpExpedicion = new Paragraph(6);
        encabezadoCpExpedicion.add(new Chunk("Código postal de expedición:",fuenteBlanca));
        celdaEncabezadoCpExpedicion.addElement(encabezadoCpExpedicion);
        celdaEncabezadoCpExpedicion.setBorder(PdfPCell.NO_BORDER);
        celdaEncabezadoCpExpedicion.setBackgroundColor(rojoPersonalizado);
        
        //celda con el cp de expedicion
        PdfPCell celdaCpExpedicion = new PdfPCell();
        Paragraph cpExpedicion = new Paragraph(6);
        cpExpedicion.add(new Chunk("39890",FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 7)));
        celdaCpExpedicion.addElement(cpExpedicion);
        celdaCpExpedicion.setBorder(PdfPCell.NO_BORDER);

        //tabla para todos los datos de la factura
        PdfPTable datosFactura = new PdfPTable(1);
        datosFactura.addCell(celdaNumeracionFactura);
        datosFactura.addCell(celdaEncabezadoFechaHoraEmision);
        datosFactura.addCell(celdaFechaHoraEmision);
        datosFactura.addCell(celdaEncabezadoFechaHoraCertificacion);
        datosFactura.addCell(celdaFechaHoraCertificacion);
        datosFactura.addCell(celdaEncabezadoCpExpedicion);
        datosFactura.addCell(celdaCpExpedicion);
        // Crear una celda para contener la tabla noFactura
        PdfPCell celdaDatosFactura = new PdfPCell(datosFactura);
        // Establecer que no haya borde para la celda que contiene noFactura
        celdaDatosFactura.setBorder(PdfPCell.NO_BORDER);
        //agrega al encabezado los datos de la factura
        encabezado.addCell(celdaDatosFactura);
        // Agregar encabezado al documento
        documento.add(encabezado);
        
        
        

        // Cerrar el documento
        documento.close();

        // Abrir el archivo generado
        File path = new File(rutaArchivo);
        if (path.exists()) {
            Desktop.getDesktop().open(path);
        } else {
            System.out.println("Error: No se pudo generar el archivo.");
        }
    }

    public static void main(String[] args) {
        try {
            FacturaPDF pdf = new FacturaPDF();
            pdf.generarFacturaPDF("C:\\Users\\ar275\\Documents\\Generador de facturas");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
