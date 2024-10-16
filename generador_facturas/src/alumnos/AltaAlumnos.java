/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package alumnos;


import emisor.*;
import conexion.conexion;
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
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import login.login_window;
import validacion.Validacion;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import padres.AltaPadres;
import padres.EliminarPadre;
import padres.ModificarPadre;

/**
 *
 * @author ar275
 */
public class AltaAlumnos extends javax.swing.JFrame {

    private String[] grados_preescolar = {"<seleccionar>","Primero","Segundo","Tercero"}; 
    private String[] grados_primaria = {"<seleccionar>","Primero","Segundo","Tercero","Cuarto","Quinto","Sexto"}; 
    private String[] grados_secundaria = {"<seleccionar>","Primero","Segundo","Tercero"}; 
    
    conexion cx = new conexion();
    private String usuario;//Nombre del usuario que inicia sesión
    Validacion valida = new Validacion();//objeto para valdicar los datos
    //Colores para los botones seleccionados y no
    Color colorbtnSeleccionado = Color.decode("#A91E1F");
    Color colorbtnNoSeleccionado = Color.decode("#C94545");
    //Iconos de item para menu no selccionado
    Image icon_img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/icon_itemMenu.png"));
     //Imagen para menu selccionado
    Image icon_seleccionado = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/icon_itemSeleccionado.png"));
    Image info_img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/icon_info.png"));
    
    Image img_regresar = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/icon_regresar.png"));
    
    String[] tipos = {"Hola","Adios","XD"};
     public AltaAlumnos() {
        initComponents();
        
        info_nombre.setVisible(false);
        infoFecha_lb.setVisible(false);
        
        infoIcon_lb.setIcon(new ImageIcon(info_img.getScaledInstance(infoIcon_lb2.getWidth(), infoIcon_lb2.getHeight(), Image.SCALE_SMOOTH)));
        infoIcon_lb2.setIcon(new ImageIcon(info_img.getScaledInstance(infoIcon_lb2.getWidth(), infoIcon_lb2.getHeight(), Image.SCALE_SMOOTH)));
        infoIcon_lb3.setIcon(new ImageIcon(info_img.getScaledInstance(infoIcon_lb.getWidth(), infoIcon_lb.getHeight(), Image.SCALE_SMOOTH)));
     
        
        icon_regresarlb.setIcon(new ImageIcon(img_regresar.getScaledInstance(icon_regresarlb.getWidth(), icon_regresarlb.getHeight(), Image.SCALE_SMOOTH)));
        
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
        cargarTipo();
        autoCompletar();
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
        icon_regresarlb = new javax.swing.JLabel();
        menu_alumnos = new javax.swing.JPanel();
        txt_altaAlumnos = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        txt_consultarAlmnos = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        txt_modificarAlumnos = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        menu_salir = new javax.swing.JPanel();
        nombre_user = new javax.swing.JPanel();
        user_menuIcon1 = new javax.swing.JLabel();
        txt_nombreUser = new javax.swing.JLabel();
        cerrar_icon = new javax.swing.JLabel();
        btn_salir = new javax.swing.JPanel();
        icon_salir = new javax.swing.JLabel();
        text_salir = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        btn_cerrarSesion = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txt_cerrarSesion = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        menu_padres = new javax.swing.JPanel();
        txt_altaPadres = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        txt_consultarPadres = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        txt_modificarPadres = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        txt_eliminarPadres = new javax.swing.JLabel();
        menu_factura = new javax.swing.JPanel();
        txt_generarFcatura = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        txt_consultarAlmnos1 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        txt_modificarAlumnos1 = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
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
        contenedor = new javax.swing.JPanel();
        infoFecha_lb = new javax.swing.JLabel();
        info_nombre = new javax.swing.JLabel();
        btn_guardarDatos = new paneles.PanelRound();
        contenedor_btn = new paneles.PanelRound();
        text_guardarDatos = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        entrada_apellidoMaterno = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        entrada_apellidoPaterno = new javax.swing.JTextField();
        nombres_lb = new javax.swing.JLabel();
        txt_registrarAlumno = new javax.swing.JLabel();
        entrada_fechaNacimiento = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        nombre_padre = new javax.swing.JTextField();
        txt_nombrePadre = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        apellidoPaterno_padre = new javax.swing.JTextField();
        apellido_maternoPadre = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        entrada_nombres = new javax.swing.JTextPane();
        entrada_curp = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        entrada_nivelEscolar = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        entrada_gradoEscolar = new javax.swing.JComboBox<>();
        infoIcon_lb = new javax.swing.JLabel();
        infoIcon_lb2 = new javax.swing.JLabel();
        infoIcon_lb3 = new javax.swing.JLabel();
        rfc_padre = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Instituto Andrés Manuel López Obrador - Registrar alumnos");
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
        btn_emisor.setBounds(520, 40, 90, 30);

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

        menu_alumnos.setBackground(new java.awt.Color(198, 54, 55));
        menu_alumnos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_altaAlumnos.setBackground(new java.awt.Color(255, 255, 255));
        txt_altaAlumnos.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_altaAlumnos.setForeground(new java.awt.Color(255, 255, 255));
        txt_altaAlumnos.setText("Dar de alta Alumnos");
        txt_altaAlumnos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_alumnos.add(txt_altaAlumnos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 190, 40));
        menu_alumnos.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 150, 10));

        txt_consultarAlmnos.setBackground(new java.awt.Color(255, 255, 255));
        txt_consultarAlmnos.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_consultarAlmnos.setForeground(new java.awt.Color(255, 255, 255));
        txt_consultarAlmnos.setText("Consultar Alumnos");
        txt_consultarAlmnos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_alumnos.add(txt_consultarAlmnos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 190, 40));
        menu_alumnos.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 150, 10));

        txt_modificarAlumnos.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_modificarAlumnos.setForeground(new java.awt.Color(255, 255, 255));
        txt_modificarAlumnos.setText("Modificar Alumnos");
        txt_modificarAlumnos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_alumnos.add(txt_modificarAlumnos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 190, 40));
        menu_alumnos.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 150, 10));

        jLabel1.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Eliminar Alumnos");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_alumnos.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 190, 40));

        fondo.add(menu_alumnos);
        menu_alumnos.setBounds(200, 100, 200, 160);

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

        cerrar_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/x_menuUser.png"))); // NOI18N
        cerrar_icon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cerrar_icon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cerrar_iconMouseClicked(evt);
            }
        });
        nombre_user.add(cerrar_icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 0, -1, 30));

        menu_salir.add(nombre_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 210, -1));

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
        btn_salir.add(icon_salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 5, 50, 45));

        text_salir.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        text_salir.setForeground(new java.awt.Color(255, 255, 255));
        text_salir.setText("Salir");
        btn_salir.add(text_salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(61, 0, 90, 50));
        btn_salir.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 140, 10));
        btn_salir.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 140, 10));

        menu_salir.add(btn_salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 180, 50));

        btn_cerrarSesion.setBackground(new java.awt.Color(198, 54, 55));
        btn_cerrarSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cerrarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_cerrarSesionMouseClicked(evt);
            }
        });
        btn_cerrarSesion.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_cerrarSesion.png"))); // NOI18N
        btn_cerrarSesion.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 40, 50));

        txt_cerrarSesion.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_cerrarSesion.setForeground(new java.awt.Color(255, 255, 255));
        txt_cerrarSesion.setText("Cerrar sesión");
        btn_cerrarSesion.add(txt_cerrarSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 0, 120, 50));
        btn_cerrarSesion.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 140, 10));
        btn_cerrarSesion.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 47, 140, 10));

        menu_salir.add(btn_cerrarSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 135, 180, 50));

        fondo.add(menu_salir);
        menu_salir.setBounds(840, 100, 210, 190);

        menu_padres.setBackground(new java.awt.Color(198, 54, 55));
        menu_padres.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu_padresMouseClicked(evt);
            }
        });
        menu_padres.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_altaPadres.setBackground(new java.awt.Color(255, 255, 255));
        txt_altaPadres.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_altaPadres.setForeground(new java.awt.Color(255, 255, 255));
        txt_altaPadres.setText("Dar de alta padres");
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
        txt_consultarPadres.setText("Consultar Padres");
        txt_consultarPadres.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_padres.add(txt_consultarPadres, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 190, 40));
        menu_padres.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 150, 10));

        txt_modificarPadres.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_modificarPadres.setForeground(new java.awt.Color(255, 255, 255));
        txt_modificarPadres.setText("Modificar Padres");
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
        txt_eliminarPadres.setText("Eliminar Padres");
        txt_eliminarPadres.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txt_eliminarPadres.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_eliminarPadresMouseClicked(evt);
            }
        });
        menu_padres.add(txt_eliminarPadres, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 190, 40));

        fondo.add(menu_padres);
        menu_padres.setBounds(0, 100, 200, 160);

        menu_factura.setBackground(new java.awt.Color(198, 54, 55));
        menu_factura.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menu_factura.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_generarFcatura.setBackground(new java.awt.Color(255, 255, 255));
        txt_generarFcatura.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_generarFcatura.setForeground(new java.awt.Color(255, 255, 255));
        txt_generarFcatura.setText("Generar factura");
        txt_generarFcatura.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_factura.add(txt_generarFcatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 190, 40));
        menu_factura.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 150, 10));

        txt_consultarAlmnos1.setBackground(new java.awt.Color(255, 255, 255));
        txt_consultarAlmnos1.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_consultarAlmnos1.setForeground(new java.awt.Color(255, 255, 255));
        txt_consultarAlmnos1.setText("Opcion");
        txt_consultarAlmnos1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_factura.add(txt_consultarAlmnos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 190, 40));
        menu_factura.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 150, 10));

        txt_modificarAlumnos1.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_modificarAlumnos1.setForeground(new java.awt.Color(255, 255, 255));
        txt_modificarAlumnos1.setText("Opcion");
        txt_modificarAlumnos1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_factura.add(txt_modificarAlumnos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 190, 40));
        menu_factura.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 150, 10));

        jLabel2.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Opcion");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_factura.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 190, 40));

        fondo.add(menu_factura);
        menu_factura.setBounds(400, 100, 200, 160);

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
        menu_emisor.add(txt_editarEmisor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 190, 40));
        menu_emisor.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 150, 10));

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
        menu_emisor.add(txt_altaEmisor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 190, 40));
        menu_emisor.add(jSeparator16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 150, 10));

        txt_eliminarEmisor.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_eliminarEmisor.setForeground(new java.awt.Color(255, 255, 255));
        txt_eliminarEmisor.setText("Eliminar emisor");
        txt_eliminarEmisor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txt_eliminarEmisor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_eliminarEmisorMouseClicked(evt);
            }
        });
        menu_emisor.add(txt_eliminarEmisor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 190, 40));

        fondo.add(menu_emisor);
        menu_emisor.setBounds(800, 100, 200, 130);

        contenedor.setBackground(new java.awt.Color(255, 255, 255));
        contenedor.setLayout(null);

        infoFecha_lb.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
        infoFecha_lb.setText("Ej: 02 dic 2003");
        contenedor.add(infoFecha_lb);
        infoFecha_lb.setBounds(190, 500, 80, 15);

        info_nombre.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
        info_nombre.setText("Ingrese los nombres separados por espacio");
        contenedor.add(info_nombre);
        info_nombre.setBounds(280, 370, 232, 20);

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
        text_guardarDatos.setText("Guardar datos del alumno");
        text_guardarDatos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        contenedor_btn.add(text_guardarDatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 40));

        btn_guardarDatos.add(contenedor_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 2, 240, 40));

        contenedor.add(btn_guardarDatos);
        btn_guardarDatos.setBounds(410, 550, 245, 45);

        jLabel6.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel6.setText("Fecha de nacimiento");
        contenedor.add(jLabel6);
        jLabel6.setBounds(50, 420, 180, 40);
        contenedor.add(entrada_apellidoMaterno);
        entrada_apellidoMaterno.setBounds(790, 340, 190, 30);

        jLabel5.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel5.setText("Apellido Materno");
        contenedor.add(jLabel5);
        jLabel5.setBounds(790, 310, 150, 30);

        jLabel3.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel3.setText("Apellido peterno");
        contenedor.add(jLabel3);
        jLabel3.setBounds(550, 310, 140, 30);
        contenedor.add(entrada_apellidoPaterno);
        entrada_apellidoPaterno.setBounds(550, 340, 190, 30);

        nombres_lb.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        nombres_lb.setText("Nombre (s)");
        contenedor.add(nombres_lb);
        nombres_lb.setBounds(300, 310, 110, 30);

        txt_registrarAlumno.setFont(new java.awt.Font("Roboto Light", 1, 48)); // NOI18N
        txt_registrarAlumno.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_registrarAlumno.setText("Registrar alumno");
        contenedor.add(txt_registrarAlumno);
        txt_registrarAlumno.setBounds(200, 30, 630, 60);

        entrada_fechaNacimiento.setDateFormatString("dd MMM y");
        entrada_fechaNacimiento.setMaxSelectableDate(new java.util.Date(1735628468000L));
        entrada_fechaNacimiento.setMinSelectableDate(new java.util.Date(-315593932000L));
        contenedor.add(entrada_fechaNacimiento);
        entrada_fechaNacimiento.setBounds(50, 460, 190, 30);

        jLabel12.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Datos del padre o tutor");
        contenedor.add(jLabel12);
        jLabel12.setBounds(30, 120, 220, 30);

        jLabel8.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel8.setText("RFC");
        contenedor.add(jLabel8);
        jLabel8.setBounds(50, 160, 40, 30);

        jLabel11.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("Datos del alumno");
        contenedor.add(jLabel11);
        jLabel11.setBounds(50, 280, 200, 30);

        nombre_padre.setEditable(false);
        nombre_padre.setBackground(new java.awt.Color(255, 255, 255));
        nombre_padre.setEnabled(false);
        contenedor.add(nombre_padre);
        nombre_padre.setBounds(300, 190, 160, 30);

        txt_nombrePadre.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        txt_nombrePadre.setText("Nombre (s)");
        contenedor.add(txt_nombrePadre);
        txt_nombrePadre.setBounds(300, 160, 100, 30);

        jLabel13.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel13.setText("Apellido paterno");
        contenedor.add(jLabel13);
        jLabel13.setBounds(550, 160, 140, 30);

        apellidoPaterno_padre.setEditable(false);
        apellidoPaterno_padre.setEnabled(false);
        contenedor.add(apellidoPaterno_padre);
        apellidoPaterno_padre.setBounds(550, 190, 160, 30);

        apellido_maternoPadre.setEditable(false);
        apellido_maternoPadre.setEnabled(false);
        apellido_maternoPadre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apellido_maternoPadreActionPerformed(evt);
            }
        });
        contenedor.add(apellido_maternoPadre);
        apellido_maternoPadre.setBounds(790, 190, 160, 30);

        jLabel14.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel14.setText("Apellido materno");
        contenedor.add(jLabel14);
        jLabel14.setBounds(790, 160, 160, 30);

        entrada_nombres.setFocusTraversalPolicyProvider(true);
        jScrollPane1.setViewportView(entrada_nombres);

        contenedor.add(jScrollPane1);
        jScrollPane1.setBounds(300, 340, 190, 30);
        contenedor.add(entrada_curp);
        entrada_curp.setBounds(50, 340, 180, 30);

        jLabel9.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel9.setText("Nivel escolar");
        contenedor.add(jLabel9);
        jLabel9.setBounds(540, 430, 120, 30);

        entrada_nivelEscolar.setFont(new java.awt.Font("Roboto Light", 0, 18)); // NOI18N
        entrada_nivelEscolar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<seleccionar>", "Preescolar", "Primaria", "Secundaria" }));
        entrada_nivelEscolar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entrada_nivelEscolarActionPerformed(evt);
            }
        });
        contenedor.add(entrada_nivelEscolar);
        entrada_nivelEscolar.setBounds(540, 460, 200, 30);

        jLabel7.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel7.setText("CURP");
        contenedor.add(jLabel7);
        jLabel7.setBounds(50, 310, 60, 30);

        jLabel10.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel10.setText("Grado escolar");
        contenedor.add(jLabel10);
        jLabel10.setBounds(790, 430, 160, 30);

        entrada_gradoEscolar.setFont(new java.awt.Font("Roboto Light", 0, 18)); // NOI18N
        entrada_gradoEscolar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<seleccionar>" }));
        entrada_gradoEscolar.setEnabled(false);
        contenedor.add(entrada_gradoEscolar);
        entrada_gradoEscolar.setBounds(790, 460, 200, 30);

        infoIcon_lb.setText("jLabel11");
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
        infoIcon_lb.setBounds(490, 346, 20, 20);

        infoIcon_lb2.setText("jLabel11");
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
        infoIcon_lb2.setBounds(230, 346, 20, 20);

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
        infoIcon_lb3.setBounds(250, 463, 20, 20);

        rfc_padre.setEditable(true);
        rfc_padre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<seleccionar>" }));
        rfc_padre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rfc_padreActionPerformed(evt);
            }
        });
        contenedor.add(rfc_padre);
        rfc_padre.setBounds(40, 190, 170, 30);

        fondo.add(contenedor);
        contenedor.setBounds(0, 0, 1050, 650);

        getContentPane().add(fondo, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
     
    private void cargarTipo() {
        try {
            //Seleccionar los datos del emisor
            String consulta = "SELECT * FROM padre_familia";
            PreparedStatement ps = cx.conectar().prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            //Arreglo de datos
            while (rs.next()) {
                rfc_padre.addItem(rs.getString("rfc"));
            }
        } catch (SQLException ex) {
            //Logger.getLogger(EliminarPadre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void autoCompletar(){
        AutoCompleteDecorator.decorate(rfc_padre);
    }
    public void setUsuario(String usuario){
        this.usuario=usuario;
        txt_nombreUser.setText(usuario);
    }
    
    private void cerrar_iconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cerrar_iconMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt)){//cerrar el menu de salir
            menu_salir.setVisible(false);  
            menu_user.setBackground(colorbtnNoSeleccionado);
        }
    }//GEN-LAST:event_cerrar_iconMouseClicked

    private void btn_salirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salirMouseClicked
        Object[] opciones = {"Aceptar", "Cancelar"};
        if (SwingUtilities.isLeftMouseButton(evt)) {//click izquierdo
            //dialogo que pregunta si desea confirmar salir
            int opcionSeleccionada = JOptionPane.showOptionDialog(null,
                "¿Cerrar sesión y salir?", "Confirmación de salida", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
                null, opciones, opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"
            // Manejar las opciones seleccionadas
            if (opcionSeleccionada == JOptionPane.YES_OPTION) {
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
                login_window ventanaLogin = new login_window();
                ventanaLogin.setVisible(true);
                this.dispose();
            } else {
                return;
            }
        }
    }//GEN-LAST:event_btn_cerrarSesionMouseClicked

    private void nombre_userMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nombre_userMouseClicked
        
    }//GEN-LAST:event_nombre_userMouseClicked

    private void menu_padresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_padresMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_menu_padresMouseClicked

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
            // Cerrar la aplicación
            this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        } else {
            // Evitar que la ventana se cierre
            this.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_formWindowClosing

    public boolean existeInfo() {
        // Retorna true si al menos uno de los campos tiene información que no sea solo espacios
        return !(entrada_curp.getText().trim().isEmpty()
                && entrada_nombres.getText().trim().isEmpty()
                && entrada_apellidoPaterno.getText().trim().isEmpty()
                && entrada_apellidoMaterno.getText().trim().isEmpty()
                && entrada_fechaNacimiento.getDate() == null);
    }

    public boolean fechaValida(){
        return entrada_fechaNacimiento.getDate() != null;
    }

    private boolean curp_valida(){
        //valida la longitud de la curp 
        if(entrada_curp.getText().length()!=18){
            JOptionPane.showMessageDialog(null, "El tamaño de la CURP es de 18 caracteres obligatoriamnete", "CURP no valida", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        //Valida la curp coincide con los datos
        Date fechaNacimiento = entrada_fechaNacimiento.getDate();
        if(valida.verificarCURP(entrada_curp.getText(), entrada_nombres.getText(), entrada_apellidoPaterno.getText(), entrada_apellidoMaterno.getText(), fechaNacimiento)){
           //Valida que cumpla con el formato de una CURP
           String curpPattern = "^[A-Z]{4}\\d{6}[HM][A-Z]{2}[B-DF-HJ-NP-TV-Z]{3}[A-Z0-9][0-9]$";
           Pattern pattern = Pattern.compile(curpPattern);
           Matcher matcher = pattern.matcher(entrada_curp.getText());
            if(!matcher.matches()){//sino coincide con el formato retorna falso
                JOptionPane.showMessageDialog(null, "Ingrese una CURP valida", "CURP no valido", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            //si cumple con el formato retorna verdadero
            return true;
        }
        JOptionPane.showMessageDialog(null,"La CURP no coincide con el nombre, apellidos o con la fecha de nacimiento del alumno", "CURP no valida", JOptionPane.WARNING_MESSAGE);
        return false;
    }
    
   public boolean curp_existente(){
        try {
            //Prepara la consulta para verificar si existe el RFC
            String consulta_rfc = "SELECT * FROM alumnos WHERE curp = ?";
            PreparedStatement ps = cx.conectar().prepareStatement(consulta_rfc);
            ps.setString(1, entrada_curp.getText().toUpperCase());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){//si encuentra un fila con la curp quiere decir que ya existe
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AltaEmisorPrim.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;//Retorna falso si no encuentra la curp
    }
    

    public void altaAlumno() {
        try {
            //Obtener todos los datos de entrada
            Date fecha_nacimiento = entrada_fechaNacimiento.getDate();
            java.sql.Date fecha_sql = new java.sql.Date(fecha_nacimiento.getTime());
            //Crear conexion a la base de datos
            //Preparar consulta para insertar los datos
            String query_alta = "INSERT INTO alumnos"
                    + "(rfc_padre, curp, nombres, apellido_paterno, apellido_materno, fecha_nacimiento, nivel_escolaridad, grado_escolar)"
                    + "VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement ps = cx.conectar().prepareStatement(query_alta);//Creacion de la consulta
            ps.setString(1, rfc_padre.getSelectedItem().toString());
            ps.setString(2, entrada_curp.getText().toUpperCase());
            ps.setString(3, entrada_nombres.getText());
            ps.setString(4, entrada_apellidoPaterno.getText());
            ps.setString(5, entrada_apellidoMaterno.getText());
            ps.setDate(6, fecha_sql);
            ps.setString(7, entrada_nivelEscolar.getSelectedItem().toString());
            ps.setString(8, entrada_gradoEscolar.getSelectedItem().toString());
            
            //Verifica que se realizó el registro
            int filas_insertadas = ps.executeUpdate();
            if(filas_insertadas >0){
                JOptionPane.showMessageDialog(null,"Datos registrados exitosamente", "Registro existoso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }else{
                 JOptionPane.showMessageDialog(null,"Hubo un error al registrar los datos, intente otra vez", "Error en el registro", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Hubo un error al registrar los datos, intente otra vez", "Error en el registro", JOptionPane.WARNING_MESSAGE);;
        }
    }
    
    private void txt_altaEmisorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_altaEmisorMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt)){
                        Object[] opciones = {"Aceptar", "Cancelar"};
            // Si existe información que no ha sido guardada
            // Mostrar diálogo que pregunta si desea confirmar la salida
            int opcionSeleccionada = JOptionPane.showOptionDialog(
                    null,
                    "Se perderan los datos ingresados, ¿Abandonar pantalla de registro?",
                    "Confirmación de volver",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    opciones,
                    opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"

            // Manejar las opciones seleccionadas
            if (opcionSeleccionada == JOptionPane.YES_OPTION) {
                //Regresa al menu principal
                AltaEmisorMenu ventana = new AltaEmisorMenu();
                ventana.setUsuario(usuario);
                ventana.setVisible(true);
                this.dispose();
            } else {
                // Evitar que la ventana se cierre
                return;
            }
        }
    }//GEN-LAST:event_txt_altaEmisorMouseClicked

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
                ventana.setUsuario(usuario);
                ventana.setVisible(true);
                this.dispose();
            } else {
                // Evitar que la ventana se cierre
                return;
            }
        }
    }//GEN-LAST:event_icon_regresarlbMouseClicked

    private void txt_editarEmisorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_editarEmisorMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt)) {
            Object[] opciones = {"Aceptar", "Cancelar"};
            // Si existe información que no ha sido guardada
            // Mostrar diálogo que pregunta si desea confirmar la salida
            int opcionSeleccionada = JOptionPane.showOptionDialog(
                    null,
                    "Se perderan los datos ingresados, ¿Abandonar pantalla de registro?",
                    "Confirmación de volver",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    opciones,
                    opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"

            // Manejar las opciones seleccionadas
            if (opcionSeleccionada == JOptionPane.YES_OPTION) {
                //Regresa al menu principal
                ConsultarEmisor ventana = new ConsultarEmisor();
                ventana.setUsuario(usuario);
                ventana.setVisible(true);
                this.dispose();
            } else {
                // Evitar que la ventana se cierre
                return;
            }
        }
    }//GEN-LAST:event_txt_editarEmisorMouseClicked

    private void txt_eliminarEmisorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_eliminarEmisorMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt)) {
            Object[] opciones = {"Aceptar", "Cancelar"};
            // Si existe información que no ha sido guardada
            // Mostrar diálogo que pregunta si desea confirmar la salida
            int opcionSeleccionada = JOptionPane.showOptionDialog(
                    null,
                    "Se perderan los datos ingresados, ¿Abandonar pantalla de registro?",
                    "Confirmación de volver",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    opciones,
                    opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"

            // Manejar las opciones seleccionadas
            if (opcionSeleccionada == JOptionPane.YES_OPTION) {
                //Regresa al menu principal
                EliminarEmisor ventana = new EliminarEmisor();
                ventana.setUsuario(usuario);
                ventana.setVisible(true);
                this.dispose();
            } else {
                // Evitar que la ventana se cierre
                return;
            }
        }
    }//GEN-LAST:event_txt_eliminarEmisorMouseClicked

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

    private void apellido_maternoPadreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apellido_maternoPadreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_apellido_maternoPadreActionPerformed

    private void btn_guardarDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_guardarDatosMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt)) {//click izquierdo
            if(!existeInfo()){
                JOptionPane.showMessageDialog(null, "Ingrese todos los datos del padre de familia", "Todos los datos son obligatorios", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(!rfcPadre_existente()){
                JOptionPane.showMessageDialog(null, "El RFC del padre no se encuentra registrado", "RFC no registrado", JOptionPane.WARNING_MESSAGE);
                rfc_padre.requestFocusInWindow(); //hace focus al elemento
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
            if(!curp_valida()){
                entrada_curp.requestFocusInWindow();
                return;
            }
            if(curp_existente()){
                JOptionPane.showMessageDialog(null, "La CURP ya se encuentra registrada", "CURP existente", JOptionPane.WARNING_MESSAGE);
                entrada_curp.requestFocusInWindow();  
                return; 
            }
            if(entrada_nivelEscolar.getSelectedIndex()==0){ //la opcion 0 es <seleccionar>  
                JOptionPane.showMessageDialog(null, "Selecione un nivel escolar", "Dato no seleccionado", JOptionPane.WARNING_MESSAGE);
                entrada_nivelEscolar.requestFocusInWindow();   
                return;
            }
            if(entrada_gradoEscolar.getSelectedIndex()==0){//si no selecciona un opcion valida
                JOptionPane.showMessageDialog(null, "Selecione un grado escolar", "Dato no seleccionado", JOptionPane.WARNING_MESSAGE);
                entrada_gradoEscolar.requestFocusInWindow();    
                return;
            }
            altaAlumno();
        }
    }//GEN-LAST:event_btn_guardarDatosMouseClicked

    private void datosPadre(){
        
    }
    
    private void infoIcon_lbMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoIcon_lbMouseEntered
        
    }//GEN-LAST:event_infoIcon_lbMouseEntered

    private void infoIcon_lbMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoIcon_lbMouseExited
        
    }//GEN-LAST:event_infoIcon_lbMouseExited

    private void infoIcon_lb2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoIcon_lb2MouseEntered
        
    }//GEN-LAST:event_infoIcon_lb2MouseEntered

    private void infoIcon_lb2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoIcon_lb2MouseExited
        
    }//GEN-LAST:event_infoIcon_lb2MouseExited

    private void infoIcon_lb3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoIcon_lb3MouseEntered
        
    }//GEN-LAST:event_infoIcon_lb3MouseEntered

    private void infoIcon_lb3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoIcon_lb3MouseExited
        
    }//GEN-LAST:event_infoIcon_lb3MouseExited

    private void rfc_padreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rfc_padreActionPerformed
        try {
            //Seleccionar los datos del emisor
            String consulta = "SELECT * FROM padre_familia WHERE rfc = '"+rfc_padre.getSelectedItem().toString()+"'";
            PreparedStatement ps = cx.conectar().prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            //Arreglo de datos
            while (rs.next()) {
                nombre_padre.setText(rs.getString("nombres"));
                apellidoPaterno_padre.setText(rs.getString("apellido_paterno"));
                apellido_maternoPadre.setText(rs.getString("apellido_materno"));
            }

        } catch (SQLException ex) {
            System.out.println(ex);;
        }
    }//GEN-LAST:event_rfc_padreActionPerformed

        public boolean rfcPadre_existente(){
        try {
            //Prepara la consulta para verificar si existe el RFC
            String consulta_rfc = "SELECT * FROM padre_familia WHERE rfc = ?";
            PreparedStatement ps = cx.conectar().prepareStatement(consulta_rfc);
            ps.setString(1, rfc_padre.getSelectedItem().toString().toUpperCase());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){//si encuentra un fila con el RFC quiere decir que ya existe
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AltaEmisorPrim.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;//Retorna falso si no encuentra el RFC
    }
    
    private void txt_altaPadresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_altaPadresMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt)){
           AltaPadres ventana = new AltaPadres();
           ventana.setUsuario(usuario);
           ventana.setVisible(true);
           this.dispose();
       }
    }//GEN-LAST:event_txt_altaPadresMouseClicked

    private void txt_modificarPadresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_modificarPadresMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt)){
           ModificarPadre ventana = new ModificarPadre();
           ventana.setUsuario(usuario);
           ventana.setVisible(true);
           this.dispose();
       }
    }//GEN-LAST:event_txt_modificarPadresMouseClicked

    private void txt_eliminarPadresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_eliminarPadresMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt)){
           EliminarPadre ventana = new EliminarPadre();
           ventana.setUsuario(usuario);
           ventana.setVisible(true);
           this.dispose();
       }
    }//GEN-LAST:event_txt_eliminarPadresMouseClicked

    private void entrada_nivelEscolarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entrada_nivelEscolarActionPerformed
       //Las opciones de grado escolar solo se pueden seleccionar cuando se elige un nivel escolar
        if(entrada_nivelEscolar.getSelectedIndex()!=0){
            entrada_gradoEscolar.setEnabled(true);
        }else{
            entrada_gradoEscolar.setEnabled(false);
        }
        if(entrada_nivelEscolar.getSelectedIndex()==1){//selecciona preescolar
            entrada_gradoEscolar.removeAllItems();
            entrada_gradoEscolar.setModel(new DefaultComboBoxModel<>(grados_preescolar));
        }
        if(entrada_nivelEscolar.getSelectedIndex()==2){//selecciona preescolar
            entrada_gradoEscolar.removeAllItems();
            entrada_gradoEscolar.setModel(new DefaultComboBoxModel<>(grados_primaria));
        }
        if (entrada_nivelEscolar.getSelectedIndex() == 3) {//selecciona preescolar
            entrada_gradoEscolar.removeAllItems();
            entrada_gradoEscolar.setModel(new DefaultComboBoxModel<>(grados_secundaria));
        }
    }//GEN-LAST:event_entrada_nivelEscolarActionPerformed

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
            java.util.logging.Logger.getLogger(AltaAlumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AltaAlumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AltaAlumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AltaAlumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new AltaAlumnos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fecha;
    private javax.swing.JTextField apellidoPaterno_padre;
    private javax.swing.JTextField apellido_maternoPadre;
    private javax.swing.JPanel barra_nav;
    private javax.swing.JPanel btn_alumnos;
    private javax.swing.JPanel btn_cerrarSesion;
    private javax.swing.JPanel btn_emisor;
    private javax.swing.JPanel btn_estadisticas;
    private javax.swing.JPanel btn_facturas;
    private paneles.PanelRound btn_guardarDatos;
    private javax.swing.JPanel btn_padres;
    private javax.swing.JPanel btn_salir;
    private javax.swing.JLabel cerrar_icon;
    private javax.swing.JPanel contenedor;
    private paneles.PanelRound contenedor_btn;
    private javax.swing.JPanel contenedor_menu;
    private javax.swing.JTextField entrada_apellidoMaterno;
    private javax.swing.JTextField entrada_apellidoPaterno;
    private javax.swing.JTextField entrada_curp;
    private com.toedter.calendar.JDateChooser entrada_fechaNacimiento;
    private javax.swing.JComboBox<String> entrada_gradoEscolar;
    private javax.swing.JComboBox<String> entrada_nivelEscolar;
    private javax.swing.JTextPane entrada_nombres;
    private javax.swing.JPanel fondo;
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
    private javax.swing.JLabel info_nombre;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
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
    private javax.swing.JTextField nombre_padre;
    private javax.swing.JPanel nombre_user;
    private javax.swing.JLabel nombres_lb;
    private javax.swing.JComboBox<String> rfc_padre;
    private javax.swing.JLabel text_guardarDatos;
    private javax.swing.JLabel text_salir;
    private javax.swing.JLabel txt_altaAlumnos;
    private javax.swing.JLabel txt_altaEmisor;
    private javax.swing.JLabel txt_altaPadres;
    private javax.swing.JLabel txt_alumnos;
    private javax.swing.JLabel txt_cerrarSesion;
    private javax.swing.JLabel txt_consultarAlmnos;
    private javax.swing.JLabel txt_consultarAlmnos1;
    private javax.swing.JLabel txt_consultarPadres;
    private javax.swing.JLabel txt_editarEmisor;
    private javax.swing.JLabel txt_eliminarEmisor;
    private javax.swing.JLabel txt_eliminarPadres;
    private javax.swing.JLabel txt_emisor;
    private javax.swing.JLabel txt_estadisticas;
    private javax.swing.JLabel txt_factura;
    private javax.swing.JLabel txt_facturasGeneradas;
    private javax.swing.JLabel txt_generarFcatura;
    private javax.swing.JLabel txt_ingresos;
    private javax.swing.JLabel txt_modificarAlumnos;
    private javax.swing.JLabel txt_modificarAlumnos1;
    private javax.swing.JLabel txt_modificarPadres;
    private javax.swing.JLabel txt_nombrePadre;
    private javax.swing.JLabel txt_nombreUser;
    private javax.swing.JLabel txt_padres;
    private javax.swing.JLabel txt_registrarAlumno;
    private javax.swing.JLabel user_menuIcon;
    private javax.swing.JLabel user_menuIcon1;
    // End of variables declaration//GEN-END:variables
}
