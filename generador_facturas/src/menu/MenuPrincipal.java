/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package menu;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import login.login_window;

/**
 *
 * @author ar275
 */
public class MenuPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form MenuPrincipal
     * 
     */
    //Colores para los botones seleccionados y no
    Color colorbtnSeleccionado = Color.decode("#A91E1F");
    Color colorbtnNoSeleccionado = Color.decode("#C94545");
    public MenuPrincipal() {
        initComponents();
        
        //Imagen del logo de la escuela
        Image logo_img= Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/logo_escuela.png"));
        logo_lb.setIcon(new ImageIcon(logo_img.getScaledInstance(logo_lb.getWidth(), logo_lb.getHeight(), Image.SCALE_SMOOTH)));
       
        //Iconos de item para menu
        Image icon_img= Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/icon_itemMenu.png"));
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
                menu_salir.setLocation(barra_nav.getWidth()-menu_salir.getWidth(), menu_salir.getLocation().y);//menu salir responsive
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
        menu_salir.setVisible(false);//por defecto el menu de salir no es visible
        this.setLocationRelativeTo(null);//La ventana aparece en el centro
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
        contenedor = new javax.swing.JPanel();
        logo_lb = new javax.swing.JLabel();
        txt_facturacion = new javax.swing.JLabel();
        txt_del = new javax.swing.JLabel();
        txt_instituto = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Instituto Andrés Manuel López Obrador - Menu Principal");
        setMinimumSize(new java.awt.Dimension(1050, 735));
        setSize(new java.awt.Dimension(1050, 735));

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
        btn_alumnos.setVerifyInputWhenFocusTarget(false);
        btn_alumnos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_alumnos.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_alumnos.setForeground(new java.awt.Color(255, 255, 255));
        txt_alumnos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_alumnos.setText("Alumnos");
        btn_alumnos.add(txt_alumnos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 30));

        icon_item.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_itemMenu.png"))); // NOI18N
        btn_alumnos.add(icon_item, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 8, 18, 15));

        contenedor_menu.add(btn_alumnos);
        btn_alumnos.setBounds(0, 37, 136, 37);

        btn_padres.setBackground(new java.awt.Color(201, 69, 69));
        btn_padres.setVerifyInputWhenFocusTarget(false);
        btn_padres.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_padres.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_padres.setForeground(new java.awt.Color(255, 255, 255));
        txt_padres.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_padres.setText("Padres");
        btn_padres.add(txt_padres, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 30));

        icon_item2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_itemMenu.png"))); // NOI18N
        btn_padres.add(icon_item2, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 8, 18, 15));

        contenedor_menu.add(btn_padres);
        btn_padres.setBounds(125, 37, 83, 30);

        btn_facturas.setBackground(new java.awt.Color(201, 69, 69));
        btn_facturas.setVerifyInputWhenFocusTarget(false);
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
        btn_estadisticas.setVerifyInputWhenFocusTarget(false);
        btn_estadisticas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_estadisticas.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        txt_estadisticas.setForeground(new java.awt.Color(255, 255, 255));
        txt_estadisticas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_estadisticas.setText("Estadisticas");
        btn_estadisticas.add(txt_estadisticas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, 30));

        icon_item4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_itemMenu.png"))); // NOI18N
        btn_estadisticas.add(icon_item4, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 8, 18, 15));

        contenedor_menu.add(btn_estadisticas);
        btn_estadisticas.setBounds(375, 37, 120, 30);

        btn_emisor.setBackground(new java.awt.Color(201, 69, 69));
        btn_emisor.setVerifyInputWhenFocusTarget(false);
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
        txt_nombreUser.setText("User");
        nombre_user.add(txt_nombreUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 80, 50));

        cerrar_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/x_menuUser.png"))); // NOI18N
        cerrar_icon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cerrar_icon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cerrar_iconMouseClicked(evt);
            }
        });
        nombre_user.add(cerrar_icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, -1, 30));

        menu_salir.add(nombre_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, -1));

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
        menu_salir.setBounds(870, 100, 180, 190);

        contenedor.setBackground(new java.awt.Color(255, 255, 255));
        contenedor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logo_lb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo_escuela.png"))); // NOI18N
        logo_lb.setText("jLabel2");
        logo_lb.setMaximumSize(new java.awt.Dimension(400, 400));
        logo_lb.setMinimumSize(new java.awt.Dimension(400, 400));
        logo_lb.setPreferredSize(new java.awt.Dimension(400, 600));
        contenedor.add(logo_lb, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 130, 370, 360));

        txt_facturacion.setFont(new java.awt.Font("Teko", 1, 40)); // NOI18N
        txt_facturacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_facturacion.setText("SISTEMA DE FACTURACION");
        contenedor.add(txt_facturacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 570, 60));

        txt_del.setFont(new java.awt.Font("Teko", 1, 40)); // NOI18N
        txt_del.setText("DEL");
        contenedor.add(txt_del, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, -1, -1));

        txt_instituto.setFont(new java.awt.Font("Teko", 1, 36)); // NOI18N
        txt_instituto.setForeground(new java.awt.Color(198, 54, 55));
        txt_instituto.setText("“INSTITUTO ANDRÉS MANUEL LÓPEZ OBRADOR”");
        contenedor.add(txt_instituto, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, 660, -1));

        fondo.add(contenedor);
        contenedor.setBounds(30, 150, 990, 510);

        getContentPane().add(fondo, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menu_userMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_userMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt)) {
            if(menu_salir.isVisible()){//hacer click en el menu de salir
                menu_salir.setVisible(false);  
                menu_user.setBackground(colorbtnNoSeleccionado);
            }else{
                menu_salir.setVisible(true);
                menu_user.setBackground(colorbtnSeleccionado);
            }
        }
    }//GEN-LAST:event_menu_userMouseClicked

    private void cerrar_iconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cerrar_iconMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt)){
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
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPrincipal().setVisible(true);
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
    private javax.swing.JPanel btn_padres;
    private javax.swing.JPanel btn_salir;
    private javax.swing.JLabel cerrar_icon;
    private javax.swing.JPanel contenedor;
    private javax.swing.JPanel contenedor_menu;
    private javax.swing.JPanel fondo;
    private javax.swing.JLabel hora_lb;
    private javax.swing.JLabel icon_item;
    private javax.swing.JLabel icon_item2;
    private javax.swing.JLabel icon_item3;
    private javax.swing.JLabel icon_item4;
    private javax.swing.JLabel icon_item5;
    private javax.swing.JLabel icon_salir;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel logo_lb;
    private javax.swing.JPanel menu_salir;
    private javax.swing.JPanel menu_user;
    private javax.swing.JPanel nombre_user;
    private javax.swing.JLabel text_salir;
    private javax.swing.JLabel txt_alumnos;
    private javax.swing.JLabel txt_cerrarSesion;
    private javax.swing.JLabel txt_del;
    private javax.swing.JLabel txt_emisor;
    private javax.swing.JLabel txt_estadisticas;
    private javax.swing.JLabel txt_factura;
    private javax.swing.JLabel txt_facturacion;
    private javax.swing.JLabel txt_instituto;
    private javax.swing.JLabel txt_nombreUser;
    private javax.swing.JLabel txt_padres;
    private javax.swing.JLabel user_menuIcon;
    private javax.swing.JLabel user_menuIcon1;
    // End of variables declaration//GEN-END:variables
}