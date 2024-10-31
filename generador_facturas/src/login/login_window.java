/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package login;

import java.sql.*;
import conexion.conexion;
import emisor.PrimerInicio;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.Timer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import menu.MenuPrincipal;

/**
 *
 * @author ar275
 */
public class login_window extends javax.swing.JFrame {

    conexion cx;//variable para la conexion a la base de datos
    
    private static int tiempoRestante = 30; // tiempo en segundos
    private static Timer tiempoBloqueo;
    private static boolean bloqueado = false;
    
    int numIntentosFallidos = 0;
    int llamadaPanel=0;
    private JOptionPane optionPane;
    private JDialog dialog;


    public login_window() {
        initComponents();
        //Modificar el teamño del logo
        Image logo_img= Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/logo_escuela.png"));
        logo_lb.setIcon(new ImageIcon(logo_img.getScaledInstance(logo_lb.getWidth(), logo_lb.getHeight(), Image.SCALE_SMOOTH)));
        
        Image icon_block = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/icon_block.png"));
        img_block.setIcon(new ImageIcon(icon_block.getScaledInstance(img_block.getWidth(), img_block.getHeight(), Image.SCALE_SMOOTH)));
        //Configuracion de bordes para las entradas
        usuario_entrada.setBorder(new CompoundBorder(
                new LineBorder(Color.BLACK), // Borde exterior
                new EmptyBorder(5, 10, 5, 10) // "Padding" interno
        ));
        password_entrada.setBorder(new CompoundBorder(
                new LineBorder(Color.BLACK), // Borde exterior
                new EmptyBorder(5, 10, 5, 10) // "Padding" interno
        ));
        
        // Formatear la fecha en el formato "dd/MM/yyyy"
        LocalDate fechaActual = LocalDate.now();
        // Crear un formato con localización en español
        DateTimeFormatter formatoEspanol = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        // Formatear la fecha en español
        String fechaFormateada = fechaActual.format(formatoEspanol);
        Fecha.setText(fechaFormateada);//Mostar hora
        
        cx = new conexion();//creacion de una nueva conexion
        cx.conectar();//conectar a la base de datos

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
        
                // Añadir el listener para detectar cuando la ventana es redimensionada
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int panelWidth = panel_login.getWidth();
                int panel_heigth = panel_login.getHeight()-barra_nav.getHeight();
                int newX = (fondo.getWidth() - panelWidth) / 2; // Calcular la nueva posición en 
                int newY = (fondo.getHeight() - panel_heigth)/2;
                barra_nav.setSize(fondo.getWidth(), barra_nav.getHeight());
                panel_login.setLocation(newX, newY);
                fondo_redondoLogin.setLocation(newX-8, newY-8);
                btn_salir.setLocation(barra_nav.getWidth()-200, btn_salir.getLocation().y);
            }
        });
        
        this.addWindowStateListener(new WindowStateListener() {
            int panelWidth = panel_login.getWidth();
            int panel_heigth = panel_login.getHeight()-barra_nav.getHeight();
            int newX = (fondo.getWidth() - panelWidth) / 2; // Calcular la nueva posición en 
            int newY = (fondo.getHeight() - panel_heigth)/2;
            @Override
            public void windowStateChanged(WindowEvent e) {
                barra_nav.setSize(fondo.getWidth(), barra_nav.getHeight());
                panel_login.setLocation(newX, newY);
                fondo_redondoLogin.setLocation(newX-8, newY-8);
            }
        });
        
        tiempoBloqueo= new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tiempoRestante--;    
                if (tiempoRestante <= 0) {
                    dialog.dispose();
                    tiempoBloqueo.stop();
                    desbloquear();
                }
                // Actualizar el mensaje del JOptionPane
                optionPane.setMessage("Demasiados intentos fallidos\n"
                + "Contacte al soporte del sistema o espere: "+tiempoRestante+" segundos para realizar otra accion");
            }
        });
        
        this.setIconImage(logo_img);//agregar logo a ventana
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
        btn_salir = new javax.swing.JPanel();
        icon_salir = new javax.swing.JLabel();
        text_salir = new javax.swing.JLabel();
        panel_login = new javax.swing.JPanel();
        panelRound2 = new paneles.PanelRound();
        usuario_entrada = new javax.swing.JTextField();
        iniciar_sesion = new javax.swing.JButton();
        password_entrada = new javax.swing.JPasswordField();
        etiqueta_usuario = new javax.swing.JLabel();
        etiqueta_password = new javax.swing.JLabel();
        txt_year = new javax.swing.JLabel();
        text_facturacion = new javax.swing.JLabel();
        img_user = new javax.swing.JLabel();
        img_block = new javax.swing.JLabel();
        mostrar_password = new javax.swing.JCheckBox();
        logo_lb = new javax.swing.JLabel();
        fondo_redondoLogin = new paneles.PanelRound();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Instituto Manuel Andres Lopez Obrador - Iniciar Sesión");
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(750, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        fondo.setBackground(new java.awt.Color(255, 255, 255));
        fondo.setMinimumSize(new java.awt.Dimension(750, 600));
        fondo.setPreferredSize(new java.awt.Dimension(900, 600));
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
        hora_lb.setBounds(40, 40, 150, 22);

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
        btn_salir.add(icon_salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 50, 50));

        text_salir.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        text_salir.setForeground(new java.awt.Color(255, 255, 255));
        text_salir.setText("Salir");
        btn_salir.add(text_salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 60, 50));

        barra_nav.add(btn_salir);
        btn_salir.setBounds(760, 10, 150, 50);

        fondo.add(barra_nav);
        barra_nav.setBounds(0, 0, 924, 70);

        panel_login.setBackground(new java.awt.Color(0, 0, 0));
        panel_login.setPreferredSize(new java.awt.Dimension(700, 450));

        panelRound2.setBackground(new java.awt.Color(255, 255, 255));
        panelRound2.setRoundBottomLeft(10);
        panelRound2.setRoundBottomRight(10);
        panelRound2.setRoundTopLeft(10);
        panelRound2.setRoundTopRight(10);
        panelRound2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        usuario_entrada.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        usuario_entrada.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        usuario_entrada.setToolTipText("");
        usuario_entrada.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        usuario_entrada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                usuario_entradaKeyTyped(evt);
            }
        });
        panelRound2.add(usuario_entrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 300, 45));

        iniciar_sesion.setBackground(new java.awt.Color(102, 102, 102));
        iniciar_sesion.setFont(new java.awt.Font("Teko", 1, 18)); // NOI18N
        iniciar_sesion.setForeground(new java.awt.Color(255, 255, 255));
        iniciar_sesion.setText("Iniciar Sesión");
        iniciar_sesion.setBorderPainted(false);
        iniciar_sesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        iniciar_sesion.setFocusPainted(false);
        iniciar_sesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iniciar_sesionActionPerformed(evt);
            }
        });
        panelRound2.add(iniciar_sesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 360, 168, 45));

        password_entrada.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        password_entrada.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        password_entrada.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        password_entrada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                password_entradaKeyTyped(evt);
            }
        });
        panelRound2.add(password_entrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 265, 300, 45));

        etiqueta_usuario.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        etiqueta_usuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiqueta_usuario.setLabelFor(usuario_entrada);
        etiqueta_usuario.setText("Usuario");
        etiqueta_usuario.setAlignmentX(0.5F);
        panelRound2.add(etiqueta_usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 150, 80, 30));

        etiqueta_password.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        etiqueta_password.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiqueta_password.setLabelFor(password_entrada);
        etiqueta_password.setText("Contraseña");
        panelRound2.add(etiqueta_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 239, 100, -1));

        txt_year.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        txt_year.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_year.setText("2024 - MTAL SOLUTIONS");
        panelRound2.add(txt_year, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 420, 110, 20));

        text_facturacion.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        text_facturacion.setText("Facturación Electronica CFDI");
        panelRound2.add(text_facturacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 440, -1, -1));

        img_user.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        img_user.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        img_user.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_user.png"))); // NOI18N
        panelRound2.add(img_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 180, 40, 50));

        img_block.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        img_block.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_block.png"))); // NOI18N
        panelRound2.add(img_block, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 270, 40, 40));

        mostrar_password.setFont(new java.awt.Font("Roboto Light", 1, 12)); // NOI18N
        mostrar_password.setText("   Mostrar contraseña");
        mostrar_password.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        mostrar_password.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mostrar_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostrar_passwordActionPerformed(evt);
            }
        });
        panelRound2.add(mostrar_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 320, 160, 20));

        logo_lb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo_escuela.png"))); // NOI18N
        panelRound2.add(logo_lb, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 190, 160));

        javax.swing.GroupLayout panel_loginLayout = new javax.swing.GroupLayout(panel_login);
        panel_login.setLayout(panel_loginLayout);
        panel_loginLayout.setHorizontalGroup(
            panel_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound2, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        panel_loginLayout.setVerticalGroup(
            panel_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound2, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        fondo.add(panel_login);
        panel_login.setBounds(118, 88, 700, 460);

        fondo_redondoLogin.setBackground(new java.awt.Color(0, 0, 0));
        fondo_redondoLogin.setPreferredSize(new java.awt.Dimension(708, 480));
        fondo_redondoLogin.setRoundBottomLeft(20);
        fondo_redondoLogin.setRoundBottomRight(20);
        fondo_redondoLogin.setRoundTopLeft(20);
        fondo_redondoLogin.setRoundTopRight(20);
        fondo_redondoLogin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        fondo.add(fondo_redondoLogin);
        fondo_redondoLogin.setBounds(110, 82, 715, 475);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fondo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 924, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fondo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void iniciar_sesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iniciar_sesionActionPerformed
        try {
            //Almacena los valores de entrada
            String usuario = usuario_entrada.getText().trim();
            String password = String.valueOf(password_entrada.getPassword());
            //valida que los campos no esten vacios
            if (usuario.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor ingrese usuario y contraseña", "Campos Vacios", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            //Creaciond de consulta segura con Prepared
            String query = "SELECT * FROM usuario WHERE usuario = ? and password = ?";
            //Preparar consulta
            PreparedStatement ps = cx.conectar().prepareStatement(query);
            ps.setString(1, usuario);
            ps.setString(2, password);
            //Executar consulta
            ResultSet rs = ps.executeQuery();
            
            //validar si se encontró el usuario
            if (rs.next()) {
              
                usuario_entrada.setText("");
                password_entrada.setText("");
                //Verifica que aun no exista ningun emisor
                String emisor_existente = "SELECT * FROM emisor";
                Statement consult_emisor=cx.conectar().createStatement();
                ResultSet rs_emisor = consult_emisor.executeQuery(emisor_existente);
                
                if(rs_emisor.next()){//Si existe un emisor te redirije al menu principal
                    LocalDate fechaInicioSesion = LocalDate.now();//obtener la fecha del inicio de sesion
                    LocalTime horaInicioSesion = LocalTime.now();
                    MenuPrincipal ventana = new MenuPrincipal();
                    ventana.setDatos(usuario,fechaInicioSesion,horaInicioSesion);
                    ventana.setVisible(true);
                    this.dispose();
                    
                }else{//Sino te dirije a la ventana de primir inicio
                    LocalDate fechaInicioSesion = LocalDate.now();//obtener la fecha del inicio de sesion
                    LocalTime horaInicioSesion = LocalTime.now();
                    PrimerInicio ventana = new PrimerInicio();
                    ventana.setDatos(usuario, fechaInicioSesion,horaInicioSesion);
                    ventana.setVisible(true);
                    this.setVisible(false);
                }
            }
            else {
                numIntentosFallidos++;
                if(numIntentosFallidos>=6){//si llega el numero de intentos bloquea y comienza el conteo
                    bloquear();
                    tiempoBloqueo.start();                  
                    mostrarTiempoEnDialogo();
                    return;
                }
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "Error de autenticación", JOptionPane.ERROR_MESSAGE);           
            }
        } catch (SQLException ex) {
            //Logger.getLogger(login_window.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("NO SE CONECTO A LA BASE DE DATOS");
        }
    }//GEN-LAST:event_iniciar_sesionActionPerformed
    
    private void mostrar_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrar_passwordActionPerformed
        //boton para mostrar y ocultar contraseña
        if (mostrar_password.isSelected()) {
            // Mostrar la contraseña en texto
            password_entrada.setEchoChar((char) 0);
            password_entrada.requestFocusInWindow();
        } else {
            // Ocultar la contraseña con el carácter por defecto (asterisco)
            password_entrada.setEchoChar('*');
            password_entrada.requestFocusInWindow();
        }
    }//GEN-LAST:event_mostrar_passwordActionPerformed

    private void btn_salirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salirMouseClicked
        if(bloqueado){//si el sistema esta bloqueado
            mostrarTiempoEnDialogo();//muestra el mensaje
            return;
        }
        Object[] opciones = {"Aceptar", "Cancelar"};
        if (SwingUtilities.isLeftMouseButton(evt)) {//click izquierdo
            //dialogo que pregunta si desea confirmar salir
            int opcionSeleccionada = JOptionPane.showOptionDialog(null,
                "¿Salir de la aplicación?", "Confirmación de salida", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
                null, opciones, opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"
            // Manejar las opciones seleccionadas
            if (opcionSeleccionada == JOptionPane.YES_OPTION) {
                System.exit(0); // Salir del programa
            } else {
                return;
            }
        }
    }//GEN-LAST:event_btn_salirMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if(bloqueado){//si el sistema esta bloqueado
            mostrarTiempoEnDialogo();//muestra el mensaje
            return;
        }
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
            // Cerrar la aplicación
            this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        } else {
            // Evitar que la ventana se cierre
            this.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_formWindowClosing

    private void bloquear(){
        bloqueado=true;
        usuario_entrada.setEditable(false);
        password_entrada.setEditable(false);
        iniciar_sesion.setEnabled(false);
    }
    
    void desbloquear(){
        bloqueado=false;
        usuario_entrada.setEditable(true);
        password_entrada.setEditable(true);
        iniciar_sesion.setEnabled(true);
        tiempoRestante=10;
        numIntentosFallidos=0;
    }
    
        // Método para mostrar el tiempo restante en un JOptionPane
    private void mostrarTiempoEnDialogo() {
        optionPane = new JOptionPane("Demasiados intentos fallidos\n"
                + "Contacte al soporte del sistema o espere: "+tiempoRestante+" segundos para realizar otra accion",
                JOptionPane.WARNING_MESSAGE);
        dialog = optionPane.createDialog(null,"Alerta");
        dialog.setModal(true); // No modal para permitir actualizaciones
        dialog.setVisible(true);
    }
    
    private void usuario_entradaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usuario_entradaKeyTyped
        if(usuario_entrada.getText().length()>=30){
            JOptionPane.showMessageDialog(null, "Numero maximo de caracteres alcanzados", "Mensaje", JOptionPane.WARNING_MESSAGE);
            evt.consume();
        }
    }//GEN-LAST:event_usuario_entradaKeyTyped

    private void password_entradaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_password_entradaKeyTyped
        if(password_entrada.getPassword().length>30){
            JOptionPane.showMessageDialog(null, "Numero maximo de caracteres alcanzados", "Mensaje", JOptionPane.WARNING_MESSAGE);
            evt.consume();
        }
    }//GEN-LAST:event_password_entradaKeyTyped

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
            java.util.logging.Logger.getLogger(login_window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(login_window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(login_window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(login_window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new login_window().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fecha;
    private javax.swing.JPanel barra_nav;
    private javax.swing.JPanel btn_salir;
    private javax.swing.JLabel etiqueta_password;
    private javax.swing.JLabel etiqueta_usuario;
    private javax.swing.JPanel fondo;
    private paneles.PanelRound fondo_redondoLogin;
    private javax.swing.JLabel hora_lb;
    private javax.swing.JLabel icon_salir;
    private javax.swing.JLabel img_block;
    private javax.swing.JLabel img_user;
    private javax.swing.JButton iniciar_sesion;
    private javax.swing.JLabel logo_lb;
    private javax.swing.JCheckBox mostrar_password;
    private paneles.PanelRound panelRound2;
    private javax.swing.JPanel panel_login;
    private javax.swing.JPasswordField password_entrada;
    private javax.swing.JLabel text_facturacion;
    private javax.swing.JLabel text_salir;
    private javax.swing.JLabel txt_year;
    private javax.swing.JTextField usuario_entrada;
    // End of variables declaration//GEN-END:variables

    private int tiempoRestante(long l) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
