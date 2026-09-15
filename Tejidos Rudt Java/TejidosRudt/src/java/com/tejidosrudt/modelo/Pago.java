/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.modelo;
import java.sql.*;
import java.math.BigDecimal;

/**
 *
 * @author sarac
 */

public class Pago {
    
    public enum MetodoPago{
    Efectivo,
    Tarjeta,
    Transferencia
    } 
    
    private int idPago;
    private BigDecimal monto;
    private MetodoPago metodo;
    private int idPedido;

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public MetodoPago getMetodo() {
        return metodo;
    }

    public void setMetodo(MetodoPago metodo) {
        this.metodo = metodo;
    }
    
    public int getIdPedido() { 
        return idPedido; }
    
    public void setIdPedido(int idPedido) { 
        this.idPedido = idPedido; } 

    public Pago(int idPago, BigDecimal monto, MetodoPago metodo, int idPedido) {
        this.idPago = idPago;
        this.monto = monto;
        this.metodo = metodo;
        this.idPedido = idPedido;
    }
    
     // Constructor sin idPedido 
    public Pago(int idPago, BigDecimal monto, MetodoPago metodo) {
        this.idPago = idPago;
        this.monto = monto;
        this.metodo = metodo;
    }

    public Pago(BigDecimal monto, MetodoPago metodo) {
        this.monto = monto;
        this.metodo = metodo;       
    }

    public Pago() {
    }
            
           
}
