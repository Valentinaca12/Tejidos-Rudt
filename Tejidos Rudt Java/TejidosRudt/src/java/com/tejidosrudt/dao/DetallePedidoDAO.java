/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.dao;
import com.tejidosrudt.modelo.DetallePedido;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import com.tejidosrudt.config.ConexionBD;

/**
 *
 * @author sarac
 */
public class DetallePedidoDAO {
     Connection con;
    
    public DetallePedidoDAO(){
        con = ConexionBD.getConnection();
}

    // Registar un nuevo detalle en la BD
    public void crearDetallePedido(DetallePedido detallePedido)throws SQLException{
        String SQL = "INSERT INTO detallePedido (idPedido, idProducto, cantidad, subtotal) VALUES (?,?,?,?);";
       
           try(PreparedStatement ps = con.prepareStatement(SQL)){
            ps.setInt(1, detallePedido.getIdPedido());
            ps.setInt(2, detallePedido.getIdProducto());
            ps.setInt(3, detallePedido.getCantidad());
            ps.setInt(4, detallePedido.getSubtotal());
            ps.executeUpdate();
        } 
    }
    
    //Las operaciones de crear, actualizar o eliminar pertenecen al PedidoEncargoDAO.
    
      //Lista los detalles de los pedidos registrados
     public List<DetallePedido> listarDetallePedido()throws SQLException{
         List<DetallePedido> lista = new ArrayList<>();
         String SQL = "SELECT * FROM detallePedido;";
         
             try(PreparedStatement ps = con.prepareStatement(SQL);
              ResultSet rs = ps.executeQuery()){
              
              while (rs.next()){
              lista.add (new DetallePedido(
              rs.getInt("idDetalle"),
              rs.getInt("idPedido"),
              rs.getInt("idProducto"),
              rs.getInt("cantidad"),
              rs.getInt("subtotal")));
              }
         } 
         return lista;
     }
     
     //Lista los detalles un pedido por el IdPedido
     public List<DetallePedido> listarPorPedido(int idPedido) throws SQLException {
        List<DetallePedido> lista = new ArrayList<>();
        String SQL =
            "SELECT dp.*, p.nombreProducto " +
            "FROM detallepedido dp " +
            "INNER JOIN producto p " +
            "ON dp.idProducto = p.idProducto " +
            "WHERE dp.idPedido = ?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, idPedido);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DetallePedido detalle = new DetallePedido();
                detalle.setIdDetalle(rs.getInt("idDetalle"));
                detalle.setIdPedido(rs.getInt("idPedido"));
                detalle.setIdProducto(rs.getInt("idProducto"));
                detalle.setCantidad(rs.getInt("cantidad"));
                detalle.setSubtotal(rs.getInt("subtotal"));
                detalle.setNombreProducto(rs.getString("nombreProducto"));
                lista.add(detalle);
            }
        }
        return lista;
}

     
     //Busca un detalle de pedido por su ID
     
      public DetallePedido buscarXIdDetallePedido(DetallePedido detallePedido)throws SQLException{
          String SQL = "SELECT * FROM detallePedido WHERE idDetalle=?;";
         
             try(PreparedStatement ps = con.prepareStatement(SQL)){
              ps.setInt(1, detallePedido.getIdDetalle());
              ResultSet rs = ps.executeQuery();
              while (rs.next()) {
                  return new DetallePedido(
                          rs.getInt("idDetalle"),
                          rs.getInt("idPedido"),
                          rs.getInt("idProducto"),
                          rs.getInt("cantidad"),
                          rs.getInt("subtotal"));
              }
          } 
          return null;
    }
}
