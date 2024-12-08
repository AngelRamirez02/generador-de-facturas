/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package emisor;

import TablaPersonalizada.TablaPersonalizada;
import conexion.conexion;
import emisor.AltaEmisor;
import java.awt.Color;
import java.awt.Dimension;
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
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import login.login_window;
import menu.MenuPrincipal;
import padres.AltaPadres;
import padres.ConsultarPadre;
import padres.ConsultarPadre;
import padres.EliminarPadre;
import sesiones.HistorialSesiones;
import alumnos.AltaAlumnos;
import alumnos.ConsultarAlumnos;
import alumnos.ModificarAlumno;
import alumnos.EliminarAlumno;
import facturacion.GenerarFactura;
import java.awt.event.KeyEvent;
import validacion.Validacion;

/**
 *
 * @author ar275
 */
public class ConsultarEmisor extends javax.swing.JFrame {  
    
    conexion cx = new conexion();
    
    DefaultTableModel modelo;
    
    private String usuario;//Nombre del usuario que inicia sesión
    LocalDate fechaInicioSesion;
    LocalTime horaInicioSesion;
    
    //Colores para los botones seleccionados y no
    Color colorbtnSeleccionado = Color.decode("#A91E1F");
    Color colorbtnNoSeleccionado = Color.decode("#C94545");
    //Iconos de item para menu no selccionado
    Image icon_img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/icon_itemMenu.png"));
    //Imagen para menu selccionado
    Image icon_seleccionado = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/icon_itemSeleccionado.png"));
    Image img_regresar = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/icon_regresar.png"));
    
     public ConsultarEmisor() {
        initComponents();      
        
        //Menus ocultos por defecto
        menu_padres.setVisible(false);
        menu_alumnos.setVisible(false);
        menu_factura.setVisible(false);
        menu_estadisticas.setVisible(false);
        menu_emisor.setVisible(false);
        //Imagen del logo de la escuela
        Image logo_img= Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/logo_escuela.png"));
        logo_lb.setIcon(new ImageIcon(logo_img.getScaledInstance(logo_lb.getWidth(), logo_lb.getHeight(), Image.SCALE_SMOOTH)));
        
        //Iconos para botones de menu
        icon_item.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
        icon_item2.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
        icon_item3.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
        icon_item4.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
        icon_item5.setIcon(new ImageIcon(icon_img.getScaledInstance(icon_item.getWidth(), icon_item.getHeight(), Image.SCALE_SMOOTH)));
        contenedor_menu.setLocation(user_menuIcon.getLocation().x-650, contenedor_menu.getLocation().y);//centrar el contenedor   
        
        icon_regresarlb.setIcon(new ImageIcon(img_regresar.getScaledInstance(icon_regresarlb.getWidth(), icon_regresarlb.getHeight(), Image.SCALE_SMOOTH)));
        
        Image img_buscar = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/btn_buscar3.png"));
        icon_buscar.setIcon(new ImageIcon(img_buscar.getScaledInstance(icon_buscar.getWidth(), icon_buscar.getHeight(), Image.SCALE_SMOOTH)));
        //Imaganes para el menu del usuario
        Image icon_historial = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/icon_historial.png"));
        historial_lb.setIcon(new ImageIcon(icon_historial.getScaledInstance(historial_lb.getWidth(), historial_lb.getHeight(), Image.SCALE_SMOOTH)));
        Image icon_salirImg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/icon_salir.png"));
        icon_salir.setIcon(new ImageIcon(icon_salirImg.getScaledInstance(icon_salir.getWidth(), icon_salir.getHeight(), Image.SCALE_SMOOTH)));
        
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
        
        ocultarCampos();
        
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
        jLabel16 = new javax.swing.JLabel();
        txt_cerrarSesion1 = new javax.swing.JLabel();
        jSeparator17 = new javax.swing.JSeparator();
        cerrar_icon = new javax.swing.JLabel();
        icon_regresarlb = new javax.swing.JLabel();
        menu_factura = new javax.swing.JPanel();
        txt_generarFcatura = new javax.swing.JLabel();
        txt_consultarAlmnos1 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
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
        contenedor = new javax.swing.JPanel();
        txt_emisoresRegistrados = new javax.swing.JLabel();
        titulo_rfc = new javax.swing.JTextField();
        rfc_show = new javax.swing.JTextField();
        titulo_estado = new javax.swing.JTextField();
        titulo_apellidoPaterno = new javax.swing.JTextField();
        estado = new javax.swing.JTextField();
        titulo_apellidoMaterno = new javax.swing.JTextField();
        apellido_materno = new javax.swing.JTextField();
        titulo_nombres = new javax.swing.JTextField();
        nombres = new javax.swing.JTextField();
        titulo_regimen = new javax.swing.JTextField();
        titulo_correo = new javax.swing.JTextField();
        titulo_cp = new javax.swing.JTextField();
        titulo_fechaNacimiento = new javax.swing.JTextField();
        titulo_municipio = new javax.swing.JTextField();
        lb_campos = new javax.swing.JLabel();
        apellido_paterno = new javax.swing.JTextField();
        correo_electronico = new javax.swing.JTextField();
        codigo_postal = new javax.swing.JTextField();
        fecha_nacimiento = new javax.swing.JTextField();
        municipio = new javax.swing.JTextField();
        regimen = new javax.swing.JTextField();
        no_interior = new javax.swing.JTextField();
        titulo_colonia = new javax.swing.JTextField();
        titulo_noExterior = new javax.swing.JTextField();
        lb_inicial = new javax.swing.JLabel();
        titulo_noInterior = new javax.swing.JTextField();
        colonia = new javax.swing.JTextField();
        no_exterior = new javax.swing.JTextField();
        rfc_busqueda = new javax.swing.JTextField();
        icon_buscar = new javax.swing.JLabel();
        btn_cerrarConsulta = new javax.swing.JButton();
        logo_lb = new javax.swing.JLabel();
        txt_rfc = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Instituto Andrés Manuel López Obrador - Consultar Emisor");
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

        menu_factura.setBackground(new java.awt.Color(198, 54, 55));
        menu_factura.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menu_factura.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_generarFcatura.setBackground(new java.awt.Color(255, 255, 255));
        txt_generarFcatura.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_generarFcatura.setForeground(new java.awt.Color(255, 255, 255));
        txt_generarFcatura.setText("Generar factura");
        txt_generarFcatura.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txt_generarFcatura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_generarFcaturaMouseClicked(evt);
            }
        });
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

        contenedor.setBackground(new java.awt.Color(255, 255, 255));
        contenedor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_emisoresRegistrados.setFont(new java.awt.Font("Roboto Light", 1, 36)); // NOI18N
        txt_emisoresRegistrados.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_emisoresRegistrados.setText("CONSULTA DE EMISOR");
        contenedor.add(txt_emisoresRegistrados, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 1050, 50));

        titulo_rfc.setEditable(false);
        titulo_rfc.setBackground(new java.awt.Color(198, 54, 55));
        titulo_rfc.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        titulo_rfc.setForeground(new java.awt.Color(255, 255, 255));
        titulo_rfc.setText("  RFC");
        titulo_rfc.setBorder(null);
        titulo_rfc.setFocusable(false);
        contenedor.add(titulo_rfc, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 210, 230, 40));

        rfc_show.setEditable(false);
        rfc_show.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        rfc_show.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        rfc_show.setText("jTextField1");
        rfc_show.setBorder(null);
        rfc_show.setFocusable(false);
        contenedor.add(rfc_show, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 250, 230, 40));

        titulo_estado.setEditable(false);
        titulo_estado.setBackground(new java.awt.Color(198, 54, 55));
        titulo_estado.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        titulo_estado.setForeground(new java.awt.Color(255, 255, 255));
        titulo_estado.setText("   Estado");
        titulo_estado.setBorder(null);
        titulo_estado.setFocusable(false);
        contenedor.add(titulo_estado, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 300, 270, 40));

        titulo_apellidoPaterno.setEditable(false);
        titulo_apellidoPaterno.setBackground(new java.awt.Color(198, 54, 55));
        titulo_apellidoPaterno.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        titulo_apellidoPaterno.setForeground(new java.awt.Color(255, 255, 255));
        titulo_apellidoPaterno.setText("   Apellido Paterno");
        titulo_apellidoPaterno.setBorder(null);
        titulo_apellidoPaterno.setFocusable(false);
        contenedor.add(titulo_apellidoPaterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 250, 40));

        estado.setEditable(false);
        estado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        estado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        estado.setText("jTextField1");
        estado.setBorder(null);
        estado.setFocusable(false);
        contenedor.add(estado, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 340, 270, 40));

        titulo_apellidoMaterno.setEditable(false);
        titulo_apellidoMaterno.setBackground(new java.awt.Color(198, 54, 55));
        titulo_apellidoMaterno.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        titulo_apellidoMaterno.setForeground(new java.awt.Color(255, 255, 255));
        titulo_apellidoMaterno.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        titulo_apellidoMaterno.setText("    Apellido Materno");
        titulo_apellidoMaterno.setBorder(null);
        titulo_apellidoMaterno.setFocusable(false);
        titulo_apellidoMaterno.setMargin(new java.awt.Insets(10, 6, 2, 6));
        contenedor.add(titulo_apellidoMaterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 210, 230, 40));

        apellido_materno.setEditable(false);
        apellido_materno.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        apellido_materno.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        apellido_materno.setText("jTextField1");
        apellido_materno.setBorder(null);
        apellido_materno.setFocusable(false);
        contenedor.add(apellido_materno, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 250, 230, 40));

        titulo_nombres.setEditable(false);
        titulo_nombres.setBackground(new java.awt.Color(198, 54, 55));
        titulo_nombres.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        titulo_nombres.setForeground(new java.awt.Color(255, 255, 255));
        titulo_nombres.setText("  Nombre(s)");
        titulo_nombres.setBorder(null);
        titulo_nombres.setFocusable(false);
        contenedor.add(titulo_nombres, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 210, 280, 40));

        nombres.setEditable(false);
        nombres.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        nombres.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        nombres.setText("jTextField1");
        nombres.setBorder(null);
        nombres.setFocusable(false);
        contenedor.add(nombres, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 250, 280, 40));

        titulo_regimen.setEditable(false);
        titulo_regimen.setBackground(new java.awt.Color(198, 54, 55));
        titulo_regimen.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        titulo_regimen.setForeground(new java.awt.Color(255, 255, 255));
        titulo_regimen.setText("   Regimen fiscal");
        titulo_regimen.setBorder(null);
        titulo_regimen.setFocusable(false);
        contenedor.add(titulo_regimen, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 400, 330, 40));

        titulo_correo.setEditable(false);
        titulo_correo.setBackground(new java.awt.Color(198, 54, 55));
        titulo_correo.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        titulo_correo.setForeground(new java.awt.Color(255, 255, 255));
        titulo_correo.setText("   Correo electronico");
        titulo_correo.setBorder(null);
        titulo_correo.setFocusable(false);
        contenedor.add(titulo_correo, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 300, 330, 40));

        titulo_cp.setEditable(false);
        titulo_cp.setBackground(new java.awt.Color(198, 54, 55));
        titulo_cp.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        titulo_cp.setForeground(new java.awt.Color(255, 255, 255));
        titulo_cp.setText("   Codigo postal");
        titulo_cp.setBorder(null);
        titulo_cp.setFocusable(false);
        contenedor.add(titulo_cp, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 300, 180, 40));

        titulo_fechaNacimiento.setEditable(false);
        titulo_fechaNacimiento.setBackground(new java.awt.Color(198, 54, 55));
        titulo_fechaNacimiento.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        titulo_fechaNacimiento.setForeground(new java.awt.Color(255, 255, 255));
        titulo_fechaNacimiento.setText("   Fecha de nacimiento");
        titulo_fechaNacimiento.setBorder(null);
        titulo_fechaNacimiento.setFocusable(false);
        contenedor.add(titulo_fechaNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 210, 40));

        titulo_municipio.setEditable(false);
        titulo_municipio.setBackground(new java.awt.Color(198, 54, 55));
        titulo_municipio.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        titulo_municipio.setForeground(new java.awt.Color(255, 255, 255));
        titulo_municipio.setText("  Municipio");
        titulo_municipio.setBorder(null);
        titulo_municipio.setFocusable(false);
        contenedor.add(titulo_municipio, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 210, 40));

        lb_campos.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lb_campos.setText("Datos del emisor consultado:");
        contenedor.add(lb_campos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 350, 40));

        apellido_paterno.setEditable(false);
        apellido_paterno.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        apellido_paterno.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        apellido_paterno.setText("jTextField1");
        apellido_paterno.setBorder(null);
        apellido_paterno.setFocusable(false);
        contenedor.add(apellido_paterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 250, 40));

        correo_electronico.setEditable(false);
        correo_electronico.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        correo_electronico.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        correo_electronico.setText("jTextField1");
        correo_electronico.setBorder(null);
        correo_electronico.setFocusable(false);
        contenedor.add(correo_electronico, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 340, 330, 40));

        codigo_postal.setEditable(false);
        codigo_postal.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        codigo_postal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        codigo_postal.setText("jTextField1");
        codigo_postal.setBorder(null);
        codigo_postal.setFocusable(false);
        contenedor.add(codigo_postal, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 340, 180, 40));

        fecha_nacimiento.setEditable(false);
        fecha_nacimiento.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        fecha_nacimiento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fecha_nacimiento.setText("jTextField1");
        fecha_nacimiento.setBorder(null);
        fecha_nacimiento.setFocusable(false);
        contenedor.add(fecha_nacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 210, 40));

        municipio.setEditable(false);
        municipio.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        municipio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        municipio.setText("jTextField1");
        municipio.setBorder(null);
        municipio.setFocusable(false);
        contenedor.add(municipio, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 440, 210, 40));

        regimen.setEditable(false);
        regimen.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        regimen.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        regimen.setText("jTextField1");
        regimen.setBorder(null);
        regimen.setFocusable(false);
        regimen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regimenActionPerformed(evt);
            }
        });
        contenedor.add(regimen, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 440, 330, 40));

        no_interior.setEditable(false);
        no_interior.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        no_interior.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        no_interior.setText("jTextField1");
        no_interior.setBorder(null);
        no_interior.setFocusable(false);
        contenedor.add(no_interior, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 440, 130, 40));

        titulo_colonia.setEditable(false);
        titulo_colonia.setBackground(new java.awt.Color(198, 54, 55));
        titulo_colonia.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        titulo_colonia.setForeground(new java.awt.Color(255, 255, 255));
        titulo_colonia.setText("   Colonia");
        titulo_colonia.setBorder(null);
        titulo_colonia.setFocusable(false);
        contenedor.add(titulo_colonia, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 400, 200, 40));

        titulo_noExterior.setEditable(false);
        titulo_noExterior.setBackground(new java.awt.Color(198, 54, 55));
        titulo_noExterior.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        titulo_noExterior.setForeground(new java.awt.Color(255, 255, 255));
        titulo_noExterior.setText("   No Exterior");
        titulo_noExterior.setBorder(null);
        titulo_noExterior.setFocusable(false);
        contenedor.add(titulo_noExterior, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 400, 130, 40));

        lb_inicial.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lb_inicial.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lb_inicial.setText("INGRESE EL RFC DEL PADRE A CONSULTAR");
        contenedor.add(lb_inicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 550, 30));

        titulo_noInterior.setEditable(false);
        titulo_noInterior.setBackground(new java.awt.Color(198, 54, 55));
        titulo_noInterior.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        titulo_noInterior.setForeground(new java.awt.Color(255, 255, 255));
        titulo_noInterior.setText("   No Interior");
        titulo_noInterior.setBorder(null);
        titulo_noInterior.setFocusable(false);
        contenedor.add(titulo_noInterior, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 400, 130, 40));

        colonia.setEditable(false);
        colonia.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        colonia.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        colonia.setText("jTextField1");
        colonia.setBorder(null);
        colonia.setFocusable(false);
        contenedor.add(colonia, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 440, 200, 40));

        no_exterior.setEditable(false);
        no_exterior.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        no_exterior.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        no_exterior.setText("jTextField1");
        no_exterior.setBorder(null);
        no_exterior.setFocusable(false);
        contenedor.add(no_exterior, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 440, 130, 40));

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
        contenedor.add(rfc_busqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120, 710, 50));

        icon_buscar.setIcon(new javax.swing.ImageIcon("C:\\Users\\ar275\\Documents\\Generador de facturas\\generador-de-facturas\\generador_facturas\\src\\img\\btn_buscar.png")); // NOI18N
        icon_buscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        icon_buscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                icon_buscarMouseClicked(evt);
            }
        });
        contenedor.add(icon_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 110, 70, 70));

        btn_cerrarConsulta.setBackground(new java.awt.Color(198, 54, 55));
        btn_cerrarConsulta.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btn_cerrarConsulta.setForeground(new java.awt.Color(255, 255, 255));
        btn_cerrarConsulta.setText("Cerrar consulta");
        btn_cerrarConsulta.setBorder(null);
        btn_cerrarConsulta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cerrarConsulta.setFocusPainted(false);
        btn_cerrarConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cerrarConsultaActionPerformed(evt);
            }
        });
        contenedor.add(btn_cerrarConsulta, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 530, 170, 40));

        logo_lb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo_escuela.png"))); // NOI18N
        logo_lb.setText("jLabel2");
        logo_lb.setMaximumSize(new java.awt.Dimension(400, 400));
        logo_lb.setMinimumSize(new java.awt.Dimension(400, 400));
        logo_lb.setPreferredSize(new java.awt.Dimension(400, 600));
        contenedor.add(logo_lb, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 180, 370, 360));

        txt_rfc.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txt_rfc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_rfc.setText("jLabel1");
        contenedor.add(txt_rfc, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, 880, 50));

        fondo.add(contenedor);
        contenedor.setBounds(0, 100, 1050, 630);

        getContentPane().add(fondo, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    void mostrarDatos(String rfc) {
        try {
            //Seleccionar los datos del emisor
            String consulta = "SELECT * FROM emisor WHERE rfc =?";
            PreparedStatement ps = cx.conectar().prepareStatement(consulta);
            ps.setString(1, rfc);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                mostrarCampos();
                txt_rfc.setText(rs.getString("rfc"));//etiqueta con el nombre del RFC
                rfc_show.setText(rs.getString("rfc"));
                nombres.setText(rs.getString("nombres"));
                apellido_paterno.setText(rs.getString("apellido_paterno"));
                apellido_materno.setText(rs.getString("apellido_materno"));
                fecha_nacimiento.setText(rs.getDate("fecha_nacimiento").toString());
                codigo_postal.setText(String.valueOf(rs.getInt("domicilio_fiscal")));
                correo_electronico.setText(rs.getString("correo_electronico"));
                estado.setText(rs.getString("estado"));
                municipio.setText(rs.getString("municipio"));
                colonia.setText(rs.getString("colonia"));
                no_exterior.setText(rs.getString("num_exterior"));
                no_interior.setText(rs.getString("num_interior"));
                regimen.setText(rs.getString("regimen"));
            }else{
                JOptionPane.showMessageDialog(null, "El RFC que solicitó no se encuentra registrado", "RFC no encontrado", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultarEmisor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

        private void ocultarCampos() {
        //
        lb_campos.setVisible(false);
        //Titulos de los campos
        titulo_apellidoPaterno.setVisible(false);
        titulo_apellidoMaterno.setVisible(false);
        titulo_nombres.setVisible(false);
        titulo_rfc.setVisible(false);
        titulo_correo.setVisible(false);
        titulo_fechaNacimiento.setVisible(false);
        titulo_cp.setVisible(false);
        titulo_estado.setVisible(false);
        titulo_municipio.setVisible(false);
        titulo_colonia.setVisible(false);
        titulo_noExterior.setVisible(false);
        titulo_noInterior.setVisible(false);
        titulo_regimen.setVisible(false);
        //Campos
        rfc_show.setVisible(false);
        nombres.setVisible(false);
        apellido_paterno.setVisible(false);
        apellido_materno.setVisible(false);
        fecha_nacimiento.setVisible(false);
        codigo_postal.setVisible(false);
        correo_electronico.setVisible(false);
        estado.setVisible(false);
        municipio.setVisible(false);
        colonia.setVisible(false);
        no_exterior.setVisible(false);
        no_interior.setVisible(false);
        regimen.setVisible(false);
        //btn cerrar
        btn_cerrarConsulta.setVisible(false);
        //
        txt_rfc.setVisible(false);

    }

    private void mostrarCampos() {
        //OCulta logo
        logo_lb.setVisible(false);
        //mostrar boton para cerrar consulta
        btn_cerrarConsulta.setVisible(true);
        //ocultar barra de busqueda
        rfc_busqueda.setVisible(false);
        icon_buscar.setVisible(false);

        //ocultar eiqueta de la barra
        lb_inicial.setVisible(false);

        lb_campos.setVisible(true);
        //
        titulo_apellidoPaterno.setVisible(true);
        titulo_apellidoMaterno.setVisible(true);
        titulo_nombres.setVisible(true);
        titulo_rfc.setVisible(true);
        titulo_correo.setVisible(true);
        titulo_fechaNacimiento.setVisible(true);
        titulo_cp.setVisible(true);
        titulo_estado.setVisible(true);
        titulo_municipio.setVisible(true);
        titulo_colonia.setVisible(true);
        titulo_noExterior.setVisible(true);
        titulo_noInterior.setVisible(true);
        titulo_regimen.setVisible(true);
        //
        rfc_show.setVisible(true);
        nombres.setVisible(true);
        apellido_paterno.setVisible(true);
        apellido_materno.setVisible(true);
        fecha_nacimiento.setVisible(true);
        codigo_postal.setVisible(true);
        correo_electronico.setVisible(true);
        estado.setVisible(true);
        municipio.setVisible(true);
        colonia.setVisible(true);
        no_exterior.setVisible(true);
        no_interior.setVisible(true);
        regimen.setVisible(true);
        //btn cerrar
        btn_cerrarConsulta.setVisible(true);
        txt_rfc.setVisible(true);
    }

    public void setDatos(String usuario, LocalDate fechaInicioSesion, LocalTime horaInicioSesion) {
        this.usuario = usuario;
        this.fechaInicioSesion = fechaInicioSesion;
        this.horaInicioSesion = horaInicioSesion;
        txt_nombreUser.setText(usuario);
        //solo muestra el menu de emisor si el usuario es el director
        if (!"director".equals(this.usuario)) {
            btn_emisor.setVisible(false);
        }
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
                "¿Desea salir de la aplicación?",
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
            //Regresa al menu principal
            MenuPrincipal ventana = new MenuPrincipal();
            ventana.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
            ventana.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_icon_regresarlbMouseClicked

    private void nombre_userMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nombre_userMouseClicked

    }//GEN-LAST:event_nombre_userMouseClicked

    private void btn_historialSesionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_historialSesionesMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt)){
            HistorialSesiones ventana = new HistorialSesiones();
            ventana.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
            ventana.setVisible(true);
            this.dispose();
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

    private void txt_altaPadresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_altaPadresMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt)){
            AltaPadres ventana = new AltaPadres();
            ventana.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
            ventana.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_txt_altaPadresMouseClicked

    private void txt_consultarPadresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_consultarPadresMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt)){//click izquierdo
            ConsultarPadre ventena = new ConsultarPadre();
            ventena.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
            ventena.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_txt_consultarPadresMouseClicked

    private void txt_modificarPadresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_modificarPadresMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt)){
            ConsultarPadre ventana = new ConsultarPadre();
            ventana.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
            ventana.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_txt_modificarPadresMouseClicked

    private void txt_eliminarPadresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_eliminarPadresMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt)){
            EliminarPadre ventana = new EliminarPadre();
            ventana.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
            ventana.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_txt_eliminarPadresMouseClicked

    private void menu_padresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_padresMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_menu_padresMouseClicked

    private void txt_altaAlumnosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_altaAlumnosMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt)){
            AltaAlumnos ventana = new AltaAlumnos();
            ventana.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
            ventana.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_txt_altaAlumnosMouseClicked

    private void txt_consultarAlmnosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_consultarAlmnosMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt)){//click izquierdo
            ConsultarAlumnos ventana = new ConsultarAlumnos();
            ventana.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
            ventana.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_txt_consultarAlmnosMouseClicked

    private void txt_modificarAlumnosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_modificarAlumnosMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt)){
            ModificarAlumno ventana = new ModificarAlumno();
            ventana.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
            ventana.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_txt_modificarAlumnosMouseClicked

    private void txt_eliminarAlumnoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_eliminarAlumnoMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt)){
            EliminarAlumno ventena = new EliminarAlumno();
            ventena.setDatos(usuario, fechaInicioSesion, horaInicioSesion);;
            ventena.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_txt_eliminarAlumnoMouseClicked

    private void txt_editarEmisorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_editarEmisorMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt)){
            ModificarEmisor ventana = new ModificarEmisor();
            ventana.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
            ventana.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_txt_editarEmisorMouseClicked

    private void txt_altaEmisorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_altaEmisorMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt)){
            AltaEmisor ventana = new AltaEmisor();
            ventana.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
            ventana.setVisible(true);
            this.setVisible(false);
        }
    }//GEN-LAST:event_txt_altaEmisorMouseClicked

    private void txt_eliminarEmisorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_eliminarEmisorMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt)){
            EliminarEmisor ventana = new EliminarEmisor();
            ventana.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
            ventana.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_txt_eliminarEmisorMouseClicked

    private void txt_ConsultarEmisorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_ConsultarEmisorMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt)){//click izquierdo
            ConsultarEmisor ventana = new ConsultarEmisor();
            ventana.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
            ventana.setVisible(true);
            this.dispose();

        }
    }//GEN-LAST:event_txt_ConsultarEmisorMouseClicked

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

    private void btn_cerrarConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cerrarConsultaActionPerformed
        ocultarCampos();
        rfc_busqueda.setVisible(true);
        rfc_busqueda.setText("");
        lb_inicial.setVisible(true);
        logo_lb.setVisible(true);
        icon_buscar.setVisible(true);
        btn_cerrarConsulta.setVisible(false);
    }//GEN-LAST:event_btn_cerrarConsultaActionPerformed

    private void regimenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regimenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_regimenActionPerformed

    private void txt_generarFcaturaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_generarFcaturaMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt)){
            GenerarFactura ventana = new GenerarFactura();
            ventana.setDatos(usuario, fechaInicioSesion, horaInicioSesion);
            ventana.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_txt_generarFcaturaMouseClicked

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
            java.util.logging.Logger.getLogger(ConsultarEmisor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConsultarEmisor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConsultarEmisor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConsultarEmisor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new ConsultarEmisor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fecha;
    private javax.swing.JTextField apellido_materno;
    private javax.swing.JTextField apellido_paterno;
    private javax.swing.JPanel barra_nav;
    private javax.swing.JPanel btn_alumnos;
    private javax.swing.JButton btn_cerrarConsulta;
    private javax.swing.JPanel btn_cerrarSesion;
    private javax.swing.JPanel btn_emisor;
    private javax.swing.JPanel btn_estadisticas;
    private javax.swing.JPanel btn_facturas;
    private javax.swing.JPanel btn_historialSesiones;
    private javax.swing.JPanel btn_padres;
    private javax.swing.JPanel btn_salir;
    private javax.swing.JLabel cerrar_icon;
    private javax.swing.JTextField codigo_postal;
    private javax.swing.JTextField colonia;
    private javax.swing.JPanel contenedor;
    private javax.swing.JPanel contenedor_menu;
    private javax.swing.JTextField correo_electronico;
    private javax.swing.JTextField estado;
    private javax.swing.JTextField fecha_nacimiento;
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
    private javax.swing.JLabel lb_campos;
    private javax.swing.JLabel lb_inicial;
    private javax.swing.JLabel logo_lb;
    private javax.swing.JPanel menu_alumnos;
    private javax.swing.JPanel menu_emisor;
    private javax.swing.JPanel menu_estadisticas;
    private javax.swing.JPanel menu_factura;
    private javax.swing.JPanel menu_padres;
    private javax.swing.JPanel menu_salir;
    private javax.swing.JPanel menu_user;
    private javax.swing.JTextField municipio;
    private javax.swing.JTextField no_exterior;
    private javax.swing.JTextField no_interior;
    private javax.swing.JPanel nombre_user;
    private javax.swing.JTextField nombres;
    private javax.swing.JTextField regimen;
    private javax.swing.JTextField rfc_busqueda;
    private javax.swing.JTextField rfc_show;
    private javax.swing.JLabel text_salir;
    private javax.swing.JTextField titulo_apellidoMaterno;
    private javax.swing.JTextField titulo_apellidoPaterno;
    private javax.swing.JTextField titulo_colonia;
    private javax.swing.JTextField titulo_correo;
    private javax.swing.JTextField titulo_cp;
    private javax.swing.JTextField titulo_estado;
    private javax.swing.JTextField titulo_fechaNacimiento;
    private javax.swing.JTextField titulo_municipio;
    private javax.swing.JTextField titulo_noExterior;
    private javax.swing.JTextField titulo_noInterior;
    private javax.swing.JTextField titulo_nombres;
    private javax.swing.JTextField titulo_regimen;
    private javax.swing.JTextField titulo_rfc;
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
    private javax.swing.JLabel txt_emisoresRegistrados;
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
