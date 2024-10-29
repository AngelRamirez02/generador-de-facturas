/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package login;

import emisor.*;
import conexion.conexion;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import menu.MenuPrincipal;

/**
 *
 * @author ar275
 */
public class Bienvenida extends javax.swing.JFrame {
    
    private String usuario;
    LocalDate fechaInicioSesion;
    LocalTime horaInicioSesion;
    /**
     * Creates new form PrimerInicio
     */
    public Bienvenida() {
        initComponents();
        //Personalizar el tamaño del logo
         Image logo_img= Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/logo_escuela.png"));
        logo_lb.setIcon(new ImageIcon(logo_img.getScaledInstance(logo_lb.getWidth(), logo_lb.getHeight(), Image.SCALE_SMOOTH)));
        
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
            }
        });
        
        this.addWindowStateListener(new WindowStateListener() {
            int panelWidth = contenedor.getWidth();
            int panel_heigth = contenedor.getHeight()-barra_nav.getHeight();
            int newX = (fondo.getWidth() - panelWidth) / 2; // Calcular la nueva posición en 
            int newY = (fondo.getHeight() - panel_heigth)/2;
            @Override
            public void windowStateChanged(WindowEvent e) {
                barra_nav.setSize(fondo.getWidth(), barra_nav.getHeight());
                contenedor.setLocation(newX, newY);
            }
        });
        timer.start();
        
        //Mostrar ventana en el centro
        this.setLocationRelativeTo(null);//La ventana aparece en el centro
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

        jLabel7 = new javax.swing.JLabel();
        fondo = new javax.swing.JPanel();
        contenedor = new javax.swing.JPanel();
        btn_continuar = new paneles.PanelRound();
        text_continuar = new javax.swing.JLabel();
        msj_text1 = new javax.swing.JLabel();
        logo_lb = new javax.swing.JLabel();
        mjs_text2 = new javax.swing.JLabel();
        text_bienvenida3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        barra_nav = new javax.swing.JPanel();
        Fecha = new javax.swing.JLabel();
        hora_lb = new javax.swing.JLabel();

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("CLAVE 123456789");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Instituto Manuel Andres Lopez Obrador - Bienvenida");
        setMinimumSize(new java.awt.Dimension(910, 650));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        fondo.setBackground(new java.awt.Color(255, 255, 255));
        fondo.setMinimumSize(new java.awt.Dimension(0, 620));
        fondo.setPreferredSize(new java.awt.Dimension(924, 600));
        fondo.setLayout(null);

        contenedor.setBackground(new java.awt.Color(255, 255, 255));
        contenedor.setLayout(null);

        btn_continuar.setBackground(new java.awt.Color(198, 54, 55));
        btn_continuar.setRoundBottomLeft(10);
        btn_continuar.setRoundBottomRight(10);
        btn_continuar.setRoundTopLeft(10);
        btn_continuar.setRoundTopRight(10);
        btn_continuar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_continuarMouseClicked(evt);
            }
        });
        btn_continuar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        text_continuar.setFont(new java.awt.Font("Teko", 1, 36)); // NOI18N
        text_continuar.setForeground(new java.awt.Color(255, 255, 255));
        text_continuar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        text_continuar.setText("ENTRAR");
        text_continuar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_continuar.add(text_continuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 50));

        contenedor.add(btn_continuar);
        btn_continuar.setBounds(350, 470, 160, 50);

        msj_text1.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        msj_text1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        msj_text1.setText("Bienvenido al Sistema de Facturación Escolar");
        contenedor.add(msj_text1);
        msj_text1.setBounds(-10, 420, 900, 30);

        logo_lb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo_escuela.png"))); // NOI18N
        logo_lb.setText("jLabel2");
        logo_lb.setMaximumSize(new java.awt.Dimension(400, 400));
        logo_lb.setMinimumSize(new java.awt.Dimension(400, 400));
        logo_lb.setPreferredSize(new java.awt.Dimension(400, 600));
        contenedor.add(logo_lb);
        logo_lb.setBounds(310, 180, 250, 220);

        mjs_text2.setFont(new java.awt.Font("Roboto Black", 1, 30)); // NOI18N
        mjs_text2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mjs_text2.setText("“Educando hoy, transformando el mañana”");
        contenedor.add(mjs_text2);
        mjs_text2.setBounds(-10, 40, 910, 30);

        text_bienvenida3.setFont(new java.awt.Font("Roboto Black", 1, 36)); // NOI18N
        text_bienvenida3.setForeground(new java.awt.Color(134, 10, 10));
        text_bienvenida3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        text_bienvenida3.setText("INSTITUTO ANDRÉS MANUEL LÓPEZ OBRADOR");
        contenedor.add(text_bienvenida3);
        text_bienvenida3.setBounds(-20, 0, 910, 43);

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("SECCIÓN SECUNDARIA");
        contenedor.add(jLabel2);
        jLabel2.setBounds(620, 140, 210, 17);

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("SECCIÓN JARDIN DE NIÑOS");
        contenedor.add(jLabel3);
        jLabel3.setBounds(70, 140, 210, 17);

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("CLAVE 123456789");
        contenedor.add(jLabel4);
        jLabel4.setBounds(70, 160, 210, 17);

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("SECCIÓN PRIMARIA");
        contenedor.add(jLabel5);
        jLabel5.setBounds(340, 140, 210, 17);

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("CLAVE 123456789");
        contenedor.add(jLabel6);
        jLabel6.setBounds(340, 160, 210, 17);

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("CLAVE 123456789");
        contenedor.add(jLabel8);
        jLabel8.setBounds(620, 160, 210, 17);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Calzada pie de la cuesta No 12 calle vista al mar C.P. 39800 Tel 3-90-18-23, Acapulco, Gro.");
        contenedor.add(jLabel1);
        jLabel1.setBounds(0, 90, 890, 30);

        fondo.add(contenedor);
        contenedor.setBounds(20, 70, 890, 530);

        barra_nav.setBackground(new java.awt.Color(198, 54, 55));
        barra_nav.setName("instituo Manuel Andres Lopez Obrador - Login"); // NOI18N
        barra_nav.setPreferredSize(new java.awt.Dimension(0, 70));
        barra_nav.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Fecha.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        Fecha.setForeground(new java.awt.Color(255, 255, 255));
        Fecha.setText("Fecha: ");
        barra_nav.add(Fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 260, -1));

        hora_lb.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        hora_lb.setForeground(new java.awt.Color(255, 255, 255));
        hora_lb.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        hora_lb.setText("Hora: ");
        barra_nav.add(hora_lb, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 140, -1));

        fondo.add(barra_nav);
        barra_nav.setBounds(0, 0, 912, 70);

        getContentPane().add(fondo, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    public void setDatos(String usuario, LocalDate fechaInicioSesion, LocalTime horaInicioSesion){
        this.usuario=usuario;
        this.fechaInicioSesion = fechaInicioSesion;
        this.horaInicioSesion = horaInicioSesion;
    }
    
    
    private void btn_continuarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_continuarMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt)) {
           login_window ventana = new login_window();
           ventana.setVisible(true);
           this.dispose();
        }
    }//GEN-LAST:event_btn_continuarMouseClicked

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
                conexion cx = new conexion();
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
            java.util.logging.Logger.getLogger(Bienvenida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Bienvenida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Bienvenida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Bienvenida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Bienvenida().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fecha;
    private javax.swing.JPanel barra_nav;
    private paneles.PanelRound btn_continuar;
    private javax.swing.JPanel contenedor;
    private javax.swing.JPanel fondo;
    private javax.swing.JLabel hora_lb;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel logo_lb;
    private javax.swing.JLabel mjs_text2;
    private javax.swing.JLabel msj_text1;
    private javax.swing.JLabel text_bienvenida3;
    private javax.swing.JLabel text_continuar;
    // End of variables declaration//GEN-END:variables
}
