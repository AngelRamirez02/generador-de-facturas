/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ar275
 */
public class conexion {
    String bd ="sistema_facturacion";
    String url = "jdbc:mysql://localhost:3306/"; 
    String user = "root";
    String password = "";
    String driver = "com.mysql.cj.jdbc.Driver";
    Connection cx;
    
    public conexion(){    
    }
    public Connection conectar(){
        try {
            Class.forName(driver);
            cx=DriverManager.getConnection(url+bd,user,password);
            System.out.println("CONEXION A BASE DE DATOS "+bd);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("NO SE CONECTO A LA BASE DE DATOS "+bd);
            //Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, ex);
        }    
        return cx;
    }
    public void desconectar(){
        try {
            cx.close();
        } catch (SQLException ex) {
            System.out.println("ERROR AL DESCONECTAR DE LA BASE DE DATOS "+bd);
            //Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) {
        conexion conection=new conexion();
        conection.conectar();
    }
}
