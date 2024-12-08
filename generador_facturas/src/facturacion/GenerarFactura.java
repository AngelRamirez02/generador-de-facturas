/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package facturacion;

import padres.*;
import alumnos.AltaAlumnos;
import alumnos.ConsultarAlumnos;
import alumnos.ModificarAlumno;
import alumnos.EliminarAlumno;
import com.itextpdf.text.DocumentException;
import emisor.*;
import conexion.conexion;
import direccion.ObtenerDireccion;
import menu.*;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.Border;
import login.login_window;
import sesiones.HistorialSesiones;
import validacion.Validacion;

/**
 *
 * @author ar275
 */
public class GenerarFactura extends javax.swing.JFrame {

    //Objeto para la conexion
    conexion cx = new conexion();
    
    //Objetos para recpetor y alumno
    Emisor emisor;
    Receptor receptor;
    Alumno alumno;
    //Objeto para la factura
    Factura factura;
    
    //Deescripcion
    String descripcion="";
    
    //-------VARIABLES PARA LOS PRECIOS
    double precio=0;
    double importe=0;
    double impuestos = 0;
    double descuento=0;
    double subtotal=0;
    
    //Datos para el inicio de sesion
    private String usuario;
    LocalDate fechaInicioSesion;
    LocalTime horaInicioSesion;    
    Validacion valida = new Validacion();//objeto para valdicar los datos
    
    String mesEnLetras;
    int anioActual;
    
    //Colores para los botones seleccionados y no
    Color colorbtnSeleccionado = Color.decode("#A91E1F");
    Color colorbtnNoSeleccionado = Color.decode("#C94545");
    //Iconos de item para menu no selccionado
    Image icon_img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/icon_itemMenu.png"));
     //Imagen para menu selccionado
    Image icon_seleccionado = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/icon_itemSeleccionado.png"));
    Image img_regresar = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/icon_regresar.png"));
    
     public GenerarFactura() {
        initComponents();
        
        icon_regresarlb.setIcon(new ImageIcon(img_regresar.getScaledInstance(icon_regresarlb.getWidth(), icon_regresarlb.getHeight(), Image.SCALE_SMOOTH)));
        
        //Menus ocultos por defecto
        menu_padres.setVisible(false);
        menu_alumnos.setVisible(false);
        menu_factura.setVisible(false);
        menu_estadisticas.setVisible(false);
        menu_emisor.setVisible(false);
        //Imagen del logo de la escuela
        Image logo_img= Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/logo_escuela.png"));
       
        //Iconos para botones de menu
        icon_item.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
        icon_item2.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
        icon_item3.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
        icon_item4.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
        icon_item5.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
        contenedor_menu.setLocation(user_menuIcon.getLocation().x-650, contenedor_menu.getLocation().y);//centrar el contenedor   
        
        //Imaganes para el menu del usuario
        Image icon_historial = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/icon_historial.png"));
        historial_lb.setIcon(new ImageIcon(icon_historial.getScaledInstance(historial_lb.getWidth(), historial_lb.getHeight(), Image.SCALE_SMOOTH)));
        Image icon_salirImg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/icon_salir.png"));
        icon_salir.setIcon(new ImageIcon(icon_salirImg.getScaledInstance(icon_salir.getWidth(), icon_salir.getHeight(), Image.SCALE_SMOOTH)));

        //iconos de buscar
        Image img_buscar = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/btn_buscar3.png"));
        icon_buscarPadre.setIcon(new ImageIcon(img_buscar.getScaledInstance(icon_buscarPadre.getWidth(), icon_buscarPadre.getHeight(), Image.SCALE_SMOOTH)));
        
        //Iconos para opciones de factura
        Image icon_opFactura = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/icon_factura.png"));
        iconFac_lb.setIcon(new ImageIcon(icon_opFactura.getScaledInstance(iconFac_lb.getWidth(), iconFac_lb.getHeight(), Image.SCALE_SMOOTH)));
        iconFac_lb1.setIcon(new ImageIcon(icon_opFactura.getScaledInstance(iconFac_lb.getWidth(), iconFac_lb.getHeight(), Image.SCALE_SMOOTH)));
        iconFac_lb2.setIcon(new ImageIcon(icon_opFactura.getScaledInstance(iconFac_lb.getWidth(), iconFac_lb.getHeight(), Image.SCALE_SMOOTH)));
        iconFac_lb3.setIcon(new ImageIcon(icon_opFactura.getScaledInstance(iconFac_lb.getWidth(), iconFac_lb.getHeight(), Image.SCALE_SMOOTH)));
        iconFac_lb4.setIcon(new ImageIcon(icon_opFactura.getScaledInstance(iconFac_lb.getWidth(), iconFac_lb.getHeight(), Image.SCALE_SMOOTH)));
        iconFac_lb5.setIcon(new ImageIcon(icon_opFactura.getScaledInstance(iconFac_lb.getWidth(), iconFac_lb.getHeight(), Image.SCALE_SMOOTH)));
        iconFac_lb6.setIcon(new ImageIcon(icon_opFactura.getScaledInstance(iconFac_lb.getWidth(), iconFac_lb.getHeight(), Image.SCALE_SMOOTH)));        
        
        // Formatear la fecha en el formato "dd/MM/yyyy"
        LocalDate fechaActual = LocalDate.now();
        // Crear un formato con localización en español
        DateTimeFormatter formatoEspanol = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        // Formatear la fecha en español
        String fechaFormateada = fechaActual.format(formatoEspanol);
        Fecha.setText(fechaFormateada);//Mostar hora
         // Formato para mostrar el mes en letras
        DateTimeFormatter formatoMes = DateTimeFormatter.ofPattern("MMMM");
        mesEnLetras = fechaActual.format(formatoMes);       
        // Obtiene el año actual
        anioActual = fechaActual.getYear();
        
        //Cuando el usuario arrasta para agrandar la ventana
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int panelWidth = contenedor.getWidth();
                int panelHeight = contenedor.getHeight() - barra_nav.getHeight();
                int newX = (fondo.getWidth() - panelWidth) / 2; // Calcular la nueva posición en X
                int newY = (fondo.getHeight() - panelHeight) / 2;
                barra_nav.setSize(fondo.getWidth(),barra_nav.getHeight());//Barra responsiva
                contenedor.setLocation(newX, newY);//contenedor en el centro
                menu_user.setLocation(barra_nav.getWidth()-75, menu_user.getLocation().y);//menu user pegado al final de la barra
                contenedor_menu.setLocation(menu_user.getLocation().x-650, contenedor_menu.getLocation().y); //contenedor centrado
                menu_salir.setLocation(barra_nav.getWidth() - menu_salir.getWidth(), menu_salir.getLocation().y);//menu salir responsive
                //alinear submenus 
                menu_padres.setLocation(contenedor_menu.getLocation().x, menu_padres.getLocation().y);
                menu_alumnos.setLocation(menu_padres.getLocation().x+120, menu_alumnos.getLocation().y);
                menu_factura.setLocation(menu_alumnos.getLocation().x+120, menu_factura.getLocation().y);
                menu_estadisticas.setLocation(menu_factura.getLocation().x+120, menu_estadisticas.getLocation().y);
                menu_emisor.setLocation(menu_estadisticas.getLocation().x+120, menu_emisor.getLocation().y);
                icon_regresarlb.setLocation(30, icon_regresarlb.getLocation().y);
            }
        });
        //Cuando el usuario extiende por completo la pantalla
        this.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                int panelWidth = contenedor.getWidth();
                int panelHeight = contenedor.getHeight() - barra_nav.getHeight();
                int newX = (fondo.getWidth() - panelWidth) / 2; // Calcular la nueva posición en X
                int newY = (fondo.getHeight() - panelHeight) / 2;
                barra_nav.setSize(fondo.getWidth(),barra_nav.getHeight());//Barra responsiva
                contenedor.setLocation(newX, newY);//contenedor en el centro
                menu_user.setLocation(barra_nav.getWidth()-75, menu_user.getLocation().y);//menu user pegado al final de la barra
                contenedor_menu.setLocation(menu_user.getLocation().x-650, contenedor_menu.getLocation().y); //contenedor centrado
                menu_salir.setLocation(barra_nav.getWidth()-menu_salir.getWidth(), menu_salir.getLocation().y);//menu salir responsive
                //alinear submenus 
                menu_padres.setLocation(contenedor_menu.getLocation().x, menu_alumnos.getLocation().y);
                menu_alumnos.setLocation(menu_padres.getLocation().x+120, menu_alumnos.getLocation().y);
                menu_factura.setLocation(menu_alumnos.getLocation().x+120, menu_factura.getLocation().y);
                menu_emisor.setLocation(menu_estadisticas.getLocation().x+120, menu_emisor.getLocation().y);
            }
        });
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener la hora actual
                LocalTime horaActual = LocalTime.now();
                // Formatear la hora en el formato "HH:mm:ss"
                DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("hh:mm:ss a");
                String horaFormateada = horaActual.format(formatoHora);
                // Actualizar el JLabel con la hora actual
                hora_lb.setText(horaFormateada);
            }
        });
        timer.start();
        txt_nombreUser.setText(usuario);
        menu_salir.setVisible(false);//por defecto el menu de salir no es visible

         //ocultar rutas por defecto
         titulo_rutas.setVisible(false);
         rutas.setVisible(false);

        // Ajustar la velocidad de desplazamiento
        JScrollBar verticalBar = contenedor.getVerticalScrollBar();
        verticalBar.setUnitIncrement(16);
        
        //Cargar los datos del emisor
        cargarEmisor();
        
        this.setIconImage(logo_img);//Agregar logo a ventana;
        this.setLocationRelativeTo(null);//La ventana aparece en el centro
        this.setExtendedState(this.MAXIMIZED_BOTH);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fondo = new javax.swing.JPanel();
        barra_nav = new javax.swing.JPanel();
        Fecha = new javax.swing.JLabel();
        hora_lb = new javax.swing.JLabel();
        contenedor_menu = new javax.swing.JPanel();
        btn_alumnos = new javax.swing.JPanel();
        txt_alumnos = new javax.swing.JLabel();
        icon_item = new javax.swing.JLabel();
        btn_padres = new javax.swing.JPanel();
        txt_padres = new javax.swing.JLabel();
        icon_item2 = new javax.swing.JLabel();
        btn_facturas = new javax.swing.JPanel();
        txt_factura = new javax.swing.JLabel();
        icon_item3 = new javax.swing.JLabel();
        btn_estadisticas = new javax.swing.JPanel();
        txt_estadisticas = new javax.swing.JLabel();
        icon_item4 = new javax.swing.JLabel();
        btn_emisor = new javax.swing.JPanel();
        txt_emisor = new javax.swing.JLabel();
        icon_item5 = new javax.swing.JLabel();
        menu_user = new javax.swing.JPanel();
        user_menuIcon = new javax.swing.JLabel();
        menu_salir = new javax.swing.JPanel();
        nombre_user = new javax.swing.JPanel();
        user_menuIcon1 = new javax.swing.JLabel();
        txt_nombreUser = new javax.swing.JLabel();
        btn_historialSesiones = new javax.swing.JPanel();
        historial_lb = new javax.swing.JLabel();
        txt_cerrarSesion = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        btn_salir = new javax.swing.JPanel();
        icon_salir = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        text_salir = new javax.swing.JLabel();
        btn_cerrarSesion = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txt_cerrarSesion1 = new javax.swing.JLabel();
        jSeparator17 = new javax.swing.JSeparator();
        cerrar_icon = new javax.swing.JLabel();
        menu_estadisticas = new javax.swing.JPanel();
        txt_facturasGeneradas = new javax.swing.JLabel();
        txt_ingresos = new javax.swing.JLabel();
        jSeparator15 = new javax.swing.JSeparator();
        menu_emisor = new javax.swing.JPanel();
        txt_editarEmisor = new javax.swing.JLabel();
        jSeparator14 = new javax.swing.JSeparator();
        txt_altaEmisor = new javax.swing.JLabel();
        jSeparator16 = new javax.swing.JSeparator();
        txt_eliminarEmisor = new javax.swing.JLabel();
        jSeparator18 = new javax.swing.JSeparator();
        txt_ConsultarEmisor = new javax.swing.JLabel();
        menu_padres = new javax.swing.JPanel();
        txt_altaPadres = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        txt_consultarPadres = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        txt_modificarPadres = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        txt_eliminarPadres = new javax.swing.JLabel();
        menu_alumnos = new javax.swing.JPanel();
        txt_altaAlumnos = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        txt_consultarAlmnos = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        txt_modificarAlumnos = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        txt_eliminarAlumno = new javax.swing.JLabel();
        menu_factura = new javax.swing.JPanel();
        txt_generarFcatura = new javax.swing.JLabel();
        txt_consultarAlmnos1 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        contenedor = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        icon_regresarlb = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btn_enviarCorreo = new javax.swing.JButton();
        panelRound1 = new paneles.PanelRound();
        lb_comprobante = new javax.swing.JLabel();
        panelRound2 = new paneles.PanelRound();
        txt_nombrePadre = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        apellidoPaterno_padre = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        regimen_padre = new javax.swing.JTextField();
        icon_buscarPadre = new javax.swing.JLabel();
        rfc_padre = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        nombre_padre = new javax.swing.JTextField();
        txt_nombrePadre2 = new javax.swing.JLabel();
        apellidoMaterno_padre = new javax.swing.JTextField();
        domicilio_padre = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        titulo_correoPadre = new javax.swing.JLabel();
        correo_padre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        uso_cfdi = new javax.swing.JComboBox<>();
        panelRound3 = new paneles.PanelRound();
        lb_comprobante1 = new javax.swing.JLabel();
        panelRound4 = new paneles.PanelRound();
        titulo_tipoFactura1 = new javax.swing.JLabel();
        metodoPago = new javax.swing.JLabel();
        titulo_regimenEmisor1 = new javax.swing.JLabel();
        moneda = new javax.swing.JLabel();
        titulo_cpEmision1 = new javax.swing.JLabel();
        rfc_emisor = new javax.swing.JLabel();
        titulo_formaPago = new javax.swing.JLabel();
        formaPago = new javax.swing.JComboBox<>();
        titulo_metodoPago = new javax.swing.JLabel();
        tituloMoneda1 = new javax.swing.JLabel();
        tipo_factura = new javax.swing.JLabel();
        iconFac_lb = new javax.swing.JLabel();
        iconFac_lb1 = new javax.swing.JLabel();
        iconFac_lb2 = new javax.swing.JLabel();
        iconFac_lb3 = new javax.swing.JLabel();
        iconFac_lb4 = new javax.swing.JLabel();
        iconFac_lb5 = new javax.swing.JLabel();
        titulo_regimenEmisor2 = new javax.swing.JLabel();
        iconFac_lb6 = new javax.swing.JLabel();
        regimen_emisor = new javax.swing.JLabel();
        cp_expedicion1 = new javax.swing.JLabel();
        panelRound5 = new paneles.PanelRound();
        lb_comprobante2 = new javax.swing.JLabel();
        panelRound6 = new paneles.PanelRound();
        jLabel8 = new javax.swing.JLabel();
        nombres_alumno = new javax.swing.JComboBox<>();
        txt_nombrePadre1 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        apellidoPaterno_alumno = new javax.swing.JTextField();
        apellidoMaterno_alumno = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        nivel_escolar = new javax.swing.JTextField();
        curp_alumno = new javax.swing.JTextField();
        txt_nombrePadre3 = new javax.swing.JLabel();
        txt_nombrePadre4 = new javax.swing.JLabel();
        grado_escolar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        servicio = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cantidad = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        unidad = new javax.swing.JTextField();
        obj_impuestos = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        campo_precioUnitario = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        clave = new javax.swing.JTextField();
        titulo_rutas = new javax.swing.JLabel();
        rutas = new javax.swing.JComboBox<>();
        campo_importe = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        btn_agregar = new javax.swing.JButton();
        panelRound7 = new paneles.PanelRound();
        lb_comprobante3 = new javax.swing.JLabel();
        panelRound8 = new paneles.PanelRound();
        campo_total = new javax.swing.JTextField();
        tituloTotal = new javax.swing.JTextField();
        subTotal = new javax.swing.JTextField();
        descuento_totales = new javax.swing.JTextField();
        impuestos_total = new javax.swing.JTextField();
        btn_eliminar = new javax.swing.JButton();
        tituloSubtotal = new javax.swing.JTextField();
        tituloDescuento = new javax.swing.JTextField();
        tituloImpuestos = new javax.swing.JTextField();
        btn_previsualizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Instituto Andrés Manuel López Obrador - Generar Factura");
        setMinimumSize(new java.awt.Dimension(1250, 735));
        setSize(new java.awt.Dimension(1200, 735));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        fondo.setBackground(new java.awt.Color(255, 255, 255));
        fondo.setMinimumSize(new java.awt.Dimension(1050, 650));
        fondo.setLayout(null);

        barra_nav.setBackground(new java.awt.Color(201, 69, 69));
        barra_nav.setLayout(null);

        Fecha.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        Fecha.setForeground(new java.awt.Color(255, 255, 255));
        Fecha.setText("Fecha: ");
        barra_nav.add(Fecha);
        Fecha.setBounds(40, 20, 260, 17);

        hora_lb.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        hora_lb.setForeground(new java.awt.Color(255, 255, 255));
        hora_lb.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        hora_lb.setText("Hora: ");
        barra_nav.add(hora_lb);
        hora_lb.setBounds(40, 50, 150, 17);

        contenedor_menu.setBackground(new java.awt.Color(201, 69, 69));
        contenedor_menu.setLayout(null);

        btn_alumnos.setBackground(new java.awt.Color(201, 69, 69));
        btn_alumnos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_alumnos.setVerifyInputWhenFocusTarget(false);
        btn_alumnos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_alumnosMouseClicked(evt);
            }
        });
        btn_alumnos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_alumnos.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_alumnos.setForeground(new java.awt.Color(255, 255, 255));
        txt_alumnos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_alumnos.setText("Alumnos");
        btn_alumnos.add(txt_alumnos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 30));

        icon_item.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_itemMenu.png"))); // NOI18N
        btn_alumnos.add(icon_item, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 8, 18, 15));

        contenedor_menu.add(btn_alumnos);
        btn_alumnos.setBounds(127, 37, 83, 30);

        btn_padres.setBackground(new java.awt.Color(201, 69, 69));
        btn_padres.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_padres.setVerifyInputWhenFocusTarget(false);
        btn_padres.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_padresMouseClicked(evt);
            }
        });
        btn_padres.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_padres.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_padres.setForeground(new java.awt.Color(255, 255, 255));
        txt_padres.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_padres.setText("Padres");
        btn_padres.add(txt_padres, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 30));

        icon_item2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_itemMenu.png"))); // NOI18N
        btn_padres.add(icon_item2, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 8, 18, 15));

        contenedor_menu.add(btn_padres);
        btn_padres.setBounds(0, 37, 83, 30);

        btn_facturas.setBackground(new java.awt.Color(201, 69, 69));
        btn_facturas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_facturas.setVerifyInputWhenFocusTarget(false);
        btn_facturas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_facturasMouseClicked(evt);
            }
        });
        btn_facturas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_factura.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_factura.setForeground(new java.awt.Color(255, 255, 255));
        txt_factura.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_factura.setText("Facturas");
        btn_facturas.add(txt_factura, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 30));

        icon_item3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_itemMenu.png"))); // NOI18N
        btn_facturas.add(icon_item3, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 8, 18, 15));

        contenedor_menu.add(btn_facturas);
        btn_facturas.setBounds(252, 37, 83, 30);

        btn_estadisticas.setBackground(new java.awt.Color(201, 69, 69));
        btn_estadisticas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_estadisticas.setVerifyInputWhenFocusTarget(false);
        btn_estadisticas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_estadisticasMouseClicked(evt);
            }
        });
        btn_estadisticas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_estadisticas.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_estadisticas.setForeground(new java.awt.Color(255, 255, 255));
        txt_estadisticas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_estadisticas.setText("Estadísticas");
        btn_estadisticas.add(txt_estadisticas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, 30));

        icon_item4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_itemMenu.png"))); // NOI18N
        btn_estadisticas.add(icon_item4, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 8, 18, 15));

        contenedor_menu.add(btn_estadisticas);
        btn_estadisticas.setBounds(375, 37, 120, 30);

        btn_emisor.setBackground(new java.awt.Color(201, 69, 69));
        btn_emisor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_emisor.setVerifyInputWhenFocusTarget(false);
        btn_emisor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_emisorMouseClicked(evt);
            }
        });
        btn_emisor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_emisor.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_emisor.setForeground(new java.awt.Color(255, 255, 255));
        txt_emisor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_emisor.setText("Emisor");
        btn_emisor.add(txt_emisor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 30));

        icon_item5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_itemMenu.png"))); // NOI18N
        btn_emisor.add(icon_item5, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 8, 18, 15));

        contenedor_menu.add(btn_emisor);
        btn_emisor.setBounds(520, 37, 90, 30);

        barra_nav.add(contenedor_menu);
        contenedor_menu.setBounds(260, 0, 610, 100);

        menu_user.setBackground(new java.awt.Color(201, 69, 69));
        menu_user.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_user.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu_userMouseClicked(evt);
            }
        });
        menu_user.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        user_menuIcon.setBackground(new java.awt.Color(0, 0, 0));
        user_menuIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        user_menuIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_usuarioMenu.png"))); // NOI18N
        menu_user.add(user_menuIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 50));

        barra_nav.add(menu_user);
        menu_user.setBounds(970, 25, 60, 50);

        fondo.add(barra_nav);
        barra_nav.setBounds(0, 0, 1050, 100);

        menu_salir.setBackground(new java.awt.Color(198, 54, 55));
        menu_salir.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        nombre_user.setBackground(new java.awt.Color(198, 54, 55));
        nombre_user.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        nombre_user.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        user_menuIcon1.setBackground(new java.awt.Color(0, 0, 0));
        user_menuIcon1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        user_menuIcon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_usuarioMenu.png"))); // NOI18N
        nombre_user.add(user_menuIcon1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 50));

        txt_nombreUser.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        txt_nombreUser.setForeground(new java.awt.Color(255, 255, 255));
        txt_nombreUser.setText("Administrador");
        nombre_user.add(txt_nombreUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 130, 50));

        menu_salir.add(nombre_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 210, -1));

        btn_historialSesiones.setBackground(new java.awt.Color(198, 54, 55));
        btn_historialSesiones.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_historialSesiones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_historialSesionesMouseClicked(evt);
            }
        });
        btn_historialSesiones.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        historial_lb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_historial.png"))); // NOI18N
        btn_historialSesiones.add(historial_lb, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 3, 40, 40));

        txt_cerrarSesion.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        txt_cerrarSesion.setForeground(new java.awt.Color(255, 255, 255));
        txt_cerrarSesion.setText(" Historial de sesiones");
        btn_historialSesiones.add(txt_cerrarSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 0, 190, 50));
        btn_historialSesiones.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 47, 250, 10));

        menu_salir.add(btn_historialSesiones, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 250, 50));

        btn_salir.setBackground(new java.awt.Color(198, 54, 55));
        btn_salir.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
        btn_salir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_salir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_salirMouseClicked(evt);
            }
        });
        btn_salir.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        icon_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_salir.png"))); // NOI18N
        btn_salir.add(icon_salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -5, 50, 50));
        btn_salir.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 43, 240, 10));

        text_salir.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        text_salir.setForeground(new java.awt.Color(255, 255, 255));
        text_salir.setText("Salir");
        btn_salir.add(text_salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 0, 150, 40));

        menu_salir.add(btn_salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 250, 60));

        btn_cerrarSesion.setBackground(new java.awt.Color(198, 54, 55));
        btn_cerrarSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cerrarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_cerrarSesionMouseClicked(evt);
            }
        });
        btn_cerrarSesion.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_cerrarSesion.png"))); // NOI18N
        btn_cerrarSesion.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 40, 50));

        txt_cerrarSesion1.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        txt_cerrarSesion1.setForeground(new java.awt.Color(255, 255, 255));
        txt_cerrarSesion1.setText("Cerrar sesión");
        btn_cerrarSesion.add(txt_cerrarSesion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 0, 150, 50));
        btn_cerrarSesion.add(jSeparator17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 47, 250, 10));

        menu_salir.add(btn_cerrarSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 250, 50));

        cerrar_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/x_menuUser.png"))); // NOI18N
        cerrar_icon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cerrar_icon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cerrar_iconMouseClicked(evt);
            }
        });
        menu_salir.add(cerrar_icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 40, 40));

        fondo.add(menu_salir);
        menu_salir.setBounds(790, 100, 260, 240);

        menu_estadisticas.setBackground(new java.awt.Color(198, 54, 55));
        menu_estadisticas.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menu_estadisticas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_facturasGeneradas.setBackground(new java.awt.Color(255, 255, 255));
        txt_facturasGeneradas.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_facturasGeneradas.setForeground(new java.awt.Color(255, 255, 255));
        txt_facturasGeneradas.setText("Facturas generadas");
        txt_facturasGeneradas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_estadisticas.add(txt_facturasGeneradas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 190, 40));

        txt_ingresos.setBackground(new java.awt.Color(255, 255, 255));
        txt_ingresos.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_ingresos.setForeground(new java.awt.Color(255, 255, 255));
        txt_ingresos.setText("Ingresos");
        txt_ingresos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_estadisticas.add(txt_ingresos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 190, 40));
        menu_estadisticas.add(jSeparator15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 150, 10));

        fondo.add(menu_estadisticas);
        menu_estadisticas.setBounds(600, 100, 200, 90);

        menu_emisor.setBackground(new java.awt.Color(198, 54, 55));
        menu_emisor.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menu_emisor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_editarEmisor.setBackground(new java.awt.Color(255, 255, 255));
        txt_editarEmisor.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_editarEmisor.setForeground(new java.awt.Color(255, 255, 255));
        txt_editarEmisor.setText("Editar emisor");
        txt_editarEmisor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txt_editarEmisor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_editarEmisorMouseClicked(evt);
            }
        });
        menu_emisor.add(txt_editarEmisor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 190, 40));
        menu_emisor.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 150, 10));

        txt_altaEmisor.setBackground(new java.awt.Color(255, 255, 255));
        txt_altaEmisor.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_altaEmisor.setForeground(new java.awt.Color(255, 255, 255));
        txt_altaEmisor.setText("Dar de alta emisor");
        txt_altaEmisor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txt_altaEmisor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_altaEmisorMouseClicked(evt);
            }
        });
        menu_emisor.add(txt_altaEmisor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 190, 40));
        menu_emisor.add(jSeparator16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 150, 10));

        txt_eliminarEmisor.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_eliminarEmisor.setForeground(new java.awt.Color(255, 255, 255));
        txt_eliminarEmisor.setText("Eliminar emisor");
        txt_eliminarEmisor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txt_eliminarEmisor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_eliminarEmisorMouseClicked(evt);
            }
        });
        menu_emisor.add(txt_eliminarEmisor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 190, 40));
        menu_emisor.add(jSeparator18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 150, 10));

        txt_ConsultarEmisor.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_ConsultarEmisor.setForeground(new java.awt.Color(255, 255, 255));
        txt_ConsultarEmisor.setText("Consultar Emisor");
        txt_ConsultarEmisor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txt_ConsultarEmisor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_ConsultarEmisorMouseClicked(evt);
            }
        });
        menu_emisor.add(txt_ConsultarEmisor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 190, 40));

        fondo.add(menu_emisor);
        menu_emisor.setBounds(800, 100, 200, 160);

        menu_padres.setBackground(new java.awt.Color(198, 54, 55));
        menu_padres.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_altaPadres.setBackground(new java.awt.Color(255, 255, 255));
        txt_altaPadres.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_altaPadres.setForeground(new java.awt.Color(255, 255, 255));
        txt_altaPadres.setText("Dar de alta padre");
        txt_altaPadres.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txt_altaPadres.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_altaPadresMouseClicked(evt);
            }
        });
        menu_padres.add(txt_altaPadres, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 190, 40));
        menu_padres.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 150, 10));

        txt_consultarPadres.setBackground(new java.awt.Color(255, 255, 255));
        txt_consultarPadres.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_consultarPadres.setForeground(new java.awt.Color(255, 255, 255));
        txt_consultarPadres.setText("Consultar Padre");
        txt_consultarPadres.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txt_consultarPadres.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_consultarPadresMouseClicked(evt);
            }
        });
        menu_padres.add(txt_consultarPadres, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 190, 40));
        menu_padres.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 150, 10));

        txt_modificarPadres.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_modificarPadres.setForeground(new java.awt.Color(255, 255, 255));
        txt_modificarPadres.setText("Modificar Padre");
        txt_modificarPadres.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txt_modificarPadres.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_modificarPadresMouseClicked(evt);
            }
        });
        menu_padres.add(txt_modificarPadres, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 190, 40));
        menu_padres.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 150, 10));

        txt_eliminarPadres.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_eliminarPadres.setForeground(new java.awt.Color(255, 255, 255));
        txt_eliminarPadres.setText("Eliminar Padre");
        txt_eliminarPadres.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txt_eliminarPadres.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_eliminarPadresMouseClicked(evt);
            }
        });
        menu_padres.add(txt_eliminarPadres, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 190, 40));

        fondo.add(menu_padres);
        menu_padres.setBounds(0, 100, 200, 160);

        menu_alumnos.setBackground(new java.awt.Color(198, 54, 55));
        menu_alumnos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_altaAlumnos.setBackground(new java.awt.Color(255, 255, 255));
        txt_altaAlumnos.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_altaAlumnos.setForeground(new java.awt.Color(255, 255, 255));
        txt_altaAlumnos.setText("Dar de alta Alumno");
        txt_altaAlumnos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txt_altaAlumnos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_altaAlumnosMouseClicked(evt);
            }
        });
        menu_alumnos.add(txt_altaAlumnos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 190, 40));
        menu_alumnos.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 150, 10));

        txt_consultarAlmnos.setBackground(new java.awt.Color(255, 255, 255));
        txt_consultarAlmnos.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_consultarAlmnos.setForeground(new java.awt.Color(255, 255, 255));
        txt_consultarAlmnos.setText("Consultar Alumno");
        txt_consultarAlmnos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txt_consultarAlmnos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_consultarAlmnosMouseClicked(evt);
            }
        });
        menu_alumnos.add(txt_consultarAlmnos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 190, 40));
        menu_alumnos.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 150, 10));

        txt_modificarAlumnos.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_modificarAlumnos.setForeground(new java.awt.Color(255, 255, 255));
        txt_modificarAlumnos.setText("Modificar Alumno");
        txt_modificarAlumnos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txt_modificarAlumnos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_modificarAlumnosMouseClicked(evt);
            }
        });
        menu_alumnos.add(txt_modificarAlumnos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 190, 40));
        menu_alumnos.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 150, 10));

        txt_eliminarAlumno.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_eliminarAlumno.setForeground(new java.awt.Color(255, 255, 255));
        txt_eliminarAlumno.setText("Eliminar Alumno");
        txt_eliminarAlumno.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txt_eliminarAlumno.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_eliminarAlumnoMouseClicked(evt);
            }
        });
        menu_alumnos.add(txt_eliminarAlumno, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 190, 40));

        fondo.add(menu_alumnos);
        menu_alumnos.setBounds(200, 100, 200, 160);

        menu_factura.setBackground(new java.awt.Color(198, 54, 55));
        menu_factura.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menu_factura.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_generarFcatura.setBackground(new java.awt.Color(255, 255, 255));
        txt_generarFcatura.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_generarFcatura.setForeground(new java.awt.Color(255, 255, 255));
        txt_generarFcatura.setText("Generar factura");
        txt_generarFcatura.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_factura.add(txt_generarFcatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 190, 40));

        txt_consultarAlmnos1.setBackground(new java.awt.Color(255, 255, 255));
        txt_consultarAlmnos1.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_consultarAlmnos1.setForeground(new java.awt.Color(255, 255, 255));
        txt_consultarAlmnos1.setText("Consultar facturas");
        txt_consultarAlmnos1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_factura.add(txt_consultarAlmnos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 190, 40));
        menu_factura.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 150, 10));

        fondo.add(menu_factura);
        menu_factura.setBounds(400, 100, 200, 90);

        contenedor.setBackground(new java.awt.Color(255, 255, 255));
        contenedor.setBorder(null);
        contenedor.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        contenedor.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1040, 2100));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        icon_regresarlb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon_regresarlb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_regresar.png"))); // NOI18N
        icon_regresarlb.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        icon_regresarlb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                icon_regresarlbMouseClicked(evt);
            }
        });
        jPanel1.add(icon_regresarlb, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 60, 60));

        jLabel1.setFont(new java.awt.Font("Roboto", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Generar factura");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 1190, 50));

        btn_enviarCorreo.setBackground(new java.awt.Color(0, 153, 255));
        btn_enviarCorreo.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btn_enviarCorreo.setForeground(new java.awt.Color(255, 255, 255));
        btn_enviarCorreo.setText("Sellar y enviar por correo");
        btn_enviarCorreo.setEnabled(false);
        btn_enviarCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_enviarCorreoActionPerformed(evt);
            }
        });
        jPanel1.add(btn_enviarCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 2030, 210, 40));

        panelRound1.setRoundBottomLeft(50);
        panelRound1.setRoundBottomRight(50);
        panelRound1.setRoundTopLeft(50);
        panelRound1.setRoundTopRight(50);
        panelRound1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lb_comprobante.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lb_comprobante.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_comprobante.setText("Datos del padre");
        panelRound1.add(lb_comprobante, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 210, 30));

        panelRound2.setBackground(new java.awt.Color(255, 255, 255));
        panelRound2.setRoundBottomLeft(50);
        panelRound2.setRoundBottomRight(50);
        panelRound2.setRoundTopLeft(50);
        panelRound2.setRoundTopRight(50);
        panelRound2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_nombrePadre.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        txt_nombrePadre.setText("Regimen fiscal");
        panelRound2.add(txt_nombrePadre, new org.netbeans.lib.awtextra.AbsoluteConstraints(347, 104, 150, 30));

        jLabel13.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel13.setText("Apellido paterno");
        panelRound2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(597, 24, 179, 30));

        apellidoPaterno_padre.setEditable(false);
        apellidoPaterno_padre.setBackground(new java.awt.Color(255, 255, 255));
        apellidoPaterno_padre.setFocusCycleRoot(true);
        apellidoPaterno_padre.setFocusable(false);
        panelRound2.add(apellidoPaterno_padre, new org.netbeans.lib.awtextra.AbsoluteConstraints(597, 54, 190, 36));

        jLabel14.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel14.setText("Domicilio fiscal");
        panelRound2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(347, 194, 150, 30));

        regimen_padre.setEditable(false);
        regimen_padre.setBackground(new java.awt.Color(255, 255, 255));
        regimen_padre.setFocusable(false);
        panelRound2.add(regimen_padre, new org.netbeans.lib.awtextra.AbsoluteConstraints(347, 140, 300, 36));

        icon_buscarPadre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/btn_buscar3.png"))); // NOI18N
        icon_buscarPadre.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        icon_buscarPadre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                icon_buscarPadreMouseClicked(evt);
            }
        });
        panelRound2.add(icon_buscarPadre, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 44, 50, 50));

        rfc_padre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                rfc_padreFocusLost(evt);
            }
        });
        rfc_padre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rfc_padreActionPerformed(evt);
            }
        });
        rfc_padre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                rfc_padreKeyTyped(evt);
            }
        });
        panelRound2.add(rfc_padre, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 54, 160, 36));

        jLabel9.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel9.setText("RFC");
        panelRound2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 24, 160, 30));

        nombre_padre.setEditable(false);
        nombre_padre.setBackground(new java.awt.Color(255, 255, 255));
        nombre_padre.setFocusable(false);
        panelRound2.add(nombre_padre, new org.netbeans.lib.awtextra.AbsoluteConstraints(347, 54, 200, 36));

        txt_nombrePadre2.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        txt_nombrePadre2.setText("Nombre (s)");
        panelRound2.add(txt_nombrePadre2, new org.netbeans.lib.awtextra.AbsoluteConstraints(347, 24, 150, 30));

        apellidoMaterno_padre.setEditable(false);
        apellidoMaterno_padre.setBackground(new java.awt.Color(255, 255, 255));
        apellidoMaterno_padre.setFocusable(false);
        panelRound2.add(apellidoMaterno_padre, new org.netbeans.lib.awtextra.AbsoluteConstraints(868, 54, 180, 36));

        domicilio_padre.setEditable(false);
        domicilio_padre.setBackground(new java.awt.Color(255, 255, 255));
        domicilio_padre.setFocusable(false);
        panelRound2.add(domicilio_padre, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 230, 150, 36));

        jLabel17.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel17.setText("Apellido materno");
        panelRound2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(868, 24, 180, 30));

        titulo_correoPadre.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        titulo_correoPadre.setText("Correo");
        panelRound2.add(titulo_correoPadre, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 194, 150, 30));

        correo_padre.setEditable(false);
        correo_padre.setBackground(new java.awt.Color(255, 255, 255));
        correo_padre.setFocusable(false);
        panelRound2.add(correo_padre, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 230, 300, 36));

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel6.setText("Uso de CFDI");
        panelRound2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 104, -1, 30));

        uso_cfdi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<seleccionar>" }));
        uso_cfdi.setEnabled(false);
        uso_cfdi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                uso_cfdiItemStateChanged(evt);
            }
        });
        panelRound2.add(uso_cfdi, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 140, 247, 36));

        panelRound1.add(panelRound2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 1130, 280));

        jPanel1.add(panelRound1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, 1160, 350));

        panelRound3.setRoundBottomLeft(50);
        panelRound3.setRoundBottomRight(50);
        panelRound3.setRoundTopLeft(50);
        panelRound3.setRoundTopRight(50);
        panelRound3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lb_comprobante1.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lb_comprobante1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_comprobante1.setText("Comprobante");
        panelRound3.add(lb_comprobante1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 210, 30));

        panelRound4.setBackground(new java.awt.Color(255, 255, 255));
        panelRound4.setRoundBottomLeft(50);
        panelRound4.setRoundBottomRight(50);
        panelRound4.setRoundTopLeft(50);
        panelRound4.setRoundTopRight(50);
        panelRound4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        titulo_tipoFactura1.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        titulo_tipoFactura1.setText("Tipo de factura");
        panelRound4.add(titulo_tipoFactura1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 220, 25));

        metodoPago.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        metodoPago.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        metodoPago.setText("PUE (Pago en una solo exhibición)");
        panelRound4.add(metodoPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 180, 240, 36));

        titulo_regimenEmisor1.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        titulo_regimenEmisor1.setText("RFC emisor");
        panelRound4.add(titulo_regimenEmisor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 126, 27));

        moneda.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        moneda.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        moneda.setText("Peso Mexicano");
        panelRound4.add(moneda, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 270, 200, 36));

        titulo_cpEmision1.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        titulo_cpEmision1.setText("Código postal");
        panelRound4.add(titulo_cpEmision1, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 40, 160, 25));

        rfc_emisor.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        rfc_emisor.setText("RACA031202HGRMSNA2");
        panelRound4.add(rfc_emisor, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 240, 36));

        titulo_formaPago.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        titulo_formaPago.setText("Forma de pago");
        panelRound4.add(titulo_formaPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 150, 280, 25));

        formaPago.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        formaPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01 Efectivo", "02 Transferencia electrónica de fondos" }));
        panelRound4.add(formaPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 180, -1, 35));

        titulo_metodoPago.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        titulo_metodoPago.setText("Método de pago");
        panelRound4.add(titulo_metodoPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 148, 200, 25));

        tituloMoneda1.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        tituloMoneda1.setText("Moneda");
        panelRound4.add(tituloMoneda1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, 200, 25));

        tipo_factura.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        tipo_factura.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        tipo_factura.setText("I Ingreso");
        panelRound4.add(tipo_factura, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 220, 36));

        iconFac_lb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_factura.png"))); // NOI18N
        panelRound4.add(iconFac_lb, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 40, 27, 25));

        iconFac_lb1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_factura.png"))); // NOI18N
        panelRound4.add(iconFac_lb1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 27, 25));

        iconFac_lb2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_factura.png"))); // NOI18N
        panelRound4.add(iconFac_lb2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 27, 25));

        iconFac_lb3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_factura.png"))); // NOI18N
        panelRound4.add(iconFac_lb3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 27, 25));

        iconFac_lb4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_factura.png"))); // NOI18N
        panelRound4.add(iconFac_lb4, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 150, 27, 25));

        iconFac_lb5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_factura.png"))); // NOI18N
        panelRound4.add(iconFac_lb5, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 150, 27, 25));

        titulo_regimenEmisor2.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        titulo_regimenEmisor2.setText("Regimen emisor");
        panelRound4.add(titulo_regimenEmisor2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 50, 330, 27));

        iconFac_lb6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_factura.png"))); // NOI18N
        panelRound4.add(iconFac_lb6, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 50, 27, 25));

        regimen_emisor.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        regimen_emisor.setText("Persona Física con Actividad Empresarial (Clave 612)");
        panelRound4.add(regimen_emisor, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 80, 380, 36));

        cp_expedicion1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cp_expedicion1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cp_expedicion1.setText("39890");
        panelRound4.add(cp_expedicion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 70, 160, 36));

        panelRound3.add(panelRound4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 1130, 320));

        jPanel1.add(panelRound3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 1160, 390));

        panelRound5.setRoundBottomLeft(50);
        panelRound5.setRoundBottomRight(50);
        panelRound5.setRoundTopLeft(50);
        panelRound5.setRoundTopRight(50);
        panelRound5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lb_comprobante2.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lb_comprobante2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_comprobante2.setText("Servicio");
        panelRound5.add(lb_comprobante2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 160, 30));

        panelRound6.setBackground(new java.awt.Color(255, 255, 255));
        panelRound6.setRoundBottomLeft(50);
        panelRound6.setRoundBottomRight(50);
        panelRound6.setRoundTopLeft(50);
        panelRound6.setRoundTopRight(50);

        jLabel8.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel8.setText("Nombre (s)");

        nombres_alumno.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        nombres_alumno.setEnabled(false);
        nombres_alumno.setRequestFocusEnabled(false);
        nombres_alumno.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                nombres_alumnoItemStateChanged(evt);
            }
        });

        txt_nombrePadre1.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        txt_nombrePadre1.setText("Nivel escolar");

        jLabel15.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel15.setText("Apellido paterno");

        apellidoPaterno_alumno.setEditable(false);
        apellidoPaterno_alumno.setBackground(new java.awt.Color(255, 255, 255));
        apellidoPaterno_alumno.setFocusCycleRoot(true);
        apellidoPaterno_alumno.setFocusable(false);

        apellidoMaterno_alumno.setEditable(false);
        apellidoMaterno_alumno.setBackground(new java.awt.Color(255, 255, 255));
        apellidoMaterno_alumno.setFocusable(false);

        jLabel16.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel16.setText("Apellido materno");

        nivel_escolar.setEditable(false);
        nivel_escolar.setBackground(new java.awt.Color(255, 255, 255));
        nivel_escolar.setFocusable(false);

        curp_alumno.setEditable(false);
        curp_alumno.setBackground(new java.awt.Color(255, 255, 255));
        curp_alumno.setFocusable(false);

        txt_nombrePadre3.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        txt_nombrePadre3.setText("Curp");

        txt_nombrePadre4.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        txt_nombrePadre4.setText("Grado escolar");

        grado_escolar.setEditable(false);
        grado_escolar.setBackground(new java.awt.Color(255, 255, 255));
        grado_escolar.setFocusable(false);

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel3.setText("Seleccionar hijo");

        servicio.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        servicio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pago de colegiatura", "Pago de servicio de transporte escolar" }));
        servicio.setEnabled(false);
        servicio.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                servicioItemStateChanged(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel5.setText("Seleccionar servicio");

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel7.setText("Objeto de impuestos");

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel10.setText("Unidad");

        cantidad.setEditable(false);
        cantidad.setBackground(new java.awt.Color(204, 204, 204));
        cantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cantidad.setFocusable(false);

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel11.setText("Precio unitario");

        unidad.setEditable(false);
        unidad.setBackground(new java.awt.Color(204, 204, 204));
        unidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        unidad.setFocusable(false);

        obj_impuestos.setEditable(false);
        obj_impuestos.setBackground(new java.awt.Color(204, 204, 204));
        obj_impuestos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        obj_impuestos.setFocusable(false);

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel12.setText("Cantidad");

        campo_precioUnitario.setEditable(false);
        campo_precioUnitario.setBackground(new java.awt.Color(204, 204, 204));
        campo_precioUnitario.setFocusable(false);

        jLabel19.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel19.setText("Importe");

        clave.setEditable(false);
        clave.setBackground(new java.awt.Color(204, 204, 204));
        clave.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        clave.setFocusable(false);

        titulo_rutas.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        titulo_rutas.setText("Rutas");

        rutas.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        rutas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Calzada - Centro -Ruta corta", "Calzada - Costera - Ruta media", "Calzada - Coloso - Ruta Larga" }));
        rutas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rutasItemStateChanged(evt);
            }
        });

        campo_importe.setEditable(false);
        campo_importe.setBackground(new java.awt.Color(204, 204, 204));
        campo_importe.setFocusable(false);

        jLabel21.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel21.setText("Clave");

        btn_agregar.setBackground(new java.awt.Color(0, 0, 204));
        btn_agregar.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btn_agregar.setForeground(new java.awt.Color(255, 255, 255));
        btn_agregar.setText("Agregar");
        btn_agregar.setBorder(null);
        btn_agregar.setEnabled(false);
        btn_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRound6Layout = new javax.swing.GroupLayout(panelRound6);
        panelRound6.setLayout(panelRound6Layout);
        panelRound6Layout.setHorizontalGroup(
            panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound6Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound6Layout.createSequentialGroup()
                        .addGroup(panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelRound6Layout.createSequentialGroup()
                                .addGroup(panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(nombres_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelRound6Layout.createSequentialGroup()
                                        .addGap(140, 140, 140)
                                        .addGroup(panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(apellidoPaterno_alumno)
                                            .addComponent(txt_nombrePadre1, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(nivel_escolar, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                                        .addGap(60, 60, 60)
                                        .addGroup(panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txt_nombrePadre4, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                            .addComponent(apellidoMaterno_alumno)
                                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(30, 30, 30)
                                        .addGroup(panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(curp_alumno, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                            .addComponent(txt_nombrePadre3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(panelRound6Layout.createSequentialGroup()
                                        .addGap(380, 380, 380)
                                        .addComponent(grado_escolar, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(panelRound6Layout.createSequentialGroup()
                                .addGroup(panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(campo_precioUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 79, Short.MAX_VALUE)
                                .addGroup(panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(panelRound6Layout.createSequentialGroup()
                                        .addGroup(panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(campo_importe, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(270, 270, 270))
                                    .addGroup(panelRound6Layout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(62, 62, 62)
                                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelRound6Layout.createSequentialGroup()
                                        .addComponent(unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(62, 62, 62)
                                        .addComponent(clave, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelRound6Layout.createSequentialGroup()
                                        .addGap(41, 41, 41)
                                        .addComponent(obj_impuestos, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelRound6Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(18, 18, 18))
                    .addGroup(panelRound6Layout.createSequentialGroup()
                        .addGroup(panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(servicio, javax.swing.GroupLayout.Alignment.LEADING, 0, 346, Short.MAX_VALUE))
                        .addGap(317, 317, 317)
                        .addGroup(panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelRound6Layout.createSequentialGroup()
                                .addComponent(rutas, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound6Layout.createSequentialGroup()
                                .addComponent(titulo_rutas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(87, 87, 87))))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(450, 450, 450))
        );
        panelRound6Layout.setVerticalGroup(
            panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_nombrePadre3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nombres_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(apellidoPaterno_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(curp_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(apellidoMaterno_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addGroup(panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nombrePadre1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nombrePadre4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(grado_escolar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nivel_escolar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(titulo_rutas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(servicio, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rutas, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addGroup(panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(obj_impuestos, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clave, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campo_precioUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campo_importe, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(btn_agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        panelRound5.add(panelRound6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 1120, 700));

        jPanel1.add(panelRound5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 920, 1160, 770));

        panelRound7.setRoundBottomLeft(50);
        panelRound7.setRoundBottomRight(50);
        panelRound7.setRoundTopLeft(50);
        panelRound7.setRoundTopRight(50);
        panelRound7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lb_comprobante3.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lb_comprobante3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_comprobante3.setText("Totales");
        panelRound7.add(lb_comprobante3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 150, 30));

        panelRound8.setBackground(new java.awt.Color(255, 255, 255));
        panelRound8.setRoundBottomLeft(50);
        panelRound8.setRoundBottomRight(50);
        panelRound8.setRoundTopLeft(50);
        panelRound8.setRoundTopRight(50);
        panelRound8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        campo_total.setEditable(false);
        campo_total.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        campo_total.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campo_total.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        campo_total.setFocusable(false);
        panelRound8.add(campo_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 70, 210, 40));

        tituloTotal.setEditable(false);
        tituloTotal.setBackground(new java.awt.Color(198, 54, 55));
        tituloTotal.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        tituloTotal.setForeground(new java.awt.Color(255, 255, 255));
        tituloTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tituloTotal.setText("Total");
        tituloTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        tituloTotal.setFocusable(false);
        tituloTotal.setMargin(new java.awt.Insets(10, 6, 2, 6));
        panelRound8.add(tituloTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 30, 210, 40));

        subTotal.setEditable(false);
        subTotal.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        subTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        subTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        subTotal.setFocusable(false);
        panelRound8.add(subTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 220, 40));

        descuento_totales.setEditable(false);
        descuento_totales.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        descuento_totales.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        descuento_totales.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        descuento_totales.setFocusable(false);
        panelRound8.add(descuento_totales, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 70, 230, 40));

        impuestos_total.setEditable(false);
        impuestos_total.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        impuestos_total.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        impuestos_total.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        impuestos_total.setFocusable(false);
        panelRound8.add(impuestos_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 70, 230, 40));

        btn_eliminar.setBackground(new java.awt.Color(255, 0, 0));
        btn_eliminar.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btn_eliminar.setForeground(new java.awt.Color(255, 255, 255));
        btn_eliminar.setText("Eliminar");
        btn_eliminar.setBorder(null);
        btn_eliminar.setEnabled(false);
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });
        panelRound8.add(btn_eliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 150, 173, 45));

        tituloSubtotal.setEditable(false);
        tituloSubtotal.setBackground(new java.awt.Color(198, 54, 55));
        tituloSubtotal.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        tituloSubtotal.setForeground(new java.awt.Color(255, 255, 255));
        tituloSubtotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tituloSubtotal.setText("Subtotal");
        tituloSubtotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        tituloSubtotal.setFocusable(false);
        tituloSubtotal.setMargin(new java.awt.Insets(10, 6, 2, 6));
        panelRound8.add(tituloSubtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 220, 40));

        tituloDescuento.setEditable(false);
        tituloDescuento.setBackground(new java.awt.Color(198, 54, 55));
        tituloDescuento.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        tituloDescuento.setForeground(new java.awt.Color(255, 255, 255));
        tituloDescuento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tituloDescuento.setText("Descuento");
        tituloDescuento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        tituloDescuento.setFocusable(false);
        tituloDescuento.setMargin(new java.awt.Insets(10, 6, 2, 6));
        panelRound8.add(tituloDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, 230, 40));

        tituloImpuestos.setEditable(false);
        tituloImpuestos.setBackground(new java.awt.Color(198, 54, 55));
        tituloImpuestos.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        tituloImpuestos.setForeground(new java.awt.Color(255, 255, 255));
        tituloImpuestos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tituloImpuestos.setText("Impuestos");
        tituloImpuestos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        tituloImpuestos.setFocusable(false);
        tituloImpuestos.setMargin(new java.awt.Insets(10, 6, 2, 6));
        panelRound8.add(tituloImpuestos, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 30, 230, 40));

        panelRound7.add(panelRound8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 1130, 210));

        jPanel1.add(panelRound7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 1720, 1160, 280));

        btn_previsualizar.setBackground(new java.awt.Color(0, 153, 255));
        btn_previsualizar.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btn_previsualizar.setForeground(new java.awt.Color(255, 255, 255));
        btn_previsualizar.setText("Previsualizar");
        btn_previsualizar.setEnabled(false);
        btn_previsualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_previsualizarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_previsualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 2030, 210, 40));

        contenedor.setViewportView(jPanel1);

        fondo.add(contenedor);
        contenedor.setBounds(0, 100, 1200, 600);

        getContentPane().add(fondo, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void setDatos(String usuario, LocalDate fechaInicioSesion, LocalTime horaInicioSesion){
        this.usuario=usuario;
        this.fechaInicioSesion = fechaInicioSesion;
        this.horaInicioSesion = horaInicioSesion;
        txt_nombreUser.setText(usuario);
        //solo muestra el menu de emisor si el usuario es el director
        if(!"director".equals(this.usuario)){
            btn_emisor.setVisible(false);
        }
    }
    
    private void cargarEmisor() {
        try {
            //Prepara la consulta para verificar si existe el RFC
            String consulta_rfc = "SELECT * FROM emisor";
            PreparedStatement ps = cx.conectar().prepareStatement(consulta_rfc);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rfc_emisor.setText(rs.getString("rfc"));
                regimen_emisor.setText(rs.getString("regimen"));
                emisor = new Emisor(rs.getString("rfc"), rs.getString("nombres"), rs.getString("apellido_paterno"), rs.getString("apellido_materno"), 
                        rs.getString("regimen"), rs.getString("domicilio_fiscal"));
            } else {

            }
        } catch (SQLException ex) {
            Logger.getLogger(AltaEmisorPrim.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void obtenerDatosPadre(String rfc) {
        try {
            //Prepara la consulta para verificar si existe el RFC
            String consulta_rfc = "SELECT * FROM padre_familia WHERE rfc = ?";
            PreparedStatement ps = cx.conectar().prepareStatement(consulta_rfc);
            ps.setString(1, rfc);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                //llenar los campos
                nombre_padre.setText(rs.getString("nombres"));
                apellidoPaterno_padre.setText(rs.getString("apellido_paterno"));
                apellidoMaterno_padre.setText(rs.getString("apellido_materno"));
                regimen_padre.setText(rs.getString("regimen"));
                domicilio_padre.setText(rs.getString("domicilio_fiscal"));
                correo_padre.setText(rs.getString("correo_electronico"));
                //obtener los posibles usos de CFDI
                obtenerUsosCFDI(rs.getString("regimen"));
                //carga los hijos del padre
                cargarHijos(rfc);
                //habilita los demas botones
                btn_agregar.setEnabled(true);
                uso_cfdi.setEnabled(true);
                
                //Crear objetto para el receptor
                receptor = new Receptor(rfc, rs.getString("nombres"), rs.getString("apellido_paterno"),rs.getString("apellido_materno"), 
                        rs.getString("regimen"), rs.getString("domicilio_fiscal"), rs.getString("correo_electronico"));
            } else {
                JOptionPane.showMessageDialog(null, "El RFEC del padre que busca no se encuentra registrado", 
                        "Padre no registrado", JOptionPane.WARNING_MESSAGE);
                LimpiarCampos();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AltaEmisorPrim.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void obtenerDatosAlumno(String curp) {
        try {
            int i=0;
            String consulta_rfc = "SELECT * FROM alumnos WHERE curp =?";
            PreparedStatement ps = cx.conectar().prepareStatement(consulta_rfc);
            ps.setString(1, curp);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                //llena los campos
                curp_alumno.setText(rs.getString("curp"));
                apellidoPaterno_alumno.setText(rs.getString("apellido_paterno"));
                apellidoMaterno_alumno.setText(rs.getString("apellido_materno"));
                nivel_escolar.setText(rs.getString("nivel_escolaridad")); 
                grado_escolar.setText(rs.getString("grado_escolar"));
                
                //crea el objeto alumno
                alumno = new Alumno(rs.getString("curp"),rs.getString("nombres"),rs.getString("apellido_paterno"),rs.getString("apellido_materno"),
                        rs.getString("nivel_escolaridad"), rs.getString("grado_escolar"));
                alumno.setClave_escuela(obtenerClaveEscuela(alumno.getNivel_escolar()));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AltaEmisorPrim.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void cargarHijos(String rfcPadre) {
        try {
            nombres_alumno.removeAllItems();
// Seleccionar los datos del emisor
            String consulta = "SELECT * FROM alumnos WHERE rfc_padre = ?";
            PreparedStatement ps = cx.conectar().prepareStatement(consulta);
            ps.setString(1, rfcPadre);
            ResultSet rs = ps.executeQuery();

        // Verificar si hay resultados
            if (!rs.next()) { // Si no hay filas en el resultado
                JOptionPane.showMessageDialog(null,
                        "El padre seleccionado no cuenta con ningún hijo registrado\n"
                        + "Registre un nuevo alumno en Alumnos > Dar de alta alumno",
                        "Sin hijos registrados",
                        JOptionPane.WARNING_MESSAGE);
                btn_agregar.setEnabled(false);
                return;
            }
            // Procesar los datos si se encontraron
            do {
                btn_agregar.setEnabled(true);
                nombres_alumno.setEnabled(true);
                nombres_alumno.addItem(rs.getString("nombres") + "                                           - \n" + rs.getString("curp"));
            } while (rs.next());

        } catch (SQLException ex) {
            Logger.getLogger(GenerarFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void obtenerUsosCFDI(String regimen){
        if(regimen.equalsIgnoreCase("626 Simplificado de Confianza")){
            uso_cfdi.removeAllItems();//eliminar las opciones anteriores
            uso_cfdi.addItem("G03 Gastos en general");
        }else{
            uso_cfdi.removeAllItems();//eliminar las opciones anteriores
            uso_cfdi.addItem("D10 Pagos por servicios educativos");
            uso_cfdi.addItem("D08 Transporte escolar");
        }
    }
    
    private String obtenerClaveEscuela(String nivelEscolar){
        String claveAlumno="";
        switch (nivelEscolar) {
            case "Preescolar":
                claveAlumno="123456789";
                break;
            case "Primaria":
                claveAlumno="1234567891";
                break;
            case "Secundaria":
                claveAlumno="123456782";
                break;
        }
        return claveAlumno;
    }
    
    private void obtenerCostosColegiatura(){
        //obtener titulos
        cantidad.setText("1");
        unidad.setText("E48 / Unidad de servicio");
        clave.setText("861221500");
        obj_impuestos.setText("No objeto de impuestos");
        //generar escripcion
        descripcion = "PAGO POR CONCEPTO DE COLEGIATURA DEL"
                + "INSTITUTO ANDRÉS MANUEL LÓPEZ OBRADOR, CON CLAVE: "+alumno.getClave_escuela()+" CORRESPONDIENTE AL MES DE "+mesEnLetras.toUpperCase()+
                " DEL "+anioActual+", DEL ALUMNO " + alumno.getNombreCompletoMayus() + ", QUE CURSA EL " + alumno.getGrado_escolar().toUpperCase() + " GRADO DE "
                + alumno.getNivel_escolar().toUpperCase() + ". CURP: " + alumno.getCurp() + ".";
                
        //Obtener precios para colegiaturas
        switch (alumno.getNivel_escolar()) {
            case "Preescolar":
                precio = 2000.00;
                importe=2000.00;
                campo_precioUnitario.setText("$"+precio);
                campo_importe.setText("$"+importe);
                break;
            case "Primaria":
                precio = 2500.00;
                importe=2500.0;
                campo_precioUnitario.setText("$"+precio);
                campo_importe.setText("$"+importe);
                break;
            case "Secundaria":
                precio=3500.00;
                importe=3500.00;
                campo_precioUnitario.setText("$"+precio);
                campo_importe.setText("$"+importe);
                break;
        }
    }
    
    private void obtenerCostosTransporte() {
        String rutas_descrip[]=rutas.getSelectedItem().toString().split("-");
        //obtener titulos
        cantidad.setText("1");
        unidad.setText("E48 / Unidad de servicio");
        clave.setText("861221500");
        obj_impuestos.setText("No objeto de impuestos");
        //generar escripcion
        descripcion = "PAGO POR CONCEPTO DE SERVICIO DE TRANSPORTE DE LA RUTA: "+rutas_descrip[0].toUpperCase()+" A "+rutas_descrip[1].toUpperCase()+", DEL "
                + "INSTITUTO ANDRÉS MANUEL LÓPEZ OBRADOR, CON CLAVE: " + alumno.getClave_escuela() + " CORRESPONDIENTE AL MES DE "+mesEnLetras.toUpperCase()
                + "DEL "+anioActual+", DEL ALUMNO " + alumno.getNombreCompletoMayus() + ", QUE CURSA EL " + alumno.getGrado_escolar().toUpperCase() + " GRADO DE "
                + alumno.getNivel_escolar().toUpperCase() + ". CURP: " + alumno.getCurp() + ".";

        if (rutas.getSelectedIndex() == 0) {
            precio = 600.00;
            importe = 600.00;
            campo_precioUnitario.setText("$" + precio);
            campo_importe.setText("$" + importe);
            return;
        }
        if (rutas.getSelectedIndex() == 1) {
            precio = 1000.00;
            importe = 1000.00;
            campo_precioUnitario.setText("$" + precio);
            campo_importe.setText("$" + importe);
            return;
        }
        if (rutas.getSelectedIndex() == 2) {
            precio = 1600.00;
            importe = 1600.00;
            campo_precioUnitario.setText("$" + precio);
            campo_importe.setText("$" + importe);
            return;
        }
    }

   public void sellarFactura() throws DocumentException, IOException {
        // Obtener la fecha y hora actual
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        // Definir el formato deseado
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a");
        // Aplicar el formato a la fecha y hora actual
        String fechaHoraSellada = fechaHoraActual.format(formato);
        //Añadir la hora en la que se crea la factura
        factura.setFechaHoraSellada(fechaHoraSellada);
        validarsellos();//crear los sellos de la factra
        
        String sql = "INSERT INTO facturas_generadas (folio_factura, rfc_emisor, rfc_receptor, fecha, forma_pago, metodo_De_Pago, uso_CFDI, cantidad,"
                + " unidad, clave_producto, descripcion, obj_impuestos, precio_unitario, importe, subtotal, impuestos, descuento, total, folio_sat, "
                + "num_certificacion_emisor, num_certificacion_SAT, cadena_complemento_certificacion_SAT, sello_SAT, sello_CFDI, usuario_responsable)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement ps = cx.conectar().prepareStatement(sql);//Creacion de la consulta
            ps.setInt(1, factura.getId_factura());
            ps.setString(2, emisor.getRfc());
            ps.setString(3, receptor.getRfc());
            ps.setString(4, factura.getFechaHoraSellada());
            ps.setString(5, factura.getForma_pago());
            ps.setString(6, factura.getMetodo_pago());
            ps.setString(7, receptor.getUso_CFDI());
            ps.setString(8, factura.getCantidad());
            ps.setString(9, factura.getUnidad());
            ps.setString(10, factura.getClave());
            ps.setString(11, factura.getDescripcion());
            ps.setString(12, factura.getObj_impuestos());
            ps.setDouble(13, factura.getPrecio_unitario());
            ps.setDouble(14, factura.getImporte());
            ps.setDouble(15, factura.getSubtotal());
            ps.setDouble(16, factura.getImpuesto());
            ps.setDouble(17, factura.getDescuento());
            ps.setDouble(18, factura.getTotal());
            ps.setString(19, factura.getFolioSat());
            ps.setString(20, factura.getNumero_serie_certificado_emisor());
            ps.setString(21, factura.getNumero_serie_certificado_SAT());
            ps.setString(22, factura.getCadena_original_complemento_certificacion_digital_SAT());
            ps.setString(23, factura.getSello_digital_CFDI());
            ps.setString(24, factura.getSello_SAT());
            ps.setString(25, "director");

            // Ejecutar la consulta
            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                //crear psdf
                FacturaPDF facturaPdf = new FacturaPDF();
                facturaPdf.generarFacturaPDF(emisor, receptor, alumno, factura);
                limpiarTotales();
                LimpiarCampos();
                JOptionPane.showMessageDialog(null, "La factura ha sido generada correctamente\n"
                        + "Puede encontrar los archivos en la ruta de >>Descargas", "Factura generada correctamente", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void validarsellos() {
        //Genera todos los sellos
        Sellos sello = new Sellos();
        factura.setFolioSat(sello.generarFolioSAT());
        factura.setNumero_serie_certificado_emisor(sello.generarNumeroSerieEmisor());
        factura.setNumero_serie_certificado_SAT(sello.generarNumeroSerieSAT());
        factura.setCadena_original_complemento_certificacion_digital_SAT(sello.generarCadenaSAT(emisor.getRfc(), 
        factura.getNumero_serie_certificado_emisor(),factura.getFechaHoraSellada()));
        factura.setSello_digital_CFDI(sello.generarSello());
        factura.setSello_SAT(sello.generarSello());
    }
    
    private void generarIdFactura() {
        String sql = "SELECT COUNT(*) AS total FROM facturas_generadas";   //obtener el numero total de facturas   
        try {
            PreparedStatement ps = cx.conectar().prepareStatement(sql);//Creacion de la consulta
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id_factura = rs.getInt("total")+1;
                factura.setId_factura(id_factura);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    private void limpiarTotales(){
        subTotal.setText("");
        descuento_totales.setText("");
        impuestos_total.setText("");
        campo_total.setText("");
    }
    
    private void LimpiarCampos(){
        //deshabilita el boton de agregar
       btn_agregar.setEnabled(false); 
       btn_eliminar.setEnabled(false);
       //limpiar campos del padre
       rfc_padre.setText("");
       nombre_padre.setText("");
       apellidoPaterno_padre.setText("");
       apellidoMaterno_padre.setText("");
       regimen_padre.setText("");
       uso_cfdi.removeAllItems();
       uso_cfdi.addItem("<seleccionar>");
       uso_cfdi.setEnabled(false);
       domicilio_padre.setText("");
       correo_padre.setText("");
       
       //limpiar campos del servicio
       nombres_alumno.setEnabled(false);
       nombres_alumno.removeAllItems();
       apellidoPaterno_alumno.setText("");
       apellidoMaterno_alumno.setText("");
       curp_alumno.setText("");
       nivel_escolar.setText("");
       grado_escolar.setText("");
       servicio.setEnabled(false);
       servicio.setSelectedIndex(0);
       rutas.setEnabled(true);
       rutas.setVisible(false);    
       cantidad.setText("");
       unidad.setText("");
       clave.setText("");
       obj_impuestos.setText("");
       campo_precioUnitario.setText("");
       campo_importe.setText("");
       
        //habilitar para buscar a un nuevo padre
       rfc_padre.setEnabled(true);
       icon_buscarPadre.setVisible(true);
       
       //deshabilitar botones para generar y visualizar factura
       btn_previsualizar.setEnabled(false);
       btn_enviarCorreo.setEnabled(false);
    }
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Object[] opciones = {"Aceptar", "Cancelar"};
        // Si existe información que no ha sido guardada
        // Mostrar diálogo que pregunta si desea confirmar la salida
        int opcionSeleccionada = JOptionPane.showOptionDialog(
                null,
                "¿Desea salir de la apliación?",
                "Confirmación de salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                opciones,
                opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"

        // Manejar las opciones seleccionadas
        if (opcionSeleccionada == JOptionPane.YES_OPTION) {
            //Creacion de consulta para el historial de sesione
            LocalTime horaFinSesion = LocalTime.now();//Hora de salida
            LocalDate fecha_salida = LocalDate.now();//Fecha de salida
            String sql = "INSERT INTO historial_sesiones"
                    + "(usuario, fecha_ingreso, hora_inicioSesion, fecha_salida, hora_FinSesion)"
                    + "values (?,?,?,?,?)";
            try {
                PreparedStatement ps = cx.conectar().prepareStatement(sql);//Creacion de la consulta
                ps.setString(1, usuario);
                ps.setObject(2, fechaInicioSesion);
                ps.setObject(3, horaInicioSesion);
                ps.setObject(4, fecha_salida);
                ps.setObject(5, horaFinSesion);
                // Paso 4: Ejecutar la consulta
                int rowsInserted = ps.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Historial guardado");
                }
            } catch (SQLException ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            // Cerrar la aplicación
            this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        } else {
            // Evitar que la ventana se cierre
            this.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_formWindowClosing

    
    private void icon_regresarlbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_icon_regresarlbMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt)) {
            Object[] opciones = {"Aceptar", "Cancelar"};
            // Si existe información que no ha sido guardada
            // Mostrar diálogo que pregunta si desea confirmar la salida
            int opcionSeleccionada = JOptionPane.showOptionDialog(
                    null,
                    "¿Regresar a la pantalla de inicio?",
                    "Confirmación de volver",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    opciones,
                    opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"

            // Manejar las opciones seleccionadas
            if (opcionSeleccionada == JOptionPane.YES_OPTION) {
                //Regresa al menu principal
                MenuPrincipal ventana = new MenuPrincipal();
                ventana.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
                ventana.setVisible(true);
                this.dispose();
            } else {
                // Evitar que la ventana se cierre
                return;
            }
        }
    }//GEN-LAST:event_icon_regresarlbMouseClicked

    private void btn_alumnosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_alumnosMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt)){
            if(menu_alumnos.isVisible()){//si es visible el menu
                //Oculta el menu, cambia el color del btn y cambia el icono
                menu_alumnos.setVisible(false);
                btn_alumnos.setBackground(colorbtnNoSeleccionado);
                icon_item.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
            }else{
                //Muestra el menu, cambia el color del btn y cambia el icono
                menu_alumnos.setVisible(true);
                icon_item.setIcon(new ImageIcon(icon_seleccionado.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
                btn_alumnos.setBackground(colorbtnSeleccionado);
                //Oculta los demas menus y cambia el color e iconos
                //Ocultar menu padres
                menu_padres.setVisible(false);
                menu_padres.setVisible(false);
                btn_padres.setBackground(colorbtnNoSeleccionado);
                icon_item2.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
                //Ocultar menu facturas
                menu_factura.setVisible(false);
                btn_facturas.setBackground(colorbtnNoSeleccionado);
                icon_item3.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
                //Ocultar menu estadisticas
                menu_estadisticas.setVisible(false);
                btn_estadisticas.setBackground(colorbtnNoSeleccionado);
                icon_item4.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
                //Ocultar menu emisor
                menu_emisor.setVisible(false);
                btn_emisor.setBackground(colorbtnNoSeleccionado);
                icon_item5.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
                //oculta el menu de usuario
                menu_salir.setVisible(false);
                menu_user.setBackground(colorbtnNoSeleccionado);
            }
        }
    }//GEN-LAST:event_btn_alumnosMouseClicked

    private void btn_padresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_padresMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt)){
            if(menu_padres.isVisible()){//si es visible el menu
                //Oculta el menu y cambia el fondo e icos
                menu_padres.setVisible(false);
                btn_padres.setBackground(colorbtnNoSeleccionado);
                icon_item2.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
            }else{
                //Muestra el menu y cambia el color de fondo e icono
                menu_padres.setVisible(true);
                icon_item2.setIcon(new ImageIcon(icon_seleccionado.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
                btn_padres.setBackground(colorbtnSeleccionado);
                //Oculta el resto de menus y cambia sus fonos e iconos
                //Ocultar menu alumnos
                menu_alumnos.setVisible(false);
                btn_alumnos.setBackground(colorbtnNoSeleccionado);
                icon_item.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
                //Ocultar menu facturas
                menu_factura.setVisible(false);
                btn_facturas.setBackground(colorbtnNoSeleccionado);
                icon_item3.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
                //Ocultar menu estadisticas
                menu_estadisticas.setVisible(false);
                btn_estadisticas.setBackground(colorbtnNoSeleccionado);
                icon_item4.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
                //Ocultar menu emisor
                menu_emisor.setVisible(false);
                btn_emisor.setBackground(colorbtnNoSeleccionado);
                icon_item5.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
                //oculta el menu de usuario
                menu_salir.setVisible(false);
                menu_user.setBackground(colorbtnNoSeleccionado);
            }
        }
    }//GEN-LAST:event_btn_padresMouseClicked

    private void btn_facturasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_facturasMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt)){
            if(menu_factura.isVisible()){//si es visible el menu
                //Oculta el menu y cambia el fondo e icos
                menu_factura.setVisible(false);
                btn_facturas.setBackground(colorbtnNoSeleccionado);
                icon_item3.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
            }else{
                //Muestra el menu y cambia el color de fondo e icono
                menu_factura.setVisible(true);
                icon_item3.setIcon(new ImageIcon(icon_seleccionado.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
                btn_facturas.setBackground(colorbtnSeleccionado);
                //Oculta el resto de menus y cambia sus fonos e iconos
                //Ocultar menu padres
                menu_padres.setVisible(false);
                btn_padres.setBackground(colorbtnNoSeleccionado);
                icon_item2.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
                //Ocultar menu alumnos
                menu_alumnos.setVisible(false);
                btn_alumnos.setBackground(colorbtnNoSeleccionado);
                icon_item.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
                //Ocultar menu estadisticas
                menu_estadisticas.setVisible(false);
                btn_estadisticas.setBackground(colorbtnNoSeleccionado);
                icon_item4.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
                //Ocultar menu emisor
                menu_emisor.setVisible(false);
                btn_emisor.setBackground(colorbtnNoSeleccionado);
                icon_item5.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
                //oculta el menu de usuario
                menu_salir.setVisible(false);
                menu_user.setBackground(colorbtnNoSeleccionado);
            }
        }
    }//GEN-LAST:event_btn_facturasMouseClicked

    private void btn_estadisticasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_estadisticasMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt)){
            if(menu_estadisticas.isVisible()){//si es visible el menu
                //Oculta el menu y cambia el fondo e icos
                menu_estadisticas.setVisible(false);
                btn_estadisticas.setBackground(colorbtnNoSeleccionado);
                icon_item4.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
            }else{
                //Muestra el menu y cambia el color de fondo e icono
                menu_estadisticas.setVisible(true);
                icon_item4.setIcon(new ImageIcon(icon_seleccionado.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
                btn_estadisticas.setBackground(colorbtnSeleccionado);
                //Oculta el resto de menus y cambia sus fonos e iconos
                //Ocultar menu padres
                menu_padres.setVisible(false);
                btn_padres.setBackground(colorbtnNoSeleccionado);
                icon_item2.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
                //Ocultar menu alumnos
                menu_alumnos.setVisible(false);
                btn_alumnos.setBackground(colorbtnNoSeleccionado);
                icon_item.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
                //Ocultar menu facturas
                menu_factura.setVisible(false);
                btn_facturas.setBackground(colorbtnNoSeleccionado);
                icon_item3.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
                //Ocultar menu emisor
                menu_emisor.setVisible(false);
                btn_emisor.setBackground(colorbtnNoSeleccionado);
                icon_item5.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
                //oculta el menu de usuario
                menu_salir.setVisible(false);
                menu_user.setBackground(colorbtnNoSeleccionado);
            }
        }
    }//GEN-LAST:event_btn_estadisticasMouseClicked

    private void btn_emisorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_emisorMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt)){
            if(menu_emisor.isVisible()){//si es visible el menu
                //Oculta el menu y cambia el fondo e icos
                menu_emisor.setVisible(false);
                btn_emisor.setBackground(colorbtnNoSeleccionado);
                icon_item5.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
            }else{
                //Muestra el menu y cambia el color de fondo e icono
                menu_emisor.setVisible(true);
                icon_item5.setIcon(new ImageIcon(icon_seleccionado.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
                btn_emisor.setBackground(colorbtnSeleccionado);
                //Oculta el resto de menus y cambia sus fonos e iconos
                //Ocultar menu padres
                menu_padres.setVisible(false);
                btn_padres.setBackground(colorbtnNoSeleccionado);
                icon_item2.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
                //Ocultar menu alumnos
                menu_alumnos.setVisible(false);
                btn_alumnos.setBackground(colorbtnNoSeleccionado);
                icon_item.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
                //Ocultar menu facturas
                menu_factura.setVisible(false);
                btn_facturas.setBackground(colorbtnNoSeleccionado);
                icon_item4.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
                //Oculta menu estadisticas
                menu_estadisticas.setVisible(false);
                btn_estadisticas.setBackground(colorbtnNoSeleccionado);
                icon_item4.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
                //oculta el menu de usuario
                menu_salir.setVisible(false);
                menu_user.setBackground(colorbtnNoSeleccionado);
            }
        }
    }//GEN-LAST:event_btn_emisorMouseClicked

    private void menu_userMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_userMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt)) {
            if(menu_salir.isVisible()){//si es visible el menu de salir
                //lo oculta y cambia el color del btn
                menu_salir.setVisible(false);
                menu_user.setBackground(colorbtnNoSeleccionado);
            } else {
                //Lo muestra y cambia el color del btn
                menu_salir.setVisible(true);
                menu_user.setBackground(colorbtnSeleccionado);
                //Oculta los demas menus
                menu_padres.setVisible(false);
                menu_alumnos.setVisible(false);
                menu_factura.setVisible(false);
                menu_estadisticas.setVisible(false);
                menu_emisor.setVisible(false);
                menu_emisor.setVisible(false);
                //Botones en modo no seleccionados
                btn_padres.setBackground(colorbtnNoSeleccionado);
                icon_item.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
                btn_alumnos.setBackground(colorbtnNoSeleccionado);
                icon_item2.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
                btn_facturas.setBackground(colorbtnNoSeleccionado);
                icon_item3.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
                btn_estadisticas.setBackground(colorbtnNoSeleccionado);
                icon_item4.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
                btn_emisor.setBackground(colorbtnNoSeleccionado);
                icon_item5.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
            }
        }
    }//GEN-LAST:event_menu_userMouseClicked

    private void btn_historialSesionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_historialSesionesMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt)) {

            Object[] opciones = {"Aceptar", "Cancelar"};
            // Si existe información que no ha sido guardada
            // Mostrar diálogo que pregunta si desea confirmar la salida
            int opcionSeleccionada = JOptionPane.showOptionDialog(
                    null,
                    "No se guardarán los cambios, ¿Desea salir?",
                    "Confirmación de salida",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    opciones,
                    opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"

            // Manejar las opciones seleccionadas
            if (opcionSeleccionada == JOptionPane.YES_OPTION) {
                EliminarEmisor ventana = new EliminarEmisor();
                ventana.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
                ventana.setVisible(true);
                this.dispose();
            } else {
                // Evitar que la ventana se cierre
                return;
            }
        }
    }//GEN-LAST:event_btn_historialSesionesMouseClicked

    private void btn_salirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salirMouseClicked
        Object[] opciones = {"Aceptar", "Cancelar"};
        if (SwingUtilities.isLeftMouseButton(evt)) {//click izquierdo
            //dialogo que pregunta si desea confirmar salir
            int opcionSeleccionada = JOptionPane.showOptionDialog(null,
                "¿Cerrar sesión y salir?", "Confirmación de salida", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
                null, opciones, opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"
            // Manejar las opciones seleccionadas
            if (opcionSeleccionada == JOptionPane.YES_OPTION) {

                //Creacion de consulta para el historial de sesione
                LocalTime horaFinSesion = LocalTime.now();//Hora de salida
                LocalDate fecha_salida =    LocalDate.now();//Fecha de salida
                String sql = "INSERT INTO historial_sesiones"
                + "(usuario, fecha_ingreso, hora_inicioSesion, fecha_salida, hora_FinSesion)"
                + "values (?,?,?,?,?)";
                try {
                    PreparedStatement ps = cx.conectar().prepareStatement(sql);//Creacion de la consulta
                    ps.setString(1, usuario);
                    ps.setObject(2, fechaInicioSesion);
                    ps.setObject(3, horaInicioSesion);
                    ps.setObject(4, fecha_salida);
                    ps.setObject(5, horaFinSesion);
                    // Paso 4: Ejecutar la consulta
                    int rowsInserted = ps.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Historial guardado");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.exit(0); // Salir del programa
            } else {
                return;
            }
        }
    }//GEN-LAST:event_btn_salirMouseClicked

    private void btn_cerrarSesionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_cerrarSesionMouseClicked
        Object[] opciones = {"Aceptar", "Cancelar"};
        if (SwingUtilities.isLeftMouseButton(evt)) {//click izquierdo
            //dialogo que pregunta si desea confirmar salir
            int opcionSeleccionada = JOptionPane.showOptionDialog(null,
                "¿Cerrar sesión?", "Confirmación de cerrar sesión", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
                null, opciones, opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"
            // Manejar las opciones seleccionadas
            if (opcionSeleccionada == JOptionPane.YES_OPTION) {
                //Creacion de consulta para el historial de sesione
                LocalTime horaFinSesion = LocalTime.now();//Hora de salida
                LocalDate fecha_salida =    LocalDate.now();//Fecha de salida
                String sql = "INSERT INTO historial_sesiones"
                + "(usuario, fecha_ingreso, hora_inicioSesion, fecha_salida, hora_FinSesion)"
                + "values (?,?,?,?,?)";
                try {
                    PreparedStatement ps = cx.conectar().prepareStatement(sql);//Creacion de la consulta
                    ps.setString(1, usuario);
                    ps.setObject(2, fechaInicioSesion);
                    ps.setObject(3, horaInicioSesion);
                    ps.setObject(4, fecha_salida);
                    ps.setObject(5, horaFinSesion);
                    // Paso 4: Ejecutar la consulta
                    int rowsInserted = ps.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Historial guardado");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
                //cerrar ventana y regresar a login
                login_window ventanaLogin = new login_window();
                ventanaLogin.setVisible(true);
                this.dispose();
            } else {
                return;
            }
        }
    }//GEN-LAST:event_btn_cerrarSesionMouseClicked

    private void cerrar_iconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cerrar_iconMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt)){//cerrar el menu de salir
            menu_salir.setVisible(false);
            menu_user.setBackground(colorbtnNoSeleccionado);
        }
    }//GEN-LAST:event_cerrar_iconMouseClicked

    private void txt_editarEmisorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_editarEmisorMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt)) {

            Object[] opciones = {"Aceptar", "Cancelar"};
            // Si existe información que no ha sido guardada
            // Mostrar diálogo que pregunta si desea confirmar la salida
            int opcionSeleccionada = JOptionPane.showOptionDialog(
                null,
                "No se guardarán los cambios, ¿Desea salir?",
                "Confirmación de salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                opciones,
                opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"

            // Manejar las opciones seleccionadas
            if (opcionSeleccionada == JOptionPane.YES_OPTION) {
                ModificarEmisor ventana = new ModificarEmisor();
                ventana.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
                ventana.setVisible(true);
                this.dispose();
                this.dispose();
            } else {
                // Evitar que la ventana se cierre
                return;
            }
        }
    }//GEN-LAST:event_txt_editarEmisorMouseClicked

    private void txt_altaEmisorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_altaEmisorMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt)) {
            Object[] opciones = {"Aceptar", "Cancelar"};
            // Si existe información que no ha sido guardada
            // Mostrar diálogo que pregunta si desea confirmar la salida
            int opcionSeleccionada = JOptionPane.showOptionDialog(
                null,
                "No se guardarán los cambios, ¿Desea salir?",
                "Confirmación de salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                opciones,
                opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"

            // Manejar las opciones seleccionadas
            if (opcionSeleccionada == JOptionPane.YES_OPTION) {
                AltaEmisor ventana = new AltaEmisor();
                ventana.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
                ventana.setVisible(true);
                this.dispose();
            } else {
                // Evitar que la ventana se cierre
                return;
            }
        }
    }//GEN-LAST:event_txt_altaEmisorMouseClicked

    private void txt_eliminarEmisorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_eliminarEmisorMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt)) {
            Object[] opciones = {"Aceptar", "Cancelar"};
            // Si existe información que no ha sido guardada
            // Mostrar diálogo que pregunta si desea confirmar la salida
            int opcionSeleccionada = JOptionPane.showOptionDialog(
                null,
                "No se guardarán los cambios, ¿Desea salir?",
                "Confirmación de salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                opciones,
                opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"

            // Manejar las opciones seleccionadas
            if (opcionSeleccionada == JOptionPane.YES_OPTION) {
                EliminarEmisor ventana = new EliminarEmisor();
                ventana.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
                ventana.setVisible(true);
                this.dispose();
            } else {
                // Evitar que la ventana se cierre
                return;
            }
        }
    }//GEN-LAST:event_txt_eliminarEmisorMouseClicked

    private void txt_ConsultarEmisorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_ConsultarEmisorMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt)) {//click izquierdo

            Object[] opciones = {"Aceptar", "Cancelar"};
            // Si existe información que no ha sido guardada
            // Mostrar diálogo que pregunta si desea confirmar la salida
            int opcionSeleccionada = JOptionPane.showOptionDialog(
                null,
                "No se guardarán los cambios, ¿Desea salir?",
                "Confirmación de salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                opciones,
                opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"

            // Manejar las opciones seleccionadas
            if (opcionSeleccionada == JOptionPane.YES_OPTION) {
                ConsultarEmisor ventana = new ConsultarEmisor();
                ventana.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
                ventana.setVisible(true);
                this.dispose();
            } else {
                // Evitar que la ventana se cierre
                return;
            }
        }
    }//GEN-LAST:event_txt_ConsultarEmisorMouseClicked

    private void txt_altaPadresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_altaPadresMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt)) {//click izquierdo
            Object[] opciones = {"Aceptar", "Cancelar"};
            // Si existe información que no ha sido guardada
            // Mostrar diálogo que pregunta si desea confirmar la salida
            int opcionSeleccionada = JOptionPane.showOptionDialog(
                    null,
                    "No se guardarán los datos ingresados, ¿Desea salir?",
                    "Confirmación de salida",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    opciones,
                    opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"

            // Manejar las opciones seleccionadas
            if (opcionSeleccionada == JOptionPane.YES_OPTION) {
               AltaPadres ventena = new AltaPadres();
                ventena.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
                ventena.setVisible(true);
                this.dispose();
            } else {
                // Evitar que la ventana se cierre
                return;
            }
        }
    }//GEN-LAST:event_txt_altaPadresMouseClicked

    private void txt_consultarPadresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_consultarPadresMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt)){//click izquierdo
            Object[] opciones = {"Aceptar", "Cancelar"};
            // Si existe información que no ha sido guardada
            // Mostrar diálogo que pregunta si desea confirmar la salida
            int opcionSeleccionada = JOptionPane.showOptionDialog(
                null,
                "No se guardarán los datos ingresados, ¿Desea salir?",
                "Confirmación de salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                opciones,
                opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"

            // Manejar las opciones seleccionadas
            if (opcionSeleccionada == JOptionPane.YES_OPTION) {
                ConsultarPadre ventena = new ConsultarPadre();
                ventena.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
                ventena.setVisible(true);
                this.dispose();
            } else {
                // Evitar que la ventana se cierre
                return;
            }
        }
    }//GEN-LAST:event_txt_consultarPadresMouseClicked

    private void txt_modificarPadresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_modificarPadresMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt)) {

            Object[] opciones = {"Aceptar", "Cancelar"};
            // Si existe información que no ha sido guardada
            // Mostrar diálogo que pregunta si desea confirmar la salida
            int opcionSeleccionada = JOptionPane.showOptionDialog(
                null,
                "No se guardarán los datos ingresados, ¿Desea salir?",
                "Confirmación de salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                opciones,
                opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"

            // Manejar las opciones seleccionadas
            if (opcionSeleccionada == JOptionPane.YES_OPTION) {
                ModificarPadre ventana = new ModificarPadre();
                ventana.setDatosSesion(usuario, fechaInicioSesion, horaInicioSesion);
                ventana.setVisible(true);
                this.dispose();
                this.dispose();
            } else {
                // Evitar que la ventana se cierre
                return;
            }
        }
    }//GEN-LAST:event_txt_modificarPadresMouseClicked

    private void txt_eliminarPadresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_eliminarPadresMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt)) {

            Object[] opciones = {"Aceptar", "Cancelar"};
            // Si existe información que no ha sido guardada
            // Mostrar diálogo que pregunta si desea confirmar la salida
            int opcionSeleccionada = JOptionPane.showOptionDialog(
                null,
                "No se guardarán los datos ingresados, ¿Desea salir?",
                "Confirmación de salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                opciones,
                opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"

            // Manejar las opciones seleccionadas
            if (opcionSeleccionada == JOptionPane.YES_OPTION) {

                EliminarPadre ventana = new EliminarPadre();
                ventana.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
                ventana.setVisible(true);
                this.dispose();
            } else {
                // Evitar que la ventana se cierre
                return;
            }
        }
    }//GEN-LAST:event_txt_eliminarPadresMouseClicked

    private void txt_altaAlumnosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_altaAlumnosMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt)) {
            Object[] opciones = {"Aceptar", "Cancelar"};
            // Si existe información que no ha sido guardada
            // Mostrar diálogo que pregunta si desea confirmar la salida
            int opcionSeleccionada = JOptionPane.showOptionDialog(
                null,
                "No se guardarán los cambios, ¿Desea salir?",
                "Confirmación de salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                opciones,
                opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"

            // Manejar las opciones seleccionadas
            if (opcionSeleccionada == JOptionPane.YES_OPTION) {
                AltaAlumnos ventana = new AltaAlumnos();
                ventana.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
                ventana.setVisible(true);
                this.dispose();
            } else {
                // Evitar que la ventana se cierre
                return;
            }
        }
    }//GEN-LAST:event_txt_altaAlumnosMouseClicked

    private void txt_consultarAlmnosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_consultarAlmnosMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt)){//click izquierdo
            Object[] opciones = {"Aceptar", "Cancelar"};
            // Si existe información que no ha sido guardada
            // Mostrar diálogo que pregunta si desea confirmar la salida
            int opcionSeleccionada = JOptionPane.showOptionDialog(
                null,
                "No se guardarán los cambios, ¿Desea salir?",
                "Confirmación de salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                opciones,
                opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"

            // Manejar las opciones seleccionadas
            if (opcionSeleccionada == JOptionPane.YES_OPTION) {
                ConsultarAlumnos ventana = new ConsultarAlumnos();
                ventana.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
                ventana.setVisible(true);
                this.dispose();
                this.dispose();
            } else {
                // Evitar que la ventana se cierre
                return;
            }
        }
    }//GEN-LAST:event_txt_consultarAlmnosMouseClicked

    private void txt_modificarAlumnosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_modificarAlumnosMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt)){
            Object[] opciones = {"Aceptar", "Cancelar"};
            // Si existe información que no ha sido guardada
            // Mostrar diálogo que pregunta si desea confirmar la salida
            int opcionSeleccionada = JOptionPane.showOptionDialog(
                null,
                "No se guardarán los datos ingresados, ¿Desea salir?",
                "Confirmación de salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                opciones,
                opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"

            // Manejar las opciones seleccionadas
            if (opcionSeleccionada == JOptionPane.YES_OPTION) {
                ModificarAlumno ventana = new ModificarAlumno();
                ventana.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
                ventana.setVisible(true);
                this.dispose();
                this.dispose();
            } else {
                // Evitar que la ventana se cierre
                return;
            }
        }
    }//GEN-LAST:event_txt_modificarAlumnosMouseClicked

    private void txt_eliminarAlumnoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_eliminarAlumnoMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt)){

            Object[] opciones = {"Aceptar", "Cancelar"};
            // Si existe información que no ha sido guardada
            // Mostrar diálogo que pregunta si desea confirmar la salida
            int opcionSeleccionada = JOptionPane.showOptionDialog(
                null,
                "No se guardarán los datos ingresados, ¿Desea salir?",
                "Confirmación de salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                opciones,
                opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"

            // Manejar las opciones seleccionadas
            if (opcionSeleccionada == JOptionPane.YES_OPTION) {
                EliminarAlumno ventena = new EliminarAlumno();
                ventena.setDatos(usuario, fechaInicioSesion, horaInicioSesion);;
                ventena.setVisible(true);
                this.dispose();
            } else {
                // Evitar que la ventana se cierre
                return;
            }
        }
    }//GEN-LAST:event_txt_eliminarAlumnoMouseClicked

    private void icon_buscarPadreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_icon_buscarPadreMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt)) {
            Validacion valida = new Validacion();
            if (rfc_padre.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor ingrese un RFC para consultar", "RFC no ingresado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (rfc_padre.getText().length() < 13) {
                JOptionPane.showMessageDialog(null, "El RFC debe ser de 13 digitos", "RFC no valido", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!valida.rfc_valido(rfc_padre.getText().toUpperCase())) {
                JOptionPane.showMessageDialog(null, "Por favor ingrese un RFC valido para consultar", "RFC no valido", JOptionPane.WARNING_MESSAGE);
                return;
            }
            obtenerDatosPadre(rfc_padre.getText().toUpperCase());
            rfc_padre.setText(rfc_padre.getText().toUpperCase());
        }
    }//GEN-LAST:event_icon_buscarPadreMouseClicked

    private void rfc_padreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rfc_padreActionPerformed
        Validacion valida = new Validacion();
        if (rfc_padre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor ingrese un RFC para consultar", "RFC no ingresado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (rfc_padre.getText().length() < 13) {
            JOptionPane.showMessageDialog(null, "El RFC debe ser de 13 digitos", "RFC no valido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!valida.rfc_valido(rfc_padre.getText().toUpperCase())) {
            JOptionPane.showMessageDialog(null, "Por favor ingrese un RFC valido para consultar", "RFC no valido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        obtenerDatosPadre(rfc_padre.getText().toUpperCase());
        rfc_padre.setText(rfc_padre.getText().toUpperCase());
    }//GEN-LAST:event_rfc_padreActionPerformed

    private void rfc_padreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rfc_padreKeyTyped
        if (rfc_padre.getText().length() >= 13 && evt.getKeyChar() != KeyEvent.VK_ENTER) {
            JOptionPane.showMessageDialog(null, "El RFC debe ser de 13 digitos", "Advertencia", JOptionPane.WARNING_MESSAGE);
            evt.consume();
        }
    }//GEN-LAST:event_rfc_padreKeyTyped

    private void btn_enviarCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_enviarCorreoActionPerformed
        try {
            sellarFactura();
        } catch (DocumentException ex) {
            Logger.getLogger(GenerarFactura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GenerarFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_enviarCorreoActionPerformed

    private void nombres_alumnoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_nombres_alumnoItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) { //si selecciona un item verificar    
            String curp[] = nombres_alumno.getSelectedItem().toString().split("-");
            obtenerDatosAlumno(curp[1].trim());

            //cargar el dato de la colegiatura
            if (servicio.getSelectedItem().toString().equals("Pago de colegiatura")) {
                obtenerCostosColegiatura();
            }
        }
    }//GEN-LAST:event_nombres_alumnoItemStateChanged

    private void btn_previsualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_previsualizarActionPerformed
        //Funcion para previsualizar la factura
      
        //Previsualizar el modelo
        ModeloFactura facturaPrevia = new ModeloFactura(this, true, emisor,receptor, alumno,factura);
        facturaPrevia.setVisible(true);
    }//GEN-LAST:event_btn_previsualizarActionPerformed

    private void servicioItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_servicioItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) { //si selecciona un item verificar    
            //Obtener el item seleccionado
            if (servicio.getSelectedIndex() == 0) {
                //Ocultar las rutas
                titulo_rutas.setVisible(false);
                rutas.setVisible(false);
                obtenerCostosColegiatura();
                return;
            }
            if(servicio.getSelectedIndex()==1){
                //Mostrar las rutas
                titulo_rutas.setVisible(true);
                rutas.setVisible(true);
                obtenerCostosTransporte();
                return;
            }
        }
    }//GEN-LAST:event_servicioItemStateChanged

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed
        if(descuento > importe){
           JOptionPane.showMessageDialog(null, "El descuento no puede ser mayor al precio del servicio", "Error", JOptionPane.WARNING_MESSAGE); 
           return;
        }
       
        //obtener el uso de CFDI del receptor
        receptor.setUso_CFDI(uso_cfdi.getSelectedItem().toString());
        
        //Crear el objeto para la factura
        factura = new Factura(formaPago.getSelectedItem().toString(), metodoPago.getText().toString(), tipo_factura.getText(),
        cantidad.getText(), unidad.getText(), clave.getText(), descripcion, obj_impuestos.getText(), precio, importe,impuestos,descuento);
        generarIdFactura();//se genera el ID de la factura
       
        //Bloquear ingresar datos padre
        rfc_padre.setEnabled(false);
        icon_buscarPadre.setVisible(false);
        uso_cfdi.setEnabled(false);
        
        //bloquear ingresar datos servicios
        nombres_alumno.setEnabled(false);
        //campoDescuento.setEnabled(false);
        servicio.setEnabled(false);
        rutas.setEnabled(false);
        
        //Llenar la tabla de totales
        subTotal.setText("$"+factura.getSubtotal());
        descuento_totales.setText("$"+factura.getDescuento());
        impuestos_total.setText("$"+factura.getImpuesto());
        campo_total.setText("$"+factura.getTotal());

        //habilitar los botones de visualizar y enviar por correo
        btn_previsualizar.setEnabled(true);
        btn_enviarCorreo.setEnabled(true);
        
        //bloquear boton de agregar
        btn_agregar.setEnabled(false);
        //Habilitar boton eliminar
        btn_eliminar.setEnabled(true);
    }//GEN-LAST:event_btn_agregarActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        //habilitar ingresar datos padre
        rfc_padre.setEnabled(true);
        icon_buscarPadre.setVisible(true);
        uso_cfdi.setEnabled(true);
        
        //habilitar ingresar datos servicios
        nombres_alumno.setEnabled(true);
        //campoDescuento.setEnabled(true);
        servicio.setEnabled(true);
        rutas.setEnabled(true);
        
        //deshabilitar los botones de visualizar y enviar por correo
        btn_previsualizar.setEnabled(false);
        btn_enviarCorreo.setEnabled(false);  
        
        //habilitar boton de agregar
        btn_agregar.setEnabled(true);
        //deshabilitar boton de eliminar
        btn_eliminar.setEnabled(false);
        
        //limpiar los campos de totales
        limpiarTotales();
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void rutasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rutasItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) { //si selecciona un item verificar  
            obtenerCostosTransporte();
        }
    }//GEN-LAST:event_rutasItemStateChanged

    private void uso_cfdiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_uso_cfdiItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) { //si selecciona un item verificar    
            if (uso_cfdi.getSelectedItem().toString().equalsIgnoreCase("D10 Pagos por servicios educativos")) {
                servicio.setSelectedIndex(0);
                servicio.setEnabled(false);
                return;
            }
            if (uso_cfdi.getSelectedItem().toString().equalsIgnoreCase("D08 Transporte escolar")) {
                servicio.setSelectedIndex(1);
                servicio.setEnabled(false);
                return;
            }
            servicio.setEnabled(true);
        }
    }//GEN-LAST:event_uso_cfdiItemStateChanged

    private void rfc_padreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rfc_padreFocusLost
        rfc_padre.setText(rfc_padre.getText().toUpperCase());
    }//GEN-LAST:event_rfc_padreFocusLost

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
            java.util.logging.Logger.getLogger(GenerarFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GenerarFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GenerarFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GenerarFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GenerarFactura().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fecha;
    private javax.swing.JTextField apellidoMaterno_alumno;
    private javax.swing.JTextField apellidoMaterno_padre;
    private javax.swing.JTextField apellidoPaterno_alumno;
    private javax.swing.JTextField apellidoPaterno_padre;
    private javax.swing.JPanel barra_nav;
    private javax.swing.JButton btn_agregar;
    private javax.swing.JPanel btn_alumnos;
    private javax.swing.JPanel btn_cerrarSesion;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JPanel btn_emisor;
    private javax.swing.JButton btn_enviarCorreo;
    private javax.swing.JPanel btn_estadisticas;
    private javax.swing.JPanel btn_facturas;
    private javax.swing.JPanel btn_historialSesiones;
    private javax.swing.JPanel btn_padres;
    private javax.swing.JButton btn_previsualizar;
    private javax.swing.JPanel btn_salir;
    private javax.swing.JTextField campo_importe;
    private javax.swing.JTextField campo_precioUnitario;
    private javax.swing.JTextField campo_total;
    private javax.swing.JTextField cantidad;
    private javax.swing.JLabel cerrar_icon;
    private javax.swing.JTextField clave;
    private javax.swing.JScrollPane contenedor;
    private javax.swing.JPanel contenedor_menu;
    private javax.swing.JTextField correo_padre;
    private javax.swing.JLabel cp_expedicion1;
    private javax.swing.JTextField curp_alumno;
    private javax.swing.JTextField descuento_totales;
    private javax.swing.JTextField domicilio_padre;
    private javax.swing.JPanel fondo;
    private javax.swing.JComboBox<String> formaPago;
    private javax.swing.JTextField grado_escolar;
    private javax.swing.JLabel historial_lb;
    private javax.swing.JLabel hora_lb;
    private javax.swing.JLabel iconFac_lb;
    private javax.swing.JLabel iconFac_lb1;
    private javax.swing.JLabel iconFac_lb2;
    private javax.swing.JLabel iconFac_lb3;
    private javax.swing.JLabel iconFac_lb4;
    private javax.swing.JLabel iconFac_lb5;
    private javax.swing.JLabel iconFac_lb6;
    private javax.swing.JLabel icon_buscarPadre;
    private javax.swing.JLabel icon_item;
    private javax.swing.JLabel icon_item2;
    private javax.swing.JLabel icon_item3;
    private javax.swing.JLabel icon_item4;
    private javax.swing.JLabel icon_item5;
    private javax.swing.JLabel icon_regresarlb;
    private javax.swing.JLabel icon_salir;
    private javax.swing.JTextField impuestos_total;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel lb_comprobante;
    private javax.swing.JLabel lb_comprobante1;
    private javax.swing.JLabel lb_comprobante2;
    private javax.swing.JLabel lb_comprobante3;
    private javax.swing.JPanel menu_alumnos;
    private javax.swing.JPanel menu_emisor;
    private javax.swing.JPanel menu_estadisticas;
    private javax.swing.JPanel menu_factura;
    private javax.swing.JPanel menu_padres;
    private javax.swing.JPanel menu_salir;
    private javax.swing.JPanel menu_user;
    private javax.swing.JLabel metodoPago;
    private javax.swing.JLabel moneda;
    private javax.swing.JTextField nivel_escolar;
    private javax.swing.JTextField nombre_padre;
    private javax.swing.JPanel nombre_user;
    private javax.swing.JComboBox<String> nombres_alumno;
    private javax.swing.JTextField obj_impuestos;
    private paneles.PanelRound panelRound1;
    private paneles.PanelRound panelRound2;
    private paneles.PanelRound panelRound3;
    private paneles.PanelRound panelRound4;
    private paneles.PanelRound panelRound5;
    private paneles.PanelRound panelRound6;
    private paneles.PanelRound panelRound7;
    private paneles.PanelRound panelRound8;
    private javax.swing.JLabel regimen_emisor;
    private javax.swing.JTextField regimen_padre;
    private javax.swing.JLabel rfc_emisor;
    private javax.swing.JTextField rfc_padre;
    private javax.swing.JComboBox<String> rutas;
    private javax.swing.JComboBox<String> servicio;
    private javax.swing.JTextField subTotal;
    private javax.swing.JLabel text_salir;
    private javax.swing.JLabel tipo_factura;
    private javax.swing.JTextField tituloDescuento;
    private javax.swing.JTextField tituloImpuestos;
    private javax.swing.JLabel tituloMoneda1;
    private javax.swing.JTextField tituloSubtotal;
    private javax.swing.JTextField tituloTotal;
    private javax.swing.JLabel titulo_correoPadre;
    private javax.swing.JLabel titulo_cpEmision1;
    private javax.swing.JLabel titulo_formaPago;
    private javax.swing.JLabel titulo_metodoPago;
    private javax.swing.JLabel titulo_regimenEmisor1;
    private javax.swing.JLabel titulo_regimenEmisor2;
    private javax.swing.JLabel titulo_rutas;
    private javax.swing.JLabel titulo_tipoFactura1;
    private javax.swing.JLabel txt_ConsultarEmisor;
    private javax.swing.JLabel txt_altaAlumnos;
    private javax.swing.JLabel txt_altaEmisor;
    private javax.swing.JLabel txt_altaPadres;
    private javax.swing.JLabel txt_alumnos;
    private javax.swing.JLabel txt_cerrarSesion;
    private javax.swing.JLabel txt_cerrarSesion1;
    private javax.swing.JLabel txt_consultarAlmnos;
    private javax.swing.JLabel txt_consultarAlmnos1;
    private javax.swing.JLabel txt_consultarPadres;
    private javax.swing.JLabel txt_editarEmisor;
    private javax.swing.JLabel txt_eliminarAlumno;
    private javax.swing.JLabel txt_eliminarEmisor;
    private javax.swing.JLabel txt_eliminarPadres;
    private javax.swing.JLabel txt_emisor;
    private javax.swing.JLabel txt_estadisticas;
    private javax.swing.JLabel txt_factura;
    private javax.swing.JLabel txt_facturasGeneradas;
    private javax.swing.JLabel txt_generarFcatura;
    private javax.swing.JLabel txt_ingresos;
    private javax.swing.JLabel txt_modificarAlumnos;
    private javax.swing.JLabel txt_modificarPadres;
    private javax.swing.JLabel txt_nombrePadre;
    private javax.swing.JLabel txt_nombrePadre1;
    private javax.swing.JLabel txt_nombrePadre2;
    private javax.swing.JLabel txt_nombrePadre3;
    private javax.swing.JLabel txt_nombrePadre4;
    private javax.swing.JLabel txt_nombreUser;
    private javax.swing.JLabel txt_padres;
    private javax.swing.JTextField unidad;
    private javax.swing.JLabel user_menuIcon;
    private javax.swing.JLabel user_menuIcon1;
    private javax.swing.JComboBox<String> uso_cfdi;
    // End of variables declaration//GEN-END:variables
}
