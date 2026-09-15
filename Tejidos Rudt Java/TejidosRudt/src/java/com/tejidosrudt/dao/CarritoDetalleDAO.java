/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.dao;
import com.tejidosrudt.config.ConexionBD;
import com.tejidosrudt.modelo.CarritoDetalle;
import com.tejidosrudt.modelo.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sarac
 */
public class CarritoDetalleDAO {
    Connection con;
    
    public CarritoDetalleDAO(){
        con = ConexionBD.getConnection();
}
     
    //Registra un producto dentro del detalle de un carrito
    public void crearCarritoDetalle(CarritoDetalle carritoDetalle)throws SQLException{
        String SQL = "INSERT INTO carritoDetalle (idCarrito, idProducto, cantidad) VALUES (?,?,?);";
        
           try (PreparedStatement ps = con.prepareStatement(SQL)){
            ps.setInt(1, carritoDetalle.getIdCarrito());
            ps.setInt(2, carritoDetalle.getIdProducto());
            ps.setInt(3, carritoDetalle.getCantidad());
            ps.executeUpdate();
        } 
    }
    
    //Actualiza la información de un producto 
     public void actualizarCarritoDetalle(CarritoDetalle carritoDetalle)throws SQLException{
         String SQL = "UPDATE carritoDetalle SET idProducto=?, cantidad=? WHERE idCarrito=?;";
         
           try(PreparedStatement ps = con.prepareStatement(SQL)){
            ps.setInt(1, carritoDetalle.getIdProducto());
            ps.setInt(2, carritoDetalle.getIdCarrito());
            ps.setInt(3, carritoDetalle.getCantidad());
            ps.executeUpdate();
        } 
    }
     
     //Elimina el carrito detalle
      public void eliminarCarritoDetalle(CarritoDetalle carritoDetalle)throws SQLException{
          String SQL = "DELETE FROM carritoDetalle WHERE idCarrito = ?;";
          
            try( PreparedStatement ps = con.prepareStatement(SQL)){
              ps.setInt(1, carritoDetalle.getIdCarrito());
              ps.executeUpdate();
          } 
    }
    
      //Lista todos los registros del detalle de los carritos
     public List<CarritoDetalle> listarCarritoDetalle()throws SQLException{
         List<CarritoDetalle> lista = new ArrayList<>();
         String SQL = "SELECT * FROM carritoDetalle;";
         
              try(PreparedStatement ps = con.prepareStatement(SQL);
              ResultSet rs = ps.executeQuery()){
                  
                  ProductoDAO productoDAO = new ProductoDAO();
              
              while (rs.next()){
                  int idCarrito = rs.getInt("idCarrito");
                  int idProducto = rs.getInt("idProducto");
                  int cantidad = rs.getInt("cantidad");
                  
                  Producto producto = productoDAO.buscarPorId(idProducto);
                  
                  lista.add(new CarritoDetalle(idCarrito, idProducto, cantidad, producto));
              }
         } 
         return lista;
     }
     
     //Busca el detalle de un carrito por su ID
     
      public CarritoDetalle buscarXIdCarritoDetalle(CarritoDetalle carritoDetalle)throws SQLException{
          String SQL = "SELECT * FROM carritoDetalle WHERE idCarrito=?;";
          
          ProductoDAO productoDAO = new ProductoDAO();
          
            try( PreparedStatement ps = con.prepareStatement(SQL)){
              ps.setInt(1, carritoDetalle.getIdCarrito());
              ResultSet rs = ps.executeQuery();
              
              while (rs.next()) {
                  int idCarrito = rs.getInt("idCarrito");
                  int idProducto = rs.getInt("idProducto");
                  int cantidad = rs.getInt("cantidad");
                  
                  Producto producto = productoDAO.buscarPorId(idProducto);
                  return new CarritoDetalle(idCarrito, idProducto, cantidad, producto);
                  
              }             
          } 
          return null;
    }
}
