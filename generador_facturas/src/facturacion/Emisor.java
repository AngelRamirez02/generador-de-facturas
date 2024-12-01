/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package facturacion;

/**
 *
 * @author ar275
 */
public class Emisor {

    private String rfc;
    private String nombres;
    private String apellido_paterno;
    private String apellido_materno;
    private String regimen;
    private String domicilio_fiscal;

    public Emisor(String rfc, String nombres, String apellido_paterno, String apellido_materno, String regimen, String domicilio_fiscal) {
        this.rfc = rfc;
        this.nombres = nombres;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.regimen = regimen;
        this.domicilio_fiscal = domicilio_fiscal;
    }
    
    public void setRfc(String rfc) {
        this.rfc = rfc;
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

    public void setRegimen(String regimen) {
        this.regimen = regimen;
    }

    public void setDomicilio_fiscal(String domicilio_fiscal) {
        this.domicilio_fiscal = domicilio_fiscal;
    }
    
    public String getRfc() {
        return rfc;
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

    public String getRegimen() {
        return regimen;
    }

    public String getDomicilio_fiscal() {
        return domicilio_fiscal;
    }
 
}

//clase para el receptor
class Receptor extends Emisor{
    private String correo_electronico;//maneja su correo electronico

    public Receptor(String correo_electronico, String rfc, String nombres, String apellido_paterno, String apellido_materno, String regimen, String domicilio_fiscal) {
        super(rfc, nombres, apellido_paterno, apellido_materno, regimen, domicilio_fiscal);
        this.correo_electronico = correo_electronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }
    
    public String getCorreo_electronico() {
        return correo_electronico;
    }

}
