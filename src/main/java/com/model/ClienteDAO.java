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

// Clase DAO para gestionar las operaciones CRUD 

public class ClienteDAO {
    
    // Lista para almacenar los clientes 
    private ArrayList<Cliente> listadoClientes;
    
    // Constructor
    public ClienteDAO() {
        this.listadoClientes = new ArrayList<>();
    }
    
    /**
     * Agrega un nuevo cliente a la lista si la cédula no está registrada
     */
    public void agregarCliente(Cliente cliente){
        for (int i = 0; i < listadoClientes.size(); i++) {
            if (listadoClientes.get(i).getCedula().equals(cliente.getCedula())) {
                return; // Si ya existe, no hace nada y sale
            }
        }
        this.listadoClientes.add(cliente);
    }
    
    /**
     * Busca y retorna un cliente según su número de cédula
     * si no lo encuentra, retorna null 
     */
    public Cliente obtenerCliente(String cedula){
        for (int i = 0; i < listadoClientes.size(); i++) {
            if (listadoClientes.get(i).getCedula().equals(cedula)) {
                return listadoClientes.get(i);
            }
        }
        return null;
    }
    
    /**
     * Retorna la posición de un cliente en la lista usando su cédula, o -1 si no existe.
     */
    public int obtenerIndiceCliente(String cedula){
        for (int i = 0; i < listadoClientes.size(); i++) {
            if (listadoClientes.get(i).getCedula().equals(cedula)) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Retorna la lista completa de clientes registrados.
     */
    public ArrayList<Cliente> getListadoClientes(){
        return this.listadoClientes;
    }
    
    /**
     * Actualiza la información de un cliente existente buscando por su cédula
     */
    public void actualizarCliente(Cliente cliente){
        int index = obtenerIndiceCliente(cliente.getCedula());
        if (index != -1) {
            listadoClientes.set(index, cliente);
        }
    }
    
    /**
     * Elimina un cliente de la lista utilizando su número de cédula.
     */
    public void eliminarCliente(String cedula){
        for (int i = 0; i < listadoClientes.size(); i++) {
            if (listadoClientes.get(i).getCedula().equals(cedula)) {
                listadoClientes.remove(i);
                return;
            }
        }
    }
}
