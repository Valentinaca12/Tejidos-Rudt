/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tejidosrudt.modelo;

/**
 *
 * @author sarac
 */
public class Cliente {
    private int idCliente;
    private String nombre;
    private String correo;
    private int edad;
    private Sexo sexo;
    private String telefono;
    private String contraseña;

    // Enum para sexo
    public enum Sexo {
        MASCULINO,
        FEMENINO
    }

    public Cliente(String nombre, String correo, int edad, Sexo sexo, String telefono, String contraseña) {
        this.nombre = nombre;
        this.correo = correo;
        this.edad = edad;
        this.sexo = sexo;
        this.telefono = telefono;
        this.contraseña = contraseña;
    }

    public Cliente(int idCliente, String nombre, String correo, int edad, Sexo sexo, String telefono, String contraseña) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.correo = correo;
        this.edad = edad;
        this.sexo = sexo;
        this.telefono = telefono;
        this.contraseña = contraseña;
    }

    public Cliente() {
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getContraseña() {
        return contraseña; 
    }
    
    public void setContraseña(String contraseña) { 
        this.contraseña = contraseña; }
}

