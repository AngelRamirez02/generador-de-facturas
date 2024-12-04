/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package facturacion;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.UUID;

/**
 *
 * @author ar275
 */
public class Sellos {
    
    //Genera el folio del SAT
    public String generarFolioSAT() {
        // Generar un UUID aleatorio
        UUID uuid = UUID.randomUUID();
        
        // Convertir el UUID a cadena en formato estándar
        return uuid.toString().toUpperCase();
    }
    
    public String generarNumeroSerieEmisor() {
        // Crear un número aleatorio dentro de un rango de 1 a 9999999999 (10 dígitos)
        long numeroAleatorio = (long) (Math.random() * 10000000000L);

        // Formatear el número para asegurar que tenga 20 dígitos
        DecimalFormat df = new DecimalFormat("00000000000000000000");  // 20 dígitos
        return df.format(numeroAleatorio);
    }
    
    public String generarNumeroSerieSAT() {
        // Crear un número aleatorio dentro de un rango de 1 a 9999999999 (10 dígitos)
        long numeroAleatorio = (long) (Math.random() * 10000000000L);

        // Formatear el número para asegurar que tenga 20 dígitos
        DecimalFormat df = new DecimalFormat("00000000000000000000");  // 20 dígitos
        return df.format(numeroAleatorio);
    }
 
    public String generarCadenaSAT(String rfcEmisor, String numSerieEmisor, String fecha ) {
        // Datos de entrada
        String version = "1.1";
        String uuid = "7733d569-d3e7-4084-a167-51ab5b1337f6";
        String sello = generarSello();
        return "||" + version + "|" + uuid + "|" + fecha + "|" + rfcEmisor + "|" + sello + "|" + numSerieEmisor + "|";
    }
    
    public String generarSello() {
        Random random = new Random();
         int indiceAleatorio;
        String sello="";
        String elementos[] = {
            "C", "6", "Z", "7", "!", "o", "1", "F", "*", "H", "y", "=", "J", "+", "9", "B",
            "k", "W", "l", "v", "r", "a", "t", "d", "%", "U", "b", "P", "g", "m", "i", "E",
            "4", "L", "p", "8", "O", "T", "q", "s", "x", "h", "-", "e", "n", "z", "G", "3",
            "w", "N", "A", "f", "c", "0", "M", "V", "u", "Y", "I", "R", "/", "2", "X", "Q",
            "&", "K", "5", "D", "j", "<", ">", "|", "^", "~"
        };

        for(int i=0; i<344; i++){
        indiceAleatorio = random.nextInt(elementos.length);
        // Obtener el valor correspondiente
        sello+=elementos[indiceAleatorio];
        }
        
        return sello;
    }
    
    public static void main(String[] args) {
        Sellos s = new Sellos();
        System.out.println(s.generarSello());
    }

}
