/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validacion;

import com.toedter.calendar.JDateChooser;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author ar275
 */
public class Validacion {

    public boolean nombresValidos(String nombre) {
        String regex = "^[a-zA-ZÁÉÍÓÚÑáéíóúñ ]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(nombre);
        return matcher.matches();//retorna el resultado de evaluar el correo con la expresion regular
    }

    public boolean apellidoValido(String apelido) {
        String regex = "^[a-zA-ZÁÉÍÓÚÑáéíóúñ ]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(apelido);
        return matcher.matches();//retorna el resultado de evaluar el correo con la expresion regular
    }
    public boolean cpValido(String cp) {
        String regex = "^\\d{5}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cp);
        return matcher.matches();//retorna el resultado de evaluar el correo con la expresion regular
    }

    public boolean correo_valido(String correo) {//Valida correo electronicos 
        String regex = "[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,5}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();//retorna el resultado de evaluar el correo con la expresion regular
    }
    
public String crear_rfc(String nombres, String apellido_paterno, String apellido_materno, Calendar fecha_nacimiento, String homoclave) {
    // Obtener día, mes y año desde el Calendar que recibes
    int dia = fecha_nacimiento.get(Calendar.DAY_OF_MONTH);
    int mes = fecha_nacimiento.get(Calendar.MONTH) + 1; // Los meses son 0-11
    int year = fecha_nacimiento.get(Calendar.YEAR);
    
    //Variable que almacena el RFC creado
    StringBuilder rfc = new StringBuilder();

    // Obtener primera letra del apellido paterno
    char Primerletra_apellidoPaterno = apellido_paterno.toUpperCase().charAt(0);
    rfc.append(Primerletra_apellidoPaterno);

    // Si la primera letra es una vocal, agregar la siguiente letra
    if (isVowel(Primerletra_apellidoPaterno)) {
        rfc.append(apellido_paterno.toUpperCase().charAt(1));
    } else {
        // Si no, buscar la primera vocal en el apellido paterno y agregarla
        for (int i = 1; i < apellido_paterno.length(); i++) {
            char c = apellido_paterno.toUpperCase().charAt(i);
            if (isVowel(c)) {
                rfc.append(c);
                break;
            }
        }
    }

    // Obtener primera letra del apellido materno
    rfc.append(apellido_materno.toUpperCase().charAt(0));
    // Obtener primera letra del nombre
    rfc.append(nombres.toUpperCase().charAt(0));

    // Obtener la fecha en formato AAMMDD
    rfc.append(String.format("%02d", year % 100)); // Últimos 2 dígitos del año
    rfc.append(String.format("%02d", mes)); // Mes
    rfc.append(String.format("%02d", dia)); // Día

    // Agregar la homoclave (por ejemplo, un valor aleatorio o fijo)
    rfc.append(homoclave);

    return rfc.toString();
}
    
    private static boolean isVowel(char c) {
        return "AEIOU".indexOf(Character.toUpperCase(c)) >= 0;
    }
}
