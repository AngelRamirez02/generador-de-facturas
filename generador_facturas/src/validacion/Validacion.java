/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validacion;

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
    // Arreglo con los códigos de los estados de México

    public String[] estados = {
        "AS", "BC", "BS", "CC", "CL", "CM", "CS", "CH", "DF",
        "DG", "GT", "GR", "HG", "JC", "MC", "MN", "MS", "NT",
        "NL", "OC", "PL", "QT", "QR", "SP", "SL", "SR", "TC",
        "TL", "TS", "VZ", "YN", "ZS"
    };

    public boolean nombresValidos(String nombre) {
        String regex = "^(De|Del|Los|Las|La)?\\s?[A-ZÁÉÍÓÚÑ][a-záéíóúñ]*(\\s[A-ZÁÉÍÓÚÑ][a-záéíóúñ]*)*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(nombre);
        return matcher.matches();//retorna el resultado de evaluar el correo con la expresion regular
    }

    public boolean apellidoValido(String apelido) {
        String regex = "^(De|Del|Los|Las|La)?\\s?[A-ZÁÉÍÓÚÑ][a-záéíóúñ]*(\\s[A-ZÁÉÍÓÚÑ][a-záéíóúñ]*)*$";
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
        String regex = "^[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@(gmail|yahoo|hotmail|outlook|acapulco)([.][a-zA-Z0-9_]+)?[.](com|net|org|edu|gov|mx|tecnm)$";
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

    public String formatearNombresApellidos(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            return ""; // Maneja el caso de una cadena nula o vacía
        }

        String nombreFormateado = "";
        String[] palabras = nombre.trim().split("\\s+");
        for (String s : palabras) {
            if (s.length() > 0) {
                String aux = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
                nombreFormateado += aux + " ";
            }
        }
        System.out.println(nombreFormateado);
        return nombreFormateado.trim();//elimina el ultimo espacio
        
    }


    public String crear_rfc(String nombres, String apellido_paterno, String apellido_materno, Calendar fecha_nacimiento, String homoclave) {
        //Se eliminan las particulas de los Apellidos
        apellido_paterno = eliminarParticulasApellido(apellido_paterno);
        apellido_materno = eliminarParticulasApellido(apellido_materno);
        nombres = eliminarParticulasApellido(nombres);
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
        //if (isVowel(Primerletra_apellidoPaterno)) {
          //  rfc.append(apellido_paterno.toUpperCase().charAt(1));
        //} else {
            // Si no, buscar la primera vocal en el apellido paterno y agregarla
            for (int i = 1; i < apellido_paterno.length(); i++) {
                char c = apellido_paterno.toUpperCase().charAt(i);
                if (isVowel(c)) {
                    rfc.append(c);
                    break;
                }
            }
        //}

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
        System.out.println(rfc);
        return rfc.toString();
    }

    private static boolean isVowel(char c) {
        return "AEIOU".indexOf(Character.toUpperCase(c)) >= 0;
    }

    // Método para verificar si una CURP coincide con los datos proporcionados
    public  boolean verificarCURP(String curp, String nombre, String apellidoPaterno, String apellidoMaterno, Date fechaNacimiento) {
        boolean estado_valido=false;
        // Convertir la fecha de nacimiento a formato AAAA-MM-DD
        String fechaNacimientoStr = convertirFecha(fechaNacimiento);
        nombre=EliminarNombresComunes(nombre);

        // Generar la CURP esperada usando los datos proporcionados
        String curpGenerada = generarCURP(nombre, apellidoPaterno, apellidoMaterno, fechaNacimientoStr);
        if(!curp.substring(0, 10).equalsIgnoreCase(curpGenerada.substring(0, 10))){
            JOptionPane.showMessageDialog(null,"La CURP no coincide con el nombre, apellidos o con la fecha de nacimiento del alumno", "CURP no valida", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        String aux[] = curp.split(curpGenerada);
        if (aux.length > 1) {
            String curp_part2 = aux[1];

            // Verifica que el primer carácter de `curp_part2` sea 'H' o 'M'
            if (curp_part2.charAt(0) != 'H' && curp_part2.charAt(0) != 'M') {
                JOptionPane.showMessageDialog(null,"Se esperaba H o M en la posicion 11 de la CURP", "Sexo no valido en la curp", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            String estado = "" + curp_part2.charAt(1) + curp_part2.charAt(2);
            
            for (String s: estados){
                if (estado.equals(s)) {
                    estado_valido=true;
                    break;
                }
            }
            if(!estado_valido){
                JOptionPane.showMessageDialog(null,"El estado "+estado+" no es valido", "Estado no valido", JOptionPane.WARNING_MESSAGE);
                return false;
            }          
            String consonantesCurp = ""+curp_part2.charAt(3)+curp_part2.charAt(4)+curp_part2.charAt(5);
            String consonantes=primeraConsonanteInterna(eliminarParticulasApellido(apellidoPaterno));
            consonantes+=primeraConsonanteInterna(eliminarParticulasApellido(apellidoMaterno));
            consonantes+=primeraConsonanteInterna(eliminarParticulasApellido(nombre));
            System.out.println(consonantes);
            if(!consonantesCurp.equals(consonantes)){
                JOptionPane.showMessageDialog(null,"Las consonates: "+consonantesCurp+" no pertenecen a los apellidos ni al nombre", "Consonantes no validas", JOptionPane.WARNING_MESSAGE);
                return false;
            }                  
        }
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
        apellidoMaterno = eliminarParticulasApellido(apellidoMaterno);

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

        System.out.println(curp);
        return curp;
    }

    // Método para eliminar partículas como "De", "La", "Del" en apellidos
    private static String eliminarParticulasApellido(String apellido) {
        String[] particulas = {"DE", "LA", "DEL", "LAS", "LOS", "SAN", "SANTA", "Y"};
        for (String particula : particulas) {
            apellido = apellido.toUpperCase().replaceFirst("^" + particula + "\\s+", "");
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
        if (nombres[0].equalsIgnoreCase("José") || nombres[0].equalsIgnoreCase("María") || nombres[0].equals("Maria") || nombres[0].equals("Jose")) {
            return Character.toString(nombres[1].charAt(0)).toUpperCase();
        } else {
            return Character.toString(nombres[0].charAt(0)).toUpperCase();
        }
    }

    //metodo para eliminar nombres comunes
    private static String EliminarNombresComunes(String nombre) {
        String[] nombres = nombre.split(" ");
        if (nombres[0].equalsIgnoreCase("José") || nombres[0].equalsIgnoreCase("María") || nombres[0].equals("Maria") || nombres[0].equals("Jose")) {
            return nombres[1];
        } else {
            return nombres[0];
        }
    }
    
        private static String primeraConsonanteInterna(String palabra) {
        String vocales = "AEIOUaeiou";
        for (int i = 1; i < palabra.length(); i++) { // Comienza desde el índice 1
            char c = palabra.charAt(i);
            if (vocales.indexOf(c) == -1) { // Si no es vocal
                return String.valueOf(c).toUpperCase(); // Devuelve la primera consonante interna en mayúscula
            }
        }
        return ""; // Devuelve cadena vacía si no hay consonante interna
    }
}
