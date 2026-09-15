/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.servicios;

import com.tejidosrudt.dao.CatalogoDAO;
import com.tejidosrudt.modelo.Catalogo;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sarac
 */
public class CatalogoServicio {
    private CatalogoDAO catalogoDAO;

    public CatalogoServicio() {
        catalogoDAO = new CatalogoDAO();
    }

    // Registrar catálogo (solo si se requiere asociar producto con descripción)
    public boolean registrarCatalogo(Catalogo catalogo) {
        try {
            if (catalogo.getIdProducto() <= 0) {
                throw new IllegalArgumentException("El ID del producto no es válido");
            }
            if (catalogo.getDescripcion() == null || catalogo.getDescripcion().isEmpty()) {
                throw new IllegalArgumentException("La descripción es obligatoria");
            }

            catalogoDAO.crearCatalogo(catalogo);
            return true;
        } catch (SQLException e) {
            System.err.println("Error SQL al registrar catálogo: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("Validación fallida: " + e.getMessage());
            return false;
        }
    }

    // Listar catálogo
    public List<Catalogo> listarCatalogo() {
        try {
            return catalogoDAO.listarCatalogo();
        } catch (SQLException e) {
            System.err.println("Error SQL al listar catálogo: " + e.getMessage());
            return null;
        }
    }

    // Buscar catálogo por ID
    public Catalogo buscarCatalogoPorId(int idCatalogo) {
        try {
            Catalogo catalogo = new Catalogo();
            catalogo.setIdCatalogo(idCatalogo);
            return catalogoDAO.buscarXIdCatalogo(catalogo);
        } catch (SQLException e) {
            System.err.println("Error SQL al buscar catálogo por ID: " + e.getMessage());
            return null;
        }
    }
}
