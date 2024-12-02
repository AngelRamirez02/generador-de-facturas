/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package facturacion;

/**
 *
 * @author ar275
 */

public class NumeroALetras {

    private static final String[] UNIDADES = {
        "", "UNO", "DOS", "TRES", "CUATRO", "CINCO", "SEIS", "SIETE", "OCHO", "NUEVE"
    };
    private static final String[] DECENAS = {
        "", "DIEZ", "VEINTE", "TREINTA", "CUARENTA", "CINCUENTA", "SESENTA", "SETENTA", "OCHENTA", "NOVENTA"
    };
    private static final String[] ESPECIALES = {
        "ONCE", "DOCE", "TRECE", "CATORCE", "QUINCE", "DIECISÉIS", "DIECISIETE", "DIECIOCHO", "DIECINUEVE"
    };
    private static final String[] CENTENAS = {
        "", "CIEN", "DOSCIENTOS", "TRESCIENTOS", "CUATROCIENTOS", 
        "QUINIENTOS", "SEISCIENTOS", "SETECIENTOS", "OCHOCIENTOS", "NOVECIENTOS"
    };

    public String convertirNumero(double numero) {
        if (numero > 999999999.99) {
            throw new IllegalArgumentException("El número no puede ser mayor a 999,999,999.99");
        }

        long parteEntera = (long) numero; // Parte entera del número
        int centavos = (int) Math.round((numero - parteEntera) * 100); // Parte decimal como centavos

        return convertirParteEntera(parteEntera) + " MXN " + String.format("%02d", centavos) + "/100";
    }

    private String convertirParteEntera(long numero) {
        if (numero == 0) {
            return "CERO";
        }

        StringBuilder resultado = new StringBuilder();

        long millones = numero / 1000000;
        if (millones > 0) {
            resultado.append(millones == 1 ? "UN MILLÓN " : convertirCentenas(millones) + " MILLONES ");
            numero %= 1000000;
        }

        long miles = numero / 1000;
        if (miles > 0) {
            resultado.append(miles == 1 ? "MIL " : convertirCentenas(miles) + " MIL ");
            numero %= 1000;
        }

        if (numero > 0) {
            resultado.append(convertirCentenas(numero));
        }

        return resultado.toString().trim();
    }

    private String convertirCentenas(long numero) {
        StringBuilder resultado = new StringBuilder();

        long centenas = numero / 100;
        if (centenas > 0) {
            resultado.append(centenas == 1 && numero % 100 == 0 ? "CIEN " : CENTENAS[(int) centenas] + " ");
        }

        numero %= 100;
        if (numero > 10 && numero < 20) {
            resultado.append(ESPECIALES[(int) numero - 11]);
        } else {
            long decenas = numero / 10;
            if (decenas > 0) {
                resultado.append(DECENAS[(int) decenas]);
                if (numero % 10 > 0) {
                    resultado.append(" Y ");
                }
            }

            long unidades = numero % 10;
            if (unidades > 0) {
                resultado.append(UNIDADES[(int) unidades]);
            }
        }

        return resultado.toString().trim();
    }

}
