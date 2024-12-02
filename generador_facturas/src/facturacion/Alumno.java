/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package facturacion;

/**
 *
 * @author ar275
 */
public class Alumno {

    private String curp;
    private String nombres;
    private String apellido_paterno;
    private String apellido_materno;
    private String nivel_escolar;
    private String grado_escolar;

    public Alumno(String curp, String nombres, String apellido_paterno, String apellido_materno, String nivel_escolar, String grado_escolar) {
        this.curp = curp;
        this.nombres = nombres;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.nivel_escolar = nivel_escolar;
        this.grado_escolar = grado_escolar;
    }

    public String getCurp() {
        return curp;
    }

    public String getNombreCompletoMayus(){
        return (nombres+" "+apellido_paterno+" "+apellido_materno).toUpperCase();
    }
    
    public String getNombres() {
        return nombres;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public String getNivel_escolar() {
        return nivel_escolar;
    }

    public String getGrado_escolar() {
        return grado_escolar;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    public void setNivel_escolar(String nivel_escolar) {
        this.nivel_escolar = nivel_escolar;
    }

    public void setGrado_escolar(String grado_escolar) {
        this.grado_escolar = grado_escolar;
    }
}
