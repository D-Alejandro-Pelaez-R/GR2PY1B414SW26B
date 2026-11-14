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
     * Registra un nuevo usuario encargado evitando duplicados por cédula 
     */
    public void agregarEncargado(UsuarioEncargado encargado){
        for (int i = 0; i < listadoEncargados.size(); i++) {
            if (listadoEncargados.get(i).getCedula().equals(encargado.getCedula())) {
                return;
            }
        }
        this.listadoEncargados.add(encargado);
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
    
    /**
     * Elimina un usuario encargado de la lista utilizando su cédula 
     */
    public void eliminarEncargado(String cedula){
        for (int i = 0; i < listadoEncargados.size(); i++) {
            if (listadoEncargados.get(i).getCedula().equals(cedula)) {
                listadoEncargados.remove(i);
                return;
            }
        }
    }

    public UsuarioEncargado read(String cedula) {
        return obtenerEncargado(cedula);
    }
}
