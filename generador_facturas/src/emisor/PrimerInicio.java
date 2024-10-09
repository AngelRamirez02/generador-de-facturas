/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package emisor;

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

/**
 *
 * @author ar275
 */
public class PrimerInicio extends javax.swing.JFrame {
    
    private String usuario;
    /**
     * Creates new form PrimerInicio
     */
    public PrimerInicio() {
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

        fondo = new javax.swing.JPanel();
        contenedor = new javax.swing.JPanel();
        text_bienvenida1 = new javax.swing.JLabel();
        btn_continuar = new paneles.PanelRound();
        text_continuar = new javax.swing.JLabel();
        btn_salir = new paneles.PanelRound();
        text_salir = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        msj_text1 = new javax.swing.JLabel();
        logo_lb = new javax.swing.JLabel();
        mjs_text2 = new javax.swing.JLabel();
        text_bienvenida3 = new javax.swing.JLabel();
        barra_nav = new javax.swing.JPanel();
        Fecha = new javax.swing.JLabel();
        hora_lb = new javax.swing.JLabel();

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

        text_bienvenida1.setFont(new java.awt.Font("Roboto Light", 1, 30)); // NOI18N
        text_bienvenida1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        text_bienvenida1.setText("TE DAMOS LA BIENVENIDA AL SISTEMA DE FACTURACIÓN");
        contenedor.add(text_bienvenida1);
        text_bienvenida1.setBounds(0, 10, 910, 36);

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
        text_continuar.setText("Continuar");
        text_continuar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_continuar.add(text_continuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 50));

        contenedor.add(btn_continuar);
        btn_continuar.setBounds(680, 460, 160, 50);

        btn_salir.setBackground(new java.awt.Color(198, 54, 55));
        btn_salir.setRoundBottomLeft(10);
        btn_salir.setRoundBottomRight(10);
        btn_salir.setRoundTopLeft(10);
        btn_salir.setRoundTopRight(10);
        btn_salir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_salirMouseClicked(evt);
            }
        });
        btn_salir.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        text_salir.setFont(new java.awt.Font("Teko", 1, 36)); // NOI18N
        text_salir.setForeground(new java.awt.Color(255, 255, 255));
        text_salir.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        text_salir.setText("Salir");
        text_salir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_salir.add(text_salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 110, 50));

        contenedor.add(btn_salir);
        btn_salir.setBounds(90, 460, 110, 50);

        jLabel1.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("IMPORTANTE: ");
        contenedor.add(jLabel1);
        jLabel1.setBounds(30, 370, 97, 17);

        msj_text1.setFont(new java.awt.Font("Roboto Light", 1, 20)); // NOI18N
        msj_text1.setText("emitirá las facturas, como el dueño del colegio, etc.");
        contenedor.add(msj_text1);
        msj_text1.setBounds(30, 420, 840, 30);

        logo_lb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo_escuela.png"))); // NOI18N
        logo_lb.setText("jLabel2");
        logo_lb.setMaximumSize(new java.awt.Dimension(400, 400));
        logo_lb.setMinimumSize(new java.awt.Dimension(400, 400));
        logo_lb.setPreferredSize(new java.awt.Dimension(400, 600));
        contenedor.add(logo_lb);
        logo_lb.setBounds(300, 90, 310, 260);

        mjs_text2.setFont(new java.awt.Font("Roboto Light", 1, 20)); // NOI18N
        mjs_text2.setText("Antes de comenzar a generar facturas, necesitas registrar los datos de la persona que");
        contenedor.add(mjs_text2);
        mjs_text2.setBounds(30, 390, 840, 30);

        text_bienvenida3.setFont(new java.awt.Font("Roboto Light", 1, 30)); // NOI18N
        text_bienvenida3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        text_bienvenida3.setText(" DEL \"INSTITUTO ANDRÉS MANUEL LÓPEZ OBRADOR\"");
        contenedor.add(text_bienvenida3);
        text_bienvenida3.setBounds(80, 50, 755, 36);

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
    
    public void setUsuario(String usuario){
        this.usuario=usuario;
    }
    
    private void btn_salirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salirMouseClicked
        //boton para salir del programa
        Object[] opciones = {"Salir", "Cancelar"};
        if (SwingUtilities.isLeftMouseButton(evt)) {
            //dialogo que pregunta si desea confirmar salir
            int opcionSeleccionada = JOptionPane.showOptionDialog(null,
                    "¿Deseas salir de la aplicación?", "Confirmación de salida", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, opciones, opciones[1]); // Por defecto, la opción seleccionada es "Cancelar"
            // Manejar las opciones seleccionadas
            if (opcionSeleccionada == JOptionPane.YES_OPTION) {
                System.exit(0); // Salir del programa
            } else {
                return;
            }
        }
    }//GEN-LAST:event_btn_salirMouseClicked

    
    private void btn_continuarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_continuarMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt)) {
            AltaEmisorPrim ventana = new AltaEmisorPrim();
            ventana.setUsuario(usuario);
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
            java.util.logging.Logger.getLogger(PrimerInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrimerInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrimerInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrimerInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrimerInicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fecha;
    private javax.swing.JPanel barra_nav;
    private paneles.PanelRound btn_continuar;
    private paneles.PanelRound btn_salir;
    private javax.swing.JPanel contenedor;
    private javax.swing.JPanel fondo;
    private javax.swing.JLabel hora_lb;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel logo_lb;
    private javax.swing.JLabel mjs_text2;
    private javax.swing.JLabel msj_text1;
    private javax.swing.JLabel text_bienvenida1;
    private javax.swing.JLabel text_bienvenida3;
    private javax.swing.JLabel text_continuar;
    private javax.swing.JLabel text_salir;
    // End of variables declaration//GEN-END:variables
}
