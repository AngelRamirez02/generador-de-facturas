/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package facturacion;

import com.itextpdf.text.DocumentException;
import facturacion.GenerarFactura;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JScrollBar;

/**
 *
 * @author ar275
 */
public class ModeloFactura extends JDialog {

    Receptor receptor;
     Alumno alumno ;
    Factura factura;
    private GenerarFactura framePadre; // Referencia al JFrame
    
    public ModeloFactura(GenerarFactura FramePadre, boolean modal,Emisor emisor,Receptor recep, Alumno alum, Factura fact) {
        super(FramePadre, modal);  // Establece la ventana como modal
        this.framePadre=(GenerarFactura) FramePadre;
        // Establece el comportamiento de cierre adecuado
        initComponents();
        //Objetos para rellenar la factura
        alumno = alum;
        receptor = recep;
        factura = fact;
        
        //Objeto para pasar de numeros a letras
        NumeroALetras numLetra = new NumeroALetras();
        
        //Personalizar el tamaño del logo
        Image logo_img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/logo_escuela.png"));
        logo_lb.setIcon(new ImageIcon(logo_img.getScaledInstance(logo_lb.getWidth(), logo_lb.getHeight(), Image.SCALE_SMOOTH)));
        //----------DATOS PARA EL EMSISOR------------------------
        //etiqueta para el rfc del emisor
        regimen_emisor.setText("<html>Régimen fiscal: 612 personas físicas con actividades empresariales y profesionales</html>");
        //-------------------------------------------------------
        
        //-----NUMERO DE FACTURA------
        no_factura.setText(""+factura.getId_factura());

        //--------DATOS PARA EL EMISOR--------------------------
        //Nombre del padre
        nombre_padre.setText(receptor.getNombreCompletoMayus());
        rfc_padre.setText(receptor.getRfc());
        
        domicilio_fiscalPadre.setText(receptor.getDomicilio_fiscal());
        //regimen padre
        regimen_padre.setText("<html>"+receptor.getRegimen()+"</html>");
        //uso cfdi
        uso_CFDI.setText(receptor.getUso_CFDI());
        
        //-----------DATOS PARA LA DESCRIPCION DEL SERVICIO
        //Cantidad
        cantidad.setText(factura.getCantidad());
        //unidad
        unidad.setText(factura.getUnidad());
        //clavae
        clave.setText(factura.getClave());
        //Obj impuestos
        obj_impuestos.setText(factura.getObj_impuestos());
        //Precio
        precio.setText("$"+factura.getPrecio_unitario());
        //Importe
        importe.setText("$"+factura.getImporte());
        //unidad del producto
        unidad.setText("<html>"+factura.getUnidad()+"</html>");
 
        //descricion del producto
        descripcion.setText("<html><center><br>"+factura.getDescripcion()+"</center></htm>");
        
        //Datos del alumno
        curp_alumno.setText(alumno.getCurp());
        nivel_escolar.setText(alumno.getNivel_escolar());
        nombre_alumno.setText(alumno.getNombreCompletoMayus());
        
        //--------DATOS PARA FORMA DE PAGO
        precio_texto.setText("( "+factura.getTotal_letras()+" )");
        System.out.println(factura.getTotal());
        forma_pago.setText(factura.getForma_pago());
        metodo_pago.setText(factura.getMetodo_pago());
        tipo_comprobante.setText(factura.getTipo_comprobante());
        

        //-----Subtotales
        subtotal.setText("$"+factura.getSubtotal());
        descuento.setText("$"+factura.getDescuento());
        total.setText("$"+factura.getTotal());
        
        // Ajustar la velocidad de desplazamiento
        JScrollBar verticalBar = contenedor.getVerticalScrollBar();
        verticalBar.setUnitIncrement(16);
        //aparece en el centro de la pantalla
        this.setLocationRelativeTo(null);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel9 = new javax.swing.JLabel();
        contenedor = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        logo_lb = new javax.swing.JLabel();
        nombre_escuela = new javax.swing.JLabel();
        nombre_escuela1 = new javax.swing.JLabel();
        nombre_emisor = new javax.swing.JLabel();
        rfc_emisor = new javax.swing.JLabel();
        regimen_emisor = new javax.swing.JLabel();
        fechaHoraEmision = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        no_factura = new javax.swing.JLabel();
        titulo_fechaHoraEmision1 = new javax.swing.JTextField();
        fechaHoraEmison = new javax.swing.JTextField();
        titulo_fechaHoraCertificacion = new javax.swing.JTextField();
        cp_Expedicion = new javax.swing.JTextField();
        encabezado_Receptor = new paneles.PanelRound();
        jLabel1 = new javax.swing.JLabel();
        contenedor_receptor = new paneles.PanelRound();
        nombre_padre = new javax.swing.JLabel();
        Tituloregimen_padre = new javax.swing.JLabel();
        domicilio_fiscalPadre = new javax.swing.JLabel();
        rfc_padre = new javax.swing.JLabel();
        titulo_cfdi1 = new javax.swing.JLabel();
        titulo_cp2 = new javax.swing.JLabel();
        regimen_padre = new javax.swing.JLabel();
        titulo_rfc2 = new javax.swing.JLabel();
        uso_CFDI = new javax.swing.JLabel();
        contenedor_datosFiscales = new paneles.PanelRound();
        titulo_certificadoEmisor = new javax.swing.JLabel();
        certificadoSAT = new javax.swing.JLabel();
        titulo_exportación = new javax.swing.JLabel();
        folioSat = new javax.swing.JLabel();
        titulo_certificadoEmisor1 = new javax.swing.JLabel();
        exportacion = new javax.swing.JLabel();
        titulo_FolioSat2 = new javax.swing.JLabel();
        titulo_leyenda = new javax.swing.JLabel();
        certificadoEmisor2 = new javax.swing.JLabel();
        encabezado_datosFiscales1 = new paneles.PanelRound();
        jLabel4 = new javax.swing.JLabel();
        titulo_codigoPostalExped2 = new javax.swing.JTextField();
        EncabezadoProductos = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        contenedorProductos = new javax.swing.JPanel();
        unidad = new javax.swing.JLabel();
        clave = new javax.swing.JLabel();
        descripcion = new javax.swing.JLabel();
        obj_impuestos = new javax.swing.JLabel();
        precio = new javax.swing.JLabel();
        importe = new javax.swing.JLabel();
        curp_alumno = new javax.swing.JLabel();
        cantidad = new javax.swing.JLabel();
        clave_centroTrabajo = new javax.swing.JLabel();
        cantidad4 = new javax.swing.JLabel();
        nivel_escolar = new javax.swing.JLabel();
        cantidad5 = new javax.swing.JLabel();
        nombre_alumno = new javax.swing.JLabel();
        cantidad6 = new javax.swing.JLabel();
        cantidad1 = new javax.swing.JLabel();
        titulo_curp = new javax.swing.JLabel();
        contenedorPagos = new javax.swing.JPanel();
        precio_texto = new javax.swing.JLabel();
        metodo_pago = new javax.swing.JLabel();
        cantidad8 = new javax.swing.JLabel();
        forma_pago = new javax.swing.JLabel();
        tipo_comprobante = new javax.swing.JLabel();
        cantidad10 = new javax.swing.JLabel();
        forma_pago3 = new javax.swing.JLabel();
        cantidad11 = new javax.swing.JLabel();
        moneda = new javax.swing.JLabel();
        cantidad12 = new javax.swing.JLabel();
        cantidad13 = new javax.swing.JLabel();
        cantidad14 = new javax.swing.JLabel();
        contenedorSubTotal = new javax.swing.JPanel();
        subtotal = new javax.swing.JLabel();
        cantidad15 = new javax.swing.JLabel();
        descuento = new javax.swing.JLabel();
        cantidad17 = new javax.swing.JLabel();
        contenedorTotal = new javax.swing.JPanel();
        total = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        encabezadoCadenaSat = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        contenedorCadenaSat = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        btn_sellarFactura = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();

        jLabel9.setFont(new java.awt.Font("Roboto Black", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 0, 0));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Nota: hasta no confirmar la generación de la factura, este documento no tiene ninguna validez");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Instituto Manuel Andres Lopez Obrador - Factura previa");
        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(2147483647, 700));
        setMinimumSize(new java.awt.Dimension(700, 735));
        setPreferredSize(new java.awt.Dimension(1120, 730));
        setResizable(false);

        contenedor.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        contenedor.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(1000, 1900));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(1060, 1500));
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 1900));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logo_lb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo_escuela.png"))); // NOI18N
        logo_lb.setText("jLabel2");
        logo_lb.setMaximumSize(new java.awt.Dimension(400, 400));
        logo_lb.setMinimumSize(new java.awt.Dimension(400, 400));
        logo_lb.setPreferredSize(new java.awt.Dimension(400, 600));
        jPanel1.add(logo_lb, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, 130, 120));

        nombre_escuela.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        nombre_escuela.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nombre_escuela.setText("López Obrador");
        jPanel1.add(nombre_escuela, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 210, 130, -1));

        nombre_escuela1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        nombre_escuela1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nombre_escuela1.setText("Andrés Manuel");
        jPanel1.add(nombre_escuela1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, 130, -1));

        nombre_emisor.setFont(new java.awt.Font("Roboto Black", 0, 18)); // NOI18N
        nombre_emisor.setText("Carolina Astudillo Hernández");
        jPanel1.add(nombre_emisor, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 60, 240, 30));

        rfc_emisor.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        rfc_emisor.setText("RFC: AUHC670504FFF");
        jPanel1.add(rfc_emisor, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 90, 180, 30));

        regimen_emisor.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        regimen_emisor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        regimen_emisor.setText("Regimen emisor");
        jPanel1.add(regimen_emisor, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 120, 420, 40));

        fechaHoraEmision.setEditable(false);
        fechaHoraEmision.setBackground(new java.awt.Color(255, 255, 255));
        fechaHoraEmision.setFont(new java.awt.Font("Roboto", 2, 14)); // NOI18N
        fechaHoraEmision.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        fechaHoraEmision.setBorder(null);
        fechaHoraEmision.setFocusable(false);
        jPanel1.add(fechaHoraEmision, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 100, 260, 30));

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(198, 54, 55));
        jLabel2.setText("Factura:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 50, 70, 20));

        no_factura.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        no_factura.setForeground(new java.awt.Color(198, 54, 55));
        no_factura.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        no_factura.setText("jLabel3");
        jPanel1.add(no_factura, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 50, 190, 20));

        titulo_fechaHoraEmision1.setEditable(false);
        titulo_fechaHoraEmision1.setBackground(new java.awt.Color(198, 54, 55));
        titulo_fechaHoraEmision1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        titulo_fechaHoraEmision1.setForeground(new java.awt.Color(255, 255, 255));
        titulo_fechaHoraEmision1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        titulo_fechaHoraEmision1.setText("  Fecha y hora de emisión");
        titulo_fechaHoraEmision1.setBorder(null);
        titulo_fechaHoraEmision1.setFocusable(false);
        jPanel1.add(titulo_fechaHoraEmision1, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 70, 260, 30));

        fechaHoraEmison.setEditable(false);
        fechaHoraEmison.setBackground(new java.awt.Color(255, 255, 255));
        fechaHoraEmison.setFont(new java.awt.Font("Roboto", 2, 14)); // NOI18N
        fechaHoraEmison.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        fechaHoraEmison.setBorder(null);
        fechaHoraEmison.setFocusable(false);
        jPanel1.add(fechaHoraEmison, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 160, 260, 30));

        titulo_fechaHoraCertificacion.setEditable(false);
        titulo_fechaHoraCertificacion.setBackground(new java.awt.Color(198, 54, 55));
        titulo_fechaHoraCertificacion.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        titulo_fechaHoraCertificacion.setForeground(new java.awt.Color(255, 255, 255));
        titulo_fechaHoraCertificacion.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        titulo_fechaHoraCertificacion.setText("  Fecha y hora de certificación");
        titulo_fechaHoraCertificacion.setBorder(null);
        titulo_fechaHoraCertificacion.setFocusable(false);
        jPanel1.add(titulo_fechaHoraCertificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 130, 260, 30));

        cp_Expedicion.setEditable(false);
        cp_Expedicion.setBackground(new java.awt.Color(255, 255, 255));
        cp_Expedicion.setFont(new java.awt.Font("Roboto", 2, 14)); // NOI18N
        cp_Expedicion.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        cp_Expedicion.setText("39890");
        cp_Expedicion.setBorder(null);
        cp_Expedicion.setFocusable(false);
        jPanel1.add(cp_Expedicion, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 220, 260, 30));

        encabezado_Receptor.setBackground(new java.awt.Color(198, 54, 55));
        encabezado_Receptor.setRoundTopLeft(20);
        encabezado_Receptor.setRoundTopRight(20);
        encabezado_Receptor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Receptor");
        encabezado_Receptor.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 0, 440, 40));

        jPanel1.add(encabezado_Receptor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 490, 40));

        contenedor_receptor.setBackground(new java.awt.Color(255, 255, 255));
        contenedor_receptor.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(198, 54, 55), 2, true));
        contenedor_receptor.setRoundBottomLeft(20);
        contenedor_receptor.setRoundBottomRight(20);
        contenedor_receptor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        nombre_padre.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        nombre_padre.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        nombre_padre.setText("Angel Ramirez Castro");
        contenedor_receptor.add(nombre_padre, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 2, 460, 50));

        Tituloregimen_padre.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Tituloregimen_padre.setText("Regimen Fiscal:");
        contenedor_receptor.add(Tituloregimen_padre, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 110, 30));

        domicilio_fiscalPadre.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        domicilio_fiscalPadre.setText("39890");
        contenedor_receptor.add(domicilio_fiscalPadre, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 330, 30));

        rfc_padre.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        rfc_padre.setText("RACA031202FDD");
        contenedor_receptor.add(rfc_padre, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 330, 30));

        titulo_cfdi1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        titulo_cfdi1.setText("Uso CDFI:");
        contenedor_receptor.add(titulo_cfdi1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 80, 30));

        titulo_cp2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        titulo_cp2.setText("Domicilio:");
        contenedor_receptor.add(titulo_cp2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 110, 30));

        regimen_padre.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        regimen_padre.setText("jLabel4");
        contenedor_receptor.add(regimen_padre, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 180, 330, 50));

        titulo_rfc2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        titulo_rfc2.setText("RFC:");
        contenedor_receptor.add(titulo_rfc2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 50, 30));

        uso_CFDI.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        uso_CFDI.setText("D10 Pagos por servicios educativos (Colegiatura)");
        contenedor_receptor.add(uso_CFDI, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 330, 30));

        jPanel1.add(contenedor_receptor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 490, 250));

        contenedor_datosFiscales.setBackground(new java.awt.Color(255, 255, 255));
        contenedor_datosFiscales.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(198, 54, 55), 2, true));
        contenedor_datosFiscales.setRoundBottomLeft(20);
        contenedor_datosFiscales.setRoundBottomRight(20);
        contenedor_datosFiscales.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        titulo_certificadoEmisor.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        titulo_certificadoEmisor.setText("Numero serie del certificado SAT:");
        contenedor_datosFiscales.add(titulo_certificadoEmisor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 240, 30));

        certificadoSAT.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        contenedor_datosFiscales.add(certificadoSAT, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 470, 30));

        titulo_exportación.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        titulo_exportación.setText("Exportación:");
        contenedor_datosFiscales.add(titulo_exportación, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 80, 30));

        folioSat.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        contenedor_datosFiscales.add(folioSat, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 470, 30));

        titulo_certificadoEmisor1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        titulo_certificadoEmisor1.setText("Numero de serie certificado emisor:");
        contenedor_datosFiscales.add(titulo_certificadoEmisor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 240, 30));

        exportacion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        exportacion.setText("No aplica");
        contenedor_datosFiscales.add(exportacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 210, 90, 30));

        titulo_FolioSat2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        titulo_FolioSat2.setText("Folio SAT:");
        contenedor_datosFiscales.add(titulo_FolioSat2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 80, 30));

        titulo_leyenda.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        titulo_leyenda.setText("Leyenda:");
        contenedor_datosFiscales.add(titulo_leyenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 80, 30));

        certificadoEmisor2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        contenedor_datosFiscales.add(certificadoEmisor2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 470, 30));

        jPanel1.add(contenedor_datosFiscales, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 330, 490, 250));

        encabezado_datosFiscales1.setBackground(new java.awt.Color(198, 54, 55));
        encabezado_datosFiscales1.setRoundTopLeft(20);
        encabezado_datosFiscales1.setRoundTopRight(20);
        encabezado_datosFiscales1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Datos Fiscales");
        encabezado_datosFiscales1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 0, 370, 40));

        jPanel1.add(encabezado_datosFiscales1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 290, 490, 40));

        titulo_codigoPostalExped2.setEditable(false);
        titulo_codigoPostalExped2.setBackground(new java.awt.Color(198, 54, 55));
        titulo_codigoPostalExped2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        titulo_codigoPostalExped2.setForeground(new java.awt.Color(255, 255, 255));
        titulo_codigoPostalExped2.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        titulo_codigoPostalExped2.setText("  Codigo postal de expedición");
        titulo_codigoPostalExped2.setBorder(null);
        titulo_codigoPostalExped2.setFocusable(false);
        jPanel1.add(titulo_codigoPostalExped2, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 190, 260, 30));

        EncabezadoProductos.setBackground(new java.awt.Color(198, 54, 55));
        EncabezadoProductos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Cant");
        EncabezadoProductos.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 30));

        jLabel14.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Unidad");
        EncabezadoProductos.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 0, 110, 30));

        jLabel15.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Clave");
        EncabezadoProductos.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 0, 120, 30));

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Descripción");
        EncabezadoProductos.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 0, 350, 30));

        jLabel16.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Obj impuestos");
        EncabezadoProductos.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 0, 160, 30));

        jLabel17.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Precio");
        EncabezadoProductos.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 0, 120, 30));

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Importe");
        EncabezadoProductos.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 0, 120, 30));

        jPanel1.add(EncabezadoProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 650, 1040, 30));

        contenedorProductos.setBackground(new java.awt.Color(255, 255, 255));
        contenedorProductos.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(198, 54, 55), 1, true));
        contenedorProductos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        unidad.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        unidad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        unidad.setText("Unidad");
        contenedorProductos.add(unidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 0, 110, 120));

        clave.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        clave.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        clave.setText("Clave");
        contenedorProductos.add(clave, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 0, 120, 120));

        descripcion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        descripcion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        descripcion.setText("Descripción");
        contenedorProductos.add(descripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 0, 350, 140));

        obj_impuestos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        obj_impuestos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        obj_impuestos.setText("No objeto de impuesto");
        contenedorProductos.add(obj_impuestos, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 0, 160, 120));

        precio.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        precio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        precio.setText("$1,710.00");
        contenedorProductos.add(precio, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 0, 120, 120));

        importe.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        importe.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        importe.setText("$1,710.00");
        contenedorProductos.add(importe, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 0, 120, 120));

        curp_alumno.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        curp_alumno.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        curp_alumno.setText("RACA031202HGRMSNA2");
        contenedorProductos.add(curp_alumno, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 190, 40));

        cantidad.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cantidad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cantidad.setText("1.00");
        contenedorProductos.add(cantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 120));

        clave_centroTrabajo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        clave_centroTrabajo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        clave_centroTrabajo.setText("12PPR0395H");
        contenedorProductos.add(clave_centroTrabajo, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 200, 200, 40));

        cantidad4.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cantidad4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cantidad4.setText("Clave del centro de trabajo");
        contenedorProductos.add(cantidad4, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 160, 200, 40));

        nivel_escolar.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        nivel_escolar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        nivel_escolar.setText("Primaria");
        contenedorProductos.add(nivel_escolar, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 200, 200, 40));

        cantidad5.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cantidad5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cantidad5.setText("Nivel educativo");
        contenedorProductos.add(cantidad5, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 160, 200, 40));

        nombre_alumno.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        nombre_alumno.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        nombre_alumno.setText("Angel Ramirez Castro");
        contenedorProductos.add(nombre_alumno, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 200, 320, 40));

        cantidad6.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cantidad6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cantidad6.setText("Nombre");
        contenedorProductos.add(cantidad6, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 160, 320, 40));

        cantidad1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        cantidad1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cantidad1.setText("Alumno");
        contenedorProductos.add(cantidad1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 100, 40));

        titulo_curp.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        titulo_curp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo_curp.setText("CURP");
        contenedorProductos.add(titulo_curp, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 60, 40));

        jPanel1.add(contenedorProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 680, 1040, 240));

        contenedorPagos.setBackground(new java.awt.Color(255, 255, 255));
        contenedorPagos.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(198, 54, 55), 1, true));
        contenedorPagos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        precio_texto.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        precio_texto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        precio_texto.setText("(MIL SETECIENTOS DIEZ MXN 00/100)");
        contenedorPagos.add(precio_texto, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 470, 40));

        metodo_pago.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        metodo_pago.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        metodo_pago.setText("PUE Pago en una sola exhibicion");
        contenedorPagos.add(metodo_pago, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, 400, 40));

        cantidad8.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cantidad8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cantidad8.setText("Método de pago:");
        contenedorPagos.add(cantidad8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 160, 40));

        forma_pago.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        forma_pago.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        forma_pago.setText("03 Transferencia electrónica de fondos");
        contenedorPagos.add(forma_pago, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 400, 40));

        tipo_comprobante.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        tipo_comprobante.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        tipo_comprobante.setText("I ingreso");
        contenedorPagos.add(tipo_comprobante, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 130, 400, 40));

        cantidad10.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cantidad10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cantidad10.setText("Tipo de comprobante:");
        contenedorPagos.add(cantidad10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 160, 40));

        forma_pago3.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        forma_pago3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        contenedorPagos.add(forma_pago3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 170, 400, 40));

        cantidad11.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cantidad11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cantidad11.setText("Condiciones de pago:");
        contenedorPagos.add(cantidad11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 160, 40));

        moneda.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        moneda.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        moneda.setText("MXN Peso Mexicano");
        contenedorPagos.add(moneda, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 210, 160, 40));

        cantidad12.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cantidad12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cantidad12.setText("Tipo de camio:");
        contenedorPagos.add(cantidad12, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 210, 110, 40));

        cantidad13.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cantidad13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cantidad13.setText("Moneda:");
        contenedorPagos.add(cantidad13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 160, 40));

        cantidad14.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cantidad14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cantidad14.setText("Forma de pago:");
        contenedorPagos.add(cantidad14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 160, 40));

        jPanel1.add(contenedorPagos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 920, 690, 260));

        contenedorSubTotal.setBackground(new java.awt.Color(255, 255, 255));
        contenedorSubTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(198, 54, 55)));
        contenedorSubTotal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        subtotal.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        subtotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        subtotal.setText("$1,710.00");
        contenedorSubTotal.add(subtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 50, 130, 40));

        cantidad15.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cantidad15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        cantidad15.setText("Subtotal:");
        contenedorSubTotal.add(cantidad15, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 90, 40));

        descuento.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        descuento.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        descuento.setText("$0.00");
        contenedorSubTotal.add(descuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 120, 130, 40));

        cantidad17.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cantidad17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        cantidad17.setText("Descuento:");
        contenedorSubTotal.add(cantidad17, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120, 90, 40));

        jPanel1.add(contenedorSubTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 920, 350, 210));

        contenedorTotal.setBackground(new java.awt.Color(198, 54, 55));
        contenedorTotal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        total.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        total.setForeground(new java.awt.Color(255, 255, 255));
        total.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        total.setText("$1,710.00");
        contenedorTotal.add(total, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 230, 50));

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("TOTAL:");
        contenedorTotal.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 70, 40));

        jPanel1.add(contenedorTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 1130, 350, 50));

        encabezadoCadenaSat.setBackground(new java.awt.Color(198, 54, 55));
        encabezadoCadenaSat.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Cadena original del complemento de certificacion digital del SAT:");
        encabezadoCadenaSat.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 40));

        jPanel1.add(encabezadoCadenaSat, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 1240, 1040, 40));

        contenedorCadenaSat.setBackground(new java.awt.Color(255, 255, 255));
        contenedorCadenaSat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(198, 54, 55)));
        contenedorCadenaSat.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(contenedorCadenaSat, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 1280, 1040, 130));

        jPanel3.setBackground(new java.awt.Color(198, 54, 55));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel21.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Sello original del CFDI:");
        jPanel3.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 710, 40));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 1420, 710, 40));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(198, 55, 54)));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 1460, 710, 110));

        jPanel5.setBackground(new java.awt.Color(198, 54, 55));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Sello del SAT:");
        jPanel5.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 710, 40));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 1580, 710, 40));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(198, 55, 54)));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 1620, -1, -1));

        btn_sellarFactura.setBackground(new java.awt.Color(198, 54, 55));
        btn_sellarFactura.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btn_sellarFactura.setForeground(new java.awt.Color(255, 255, 255));
        btn_sellarFactura.setText("Sellar y enviar por correo");
        btn_sellarFactura.setBorder(null);
        btn_sellarFactura.setBorderPainted(false);
        btn_sellarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sellarFacturaActionPerformed(evt);
            }
        });
        jPanel1.add(btn_sellarFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 1810, 250, 50));

        jButton2.setBackground(new java.awt.Color(102, 102, 102));
        jButton2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Cancelar");
        jButton2.setBorder(null);
        jButton2.setBorderPainted(false);
        jButton2.setFocusable(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 1810, 160, 50));

        jLabel3.setFont(new java.awt.Font("Roboto Black", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Nota: hasta no confirmar la generación de la factura, este documento no tiene ninguna validez");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 1110, 40));

        jLabel5.setFont(new java.awt.Font("Roboto Black", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 0, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Nota: hasta no confirmar la generación de la factura, este documento no tiene ninguna validez");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 1720, 1110, 70));

        jLabel20.setFont(new java.awt.Font("Roboto Black", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 0, 0));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Nota: hasta no confirmar la generación de la factura, este documento no tiene ninguna validez");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 1180, 1110, 60));

        jLabel22.setFont(new java.awt.Font("Roboto Black", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 0, 0));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Nota: hasta no confirmar la generación de la factura, este documento no tiene ninguna validez");
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 580, 1110, 70));

        jLabel23.setFont(new java.awt.Font("Roboto Black", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 0, 0));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Nota: hasta no confirmar la generación de la factura, este documento no tiene ninguna validez");
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 1110, 40));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(198, 55, 54)));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 708, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 1620, -1, 100));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1894, Short.MAX_VALUE)
                .addContainerGap())
        );

        contenedor.setViewportView(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contenedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1120, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contenedor, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btn_sellarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sellarFacturaActionPerformed
        try {
            framePadre.sellarFactura();
            this.dispose();
        } catch (DocumentException ex) {
            Logger.getLogger(ModeloFactura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ModeloFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_sellarFacturaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ModeloFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ModeloFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ModeloFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ModeloFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ModeloFactura(null,true,null,null,null,null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel EncabezadoProductos;
    private javax.swing.JLabel Tituloregimen_padre;
    private javax.swing.JButton btn_sellarFactura;
    private javax.swing.JLabel cantidad;
    private javax.swing.JLabel cantidad1;
    private javax.swing.JLabel cantidad10;
    private javax.swing.JLabel cantidad11;
    private javax.swing.JLabel cantidad12;
    private javax.swing.JLabel cantidad13;
    private javax.swing.JLabel cantidad14;
    private javax.swing.JLabel cantidad15;
    private javax.swing.JLabel cantidad17;
    private javax.swing.JLabel cantidad4;
    private javax.swing.JLabel cantidad5;
    private javax.swing.JLabel cantidad6;
    private javax.swing.JLabel cantidad8;
    private javax.swing.JLabel certificadoEmisor2;
    private javax.swing.JLabel certificadoSAT;
    private javax.swing.JLabel clave;
    private javax.swing.JLabel clave_centroTrabajo;
    private javax.swing.JScrollPane contenedor;
    private javax.swing.JPanel contenedorCadenaSat;
    private javax.swing.JPanel contenedorPagos;
    private javax.swing.JPanel contenedorProductos;
    private javax.swing.JPanel contenedorSubTotal;
    private javax.swing.JPanel contenedorTotal;
    private paneles.PanelRound contenedor_datosFiscales;
    private paneles.PanelRound contenedor_receptor;
    private javax.swing.JTextField cp_Expedicion;
    private javax.swing.JLabel curp_alumno;
    private javax.swing.JLabel descripcion;
    private javax.swing.JLabel descuento;
    private javax.swing.JLabel domicilio_fiscalPadre;
    private javax.swing.JPanel encabezadoCadenaSat;
    private paneles.PanelRound encabezado_Receptor;
    private paneles.PanelRound encabezado_datosFiscales1;
    private javax.swing.JLabel exportacion;
    private javax.swing.JTextField fechaHoraEmision;
    private javax.swing.JTextField fechaHoraEmison;
    private javax.swing.JLabel folioSat;
    private javax.swing.JLabel forma_pago;
    private javax.swing.JLabel forma_pago3;
    private javax.swing.JLabel importe;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JLabel logo_lb;
    private javax.swing.JLabel metodo_pago;
    private javax.swing.JLabel moneda;
    private javax.swing.JLabel nivel_escolar;
    private javax.swing.JLabel no_factura;
    private javax.swing.JLabel nombre_alumno;
    private javax.swing.JLabel nombre_emisor;
    private javax.swing.JLabel nombre_escuela;
    private javax.swing.JLabel nombre_escuela1;
    private javax.swing.JLabel nombre_padre;
    private javax.swing.JLabel obj_impuestos;
    private javax.swing.JLabel precio;
    private javax.swing.JLabel precio_texto;
    private javax.swing.JLabel regimen_emisor;
    private javax.swing.JLabel regimen_padre;
    private javax.swing.JLabel rfc_emisor;
    private javax.swing.JLabel rfc_padre;
    private javax.swing.JLabel subtotal;
    private javax.swing.JLabel tipo_comprobante;
    private javax.swing.JLabel titulo_FolioSat2;
    private javax.swing.JLabel titulo_certificadoEmisor;
    private javax.swing.JLabel titulo_certificadoEmisor1;
    private javax.swing.JLabel titulo_cfdi1;
    private javax.swing.JTextField titulo_codigoPostalExped2;
    private javax.swing.JLabel titulo_cp2;
    private javax.swing.JLabel titulo_curp;
    private javax.swing.JLabel titulo_exportación;
    private javax.swing.JTextField titulo_fechaHoraCertificacion;
    private javax.swing.JTextField titulo_fechaHoraEmision1;
    private javax.swing.JLabel titulo_leyenda;
    private javax.swing.JLabel titulo_rfc2;
    private javax.swing.JLabel total;
    private javax.swing.JLabel unidad;
    private javax.swing.JLabel uso_CFDI;
    // End of variables declaration//GEN-END:variables
}
