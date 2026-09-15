/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.dao;
import com.tejidosrudt.config.ConexionBD;
import com.tejidosrudt.modelo.Carrito;
import com.tejidosrudt.modelo.CarritoDetalle;
import com.tejidosrudt.modelo.DetallePedido;
import com.tejidosrudt.modelo.Producto;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author sarac
 */
public class CarritoDAO {
    Connection con;

    public CarritoDAO() {
        con = ConexionBD.getConnection();
    }

    /* Obtine el carrito del Cliente */
    public int obtenerCarritoPorCliente(int idCliente) throws SQLException {
        String SQL = "SELECT idCarrito FROM carrito WHERE idCliente=?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("idCarrito");
            }
        }
        // Si no existe el carrito, lo crea
        String crearSQL = "INSERT INTO carrito (idCliente, valorTotal) VALUES (?, 0)";
        try (PreparedStatement ps = con.prepareStatement(crearSQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idCliente);
            ps.executeUpdate();
            ResultSet rsKeys = ps.getGeneratedKeys();
            if (rsKeys.next()) {
                return rsKeys.getInt(1);
            }
        }
        return 0;
    }

    
    //Actualiza el valor total de un carrito
    public void actualizarCarrito(Carrito carrito) throws SQLException {
        String SQL = "UPDATE carrito SET valorTotal=? WHERE idCarrito=?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, carrito.getValorTotal());
            ps.setInt(2, carrito.getIdCarrito());
            ps.executeUpdate();
        }
    }

    //Elimina el carrito asociado a un Cliente
    public void eliminarCarrito(int idCliente) throws SQLException {
        int idCarrito = obtenerCarritoPorCliente(idCliente);
        String SQL = "DELETE FROM carrito WHERE idCarrito=?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, idCarrito);
            ps.executeUpdate();
        }
    }

    //Lista los productos que un cliente tiene agregados en su carrito
    public List<CarritoDetalle> listarProductosCarrito(int idCliente) throws SQLException {
        List<CarritoDetalle> detalles = new ArrayList<>();
        //Consulta para unir los detalles del carrito con la información de cada producto
        String SQL = "SELECT dc.idCarrito, dc.idProducto, dc.cantidad, " +
                     "p.nombreProducto, p.descripcion, p.precio " +
                     "FROM carritodetalle dc " +
                     "INNER JOIN producto p ON dc.idProducto = p.idProducto " +
                     "INNER JOIN carrito c ON dc.idCarrito = c.idCarrito " +
                     "WHERE c.idCliente = ?";

        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {  
                    CarritoDetalle detalle = new CarritoDetalle();
                    detalle.setIdCarrito(rs.getInt("idCarrito"));
                    detalle.setIdProducto(rs.getInt("idProducto"));
                    detalle.setCantidad(rs.getInt("cantidad"));

                    Producto p = new Producto();
                    p.setIdProducto(rs.getInt("idProducto"));
                    p.setNombreProducto(rs.getString("nombreProducto"));
                    p.setDescripcion(rs.getString("descripcion"));
                    p.setPrecio(rs.getBigDecimal("precio"));

                    detalle.setProducto(p);
                    detalles.add(detalle);
                }
            }
        }
        return detalles;
    }
    
    //Obtiene los productos del carrito para generar el detalle de un pedido
    public List<DetallePedido> listarProductosParaPedido(int idCliente) throws SQLException {
    List<DetallePedido> lista = new ArrayList<>();
    String sql = "SELECT cd.idProducto, cd.cantidad, (cd.cantidad * p.precio) AS subtotal " +
                 "FROM carritodetalle cd " +
                 "INNER JOIN carrito c ON cd.idCarrito = c.idCarrito " +
                 "INNER JOIN producto p ON cd.idProducto = p.idProducto " +
                 "WHERE c.idCliente = ?";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, idCliente);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DetallePedido d = new DetallePedido();
                d.setIdProducto(rs.getInt("idProducto"));
                d.setCantidad(rs.getInt("cantidad"));
                d.setSubtotal((int) rs.getDouble("subtotal"));
                lista.add(d);
            }
        }
    }
    System.out.println("[DAO] Productos listos para pedido del cliente " + idCliente + ": " + lista.size());
    return lista;
}


    /* --- Operaciones sobre productos --- */
    
    //Agrega un producto al carrito de un cliente
    public void agregarProducto(int idCliente, int idProducto, int cantidad) throws SQLException {
        int idCarrito = obtenerCarritoPorCliente(idCliente);
        String SQL = "INSERT INTO carritodetalle (idCarrito, idProducto, cantidad) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, idCarrito);
            ps.setInt(2, idProducto);
            ps.setInt(3, cantidad);
            ps.executeUpdate();
        }
    }

    //Actualiza la cantidad de un producto dentro del carrito
    public void actualizarCantidad(int idCliente, int idProducto, int cantidad) throws SQLException {
        int idCarrito = obtenerCarritoPorCliente(idCliente);
        String SQL = "UPDATE carritodetalle SET cantidad=? WHERE idCarrito=? AND idProducto=?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, cantidad);
            ps.setInt(2, idCarrito);
            ps.setInt(3, idProducto);
            ps.executeUpdate();
        }
    }

    //Elimina un producto del carrito de un cliente
    public void eliminarProducto(int idCliente, int idProducto) throws SQLException {
        int idCarrito = obtenerCarritoPorCliente(idCliente);
        String SQL = "DELETE FROM carritodetalle WHERE idCarrito=? AND idProducto=?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, idCarrito);
            ps.setInt(2, idProducto);
            ps.executeUpdate();
        }
    }

    //Genera un pedido con los productos del carrito y posteriormente lo vacía
    public void confirmarPedido(int idCliente) throws SQLException {
      int idCarrito = obtenerCarritoPorCliente(idCliente);
      System.out.println("[DAO] confirmando pedido: idCliente=" + idCliente + ", idCarrito=" + idCarrito);

      //Insertar el pedido
      String SQL = "INSERT INTO pedidoencargo (idCliente, idVendedor, fechaPedido, estado, valorTotal) " +
                   "SELECT c.idCliente, 1, NOW(), 'Pendiente', c.valorTotal FROM carrito c WHERE c.idCarrito=?";
      try (PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
          ps.setInt(1, idCarrito);
          ps.executeUpdate();
          
          ResultSet rs = ps.getGeneratedKeys();
          int idPedidoGenerado = 0;
          if (rs.next()) {
              idPedidoGenerado = rs.getInt(1);
          }
          System.out.println("[DAO] Pedido insertado con idPedido=" + idPedidoGenerado);

          //Insertar los detalles del pedido
          String sqlDetalle = "INSERT INTO detallepedido (idPedido, idProducto, cantidad, subtotal) VALUES (?, ?, ?, ?)";
          try (PreparedStatement psDetalle = con.prepareStatement(sqlDetalle)) {
             List<DetallePedido> listaCarrito = listarProductosParaPedido(idCliente);
             for (DetallePedido d : listaCarrito) {
                 psDetalle.setInt(1, idPedidoGenerado);
                 psDetalle.setInt(2, d.getIdProducto());
                 psDetalle.setInt(3, d.getCantidad());
                 psDetalle.setDouble(4, d.getSubtotal());
                 psDetalle.executeUpdate();
             }
          }
          System.out.println("[DAO] Detalles insertados para pedido=" + idPedidoGenerado);
      }

      //Vaciar el carrito
      String limpiarSQL = "DELETE FROM carritodetalle WHERE idCarrito=?";
      try (PreparedStatement ps = con.prepareStatement(limpiarSQL)) {
          ps.setInt(1, idCarrito);
          ps.executeUpdate();
          System.out.println("[DAO] Carrito limpiado con idCarrito=" + idCarrito);
      }
  }
}

