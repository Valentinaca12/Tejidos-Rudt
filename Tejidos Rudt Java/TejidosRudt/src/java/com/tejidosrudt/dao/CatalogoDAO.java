/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.dao;
import com.tejidosrudt.config.ConexionBD;
import com.tejidosrudt.modelo.Catalogo;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author sarac
 */
public class CatalogoDAO {
     Connection con;
    
    public CatalogoDAO(){
        con = ConexionBD.getConnection();
} 
    
    
    
    //Registra un producto en el catalogo
    public void crearCatalogo(Catalogo catalogo) throws SQLException{
        String SQL = "INSERT INTO catalogo (idProducto, descripcion) VALUES (?,?);";
        
          try( PreparedStatement ps = con.prepareStatement(SQL)){
            ps.setInt(1, catalogo.getIdProducto());
            ps.setString(2, catalogo.getDescripcion());
            ps.executeUpdate();
        } 
    }
    
    // Las operaciones de crear, actualizar, eliminar o buscar productos se gestionan desde ProductoDAO
    
      //Lista todos los productos registrados en el catálogo
     public List<Catalogo> listarCatalogo()throws SQLException{
         List<Catalogo> lista = new ArrayList<>();
         String SQL = "SELECT * FROM catalogo;";
        
             try( PreparedStatement ps = con.prepareStatement(SQL);
              ResultSet rs = ps.executeQuery()){
              
              while (rs.next()){
              lista.add (new Catalogo(
              rs.getInt("idCatalogo"),
              rs.getInt("idProducto"),
              rs.getString("descripcion")));
              }
         } 
         return lista;
     }
     
     //Busca por ID
     
      public Catalogo buscarXIdCatalogo(Catalogo catalogo)throws SQLException{
          String SQL = "SELECT * FROM catalogo WHERE idCatalogo=?;";
         
             try(PreparedStatement ps = con.prepareStatement(SQL)){
              ps.setInt(1, catalogo.getIdProducto());
              ResultSet rs = ps.executeQuery();
              while (rs.next()) {
                  return new Catalogo(
                          rs.getInt("idCatalogo"),
                          rs.getInt("idProducto"),
                          rs.getString("descripcion"));
              }
          } 
          return null;
    }
}
