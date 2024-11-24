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
        Paragraph saltoLinea = new Paragraph("\n");
        documento.add(saltoLinea);

        //tabla para los datos del receptor y los datos fiscales
        PdfPTable tablaReceptorFiscal = new PdfPTable(3);
        tablaReceptorFiscal.setWidthPercentage(100);
        float[] anchosColumnasTablaEmisorFiscal = {49f, 2f, 49f};
        tablaReceptorFiscal.setWidths(anchosColumnasTablaEmisorFiscal);
       
        PdfPTable tablaReceptor = new PdfPTable(1);//Tabla generar el receptor       
        //Celda para el encabezado del receptor
        PdfPCell celdaEncabezadoReceptor = new PdfPCell();
        Paragraph encabezadoReceptor = new Paragraph(3);
        encabezadoReceptor.add(new Chunk("\n\n  Receptor\n ",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9,BaseColor.WHITE)));
        celdaEncabezadoReceptor.addElement(encabezadoReceptor);
        celdaEncabezadoReceptor.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celdaEncabezadoReceptor.setBorder(PdfPCell.NO_BORDER);
        celdaEncabezadoReceptor.setBackgroundColor(rojoPersonalizado);       
        tablaReceptor.addCell(celdaEncabezadoReceptor);//agrega el encabezado a la tabla del receptor
        
        //Parrafo y celda para el nombre del recptor
        Paragraph nombreReceptor = new Paragraph(10);
        nombreReceptor.add(new Chunk("ANGEL RAMIREZ CASTRO",FontFactory.getFont(FontFactory.HELVETICA, 8,BaseColor.BLACK)));
        PdfPCell celdaNombreReceptor = new PdfPCell(nombreReceptor);
        celdaNombreReceptor.setBorder(PdfPCell.NO_BORDER);
        //agrega el nombre del receptor a la tabla del receptor
        tablaReceptor.addCell(celdaNombreReceptor);
        
        //Tabla para los datos del receptor
        PdfPTable datosReceptor = new PdfPTable(2);
        float[] anchosColumnasTablaDatosReceptor = {30, 70};
        datosReceptor.setWidths(anchosColumnasTablaDatosReceptor);
        
        //Celdas para el RFC del receptor
        Paragraph tituloRfc = new Paragraph(new Chunk("RFC:",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8,BaseColor.BLACK)));
        PdfPCell celdaTituloRfc = new PdfPCell(tituloRfc);
        celdaTituloRfc.setBorder(PdfPCell.NO_BORDER);
        datosReceptor.addCell(celdaTituloRfc);      
        Paragraph RfcReceptor = new Paragraph(new Chunk("RACA031202FFF",FontFactory.getFont(FontFactory.HELVETICA, 8,BaseColor.BLACK)));
        PdfPCell celdaRfcReceptopr = new PdfPCell(RfcReceptor);
        celdaRfcReceptopr.setBorder(PdfPCell.NO_BORDER);
        datosReceptor.addCell(celdaRfcReceptopr);
        
        //celdas para el uso de CFDI
        Paragraph tituloCFDI = new Paragraph(new Chunk("USO CFDI:",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8,BaseColor.BLACK)));
        PdfPCell celdaTituloCFDI = new PdfPCell(tituloCFDI);
        celdaTituloCFDI.setBorder(PdfPCell.NO_BORDER);
        datosReceptor.addCell(celdaTituloCFDI);      
        Paragraph usoCFDI = new Paragraph(new Chunk("D10 Pagos por servicios educativos (colegiaturas)",FontFactory.getFont(FontFactory.HELVETICA, 8,BaseColor.BLACK)));
        PdfPCell celdaUsoCFDI = new PdfPCell(usoCFDI);
        celdaUsoCFDI.setBorder(PdfPCell.NO_BORDER);
        datosReceptor.addCell(celdaUsoCFDI);
        
        //celdas para el domicilio fiscal
        Paragraph tituloDomicilioFiscal = new Paragraph(new Chunk("Domicilio fiscal:",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8,BaseColor.BLACK)));
        PdfPCell celdaTituloDomicilioFiscal = new PdfPCell(tituloDomicilioFiscal);
        celdaTituloDomicilioFiscal.setBorder(PdfPCell.NO_BORDER);
        datosReceptor.addCell(celdaTituloDomicilioFiscal);      
        Paragraph domicilioFiscal = new Paragraph(new Chunk("39890",FontFactory.getFont(FontFactory.HELVETICA, 8,BaseColor.BLACK)));
        PdfPCell celdaDomicilioFiscal = new PdfPCell(domicilioFiscal);
        celdaDomicilioFiscal.setBorder(PdfPCell.NO_BORDER);
        datosReceptor.addCell(celdaDomicilioFiscal);
        
        //celdas para el regimen fiscal
        Paragraph tituloRegimenFiscal= new Paragraph(new Chunk("Regimen fiscal:",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8,BaseColor.BLACK)));
        PdfPCell celdaTituloRegimenFiscal = new PdfPCell(tituloRegimenFiscal);
        celdaTituloRegimenFiscal.setBorder(PdfPCell.NO_BORDER);
        datosReceptor.addCell(celdaTituloRegimenFiscal);      
        Paragraph regimenFiscal = new Paragraph(new Chunk("612 Personas Físicas con Actividades Empresariales y Profesionales",FontFactory.getFont(FontFactory.HELVETICA, 8,BaseColor.BLACK)));
        PdfPCell celdaRegimenFiscal = new PdfPCell(regimenFiscal);
        celdaRegimenFiscal.setBorder(PdfPCell.NO_BORDER);
        datosReceptor.addCell(celdaRegimenFiscal);
           
        //celda para la tabla de los datos del receptor
        PdfPCell celdaDatosReceptor = new PdfPCell(datosReceptor);
        celdaDatosReceptor.setBorder(PdfPCell.NO_BORDER);
        tablaReceptor.addCell(celdaDatosReceptor);
        
        //celda para eliminar los bordes a la tabla del receptor
        PdfPCell celdaReceptor = new PdfPCell(tablaReceptor);
        celdaReceptor.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.TOP | PdfPCell.BOTTOM); // Activa todos los bordes
        celdaReceptor.setBorderColor(rojoPersonalizado);
        
        //celda vacia para la separacion
        PdfPCell celdaVacia = new PdfPCell();
        celdaVacia.setBorder(PdfPCell.NO_BORDER);

        //tabla para los datos Fiscales
        PdfPTable tablaDaTosFiscales = new PdfPTable(1);
        //celda de encabezado de los datos fiscales
        Paragraph encabezadoDatosFiscales = new Paragraph(3);
        encabezadoDatosFiscales.add(new Chunk("\n\n  Datos fiscales\n ",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9,BaseColor.WHITE)));
        PdfPCell celdaEncabezadoDatosFiscales = new PdfPCell();
        celdaEncabezadoDatosFiscales.addElement(encabezadoDatosFiscales);
        celdaEncabezadoDatosFiscales.setBorder(PdfPCell.NO_BORDER);
        celdaEncabezadoDatosFiscales.setBackgroundColor(rojoPersonalizado);
        tablaDaTosFiscales.addCell(celdaEncabezadoDatosFiscales);
        
        //celdas para el folio SAT
        Paragraph tituloFolioSAT = new Paragraph(new Chunk("Folio SAT:",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8,BaseColor.BLACK)));
        PdfPCell celdaTituloFolioSAT = new PdfPCell(tituloFolioSAT);
        celdaTituloFolioSAT.setBorder(PdfPCell.NO_BORDER);
        tablaDaTosFiscales.addCell(celdaTituloFolioSAT);
        Paragraph FolioSAT = new Paragraph(new Chunk("7733D569-D3E7-4084-A167-51AB5B1337F6",FontFactory.getFont(FontFactory.HELVETICA, 8,BaseColor.BLACK)));
        PdfPCell celdaFolioSAT = new PdfPCell(FolioSAT);
        celdaFolioSAT.setBorder(PdfPCell.NO_BORDER);
        tablaDaTosFiscales.addCell(celdaFolioSAT);
        
        //celdas para Numero de serie certificado emisor:
        Paragraph tituloNumSerieCertificadoEmisor= new Paragraph(new Chunk("Numero de serie certificado emisor:",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8,BaseColor.BLACK)));
        PdfPCell celdaTituloNumSerieCertificadoEmisor = new PdfPCell(tituloNumSerieCertificadoEmisor);
        celdaTituloNumSerieCertificadoEmisor.setBorder(PdfPCell.NO_BORDER);
        tablaDaTosFiscales.addCell(celdaTituloNumSerieCertificadoEmisor);
        Paragraph NumSerieCertificadoEmisor = new Paragraph(new Chunk("00001000000512666708",FontFactory.getFont(FontFactory.HELVETICA, 8,BaseColor.BLACK)));
        PdfPCell celdaNumSerieCertificadoEmisor = new PdfPCell(NumSerieCertificadoEmisor);
        celdaNumSerieCertificadoEmisor.setBorder(PdfPCell.NO_BORDER);
        tablaDaTosFiscales.addCell(celdaNumSerieCertificadoEmisor);
        
        //Celdas para e Numero serie del certificado SAT
        Paragraph tituloNumSerieCertificadoSAT= new Paragraph(new Chunk("Numero de serie certificado emisor:",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8,BaseColor.BLACK)));
        PdfPCell celdaTituloNumSerieCertificadoSAT = new PdfPCell(tituloNumSerieCertificadoSAT);
        celdaTituloNumSerieCertificadoSAT.setBorder(PdfPCell.NO_BORDER);
        tablaDaTosFiscales.addCell(celdaTituloNumSerieCertificadoSAT);
        Paragraph NumSerieCertificadoSAT = new Paragraph(new Chunk("00001000000509846663",FontFactory.getFont(FontFactory.HELVETICA, 8,BaseColor.BLACK)));
        PdfPCell celdaNumSerieCertificadoSAT = new PdfPCell(NumSerieCertificadoSAT);
        celdaNumSerieCertificadoSAT.setBorder(PdfPCell.NO_BORDER);
        tablaDaTosFiscales.addCell(celdaNumSerieCertificadoEmisor);
        
        //Celda para leyenda
        Paragraph tituloLeyenda= new Paragraph(new Chunk("Leyenda:",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8,BaseColor.BLACK)));
        PdfPCell celdaTituloLeyenda = new PdfPCell(tituloLeyenda);
        celdaTituloLeyenda.setBorder(PdfPCell.NO_BORDER);
        tablaDaTosFiscales.addCell(celdaTituloLeyenda);
        
        //Tabla para exportacion
        PdfPTable tablaExportacion = new PdfPTable(2);
        //celdas para exportacion
        Paragraph tituloExportacion = new Paragraph(new Chunk("Exportacion:\n ",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8,BaseColor.BLACK)));
        PdfPCell celdaTituloExportacion = new PdfPCell(tituloExportacion);
        celdaTituloExportacion.setBorder(PdfPCell.NO_BORDER);
        tablaExportacion.addCell(celdaTituloExportacion);
        Paragraph exportacion = new Paragraph(new Chunk("No aplica\n ",FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 8,BaseColor.BLACK)));
        PdfPCell celdaExportacion = new PdfPCell(exportacion);
        celdaExportacion.setBorder(PdfPCell.NO_BORDER);
        tablaExportacion.addCell(celdaExportacion);
       
        //celda para la tabla de exportacion
        PdfPCell celdaDatosxportacion = new PdfPCell(tablaExportacion);
        celdaDatosxportacion.setBorder(PdfPCell.NO_BORDER);
        tablaDaTosFiscales.addCell(celdaDatosxportacion);
        //celda para salto de linea

        //celda para los datos fiscales
        PdfPCell celdaDatosFiscales = new PdfPCell(tablaDaTosFiscales);
        celdaDatosFiscales.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.TOP | PdfPCell.BOTTOM); // Activa todos los bordes
        celdaDatosFiscales.setBorderColor(rojoPersonalizado);
             
        //agrega las celdas a la tabla general
        tablaReceptorFiscal.addCell(celdaReceptor);
        tablaReceptorFiscal.addCell(celdaVacia);
        tablaReceptorFiscal.addCell(celdaDatosFiscales);
        //agrega al doc la tabla general del receptor y los datos fiscales
        documento.add(tablaReceptorFiscal);
             
        //tabla desgloce de gastos
        
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
