/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.modelo;


import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author sarac
 */
public class PedidoEncargo {
    private int idPedido;
    private Date fechaPedido;
    private Date fechaEnvio;
    private int idCliente;
    private String estado;
    private BigDecimal valorTotal;
    private int idVendedor;

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    public int getIdVendedor(){
    return idVendedor;
    }
    
    public void setIdVendedor(int idVendedor){
    this.idVendedor = idVendedor;}

    public PedidoEncargo(int idPedido, Date fechaPedido, Date fechaEnvio, int idCliente, String estado, BigDecimal valorTotal, int idVendedor) {
        this.idPedido = idPedido;
        this.fechaPedido = fechaPedido;
        this.fechaEnvio = fechaEnvio;
        this.idCliente = idCliente;
        this.estado = estado;
        this.valorTotal = valorTotal;
        this.idVendedor = idVendedor;
    }

    public PedidoEncargo(Date fechaPedido, Date fechaEnvio, int idCliente, String estado, BigDecimal valorTotal, int idVendedor) {
        this.fechaPedido = fechaPedido;
        this.fechaEnvio = fechaEnvio;
        this.idCliente = idCliente;
        this.estado = estado;
        this.valorTotal = valorTotal;
        this.idVendedor = idVendedor;
    }

    public PedidoEncargo() {
    }
}
