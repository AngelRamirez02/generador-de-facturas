/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validacion;

import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    
    public boolean numInteriorExteriorValido(String num) {
        String regex = "^[A-Za-z0-9\\s\\-\\/]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(num);
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
    
    // Método para verificar si una CURP coincide con los datos proporcionados
    public static boolean verificarCURP(String curp, String nombre, String apellidoPaterno, String apellidoMaterno, Date fechaNacimiento) {
        // Convertir la fecha de nacimiento a formato AAAA-MM-DD
        String fechaNacimientoStr = convertirFecha(fechaNacimiento);

        // Generar la CURP esperada usando los datos proporcionados
        String curpGenerada = generarCURP(nombre, apellidoPaterno, apellidoMaterno, fechaNacimientoStr);
        
        // Comparar los primeros 10 caracteres de la CURP generada con la CURP ingresada
        return curp.substring(0, 10).equalsIgnoreCase(curpGenerada.substring(0, 10));
    }

    // Método para convertir una fecha a formato AAAA-MM-DD
    private static String convertirFecha(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        return formato.format(fecha);
    }

    // Método para generar la CURP (basado en lo que puedes verificar)
    private static String generarCURP(String nombre, String apellidoPaterno, String apellidoMaterno, String fechaNacimiento) {
        // Eliminar partículas como "De", "La", "Del", etc. del apellido paterno
        apellidoPaterno = eliminarParticulasApellido(apellidoPaterno);

        // Paso 1: Primer letra del apellido paterno
        String curp = Character.toString(apellidoPaterno.charAt(0)).toUpperCase();

        // Paso 2: Primera vocal interna del apellido paterno
        curp += obtenerPrimeraVocal(apellidoPaterno);

        // Paso 3: Primera letra del apellido materno o 'X' si no tiene
        if (!apellidoMaterno.isEmpty()) {
            curp += Character.toString(apellidoMaterno.charAt(0)).toUpperCase();
        } else {
            curp += "X";
        }

        // Paso 4: Primera letra del nombre (excepto "José" o "María")
        curp += obtenerPrimeraLetraNombre(nombre);

        // Paso 5: Año, mes y día de la fecha de nacimiento en formato AAMMDD
        curp += fechaNacimiento.substring(2, 4); // Año
        curp += fechaNacimiento.substring(5, 7); // Mes
        curp += fechaNacimiento.substring(8, 10); // Día

        return curp;
    }

    // Método para eliminar partículas como "De", "La", "Del" en apellidos
    private static String eliminarParticulasApellido(String apellido) {
        String[] particulas = {"DE", "LA", "DEL", "LAS"};
        for (String particula : particulas) {
            apellido = apellido.toUpperCase().replaceFirst("^" + particula + " ", "");
        }
        return apellido;
    }

    // Método para obtener la primera vocal interna de una cadena
    private static String obtenerPrimeraVocal(String cadena) {
        for (int i = 1; i < cadena.length(); i++) {
            char letra = Character.toLowerCase(cadena.charAt(i));
            if ("aeiou".indexOf(letra) != -1) {
                return Character.toString(cadena.charAt(i)).toUpperCase();
            }
        }
        return "X";
    }

    // Método para obtener la primera letra del nombre (considerando el caso de "José" o "María")
    private static String obtenerPrimeraLetraNombre(String nombre) {
        String[] nombres = nombre.split(" ");
        if (nombres[0].equalsIgnoreCase("José") || nombres[0].equalsIgnoreCase("María")) {
            return Character.toString(nombres[1].charAt(0)).toUpperCase();
        } else {
            return Character.toString(nombres[0].charAt(0)).toUpperCase();
        }
    }
}

