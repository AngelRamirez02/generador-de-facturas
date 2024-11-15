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
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
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
import java.util.Calendar;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import padres.AltaPadres;
import padres.ConsultarPadre;
import padres.ConsultarPadre;
import padres.EliminarPadre;
import padres.ModificarPadre;
import sesiones.HistorialSesiones;

/**
 *
 * @author ar275
 */
public class ModificarAlumno extends javax.swing.JFrame {

    private String[] grados_preescolar = {"<seleccionar>", "Primero", "Segundo", "Tercero"};
    private String[] grados_primaria = {"<seleccionar>", "Primero", "Segundo", "Tercero", "Cuarto", "Quinto", "Sexto"};
    private String[] grados_secundaria = {"<seleccionar>", "Primero", "Segundo", "Tercero"};
    private int edad = 0;

    //RFC que se recibe
    String rfc_pdreOriginal;
    String curpOriginal;
    conexion cx = new conexion();

    private String usuario;//Nombre del usuario que inicia sesión
    LocalDate fechaInicioSesion;
    LocalTime horaInicioSesion;

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

    public ModificarAlumno() {
        initComponents();

        info_nombres.setVisible(false);
        infoFecha_lb.setVisible(false);
        info_curp.setVisible(false);

        infoIcon_lb.setIcon(new ImageIcon(info_img.getScaledInstance(infoIcon_lb2.getWidth(), infoIcon_lb2.getHeight(), Image.SCALE_SMOOTH)));
        infoIcon_lb2.setIcon(new ImageIcon(info_img.getScaledInstance(infoIcon_lb2.getWidth(), infoIcon_lb2.getHeight(), Image.SCALE_SMOOTH)));
        infoIcon_lb3.setIcon(new ImageIcon(info_img.getScaledInstance(infoIcon_lb.getWidth(), infoIcon_lb.getHeight(), Image.SCALE_SMOOTH)));

        icon_regresarlb.setIcon(new ImageIcon(img_regresar.getScaledInstance(icon_regresarlb.getWidth(), icon_regresarlb.getHeight(), Image.SCALE_SMOOTH)));

        //Imaganes para el menu del usuario
        Image icon_historial = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/icon_historial.png"));
        historial_lb.setIcon(new ImageIcon(icon_historial.getScaledInstance(historial_lb.getWidth(), historial_lb.getHeight(), Image.SCALE_SMOOTH)));
        Image icon_salirImg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/icon_salir.png"));
        icon_salir.setIcon(new ImageIcon(icon_salirImg.getScaledInstance(icon_salir.getWidth(), icon_salir.getHeight(), Image.SCALE_SMOOTH)));

        infoIcon_lb.setIcon(new ImageIcon(info_img.getScaledInstance(infoIcon_lb2.getWidth(), infoIcon_lb2.getHeight(), Image.SCALE_SMOOTH)));
        infoIcon_lb2.setIcon(new ImageIcon(info_img.getScaledInstance(infoIcon_lb2.getWidth(), infoIcon_lb2.getHeight(), Image.SCALE_SMOOTH)));

        //Menus ocultos por defecto
        menu_padres.setVisible(false);
        menu_alumnos.setVisible(false);
        menu_factura.setVisible(false);
        menu_estadisticas.setVisible(false);
        menu_emisor.setVisible(false);
        
        ///Imagen del logo de la escuela
        Image logo_img= Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/logo_escuela.png"));
        logo_lb.setIcon(new ImageIcon(logo_img.getScaledInstance(logo_lb.getWidth(), logo_lb.getHeight(), Image.SCALE_SMOOTH)));

        //icono de buscar
        Image img_buscar = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/btn_buscar3.png"));
        icon_buscar.setIcon(new ImageIcon(img_buscar.getScaledInstance(icon_buscar.getWidth(), icon_buscar.getHeight(), Image.SCALE_SMOOTH)));
        icon_buscarRfcPadre.setIcon(new ImageIcon(img_buscar.getScaledInstance(icon_buscarRfcPadre.getWidth(), icon_buscarRfcPadre.getHeight(), Image.SCALE_SMOOTH)));
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
        jLabel16 = new javax.swing.JLabel();
        txt_cerrarSesion1 = new javax.swing.JLabel();
        jSeparator17 = new javax.swing.JSeparator();
        cerrar_icon = new javax.swing.JLabel();
        menu_estadisticas = new javax.swing.JPanel();
        txt_facturasGeneradas = new javax.swing.JLabel();
        txt_ingresos = new javax.swing.JLabel();
        jSeparator15 = new javax.swing.JSeparator();
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
        menu_emisor = new javax.swing.JPanel();
        txt_editarEmisor = new javax.swing.JLabel();
        jSeparator14 = new javax.swing.JSeparator();
        txt_altaEmisor = new javax.swing.JLabel();
        jSeparator16 = new javax.swing.JSeparator();
        txt_eliminarEmisor = new javax.swing.JLabel();
        jSeparator18 = new javax.swing.JSeparator();
        txt_ConsultarEmisor = new javax.swing.JLabel();
        menu_factura = new javax.swing.JPanel();
        txt_generarFcatura = new javax.swing.JLabel();
        txt_consultarAlmnos1 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        contenedor = new javax.swing.JPanel();
        infoFecha_lb = new javax.swing.JLabel();
        info_nombres = new javax.swing.JLabel();
        lb_fechaNacimiento = new javax.swing.JLabel();
        entrada_apellidoMaterno = new javax.swing.JTextField();
        lb_apellidoMaternoAlumno = new javax.swing.JLabel();
        lb_apellidoPaternoAlumno = new javax.swing.JLabel();
        entrada_apellidoPaterno = new javax.swing.JTextField();
        lb_nombreAlumno = new javax.swing.JLabel();
        txt_registrarAlumno = new javax.swing.JLabel();
        entrada_fechaNacimiento = new com.toedter.calendar.JDateChooser();
        lb_datosPadre = new javax.swing.JLabel();
        lb_rfc = new javax.swing.JLabel();
        lb_datosAlumno = new javax.swing.JLabel();
        nombre_padre = new javax.swing.JTextField();
        lb_nombresPadres = new javax.swing.JLabel();
        lb_apellidoPaternoPadre = new javax.swing.JLabel();
        apellidoPaterno_padre = new javax.swing.JTextField();
        apellido_maternoPadre = new javax.swing.JTextField();
        lb_apellidoMaternoPadre = new javax.swing.JLabel();
        entrada_curp = new javax.swing.JTextField();
        lb_nivelEscolar = new javax.swing.JLabel();
        entrada_nivelEscolar = new javax.swing.JComboBox<>();
        lb_curp = new javax.swing.JLabel();
        lb_gradoEscolar = new javax.swing.JLabel();
        entrada_gradoEscolar = new javax.swing.JComboBox<>();
        infoIcon_lb = new javax.swing.JLabel();
        infoIcon_lb2 = new javax.swing.JLabel();
        infoIcon_lb3 = new javax.swing.JLabel();
        entrada_nombres = new javax.swing.JTextField();
        curp_busqueda = new javax.swing.JTextField();
        icon_buscar = new javax.swing.JLabel();
        lb_inicial = new javax.swing.JLabel();
        txt_curp = new javax.swing.JLabel();
        info_curp = new javax.swing.JLabel();
        btn_cancelar = new javax.swing.JButton();
        btn_guardarDatos = new javax.swing.JButton();
        logo_lb = new javax.swing.JLabel();
        rfc_padre = new javax.swing.JTextField();
        icon_buscarRfcPadre = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Instituto Andrés Manuel López Obrador - Modificar Alumno");
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

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_cerrarSesion.png"))); // NOI18N
        btn_cerrarSesion.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 40, 50));

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
        contenedor.setLayout(null);

        infoFecha_lb.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
        infoFecha_lb.setText("Ej: 02 dic 2003");
        contenedor.add(infoFecha_lb);
        infoFecha_lb.setBounds(190, 510, 80, 15);

        info_nombres.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
        info_nombres.setText("Ingrese los nombres separados por espacio");
        contenedor.add(info_nombres);
        info_nombres.setBounds(290, 380, 232, 20);

        lb_fechaNacimiento.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        lb_fechaNacimiento.setText("Fecha de nacimiento");
        contenedor.add(lb_fechaNacimiento);
        lb_fechaNacimiento.setBounds(50, 430, 180, 40);

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
        entrada_apellidoMaterno.setBounds(790, 350, 190, 30);

        lb_apellidoMaternoAlumno.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        lb_apellidoMaternoAlumno.setText("Apellido Materno");
        contenedor.add(lb_apellidoMaternoAlumno);
        lb_apellidoMaternoAlumno.setBounds(790, 320, 150, 30);

        lb_apellidoPaternoAlumno.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        lb_apellidoPaternoAlumno.setText("Apellido paterno");
        contenedor.add(lb_apellidoPaternoAlumno);
        lb_apellidoPaternoAlumno.setBounds(550, 320, 140, 30);

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
        entrada_apellidoPaterno.setBounds(550, 350, 190, 30);

        lb_nombreAlumno.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        lb_nombreAlumno.setText("Nombre (s)");
        contenedor.add(lb_nombreAlumno);
        lb_nombreAlumno.setBounds(300, 320, 110, 30);

        txt_registrarAlumno.setFont(new java.awt.Font("Roboto Light", 1, 48)); // NOI18N
        txt_registrarAlumno.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_registrarAlumno.setText("Modificar alumno");
        contenedor.add(txt_registrarAlumno);
        txt_registrarAlumno.setBounds(0, 30, 1050, 60);

        entrada_fechaNacimiento.setDateFormatString("dd MMM y");
        entrada_fechaNacimiento.setMaxSelectableDate(new java.util.Date(1735628468000L));
        entrada_fechaNacimiento.setMinSelectableDate(new java.util.Date(1230793268000L));
        entrada_fechaNacimiento.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                entrada_fechaNacimientoPropertyChange(evt);
            }
        });
        contenedor.add(entrada_fechaNacimiento);
        entrada_fechaNacimiento.setBounds(50, 470, 190, 30);

        lb_datosPadre.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        lb_datosPadre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_datosPadre.setText("Datos del padre o tutor");
        contenedor.add(lb_datosPadre);
        lb_datosPadre.setBounds(30, 160, 220, 30);

        lb_rfc.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        lb_rfc.setText("RFC");
        contenedor.add(lb_rfc);
        lb_rfc.setBounds(50, 190, 40, 30);

        lb_datosAlumno.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        lb_datosAlumno.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lb_datosAlumno.setText("Datos del alumno");
        contenedor.add(lb_datosAlumno);
        lb_datosAlumno.setBounds(50, 290, 200, 30);

        nombre_padre.setEditable(false);
        nombre_padre.setBackground(new java.awt.Color(255, 255, 255));
        nombre_padre.setFocusable(false);
        contenedor.add(nombre_padre);
        nombre_padre.setBounds(300, 220, 160, 30);

        lb_nombresPadres.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        lb_nombresPadres.setText("Nombre (s)");
        contenedor.add(lb_nombresPadres);
        lb_nombresPadres.setBounds(300, 190, 100, 30);

        lb_apellidoPaternoPadre.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        lb_apellidoPaternoPadre.setText("Apellido paterno");
        contenedor.add(lb_apellidoPaternoPadre);
        lb_apellidoPaternoPadre.setBounds(550, 190, 140, 30);

        apellidoPaterno_padre.setEditable(false);
        apellidoPaterno_padre.setBackground(new java.awt.Color(255, 255, 255));
        apellidoPaterno_padre.setFocusCycleRoot(true);
        apellidoPaterno_padre.setFocusable(false);
        contenedor.add(apellidoPaterno_padre);
        apellidoPaterno_padre.setBounds(550, 220, 160, 30);

        apellido_maternoPadre.setEditable(false);
        apellido_maternoPadre.setBackground(new java.awt.Color(255, 255, 255));
        apellido_maternoPadre.setFocusable(false);
        apellido_maternoPadre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apellido_maternoPadreActionPerformed(evt);
            }
        });
        contenedor.add(apellido_maternoPadre);
        apellido_maternoPadre.setBounds(790, 220, 160, 30);

        lb_apellidoMaternoPadre.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        lb_apellidoMaternoPadre.setText("Apellido materno");
        contenedor.add(lb_apellidoMaternoPadre);
        lb_apellidoMaternoPadre.setBounds(790, 190, 160, 30);

        entrada_curp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                entrada_curpFocusLost(evt);
            }
        });
        entrada_curp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                entrada_curpKeyTyped(evt);
            }
        });
        contenedor.add(entrada_curp);
        entrada_curp.setBounds(50, 350, 180, 30);

        lb_nivelEscolar.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        lb_nivelEscolar.setText("Nivel escolar");
        contenedor.add(lb_nivelEscolar);
        lb_nivelEscolar.setBounds(540, 440, 120, 30);

        entrada_nivelEscolar.setFont(new java.awt.Font("Roboto Light", 0, 18)); // NOI18N
        entrada_nivelEscolar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<seleccionar>", "Preescolar", "Primaria", "Secundaria" }));
        entrada_nivelEscolar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                entrada_nivelEscolarItemStateChanged(evt);
            }
        });
        entrada_nivelEscolar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entrada_nivelEscolarActionPerformed(evt);
            }
        });
        contenedor.add(entrada_nivelEscolar);
        entrada_nivelEscolar.setBounds(540, 470, 200, 30);

        lb_curp.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        lb_curp.setText("CURP");
        contenedor.add(lb_curp);
        lb_curp.setBounds(50, 320, 60, 30);

        lb_gradoEscolar.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        lb_gradoEscolar.setText("Grado escolar");
        contenedor.add(lb_gradoEscolar);
        lb_gradoEscolar.setBounds(790, 440, 160, 30);

        entrada_gradoEscolar.setFont(new java.awt.Font("Roboto Light", 0, 18)); // NOI18N
        entrada_gradoEscolar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<seleccionar>" }));
        entrada_gradoEscolar.setEnabled(false);
        entrada_gradoEscolar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                entrada_gradoEscolarItemStateChanged(evt);
            }
        });
        contenedor.add(entrada_gradoEscolar);
        entrada_gradoEscolar.setBounds(790, 470, 200, 30);

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
        infoIcon_lb.setBounds(230, 360, 20, 20);

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
        infoIcon_lb2.setBounds(480, 360, 20, 20);

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
        infoIcon_lb3.setBounds(250, 480, 20, 20);

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
        entrada_nombres.setBounds(290, 350, 190, 30);

        curp_busqueda.setColumns(1);
        curp_busqueda.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        curp_busqueda.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        curp_busqueda.setActionCommand("<Not Set>");
        curp_busqueda.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        curp_busqueda.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        curp_busqueda.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                curp_busquedaFocusLost(evt);
            }
        });
        curp_busqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                curp_busquedaActionPerformed(evt);
            }
        });
        curp_busqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                curp_busquedaKeyTyped(evt);
            }
        });
        contenedor.add(curp_busqueda);
        curp_busqueda.setBounds(140, 130, 710, 50);

        icon_buscar.setIcon(new javax.swing.ImageIcon("C:\\Users\\ar275\\Documents\\Generador de facturas\\generador-de-facturas\\generador_facturas\\src\\img\\btn_buscar.png")); // NOI18N
        icon_buscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        icon_buscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                icon_buscarMouseClicked(evt);
            }
        });
        contenedor.add(icon_buscar);
        icon_buscar.setBounds(850, 120, 70, 70);

        lb_inicial.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lb_inicial.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lb_inicial.setText("INGRESE LA CURP DEL ALUMNO A MODIFICAR");
        contenedor.add(lb_inicial);
        lb_inicial.setBounds(140, 110, 388, 22);

        txt_curp.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txt_curp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_curp.setText("jLabel1");
        contenedor.add(txt_curp);
        txt_curp.setBounds(10, 110, 1050, 50);

        info_curp.setText("La CURP debe ser de 18 caracteres");
        contenedor.add(info_curp);
        info_curp.setBounds(50, 380, 220, 20);

        btn_cancelar.setBackground(new java.awt.Color(102, 102, 102));
        btn_cancelar.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btn_cancelar.setForeground(new java.awt.Color(255, 255, 255));
        btn_cancelar.setText("Cancelar");
        btn_cancelar.setBorder(null);
        btn_cancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cancelar.setFocusPainted(false);
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });
        contenedor.add(btn_cancelar);
        btn_cancelar.setBounds(630, 550, 140, 40);

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
        btn_guardarDatos.setBounds(300, 550, 170, 40);

        logo_lb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo_escuela.png"))); // NOI18N
        logo_lb.setText("jLabel2");
        logo_lb.setMaximumSize(new java.awt.Dimension(400, 400));
        logo_lb.setMinimumSize(new java.awt.Dimension(400, 400));
        logo_lb.setPreferredSize(new java.awt.Dimension(400, 600));
        contenedor.add(logo_lb);
        logo_lb.setBounds(330, 230, 390, 370);

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
        contenedor.add(rfc_padre);
        rfc_padre.setBounds(40, 220, 170, 30);

        icon_buscarRfcPadre.setIcon(new javax.swing.ImageIcon("C:\\Users\\ar275\\Documents\\Generador de facturas\\generador-de-facturas\\generador_facturas\\src\\img\\btn_buscar.png")); // NOI18N
        icon_buscarRfcPadre.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        icon_buscarRfcPadre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                icon_buscarRfcPadreMouseClicked(evt);
            }
        });
        contenedor.add(icon_buscarRfcPadre);
        icon_buscarRfcPadre.setBounds(210, 210, 50, 50);

        fondo.add(contenedor);
        contenedor.setBounds(0, 0, 1050, 650);

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
    

    private void mostrarDatos(String curp) {
        try {
            //muestra los datos del alumno solicitado
            String consulta = "SELECT * FROM alumnos WHERE curp = ?";
            PreparedStatement ps = cx.conectar().prepareStatement(consulta);
            ps.setString(1, curp);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                this.curpOriginal=rs.getString("curp");//obtener la curp a modificar
                txt_curp.setText(rs.getString("curp"));
                mostrarCampos();
                //datos del padre
                obtenerDatosPadre(rs.getString("rfc_padre"));
                //datos del alumno
                entrada_curp.setText(rs.getString("curp"));
                entrada_nombres.setText(rs.getString("nombres"));
                entrada_apellidoPaterno.setText(rs.getString("apellido_paterno"));
                entrada_apellidoPaterno.setText(rs.getString("apellido_materno"));
                entrada_fechaNacimiento.setDate(rs.getDate("fecha_nacimiento"));
                entrada_nivelEscolar.setSelectedItem(rs.getString("nivel_escolaridad"));
                entrada_gradoEscolar.setSelectedItem(rs.getString("grado_escolar"));
            } else {
                JOptionPane.showMessageDialog(null, "La CURP del alumno que solicitó no se encuentra registrada", "CURP no encontrada", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultarAlumnos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void obtenerDatosPadre(String rfc) {
        try {
            //Seleccionar los datos del emisor
            String consulta = "SELECT * FROM padre_familia WHERE rfc = ?";
            PreparedStatement ps = cx.conectar().prepareStatement(consulta);
            ps.setString(1, rfc);
            ResultSet rs = ps.executeQuery();
            //muestra los datos del padre
            if (rs.next()) {
                rfc_padre.setText(rfc);
                nombre_padre.setText(rs.getString("nombres"));
                apellidoPaterno_padre.setText(rs.getString("apellido_paterno"));
                apellido_maternoPadre.setText(rs.getString("apellido_materno"));
            } else {
                JOptionPane.showMessageDialog(null, "El RFC que solicitó no se encuentra registrado", "RFC no encontrado", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultarPadre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void mostrarCampos() {
        //ocultar logo
        logo_lb.setVisible(false);
        //txt de curp
        txt_curp.setVisible(true);
        //campos de busqueda
        lb_inicial.setVisible(false);
        curp_busqueda.setVisible(false);
        icon_buscar.setVisible(false);
        //campos de entrada
        icon_buscarRfcPadre.setVisible(true);
        rfc_padre.setVisible(true);
        nombre_padre.setVisible(true);
        apellidoPaterno_padre.setVisible(true);
        apellido_maternoPadre.setVisible(true);
        entrada_curp.setVisible(true);
        entrada_nombres.setVisible(true);
        entrada_apellidoPaterno.setVisible(true);
        entrada_apellidoMaterno.setVisible(true);
        entrada_fechaNacimiento.setVisible(true);
        entrada_nivelEscolar.setVisible(true);
        entrada_gradoEscolar.setVisible(true);
        //etiquetas de los campos
        lb_datosPadre.setVisible(true);
        lb_datosAlumno.setVisible(true);
        lb_rfc.setVisible(true);
        lb_nombresPadres.setVisible(true);
        lb_apellidoPaternoPadre.setVisible(true);
        lb_apellidoMaternoPadre.setVisible(true);
        lb_curp.setVisible(true);
        lb_nombreAlumno.setVisible(true);
        lb_apellidoPaternoAlumno.setVisible(true);
        lb_apellidoMaternoAlumno.setVisible(true);
        lb_fechaNacimiento.setVisible(true);
        lb_nivelEscolar.setVisible(true);
        lb_gradoEscolar.setVisible(true);
        // etiquetas de info
        infoIcon_lb.setVisible(true);
        infoIcon_lb2.setVisible(true);
        infoIcon_lb3.setVisible(true);
        //botones
        btn_guardarDatos.setVisible(true);
        btn_cancelar.setVisible(true);
    }

    private void ocultarCampos() {
        //mostrar
        logo_lb.setVisible(true);
        //txt de curp
        txt_curp.setVisible(false);
        // campos de busqueda
        lb_inicial.setVisible(true);
        curp_busqueda.setVisible(true);
        icon_buscar.setVisible(true);
        // campos de entrada
        icon_buscarRfcPadre.setVisible(false);
        rfc_padre.setVisible(false);
        nombre_padre.setVisible(false);
        apellidoPaterno_padre.setVisible(false);
        apellido_maternoPadre.setVisible(false);
        entrada_curp.setVisible(false);
        entrada_nombres.setVisible(false);
        entrada_apellidoPaterno.setVisible(false);
        entrada_apellidoMaterno.setVisible(false);
        entrada_fechaNacimiento.setVisible(false);
        entrada_nivelEscolar.setVisible(false);
        entrada_gradoEscolar.setVisible(false);
        // etiquetas de los campos
        lb_datosPadre.setVisible(false);
        lb_datosAlumno.setVisible(false);
        lb_rfc.setVisible(false);
        lb_nombresPadres.setVisible(false);
        lb_apellidoPaternoPadre.setVisible(false);
        lb_apellidoMaternoPadre.setVisible(false);
        lb_curp.setVisible(false);
        lb_nombreAlumno.setVisible(false);
        lb_apellidoPaternoAlumno.setVisible(false);
        lb_apellidoMaternoAlumno.setVisible(false);
        lb_fechaNacimiento.setVisible(false);
        lb_nivelEscolar.setVisible(false);
        lb_gradoEscolar.setVisible(false);
        // etiquetas de info
        infoIcon_lb.setVisible(false);
        infoIcon_lb2.setVisible(false);
        infoIcon_lb3.setVisible(false);
        infoFecha_lb.setVisible(false);
        info_curp.setVisible(false);
        info_nombres.setVisible(false);
        // botones
        btn_guardarDatos.setVisible(false);
        btn_cancelar.setVisible(false);
    }


    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Object[] opciones = {"Aceptar", "Cancelar"};
        // Si existe información que no ha sido guardada
        // Mostrar diálogo que pregunta si desea confirmar la salida
        int opcionSeleccionada = JOptionPane.showOptionDialog(
                null,
                "¿Desea salir de la aplicación? No se guardaran los cambios",
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
        //JOptionPane.showMessageDialog(null,"La CURP no coincide con el nombre, apellidos o con la fecha de nacimiento del alumno", "CURP no valida", JOptionPane.WARNING_MESSAGE);
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
                if (rs.getString("curp").equals(curpOriginal)) {//es el mismo rfc que quiere actualizar
                    return false;
                }
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AltaEmisorPrim.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;//Retorna falso si no encuentra la curp
    }

    boolean esHijo() {
        if (!entrada_apellidoPaterno.getText().equals(apellidoPaterno_padre.getText())
                && !entrada_apellidoMaterno.getText().equals(apellido_maternoPadre.getText())
                && !entrada_apellidoPaterno.getText().equals(apellido_maternoPadre.getText())
                && !entrada_apellidoMaterno.getText().equals(apellidoPaterno_padre.getText())) {
            JOptionPane.showMessageDialog(null, "Ninguno de los apellidos del padre o madre no coinciden con los del hijo", "Apelidos sin coincidencia", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
    
    public void actualizarAlumno() {
        try {
            //Obtener todos los datos de entrada
            Date fecha_nacimiento = entrada_fechaNacimiento.getDate();
            java.sql.Date fecha_sql = new java.sql.Date(fecha_nacimiento.getTime());
            //Crear conexion a la base de datos
            //Preparar consulta para insertar los datos
            String query_alta = "UPDATE alumnos SET rfc_padre = ?, curp = ?, nombres = ?, apellido_paterno = ?,"
                    + "apellido_materno = ?, fecha_nacimiento = ?, nivel_escolaridad = ?, grado_escolar = ? WHERE curp = ?";
            PreparedStatement ps = cx.conectar().prepareStatement(query_alta);//Creacion de la consulta
            ps.setString(1, rfc_padre.getText().toUpperCase());
            ps.setString(2, entrada_curp.getText().toUpperCase());
            ps.setString(3, valida.formatearNombresApellidos(entrada_nombres.getText()));
            ps.setString(4, valida.formatearNombresApellidos(entrada_apellidoPaterno.getText()));
            ps.setString(5, valida.formatearNombresApellidos(entrada_apellidoMaterno.getText()));
            ps.setDate(6, fecha_sql);
            ps.setString(7, entrada_nivelEscolar.getSelectedItem().toString());
            ps.setString(8, entrada_gradoEscolar.getSelectedItem().toString());
            
            //Enviar el rfc del padre del alumno a modificar
            ps.setString(9, this.curpOriginal);
            
            //Verifica que se realizó el registro
            int filas_insertadas = ps.executeUpdate();
            if(filas_insertadas >0){
                JOptionPane.showMessageDialog(null,"Datos actualizados exitosamente", "Actualización exitosa", JOptionPane.INFORMATION_MESSAGE);
                return;
            }else{
                 JOptionPane.showMessageDialog(null,"No se encontró el registro de la CURP", "Error en la actualización", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Hubo un error al registrar los datos, intente otra vez", "Error en el registro", JOptionPane.WARNING_MESSAGE);;
        }
    }
    
    private void icon_regresarlbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_icon_regresarlbMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt)) {
            Object[] opciones = {"Aceptar", "Cancelar"};
            // Si existe información que no ha sido guardada
            // Mostrar diálogo que pregunta si desea confirmar la salida
            int opcionSeleccionada = JOptionPane.showOptionDialog(
                    null,
                    "¿Desea regresar?, No se guardaran los cambios",
                    "Confirmación de volver",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    opciones,
                    opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"

            // Manejar las opciones seleccionadas
            if (opcionSeleccionada == JOptionPane.YES_OPTION) {
                //Regresa al menu principal
                ModificarAlumno ventana = new ModificarAlumno();
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

    private void apellido_maternoPadreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apellido_maternoPadreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_apellido_maternoPadreActionPerformed

    private void infoIcon_lbMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoIcon_lbMouseEntered
        info_curp.setVisible(true);
    }//GEN-LAST:event_infoIcon_lbMouseEntered

    private void infoIcon_lbMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoIcon_lbMouseExited
        info_curp.setVisible(false);
    }//GEN-LAST:event_infoIcon_lbMouseExited

    private void infoIcon_lb2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoIcon_lb2MouseEntered
        info_nombres.setVisible(true);
    }//GEN-LAST:event_infoIcon_lb2MouseEntered

    private void infoIcon_lb2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoIcon_lb2MouseExited
        info_nombres.setVisible(false);
    }//GEN-LAST:event_infoIcon_lb2MouseExited

    private void infoIcon_lb3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoIcon_lb3MouseEntered
        infoFecha_lb.setVisible(true);
    }//GEN-LAST:event_infoIcon_lb3MouseEntered

    private void infoIcon_lb3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoIcon_lb3MouseExited
        infoFecha_lb.setVisible(false);
    }//GEN-LAST:event_infoIcon_lb3MouseExited

    public boolean rfcPadre_existente() {
        try {
            //Prepara la consulta para verificar si existe el RFC
            String consulta_rfc = "SELECT * FROM padre_familia WHERE rfc = ?";
            PreparedStatement ps = cx.conectar().prepareStatement(consulta_rfc);
            ps.setString(1, rfc_padre.getText().toUpperCase());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {//si encuentra un fila con el RFC quiere decir que ya existe
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AltaEmisorPrim.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;//Retorna falso si no encuentra el RFC
    }
    
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

    private void nombre_userMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nombre_userMouseClicked

    }//GEN-LAST:event_nombre_userMouseClicked

    private void btn_historialSesionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_historialSesionesMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt)){
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
                "¿Cerrar sesión y salir? No se guardaran los cambios", "Confirmación de salida", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
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
                "¿Cerrar sesión?, No se guardaran los cambios", "Confirmación de cerrar sesión", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
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
        if (SwingUtilities.isLeftMouseButton(evt)) {
            JOptionPane.showMessageDialog(null, "Se encuentra en esa sección", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            btn_alumnos.setBackground(colorbtnNoSeleccionado);
            menu_alumnos.setVisible(false);
            icon_item.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
            return;
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

    private void entrada_fechaNacimientoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_entrada_fechaNacimientoPropertyChange
        if (entrada_fechaNacimiento.getDate() != null) {//si la fecha de nacimiento no esta vacia
            LocalDate fechaActual = LocalDate.now();//obtiene la fecha actual
            //año de nacimiento
            int anioNacimiento = entrada_fechaNacimiento.getDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                    .getYear();
            //año actual
            int anioActual = fechaActual.getYear();
            //se calcula restando el año actual menos el año de nacimiento
            edad = anioActual - anioNacimiento;
            if (edad < 3) {//si tiene menos de 3 años
                JOptionPane.showMessageDialog(null, "El alumno no cumple con la edad suficiente para\n"
                        + "pertenecer a un nivel escolar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                entrada_fechaNacimiento.setDate(null);
                //desactiva para elegir nivel escolar
                entrada_nivelEscolar.setEnabled(false);
                return;
            }
            if (edad > 15) {//si tiene mas de 15 años 
                JOptionPane.showMessageDialog(null, "El alumno rebasa la edad para\n"
                        + "pertenecer a un nivel escolar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                entrada_fechaNacimiento.setDate(null);
                //desactiva para elegir grado escolar
                entrada_nivelEscolar.setEnabled(false);
                return;
            }
            //acativa para elegir grado escolar
            entrada_nivelEscolar.setEnabled(true);
            entrada_nivelEscolar.setSelectedIndex(0);
            System.out.println(edad);
        }
    }//GEN-LAST:event_entrada_fechaNacimientoPropertyChange

    private void entrada_gradoEscolarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_entrada_gradoEscolarItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {//si selecciona un item verificar           
            if (entrada_gradoEscolar.getSelectedIndex() == 0) {
                return;
            }
            //
            String nivel_escolar = entrada_nivelEscolar.getSelectedItem().toString();
            String grado_seleccionado = (String) evt.getItem();//pasar el item seleaccionado a string
            String gradoReal = gradoNivelYEdad(edad, nivel_escolar);
            //Si no ha seleccciona el grado correspondiente al real marca un mensaje de error
            if (!grado_seleccionado.equals(gradoReal)) {
                JOptionPane.showMessageDialog(null, "Los alumnos de: " + edad + " años "
                        + "deben pertenecer al " + gradoReal + " de " + nivel_escolar, "Advertencia", JOptionPane.ERROR_MESSAGE);
                entrada_gradoEscolar.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_entrada_gradoEscolarItemStateChanged

    private void entrada_nivelEscolarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_entrada_nivelEscolarItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {//si selecciona un item verificar
            String item_seleccionado = (String) evt.getItem();//pasar el item seleaccionado a string
            if(entrada_nivelEscolar.getSelectedIndex()==0){//caso cuando selecciona el item 0
                return;
            }
            if (edad >= 3 && edad <= 5 && !item_seleccionado.equals("Preescolar")) {
                JOptionPane.showMessageDialog(null, "Los alumnos de: " + edad + " años "
                        + "deben pertenecer al nivel de preescolar ", "Advertencia", JOptionPane.ERROR_MESSAGE);
                entrada_nivelEscolar.setSelectedIndex(0);
            }
            if(edad >= 6 && edad <= 11 && !item_seleccionado.equals("Primaria")){
                JOptionPane.showMessageDialog(null, "Los alumnos de: " + edad + " años "
                        + "deben pertenecer al nivel de primaria", "Advertencia", JOptionPane.ERROR_MESSAGE);
                entrada_nivelEscolar.setSelectedIndex(0);
            }
            if(edad >= 12 && edad <=15 && !item_seleccionado.equals("Secundaria")){
                JOptionPane.showMessageDialog(null, "Los alumnos de: " + edad + " años "
                        + "deben pertenecer al nivel de secundaria", "Advertencia", JOptionPane.ERROR_MESSAGE);
                entrada_nivelEscolar.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_entrada_nivelEscolarItemStateChanged

    private void entrada_nombresKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_entrada_nombresKeyTyped
        if(entrada_nombres.getText().length()>=80){
            JOptionPane.showMessageDialog(null, "Número maximo de cáracteres alcanzados", "Maximo alcanzado", JOptionPane.WARNING_MESSAGE);
            evt.consume();
        }
    }//GEN-LAST:event_entrada_nombresKeyTyped

    private void entrada_apellidoPaternoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_entrada_apellidoPaternoKeyTyped
        if(entrada_apellidoPaterno.getText().length()>=50){
            JOptionPane.showMessageDialog(null, "Número maximo de cáracteres alcanzados", "Maximo alcanzado", JOptionPane.WARNING_MESSAGE);
            evt.consume();
        }
    }//GEN-LAST:event_entrada_apellidoPaternoKeyTyped

    private void entrada_apellidoMaternoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_entrada_apellidoMaternoKeyTyped
        if(entrada_apellidoMaterno.getText().length()>50){
            JOptionPane.showMessageDialog(null, "Número maximo de cáracteres alcanzados", "Maximo alcanzado", JOptionPane.WARNING_MESSAGE);
            evt.consume();
        }
    }//GEN-LAST:event_entrada_apellidoMaternoKeyTyped

    private void entrada_nombresFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_entrada_nombresFocusLost
        entrada_nombres.setText(valida.formatearNombresApellidos(entrada_nombres.getText()));
    }//GEN-LAST:event_entrada_nombresFocusLost

    private void entrada_apellidoPaternoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_entrada_apellidoPaternoFocusLost
        entrada_apellidoPaterno.setText(valida.formatearNombresApellidos(entrada_apellidoPaterno.getText()));
    }//GEN-LAST:event_entrada_apellidoPaternoFocusLost

    private void entrada_apellidoMaternoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_entrada_apellidoMaternoFocusLost
        entrada_apellidoMaterno.setText(valida.formatearNombresApellidos(entrada_apellidoMaterno.getText()));
    }//GEN-LAST:event_entrada_apellidoMaternoFocusLost

    private void curp_busquedaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_curp_busquedaFocusLost
        curp_busqueda.setText(curp_busqueda.getText().toUpperCase());
    }//GEN-LAST:event_curp_busquedaFocusLost

    private void curp_busquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_curp_busquedaActionPerformed
        Validacion valida = new Validacion();
        if (curp_busqueda.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor ingrese una CURP para consultar", "CURP no ingresada", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (curp_busqueda.getText().length() < 18) {
            JOptionPane.showMessageDialog(null, "La CURP debe ser de 18 digitos", "CURP no valida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!valida.curp_valida(curp_busqueda.getText().toUpperCase())) {
            JOptionPane.showMessageDialog(null, "Por favor ingrese una CURP valido para consultar", "CURP no valida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        mostrarDatos(curp_busqueda.getText().toUpperCase());
        curp_busqueda.setText(curp_busqueda.getText().toUpperCase());
    }//GEN-LAST:event_curp_busquedaActionPerformed

    private void curp_busquedaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_curp_busquedaKeyTyped
        if (curp_busqueda.getText().length() >= 18 && evt.getKeyChar() != KeyEvent.VK_ENTER) {
            JOptionPane.showMessageDialog(null, "LA CURP debe ser de 18 digitos", "Advertencia", JOptionPane.WARNING_MESSAGE);
            evt.consume();
        }
    }//GEN-LAST:event_curp_busquedaKeyTyped

    private void icon_buscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_icon_buscarMouseClicked
        ///boton para buscar
        if(SwingUtilities.isLeftMouseButton(evt)){
            Validacion valida = new Validacion();
            if(curp_busqueda.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Por favor ingrese un RFC para consultar", "RFC no ingresado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(curp_busqueda.getText().length()<18){
                JOptionPane.showMessageDialog(null, "El RFC debe ser de 13 digitos", "RFC no valido", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(!valida.curp_valida(curp_busqueda.getText().toUpperCase())){
                JOptionPane.showMessageDialog(null, "Por favor ingrese un RFC valido para consultar", "RFC no valido", JOptionPane.WARNING_MESSAGE);
                return;
            }

            mostrarDatos(curp_busqueda.getText().toUpperCase());
            curp_busqueda.setText(curp_busqueda.getText().toUpperCase());
        }
    }//GEN-LAST:event_icon_buscarMouseClicked

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        Object[] opciones = {"Aceptar", "Cancelar"};
        // Si existe información que no ha sido guardada
        // Mostrar diálogo que pregunta si desea confirmar la salida
        int opcionSeleccionada = JOptionPane.showOptionDialog(
            null,
            "¿Seleccionar a otro alumno?",
            "Regresar",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE,
            null,
            opciones,
            opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"

        // Manejar las opciones seleccionadas
        if (opcionSeleccionada == JOptionPane.YES_OPTION) {
            ocultarCampos();
            curp_busqueda.setText("");
        } else {
            return;
        }
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void btn_guardarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarDatosActionPerformed

        if (!existeInfo()) {
            JOptionPane.showMessageDialog(null, "Ingrese todos los datos del padre de familia", "Todos los datos son obligatorios", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!rfcPadre_existente()) {
            JOptionPane.showMessageDialog(null, "El RFC del padre no se encuentra registrado", "RFC no registrado", JOptionPane.WARNING_MESSAGE);
            rfc_padre.requestFocusInWindow(); //hace focus al elemento
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
        if (!esHijo()) {
            entrada_apellidoPaterno.requestFocusInWindow();
            return;
        }
        if (!curp_valida()) {
            entrada_curp.requestFocusInWindow();
            return;
        }
        if (curp_existente()) {
            JOptionPane.showMessageDialog(null, "La CURP ya se encuentra registrada", "CURP existente", JOptionPane.WARNING_MESSAGE);
            entrada_curp.requestFocusInWindow();
            return;
        }
        if (entrada_nivelEscolar.getSelectedIndex() == 0) { //la opcion 0 es <seleccionar>  
            JOptionPane.showMessageDialog(null, "Selecione un nivel escolar", "Dato no seleccionado", JOptionPane.WARNING_MESSAGE);
            entrada_nivelEscolar.requestFocusInWindow();
            return;
        }
        if (entrada_gradoEscolar.getSelectedIndex() == 0) {//si no selecciona un opcion valida
            JOptionPane.showMessageDialog(null, "Selecione un grado escolar", "Dato no seleccionado", JOptionPane.WARNING_MESSAGE);
            entrada_gradoEscolar.requestFocusInWindow();
            return;
        }
        Object[] opciones = {"Aceptar", "Cancelar"};
        // Si existe información que no ha sido guardada
        // Mostrar diálogo que pregunta si desea confirmar la salida
        int opcionSeleccionada = JOptionPane.showOptionDialog(
                null,
                "¿Desea modificar los datos del alumno?",
                "Modificacion de datos",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                opciones,
                opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"

        // Manejar las opciones seleccionadas
        if (opcionSeleccionada == JOptionPane.YES_OPTION) {
            //actualizar datos
            actualizarAlumno();
            //regresar a la busqueda
            ocultarCampos();
            curp_busqueda.setText("");
        } else {
            // Evitar que la ventana se cierre
            return;
        }
    }//GEN-LAST:event_btn_guardarDatosActionPerformed

    private void entrada_curpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_entrada_curpKeyTyped
        if(entrada_curp.getText().length()>=18){
            evt.consume();
        }
    }//GEN-LAST:event_entrada_curpKeyTyped

    private void entrada_curpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_entrada_curpFocusLost
        entrada_curp.setText(entrada_curp.getText().toUpperCase());
    }//GEN-LAST:event_entrada_curpFocusLost

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

    private void icon_buscarRfcPadreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_icon_buscarRfcPadreMouseClicked
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
    }//GEN-LAST:event_icon_buscarRfcPadreMouseClicked

    private String gradoNivelYEdad(int edad, String nivelSeleccionado) {
        switch (nivelSeleccionado) {
            case "Preescolar":
                if (edad == 3) return "Primero";
                if (edad == 4) return "Segundo";
                if (edad == 5) return "Tercero";
                break;
            case "Primaria":
                if (edad == 6) return "Primero";
                if (edad == 7) return "Segundo";
                if (edad == 8) return "Tercero";
                if (edad == 9) return "Cuarto";
                if (edad == 10) return "Quinto";
                if (edad == 11) return "Sexto";
                break;             
            case "Secundaria":
                if (edad==12) return "Primero";
                if (edad == 13) return "Segundo";
                if (edad == 14 || edad == 15) return "Tercero";
                break;
        }
        return null;
    }
    
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
            java.util.logging.Logger.getLogger(ModificarAlumno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ModificarAlumno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ModificarAlumno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ModificarAlumno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new ModificarAlumno().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fecha;
    private javax.swing.JTextField apellidoPaterno_padre;
    private javax.swing.JTextField apellido_maternoPadre;
    private javax.swing.JPanel barra_nav;
    private javax.swing.JPanel btn_alumnos;
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
    private javax.swing.JPanel contenedor;
    private javax.swing.JPanel contenedor_menu;
    private javax.swing.JTextField curp_busqueda;
    private javax.swing.JTextField entrada_apellidoMaterno;
    private javax.swing.JTextField entrada_apellidoPaterno;
    private javax.swing.JTextField entrada_curp;
    private com.toedter.calendar.JDateChooser entrada_fechaNacimiento;
    private javax.swing.JComboBox<String> entrada_gradoEscolar;
    private javax.swing.JComboBox<String> entrada_nivelEscolar;
    private javax.swing.JTextField entrada_nombres;
    private javax.swing.JPanel fondo;
    private javax.swing.JLabel historial_lb;
    private javax.swing.JLabel hora_lb;
    private javax.swing.JLabel icon_buscar;
    private javax.swing.JLabel icon_buscarRfcPadre;
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
    private javax.swing.JLabel info_curp;
    private javax.swing.JLabel info_nombres;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JLabel lb_apellidoMaternoAlumno;
    private javax.swing.JLabel lb_apellidoMaternoPadre;
    private javax.swing.JLabel lb_apellidoPaternoAlumno;
    private javax.swing.JLabel lb_apellidoPaternoPadre;
    private javax.swing.JLabel lb_curp;
    private javax.swing.JLabel lb_datosAlumno;
    private javax.swing.JLabel lb_datosPadre;
    private javax.swing.JLabel lb_fechaNacimiento;
    private javax.swing.JLabel lb_gradoEscolar;
    private javax.swing.JLabel lb_inicial;
    private javax.swing.JLabel lb_nivelEscolar;
    private javax.swing.JLabel lb_nombreAlumno;
    private javax.swing.JLabel lb_nombresPadres;
    private javax.swing.JLabel lb_rfc;
    private javax.swing.JLabel logo_lb;
    private javax.swing.JPanel menu_alumnos;
    private javax.swing.JPanel menu_emisor;
    private javax.swing.JPanel menu_estadisticas;
    private javax.swing.JPanel menu_factura;
    private javax.swing.JPanel menu_padres;
    private javax.swing.JPanel menu_salir;
    private javax.swing.JPanel menu_user;
    private javax.swing.JTextField nombre_padre;
    private javax.swing.JPanel nombre_user;
    private javax.swing.JTextField rfc_padre;
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
    private javax.swing.JLabel txt_curp;
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
    private javax.swing.JLabel txt_registrarAlumno;
    private javax.swing.JLabel user_menuIcon;
    private javax.swing.JLabel user_menuIcon1;
    // End of variables declaration//GEN-END:variables
}
