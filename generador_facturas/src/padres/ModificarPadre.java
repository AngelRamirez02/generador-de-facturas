/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package padres;

import alumnos.AltaAlumnos;
import alumnos.ConsultarAlumnos;
import alumnos.ConsultarAlumnosEdit;
import alumnos.EliminarAlumno;
import emisor.*;
import conexion.conexion;
import direccion.ObtenerDireccion;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import login.login_window;
import menu.MenuPrincipal;
import sesiones.HistorialSesiones;
import validacion.Validacion;

/**
 *
 * @author ar275
 */
public class ModificarPadre extends javax.swing.JFrame {

    conexion cx = new conexion();
    
    ObtenerDireccion direc;
    
    Validacion valida = new Validacion();//objeto para valdicar los datos
    
    private String usuario;//Nombre del usuario que inicia sesión
    LocalDate fechaInicioSesion;
    LocalTime horaInicioSesion;
    
    private String rfc="";//rfc que se va a modficar
    private String correo="";//correo a modificar
    
    //Colores para los botones seleccionados y no
    Color colorbtnSeleccionado = Color.decode("#A91E1F");
    Color colorbtnNoSeleccionado = Color.decode("#C94545");
    //Iconos de item para menu no selccionado
    Image icon_img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/icon_itemMenu.png"));
     //Imagen para menu selccionado
    Image icon_seleccionado = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/icon_itemSeleccionado.png"));
    Image info_img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/icon_info.png"));
    
    Image img_regresar = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/icon_regresar.png"));
    
     public ModificarPadre() {
        initComponents();
        
        info_nombre.setVisible(false);
        infoFecha_lb.setVisible(false);
        infoRFC_lb.setVisible(false);
        infocp_lb.setVisible(false);
        
        infoIcon_lb.setIcon(new ImageIcon(info_img.getScaledInstance(infoIcon_lb2.getWidth(), infoIcon_lb2.getHeight(), Image.SCALE_SMOOTH)));
        infoIcon_lb2.setIcon(new ImageIcon(info_img.getScaledInstance(infoIcon_lb2.getWidth(), infoIcon_lb2.getHeight(), Image.SCALE_SMOOTH)));
        infoIcon_lb3.setIcon(new ImageIcon(info_img.getScaledInstance(infoIcon_lb3.getWidth(), infoIcon_lb3.getHeight(), Image.SCALE_SMOOTH)));
        infoIcon_lb4.setIcon(new ImageIcon(info_img.getScaledInstance(infoIcon_lb4.getWidth(), infoIcon_lb4.getHeight(), Image.SCALE_SMOOTH)));
        
        icon_regresarlb.setIcon(new ImageIcon(img_regresar.getScaledInstance(icon_regresarlb.getWidth(), icon_regresarlb.getHeight(), Image.SCALE_SMOOTH)));
        
        //Imaganes para el menu del usuario
        Image icon_historial = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/icon_historial.png"));
        historial_lb.setIcon(new ImageIcon(icon_historial.getScaledInstance(historial_lb.getWidth(), historial_lb.getHeight(), Image.SCALE_SMOOTH)));
        Image icon_salirImg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/icon_salir.png"));
        icon_salir.setIcon(new ImageIcon(icon_salirImg.getScaledInstance(icon_salir.getWidth(), icon_salir.getHeight(), Image.SCALE_SMOOTH)));
        
        //Menus ocultos por defecto
        menu_padres.setVisible(false);
        menu_alumnos.setVisible(false);
        menu_factura.setVisible(false);
        menu_estadisticas.setVisible(false);
        menu_emisor.setVisible(false);
        //Imagen del logo de la escuela
        Image logo_img= Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/logo_escuela.png"));
        logo_lb.setIcon(new ImageIcon(logo_img.getScaledInstance(logo_lb.getWidth(), logo_lb.getHeight(), Image.SCALE_SMOOTH)));
        //Iconos para botones de menu
        icon_item.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
        icon_item2.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
        icon_item3.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
        icon_item4.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
        icon_item5.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
        contenedor_menu.setLocation(user_menuIcon.getLocation().x-650, contenedor_menu.getLocation().y);//centrar el contenedor   
        
        //icono de buscar
         Image img_buscar = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/btn_buscar3.png"));
        icon_buscar.setIcon(new ImageIcon(img_buscar.getScaledInstance(icon_buscar.getWidth(), icon_buscar.getHeight(), Image.SCALE_SMOOTH)));
        
        // Formatear la fecha en el formato "dd/MM/yyyy"
        LocalDate fechaActual = LocalDate.now();
        // Crear un formato con localización en español
        DateTimeFormatter formatoEspanol = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        // Formatear la fecha en español
        String fechaFormateada = fechaActual.format(formatoEspanol);
        Fecha.setText(fechaFormateada);//Mostar hora
        
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
        
        ocultarCampos();
        
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
        icon_regresarlb = new javax.swing.JLabel();
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
        menu_factura = new javax.swing.JPanel();
        txt_generarFcatura = new javax.swing.JLabel();
        txt_consultarAlmnos1 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        menu_estadisticas = new javax.swing.JPanel();
        txt_facturasGeneradas = new javax.swing.JLabel();
        txt_ingresos = new javax.swing.JLabel();
        jSeparator15 = new javax.swing.JSeparator();
        menu_alumnos = new javax.swing.JPanel();
        txt_altaAlumnos = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        txt_consultarAlmnos = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        txt_modificarAlumnos = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        txt_eliminarAlumno = new javax.swing.JLabel();
        menu_padres = new javax.swing.JPanel();
        txt_altaPadres = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        txt_consultarPadres = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        txt_modificarPadres = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        txt_eliminarPadres = new javax.swing.JLabel();
        menu_emisor = new javax.swing.JPanel();
        txt_editarEmisor = new javax.swing.JLabel();
        jSeparator14 = new javax.swing.JSeparator();
        txt_altaEmisor = new javax.swing.JLabel();
        jSeparator16 = new javax.swing.JSeparator();
        txt_eliminarEmisor = new javax.swing.JLabel();
        jSeparator18 = new javax.swing.JSeparator();
        txt_ConsultarEmisor = new javax.swing.JLabel();
        contenedor = new javax.swing.JPanel();
        infoIcon_lb4 = new javax.swing.JLabel();
        infocp_lb = new javax.swing.JLabel();
        infoRFC_lb = new javax.swing.JLabel();
        infoIcon_lb3 = new javax.swing.JLabel();
        infoFecha_lb = new javax.swing.JLabel();
        infoIcon_lb = new javax.swing.JLabel();
        info_nombre = new javax.swing.JLabel();
        infoIcon_lb2 = new javax.swing.JLabel();
        entrada_cp = new javax.swing.JFormattedTextField();
        entrada_regimen = new javax.swing.JComboBox<>();
        cp_lb = new javax.swing.JLabel();
        entrada_rfc = new javax.swing.JTextField();
        regimen_lb = new javax.swing.JLabel();
        rfc_lb = new javax.swing.JLabel();
        entrada_correoElectronico = new javax.swing.JTextField();
        correo_lb = new javax.swing.JLabel();
        nacimiento_lb = new javax.swing.JLabel();
        entrada_apellidoMaterno = new javax.swing.JTextField();
        materno_lb = new javax.swing.JLabel();
        paterno_lb = new javax.swing.JLabel();
        entrada_apellidoPaterno = new javax.swing.JTextField();
        entrada_nombres = new javax.swing.JTextField();
        nombres_lb = new javax.swing.JLabel();
        registrarPadre = new javax.swing.JPanel();
        entrada_fechaNacimiento = new com.toedter.calendar.JDateChooser();
        entrada_estado = new javax.swing.JComboBox<>();
        entrada_municipio = new javax.swing.JComboBox<>();
        lb_estado = new javax.swing.JLabel();
        lb_municipio = new javax.swing.JLabel();
        colonia_lb = new javax.swing.JLabel();
        entrada_colonia = new javax.swing.JComboBox<>();
        entrada_noExterior = new javax.swing.JTextField();
        exterior_lb = new javax.swing.JLabel();
        entrada_noInterior = new javax.swing.JTextField();
        interior_lb = new javax.swing.JLabel();
        btn_buscarCP = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        rfc_busqueda = new javax.swing.JTextField();
        icon_buscar = new javax.swing.JLabel();
        txt_rfc = new javax.swing.JLabel();
        lb_inicial = new javax.swing.JLabel();
        txt_datosFiscales = new javax.swing.JLabel();
        btn_guardarDatos = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();
        logo_lb = new javax.swing.JLabel();
        txt_datosPersonales = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Instituto Andrés Manuel López Obrador - Modificar Padre de familia");
        setMinimumSize(new java.awt.Dimension(1050, 735));
        setSize(new java.awt.Dimension(1050, 735));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        fondo.setBackground(new java.awt.Color(255, 255, 255));
        fondo.setMinimumSize(new java.awt.Dimension(1050, 650));
        fondo.setLayout(null);

        icon_regresarlb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon_regresarlb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_regresar.png"))); // NOI18N
        icon_regresarlb.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        icon_regresarlb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                icon_regresarlbMouseClicked(evt);
            }
        });
        fondo.add(icon_regresarlb);
        icon_regresarlb.setBounds(50, 120, 60, 60);

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
        nombre_user.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nombre_userMouseClicked(evt);
            }
        });
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
        txt_consultarAlmnos1.setText("Consultar factura");
        txt_consultarAlmnos1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_factura.add(txt_consultarAlmnos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 190, 40));
        menu_factura.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 150, 10));

        fondo.add(menu_factura);
        menu_factura.setBounds(400, 100, 200, 90);

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

        contenedor.setBackground(new java.awt.Color(255, 255, 255));
        contenedor.setLayout(null);

        infoIcon_lb4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_info.png"))); // NOI18N
        infoIcon_lb4.setText("jLabel11");
        infoIcon_lb4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        infoIcon_lb4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                infoIcon_lb4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                infoIcon_lb4MouseExited(evt);
            }
        });
        contenedor.add(infoIcon_lb4);
        infoIcon_lb4.setBounds(842, 303, 20, 20);

        infocp_lb.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
        infocp_lb.setText("Un código postal debe ser de 5 números");
        contenedor.add(infocp_lb);
        infocp_lb.setBounds(670, 330, 216, 15);

        infoRFC_lb.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
        infoRFC_lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infoRFC_lb.setText("El RFC es de 13 dígitos formado por apellidos, nombre y fecha de nacimiento");
        infoRFC_lb.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        contenedor.add(infoRFC_lb);
        infoRFC_lb.setBounds(570, 260, 430, 30);

        infoIcon_lb3.setText("jLabel11");
        infoIcon_lb3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        infoIcon_lb3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                infoIcon_lb3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                infoIcon_lb3MouseExited(evt);
            }
        });
        contenedor.add(infoIcon_lb3);
        infoIcon_lb3.setBounds(840, 242, 20, 20);

        infoFecha_lb.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
        infoFecha_lb.setText("Ej: 02 dic 2003");
        contenedor.add(infoFecha_lb);
        infoFecha_lb.setBounds(400, 460, 80, 15);

        infoIcon_lb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_info.png"))); // NOI18N
        infoIcon_lb.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        infoIcon_lb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                infoIcon_lbMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                infoIcon_lbMouseExited(evt);
            }
        });
        contenedor.add(infoIcon_lb);
        infoIcon_lb.setBounds(450, 240, 20, 20);

        info_nombre.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
        info_nombre.setText("Ingrese los nombres separados por espacio");
        contenedor.add(info_nombre);
        info_nombre.setBounds(240, 263, 232, 20);

        infoIcon_lb2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_info.png"))); // NOI18N
        infoIcon_lb2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        infoIcon_lb2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                infoIcon_lb2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                infoIcon_lb2MouseExited(evt);
            }
        });
        contenedor.add(infoIcon_lb2);
        infoIcon_lb2.setBounds(450, 427, 20, 20);

        try {
            entrada_cp.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        contenedor.add(entrada_cp);
        entrada_cp.setBounds(680, 300, 160, 27);

        entrada_regimen.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        entrada_regimen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Simplificado de Confianza. ", "612  Persona Física con Actividad Empresarial", "605  Sueldos y Salarios e Ingresos Asimilados a Salarios" }));
        entrada_regimen.setSelectedIndex(1);
        contenedor.add(entrada_regimen);
        entrada_regimen.setBounds(680, 490, 360, 30);

        cp_lb.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        cp_lb.setText("Código postal");
        contenedor.add(cp_lb);
        cp_lb.setBounds(540, 300, 120, 22);

        entrada_rfc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                entrada_rfcFocusLost(evt);
            }
        });
        entrada_rfc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                entrada_rfcKeyTyped(evt);
            }
        });
        contenedor.add(entrada_rfc);
        entrada_rfc.setBounds(680, 232, 157, 30);

        regimen_lb.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        regimen_lb.setText("Régimen Fiscal");
        contenedor.add(regimen_lb);
        regimen_lb.setBounds(540, 490, 126, 30);

        rfc_lb.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        rfc_lb.setText("RFC");
        contenedor.add(rfc_lb);
        rfc_lb.setBounds(540, 240, 35, 22);

        entrada_correoElectronico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                entrada_correoElectronicoKeyTyped(evt);
            }
        });
        contenedor.add(entrada_correoElectronico);
        entrada_correoElectronico.setBounds(260, 490, 190, 30);

        correo_lb.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        correo_lb.setText("Correo electrónico");
        contenedor.add(correo_lb);
        correo_lb.setBounds(70, 490, 170, 20);

        nacimiento_lb.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        nacimiento_lb.setText("Fecha de nacimiento");
        contenedor.add(nacimiento_lb);
        nacimiento_lb.setBounds(70, 430, 180, 20);

        entrada_apellidoMaterno.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                entrada_apellidoMaternoFocusLost(evt);
            }
        });
        entrada_apellidoMaterno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                entrada_apellidoMaternoKeyTyped(evt);
            }
        });
        contenedor.add(entrada_apellidoMaterno);
        entrada_apellidoMaterno.setBounds(260, 360, 190, 30);

        materno_lb.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        materno_lb.setText("Apellido Materno");
        contenedor.add(materno_lb);
        materno_lb.setBounds(70, 367, 150, 22);

        paterno_lb.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        paterno_lb.setText("Apellido peterno");
        contenedor.add(paterno_lb);
        paterno_lb.setBounds(70, 305, 140, 22);

        entrada_apellidoPaterno.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                entrada_apellidoPaternoFocusLost(evt);
            }
        });
        entrada_apellidoPaterno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                entrada_apellidoPaternoKeyTyped(evt);
            }
        });
        contenedor.add(entrada_apellidoPaterno);
        entrada_apellidoPaterno.setBounds(260, 300, 190, 30);

        entrada_nombres.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                entrada_nombresFocusLost(evt);
            }
        });
        entrada_nombres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                entrada_nombresKeyTyped(evt);
            }
        });
        contenedor.add(entrada_nombres);
        entrada_nombres.setBounds(258, 229, 190, 30);

        nombres_lb.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        nombres_lb.setText("Nombre (s)");
        contenedor.add(nombres_lb);
        nombres_lb.setBounds(70, 240, 110, 22);

        registrarPadre.setBackground(new java.awt.Color(255, 255, 255));
        registrarPadre.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        contenedor.add(registrarPadre);
        registrarPadre.setBounds(200, 60, 620, 0);

        entrada_fechaNacimiento.setDateFormatString("dd MMM y");
        entrada_fechaNacimiento.setMaxSelectableDate(new java.util.Date(1167548468000L));
        entrada_fechaNacimiento.setMinSelectableDate(new java.util.Date(-315593932000L));
        contenedor.add(entrada_fechaNacimiento);
        entrada_fechaNacimiento.setBounds(260, 420, 190, 30);

        entrada_estado.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        entrada_estado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<seleccionar>" }));
        contenedor.add(entrada_estado);
        entrada_estado.setBounds(610, 360, 140, 30);

        entrada_municipio.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        entrada_municipio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<seleccionar>" }));
        contenedor.add(entrada_municipio);
        entrada_municipio.setBounds(850, 360, 180, 30);

        lb_estado.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        lb_estado.setText("Estado");
        contenedor.add(lb_estado);
        lb_estado.setBounds(540, 360, 90, 30);

        lb_municipio.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        lb_municipio.setText("Municipio");
        contenedor.add(lb_municipio);
        lb_municipio.setBounds(760, 360, 90, 30);

        colonia_lb.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        colonia_lb.setText("Colonia");
        contenedor.add(colonia_lb);
        colonia_lb.setBounds(540, 420, 90, 22);

        entrada_colonia.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        entrada_colonia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<seleccionar>" }));
        contenedor.add(entrada_colonia);
        entrada_colonia.setBounds(610, 420, 220, 30);

        entrada_noExterior.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                entrada_noExteriorKeyTyped(evt);
            }
        });
        contenedor.add(entrada_noExterior);
        entrada_noExterior.setBounds(840, 420, 90, 30);

        exterior_lb.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        exterior_lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        exterior_lb.setText("N° Exterior");
        contenedor.add(exterior_lb);
        exterior_lb.setBounds(840, 450, 90, 20);

        entrada_noInterior.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                entrada_noInteriorKeyTyped(evt);
            }
        });
        contenedor.add(entrada_noInterior);
        entrada_noInterior.setBounds(940, 420, 100, 30);

        interior_lb.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        interior_lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        interior_lb.setText("N° Interior");
        contenedor.add(interior_lb);
        interior_lb.setBounds(940, 450, 100, 20);

        btn_buscarCP.setText("Buscar");
        btn_buscarCP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarCPActionPerformed(evt);
            }
        });
        contenedor.add(btn_buscarCP);
        btn_buscarCP.setBounds(880, 300, 75, 30);

        jLabel4.setFont(new java.awt.Font("Roboto Light", 1, 48)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Modificar padre de familia");
        contenedor.add(jLabel4);
        jLabel4.setBounds(-10, 40, 1050, 60);

        rfc_busqueda.setColumns(1);
        rfc_busqueda.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        rfc_busqueda.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        rfc_busqueda.setActionCommand("<Not Set>");
        rfc_busqueda.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        rfc_busqueda.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        rfc_busqueda.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                rfc_busquedaFocusLost(evt);
            }
        });
        rfc_busqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rfc_busquedaActionPerformed(evt);
            }
        });
        rfc_busqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                rfc_busquedaKeyTyped(evt);
            }
        });
        contenedor.add(rfc_busqueda);
        rfc_busqueda.setBounds(170, 150, 710, 50);

        icon_buscar.setIcon(new javax.swing.ImageIcon("C:\\Users\\ar275\\Documents\\Generador de facturas\\generador-de-facturas\\generador_facturas\\src\\img\\btn_buscar.png")); // NOI18N
        icon_buscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        icon_buscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                icon_buscarMouseClicked(evt);
            }
        });
        contenedor.add(icon_buscar);
        icon_buscar.setBounds(890, 140, 70, 70);

        txt_rfc.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txt_rfc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_rfc.setText("jLabel1");
        contenedor.add(txt_rfc);
        txt_rfc.setBounds(0, 100, 1050, 50);

        lb_inicial.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lb_inicial.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lb_inicial.setText("INGRESE EL RFC DEL PADRE A MODIFICAR");
        contenedor.add(lb_inicial);
        lb_inicial.setBounds(180, 110, 345, 30);

        txt_datosFiscales.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        txt_datosFiscales.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_datosFiscales.setText("Datos fiscales");
        contenedor.add(txt_datosFiscales);
        txt_datosFiscales.setBounds(660, 190, 170, 30);

        btn_guardarDatos.setBackground(new java.awt.Color(198, 54, 55));
        btn_guardarDatos.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btn_guardarDatos.setForeground(new java.awt.Color(255, 255, 255));
        btn_guardarDatos.setText("Guardar cambios");
        btn_guardarDatos.setBorder(null);
        btn_guardarDatos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_guardarDatos.setFocusPainted(false);
        btn_guardarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarDatosActionPerformed(evt);
            }
        });
        contenedor.add(btn_guardarDatos);
        btn_guardarDatos.setBounds(270, 570, 170, 40);

        btn_cancelar.setBackground(new java.awt.Color(153, 153, 153));
        btn_cancelar.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btn_cancelar.setForeground(new java.awt.Color(255, 255, 255));
        btn_cancelar.setText("Cancelar");
        btn_cancelar.setBorder(null);
        btn_cancelar.setBorderPainted(false);
        btn_cancelar.setFocusPainted(false);
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });
        contenedor.add(btn_cancelar);
        btn_cancelar.setBounds(640, 570, 170, 40);

        logo_lb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo_escuela.png"))); // NOI18N
        logo_lb.setText("jLabel2");
        logo_lb.setMaximumSize(new java.awt.Dimension(400, 400));
        logo_lb.setMinimumSize(new java.awt.Dimension(400, 400));
        logo_lb.setPreferredSize(new java.awt.Dimension(400, 600));
        contenedor.add(logo_lb);
        logo_lb.setBounds(330, 210, 390, 370);

        txt_datosPersonales.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        txt_datosPersonales.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_datosPersonales.setText("Datos personales");
        contenedor.add(txt_datosPersonales);
        txt_datosPersonales.setBounds(100, 190, 200, 30);

        fondo.add(contenedor);
        contenedor.setBounds(0, 0, 1050, 650);

        getContentPane().add(fondo, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mostrarDatos(String rfc) {
        try {
            //Seleccionar los datos del emisor
            String consulta = "SELECT * FROM padre_familia WHERE rfc = ?";
            PreparedStatement ps = cx.conectar().prepareStatement(consulta);
            ps.setString(1, rfc);
            ResultSet rs = ps.executeQuery();
            //Arreglo de datos
            Object[] padre = new Object[13];

            if (rs.next()) {
                mostrarCampos();
                this.rfc = rs.getString("rfc");
                System.out.println(rfc);
                txt_rfc.setText(rs.getString("rfc"));//etiqueta con el nombre del RFC
                entrada_rfc.setText(rs.getString("rfc"));
                entrada_nombres.setText(rs.getString("nombres"));
                entrada_apellidoPaterno.setText(rs.getString("apellido_paterno"));
                entrada_apellidoMaterno.setText(rs.getString("apellido_materno"));
                entrada_fechaNacimiento.setDate(rs.getDate("fecha_nacimiento"));
                entrada_cp.setText(String.valueOf(rs.getInt("domicilio_fiscal")));
                entrada_correoElectronico.setText(rs.getString("correo_electronico"));
                correo =rs.getString("correo_electronico");
                entrada_noExterior.setText(rs.getString("num_exterior"));
                entrada_noInterior.setText(rs.getString("num_interior"));

                if (rs.getString("regimen").equals("Simplificado de Confianza. ")) {
                    entrada_regimen.setSelectedIndex(0);
                } else if (rs.getString("regimen").equals("612  Persona Física con Actividad Empresarial")) {
                    entrada_regimen.setSelectedIndex(1);
                } else {
                    entrada_regimen.setSelectedIndex(2);
                }
                obtenerDireccion(rs.getString("colonia"));
            } else {
                JOptionPane.showMessageDialog(null, "El RFC que solicitó no se encuentra registrado", "RFC no encontrado", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultarPadre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mostrarCampos(){
        txt_rfc.setVisible(true);
        //Ocultar barra de busqueda
        rfc_busqueda.setVisible(false);
        icon_buscar.setVisible(false);
        lb_inicial.setVisible(false);
        logo_lb.setVisible(false);
        //etiquetas de campos
        txt_datosPersonales.setVisible(true);
        txt_datosFiscales.setVisible(true);
        nombres_lb.setVisible(true);
        paterno_lb.setVisible(true);
        materno_lb.setVisible(true);
        nacimiento_lb.setVisible(true);
        correo_lb.setVisible(true);
        rfc_lb.setVisible(true);
        cp_lb.setVisible(true);
        lb_estado.setVisible(true);
        lb_municipio.setVisible(true);
        colonia_lb.setVisible(true);
        interior_lb.setVisible(true);
        exterior_lb.setVisible(true);
        regimen_lb.setVisible(true);
        //entradas
        entrada_rfc.setVisible(true);
        entrada_nombres.setVisible(true);
        entrada_apellidoPaterno.setVisible(true);
        entrada_apellidoMaterno.setVisible(true);
        entrada_fechaNacimiento.setVisible(true);
        entrada_cp.setVisible(true);
        entrada_colonia.setVisible(true);
        entrada_estado.setVisible(true);
        entrada_municipio.setVisible(true);
        entrada_correoElectronico.setVisible(true);
        entrada_noExterior.setVisible(true);
        entrada_noInterior.setVisible(true);
        entrada_regimen.setVisible(true);
        btn_guardarDatos.setVisible(true);
         btn_cancelar.setVisible(true);
        //bton de buscar cp
        btn_buscarCP.setVisible(true);
        //etiquetas de informacion
        infoIcon_lb.setVisible(true);
        infoIcon_lb2.setVisible(true);
        infoIcon_lb3.setVisible(true);
        infoIcon_lb4.setVisible(true);
    }

    public void ocultarCampos() {
        txt_rfc.setVisible(false);
        //Campos de busqueda
        rfc_busqueda.setVisible(true);
        icon_buscar.setVisible(true);
        lb_inicial.setVisible(true);
        logo_lb.setVisible(true);
        // etiquetas de campos
        txt_datosPersonales.setVisible(false);
        txt_datosFiscales.setVisible(false);
        nombres_lb.setVisible(false);
        paterno_lb.setVisible(false);
        materno_lb.setVisible(false);
        nacimiento_lb.setVisible(false);
        correo_lb.setVisible(false);
        rfc_lb.setVisible(false);
        cp_lb.setVisible(false);
        lb_estado.setVisible(false);
        lb_municipio.setVisible(false);
        colonia_lb.setVisible(false);
        interior_lb.setVisible(false);
        exterior_lb.setVisible(false);
        regimen_lb.setVisible(false);
        // entradas
        entrada_rfc.setVisible(false);
        entrada_nombres.setVisible(false);
        entrada_apellidoPaterno.setVisible(false);
        entrada_apellidoMaterno.setVisible(false);
        entrada_fechaNacimiento.setVisible(false);
        entrada_cp.setVisible(false);
        entrada_colonia.setVisible(false);
        entrada_estado.setVisible(false);
        entrada_municipio.setVisible(false);
        entrada_correoElectronico.setVisible(false);
        entrada_noExterior.setVisible(false);
        entrada_noInterior.setVisible(false);
        entrada_regimen.setVisible(false);
        btn_guardarDatos.setVisible(false);
        btn_cancelar.setVisible(false);
        // botón de buscar cp
        btn_buscarCP.setVisible(false);
        // etiquetas de información
        infoIcon_lb.setVisible(false);
        infoIcon_lb2.setVisible(false);
        infoIcon_lb3.setVisible(false);
        infoIcon_lb4.setVisible(false);
    }

    
    public void obtenerDireccion(String colonia) {
        try {
            direc = new ObtenerDireccion(entrada_cp.getText());
            entrada_estado.removeAllItems();
            entrada_municipio.removeAllItems();
            entrada_colonia.removeAllItems();
            //si el estado no esta vacio quiere decir que el codigo pertenece a mexico
            entrada_estado.addItem(direc.estado);
            entrada_municipio.addItem(direc.municipio);
            entrada_colonia.setModel(new DefaultComboBoxModel<>(direc.colonias));
            entrada_colonia.setSelectedItem(colonia);
        } catch (Exception ex) {
            Logger.getLogger(ModificarEmisor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

        
        
//    public void setDatos(String rfc, String nombres, String apellido_paterno, String apellido_materno, Calendar fecha_nacimiento, String correo, String regimen, int cp, String colonia,  String num_Exterior,String num_Interior) {
//        this.rfc=rfc;//rfc que se va a modificar
//        this.correo=correo;
//        entrada_rfc.setText(rfc);
//        entrada_nombres.setText(nombres);
//        entrada_apellidoPaterno.setText(apellido_paterno);
//        entrada_apellidoMaterno.setText(apellido_materno);
//        // Convertir Calendar a java.util.Date
//        java.util.Date fechaDate = fecha_nacimiento.getTime();
//        // Establecer la fecha en el JDateChooser
//        entrada_fechaNacimiento.setDate(fechaDate);
//        entrada_correoElectronico.setText(correo);
//        entrada_cp.setText(""+cp);
//        
//        entrada_noExterior.setText(num_Exterior);
//        entrada_noInterior.setText(num_Interior);
//        
//        //entrada de estado, municipio y colonia
//        obtenerDireccion(colonia);
//        //definir el regimen seleccionado
//        if(regimen.equals("Simplificado de Confianza. ")){
//            entrada_regimen.setSelectedIndex(0);
//        }
//        else if(regimen.equals("612  Persona Física con Actividad Empresarial")){
//            entrada_regimen.setSelectedIndex(1);
//        }else{
//            entrada_regimen.setSelectedIndex(2);
//        }
//        
//        //solo muestra el menu de emisor si el usuario es el director
//        if(!"director".equals(this.usuario)){
//            btn_emisor.setVisible(false);
//        }
//    }
    
    public void setDatosSesion(String usuario, LocalDate fechaInicioSesion, LocalTime horaInicioSesion){
        this.usuario=usuario;
        this.fechaInicioSesion = fechaInicioSesion;
        this.horaInicioSesion = horaInicioSesion;
        txt_nombreUser.setText(usuario);
    }
    
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
                    Logger.getLogger(ModificarPadre.class.getName()).log(Level.SEVERE, null, ex);
                }
            // Cerrar la aplicación
            this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        } else {
            // Evitar que la ventana se cierre
            this.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_formWindowClosing

    public boolean existeInfo() {
        // Retorna true si al menos uno de los campos tiene información que no sea solo espacios
        return !(entrada_nombres.getText().trim().isEmpty()
                && entrada_apellidoPaterno.getText().trim().isEmpty()
                && entrada_apellidoMaterno.getText().trim().isEmpty()
                && entrada_fechaNacimiento.getDate() == null // Verifica si no hay fecha
                && entrada_correoElectronico.getText().trim().isEmpty()
                && entrada_rfc.getText().trim().isEmpty()
                && entrada_cp.getText().trim().isEmpty());
    }

    public boolean fechaValida(){
        return entrada_fechaNacimiento.getDate() != null;
    }

    public boolean rfc_existente(){
        try {
            //Prepara la consulta para verificar si existe el RFC
            String consulta_rfc = "SELECT * FROM padre_familia WHERE rfc = ?";
            PreparedStatement ps = cx.conectar().prepareStatement(consulta_rfc);
            ps.setString(1, entrada_rfc.getText());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){//si encuentra un fila con el RFC quiere decir que ya existe
                if(rs.getString("rfc").equals(this.rfc.toUpperCase())){//es el mismo rfc que quiere actualizar
                    return false;
                }
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AltaEmisorPrim.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;//Retorna falso si no encuentra el RFC
    }
    
    //Funcion para validar el RFC
    public boolean rfc_valido() {
        if (entrada_rfc.getText().length() != 13) {
            JOptionPane.showMessageDialog(null, "El tamaño del RFC es de 13 caracteres obligatoriamnete", "RFC no valido", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        //obtener la homoclave del RFC ingresado
        String rfc_user = entrada_rfc.getText().toUpperCase();
        String homoclave = "";
        homoclave += entrada_rfc.getText().toUpperCase().charAt(10);
        homoclave += entrada_rfc.getText().toUpperCase().charAt(11);
        homoclave += entrada_rfc.getText().toUpperCase().charAt(12);

        // Obtener el objeto Date desde el JDateChooser
        Date fechaNacimiento = entrada_fechaNacimiento.getDate();
        // Crear una instancia de Calendar y asignarle la fecha
        Calendar calendarFechaNacimiento = Calendar.getInstance();
        calendarFechaNacimiento.setTime(fechaNacimiento);
        //Crea RFC 
        String rfc_sistema = valida.crear_rfc(
                entrada_nombres.getText(),
                entrada_apellidoPaterno.getText(),
                entrada_apellidoMaterno.getText(),
                calendarFechaNacimiento, // Pasa el objeto Calendar aquí
                homoclave
        );
        //Verifica si coincide con el RFC del sistema
        if (rfc_user.equals(rfc_sistema)) {
            //Expresion para validar un RFC
            String regex = "^[A-ZÑ&]{4}\\d{6}[A-Z0-9]{3}$";
            // Compilar la expresión regular en un patrón
            Pattern pattern = Pattern.compile(regex);
            // Crear el matcher que validará el RFC
            Matcher matcher = pattern.matcher(rfc_user);
            // Retornar si coincide o no
            if (matcher.matches()) {
                return true;
            }
            JOptionPane.showMessageDialog(null, "Ingrese un RFC valido", "RFC no valido", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        JOptionPane.showMessageDialog(null, "El RFC no coincide con el nombre, apellidos o con la fecha de nacimiento del emisor", "RFC no valido", JOptionPane.WARNING_MESSAGE);
        return false;
    }

    public boolean correoRepetido() {
        if (entrada_correoElectronico.getText().equals(this.correo)){//no existen cambios en el correo 
            return false;
        }
        try {
            //Prepara la consulta para verificar si existe el RFC
            String consulta_correo = "SELECT * FROM padre_familia WHERE correo_electronico = ?";
            PreparedStatement ps = cx.conectar().prepareStatement(consulta_correo);
            ps.setString(1, entrada_correoElectronico.getText());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {//si encuentra un fila con el correo quiere decir que ya existe
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AltaEmisorPrim.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;//Retorna falso si no encuentra registros con ese coreeo
    }
    
    public void actualizarPadre() {
        try {
            int cp = Integer.parseInt(entrada_cp.getText());

            // Obtener todos los datos de entrada
            Date fecha_nacimiento = entrada_fechaNacimiento.getDate();
            java.sql.Date fecha_sql = new java.sql.Date(fecha_nacimiento.getTime());

            // Crear consulta para actualizar los datos
            String query_actualizar = "UPDATE padre_familia SET rfc =?, nombres=?, apellido_paterno=?, apellido_materno=?, "
                    + "fecha_nacimiento=?, correo_electronico=?, domicilio_fiscal=?, estado =?, municipio=?, colonia = ?,"
                    + "num_exterior = ?, num_interior = ?, regimen=? WHERE rfc = ?";

            PreparedStatement ps = cx.conectar().prepareStatement(query_actualizar); // Creación de la consulta

            // Establecer los valores a actualizar
            ps.setString(1, entrada_rfc.getText().toUpperCase());
            ps.setString(2,valida.formatearNombresApellidos( entrada_nombres.getText()));
            ps.setString(3, valida.formatearNombresApellidos(entrada_apellidoPaterno.getText()));
            ps.setString(4, valida.formatearNombresApellidos(entrada_apellidoMaterno.getText()));
            ps.setDate(5, fecha_sql);
            ps.setString(6, entrada_correoElectronico.getText());
            ps.setInt(7, cp);
            ps.setString(8, entrada_estado.getSelectedItem().toString());
            ps.setString(9,entrada_municipio.getSelectedItem().toString());
            ps.setString(10, entrada_colonia.getSelectedItem().toString());
            ps.setString(11, entrada_noExterior.getText());
            ps.setString(12, entrada_noInterior.getText());
            ps.setString(13, entrada_regimen.getSelectedItem().toString());
            
            // Establecer el RFC para actualizar el registro correspondiente
            ps.setString(14, this.rfc.toUpperCase());


            // Verificar si se actualizó el registro
            int filas_actualizadas = ps.executeUpdate();
            if (filas_actualizadas > 0) {
                JOptionPane.showMessageDialog(null, "Datos actualizados exitosamente", "Actualización exitosa", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el registro con el RFC especificado", "Error en la actualización", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();  // Depuración detallada del error
            JOptionPane.showMessageDialog(null, "Hubo un error al actualizar los datos, intente otra vez", "Error en la actualización", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void icon_regresarlbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_icon_regresarlbMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt)) {
            Object[] opciones = {"Aceptar", "Cancelar"};
            // Si existe información que no ha sido guardada
            // Mostrar diálogo que pregunta si desea confirmar la salida
            int opcionSeleccionada = JOptionPane.showOptionDialog(
                    null,
                    "Se perderán los cambios, ¿Desea regresar?",
                    "Confirmación de volver",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    opciones,
                    opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"

            // Manejar las opciones seleccionadas
            if (opcionSeleccionada == JOptionPane.YES_OPTION) {
                //Regresa al menu principal
                MenuPrincipal ventana= new MenuPrincipal();
                ventana.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
                ventana.setVisible(true);
                this.dispose();
            } else {
                // Evitar que la ventana se cierre
                return;
            }
        }
    }//GEN-LAST:event_icon_regresarlbMouseClicked

    private void infoIcon_lb4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoIcon_lb4MouseEntered
        infocp_lb.setVisible(true);
    }//GEN-LAST:event_infoIcon_lb4MouseEntered

    private void infoIcon_lb4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoIcon_lb4MouseExited
        infocp_lb.setVisible(false);
    }//GEN-LAST:event_infoIcon_lb4MouseExited

    private void infoIcon_lb3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoIcon_lb3MouseEntered
        infoRFC_lb.setVisible(true);
    }//GEN-LAST:event_infoIcon_lb3MouseEntered

    private void infoIcon_lb3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoIcon_lb3MouseExited
        infoRFC_lb.setVisible(false);
    }//GEN-LAST:event_infoIcon_lb3MouseExited

    private void infoIcon_lbMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoIcon_lbMouseEntered
        info_nombre.setVisible(true);
    }//GEN-LAST:event_infoIcon_lbMouseEntered

    private void infoIcon_lbMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoIcon_lbMouseExited
        info_nombre.setVisible(false);
    }//GEN-LAST:event_infoIcon_lbMouseExited

    private void infoIcon_lb2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoIcon_lb2MouseEntered
        infoFecha_lb.setVisible(true);
    }//GEN-LAST:event_infoIcon_lb2MouseEntered

    private void infoIcon_lb2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoIcon_lb2MouseExited
        infoFecha_lb.setVisible(false);
    }//GEN-LAST:event_infoIcon_lb2MouseExited

    private void entrada_correoElectronicoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_entrada_correoElectronicoKeyTyped
        if(entrada_correoElectronico.getText().length()>=80){//si la longitud es mayor a 80 no permite seguir escribiendo
            JOptionPane.showMessageDialog(null, "Número maximo de cáracteres alcanzados", "Maximo alcanzado", JOptionPane.WARNING_MESSAGE);
            evt.consume();
        }
    }//GEN-LAST:event_entrada_correoElectronicoKeyTyped

    private void entrada_apellidoMaternoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_entrada_apellidoMaternoKeyTyped
        if(entrada_apellidoMaterno.getText().length()>=50){//si la longitud es mayor a 50 no permite seguir escribiendo
            JOptionPane.showMessageDialog(null, "Número maximo de cáracteres alcanzados", "Maximo alcanzado", JOptionPane.WARNING_MESSAGE);
            evt.consume();
        }
    }//GEN-LAST:event_entrada_apellidoMaternoKeyTyped

    private void entrada_apellidoPaternoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_entrada_apellidoPaternoKeyTyped
        if(entrada_apellidoPaterno.getText().length()>=50){//si la longitud es mayor a 50 no permite seguir escribiendo
            JOptionPane.showMessageDialog(null, "Número maximo de cáracteres alcanzados", "Maximo alcanzado", JOptionPane.WARNING_MESSAGE);
            evt.consume();
        }
    }//GEN-LAST:event_entrada_apellidoPaternoKeyTyped

    private void entrada_nombresKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_entrada_nombresKeyTyped
        if(entrada_nombres.getText().length()>=50){//si la longitud es mayor a 50 no permite seguir escribiendo
            JOptionPane.showMessageDialog(null, "Número maximo de cáracteres alcanzados", "Maximo alcanzado", JOptionPane.WARNING_MESSAGE);
            evt.consume();
        }
    }//GEN-LAST:event_entrada_nombresKeyTyped

    private void entrada_noExteriorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_entrada_noExteriorKeyTyped
        if(entrada_noExterior.getText().length()>=20){//si la longitud es mayor a 20 no permite seguir escribiendo
            JOptionPane.showMessageDialog(null, "Número maximo de cáracteres alcanzados", "Maximo alcanzado", JOptionPane.WARNING_MESSAGE);
            evt.consume();
        }
    }//GEN-LAST:event_entrada_noExteriorKeyTyped

    private void entrada_noInteriorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_entrada_noInteriorKeyTyped
        if(entrada_noInterior.getText().length()>=20){//si la longitud es mayor a 20 no permite seguir escribiendo
            JOptionPane.showMessageDialog(null, "Número maximo de cáracteres alcanzados", "Maximo alcanzado", JOptionPane.WARNING_MESSAGE);
            evt.consume();
        }
    }//GEN-LAST:event_entrada_noInteriorKeyTyped

    private void btn_buscarCPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarCPActionPerformed
        //busca las coincidencias con el codigo postal
        if (valida.cpValido(entrada_cp.getText())) {
            //obtener los datos del codigo postal si es valido
            try {
                direc = new ObtenerDireccion(entrada_cp.getText());
                if (!direc.estado.isEmpty()) {
                    entrada_estado.removeAllItems();
                    entrada_municipio.removeAllItems();
                    entrada_colonia.removeAllItems();
                    //si el estado no esta vacio quiere decir que el codigo pertenece a mexico
                    entrada_estado.addItem(direc.estado);
                    entrada_municipio.addItem(direc.municipio);
                    entrada_colonia.setModel(new DefaultComboBoxModel<>(direc.colonias));

                } else {
                    JOptionPane.showMessageDialog(null, "Ingrese un codigo postal de Mexico", "Codigo postal no valido", JOptionPane.WARNING_MESSAGE);
                    entrada_cp.requestFocusInWindow();    // Borde al tener foco;
                    return;
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Hubo un error en la consulta de codigos postales\n"
                        + "Verifique su conexión a internet "
                        + "\nSi el problema persiste contacte al soporte del sistema", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese un codigo postal valido", "Codigo postal no valido", JOptionPane.WARNING_MESSAGE);
            entrada_cp.requestFocusInWindow();    // Borde al tener foco;
            return;
        }
    }//GEN-LAST:event_btn_buscarCPActionPerformed

    private void nombre_userMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nombre_userMouseClicked

    }//GEN-LAST:event_nombre_userMouseClicked

    private void btn_historialSesionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_historialSesionesMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt)){
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
                HistorialSesiones ventana = new HistorialSesiones();
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
                    Logger.getLogger(ModificarPadre.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(ModificarPadre.class.getName()).log(Level.SEVERE, null, ex);
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
                ConsultarAlumnosEdit ventana = new ConsultarAlumnosEdit();
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

    private void txt_altaPadresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_altaPadresMouseClicked
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
                AltaPadres ventana = new AltaPadres();
                ventana.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
                ventana.setVisible(true);
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
                ConsultarPadre ventana = new ConsultarPadre();
                ventana.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
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
                ConsultarEmisorEdit ventana = new ConsultarEmisorEdit();
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

    private void entrada_rfcKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_entrada_rfcKeyTyped
       if(entrada_rfc.getText().length()>=13){
           //JOptionPane.showMessageDialog(null, "Número maximo de cáracteres alcanzados", "Maximo alcanzado", JOptionPane.WARNING_MESSAGE);
           evt.consume();   
       }
    }//GEN-LAST:event_entrada_rfcKeyTyped

    //Formatear los campos de entrada
    private void entrada_nombresFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_entrada_nombresFocusLost
        entrada_nombres.setText(valida.formatearNombresApellidos(entrada_nombres.getText()));
    }//GEN-LAST:event_entrada_nombresFocusLost

    private void entrada_apellidoPaternoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_entrada_apellidoPaternoFocusLost
        entrada_apellidoPaterno.setText(valida.formatearNombresApellidos(entrada_apellidoPaterno.getText()));
    }//GEN-LAST:event_entrada_apellidoPaternoFocusLost

    private void entrada_apellidoMaternoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_entrada_apellidoMaternoFocusLost
        entrada_apellidoMaterno.setText(valida.formatearNombresApellidos(entrada_apellidoMaterno.getText()));
    }//GEN-LAST:event_entrada_apellidoMaternoFocusLost

    private void entrada_rfcFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_entrada_rfcFocusLost
        entrada_rfc.setText(entrada_rfc.getText().toUpperCase());
    }//GEN-LAST:event_entrada_rfcFocusLost

    private void rfc_busquedaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rfc_busquedaFocusLost
        rfc_busqueda.setText(rfc_busqueda.getText().toUpperCase());
    }//GEN-LAST:event_rfc_busquedaFocusLost

    private void rfc_busquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rfc_busquedaActionPerformed
        Validacion valida = new Validacion();
        if (rfc_busqueda.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor ingrese un RFC para consultar", "RFC no ingresado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (rfc_busqueda.getText().length() < 13) {
            JOptionPane.showMessageDialog(null, "El RFC debe ser de 13 digitos", "RFC no valido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!valida.rfc_valido(rfc_busqueda.getText().toUpperCase())) {
            JOptionPane.showMessageDialog(null, "Por favor ingrese un RFC valido para consultar", "RFC no valido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        mostrarDatos(rfc_busqueda.getText().toUpperCase());
        rfc_busqueda.setText(rfc_busqueda.getText().toUpperCase());
    }//GEN-LAST:event_rfc_busquedaActionPerformed

    private void rfc_busquedaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rfc_busquedaKeyTyped

        if (rfc_busqueda.getText().length() >= 13 && evt.getKeyChar() != KeyEvent.VK_ENTER) {
            JOptionPane.showMessageDialog(null, "El RFC debe ser de 13 digitos", "Advertencia", JOptionPane.WARNING_MESSAGE);
            evt.consume();
        }
    }//GEN-LAST:event_rfc_busquedaKeyTyped

    private void icon_buscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_icon_buscarMouseClicked
        ///boton para buscar
        if(SwingUtilities.isLeftMouseButton(evt)){
            Validacion valida = new Validacion();
            if(rfc_busqueda.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Por favor ingrese un RFC para consultar", "RFC no ingresado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(rfc_busqueda.getText().length()<13){
                JOptionPane.showMessageDialog(null, "El RFC debe ser de 13 digitos", "RFC no valido", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(!valida.rfc_valido(rfc_busqueda.getText().toUpperCase())){
                JOptionPane.showMessageDialog(null, "Por favor ingrese un RFC valido para consultar", "RFC no valido", JOptionPane.WARNING_MESSAGE);
                return;
            }

            mostrarDatos(rfc_busqueda.getText().toUpperCase());
            rfc_busqueda.setText(rfc_busqueda.getText().toUpperCase());
        }
    }//GEN-LAST:event_icon_buscarMouseClicked

    private void btn_guardarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarDatosActionPerformed

        if (!existeInfo()) {
            JOptionPane.showMessageDialog(null, "Ingrese todos los datos del emisor", "Todos los datos son obligatorios", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!valida.nombresValidos(valida.formatearNombresApellidos(entrada_nombres.getText()))) {
            JOptionPane.showMessageDialog(null, "Ingrese un nombre valido", "Nombre no valido", JOptionPane.WARNING_MESSAGE);
            entrada_nombres.requestFocusInWindow();
            return;
        }
        if (!valida.apellidoValido(valida.formatearNombresApellidos(entrada_apellidoPaterno.getText()))) {
            JOptionPane.showMessageDialog(null, "Ingrese un apellido paterno valido", "Apellido no valido", JOptionPane.WARNING_MESSAGE);
            entrada_apellidoPaterno.requestFocusInWindow();
            return;
        }
        if (!valida.apellidoValido(valida.formatearNombresApellidos(entrada_apellidoMaterno.getText()))) {
            JOptionPane.showMessageDialog(null, "Ingrese un apellido materno valido", "Apellido no valido", JOptionPane.WARNING_MESSAGE);
            entrada_apellidoMaterno.requestFocusInWindow();
            return;
        }
        if (!fechaValida()) {
            JOptionPane.showMessageDialog(null, "Ingrese una fecha de nacimiento valida", "Fecha no valido", JOptionPane.WARNING_MESSAGE);
            entrada_fechaNacimiento.requestFocusInWindow();
            return;
        }
        if (!valida.correo_valido(entrada_correoElectronico.getText())) {
            JOptionPane.showMessageDialog(null, "Ingrese un correo electronico valido", "Correo no valido", JOptionPane.WARNING_MESSAGE);
            entrada_correoElectronico.requestFocusInWindow();
            return;
        }
        if (correoRepetido()) {
            JOptionPane.showMessageDialog(null, "El correo ya se encuentra registrado\nPor favor ingrese otro", "Correo repetido", JOptionPane.WARNING_MESSAGE);
            entrada_correoElectronico.requestFocusInWindow();
            return;
        }
        if (!rfc_valido()) {
            entrada_rfc.requestFocusInWindow();
            return;
        }
        if (rfc_existente()) {
            JOptionPane.showMessageDialog(null, "El RFC ya se encuentra registrado", "RFC existente", JOptionPane.WARNING_MESSAGE);
            entrada_rfc.requestFocusInWindow();    // Borde al tener foco;
            return;
        }
        if (!valida.cpValido(entrada_cp.getText())) {
            JOptionPane.showMessageDialog(null, "Ingrese un codigo postal valido", "Codigo postal no valido", JOptionPane.WARNING_MESSAGE);
            entrada_cp.requestFocusInWindow();    // Borde al tener foco;
            return;
        }
        if (entrada_colonia.getSelectedItem().toString().equals("<seleccionar>")) {//sino selecciona una colonia
            JOptionPane.showMessageDialog(null, "Seleccione una colonia", "Colonia no seleccionada", JOptionPane.WARNING_MESSAGE);
            entrada_colonia.requestFocusInWindow();    // Borde al tener foco;
            return;
        }
        if (!valida.numInteriorExteriorValido(entrada_noExterior.getText())) {
            JOptionPane.showMessageDialog(null, "Ingrese un nímero exterior valido", "Número exterior no valido", JOptionPane.WARNING_MESSAGE);
            entrada_noExterior.requestFocusInWindow();    // Borde al tener foco;
            return;
        }
        if (!valida.numInteriorExteriorValido(entrada_noInterior.getText())) {
            JOptionPane.showMessageDialog(null, "Ingrese un numero exterior valido", "Numero interior no valido", JOptionPane.WARNING_MESSAGE);
            entrada_noInterior.requestFocusInWindow();    // Borde al tener foco;
            return;
        }
        Object[] opciones = {"Aceptar", "Cancelar"};
        // Si existe información que no ha sido guardada
        // Mostrar diálogo que pregunta si desea confirmar la salida
        int opcionSeleccionada = JOptionPane.showOptionDialog(
                null,
                "¿Desea modificar los datos del padre de familia?",
                "Modificacion de datos",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                opciones,
                opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"

        // Manejar las opciones seleccionadas
        if (opcionSeleccionada == JOptionPane.YES_OPTION) {
            //actualizar datos
            actualizarPadre();
            ocultarCampos();
            rfc_busqueda.setText("");          
        } else {
            // Evitar que la ventana se cierre
            return;
        }
    }//GEN-LAST:event_btn_guardarDatosActionPerformed

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        Object[] opciones = {"Aceptar", "Cancelar"};
        // Si existe información que no ha sido guardada
        // Mostrar diálogo que pregunta si desea confirmar la salida
        int opcionSeleccionada = JOptionPane.showOptionDialog(
                null,
                "No se guardarán los cambios, ¿Regresar?",
                "Confirmación de salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                opciones,
                opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"

        // Manejar las opciones seleccionadas
        if (opcionSeleccionada == JOptionPane.YES_OPTION) {
            ocultarCampos();
            rfc_busqueda.setText("");
        } else {
            // Evitar que la ventana se cierre
            return;
        }      
    }//GEN-LAST:event_btn_cancelarActionPerformed

    
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
            java.util.logging.Logger.getLogger(ModificarPadre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ModificarPadre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ModificarPadre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ModificarPadre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new ModificarPadre().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fecha;
    private javax.swing.JPanel barra_nav;
    private javax.swing.JPanel btn_alumnos;
    private javax.swing.JButton btn_buscarCP;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JPanel btn_cerrarSesion;
    private javax.swing.JPanel btn_emisor;
    private javax.swing.JPanel btn_estadisticas;
    private javax.swing.JPanel btn_facturas;
    private javax.swing.JButton btn_guardarDatos;
    private javax.swing.JPanel btn_historialSesiones;
    private javax.swing.JPanel btn_padres;
    private javax.swing.JPanel btn_salir;
    private javax.swing.JLabel cerrar_icon;
    private javax.swing.JLabel colonia_lb;
    private javax.swing.JPanel contenedor;
    private javax.swing.JPanel contenedor_menu;
    private javax.swing.JLabel correo_lb;
    private javax.swing.JLabel cp_lb;
    private javax.swing.JTextField entrada_apellidoMaterno;
    private javax.swing.JTextField entrada_apellidoPaterno;
    private javax.swing.JComboBox<String> entrada_colonia;
    private javax.swing.JTextField entrada_correoElectronico;
    private javax.swing.JFormattedTextField entrada_cp;
    private javax.swing.JComboBox<String> entrada_estado;
    private com.toedter.calendar.JDateChooser entrada_fechaNacimiento;
    private javax.swing.JComboBox<String> entrada_municipio;
    private javax.swing.JTextField entrada_noExterior;
    private javax.swing.JTextField entrada_noInterior;
    private javax.swing.JTextField entrada_nombres;
    private javax.swing.JComboBox<String> entrada_regimen;
    private javax.swing.JTextField entrada_rfc;
    private javax.swing.JLabel exterior_lb;
    private javax.swing.JPanel fondo;
    private javax.swing.JLabel historial_lb;
    private javax.swing.JLabel hora_lb;
    private javax.swing.JLabel icon_buscar;
    private javax.swing.JLabel icon_item;
    private javax.swing.JLabel icon_item2;
    private javax.swing.JLabel icon_item3;
    private javax.swing.JLabel icon_item4;
    private javax.swing.JLabel icon_item5;
    private javax.swing.JLabel icon_regresarlb;
    private javax.swing.JLabel icon_salir;
    private javax.swing.JLabel infoFecha_lb;
    private javax.swing.JLabel infoIcon_lb;
    private javax.swing.JLabel infoIcon_lb2;
    private javax.swing.JLabel infoIcon_lb3;
    private javax.swing.JLabel infoIcon_lb4;
    private javax.swing.JLabel infoRFC_lb;
    private javax.swing.JLabel info_nombre;
    private javax.swing.JLabel infocp_lb;
    private javax.swing.JLabel interior_lb;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel4;
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
    private javax.swing.JLabel lb_estado;
    private javax.swing.JLabel lb_inicial;
    private javax.swing.JLabel lb_municipio;
    private javax.swing.JLabel logo_lb;
    private javax.swing.JLabel materno_lb;
    private javax.swing.JPanel menu_alumnos;
    private javax.swing.JPanel menu_emisor;
    private javax.swing.JPanel menu_estadisticas;
    private javax.swing.JPanel menu_factura;
    private javax.swing.JPanel menu_padres;
    private javax.swing.JPanel menu_salir;
    private javax.swing.JPanel menu_user;
    private javax.swing.JLabel nacimiento_lb;
    private javax.swing.JPanel nombre_user;
    private javax.swing.JLabel nombres_lb;
    private javax.swing.JLabel paterno_lb;
    private javax.swing.JLabel regimen_lb;
    private javax.swing.JPanel registrarPadre;
    private javax.swing.JTextField rfc_busqueda;
    private javax.swing.JLabel rfc_lb;
    private javax.swing.JLabel text_salir;
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
    private javax.swing.JLabel txt_datosFiscales;
    private javax.swing.JLabel txt_datosPersonales;
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
    private javax.swing.JLabel txt_nombreUser;
    private javax.swing.JLabel txt_padres;
    private javax.swing.JLabel txt_rfc;
    private javax.swing.JLabel user_menuIcon;
    private javax.swing.JLabel user_menuIcon1;
    // End of variables declaration//GEN-END:variables
}
