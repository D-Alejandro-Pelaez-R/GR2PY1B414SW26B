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

// Clase DAO para gestionar las operaciones CRUD de Producto
public class ProductoDAO {
    
    // Lista para almacenar los productos
    private ArrayList<Producto> listadoProductos;
    
    // Constructor
    public ProductoDAO() {
        this.listadoProductos = new ArrayList<>();
    }
    
    /**
     * Agrega un producto a la lista si su ID no se encuentra registrado
     */
    public void agregarProducto(Producto producto){
        for (int i = 0; i < listadoProductos.size(); i++) {
            // Se usa .equals() porque idProducto es de tipo String
            if (listadoProductos.get(i).getIdProducto().equals(producto.getIdProducto())) {
                return; // Si ya existe, sale sin agregarlo
            }
        }
        this.listadoProductos.add(producto);
    }
    
    /**
     * Busca y retorna un producto según su ID; si no existe, retorna null 
     */
    public Producto obtenerProducto(String idProducto){
        for (int i = 0; i < listadoProductos.size(); i++) {
            if (listadoProductos.get(i).getIdProducto().equals(idProducto)) {
                return listadoProductos.get(i);
            }
        }
        return null;
    }
    
    /**
     * Retorna la posición de un producto en la lista según su ID, o -1 si no se encuentra.
     */
    public int obtenerIndiceProducto(String idProducto){
        for (int i = 0; i < listadoProductos.size(); i++) {
            if (listadoProductos.get(i).getIdProducto().equals(idProducto)) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Retorna la lista completa de productos disponibles.
     */
    public ArrayList<Producto> getListadoProductos(){
        return this.listadoProductos;
    }
    
    /**
     * Modifica los datos de un producto existente utilizando su ID 
     */
    public void actualizarProducto(Producto producto){
        int index = obtenerIndiceProducto(producto.getIdProducto());
        if (index != -1) {
            listadoProductos.set(index, producto);
        }
    }
    
    /**
     * Elimina un producto de la lista en base a su ID 
     */
    public void eliminarProducto(String idProducto){
        for (int i = 0; i < listadoProductos.size(); i++) {
            if (listadoProductos.get(i).getIdProducto().equals(idProducto)) {
                listadoProductos.remove(i);
                return;
            }
        }
    }
}