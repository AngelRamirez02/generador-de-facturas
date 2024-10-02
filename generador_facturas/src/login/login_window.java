/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package login;

import java.sql.*;
import conexion.conexion;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.Timer;

/**
 *
 * @author ar275
 */
public class login_window extends javax.swing.JFrame {

    conexion cx;//variable para la conexion a la base de datos

    public login_window() {
        initComponents();
        
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
        panel_login = new javax.swing.JPanel();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Instituto Manuel Andres Lopez Obrador - Iniciar Sesión");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        fondo.setBackground(new java.awt.Color(255, 255, 255));
        fondo.setPreferredSize(new java.awt.Dimension(900, 600));

        barra_nav.setBackground(new java.awt.Color(198, 54, 55));
        barra_nav.setName("instituo Manuel Andres Lopez Obrador - Login"); // NOI18N
        barra_nav.setPreferredSize(new java.awt.Dimension(0, 70));
        barra_nav.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Fecha.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Fecha.setForeground(new java.awt.Color(255, 255, 255));
        Fecha.setText("Fecha: ");
        barra_nav.add(Fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 260, -1));

        hora_lb.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        hora_lb.setForeground(new java.awt.Color(255, 255, 255));
        hora_lb.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        hora_lb.setText("Hora: ");
        barra_nav.add(hora_lb, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 110, -1));

        panel_login.setBackground(new java.awt.Color(255, 255, 255));
        panel_login.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        panel_login.setPreferredSize(new java.awt.Dimension(700, 450));
        panel_login.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        usuario_entrada.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        usuario_entrada.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        usuario_entrada.setToolTipText("");
        usuario_entrada.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        panel_login.add(usuario_entrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 170, 300, 45));

        iniciar_sesion.setBackground(new java.awt.Color(102, 102, 102));
        iniciar_sesion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        iniciar_sesion.setForeground(new java.awt.Color(255, 255, 255));
        iniciar_sesion.setText("Iniciar Sesión");
        iniciar_sesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        iniciar_sesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iniciar_sesionActionPerformed(evt);
            }
        });
        panel_login.add(iniciar_sesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 360, 168, 45));

        password_entrada.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        password_entrada.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        password_entrada.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        panel_login.add(password_entrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 265, 300, 45));

        etiqueta_usuario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        etiqueta_usuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiqueta_usuario.setLabelFor(usuario_entrada);
        etiqueta_usuario.setText("Usuario");
        etiqueta_usuario.setAlignmentX(0.5F);
        panel_login.add(etiqueta_usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 144, 80, -1));

        etiqueta_password.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        etiqueta_password.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiqueta_password.setLabelFor(password_entrada);
        etiqueta_password.setText("Contraseña");
        panel_login.add(etiqueta_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(416, 239, 84, -1));

        txt_year.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        txt_year.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_year.setText("2024 - MTAL SOLUTIONS");
        panel_login.add(txt_year, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 420, 110, 20));

        text_facturacion.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        text_facturacion.setText("Facturación Electronica CFDI");
        panel_login.add(text_facturacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 440, -1, -1));

        img_user.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        img_user.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        img_user.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_user.png"))); // NOI18N
        panel_login.add(img_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 170, 40, 50));

        img_block.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        img_block.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_block.png"))); // NOI18N
        panel_login.add(img_block, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 270, 40, 40));

        mostrar_password.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        mostrar_password.setText("   Mostrar contraseña");
        mostrar_password.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        mostrar_password.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mostrar_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostrar_passwordActionPerformed(evt);
            }
        });
        panel_login.add(mostrar_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 320, -1, 20));

        javax.swing.GroupLayout fondoLayout = new javax.swing.GroupLayout(fondo);
        fondo.setLayout(fondoLayout);
        fondoLayout.setHorizontalGroup(
            fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoLayout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addComponent(panel_login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(106, Short.MAX_VALUE))
            .addComponent(barra_nav, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        fondoLayout.setVerticalGroup(
            fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoLayout.createSequentialGroup()
                .addComponent(barra_nav, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panel_login, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 52, Short.MAX_VALUE))
        );

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
                JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso", "Datos correctos", JOptionPane.INFORMATION_MESSAGE);
                usuario_entrada.setText("");
                password_entrada.setText("");
            } else {
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
        } else {
            // Ocultar la contraseña con el carácter por defecto (asterisco)
            password_entrada.setEchoChar('*');
        }
    }//GEN-LAST:event_mostrar_passwordActionPerformed

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
    private javax.swing.JLabel etiqueta_password;
    private javax.swing.JLabel etiqueta_usuario;
    private javax.swing.JPanel fondo;
    private javax.swing.JLabel hora_lb;
    private javax.swing.JLabel img_block;
    private javax.swing.JLabel img_user;
    private javax.swing.JButton iniciar_sesion;
    private javax.swing.JCheckBox mostrar_password;
    private javax.swing.JPanel panel_login;
    private javax.swing.JPasswordField password_entrada;
    private javax.swing.JLabel text_facturacion;
    private javax.swing.JLabel txt_year;
    private javax.swing.JTextField usuario_entrada;
    // End of variables declaration//GEN-END:variables
}
