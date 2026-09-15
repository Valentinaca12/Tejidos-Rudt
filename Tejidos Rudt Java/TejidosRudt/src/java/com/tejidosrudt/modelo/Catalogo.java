/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.modelo;

/**
 *
 * @author sarac
 */
public class Catalogo {
    private int idCatalogo;
    private int idProducto;
    private String descripcion;

    public int getIdCatalogo() {
        return idCatalogo;
    }

    public void setIdCatalogo(int idCatalogo) {
        this.idCatalogo = idCatalogo;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Catalogo(int idCatalogo, int idProducto, String descripcion) {
        this.idCatalogo = idCatalogo;
        this.idProducto = idProducto;
        this.descripcion = descripcion;
    }

    public Catalogo(int idProducto, String descripcion) {
        this.idProducto = idProducto;
        this.descripcion = descripcion;
    }

    public Catalogo() {
    }
}
