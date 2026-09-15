/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.modelo;

/**
 *
 * @author sarac
 */
public class Carrito {
    private int idCarrito;
    private int idCliente;
    private int valorTotal;
    
    public int getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(int idCarrito) {
        this.idCarrito = idCarrito;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(int valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    public Carrito(int idCarrito, int idCliente, int valorTotal) {
        this.idCarrito = idCarrito;
        this.idCliente = idCliente;
        this.valorTotal = valorTotal;
    }
  
    public Carrito(int idCliente, int valorTotal) {
        this.idCliente = idCliente;
        this.valorTotal = valorTotal;
    }
  
    public Carrito() {
    }
}
