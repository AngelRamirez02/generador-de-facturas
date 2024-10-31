/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TablaPersonalizada;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author ar275
 */
public class TablaPersonalizada extends DefaultTableCellRenderer {

    public TablaPersonalizada() {
       //Color para la cabecera de la tabla
        Color colorCabeceraTabla = Color.decode("#C94545");
        setFont(new Font("Roboto Light", Font.BOLD, 24));
        setOpaque(true);
        setBackground(colorCabeceraTabla); // Color de fondo
        setForeground(Color.WHITE); // Color del texto
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);//
        // Borde solo en los lados izquierdo y derecho de la celda
        //setBorder(new MatteBorder(0, 1, 0, 1, Color.BLACK)); // Grosor de 1 píxel y color negro en los bordes verticales
        // Verifica si el valor es una cadena y formatea el texto con salto de línea en HTML
//        if (value instanceof String) {
//            value = "<html><center>" + value.toString().replace(" ", "<br>") + "</center></html>";
//            setText((String) value);
//        }
        return this;
    }
}