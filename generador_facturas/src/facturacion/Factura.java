/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package facturacion;

/**
 *
 * @author ar275
 */
public class Factura {
    private int id_factura;
    private String forma_pago;
    private String metodo_pago;
    private String tipo_comprobante;
    private String cantidad;
    private String unidad;
    private String clave;
    private String descripcion;
    private String obj_impuestos;
    private double precio_unitario;
    private double importe;
    private double subtotal;
    private double impuesto;
    private double descuento;
    private double total;
    private String total_letras;
    private String folioSat;
    private String numero_serie_certificado_emisor;
    private String numero_serie_certificado_SAT;
    private String cadena_original_complemento_certificacion_digital_SAT;
    private String sello_digital_CFDI;
    private String sello_SAT;
    private String fechaHoraSellada;

    public Factura(String forma_pago, String metodo_pago,String tipo_comprobante ,String cantidad, String unidad, String clave, String descripcion, String obj_impuestos, double precio_unitario, double importe, double impuesto, double descuento) {
        this.forma_pago = forma_pago;
        this.metodo_pago = metodo_pago;
        this.tipo_comprobante=tipo_comprobante;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.clave = clave;
        this.descripcion = descripcion;
        this.obj_impuestos = obj_impuestos;
        this.precio_unitario = precio_unitario;
        this.importe = importe;
        this.impuesto = impuesto;
        this.descuento = descuento;
        obtenerTotal();
    }

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public String getTotal_letras() {
        return total_letras;
    }

    
    public void setFechaHoraSellada(String fechaHoraSellada) {
        this.fechaHoraSellada = fechaHoraSellada;
    }

    public String getFechaHoraSellada() {
        return fechaHoraSellada;
    }
    
    public double getTotal() {
        return total;
    }

    private void obtenerTotal(){
        NumeroALetras n = new NumeroALetras();
        subtotal=importe;
        this.total=subtotal+impuesto-descuento;
        this.total_letras = n.convertirNumero(this.total);
    }
    
    public String getForma_pago() {
        return forma_pago;
    }

    public String getMetodo_pago() {
        return metodo_pago;
    }

    public String getTipo_comprobante() {
        return tipo_comprobante;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public String getClave() {
        return clave;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getObj_impuestos() {
        return obj_impuestos;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public double getImporte() {
        return importe;
    }

    public double getImpuesto() {
        return impuesto;
    }

    public double getDescuento() {
        return descuento;
    }

    public String getFolioSat() {
        return folioSat;
    }

    public String getNumero_serie_certificado_emisor() {
        return numero_serie_certificado_emisor;
    }

    public String getNumero_serie_certificado_SAT() {
        return numero_serie_certificado_SAT;
    }

    public String getCadena_original_complemento_certificacion_digital_SAT() {
        return cadena_original_complemento_certificacion_digital_SAT;
    }

    public String getSello_digital_CFDI() {
        return sello_digital_CFDI;
    }

    public String getSello_SAT() {
        return sello_SAT;
    }

    //set
    public void setForma_pago(String forma_pago) {
        this.forma_pago = forma_pago;
    }

    public void setMetodo_pago(String metodo_pago) {
        this.metodo_pago = metodo_pago;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setObj_impuestos(String obj_impuestos) {
        this.obj_impuestos = obj_impuestos;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public void setImpuesto(double impuesto) {
        this.impuesto = impuesto;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public void setFolioSat(String folioSat) {
        this.folioSat = folioSat;
    }

    public void setNumero_serie_certificado_emisor(String numero_serie_certificado_emisor) {
        this.numero_serie_certificado_emisor = numero_serie_certificado_emisor;
    }

    public void setNumero_serie_certificado_SAT(String numero_serie_certificado_SAT) {
        this.numero_serie_certificado_SAT = numero_serie_certificado_SAT;
    }

    public void setCadena_original_complemento_certificacion_digital_SAT(String cadena_original_complemento_certificacion_digital_SAT) {
        this.cadena_original_complemento_certificacion_digital_SAT = cadena_original_complemento_certificacion_digital_SAT;
    }

    public void setSello_digital_CFDI(String sello_digital_CFDI) {
        this.sello_digital_CFDI = sello_digital_CFDI;
    }

    public void setSello_SAT(String sello_SAT) {
        this.sello_SAT = sello_SAT;
    }
    
    
}
