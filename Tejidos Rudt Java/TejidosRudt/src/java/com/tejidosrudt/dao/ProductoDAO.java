/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.dao;
import com.tejidosrudt.config.ConexionBD;
import com.tejidosrudt.modelo.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author sarac
 */
public class ProductoDAO {
    Connection con;

    public ProductoDAO() {
        con = ConexionBD.getConnection();
    }

  
    // Registra un nuevo producto en la BD
    public void crearProducto(Producto producto) throws SQLException {
        String SQL = "INSERT INTO producto (nombreProducto, estilo, precio, talla, color, capellada, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setString(1, producto.getNombreProducto());
            ps.setString(2, producto.getEstilo());
            ps.setBigDecimal(3, producto.getPrecio());
            ps.setString(4, producto.getTalla());
            ps.setString(5, producto.getColor());
            ps.setString(6, producto.getCapellada());
            ps.setString(7, producto.getDescripcion());
            ps.executeUpdate();
            
        }
    }

    // Actualiza la información de un producto
    public void actualizarProducto(Producto producto) throws SQLException {
        String SQL = "UPDATE producto SET nombreProducto=?, estilo=?, precio=?, talla=?, color=?, capellada=?, descripcion=? WHERE idProducto=?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setString(1, producto.getNombreProducto());
            ps.setString(2, producto.getEstilo());
            ps.setBigDecimal(3, producto.getPrecio());
            ps.setString(4, producto.getTalla());
            ps.setString(5, producto.getColor());
            ps.setString(6, producto.getCapellada());
            ps.setString(7, producto.getDescripcion());
            ps.setInt(8, producto.getIdProducto());
            ps.executeUpdate();
        }
    }

    //Elimina un producto de la BD
    public void eliminarProducto(Producto producto) throws SQLException {
        String SQL = "DELETE FROM producto WHERE idProducto=?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, producto.getIdProducto());
            ps.executeUpdate();
        }
    }

    //Lista todos los productos registrados
    public List<Producto> listarProductos() throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String SQL = "SELECT * FROM producto";
        try (PreparedStatement ps = con.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearProducto(rs));
            }
        }
        return lista;
    }

    // Busca un producto por su ID
    public Producto buscarPorId(int idProducto) throws SQLException {
        String SQL = "SELECT * FROM producto WHERE idProducto=?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapearProducto(rs);
            }
        }
        return null;
    }

    // Busca productos por nombre o nombre parcial
    public List<Producto> buscarPorNombre(String nombreProducto) throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String SQL = "SELECT * FROM producto WHERE nombreProducto LIKE ?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setString(1, "%" + nombreProducto + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearProducto(rs));
            }
        }
        return lista;
    }

    // Busca productos por estilo o color
    public List<Producto> buscarPorEstiloOColor(String estilo, String color) throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String SQL = "SELECT * FROM producto WHERE (? IS NULL OR estilo LIKE ?) AND (? IS NULL OR color LIKE ?)";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {

            if (estilo == null || estilo.isEmpty()) {
                ps.setNull(1, Types.VARCHAR);
                ps.setNull(2, Types.VARCHAR);
            } else {
                ps.setString(1, estilo);
                ps.setString(2, "%" + estilo + "%");
            }

            if (color == null || color.isEmpty()) {
                ps.setNull(3, Types.VARCHAR);
                ps.setNull(4, Types.VARCHAR);
            } else {
                ps.setString(3, color);
                ps.setString(4, "%" + color + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearProducto(rs));
            }
        }
        return lista;
    }

    //JOIN: lista los productos con la información del catálogo
    public List<Producto> listarProductosConCatalogo() throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String SQL = "SELECT p.idProducto, p.nombreProducto, p.estilo, p.precio, p.talla, p.color, p.capellada, p.descripcion, c.idCatalogo " +
                     "FROM producto p " +
                     "LEFT JOIN catalogo c ON p.idProducto = c.idProducto";
        try (PreparedStatement ps = con.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Producto p = mapearProducto(rs);
                lista.add(p);
            }
        }
        return lista;
    }

    /* --- Método auxiliar para mapear ResultSet a Producto --- */
    private Producto mapearProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setIdProducto(rs.getInt("idProducto"));
        p.setNombreProducto(rs.getString("nombreProducto"));
        p.setEstilo(rs.getString("estilo"));
        p.setPrecio(rs.getBigDecimal("precio"));
        p.setTalla(rs.getString("talla"));
        p.setColor(rs.getString("color"));
        p.setCapellada(rs.getString("capellada"));
        p.setDescripcion(rs.getString("descripcion"));
        return p;
    }
}