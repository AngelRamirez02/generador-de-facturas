/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package emisor;

import alumnos.AltaAlumnos;
import alumnos.ConsultarAlumnos;
import alumnos.ConsultarAlumnosEdit;
import alumnos.EliminarAlumno;
import com.toedter.calendar.JCalendar;
import conexion.conexion;
import direccion.ObtenerDireccion;
import menu.*;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
import padres.AltaPadres;
import padres.ConsultarPadres;
import padres.ConsultarPadresEdit;
import padres.EliminarPadre;
import sesiones.HistorialSesiones;
import validacion.Validacion;

/**
 *
 * @author ar275
 */
public class ModificarEmisor extends javax.swing.JFrame {

    conexion cx = new conexion();
    Validacion valida = new Validacion();//objeto para valdicar los datos
    
    ObtenerDireccion direc;
    
    private String usuario;//Nombre del usuario que inicia sesión
    LocalDate fechaInicioSesion;
    LocalTime horaInicioSesion;
    
    private String rfcOriginal;//rfc que se va a modficar
    //Colores para los botones seleccionados y no
    Color colorbtnSeleccionado = Color.decode("#A91E1F");
    Color colorbtnNoSeleccionado = Color.decode("#C94545");
    //Iconos de item para menu no selccionado
    Image icon_img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/icon_itemMenu.png"));
     //Imagen para menu selccionado
    Image icon_seleccionado = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/icon_itemSeleccionado.png"));
    Image info_img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/icon_info.png"));
    
    Image img_regresar = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/icon_regresar.png"));
    
     public ModificarEmisor() {
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
       
        //Iconos para botones de menu
        icon_item.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
        icon_item2.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
        icon_item3.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
        icon_item4.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
        icon_item5.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
        contenedor_menu.setLocation(user_menuIcon.getLocation().x-650, contenedor_menu.getLocation().y);//centrar el contenedor   
        
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
        jLabel17 = new javax.swing.JLabel();
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
        btn_guardarDatos = new paneles.PanelRound();
        contenedor_btn = new paneles.PanelRound();
        text_guardarDatos = new javax.swing.JLabel();
        entrada_cp = new javax.swing.JFormattedTextField();
        entrada_regimen = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        entrada_rfc = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        entrada_correoElectronico = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        entrada_apellidoMaterno = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        entrada_apellidoPaterno = new javax.swing.JTextField();
        entrada_nombres = new javax.swing.JTextField();
        nombres_lb = new javax.swing.JLabel();
        registrarEmisor_Titulo = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        datosPersonales_titulo = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        datosfiscales_titulo = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        entrada_fechaNacimiento = new com.toedter.calendar.JDateChooser();
        entrada_estado = new javax.swing.JComboBox<>();
        entrada_municipio = new javax.swing.JComboBox<>();
        txt_estado = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        entrada_colonia = new javax.swing.JComboBox<>();
        entrada_noExterior = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        entrada_noInterior = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Instituto Andrés Manuel López Obrador - Modificar emisorl");
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

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_cerrarSesion.png"))); // NOI18N
        btn_cerrarSesion.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 40, 50));

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
        txt_consultarAlmnos1.setText("Consultar facturas");
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

        btn_guardarDatos.setBackground(new java.awt.Color(0, 0, 0));
        btn_guardarDatos.setRoundBottomLeft(10);
        btn_guardarDatos.setRoundBottomRight(10);
        btn_guardarDatos.setRoundTopLeft(10);
        btn_guardarDatos.setRoundTopRight(10);
        btn_guardarDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_guardarDatosMouseClicked(evt);
            }
        });
        btn_guardarDatos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        contenedor_btn.setBackground(new java.awt.Color(217, 217, 217));
        contenedor_btn.setRoundBottomLeft(10);
        contenedor_btn.setRoundBottomRight(10);
        contenedor_btn.setRoundTopLeft(10);
        contenedor_btn.setRoundTopRight(10);
        contenedor_btn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        text_guardarDatos.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        text_guardarDatos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        text_guardarDatos.setText("Actualizar datos del emisor");
        text_guardarDatos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        contenedor_btn.add(text_guardarDatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 40));

        btn_guardarDatos.add(contenedor_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 2, 240, 40));

        contenedor.add(btn_guardarDatos);
        btn_guardarDatos.setBounds(400, 550, 245, 45);

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

        jLabel10.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel10.setText("Código postal");
        contenedor.add(jLabel10);
        jLabel10.setBounds(540, 300, 120, 22);
        contenedor.add(entrada_rfc);
        entrada_rfc.setBounds(680, 232, 157, 30);

        jLabel9.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel9.setText("Régimen Fiscal");
        contenedor.add(jLabel9);
        jLabel9.setBounds(540, 490, 126, 22);

        jLabel8.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel8.setText("RFC");
        contenedor.add(jLabel8);
        jLabel8.setBounds(540, 240, 35, 22);

        entrada_correoElectronico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                entrada_correoElectronicoKeyTyped(evt);
            }
        });
        contenedor.add(entrada_correoElectronico);
        entrada_correoElectronico.setBounds(260, 490, 190, 30);

        jLabel7.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel7.setText("Correo electrónico");
        contenedor.add(jLabel7);
        jLabel7.setBounds(70, 490, 170, 20);

        jLabel6.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel6.setText("Fecha de nacimiento");
        contenedor.add(jLabel6);
        jLabel6.setBounds(70, 430, 180, 20);

        entrada_apellidoMaterno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                entrada_apellidoMaternoKeyTyped(evt);
            }
        });
        contenedor.add(entrada_apellidoMaterno);
        entrada_apellidoMaterno.setBounds(260, 360, 190, 30);

        jLabel5.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel5.setText("Apellido Materno");
        contenedor.add(jLabel5);
        jLabel5.setBounds(70, 367, 150, 22);

        jLabel3.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel3.setText("Apellido peterno");
        contenedor.add(jLabel3);
        jLabel3.setBounds(70, 305, 140, 22);

        entrada_apellidoPaterno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                entrada_apellidoPaternoKeyTyped(evt);
            }
        });
        contenedor.add(entrada_apellidoPaterno);
        entrada_apellidoPaterno.setBounds(260, 300, 190, 30);

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

        registrarEmisor_Titulo.setBackground(new java.awt.Color(255, 255, 255));
        registrarEmisor_Titulo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Roboto Light", 1, 48)); // NOI18N
        jLabel4.setText("Modificar emisor");
        registrarEmisor_Titulo.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 0, -1, 60));

        contenedor.add(registrarEmisor_Titulo);
        registrarEmisor_Titulo.setBounds(200, 60, 620, 60);

        datosPersonales_titulo.setBackground(new java.awt.Color(255, 255, 255));
        datosPersonales_titulo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Datos personales");
        datosPersonales_titulo.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 30));

        contenedor.add(datosPersonales_titulo);
        datosPersonales_titulo.setBounds(115, 180, 200, 30);

        datosfiscales_titulo.setBackground(new java.awt.Color(255, 255, 255));
        datosfiscales_titulo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Datos fiscales");
        datosfiscales_titulo.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 30));

        contenedor.add(datosfiscales_titulo);
        datosfiscales_titulo.setBounds(640, 180, 170, 30);

        entrada_fechaNacimiento.setDateFormatString("dd MMM y");
        entrada_fechaNacimiento.setMaxSelectableDate(new java.util.Date(1735628468000L));
        entrada_fechaNacimiento.setMinSelectableDate(new java.util.Date(-315593932000L));
        contenedor.add(entrada_fechaNacimiento);
        entrada_fechaNacimiento.setBounds(260, 420, 190, 30);

        entrada_estado.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        entrada_estado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<seleccionar>" }));
        entrada_estado.setEnabled(false);
        contenedor.add(entrada_estado);
        entrada_estado.setBounds(610, 360, 140, 30);

        entrada_municipio.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        entrada_municipio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<seleccionar>" }));
        entrada_municipio.setEnabled(false);
        contenedor.add(entrada_municipio);
        entrada_municipio.setBounds(850, 360, 180, 30);

        txt_estado.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        txt_estado.setText("Estado");
        contenedor.add(txt_estado);
        txt_estado.setBounds(540, 360, 90, 30);

        jLabel2.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel2.setText("Municipio");
        contenedor.add(jLabel2);
        jLabel2.setBounds(760, 360, 90, 30);

        jLabel13.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel13.setText("Colonia");
        contenedor.add(jLabel13);
        jLabel13.setBounds(540, 420, 90, 22);

        entrada_colonia.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        entrada_colonia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<seleccionar>" }));
        contenedor.add(entrada_colonia);
        entrada_colonia.setBounds(620, 420, 210, 30);

        entrada_noExterior.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                entrada_noExteriorKeyTyped(evt);
            }
        });
        contenedor.add(entrada_noExterior);
        entrada_noExterior.setBounds(840, 420, 90, 30);

        jLabel14.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("N° Exterior");
        contenedor.add(jLabel14);
        jLabel14.setBounds(840, 450, 90, 20);

        entrada_noInterior.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                entrada_noInteriorKeyTyped(evt);
            }
        });
        contenedor.add(entrada_noInterior);
        entrada_noInterior.setBounds(940, 420, 100, 30);

        jLabel16.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("N° Interior");
        contenedor.add(jLabel16);
        jLabel16.setBounds(940, 450, 100, 20);

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        contenedor.add(jButton1);
        jButton1.setBounds(880, 300, 75, 30);

        fondo.add(contenedor);
        contenedor.setBounds(0, 0, 1050, 650);

        getContentPane().add(fondo, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void obtenerDireccion(String colonia){
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
    
    public void setDatos(String rfc, String nombres, String apellido_paterno, String apellido_materno, Calendar fecha_nacimiento, String correo, String regimen, int cp, String colonia,  String num_Exterior,String num_Interior) {
        this.rfcOriginal=rfc;//rfc que se va a modificar
        entrada_rfc.setText(rfc);
        entrada_nombres.setText(nombres);
        entrada_apellidoPaterno.setText(apellido_paterno);
        entrada_apellidoMaterno.setText(apellido_materno);
        // Convertir Calendar a java.util.Date
        java.util.Date fechaDate = fecha_nacimiento.getTime();
        // Establecer la fecha en el JDateChooser
        entrada_fechaNacimiento.setDate(fechaDate);
        entrada_correoElectronico.setText(correo);
        entrada_cp.setText(""+cp);
        
        entrada_noExterior.setText(num_Exterior);
        entrada_noInterior.setText(num_Interior);
        
        //entrada de estado, municipio y colonia
        obtenerDireccion(colonia);
        //definir el regimen seleccionado
        if(regimen.equals("Simplificado de Confianza. ")){
            entrada_regimen.setSelectedIndex(0);
        }
        else if(regimen.equals("612  Persona Física con Actividad Empresarial")){
            entrada_regimen.setSelectedIndex(1);
        }else{
            entrada_regimen.setSelectedIndex(2);
        }
        
        //solo muestra el menu de emisor si el usuario es el director
        if(!"director".equals(this.usuario)){
            btn_emisor.setVisible(false);
        }
    }
    
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
            String consulta_rfc = "SELECT * FROM emisor WHERE rfc = ?";
            PreparedStatement ps = cx.conectar().prepareStatement(consulta_rfc);
            ps.setString(1, entrada_rfc.getText());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){//si encuentra un fila con el RFC quiere decir que ya existe
                if(rs.getString("rfc").equals(rfcOriginal)){//es el mismo rfc que quiere actualizar
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

    public void actualizarEmisor() {
        try {
            int cp = Integer.parseInt(entrada_cp.getText());

            // Obtener todos los datos de entrada
            Date fecha_nacimiento = entrada_fechaNacimiento.getDate();
            java.sql.Date fecha_sql = new java.sql.Date(fecha_nacimiento.getTime());

            // Crear consulta para actualizar los datos
            String query_actualizar = "UPDATE emisor SET rfc =?, nombres=?, apellido_paterno=?, apellido_materno=?, "
                    + "fecha_nacimiento=?, correo_electronico=?, domicilio_fiscal=?, estado =?, municipio=?, colonia = ?,"
                    + "num_exterior = ?, num_interior = ?, regimen=? WHERE rfc = ?";

            PreparedStatement ps = cx.conectar().prepareStatement(query_actualizar); // Creación de la consulta

            // Establecer los valores a actualizar
            ps.setString(1, entrada_rfc.getText().toUpperCase());
            ps.setString(2, entrada_nombres.getText());
            ps.setString(3, entrada_apellidoPaterno.getText());
            ps.setString(4, entrada_apellidoMaterno.getText());
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
            ps.setString(14, rfcOriginal.toUpperCase());

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
                ConsultarEmisorEdit ventana = new ConsultarEmisorEdit();
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

    private void btn_guardarDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_guardarDatosMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt)) {//click izquierdo
            if(!existeInfo()){
                JOptionPane.showMessageDialog(null, "Ingrese todos los datos del emisor", "Todos los datos son obligatorios", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(!valida.nombresValidos(entrada_nombres.getText())){
                JOptionPane.showMessageDialog(null, "Ingrese un nombre valido", "Nombre no valido", JOptionPane.WARNING_MESSAGE);
                entrada_nombres.requestFocusInWindow();
                return;
            }
            if(!valida.apellidoValido(entrada_apellidoPaterno.getText())){
                JOptionPane.showMessageDialog(null, "Ingrese un apellido paterno valido", "Apellido no valido", JOptionPane.WARNING_MESSAGE);
                entrada_apellidoPaterno.requestFocusInWindow();
                return;
            }
            if(!valida.apellidoValido(entrada_apellidoMaterno.getText())){
                JOptionPane.showMessageDialog(null, "Ingrese un apellido materno valido", "Apellido no valido", JOptionPane.WARNING_MESSAGE);
                entrada_apellidoMaterno.requestFocusInWindow();
                return;
            }
            if(!fechaValida()){
                JOptionPane.showMessageDialog(null, "Ingrese una fecha de nacimiento valida", "Fecha no valido", JOptionPane.WARNING_MESSAGE);
                entrada_fechaNacimiento.requestFocusInWindow();
                return;
            }
            if(!valida.correo_valido(entrada_correoElectronico.getText())){
                JOptionPane.showMessageDialog(null, "Ingrese un correo electronico valido", "Correo no valido", JOptionPane.WARNING_MESSAGE);
                entrada_correoElectronico.requestFocusInWindow();
                return;
            }
            if(!rfc_valido()){
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
                    "¿Desea modificar los datos del emisor?",
                    "Modificacion de datos",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    opciones,
                    opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"

            // Manejar las opciones seleccionadas
            if (opcionSeleccionada == JOptionPane.YES_OPTION) {
                //actualizar datos
                actualizarEmisor();
                //volver a la lista de los emisores
                ConsultarEmisorEdit ventana = new ConsultarEmisorEdit();
                ventana.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
                ventana.setVisible(true);
                this.dispose();
            } else {
                // Evitar que la ventana se cierre
                return;
            }
        }
    }//GEN-LAST:event_btn_guardarDatosMouseClicked

    private void entrada_correoElectronicoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_entrada_correoElectronicoKeyTyped
        if(entrada_nombres.getText().length()>=80){//si la longitud es mayor a 80 no permite seguir escribiendo
            evt.consume();
        }
    }//GEN-LAST:event_entrada_correoElectronicoKeyTyped

    private void entrada_apellidoMaternoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_entrada_apellidoMaternoKeyTyped
        if(entrada_nombres.getText().length()>=50){//si la longitud es mayor a 50 no permite seguir escribiendo
            evt.consume();
        }
    }//GEN-LAST:event_entrada_apellidoMaternoKeyTyped

    private void entrada_apellidoPaternoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_entrada_apellidoPaternoKeyTyped
        if(entrada_nombres.getText().length()>=50){//si la longitud es mayor a 50 no permite seguir escribiendo
            evt.consume();
        }
    }//GEN-LAST:event_entrada_apellidoPaternoKeyTyped

    private void entrada_nombresKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_entrada_nombresKeyTyped
        if(entrada_nombres.getText().length()>=50){//si la longitud es mayor a 50 no permite seguir escribiendo
            evt.consume();
        }
    }//GEN-LAST:event_entrada_nombresKeyTyped

    private void entrada_noExteriorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_entrada_noExteriorKeyTyped
        if(entrada_nombres.getText().length()>=20){//si la longitud es mayor a 20 no permite seguir escribiendo
            evt.consume();
        }
    }//GEN-LAST:event_entrada_noExteriorKeyTyped

    private void entrada_noInteriorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_entrada_noInteriorKeyTyped
        if(entrada_nombres.getText().length()>=20){//si la longitud es mayor a 20 no permite seguir escribiendo
            evt.consume();
        }
    }//GEN-LAST:event_entrada_noInteriorKeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //busca las coincidencias con el codigo postal
        if(valida.cpValido(entrada_cp.getText())){
            //obtener los datos del codigo postal si es valido
            try {
                direc = new ObtenerDireccion(entrada_cp.getText());
                if(!direc.estado.isEmpty()){
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
                Logger.getLogger(AltaEmisor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Ingrese un codigo postal valido", "Codigo postal no valido", JOptionPane.WARNING_MESSAGE);
            entrada_cp.requestFocusInWindow();    // Borde al tener foco;
            return;
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void nombre_userMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nombre_userMouseClicked

    }//GEN-LAST:event_nombre_userMouseClicked

    private void btn_historialSesionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_historialSesionesMouseClicked
        Object[] opciones = {"Aceptar", "Cancelar"};
        if (SwingUtilities.isLeftMouseButton(evt)) {//click izquierdo
            //dialogo que pregunta si desea confirmar salir
            int opcionSeleccionada = JOptionPane.showOptionDialog(
                    null,
                    "Se perderan los cambios, ¿Abandonar la pantalla de modificación?",
                    "Confirmación de volver",
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
                ConsultarPadres ventena = new ConsultarPadres();
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
                ConsultarPadresEdit ventana = new ConsultarPadresEdit();
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
            java.util.logging.Logger.getLogger(ModificarEmisor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ModificarEmisor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ModificarEmisor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ModificarEmisor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ModificarEmisor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fecha;
    private javax.swing.JPanel barra_nav;
    private javax.swing.JPanel btn_alumnos;
    private javax.swing.JPanel btn_cerrarSesion;
    private javax.swing.JPanel btn_emisor;
    private javax.swing.JPanel btn_estadisticas;
    private javax.swing.JPanel btn_facturas;
    private paneles.PanelRound btn_guardarDatos;
    private javax.swing.JPanel btn_historialSesiones;
    private javax.swing.JPanel btn_padres;
    private javax.swing.JPanel btn_salir;
    private javax.swing.JLabel cerrar_icon;
    private javax.swing.JPanel contenedor;
    private paneles.PanelRound contenedor_btn;
    private javax.swing.JPanel contenedor_menu;
    private javax.swing.JPanel datosPersonales_titulo;
    private javax.swing.JPanel datosfiscales_titulo;
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
    private javax.swing.JPanel fondo;
    private javax.swing.JLabel historial_lb;
    private javax.swing.JLabel hora_lb;
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
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
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
    private javax.swing.JPanel menu_alumnos;
    private javax.swing.JPanel menu_emisor;
    private javax.swing.JPanel menu_estadisticas;
    private javax.swing.JPanel menu_factura;
    private javax.swing.JPanel menu_padres;
    private javax.swing.JPanel menu_salir;
    private javax.swing.JPanel menu_user;
    private javax.swing.JPanel nombre_user;
    private javax.swing.JLabel nombres_lb;
    private javax.swing.JPanel registrarEmisor_Titulo;
    private javax.swing.JLabel text_guardarDatos;
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
    private javax.swing.JLabel txt_editarEmisor;
    private javax.swing.JLabel txt_eliminarAlumno;
    private javax.swing.JLabel txt_eliminarEmisor;
    private javax.swing.JLabel txt_eliminarPadres;
    private javax.swing.JLabel txt_emisor;
    private javax.swing.JLabel txt_estadisticas;
    private javax.swing.JLabel txt_estado;
    private javax.swing.JLabel txt_factura;
    private javax.swing.JLabel txt_facturasGeneradas;
    private javax.swing.JLabel txt_generarFcatura;
    private javax.swing.JLabel txt_ingresos;
    private javax.swing.JLabel txt_modificarAlumnos;
    private javax.swing.JLabel txt_modificarPadres;
    private javax.swing.JLabel txt_nombreUser;
    private javax.swing.JLabel txt_padres;
    private javax.swing.JLabel user_menuIcon;
    private javax.swing.JLabel user_menuIcon1;
    // End of variables declaration//GEN-END:variables
}
