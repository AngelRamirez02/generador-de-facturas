/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package emisor;

import conexion.conexion;
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
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.StyledEditorKit;
import login.login_window;
import validacion.Validacion;

/**
 *
 * @author ar275
 */
public class AltaEmisor extends javax.swing.JFrame {

    /**
     * Creates new form alta_emisor
     */
    conexion cx = new conexion();//conexion a la base de datos
    Validacion valida = new Validacion();
    Image menu_img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/menu_icon.png"));
    //Imagen x del menu
    Image equis_icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/menu_iconx.png"));
    Image logo_img= Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/file.png"));
    Image info_img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/icon_info.png"));

    public AltaEmisor() {
        initComponents();
        info_nombre.setVisible(false);
        infoFecha_lb.setVisible(false);
        infoRFC_lb.setVisible(false);
        infocp_lb.setVisible(false);
        btn_menu.setIcon(new ImageIcon(menu_img.getScaledInstance( btn_menu.getWidth(),btn_menu.getHeight(), Image.SCALE_SMOOTH)));
        logo_nav.setIcon(new ImageIcon(logo_img.getScaledInstance(logo_nav.getWidth(), logo_nav.getHeight(), Image.SCALE_SMOOTH)));
        infoIcon_lb.setIcon(new ImageIcon(info_img.getScaledInstance(infoIcon_lb2.getWidth(), infoIcon_lb2.getHeight(), Image.SCALE_SMOOTH)));
        infoIcon_lb2.setIcon(new ImageIcon(info_img.getScaledInstance(infoIcon_lb2.getWidth(), infoIcon_lb2.getHeight(), Image.SCALE_SMOOTH)));
        infoIcon_lb3.setIcon(new ImageIcon(info_img.getScaledInstance(infoIcon_lb3.getWidth(), infoIcon_lb3.getHeight(), Image.SCALE_SMOOTH)));
        infoIcon_lb4.setIcon(new ImageIcon(info_img.getScaledInstance(infoIcon_lb4.getWidth(), infoIcon_lb4.getHeight(), Image.SCALE_SMOOTH)));
        menu_salir.setVisible(false);
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        this.setSize(this.getSize().width,screenSize.height);
        this.setLocationRelativeTo(null);//La ventana aparece en el centro
        this.setLocation(this.getLocation().x,0);
        
        // Formatear la fecha en el formato "dd/MM/yyyy"
        LocalDate fechaActual = LocalDate.now();
        // Crear un formato con localización en español
        DateTimeFormatter formatoEspanol = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        // Formatear la fecha en español
        String fechaFormateada = fechaActual.format(formatoEspanol);
        Fecha.setText(fechaFormateada);//Mostar hora
        //Mostrar la hora en tiempo real
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
                                // Añadir el listener para detectar cuando la ventana es redimensionada
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int panelWidth = contenedor.getWidth();
                int panel_heigth = contenedor.getHeight()-barra_nav.getHeight();
                int newX = (fondo.getWidth() - panelWidth) / 2; // Calcular la nueva posición en 
                int newY = (fondo.getHeight() - panel_heigth)/2;
                barra_nav.setSize(fondo.getWidth(), barra_nav.getHeight());
                contenedor.setLocation(newX, newY);
                logo_nav.setLocation(barra_nav.getWidth()/2-50, logo_nav.getLocation().y);
                btn_menu.setLocation(barra_nav.getWidth()-100, btn_menu.getLocation().y);
                menu_salir.setLocation(barra_nav.getWidth()-200, menu_salir.getLocation().y);
            }
        });
        
        this.addWindowStateListener(new WindowStateListener() {
            int panelWidth = contenedor.getWidth();
            int panelHeight = contenedor.getHeight() - barra_nav.getHeight();
            int newX = (fondo.getWidth() - panelWidth) / 2; // Calcular la nueva posición en X
            int newY = (fondo.getHeight() - panelHeight) / 2;

            @Override
            public void windowStateChanged(WindowEvent e) {
                // Ajustar tamaño de la barra de navegación
                barra_nav.setSize(fondo.getWidth(), barra_nav.getHeight());
                // Centrar el contenedor
                contenedor.setLocation(newX, newY);
                logo_nav.setLocation(barra_nav.getWidth()/2-50, logo_nav.getLocation().y);
                menu_salir.setLocation(barra_nav.getWidth()-200, menu_salir.getLocation().y);
            }
        });

        timer.start();
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
        btn_menu = new javax.swing.JLabel();
        logo_nav = new javax.swing.JLabel();
        menu_salir = new javax.swing.JPanel();
        btn_salir = new javax.swing.JPanel();
        icon_salir = new javax.swing.JLabel();
        text_salir = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        btn_cerrarSesion = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txt_cerrarSesion = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
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
        jLabel2 = new javax.swing.JLabel();
        entrada_apellidoPaterno = new javax.swing.JTextField();
        entrada_nombres = new javax.swing.JTextField();
        nombres_lb = new javax.swing.JLabel();
        registrarEmisor_Titulo = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        datosPersonales_titulo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        datosfiscales_titulo = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        entrada_fechaNacimiento = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Instituto Manuel Andres Lopez Obrador - Alta Emisor");
        setMinimumSize(new java.awt.Dimension(1050, 700));
        setPreferredSize(new java.awt.Dimension(1050, 700));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        fondo.setBackground(new java.awt.Color(240, 240, 240));
        fondo.setPreferredSize(new java.awt.Dimension(1043, 610));
        fondo.setLayout(null);

        barra_nav.setBackground(new java.awt.Color(198, 54, 55));
        barra_nav.setName("instituo Manuel Andres Lopez Obrador - Login"); // NOI18N
        barra_nav.setPreferredSize(new java.awt.Dimension(0, 70));
        barra_nav.setLayout(null);

        Fecha.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        Fecha.setForeground(new java.awt.Color(255, 255, 255));
        Fecha.setText("Fecha: ");
        barra_nav.add(Fecha);
        Fecha.setBounds(40, 10, 260, 22);

        hora_lb.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        hora_lb.setForeground(new java.awt.Color(255, 255, 255));
        hora_lb.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        hora_lb.setText("Hora: ");
        barra_nav.add(hora_lb);
        hora_lb.setBounds(40, 40, 130, 22);

        btn_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/menu_icon.png"))); // NOI18N
        btn_menu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_menuMouseClicked(evt);
            }
        });
        barra_nav.add(btn_menu);
        btn_menu.setBounds(970, 20, 50, 40);
        barra_nav.add(logo_nav);
        logo_nav.setBounds(450, 0, 80, 80);

        fondo.add(barra_nav);
        barra_nav.setBounds(0, 0, 1043, 80);

        menu_salir.setBackground(new java.awt.Color(169, 30, 31));
        menu_salir.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_salir.setBackground(new java.awt.Color(169, 30, 31));
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

        menu_salir.add(btn_salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 180, 50));

        btn_cerrarSesion.setBackground(new java.awt.Color(169, 30, 31));
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
        btn_cerrarSesion.add(txt_cerrarSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(61, 0, 120, 50));
        btn_cerrarSesion.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 0, 140, 10));
        btn_cerrarSesion.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 47, 140, 10));

        menu_salir.add(btn_cerrarSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 180, 50));
        menu_salir.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 60, 140, 10));

        fondo.add(menu_salir);
        menu_salir.setBounds(840, 80, 210, 130);

        contenedor.setBackground(new java.awt.Color(240, 240, 240));
        contenedor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        contenedor.add(infoIcon_lb4, new org.netbeans.lib.awtextra.AbsoluteConstraints(842, 303, 20, 20));

        infocp_lb.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
        infocp_lb.setText("Un código postal debe ser de 5 números");
        contenedor.add(infocp_lb, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 330, -1, -1));

        infoRFC_lb.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
        infoRFC_lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infoRFC_lb.setText("El RFC es de 13 digitos formado por apellidos, nombre y fecha de nacimiento");
        infoRFC_lb.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        contenedor.add(infoRFC_lb, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 260, 430, 30));

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
        contenedor.add(infoIcon_lb3, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 242, 20, 20));

        infoFecha_lb.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
        infoFecha_lb.setText("Ej: 02 dic 2003");
        contenedor.add(infoFecha_lb, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 460, -1, -1));

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
        contenedor.add(infoIcon_lb, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 240, 20, 20));

        info_nombre.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
        info_nombre.setText("Ingrese los nombres separados por espacio");
        contenedor.add(info_nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 263, -1, 20));

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
        contenedor.add(infoIcon_lb2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 427, 20, 20));

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
        text_guardarDatos.setText("Guardar datos del emisor");
        text_guardarDatos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        contenedor_btn.add(text_guardarDatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 40));

        btn_guardarDatos.add(contenedor_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 2, 240, 40));

        contenedor.add(btn_guardarDatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 550, 245, 45));

        try {
            entrada_cp.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        contenedor.add(entrada_cp, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 300, 160, 27));

        entrada_regimen.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        entrada_regimen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Simplificado de Confianza. ", "612  Persona Física con Actividad Empresarial", "605  Sueldos y Salarios e Ingresos Asimilados a Salarios" }));
        entrada_regimen.setSelectedIndex(1);
        contenedor.add(entrada_regimen, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 360, 360, 30));

        jLabel10.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel10.setText("Código postal");
        contenedor.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 300, 120, -1));
        contenedor.add(entrada_rfc, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 240, 157, -1));

        jLabel9.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel9.setText("Regimen Fiscal");
        contenedor.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 370, -1, -1));

        jLabel8.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel8.setText("RFC");
        contenedor.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 240, -1, -1));
        contenedor.add(entrada_correoElectronico, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 490, 190, -1));

        jLabel7.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel7.setText("Correo electronico");
        contenedor.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 490, 170, 20));

        jLabel6.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel6.setText("Fecha de nacimiento");
        contenedor.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 430, 180, 20));
        contenedor.add(entrada_apellidoMaterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(258, 364, 190, -1));

        jLabel5.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel5.setText("Apellido Materno");
        contenedor.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 367, 150, -1));

        jLabel2.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel2.setText("Apellido peterno");
        contenedor.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 305, 140, -1));
        contenedor.add(entrada_apellidoPaterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(258, 302, 190, -1));
        contenedor.add(entrada_nombres, new org.netbeans.lib.awtextra.AbsoluteConstraints(258, 237, 190, -1));

        nombres_lb.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        nombres_lb.setLabelFor(entrada_nombres);
        nombres_lb.setText("Nombre (s)");
        contenedor.add(nombres_lb, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, 110, -1));

        registrarEmisor_Titulo.setBackground(new java.awt.Color(255, 255, 255));
        registrarEmisor_Titulo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Roboto Light", 1, 48)); // NOI18N
        jLabel4.setText("Registrar emisor");
        registrarEmisor_Titulo.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 0, -1, 60));

        contenedor.add(registrarEmisor_Titulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, 620, -1));

        datosPersonales_titulo.setBackground(new java.awt.Color(255, 255, 255));
        datosPersonales_titulo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Datos personales");
        datosPersonales_titulo.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 30));

        contenedor.add(datosPersonales_titulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 180, 200, -1));

        datosfiscales_titulo.setBackground(new java.awt.Color(255, 255, 255));
        datosfiscales_titulo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Datos fiscales");
        datosfiscales_titulo.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 30));

        contenedor.add(datosfiscales_titulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 180, 170, -1));

        entrada_fechaNacimiento.setDateFormatString("dd MMM y");
        entrada_fechaNacimiento.setMaxSelectableDate(new java.util.Date(1735628468000L));
        entrada_fechaNacimiento.setMinSelectableDate(new java.util.Date(-315593932000L));
        contenedor.add(entrada_fechaNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(258, 426, 190, -1));

        fondo.add(contenedor);
        contenedor.setBounds(0, 0, 1050, 650);

        getContentPane().add(fondo, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AltaEmisor.class.getName()).log(Level.SEVERE, null, ex);
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
        if (rfc_user.equals(rfc_sistema)){
            //Expresion para validar un RFC
            String regex = "^[A-ZÑ&]{4}\\d{6}[A-Z0-9]{3}$";
            // Compilar la expresión regular en un patrón
            Pattern pattern = Pattern.compile(regex);
            // Crear el matcher que validará el RFC
            Matcher matcher = pattern.matcher(rfc_user);
            // Retornar si coincide o no
            if(matcher.matches()){
               return true; 
            }
            JOptionPane.showMessageDialog(null, "Ingrese un RFC valido", "RFC no valido", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        JOptionPane.showMessageDialog(null,"El RFC no coincide con el nombre, apellidos o con la fecha de nacimiento del emisor", "RFC no valido", JOptionPane.WARNING_MESSAGE);
        return false;
    }
    public void altaEmisor() {
        try {
            int cp = Integer.parseInt(entrada_cp.getText());
            //Obtener todos los datos de entrada
            Date fecha_nacimiento = entrada_fechaNacimiento.getDate();
            java.sql.Date fecha_sql = new java.sql.Date(fecha_nacimiento.getTime());
            //Crear conexion a la base de datos
            //Preparar consulta para insertar los datos
            String query_alta = "INSERT INTO emisor "
                    + "(rfc, nombres, apellido_paterno, apellido_materno, fecha_nacimiento, correo_electronico, domiciolio_fiscal, regimen)"
                    + "VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement ps = cx.conectar().prepareStatement(query_alta);//Creacion de la consulta
            ps.setString(1, entrada_rfc.getText());
            ps.setString(2, entrada_nombres.getText());
            ps.setString(3, entrada_apellidoPaterno.getText());
            ps.setString(4, entrada_apellidoMaterno.getText());
            ps.setDate(5, fecha_sql);
            ps.setString(6, entrada_correoElectronico.getText());
            ps.setInt(7, cp);
            ps.setString(8, entrada_regimen.getSelectedItem().toString());
            
            //Verifica que se realizó el registro
            int filas_insertadas = ps.executeUpdate();
            if(filas_insertadas >0){
                 JOptionPane.showMessageDialog(null,"Datos registrados exitosamente", "Registro existoso", JOptionPane.INFORMATION_MESSAGE);
            }else{
                 JOptionPane.showMessageDialog(null,"Hubo un error al registrar los datos, intente otra vez", "Error en el registro", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Hubo un error al registrar los datos, intente otra vez", "Error en el registro", JOptionPane.WARNING_MESSAGE);;
        }
    }
    
    private void btn_menuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_menuMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt)) {//click izquierdo
            if(menu_salir.isVisible()){
                menu_salir.setVisible(false);
                btn_menu.setIcon(new ImageIcon(menu_img.getScaledInstance( btn_menu.getWidth(),btn_menu.getHeight(), Image.SCALE_SMOOTH)));
                
            }else{
                menu_salir.setVisible(true);
                btn_menu.setIcon(new ImageIcon(equis_icon.getScaledInstance( btn_menu.getWidth(),btn_menu.getHeight(), Image.SCALE_SMOOTH)));
            }
        }
    }//GEN-LAST:event_btn_menuMouseClicked

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
            if(rfc_existente()){
                JOptionPane.showMessageDialog(null, "El RFC ya se encuentra registrado", "RFC existente", JOptionPane.WARNING_MESSAGE);
                entrada_rfc.requestFocusInWindow();    // Borde al tener foco;
                return;
            }
            if(!valida.cpValido(entrada_cp.getText())){
                JOptionPane.showMessageDialog(null, "Ingrese un codigo postal valido", "Codigo postal no valido", JOptionPane.WARNING_MESSAGE);
                entrada_cp.requestFocusInWindow();    // Borde al tener foco;
                return;
            }
            altaEmisor();
            System.out.println("Muy bien");
        }
    }//GEN-LAST:event_btn_guardarDatosMouseClicked

    private void infoIcon_lb2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoIcon_lb2MouseEntered
       infoFecha_lb.setVisible(true);
    }//GEN-LAST:event_infoIcon_lb2MouseEntered

    private void infoIcon_lb2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoIcon_lb2MouseExited
        infoFecha_lb.setVisible(false);
    }//GEN-LAST:event_infoIcon_lb2MouseExited

    private void infoIcon_lbMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoIcon_lbMouseEntered
         info_nombre.setVisible(true);
    }//GEN-LAST:event_infoIcon_lbMouseEntered

    private void infoIcon_lbMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoIcon_lbMouseExited
        info_nombre.setVisible(false);
    }//GEN-LAST:event_infoIcon_lbMouseExited

    private void infoIcon_lb3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoIcon_lb3MouseEntered
        infoRFC_lb.setVisible(true);
    }//GEN-LAST:event_infoIcon_lb3MouseEntered

    private void infoIcon_lb3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoIcon_lb3MouseExited
        infoRFC_lb.setVisible(false);
    }//GEN-LAST:event_infoIcon_lb3MouseExited

    private void infoIcon_lb4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoIcon_lb4MouseEntered
       infocp_lb.setVisible(true);
    }//GEN-LAST:event_infoIcon_lb4MouseEntered

    private void infoIcon_lb4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoIcon_lb4MouseExited
        infocp_lb.setVisible(false);
    }//GEN-LAST:event_infoIcon_lb4MouseExited

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed

    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Object[] opciones = {"Aceptar", "Cancelar"};
        // Si existe información que no ha sido guardada
        if (existeInfo()) {
            // Mostrar diálogo que pregunta si desea confirmar la salida
            int opcionSeleccionada = JOptionPane.showOptionDialog(
                    null,
                    "Se perderán los datos ingresados, ¿Está seguro de salir?",
                    "Datos no guardados",
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
        } else {
            // Si no hay información importante, permitir el cierre
            this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        }
    }//GEN-LAST:event_formWindowClosing

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
            java.util.logging.Logger.getLogger(AltaEmisor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AltaEmisor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AltaEmisor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AltaEmisor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AltaEmisor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fecha;
    private javax.swing.JPanel barra_nav;
    private javax.swing.JPanel btn_cerrarSesion;
    private paneles.PanelRound btn_guardarDatos;
    private javax.swing.JLabel btn_menu;
    private javax.swing.JPanel btn_salir;
    private javax.swing.JPanel contenedor;
    private paneles.PanelRound contenedor_btn;
    private javax.swing.JPanel datosPersonales_titulo;
    private javax.swing.JPanel datosfiscales_titulo;
    private javax.swing.JTextField entrada_apellidoMaterno;
    private javax.swing.JTextField entrada_apellidoPaterno;
    private javax.swing.JTextField entrada_correoElectronico;
    private javax.swing.JFormattedTextField entrada_cp;
    private com.toedter.calendar.JDateChooser entrada_fechaNacimiento;
    private javax.swing.JTextField entrada_nombres;
    private javax.swing.JComboBox<String> entrada_regimen;
    private javax.swing.JTextField entrada_rfc;
    private javax.swing.JPanel fondo;
    private javax.swing.JLabel hora_lb;
    private javax.swing.JLabel icon_salir;
    private javax.swing.JLabel infoFecha_lb;
    private javax.swing.JLabel infoIcon_lb;
    private javax.swing.JLabel infoIcon_lb2;
    private javax.swing.JLabel infoIcon_lb3;
    private javax.swing.JLabel infoIcon_lb4;
    private javax.swing.JLabel infoRFC_lb;
    private javax.swing.JLabel info_nombre;
    private javax.swing.JLabel infocp_lb;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel logo_nav;
    private javax.swing.JPanel menu_salir;
    private javax.swing.JLabel nombres_lb;
    private javax.swing.JPanel registrarEmisor_Titulo;
    private javax.swing.JLabel text_guardarDatos;
    private javax.swing.JLabel text_salir;
    private javax.swing.JLabel txt_cerrarSesion;
    // End of variables declaration//GEN-END:variables
}
