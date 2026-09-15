
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.dao;

import com.tejidosrudt.config.ConexionBD;
import com.tejidosrudt.modelo.Vendedor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sarac
 */
public class VendedorDAO {
    Connection con;

    public VendedorDAO() {
        con = ConexionBD.getConnection();
    }


    // Registra un nuevo vendedor en la BD
    public void crearVendedor(Vendedor vendedor) throws SQLException {
        String SQL = "INSERT INTO vendedor (nombre, contraseña) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setString(1, vendedor.getNombre());
            ps.setString(2, vendedor.getContraseña());
            ps.executeUpdate();
        }
    }

    //Actualiza la información de un vendedor
    public void actualizarVendedor(Vendedor vendedor) throws SQLException {
        String SQL = "UPDATE vendedor SET nombre=?, contraseña=? WHERE idVendedor=?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setString(1, vendedor.getNombre());
            ps.setString(2, vendedor.getContraseña());
            ps.setInt(3, vendedor.getIdVendedor());
            ps.executeUpdate();
        }
    }

    //Elimina un vendedor de la BD
    public void eliminarVendedor(Vendedor vendedor) throws SQLException {
        String SQL = "DELETE FROM vendedor WHERE idVendedor=?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, vendedor.getIdVendedor());
            ps.executeUpdate();
        }
    }

    // Lista todos los vendedores registrados
    public List<Vendedor> listarVendedores() throws SQLException {
        List<Vendedor> lista = new ArrayList<>();
        String SQL = "SELECT * FROM vendedor";
        try (PreparedStatement ps = con.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearVendedor(rs));
            }
        }
        return lista;
    }

    // Busca un vendedor por su ID
    public Vendedor buscarPorId(int idVendedor) throws SQLException {
        String SQL = "SELECT * FROM vendedor WHERE idVendedor=?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, idVendedor);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapearVendedor(rs);
            }
        }
        return null;
    }

    // Busca vendedores por nombre o nombre parcial
    public List<Vendedor> buscarPorNombre(String nombre) throws SQLException {
        List<Vendedor> lista = new ArrayList<>();
        String SQL = "SELECT * FROM vendedor WHERE nombre LIKE ?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearVendedor(rs));
            }
        }
        return lista;
    }

    // Busca vendedores por su nombre o ID 
    public List<Vendedor> buscarPorNombreOId(String nombre, Integer idVendedor) throws SQLException {
        List<Vendedor> lista = new ArrayList<>();
        String SQL = "SELECT * FROM vendedor WHERE (? IS NULL OR nombre LIKE ?) AND (? IS NULL OR idVendedor = ?)";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {

            if (nombre == null || nombre.isEmpty()) {
                ps.setNull(1, Types.VARCHAR);
                ps.setNull(2, Types.VARCHAR);
            } else {
                ps.setString(1, nombre);
                ps.setString(2, "%" + nombre + "%");
            }

            if (idVendedor == null) {
                ps.setNull(3, Types.INTEGER);
                ps.setNull(4, Types.INTEGER);
            } else {
                ps.setInt(3, idVendedor);
                ps.setInt(4, idVendedor);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearVendedor(rs));
            }
        }
        return lista;
    }
    // Busca un vendedor por su nombre
    public Vendedor buscarXNombreVendedor(String nombre) throws SQLException {
        Vendedor vendedor = null;
        String SQL = "SELECT * FROM vendedor WHERE nombre = ?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                vendedor = mapearVendedor(rs);
            }
    }
    return vendedor;
}


    //JOIN: lista vendedores con información de los pagos asociados
    public List<Vendedor> listarVendedoresConPagos() throws SQLException {
        List<Vendedor> lista = new ArrayList<>();
        String SQL = "SELECT v.idVendedor, v.nombre, v.contraseña, p.idPago " +
                     "FROM vendedor v " +
                     "LEFT JOIN pago p ON v.idVendedor = p.idVendedor";
        try (PreparedStatement ps = con.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Vendedor v = mapearVendedor(rs);
               
                lista.add(v);
            }
        }
        return lista;
    }

    /* --- Método auxiliar para mapear ResultSet a Vendedor --- */
    private Vendedor mapearVendedor(ResultSet rs) throws SQLException {
        Vendedor v = new Vendedor();
        v.setIdVendedor(rs.getInt("idVendedor"));
        v.setNombre(rs.getString("nombre"));
        v.setContraseña(rs.getString("contraseña"));
        return v;
    }
}
