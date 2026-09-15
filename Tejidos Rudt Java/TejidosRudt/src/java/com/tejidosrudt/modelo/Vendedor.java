/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.modelo;

/**
 *
 * @author sarac
 */
public class Vendedor {
    private int idVendedor;
    private String nombre;
    private String contraseña;

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Vendedor(int idVendedor, String nombre, String contraseña) {
        this.idVendedor = idVendedor;
        this.nombre = nombre;
        this.contraseña = contraseña;
    }

    public Vendedor(String nombre, String contraseña) {
        this.nombre = nombre;
        this.contraseña = contraseña;
    }

    public Vendedor() {
    }
}
