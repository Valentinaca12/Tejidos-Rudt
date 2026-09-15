/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.dao;

import com.tejidosrudt.config.ConexionBD;
import com.tejidosrudt.modelo.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sarac
 */
public class ClienteDAO {
    Connection con;

    public ClienteDAO() {
        con = ConexionBD.getConnection();
    }


    // Registra un nuevo cliente en la BD
    public void crearCliente(Cliente cliente) throws SQLException {
        String SQL = "INSERT INTO cliente (nombre, correo, telefono, contraseña, edad, sexo) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getCorreo());
            ps.setString(3, cliente.getTelefono());
            ps.setString(4, cliente.getContraseña());
            ps.setInt(5, cliente.getEdad());
            ps.setString(6, cliente.getSexo().name());
            ps.executeUpdate();
        }
    }

    //Actualiza la información de un cliente
    public void actualizarCliente(Cliente cliente) throws SQLException {
        String SQL = "UPDATE cliente SET nombre=?, correo=?, telefono=?, contraseña=?, edad=?, sexo=? WHERE idCliente=?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getCorreo());
            ps.setString(3, cliente.getTelefono());
            ps.setInt(4, cliente.getEdad());
            ps.setString(5, cliente.getContraseña());
            ps.setString(6, cliente.getSexo().name());
            ps.setInt(7, cliente.getIdCliente());
            ps.executeUpdate();
        }
    }
    
    //Elimina un cliente de la BD
    public void eliminarCliente(Cliente cliente) throws SQLException {
        String SQL = "DELETE FROM cliente WHERE idCliente=?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, cliente.getIdCliente());
            ps.executeUpdate();
        }
    }

    //Lista todos los clienets registrados
    public List<Cliente> listarCliente() throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        String SQL = "SELECT * FROM cliente";
        try (PreparedStatement ps = con.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearCliente(rs));
            }
        }
        return lista;
    }


    // Busca un cliente por su ID
    public Cliente buscarXIdCliente(int idCliente) throws SQLException {
        String SQL = "SELECT * FROM cliente WHERE idCliente=?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapearCliente(rs);
            }
        }
        return null;
    }

    // Busca cliente por su correo
    public Cliente buscarXCorreoCliente(String correo) throws SQLException {
        String SQL = "SELECT * FROM cliente WHERE correo=?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapearCliente(rs);
            }
        }
        return null;
    }

    // Busca cliente por su nombre aunque coincida parcial o totalmente
    public List<Cliente> buscarPorNombre(String nombre) throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        String SQL = "SELECT * FROM cliente WHERE nombre LIKE ?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearCliente(rs));
            }
        }
        return lista;
    }

    // Busca cliente por su nombre o correo
    public List<Cliente> buscarPorNombreOCorreo(String nombre, String correo) throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        String SQL = "SELECT * FROM cliente WHERE (? IS NULL OR nombre LIKE ?) AND (? IS NULL OR correo LIKE ?)";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {

            if (nombre == null || nombre.isEmpty()) {
                ps.setNull(1, Types.VARCHAR);
                ps.setNull(2, Types.VARCHAR);
            } else {
                ps.setString(1, nombre);
                ps.setString(2, "%" + nombre + "%");
            }

            if (correo == null || correo.isEmpty()) {
                ps.setNull(3, Types.VARCHAR);
                ps.setNull(4, Types.VARCHAR);
            } else {
                ps.setString(3, correo);
                ps.setString(4, "%" + correo + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearCliente(rs));
            }
        }
        return lista;
    }

    //JOIN: lista clientes con información de sus pedidos
    public List<Cliente> listarClientesConPedidos() throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        String SQL = "SELECT c.idCliente, c.nombre, c.correo, c.telefono, c.edad, c.sexo, p.idPedido " +
                     "FROM cliente c " +
                     "LEFT JOIN pedidoencargo p ON c.idCliente = p.idCliente";
        try (PreparedStatement ps = con.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cliente c = mapearCliente(rs);
          
                lista.add(c);
            }
        }
        return lista;
    }

    /* --- Método auxiliar para mapear ResultSet a Cliente --- */
    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getInt("idCliente"),
                rs.getString("nombre"),
                rs.getString("correo"),
                rs.getInt("edad"),
                Cliente.Sexo.valueOf(rs.getString("sexo").toUpperCase()),
                rs.getString("telefono"),
                rs.getString("contraseña")
        );
    }
}

