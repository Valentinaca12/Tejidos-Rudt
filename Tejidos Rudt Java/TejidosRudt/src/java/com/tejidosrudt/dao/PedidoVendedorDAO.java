/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.dao;
import java.sql.*;
import com.tejidosrudt.modelo.PedidoVendedor;
import com.tejidosrudt.config.ConexionBD;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author sarac
 */
public class PedidoVendedorDAO {
     Connection con;
    
    public PedidoVendedorDAO(){
        con = ConexionBD.getConnection();
}

     //Asigna un vendedor a un pedido
    public void asignarVendedor(int idPedido, int idVendedor)throws SQLException {
        String SQL = "INSERT INTO pedidoVendedor (idPedido, idVendedor) VALUES (?, ?)";
        
           try(PreparedStatement ps = con.prepareStatement(SQL)){
            ps.setInt(1, idPedido);
            ps.setInt(2, idVendedor);
            ps.executeUpdate();
        } 
    }
   
    
      //Lista los pedidos asociados a un vendedor
     public List<Integer> listarPedVende(int idVendedor) throws SQLException{
         List<Integer> lista = new ArrayList<>();
         String SQL = "SELECT * FROM pedidoVendedor WHERE idVendedor =?;";
          
            try(PreparedStatement ps = con.prepareStatement(SQL)){
            ps.setInt(1, idVendedor);
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    lista.add(rs.getInt("idPedido"));
             }
           }
         }
         return lista;
     }
     
     // Obtiene el vendedor asignado a un pedido
    public int obtenerVendedorPorPedido(int idPedido)throws SQLException{
        String SQL = "SELECT idVendedor FROM pedidoVendedor WHERE idPedido = ?";
        
           try(PreparedStatement ps = con.prepareStatement(SQL)){
                ps.setInt(1, idPedido);
           
            try(ResultSet rs = ps.executeQuery()){        
               if (rs.next()) {
                  return rs.getInt("idVendedor");
            }
           }
         }
        return -1; // si no se encuentra
    }
    
     //Busca la asignación de vendedor a un pedido (por su ID)
     
      public PedidoVendedor buscarXIdPedVen(PedidoVendedor pedidoVendedor)throws SQLException{
          String SQL = "SELECT * FROM pedidoVendedor WHERE idPedido=?;";
          
             try(PreparedStatement ps = con.prepareStatement(SQL)){
              ps.setInt(1, pedidoVendedor.getIdPedido());
              ResultSet rs = ps.executeQuery();
              while (rs.next()) {
                  return new PedidoVendedor(
                          rs.getInt("idPedido"),
                          rs.getInt("idVendedor"));
              }
          } 
          return null;
    }
}
