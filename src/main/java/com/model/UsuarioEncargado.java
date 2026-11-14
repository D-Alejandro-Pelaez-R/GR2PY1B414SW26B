/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;

public class UsuarioEncargado {
    //Atributos 
    private String cedula;
    private String contrasena;
    
 // Constructor Vacio
    public UsuarioEncargado() {
    }
    
    // Costructor con parametros 
    public UsuarioEncargado(String cedula, String contrasena) {
        this.cedula = cedula;
        this.contrasena = contrasena;
    }
    
     //Getters y Setters
       /**
     * Obtiene la cedula del encargado.
     *
     * @return cedula del encargado
     */
    public String getCedula() {
        return cedula;
    }
     /**
     * Obtiene la contraseña del encargado.
     *
     * @return contraseña del encargado
     */
    public String getContrasena() {
        return contrasena;
    }
     /**
     * Modifica la cedula del encargado.
     *
     * @param cedula nueva cedula del encargado
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
       /**
     * Modifica la contraseña del encargado.
     *
     * @param contrasena nueva contraseña del encargado
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    
}
