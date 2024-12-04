package facturacion;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import correo.Correo;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class FacturaPDF {

    public void generarFacturaPDF(String ruta, Receptor receptor, Alumno alumno, Factura factura) throws FileNotFoundException, DocumentException, IOException {
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
        FechaHoraEmision.add(new Chunk(factura.getFechaHoraSellada(),FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 7)));
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
        FechaHoraCertifacion.add(new Chunk(factura.getFechaHoraSellada(),FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 7)));
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
        nombreReceptor.add(new Chunk(receptor.getNombreCompletoMayus(),FontFactory.getFont(FontFactory.HELVETICA, 8,BaseColor.BLACK)));
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
        Paragraph RfcReceptor = new Paragraph(new Chunk(receptor.getRfc(),FontFactory.getFont(FontFactory.HELVETICA, 8,BaseColor.BLACK)));
        PdfPCell celdaRfcReceptopr = new PdfPCell(RfcReceptor);
        celdaRfcReceptopr.setBorder(PdfPCell.NO_BORDER);
        datosReceptor.addCell(celdaRfcReceptopr);
        
        //celdas para el uso de CFDI
        Paragraph tituloCFDI = new Paragraph(new Chunk("USO CFDI:",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8,BaseColor.BLACK)));
        PdfPCell celdaTituloCFDI = new PdfPCell(tituloCFDI);
        celdaTituloCFDI.setBorder(PdfPCell.NO_BORDER);
        datosReceptor.addCell(celdaTituloCFDI);      
        Paragraph usoCFDI = new Paragraph(new Chunk(receptor.getUso_CFDI(),FontFactory.getFont(FontFactory.HELVETICA, 8,BaseColor.BLACK)));
        PdfPCell celdaUsoCFDI = new PdfPCell(usoCFDI);
        celdaUsoCFDI.setBorder(PdfPCell.NO_BORDER);
        datosReceptor.addCell(celdaUsoCFDI);
        
        //celdas para el domicilio fiscal
        Paragraph tituloDomicilioFiscal = new Paragraph(new Chunk("Domicilio fiscal:",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8,BaseColor.BLACK)));
        PdfPCell celdaTituloDomicilioFiscal = new PdfPCell(tituloDomicilioFiscal);
        celdaTituloDomicilioFiscal.setBorder(PdfPCell.NO_BORDER);
        datosReceptor.addCell(celdaTituloDomicilioFiscal);      
        Paragraph domicilioFiscal = new Paragraph(new Chunk(receptor.getDomicilio_fiscal(),FontFactory.getFont(FontFactory.HELVETICA, 8,BaseColor.BLACK)));
        PdfPCell celdaDomicilioFiscal = new PdfPCell(domicilioFiscal);
        celdaDomicilioFiscal.setBorder(PdfPCell.NO_BORDER);
        datosReceptor.addCell(celdaDomicilioFiscal);
        
        //celdas para el regimen fiscal
        Paragraph tituloRegimenFiscal= new Paragraph(new Chunk("Regimen fiscal:",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8,BaseColor.BLACK)));
        PdfPCell celdaTituloRegimenFiscal = new PdfPCell(tituloRegimenFiscal);
        celdaTituloRegimenFiscal.setBorder(PdfPCell.NO_BORDER);
        datosReceptor.addCell(celdaTituloRegimenFiscal);      
        Paragraph regimenFiscal = new Paragraph(new Chunk(receptor.getRegimen(),FontFactory.getFont(FontFactory.HELVETICA, 8,BaseColor.BLACK)));
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
        Paragraph FolioSAT = new Paragraph(new Chunk(factura.getFolioSat(),FontFactory.getFont(FontFactory.HELVETICA, 8,BaseColor.BLACK)));
        PdfPCell celdaFolioSAT = new PdfPCell(FolioSAT);
        celdaFolioSAT.setBorder(PdfPCell.NO_BORDER);
        tablaDaTosFiscales.addCell(celdaFolioSAT);
        
        //celdas para Numero de serie certificado emisor:
        Paragraph tituloNumSerieCertificadoEmisor= new Paragraph(new Chunk("Numero de serie certificado emisor:",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8,BaseColor.BLACK)));
        PdfPCell celdaTituloNumSerieCertificadoEmisor = new PdfPCell(tituloNumSerieCertificadoEmisor);
        celdaTituloNumSerieCertificadoEmisor.setBorder(PdfPCell.NO_BORDER);
        tablaDaTosFiscales.addCell(celdaTituloNumSerieCertificadoEmisor);
        Paragraph NumSerieCertificadoEmisor = new Paragraph(new Chunk(factura.getNumero_serie_certificado_emisor(),FontFactory.getFont(FontFactory.HELVETICA, 8,BaseColor.BLACK)));
        PdfPCell celdaNumSerieCertificadoEmisor = new PdfPCell(NumSerieCertificadoEmisor);
        celdaNumSerieCertificadoEmisor.setBorder(PdfPCell.NO_BORDER);
        tablaDaTosFiscales.addCell(celdaNumSerieCertificadoEmisor);
        
        //Celdas para e Numero serie del certificado SAT
        Paragraph tituloNumSerieCertificadoSAT= new Paragraph(new Chunk("Numero de serie del certificado SAT:",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8,BaseColor.BLACK)));
        PdfPCell celdaTituloNumSerieCertificadoSAT = new PdfPCell(tituloNumSerieCertificadoSAT);
        celdaTituloNumSerieCertificadoSAT.setBorder(PdfPCell.NO_BORDER);
        tablaDaTosFiscales.addCell(celdaTituloNumSerieCertificadoSAT);
        Paragraph NumSerieCertificadoSAT = new Paragraph(new Chunk(factura.getNumero_serie_certificado_SAT(),FontFactory.getFont(FontFactory.HELVETICA, 8,BaseColor.BLACK)));
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
        
        //salto de linea para separar la siguiente tabla
        documento.add(saltoLinea);
        
        //tabla desgloce de gastos 
        PdfPTable tablagastos = new PdfPTable(1);
        tablagastos.setWidthPercentage(100);
        
        //Tabla desgloce de gastos
        PdfPTable tablaDesgloceGastos = new PdfPTable(7);
        float[] anchosColumnastablaDesgloceGastos = {5f,10f,10f,30f,15f,15f,15f};
        tablaDesgloceGastos.setWidths(anchosColumnastablaDesgloceGastos);
        
        //Encabezados para la tabla desgloce gastos
        //Encabezado cantidad
        Paragraph tituloCantidad = new Paragraph(3);
        tituloCantidad.add((new Chunk("\n\nCant\n ",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9,BaseColor.WHITE))));
        tituloCantidad.setAlignment(Element.ALIGN_CENTER);
        PdfPCell celdaTituloCant = new PdfPCell();
        celdaTituloCant.addElement(tituloCantidad);
        celdaTituloCant.setBorder(PdfPCell.NO_BORDER);
        celdaTituloCant.setBackgroundColor(rojoPersonalizado);
        tablaDesgloceGastos.addCell(celdaTituloCant);
       
        //Encabezado unidad
        Paragraph tituloUnidad = new Paragraph(3);
        tituloUnidad.add((new Chunk("\n\nUnidad\n ",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9,BaseColor.WHITE))));
        tituloUnidad.setAlignment(Element.ALIGN_CENTER);
        PdfPCell celdaTituloUnidad = new PdfPCell();
        celdaTituloUnidad.addElement(tituloUnidad);
        celdaTituloUnidad.setBorder(PdfPCell.NO_BORDER);
        celdaTituloUnidad.setBackgroundColor(rojoPersonalizado);
        tablaDesgloceGastos.addCell(celdaTituloUnidad);

        //Encabezado clave
        Paragraph tituloClave = new Paragraph(3);
        tituloClave.add((new Chunk("\n\nClave\n ",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9,BaseColor.WHITE))));
        tituloClave.setAlignment(Element.ALIGN_CENTER);
        PdfPCell celdaTituloClave = new PdfPCell();
        celdaTituloClave.addElement(tituloClave);
        celdaTituloClave.setBorder(PdfPCell.NO_BORDER);
        celdaTituloClave.setBackgroundColor(rojoPersonalizado);
        tablaDesgloceGastos.addCell(celdaTituloClave);
        
        //Encabezado descrip
        Paragraph tituloDescrip = new Paragraph(3);
        tituloDescrip.add((new Chunk("\n\nDescripción\n ",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9,BaseColor.WHITE))));
        tituloDescrip.setAlignment(Element.ALIGN_CENTER);
        PdfPCell celdaTituloDescrip = new PdfPCell();
        celdaTituloDescrip.addElement(tituloDescrip);
        celdaTituloDescrip.setBorder(PdfPCell.NO_BORDER);
        celdaTituloDescrip.setBackgroundColor(rojoPersonalizado);
        tablaDesgloceGastos.addCell(celdaTituloDescrip);
        
        //Encabezado ob impuestos
        Paragraph tituloImpuestos = new Paragraph(3);
        tituloImpuestos.add((new Chunk("\n\nObj impuestos\n ",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9,BaseColor.WHITE))));
        tituloImpuestos.setAlignment(Element.ALIGN_CENTER);
        PdfPCell celdaTituloImpuestos = new PdfPCell();
        celdaTituloImpuestos.addElement(tituloImpuestos);
        celdaTituloImpuestos.setBorder(PdfPCell.NO_BORDER);
        celdaTituloImpuestos.setBackgroundColor(rojoPersonalizado);
        tablaDesgloceGastos.addCell(celdaTituloImpuestos);
        
        //Encabezado precio
        Paragraph tituloPrecio = new Paragraph(3);
        tituloPrecio.add((new Chunk("\n\nPrecio\n ",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9,BaseColor.WHITE))));
        tituloPrecio.setAlignment(Element.ALIGN_CENTER);
        PdfPCell celdaTituloPrecio = new PdfPCell();
        celdaTituloPrecio.addElement(tituloPrecio);
        celdaTituloPrecio.setBorder(PdfPCell.NO_BORDER);
        celdaTituloPrecio.setBackgroundColor(rojoPersonalizado);
        tablaDesgloceGastos.addCell(celdaTituloPrecio);
        
        //Encabezado importe
        Paragraph tituloImporte = new Paragraph(3);
        tituloImporte.add((new Chunk("\n\nImporte\n ",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9,BaseColor.WHITE))));
        tituloImporte.setAlignment(Element.ALIGN_CENTER);
        PdfPCell celdaTituloImporte = new PdfPCell();
        celdaTituloImporte.addElement(tituloImporte);
        celdaTituloImporte.setBorder(PdfPCell.NO_BORDER);
        celdaTituloImporte.setBackgroundColor(rojoPersonalizado);
        tablaDesgloceGastos.addCell(celdaTituloImporte);
        
        //------DATOS PARA LA TABLA DE DESGLOCE DE GASTOS-----------
         //cantidad
        Paragraph Cantidad = new Paragraph(3);
        Cantidad.add((new Chunk(factura.getCantidad(),FontFactory.getFont(FontFactory.HELVETICA, 7,BaseColor.BLACK))));
        Cantidad.setAlignment(Element.ALIGN_CENTER);
        PdfPCell celdaCant = new PdfPCell();
        celdaCant.addElement(Cantidad);
        celdaCant.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celdaCant.setBorder(PdfPCell.NO_BORDER);
        tablaDesgloceGastos.addCell(celdaCant);
        
        //Unidad
        Paragraph Unidad = new Paragraph(7);
        Unidad.add((new Chunk(factura.getUnidad(),FontFactory.getFont(FontFactory.HELVETICA, 7,BaseColor.BLACK))));
        Unidad.setAlignment(Element.ALIGN_CENTER);
        PdfPCell celdaUnidad = new PdfPCell();
        celdaUnidad.addElement(Unidad);
        celdaUnidad.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        celdaUnidad.setBorder(PdfPCell.NO_BORDER);
        tablaDesgloceGastos.addCell(celdaUnidad);
        
        //Clave
        Paragraph Clave = new Paragraph(3);
        Clave.add((new Chunk(factura.getClave(),FontFactory.getFont(FontFactory.HELVETICA, 7,BaseColor.BLACK))));
        Clave.setAlignment(Element.ALIGN_CENTER);
        PdfPCell celdaClave = new PdfPCell();
        celdaClave.addElement(Clave);
        celdaClave.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celdaClave.setBorder(PdfPCell.NO_BORDER);
        tablaDesgloceGastos.addCell(celdaClave);
        
        //Descripcion
        Paragraph Descripcion = new Paragraph(7);
        Descripcion.add((new Chunk(factura.getDescripcion(),FontFactory.getFont(FontFactory.HELVETICA,6,BaseColor.BLACK))));
        Descripcion.setAlignment(Element.ALIGN_LEFT);
        PdfPCell celdaDescripcion = new PdfPCell();
        celdaDescripcion.addElement(Descripcion);
        celdaDescripcion.setBorder(PdfPCell.NO_BORDER);
        tablaDesgloceGastos.addCell(celdaDescripcion);
        
        //Objeto impuestos
        Paragraph objImpuestos = new Paragraph(3);
        objImpuestos.add((new Chunk(factura.getObj_impuestos(),FontFactory.getFont(FontFactory.HELVETICA, 7,BaseColor.BLACK))));
        objImpuestos.setAlignment(Element.ALIGN_CENTER);
        PdfPCell celdaObjImpuestos = new PdfPCell();
        celdaObjImpuestos.addElement(objImpuestos);
        celdaObjImpuestos.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celdaObjImpuestos.setBorder(PdfPCell.NO_BORDER);
        tablaDesgloceGastos.addCell(celdaObjImpuestos);
        
        //Precio
        Paragraph Precio = new Paragraph(3);
        Precio.add((new Chunk("\n\n$"+factura.getPrecio_unitario()+"\n ",FontFactory.getFont(FontFactory.HELVETICA, 7,BaseColor.BLACK))));
        Precio.setAlignment(Element.ALIGN_CENTER);
        PdfPCell celdaPrecio = new PdfPCell();
        celdaPrecio.addElement(Precio);
        celdaPrecio.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celdaPrecio.setBorder(PdfPCell.NO_BORDER);
        tablaDesgloceGastos.addCell(celdaPrecio);
        
        Paragraph Importe = new Paragraph(3);
        Importe.add((new Chunk("\n\n$"+factura.getImporte()+"\n ",FontFactory.getFont(FontFactory.HELVETICA, 7,BaseColor.BLACK))));
        Importe.setAlignment(Element.ALIGN_CENTER);
        PdfPCell celdaImporte = new PdfPCell();
        celdaImporte.addElement(Importe);
        celdaImporte.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celdaImporte.setBorder(PdfPCell.NO_BORDER);
        tablaDesgloceGastos.addCell(celdaImporte);
        
        
        //----------------------------------------------------------
        
        //agrega la tabla de desgloces a la tabla de gastos
        PdfPCell celdaDesgloceGastos = new PdfPCell(tablaDesgloceGastos);
        celdaDesgloceGastos.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.NO_BORDER | PdfPCell.NO_BORDER);
        celdaDesgloceGastos.setBorderColor(rojoPersonalizado);
        
        //agrega a la tabla de gastos 
        tablagastos.addCell(celdaDesgloceGastos);
        
        //Celda para el titulo de alumno
        Paragraph tituloAlumno = new Paragraph(3);
        tituloAlumno.add((new Chunk("\n\n    Alumno\n ",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 7,BaseColor.BLACK))));
        PdfPCell celdaTituloAlumno = new PdfPCell();
        celdaTituloAlumno.addElement(tituloAlumno);
        celdaTituloAlumno.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.NO_BORDER | PdfPCell.NO_BORDER);
        celdaTituloAlumno.setBorderColor(rojoPersonalizado);
        tablagastos.addCell(celdaTituloAlumno);
        
        //Tabla alumno
        //Encabezados para la tabla alumno
        PdfPTable tablaAlumno = new PdfPTable(4);
        float[] anchosColumnastablaAlumno = {25,25f,25f,25f};
        
        //Encabezado paracurp
        Paragraph tituloCurp = new Paragraph(3);
        tituloCurp.add((new Chunk("\n\n      CURP\n ",FontFactory.getFont(FontFactory.HELVETICA, 7,BaseColor.BLACK))));
        tituloCurp.setAlignment(Element.ALIGN_LEFT);
        PdfPCell celdaTituloCurp = new PdfPCell();
        celdaTituloCurp.addElement(tituloCurp);
        celdaTituloCurp.setBorder(PdfPCell.NO_BORDER);
        tablaAlumno.addCell(celdaTituloCurp);
        
        //Encabezado clave centro trabajo
        Paragraph tituloCentroTrabajo = new Paragraph(3);
        tituloCentroTrabajo.add((new Chunk("\n\nCentro de trabajo\n ",FontFactory.getFont(FontFactory.HELVETICA, 7,BaseColor.BLACK))));
        tituloCentroTrabajo.setAlignment(Element.ALIGN_LEFT);
        PdfPCell celdaTituloCentroTrabajo = new PdfPCell();
        celdaTituloCentroTrabajo.addElement(tituloCentroTrabajo);
        celdaTituloCentroTrabajo.setBorder(PdfPCell.NO_BORDER);
        tablaAlumno.addCell(celdaTituloCentroTrabajo);

        //Encabezado Nivel Educativo
        Paragraph tituloNivelEscolar = new Paragraph(3);
        tituloNivelEscolar.add((new Chunk("\n\nNivel educativo\n ",FontFactory.getFont(FontFactory.HELVETICA, 7,BaseColor.BLACK))));
        tituloNivelEscolar.setAlignment(Element.ALIGN_LEFT);
        PdfPCell celdaTituloNivelEscolar = new PdfPCell();
        celdaTituloNivelEscolar.addElement(tituloNivelEscolar);
        celdaTituloNivelEscolar.setBorder(PdfPCell.NO_BORDER);
        tablaAlumno.addCell(celdaTituloNivelEscolar);
        
        //Encabezado Nombre
        Paragraph tituloNombre = new Paragraph(3);
        tituloNombre.add((new Chunk("\n\nNombre\n ",FontFactory.getFont(FontFactory.HELVETICA, 7,BaseColor.BLACK))));
        tituloNombre.setAlignment(Element.ALIGN_LEFT);
        PdfPCell celdaTituloNombre = new PdfPCell();
        celdaTituloNombre.addElement(tituloNombre);
        celdaTituloNombre.setBorder(PdfPCell.NO_BORDER);
        tablaAlumno.addCell(celdaTituloNombre);
        
        //-------------SECCCION PARA LLENAR LA TABLA DE ALUMNOS
        //CURP
        Paragraph CURP = new Paragraph(3);
        CURP.add((new Chunk("\n\n"+alumno.getCurp()+"\n ",FontFactory.getFont(FontFactory.HELVETICA, 7,BaseColor.BLACK))));
        CURP.setAlignment(Element.ALIGN_LEFT);
        PdfPCell celdaCurp = new PdfPCell();
        celdaCurp.addElement(CURP);
        celdaCurp.setBorder(PdfPCell.NO_BORDER);
        tablaAlumno.addCell(celdaCurp);
        
        //Centro de trabajo
        Paragraph centroTrabajo = new Paragraph(3);
        centroTrabajo.add((new Chunk("\n\n"+"11122221212"+"\n ",FontFactory.getFont(FontFactory.HELVETICA, 7,BaseColor.BLACK))));
        centroTrabajo.setAlignment(Element.ALIGN_LEFT);
        PdfPCell celdacentroTrabajo = new PdfPCell();
        celdacentroTrabajo.addElement(centroTrabajo);
        celdacentroTrabajo.setBorder(PdfPCell.NO_BORDER);
        tablaAlumno.addCell(celdacentroTrabajo);
        
        //Nivel educativo
        Paragraph nivelEducativo = new Paragraph(3);
        nivelEducativo.add((new Chunk("\n\n"+alumno.getNivel_escolar()+"\n ",FontFactory.getFont(FontFactory.HELVETICA, 7,BaseColor.BLACK))));
        nivelEducativo.setAlignment(Element.ALIGN_LEFT);
        PdfPCell celdaNivelEducativo = new PdfPCell();
        celdaNivelEducativo.addElement(nivelEducativo);
        celdaNivelEducativo.setBorder(PdfPCell.NO_BORDER);
        tablaAlumno.addCell(celdaNivelEducativo);
        
        //Nombre
        Paragraph nombreAlumno = new Paragraph(3);
        nombreAlumno.add((new Chunk("\n\n"+alumno.getNombreCompletoMayus()+"\n ",FontFactory.getFont(FontFactory.HELVETICA, 7,BaseColor.BLACK))));
        nombreAlumno.setAlignment(Element.ALIGN_LEFT);
        PdfPCell celdaNombreAlumno = new PdfPCell();
        celdaNombreAlumno.addElement(nombreAlumno);
        celdaNombreAlumno.setBorder(PdfPCell.NO_BORDER);
        tablaAlumno.addCell(celdaNombreAlumno);      
        //-----------------------------------------------------     
        
        //Celda para la tabla de alumnos
        PdfPCell celdaTablaAlumno = new PdfPCell(tablaAlumno);
        celdaTablaAlumno.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.NO_BORDER | PdfPCell.BOTTOM);
        celdaTablaAlumno.setBorderColor (rojoPersonalizado);       
        //Agrega la tabla alumno
        tablagastos.addCell(celdaTablaAlumno);
        
        //Tabla general para los datos pago
        PdfPTable tablaGeneralPagos = new PdfPTable(2);
        float[] anchosColumnasTablaPagos = {60f,40f};
        tablaGeneralPagos.setWidths(anchosColumnasTablaPagos);
             
        //Tabla para datos pago izq
        PdfPTable tablaPagos = new PdfPTable(1);
        //Celdas para la tabla de pagos
        //Celda para el total en texto
        Paragraph totalTexto = new Paragraph(3);
        totalTexto.add(new Chunk("\n\n( "+factura.getTotal_letras()+" )\n ",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8,BaseColor.BLACK)));
        PdfPCell celdaTotalTexto = new PdfPCell();
        celdaTotalTexto.addElement(totalTexto);
        celdaTotalTexto.setBorder(PdfPCell.NO_BORDER);       
        //agregar total en texto
        tablaPagos.addCell(celdaTotalTexto);
        
        //Tabla para las formas de pago
        PdfPTable tablaFormasDePago = new PdfPTable(2);
        float[] anchosColumnasTablaFormasPagos = {25f,75f};
        tablaFormasDePago.setWidths(anchosColumnasTablaFormasPagos);
        
        //-------FORMA DE PAGO---------------------
        //Titulos para la tabla de formas de pago
        Paragraph tituloFormaPago = new Paragraph(3);
        tituloFormaPago.add(new Chunk("\n\n\nForma de pago: ",FontFactory.getFont(FontFactory.HELVETICA, 7,BaseColor.BLACK)));
        PdfPCell celdaTituloFormaPago = new PdfPCell();
        celdaTituloFormaPago.addElement(tituloFormaPago);
        celdaTituloFormaPago.setBorder(PdfPCell.NO_BORDER);
        tablaFormasDePago.addCell(celdaTituloFormaPago);
        //datos para la forma de pago
        Paragraph FormaPago = new Paragraph(3);
        FormaPago.add(new Chunk("\n\n\n"+factura.getForma_pago(),FontFactory.getFont(FontFactory.HELVETICA, 7,BaseColor.BLACK)));
        PdfPCell celdaFormaPago = new PdfPCell();
        celdaFormaPago.addElement(FormaPago);
        celdaFormaPago.setBorder(PdfPCell.NO_BORDER);
        tablaFormasDePago.addCell(celdaFormaPago);
        //-----------------------------------------
        
        //-------METODO DE PAGO--------------------
        //Titulo metodo pago
        Paragraph tituloMetodoPago = new Paragraph(3);
        tituloMetodoPago.add(new Chunk("\n\n\nMétodo de pago: ",FontFactory.getFont(FontFactory.HELVETICA, 7,BaseColor.BLACK)));
        PdfPCell celdaTituloMetodoPago = new PdfPCell();
        celdaTituloMetodoPago.addElement(tituloMetodoPago);
        celdaTituloMetodoPago.setBorder(PdfPCell.NO_BORDER);
        tablaFormasDePago.addCell(celdaTituloMetodoPago);
       
        //datos para la metodo de pago
        Paragraph metodoPago = new Paragraph(3);
        metodoPago.add(new Chunk("\n\n\n"+factura.getMetodo_pago(),FontFactory.getFont(FontFactory.HELVETICA, 7,BaseColor.BLACK)));
        PdfPCell celdaMetodoPago = new PdfPCell();
        celdaMetodoPago.addElement(metodoPago);
        celdaMetodoPago.setBorder(PdfPCell.NO_BORDER);
        tablaFormasDePago.addCell(celdaMetodoPago);
        //-----------------------------------------
        
        //--------------TIPO DE COMPROBANTE---------------------
        //Titulo Tipo de comprobante:
        Paragraph tituloTipoComprobante = new Paragraph(3);
        tituloTipoComprobante.add(new Chunk("\n\n\nTipo de comprobante:",FontFactory.getFont(FontFactory.HELVETICA, 7,BaseColor.BLACK)));
        PdfPCell celdaTituloTipoComprobante = new PdfPCell();
        celdaTituloTipoComprobante.addElement(tituloTipoComprobante);
        celdaTituloTipoComprobante.setBorder(PdfPCell.NO_BORDER);
        tablaFormasDePago.addCell(celdaTituloTipoComprobante);
       
        //datos para Tipo de comprobante
        Paragraph tipoComprobante = new Paragraph(3);
        tipoComprobante.add(new Chunk("\n\n\n"+factura.getTipo_comprobante(),FontFactory.getFont(FontFactory.HELVETICA, 7,BaseColor.BLACK)));
        PdfPCell celdaTipoComprobante = new PdfPCell();
        celdaTipoComprobante.addElement(tipoComprobante);
        celdaTipoComprobante.setBorder(PdfPCell.NO_BORDER);
        tablaFormasDePago.addCell(celdaTipoComprobante);
        //-----------------------------------------------------
        
        //---------CONDICIONES PAGO------------------------------
        //Titulo condiciones pago
        Paragraph tituloCondicionesPago = new Paragraph(3);
        tituloCondicionesPago.add(new Chunk("\n\n\nCondiciones pago:",FontFactory.getFont(FontFactory.HELVETICA, 7,BaseColor.BLACK)));
        PdfPCell celdaTituloCondicionesPago= new PdfPCell();
        celdaTituloCondicionesPago.addElement(tituloCondicionesPago);
        celdaTituloCondicionesPago.setBorder(PdfPCell.NO_BORDER);
        tablaFormasDePago.addCell(celdaTituloCondicionesPago);
       
        //datos para Tipo de comprobante
        Paragraph condicionesPago = new Paragraph(3);
        condicionesPago.add(new Chunk("\n\n\n  ",FontFactory.getFont(FontFactory.HELVETICA, 7,BaseColor.BLACK)));
        PdfPCell celdaCondicionesPago = new PdfPCell();
        celdaCondicionesPago.addElement(condicionesPago);
        celdaCondicionesPago.setBorder(PdfPCell.NO_BORDER);
        tablaFormasDePago.addCell(celdaCondicionesPago);
        //--------------------------------------------------
        
        //---------------MONEDA---------------------------
        PdfPTable tablaMoneda = new PdfPTable(4);//Tabla para el tipo de moneda y cambio
         float[] anchosColumnasTablaMoneda = {25f,30f,25f,20f};
         tablaMoneda.setWidths(anchosColumnasTablaMoneda);
        //Titulo tipo moneda
        Paragraph tituloMoneda = new Paragraph((float) 2.5);
        tituloMoneda.add(new Chunk("\n\n\nMoneda:",FontFactory.getFont(FontFactory.HELVETICA, 7,BaseColor.BLACK)));
        PdfPCell celdaTituloMoneda = new PdfPCell();
        celdaTituloMoneda.addElement(tituloMoneda);
        celdaTituloMoneda.setBorder(PdfPCell.NO_BORDER);
        tablaMoneda.addCell(celdaTituloMoneda);
        
        //Moneda
        Paragraph moneda = new Paragraph((float) 2.5);
        moneda.add(new Chunk("\n\n\nMXN Peso Mexicano",FontFactory.getFont(FontFactory.HELVETICA, 7,BaseColor.BLACK)));
        PdfPCell celdaMoneda = new PdfPCell();
        celdaMoneda.addElement(moneda);
        celdaMoneda.setBorder(PdfPCell.NO_BORDER);
        tablaMoneda.addCell(celdaMoneda);
        
        //Titulo Tipo Cambio
        Paragraph tituloTipoCambio = new Paragraph((float) 2.5);
        tituloTipoCambio.add(new Chunk("\n\n\nTipo cambio:",FontFactory.getFont(FontFactory.HELVETICA, 7,BaseColor.BLACK)));
        PdfPCell celdaTituloTipoCambio = new PdfPCell();
        celdaTituloTipoCambio.addElement(tituloTipoCambio);
        celdaTituloTipoCambio.setBorder(PdfPCell.NO_BORDER);
        tablaMoneda.addCell(celdaTituloTipoCambio);
        
        //Tipo cambio
        Paragraph tipoCambio = new Paragraph((float) 2.5);
        moneda.add(new Chunk("\n\n\n ",FontFactory.getFont(FontFactory.HELVETICA, 7,BaseColor.BLACK)));
        PdfPCell celdaTipoCambio = new PdfPCell();
        celdaTipoCambio.addElement(tipoCambio);
        celdaTipoCambio.setBorder(PdfPCell.NO_BORDER);
        tablaMoneda.addCell(celdaTipoCambio);
        //-----------------------------------------------
        
        //Agregar tabla de datos de pago
        PdfPCell celdaDatosFormasDePago = new PdfPCell(tablaFormasDePago);
        celdaDatosFormasDePago.setBorder(PdfPCell.NO_BORDER);
        tablaPagos.addCell(celdaDatosFormasDePago);
        
        //agregar tabla moneda a tabla datos de gastos
        PdfPCell celdaTablaMoneda = new PdfPCell(tablaMoneda);
        celdaTablaMoneda.setBorder(PdfPCell.NO_BORDER);
        tablaPagos.addCell(celdaTablaMoneda);
        
        //------------TABLA DERECHA PARA TOTALES-------------------
        //TABLA DERECHA PARA SUBTOTAL, descuento Y TOTALES
        PdfPTable tablaTotalesGeneral = new PdfPTable(1);
        
        //Tabla de subtotal y descuento
        PdfPTable tablaTotales = new PdfPTable(2);
        //Titulo subtotal
        Paragraph tituloSubtotal = new Paragraph(3);
        tituloSubtotal.add(new Chunk("\n\n\n\n\nSubtotal:",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8,BaseColor.BLACK)));
        tituloSubtotal.setAlignment(Element.ALIGN_RIGHT);
        PdfPCell celdaTituloSubTotal = new PdfPCell();
        celdaTituloSubTotal.addElement(tituloSubtotal);
        celdaTituloSubTotal.setBorder(PdfPCell.NO_BORDER);
        tablaTotales.addCell(celdaTituloSubTotal);
        
        //subtotal
        Paragraph subtotal = new Paragraph(3);
        subtotal.add(new Chunk("\n\n\n$"+factura.getSubtotal(),FontFactory.getFont(FontFactory.HELVETICA, 10,BaseColor.BLACK)));
        subtotal.setAlignment(Element.ALIGN_RIGHT);
        PdfPCell celdaSubTotal = new PdfPCell();
        celdaSubTotal.addElement(subtotal);
        celdaSubTotal.setBorder(PdfPCell.NO_BORDER);
        tablaTotales.addCell(celdaSubTotal);
        
        //Titulo descuento
        Paragraph tituloDescuento = new Paragraph(3);
        tituloDescuento.add(new Chunk("\n\n\nDescuento:",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8,BaseColor.BLACK)));
        tituloDescuento.setAlignment(Element.ALIGN_RIGHT);
        PdfPCell celdaTituloDescuento = new PdfPCell();
        celdaTituloDescuento.addElement(tituloDescuento);
        celdaTituloDescuento.setBorder(PdfPCell.NO_BORDER);
        tablaTotales.addCell(celdaTituloDescuento);
        
        //Descuento
        Paragraph Descuento = new Paragraph(3);
        Descuento.add(new Chunk("\n\n\n$"+factura.getDescuento(),FontFactory.getFont(FontFactory.HELVETICA, 10,BaseColor.BLACK)));
        Descuento.setAlignment(Element.ALIGN_RIGHT);
        PdfPCell celdaDescuento = new PdfPCell();
        celdaDescuento.addElement(Descuento);
        celdaDescuento.setBorder(PdfPCell.NO_BORDER);
        tablaTotales.addCell(celdaDescuento);
        
        //Agregar tabla de subtotal y descuentos
        PdfPCell celdaTablaSubtotalDescuento = new PdfPCell(tablaTotales);
        celdaTablaSubtotalDescuento.setBorder(PdfPCell.NO_BORDER);
        celdaTablaSubtotalDescuento.setFixedHeight(110f);
        tablaTotalesGeneral.addCell(celdaTablaSubtotalDescuento);
        
        //----------TABLA TOTAL------------------
        //TABLA TOTAL
        PdfPTable tablaTotal = new PdfPTable(2);
        //Titulo Total
        Paragraph tituloTotal = new Paragraph(3);
        tituloTotal.add(new Chunk("\n\nTotal:",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10,BaseColor.WHITE)));
        tituloTotal.setAlignment(Element.ALIGN_RIGHT);
        PdfPCell celdaTituloTotal = new PdfPCell();
        celdaTituloTotal.addElement(tituloTotal);
        celdaTituloTotal.setBorder(PdfPCell.NO_BORDER); 
        tablaTotal.addCell(celdaTituloTotal);

        //Total
        Paragraph Total = new Paragraph(3);
        Total.add(new Chunk("\n\n"+factura.getTotal(),FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10,BaseColor.WHITE)));
        Total.setAlignment(Element.ALIGN_RIGHT);
        PdfPCell celdaTotal = new PdfPCell();
        celdaTotal.addElement(Total);
        celdaTotal.setBorder(PdfPCell.NO_BORDER); 
        tablaTotal.addCell(celdaTotal);

        
        //Agregar tabla de total
        PdfPCell celdaTablaTotal = new PdfPCell(tablaTotal);
        celdaTablaTotal.setBackgroundColor(rojoPersonalizado);
        celdaTablaTotal.setBorder(PdfPCell.NO_BORDER);
        tablaTotalesGeneral.addCell(celdaTablaTotal);
        //----------------------------------------------------    
        
        //agregar celdas a la tabla general de gastos
        //Tabla izq
        PdfPCell celdaTablaPagosIzq = new PdfPCell(tablaPagos);
        celdaTablaPagosIzq.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.NO_BORDER | PdfPCell.BOTTOM);
        celdaTablaPagosIzq.setBorderColor(rojoPersonalizado);
        tablaGeneralPagos.addCell(celdaTablaPagosIzq);
        //Tabla derecha
        PdfPCell celdaTablaTotalesDer = new PdfPCell(tablaTotalesGeneral);
        celdaTablaTotalesDer.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.NO_BORDER | PdfPCell.BOTTOM);
        celdaTablaTotalesDer.setBorderColor(rojoPersonalizado);
        tablaGeneralPagos.addCell(celdaTablaTotalesDer);
        
        //agregar tabla pagos a la tabla general
        PdfPCell celdaTablaPagos = new PdfPCell(tablaGeneralPagos);
        celdaTablaPagos.setBorder(PdfPCell.NO_BORDER);
        tablagastos.addCell(celdaTablaPagos);
        
        //agregar tabla de gastos al documento
        documento.add(tablagastos);
        
        //agregar parrafo para salto de linea
        documento.add(saltoLinea);
        documento.add(saltoLinea);

        //Tbla cadena original del complemento de certificación digital del SAT:
        PdfPTable tablaCertificacionSat = new PdfPTable(1);
        tablaCertificacionSat.setWidthPercentage(100);
        
        //Encabezado para tabla del sello certificacion SAT
        Paragraph tituloCadenaSat = new Paragraph(8);
        tituloCadenaSat.add(new Chunk("Cadena original del complemento de certificación digital del SAT:",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10,BaseColor.WHITE)));
        PdfPCell celdaTituloCertificacionSat = new PdfPCell();
        celdaTituloCertificacionSat.addElement(tituloCadenaSat);
        celdaTituloCertificacionSat.setBackgroundColor(rojoPersonalizado);
        celdaTituloCertificacionSat.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.TOP | PdfPCell.BOTTOM);
        celdaTituloCertificacionSat.setBorderColor(rojoPersonalizado);
        tablaCertificacionSat.addCell(celdaTituloCertificacionSat);
               
        //-----PARRAFO PARA CERTIFICACION SAT
        Paragraph selloCertificacionSat = new Paragraph(8);
        selloCertificacionSat.add(new Chunk(factura.getCadena_original_complemento_certificacion_digital_SAT(), FontFactory.getFont(FontFactory.HELVETICA, 7, BaseColor.BLACK)));
        PdfPCell celdaCertificacionSat = new PdfPCell();
        celdaCertificacionSat.addElement(selloCertificacionSat);
        celdaCertificacionSat.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.TOP | PdfPCell.BOTTOM);
        celdaCertificacionSat.setBorderColor(rojoPersonalizado);
        
        //agregar el sello a la tabla
        tablaCertificacionSat.addCell(celdaCertificacionSat);

        //agregarTabla al documento
        documento.add(tablaCertificacionSat);
        
        Paragraph espaciadoTablas = new Paragraph(3);
        espaciadoTablas.add(new Chunk("\n ",FontFactory.getFont(FontFactory.HELVETICA, 10,BaseColor.WHITE)));
        
        documento.add(espaciadoTablas);
        
        //Tabla para los sellos y el QR
        PdfPTable tablaSellosQr = new PdfPTable(2);
        float[] anchosColumnasTablaSellosQr= {25f,75f};
        tablaSellosQr.setWidths(anchosColumnasTablaSellosQr);
        tablaSellosQr.setWidthPercentage(100);
        
        //-------AGREGAR QR
        Image qr = Image.getInstance("src/img/qr.png");
        qr.scaleToFit(120, 120);
        PdfPCell celdaQR = new PdfPCell(qr);
        celdaQR.setBorder(PdfPCell.NO_BORDER);
        celdaQR.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celdaQR.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        //Agregar QR
        tablaSellosQr.addCell(celdaQR);
        
        //Tabla para sello digital del CFDI:
        PdfPTable tablaSellos = new PdfPTable(1);
        
        //Encabezado para la tabla del sello digital
        Paragraph tituloSelloDigitalCFDI = new Paragraph(8);
        tituloSelloDigitalCFDI.add(new Chunk("Sello digital del CFDI:",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10,BaseColor.WHITE)));
        PdfPCell celdaTituloSelloCFDI = new PdfPCell();
        celdaTituloSelloCFDI.addElement(tituloSelloDigitalCFDI);
        celdaTituloSelloCFDI.setBackgroundColor(rojoPersonalizado);
        celdaTituloSelloCFDI.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.TOP | PdfPCell.BOTTOM);
        celdaTituloSelloCFDI.setBorderColor(rojoPersonalizado);
        tablaSellos.addCell(celdaTituloSelloCFDI);

        //--------------SELLO CFDI
        //Encabezado para la tabla del sello digital
        Paragraph SelloDigitalCFDI = new Paragraph(8);
        SelloDigitalCFDI.add(new Chunk(factura.getSello_digital_CFDI(), FontFactory.getFont(FontFactory.HELVETICA, 7, BaseColor.BLACK)));
        PdfPCell celdaSelloCFDI = new PdfPCell();
        celdaSelloCFDI.addElement(SelloDigitalCFDI);
        celdaSelloCFDI.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.TOP | PdfPCell.BOTTOM);
        celdaSelloCFDI.setBorderColor(rojoPersonalizado);
        tablaSellos.addCell(celdaSelloCFDI);
        
        //Separacion de tablas
        PdfPCell celdaSeparacion = new PdfPCell(espaciadoTablas);
        celdaSeparacion.setBorder(PdfPCell.NO_BORDER);
        celdaSeparacion.setFixedHeight(5f);
        tablaSellos.addCell(celdaSeparacion);

        //---Encabezado para la tabla Sello del SAT:
        Paragraph tituloSelloSAT = new Paragraph(8);
        tituloSelloSAT.add(new Chunk("Sello del SAT:",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10,BaseColor.WHITE)));
        PdfPCell celdaTituloSelloSAT = new PdfPCell();
        celdaTituloSelloSAT.addElement(tituloSelloSAT);
        celdaTituloSelloSAT.setBackgroundColor(rojoPersonalizado);
        celdaTituloSelloSAT.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.TOP | PdfPCell.BOTTOM);
        celdaTituloSelloSAT.setBorderColor(rojoPersonalizado);
        tablaSellos.addCell(celdaTituloSelloSAT);
        
        
        //-------SELLO DEL SAT
        Paragraph SelloSAT = new Paragraph(8);
        SelloSAT.add(new Chunk(factura.getSello_SAT(), FontFactory.getFont(FontFactory.HELVETICA, 7, BaseColor.BLACK)));
        PdfPCell celdaSelloSAT = new PdfPCell();
        celdaSelloSAT.addElement(SelloSAT);
        celdaSelloSAT.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.TOP | PdfPCell.BOTTOM);
        celdaSelloSAT.setBorderColor(rojoPersonalizado);
        tablaSellos.addCell(celdaSelloSAT);

        //Celda para los sellos
        PdfPCell celdaSellos = new PdfPCell(tablaSellos);
        celdaSellos.setBorder(PdfPCell.NO_BORDER);
        tablaSellosQr.addCell(celdaSellos);

        //agregar tabla de sellos al documento
        documento.add(tablaSellosQr);
        
        //Tabla para el pie de pagina
        PdfPTable tablaPiePagina = new PdfPTable(3);
        
        //Celda para el link de la empresa
        Paragraph paginaEmpresa = new Paragraph(new Chunk("https://mtasolutions.mx/",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10,BaseColor.BLACK)));
        PdfPCell celdaPaginaEmpresa = new PdfPCell(paginaEmpresa);
        
        tablaPiePagina.addCell(celdaPaginaEmpresa);
        tablaPiePagina.addCell(celdaPaginaEmpresa);
        tablaPiePagina.addCell(celdaPaginaEmpresa);
        
        documento.add(tablaPiePagina);
        // Cerrar el documento
        documento.close();
        
//        Correo enviarCorreo = new Correo("", rutaArchivo);
//        enviarCorreo.envioDeCorreos();
//        
        // Abrir el archivo generado
        File path = new File(rutaArchivo);
        if (path.exists()) {
            Desktop.getDesktop().open(path);
        } else {
            System.out.println("Error: No se pudo generar el archivo.");
        }
    }

//    public static void main(String[] args) {
//        try {
//            FacturaPDF pdf = new FacturaPDF();
//            pdf.generarFacturaPDF("C:\\Users\\ar275\\Documents\\Generador de facturas");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
