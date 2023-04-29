/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabajofinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author stere
 */
public class Conexion {
    
    Connection conectar = null;
    
    String usuario = "root";
    String contraseña = "pablo";
    String bd = "trabajo_final";
    String ip = "localhost";
    String puerto = "3306";
    
    String ruta = "jdbc:mysql://" + ip + ":" + puerto + "/" + bd;
    
    public Connection estableceConexion(){
        
        try{
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            conectar = DriverManager.getConnection(ruta, usuario, contraseña);
            JOptionPane.showMessageDialog(null, "Se conecto correctamente.");
            
        }catch(Exception e){
            
            JOptionPane.showMessageDialog(null, "No se conecto correctamente." + e);
            
        }
        return conectar;
    }
    
    public Connection cerrarConexion(){
        try{
            conectar.close();
            JOptionPane.showMessageDialog(null, "Se desconecto correctamente.");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "No se desconecto correctamente." + e);
        }
        return conectar;
    }

}

  
