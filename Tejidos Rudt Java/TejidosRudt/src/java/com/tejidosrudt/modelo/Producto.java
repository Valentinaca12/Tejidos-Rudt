/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.modelo;

import java.math.BigDecimal;

/**
 *
 * @author sarac
 */
public class Producto {
    private int idProducto;
    private String nombreProducto;
    private String estilo;
    private BigDecimal precio;
    private String talla;
    private String color;
    private String capellada;
    private String descripcion;

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCapellada() {
        return capellada;
    }

    public void setCapellada(String capellada) {
        this.capellada = capellada;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Producto(int idProducto, String nombreProducto, String estilo, BigDecimal precio, String talla, String color, String capellada, String descripcion) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.estilo = estilo;
        this.precio = precio;
        this.talla = talla;
        this.color = color;
        this.capellada = capellada;
        this.descripcion = descripcion;
    }

    public Producto(String nombreProducto, String estilo, BigDecimal precio, String talla, String color, String capellada, String descripcion) {
        this.nombreProducto = nombreProducto;
        this.estilo = estilo;
        this.precio = precio;
        this.talla = talla;
        this.color = color;
        this.capellada = capellada;
        this.descripcion = descripcion;
    }

    public Producto() {
    }
}
