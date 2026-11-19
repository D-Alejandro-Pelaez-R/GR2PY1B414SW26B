/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;
import java.util.ArrayList;
/**
 *
 * @author Rosalia Piedra
 */

 // Clase DAO para gestionar el almacenamiento y validación de los usuarios encargados del sistema.
 
public class UsuarioEncargadoDAO {
    
    // Lista para almacenar los usuarios encargados
    private ArrayList<UsuarioEncargado> listadoEncargados;
    
    // Constructor
    public UsuarioEncargadoDAO() {
        this.listadoEncargados = new ArrayList<>(); 
                // por defecto
        this.listadoEncargados.add(new UsuarioEncargado("1004339824", "2634"));
    }
    
    /**
     * Busca y retorna un usuario encargado mediante su cédula 
     */
    public UsuarioEncargado obtenerEncargado(String cedula){
        for (int i = 0; i < listadoEncargados.size(); i++) {
            if (listadoEncargados.get(i).getCedula().equals(cedula)) {
                return listadoEncargados.get(i);
            }
        }
        return null;
    }
}