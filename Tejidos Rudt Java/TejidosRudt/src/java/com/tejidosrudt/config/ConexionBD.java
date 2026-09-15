/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.config;
import java.sql.*;

/**
 *
 * @author sarac
 */
public class ConexionBD {
    private static final String URL="jdbc:mysql://localhost:3306/tejidosrudt1";
    private static final String USER="root";
    private static final String PASS="MySQLsara23!";
    
    private ConexionBD(){}
    
    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e){
            System.err.println("No se encontró el Conector de MySQL");
        } catch(SQLException e) {
            System.err.print("Error en las credenciales de acceso al SGBD");
        }
        return null;
    }
}

