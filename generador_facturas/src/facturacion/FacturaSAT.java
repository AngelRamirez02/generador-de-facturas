/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package facturacion;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author ar275
 */
public class FacturaSAT {

    public String generarFacturaPdfSAT(Factura factura, Emisor emisor, Receptor receptor) throws FileNotFoundException, DocumentException, IOException {
    
        // Obtener la carpeta de Descargas del usuario
        String rutaDescargas = System.getProperty("user.home") + File.separator + "Downloads";
        // Ruta para guardar el documento
        String rutaArchivo = rutaDescargas + File.separator + "Factura_SAT" + ".pdf";

        //color rojo personalizado de la escuela
        BaseColor gris = new BaseColor(191,191,191); // Usando RGB

        // Crear documento
        Document documento = new Document();
        FileOutputStream ficheroPdf = new FileOutputStream(rutaArchivo);
        PdfWriter.getInstance(documento, ficheroPdf);

        // Abrir el documento
        documento.open();
        
        //Tabla de datos generales
        PdfPTable tablaDatosGenerales = new PdfPTable(2);
        tablaDatosGenerales.setWidthPercentage(100);
        
        //tabla izq
        PdfPTable tablaIzq = new PdfPTable(2);
        //-------RFC EMISOR---------
        Paragraph tituloRfcEmisor = new Paragraph("RFC emisor:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, BaseColor.BLACK));
        PdfPCell celdaTituloRFcEmisor = new PdfPCell(tituloRfcEmisor);
        celdaTituloRFcEmisor.setBorder(PdfPCell.NO_BORDER);
        tablaIzq.addCell(celdaTituloRFcEmisor);
        
        Paragraph rfcEmisor = new Paragraph(emisor.getRfc(), FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK));
        PdfPCell celdaRFcEmisor = new PdfPCell(rfcEmisor);
        celdaRFcEmisor.setBorder(PdfPCell.NO_BORDER);
        tablaIzq.addCell(celdaRFcEmisor);
        
          //------NOMBRE EMISOR
        Paragraph tituloNombreEmisor = new Paragraph("Nombre emisor:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, BaseColor.BLACK));
        PdfPCell celdaTituloNombreEmisor = new PdfPCell(tituloNombreEmisor);
        celdaTituloNombreEmisor.setBorder(PdfPCell.NO_BORDER);
        tablaIzq.addCell(celdaTituloNombreEmisor);
        
        Paragraph nombreEmisor = new Paragraph(emisor.getNombreCompletoMayus(), FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK));
        PdfPCell celdaNombreEmisor = new PdfPCell(nombreEmisor);
        celdaNombreEmisor.setBorder(PdfPCell.NO_BORDER);
        tablaIzq.addCell(celdaNombreEmisor);
        
        
       //-------FOLIO
        Paragraph Titulofolio = new Paragraph("Folio:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, BaseColor.BLACK));
        PdfPCell celdaTituloFolio = new PdfPCell(Titulofolio);
        celdaTituloFolio.setBorder(PdfPCell.NO_BORDER);
        tablaIzq.addCell(celdaTituloFolio);
        
        Paragraph folio = new Paragraph(""+factura.getId_factura(), FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK));
        PdfPCell celdaFolio = new PdfPCell(folio);
        celdaFolio.setBorder(PdfPCell.NO_BORDER);
        tablaIzq.addCell(celdaFolio);
        
         //--RFC RECEPRTOR
        Paragraph tituloRfcRecptor = new Paragraph("RFC receptor:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, BaseColor.BLACK));
        PdfPCell celdaTituloRFcReceptor = new PdfPCell(tituloRfcRecptor);
        celdaTituloRFcReceptor.setBorder(PdfPCell.NO_BORDER);
        tablaIzq.addCell(celdaTituloRFcReceptor);
        
        Paragraph rfcReceptor = new Paragraph(receptor.getRfc(), FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK));
        PdfPCell celdaRfcReceptor= new PdfPCell(rfcReceptor);
        celdaRfcReceptor.setBorder(PdfPCell.NO_BORDER);
        tablaIzq.addCell(celdaRfcReceptor);
        
       //-----------NOMBRE RECEPTOR
        Paragraph tituloNombreRecptor = new Paragraph("Nombre receptor:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, BaseColor.BLACK));
        PdfPCell celdaTituloNombreReceptor = new PdfPCell(tituloNombreRecptor);
        celdaTituloNombreReceptor.setBorder(PdfPCell.NO_BORDER);
        tablaIzq.addCell(celdaTituloNombreReceptor);
        
        Paragraph NombreReceptor = new Paragraph(receptor.getNombreCompletoMayus(), FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK));
        PdfPCell celdaNombreReceptor= new PdfPCell(NombreReceptor);
        celdaNombreReceptor.setBorder(PdfPCell.NO_BORDER);
        tablaIzq.addCell(celdaNombreReceptor);
        
        //---CP DEL RECEPTOR
        Paragraph tituloCpReceptor = new Paragraph("Código postal del receptor:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, BaseColor.BLACK));
        PdfPCell celdaTituloCpReceptor = new PdfPCell(tituloCpReceptor);
        celdaTituloCpReceptor.setBorder(PdfPCell.NO_BORDER);
        tablaIzq.addCell(celdaTituloCpReceptor);
        
        Paragraph cpReceptor= new Paragraph(receptor.getDomicilio_fiscal(), FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK));
        PdfPCell celdaCpReceptor = new PdfPCell(cpReceptor);
        celdaCpReceptor.setBorder(PdfPCell.NO_BORDER);
        tablaIzq.addCell(celdaCpReceptor);
        
        //-----Régimen fiscal receptor
        Paragraph tituloRegimenReceptor = new Paragraph("Régimen fiscal receptor:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, BaseColor.BLACK));
        PdfPCell celdaTituloRegimenReceptor = new PdfPCell(tituloRegimenReceptor);
        celdaTituloRegimenReceptor.setBorder(PdfPCell.NO_BORDER);
        tablaIzq.addCell(celdaTituloRFcReceptor);
        
        Paragraph RegimenReceptor = new Paragraph(receptor.getRegimen(), FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK));
        PdfPCell celdaRegimenReceptor = new PdfPCell(RegimenReceptor);
        celdaRegimenReceptor.setBorder(PdfPCell.NO_BORDER);
        tablaIzq.addCell(celdaRegimenReceptor);
        
          //-----Uso CFDI
        Paragraph tituloUsoCFDI = new Paragraph("Uso CFDI:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, BaseColor.BLACK));
        PdfPCell celdaTituloUsoCFDI = new PdfPCell(tituloUsoCFDI);
        celdaTituloUsoCFDI.setBorder(PdfPCell.NO_BORDER);
        tablaIzq.addCell(celdaTituloUsoCFDI);
        
        Paragraph usoCFDI = new Paragraph(receptor.getUso_CFDI(), FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK));
        PdfPCell celdaUsoCFDI = new PdfPCell(usoCFDI);
        celdaUsoCFDI.setBorder(PdfPCell.NO_BORDER);
        tablaIzq.addCell(celdaUsoCFDI);
        
        //CELDA PARA AGREGAR A LA TABLA GENERAL
        PdfPCell celdaIzq = new PdfPCell(tablaIzq);
        celdaIzq.setBorder(PdfPCell.NO_BORDER);
        tablaDatosGenerales.addCell(celdaIzq);
        
        PdfPTable tablaDer = new PdfPTable(2);
        
        //------FOLIO FISCAL-----------
        Paragraph tituloFolioFiscal = new Paragraph("Folio fiscal:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, BaseColor.BLACK));
        PdfPCell celdaTituloFolioFiscal = new PdfPCell(tituloFolioFiscal);
        celdaTituloFolioFiscal.setBorder(PdfPCell.NO_BORDER);
        tablaDer.addCell(celdaTituloFolioFiscal);
        
        Paragraph FolioFiscal = new Paragraph(factura.getFolioSat(), FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK));
        PdfPCell celdaFolioFiscal = new PdfPCell(FolioFiscal);
        celdaFolioFiscal.setBorder(PdfPCell.NO_BORDER);
        tablaDer.addCell(celdaFolioFiscal);
        
        //-----------No. de serie del CSD:
        Paragraph tituloNoCSD = new Paragraph("No. de serie del CSD:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, BaseColor.BLACK));
        PdfPCell celdaTituloNoCSD = new PdfPCell(tituloNoCSD);
        celdaTituloNoCSD.setBorder(PdfPCell.NO_BORDER);
        tablaDer.addCell(celdaTituloNoCSD);
        
        Paragraph noCSD = new Paragraph(factura.getNumero_serie_certificado_emisor(), FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK));
        PdfPCell celdaNoCSD = new PdfPCell(noCSD);
        celdaNoCSD.setBorder(PdfPCell.NO_BORDER);
        tablaDer.addCell(celdaNoCSD);

        //-------SERIE
        Paragraph TituloSerie = new Paragraph("Serie:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, BaseColor.BLACK));
        PdfPCell celdaTituloSerie= new PdfPCell(TituloSerie);
        celdaTituloSerie.setBorder(PdfPCell.NO_BORDER);
        tablaDer.addCell(celdaTituloSerie);
        
        Paragraph serie = new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK));
        PdfPCell celdaSerie = new PdfPCell(serie);
        celdaSerie.setBorder(PdfPCell.NO_BORDER);
        tablaDer.addCell(celdaSerie);
        
        //---CP Y FECHA EXPEDICION
        Paragraph tituloCpFechaExp = new Paragraph("Código postal, fecha y hora de emisión:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, BaseColor.BLACK));
        PdfPCell celdaTituloCpFechaExp = new PdfPCell(tituloCpFechaExp);
        celdaTituloCpFechaExp.setBorder(PdfPCell.NO_BORDER);
        tablaDer.addCell(celdaTituloCpFechaExp);
        
        Paragraph cpFechaEmision = new Paragraph("39890 "+factura.getFechaHoraSellada(), FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK));
        PdfPCell celdaFechaEmison = new PdfPCell(cpFechaEmision);
        celdaFechaEmison.setBorder(PdfPCell.NO_BORDER);
        tablaDer.addCell(celdaFechaEmison);
           
        //-----EFECTO DE COMPROBANTE
        Paragraph tituloEfectoCompro= new Paragraph("Efecto de comprobante:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, BaseColor.BLACK));
        PdfPCell celdaTituloEfctComprobante = new PdfPCell(tituloEfectoCompro);
        celdaTituloEfctComprobante.setBorder(PdfPCell.NO_BORDER);
        tablaDer.addCell(celdaTituloEfctComprobante);
        
        Paragraph efectComprobante = new Paragraph(factura.getTipo_comprobante(), FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK));
        PdfPCell celdaEfectComprobante = new PdfPCell(efectComprobante);
        celdaEfectComprobante.setBorder(PdfPCell.NO_BORDER);
        tablaDer.addCell(celdaEfectComprobante);
        
        //-------REGIMEN
        Paragraph tituloRegimen = new Paragraph("Régimen fiscal:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, BaseColor.BLACK));
        PdfPCell celdaTituloRegimen = new PdfPCell(tituloRegimen);
        celdaTituloRegimen.setBorder(PdfPCell.NO_BORDER);
        tablaDer.addCell(celdaTituloRegimen);
        
        Paragraph Regimen = new Paragraph(emisor.getRegimen(), FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK));
        PdfPCell celdaRegimen = new PdfPCell(Regimen);
        celdaRegimen.setBorder(PdfPCell.NO_BORDER);
        tablaDer.addCell(celdaRegimen);
        
        //----EXPORTACION
        Paragraph tituloExportacion = new Paragraph("Exportacion:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, BaseColor.BLACK));
        PdfPCell celdaTituloExportacion = new PdfPCell(tituloExportacion);
        celdaTituloExportacion.setBorder(PdfPCell.NO_BORDER);
        tablaDer.addCell(celdaTituloExportacion);
        
        Paragraph Exportacion = new Paragraph("No aplica", FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK));
        PdfPCell celdaExportacion = new PdfPCell(Exportacion);
        celdaExportacion.setBorder(PdfPCell.NO_BORDER);
        tablaDer.addCell(celdaExportacion);
        
        //CELDA PARA AGREGAR A LA TABLA GENERAL
        PdfPCell celdaDer = new PdfPCell(tablaDer);
        celdaDer.setBorder(PdfPCell.NO_BORDER);
        tablaDatosGenerales.addCell(celdaDer);
        
        //agregar tabla de datos generales
        documento.add(tablaDatosGenerales);
        
        //TABLA PARA DESCRIBIR PRODUCTOS
        Paragraph conceptos = new Paragraph("Conceptos\n ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, BaseColor.BLACK));
        documento.add(conceptos);
        
        PdfPTable tablaConceptos = new PdfPTable(9);
        tablaConceptos.setWidthPercentage(100);
        
        //celdas
        Paragraph tituloClaveServicio = new Paragraph("Clave del producto y/o servicio", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 6, BaseColor.BLACK));
        PdfPCell celdaClaveProcto = new PdfPCell(tituloClaveServicio);
        celdaClaveProcto.setHorizontalAlignment(Element.ALIGN_CENTER);
        celdaClaveProcto.setBackgroundColor(gris);
        tablaConceptos.addCell(celdaClaveProcto);
     
        Paragraph tituloNoIdent = new Paragraph("No indetificacion", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 6, BaseColor.BLACK));
        PdfPCell celdatituloNoIdent = new PdfPCell(tituloNoIdent);
        celdatituloNoIdent.setHorizontalAlignment(Element.ALIGN_CENTER);
        celdatituloNoIdent.setBackgroundColor(gris);
        tablaConceptos.addCell(celdatituloNoIdent);
     
        Paragraph tituloCant = new Paragraph("Cantidad", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 6, BaseColor.BLACK));
        PdfPCell celdatituloCant = new PdfPCell(tituloCant);
        celdatituloCant.setHorizontalAlignment(Element.ALIGN_CENTER);
        celdatituloCant.setBackgroundColor(gris);
        tablaConceptos.addCell(celdatituloCant);
        
        Paragraph tituloClaveUnidad = new Paragraph("Clave unidad", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 6, BaseColor.BLACK));
        PdfPCell celdatituloClaveUnidad = new PdfPCell(tituloClaveUnidad);
        celdatituloClaveUnidad.setHorizontalAlignment(Element.ALIGN_CENTER);
        celdatituloClaveUnidad.setBackgroundColor(gris);
        tablaConceptos.addCell(celdatituloClaveUnidad);
        
        Paragraph tituloUnidad = new Paragraph("Unidad", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 6, BaseColor.BLACK));
        PdfPCell celdatituloUnidad = new PdfPCell(tituloUnidad);
        celdatituloUnidad.setHorizontalAlignment(Element.ALIGN_CENTER);
        celdatituloUnidad.setBackgroundColor(gris);
        tablaConceptos.addCell(celdatituloUnidad);
        
        Paragraph tituloValorUnitario = new Paragraph("Valor unitario", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 6, BaseColor.BLACK));
        PdfPCell celdatituloValorUnitario = new PdfPCell(tituloValorUnitario);
        celdatituloValorUnitario.setHorizontalAlignment(Element.ALIGN_CENTER);
        celdatituloValorUnitario.setBackgroundColor(gris);
        tablaConceptos.addCell(celdatituloValorUnitario);
        
        Paragraph tituloImporte = new Paragraph("Importe", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 6, BaseColor.BLACK));
        PdfPCell celdatituloImporte = new PdfPCell(tituloImporte);
        celdatituloImporte.setHorizontalAlignment(Element.ALIGN_CENTER);
        celdatituloImporte.setBackgroundColor(gris);
        tablaConceptos.addCell(celdatituloImporte);
        
        Paragraph tituloDescuento = new Paragraph("Descuento", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 6, BaseColor.BLACK));
        PdfPCell celdatituloDescuento = new PdfPCell(tituloDescuento);
        celdatituloDescuento.setHorizontalAlignment(Element.ALIGN_CENTER);
        celdatituloDescuento.setBackgroundColor(gris);
        tablaConceptos.addCell(celdatituloDescuento);
        
        Paragraph titulo_objImpuestos = new Paragraph("Obj imppuestos", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 6, BaseColor.BLACK));
        PdfPCell celdatitulo_obj = new PdfPCell(titulo_objImpuestos);
        celdatitulo_obj.setHorizontalAlignment(Element.ALIGN_CENTER);
        celdatitulo_obj.setBackgroundColor(gris);
        tablaConceptos.addCell(celdatitulo_obj);
        
        //AGREGA LOS DATOS A LA TABLA
        String claves[] = factura.getUnidad().split("/");
        tablaConceptos.addCell(new Paragraph(factura.getClave(), FontFactory.getFont(FontFactory.HELVETICA, 6, BaseColor.BLACK)));
        tablaConceptos.addCell(new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 6, BaseColor.BLACK)));
        tablaConceptos.addCell(new Paragraph(factura.getCantidad(), FontFactory.getFont(FontFactory.HELVETICA, 6, BaseColor.BLACK)));
        tablaConceptos.addCell(new Paragraph(claves[0], FontFactory.getFont(FontFactory.HELVETICA, 6, BaseColor.BLACK)));
        tablaConceptos.addCell(new Paragraph(claves[1], FontFactory.getFont(FontFactory.HELVETICA, 6, BaseColor.BLACK)));
        tablaConceptos.addCell(new Paragraph(""+factura.getPrecio_unitario(), FontFactory.getFont(FontFactory.HELVETICA, 6, BaseColor.BLACK)));
        tablaConceptos.addCell(new Paragraph(""+factura.getImporte(), FontFactory.getFont(FontFactory.HELVETICA, 6, BaseColor.BLACK)));
        tablaConceptos.addCell(new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 6, BaseColor.BLACK)));
        tablaConceptos.addCell(new Paragraph(factura.getObj_impuestos(), FontFactory.getFont(FontFactory.HELVETICA, 6, BaseColor.BLACK)));
        documento.add(tablaConceptos);

        PdfPTable tablaDescrip = new PdfPTable(2);
        float[] anchosColumnasTablaDescrip= {20,80};
        tablaDescrip.setWidths(anchosColumnasTablaDescrip);
        tablaDescrip.setWidthPercentage(67);
        tablaDescrip.setHorizontalAlignment(Element.ALIGN_LEFT);
        
        Paragraph tituloDescrip = new Paragraph("Descripción", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 6, BaseColor.BLACK));
        PdfPCell celdaTituloDescrip = new PdfPCell(tituloDescrip);
        celdaTituloDescrip.setBackgroundColor(gris);
        celdaTituloDescrip.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        Paragraph Descrip = new Paragraph(factura.getDescripcion(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 6, BaseColor.BLACK));
        PdfPCell celdaDescrip = new PdfPCell(Descrip);
        celdaDescrip.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        tablaDescrip.addCell(celdaTituloDescrip);
        tablaDescrip.addCell(celdaDescrip);
        
        documento.add(tablaDescrip);
        
        PdfPTable tablaPagos = new PdfPTable(2);
        tablaPagos.setWidthPercentage(100);
        
        
        PdfPTable tablaPagosIzq = new PdfPTable(2);
        //----MONEDA
        Paragraph tituloMoneda = new Paragraph("Moneda", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, BaseColor.BLACK));
        PdfPCell celdaTituloMoneda = new PdfPCell(tituloMoneda);
        celdaTituloMoneda.setPaddingTop(15);
        celdaTituloMoneda.setBorder(PdfPCell.NO_BORDER);
        tablaPagosIzq.addCell(celdaTituloMoneda);
        
        Paragraph Moneda = new Paragraph("Peso Mexicano", FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK));
        PdfPCell celdaMoneda = new PdfPCell(Moneda);
        celdaMoneda.setPaddingTop(15);
        celdaMoneda.setBorder(PdfPCell.NO_BORDER);
        tablaPagosIzq.addCell(celdaMoneda);
        
        //------------Forma de pago
        Paragraph tituloFormaPago = new Paragraph("Forma de pago:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, BaseColor.BLACK));
        PdfPCell celdaTituloFormaPago= new PdfPCell(tituloFormaPago);
        celdaTituloFormaPago.setBorder(PdfPCell.NO_BORDER);
        tablaPagosIzq.addCell(celdaTituloFormaPago);
        
        Paragraph FormaPago = new Paragraph(factura.getForma_pago(), FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK));
        PdfPCell celdaFormaPago= new PdfPCell(FormaPago);
        celdaFormaPago.setBorder(PdfPCell.NO_BORDER);
        tablaPagosIzq.addCell(celdaFormaPago);
        
        //-------Metodo de pago
        Paragraph tituloMetodoPago = new Paragraph("Método de pago:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, BaseColor.BLACK));
        PdfPCell celdaTituloMetodoPago= new PdfPCell(tituloMetodoPago);
        celdaTituloMetodoPago.setBorder(PdfPCell.NO_BORDER);
        tablaPagosIzq.addCell(celdaTituloMetodoPago);
        
        Paragraph MetodoPago = new Paragraph(factura.getMetodo_pago(), FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK));
        PdfPCell celdaMetodoPago= new PdfPCell(MetodoPago);
        celdaMetodoPago.setBorder(PdfPCell.NO_BORDER);
        tablaPagosIzq.addCell(celdaMetodoPago);
        //agregar la tabla izq a la tabla general
        PdfPCell celdaPagosIzq = new PdfPCell(tablaPagosIzq);
        celdaPagosIzq.setBorder(PdfPCell.NO_BORDER);
        tablaPagos.addCell(celdaPagosIzq);
       
        
        PdfPTable tablaPagosDer = new PdfPTable(2);
       
        //---SUBTOTAL
        Paragraph tituloSubtotal = new Paragraph("Subtotal", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, BaseColor.BLACK));
        PdfPCell celdaTituloSubtotal = new PdfPCell(tituloSubtotal);
        celdaTituloSubtotal.setPaddingTop(15);
        celdaTituloSubtotal.setBorder(PdfPCell.NO_BORDER);
        tablaPagosDer.addCell(celdaTituloSubtotal);
        
        Paragraph Subtotal = new Paragraph(""+factura.getSubtotal(), FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK));
        PdfPCell celdaSubtotal = new PdfPCell(Subtotal);
        celdaSubtotal.setPaddingTop(15);
        celdaSubtotal.setBorder(PdfPCell.NO_BORDER);
        tablaPagosDer.addCell(celdaSubtotal);
        
        //-------TOTAL
        Paragraph tituloTotal = new Paragraph("Total", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, BaseColor.BLACK));
        PdfPCell celdaTituloTotal = new PdfPCell(tituloTotal);
        celdaTituloTotal.setBorder(PdfPCell.NO_BORDER);
        tablaPagosDer.addCell(celdaTituloTotal);
        
        Paragraph Total = new Paragraph(""+factura.getTotal(), FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK));
        PdfPCell celdaTotal = new PdfPCell(Total);
        celdaTotal.setBorder(PdfPCell.NO_BORDER);
        tablaPagosDer.addCell(celdaTotal);
        
        //agregar tabla izq
        PdfPCell celdaPagosDer = new PdfPCell(tablaPagosDer);
        celdaPagosDer.setBorder(PdfPCell.NO_BORDER);
        tablaPagos.addCell(celdaPagosDer);
        //agregar tabla de pagos
        documento.add(tablaPagos);
        
        //Tbla cadena original del complemento de certificación digital del SAT:
        PdfPTable tablaCertificacionSat = new PdfPTable(1);
        tablaCertificacionSat.setWidthPercentage(100);
        
        //Encabezado para tabla del sello certificacion SAT
        Paragraph tituloSelloCFDI = new Paragraph(8);
        tituloSelloCFDI.add(new Chunk("Sello digital del CFDI:",FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10,BaseColor.BLACK)));
        PdfPCell celdaTituloSelloCFDI = new PdfPCell();
        celdaTituloSelloCFDI.setPaddingTop(30);
        celdaTituloSelloCFDI.addElement(tituloSelloCFDI);
       celdaTituloSelloCFDI.setBorder(PdfPCell.NO_BORDER);
        tablaCertificacionSat.addCell(celdaTituloSelloCFDI);

        //-----PARRAFO PARA CERTIFICACION SAT
        Paragraph selloCFDI = new Paragraph(8);
        selloCFDI.add(new Chunk(factura.getSello_digital_CFDI(), FontFactory.getFont(FontFactory.HELVETICA, 7, BaseColor.BLACK)));
        PdfPCell celdaSelloCFDI = new PdfPCell();
        celdaSelloCFDI.addElement(selloCFDI);
        celdaSelloCFDI.setBorder(PdfPCell.NO_BORDER);
        tablaCertificacionSat.addCell(celdaSelloCFDI);

        documento.add(tablaCertificacionSat);
        
        //---TABLA SELLOS SAT
         PdfPTable tablaSelloSat = new PdfPTable(1);
        tablaSelloSat.setWidthPercentage(100);
        
        //Encabezado para tabla del sello certificacion SAT
        Paragraph tituloSelloSAT = new Paragraph();
        tituloSelloSAT.add(new Chunk("Sello digital del SAT:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK)));
        PdfPCell celdaTituloSelloSAT = new PdfPCell();
        celdaTituloSelloSAT.addElement(tituloSelloSAT);
        celdaTituloSelloSAT.setBorder(PdfPCell.NO_BORDER);
        tablaSelloSat.addCell(celdaTituloSelloSAT);

        //-----PARRAFO PARA CERTIFICACION SAT
        Paragraph selloSAT = new Paragraph(8);
        selloSAT.add(new Chunk(factura.getSello_SAT(), FontFactory.getFont(FontFactory.HELVETICA, 7, BaseColor.BLACK)));
        PdfPCell celdaSelloSAT = new PdfPCell();
        celdaSelloSAT.addElement(selloSAT);
        celdaSelloSAT.setBorder(PdfPCell.NO_BORDER);
        tablaSelloSat.addCell(celdaSelloSAT);

        documento.add(tablaSelloSat);
        
        ///------TABLA PARA EL QR
         //Tabla para los sellos y el QR
        PdfPTable tablaSellosQr = new PdfPTable(2);
        float[] anchosColumnasTablaSellosQr= {25f,75f};
        tablaSellosQr.setWidths(anchosColumnasTablaSellosQr);
        tablaSellosQr.setWidthPercentage(100);
        
        //-------AGREGAR QR
        InputStream is2 = getClass().getResourceAsStream("/img/qr.png");
        Image qr = Image.getInstance(is2.readAllBytes());
        is2.close(); // Cierra el InputStream
        
        qr.scaleToFit(120, 120);
        PdfPCell celdaQR = new PdfPCell(qr);
        celdaQR.setBorder(PdfPCell.NO_BORDER);
        celdaQR.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celdaQR.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        //Agregar QR
        tablaSellosQr.addCell(celdaQR);
        
        //Tabla Cadena Original del complemento de certificación digital del SAT
        PdfPTable tablaSellos = new PdfPTable(1);
        
        //Encabezado para tabla de la Cadena Original del complemento de certificación digital del SAT
        Paragraph tituloCadenaSAT = new Paragraph();
        tituloCadenaSAT.add(new Chunk("Cadena Original del complemento de certificación digital del SAT:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK)));
        PdfPCell celdaTituloCadenaSAT = new PdfPCell();
        celdaTituloCadenaSAT.addElement(tituloCadenaSAT);
        celdaTituloCadenaSAT.setBorder(PdfPCell.NO_BORDER);
        tablaSellos.addCell(celdaTituloCadenaSAT);

        Paragraph CadenaSAT = new Paragraph(8);
        CadenaSAT.add(new Chunk(factura.getCadena_original_complemento_certificacion_digital_SAT(), FontFactory.getFont(FontFactory.HELVETICA, 7, BaseColor.BLACK)));
        PdfPCell celdaCadenaSAT = new PdfPCell();
        celdaCadenaSAT.addElement(CadenaSAT);
        celdaCadenaSAT.setBorder(PdfPCell.NO_BORDER);
        tablaSellos.addCell(celdaCadenaSAT);

        //TABLA PARA FECHA Y HORA Y SERIE SAT
        //Celda para los sellos
        PdfPCell celdaSellos = new PdfPCell(tablaSellos);
        celdaSellos.setBorder(PdfPCell.NO_BORDER);
        tablaSellosQr.addCell(celdaSellos);

        //agregar tabla de sellos al documento
        documento.add(tablaSellosQr);

        //Tabla para el pie de pagina
        PdfPTable tablaPiePagina = new PdfPTable(3);
        float[] anchosColumnasTablaPiePagina= {25f,60f,15f};
        tablaPiePagina.setWidths(anchosColumnasTablaPiePagina);
        tablaPiePagina.setWidthPercentage(100);

        // Celda para el link de la empresa
        Paragraph paginaEmpresa = new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK));
        PdfPCell celdaPaginaEmpresa = new PdfPCell(paginaEmpresa);
        celdaPaginaEmpresa.setBorder(PdfPCell.NO_BORDER);
        celdaPaginaEmpresa.setPaddingTop(40);
        celdaPaginaEmpresa.setHorizontalAlignment(Element.ALIGN_CENTER); 
        tablaPiePagina.addCell(celdaPaginaEmpresa);
        
        //Celda para mensaje de CFDI
        // Celda para el link de la empresa
        Paragraph msjCFDI = new Paragraph("Este documento es una representación impresa de un CFDI", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, BaseColor.BLACK));
        PdfPCell celdamsjCFDI = new PdfPCell(msjCFDI);
        celdamsjCFDI.setBorder(PdfPCell.NO_BORDER);
        celdamsjCFDI.setPaddingTop(40);
        celdamsjCFDI.setHorizontalAlignment(Element.ALIGN_CENTER); 
        tablaPiePagina.addCell(celdamsjCFDI);
        
        // Celda para el link de la empresa
        Paragraph numPagina = new Paragraph("Página 1 de 1", FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK));
        PdfPCell celdaNumPagina = new PdfPCell(numPagina);
        celdaNumPagina.setBorder(PdfPCell.NO_BORDER);
        celdaNumPagina.setPaddingTop(40);
        celdaNumPagina.setHorizontalAlignment(Element.ALIGN_CENTER); 
        tablaPiePagina.addCell(celdaNumPagina);

        documento.add(tablaPiePagina);
        
        documento.close();
        
         // Abrir el archivo generado
        File path = new File(rutaArchivo);
        if (path.exists()) {
            Desktop.getDesktop().open(path);
        } else {
            System.out.println("Error: No se pudo generar el archivo.");
        }
        return rutaArchivo;
    }
//    public static void main(String[] args) throws DocumentException, IOException {
//        FacturaSAT f = new FacturaSAT();
//        System.out.println(f.generarFacturaPdfSAT("C:\\Users\\ar275\\Documents\\Generador de facturas",null));
//    }
}
