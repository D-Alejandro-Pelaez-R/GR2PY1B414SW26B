package com.controladores;

import com.model.ProductoDAO;
import com.model.Producto;
import java.util.ArrayList;

public class controladorInventario {
    
    private ProductoDAO productoDao;

    // Constructor vacío
    public controladorInventario() {
        this.productoDao = new ProductoDAO();
    }

    // Constructor que recibe un ProductoDAO compartido
    public controladorInventario(ProductoDAO productoDao) {
        this.productoDao = productoDao;
    }
    
    //METODOS DE GESTIÓN DE INVENTARIO
    
    /**
     * Devuelve el stock de un producto
     * @param idProducto
     * @return 
     */
    public int consutlarStock(String idProducto){
        return productoDao.obtenerProducto(idProducto).getStock();
    }
    
    /**
     * Elimina un producto existente
     * @param idProducto
     * @return 
     */
    public boolean eliminarProduct(String idProducto){
        if (productoDao.obtenerIndiceProducto(idProducto) != -1){
            productoDao.getListadoProductos().remove( productoDao.obtenerIndiceProducto(idProducto) );
            return true;
        }
        return false;
    }
    
    /**
     * Añade un producto no existente
     * @param producto
     * @return 
     */
    public boolean aniadirProducto(Producto producto){
        if (productoDao.obtenerIndiceProducto(producto.getIdProducto()) == -1){
            productoDao.getListadoProductos().add(producto);
            return true;
        }
        return false;
    }
    
    /**
     * Modifica el stock del producto
     * @param idProducto
     * @param stock
     * @return 
     */
    public boolean modificarStockProducto(String idProducto, int stock){
        if(productoDao.obtenerIndiceProducto(idProducto) != -1){
            productoDao.obtenerProducto(idProducto).setStock(stock);
            return true;
        }
        return false;
    }
    
    /**
     * Modifica el precio del producto
     * @param idProducto
     * @param precio
     * @return 
     */
    public boolean modificarPrecioProducto(String idProducto, double precio){
        if(productoDao.obtenerIndiceProducto(idProducto) != -1){
            productoDao.obtenerProducto(idProducto).setPrecio(precio);
            return true;
        }
        return false;
    }

    /**
     * Actualiza los datos completos de un producto existente
     * @param producto
     * @return 
     */
    public boolean actualizarProducto(Producto producto) {
        if (productoDao.obtenerIndiceProducto(producto.getIdProducto()) != -1) {
            productoDao.actualizarProducto(producto);
            return true;
        }
        return false;
    }

    /**
     * Devuelve el listado completo de productos del inventario
     * @return 
     */
    public ArrayList<Producto> obtenerListadoProductos() {
        return productoDao.getListadoProductos();
    }

    //METODOS DE BÚSQUEDA INTEGRADOS

    /**
     * Busca un producto por su ID
     * @param idProducto
     * @return el producto o null si no existe
     */
    public Producto buscarPorId(String idProducto) {
        return productoDao.obtenerProducto(idProducto);
    }

    /**
     * Verifica si existe un producto con ese ID
     * @param idProducto
     * @return true si existe
     */
    public boolean existeProducto(String idProducto) {
        return productoDao.obtenerIndiceProducto(idProducto) != -1;
    }

    /**
     * Busca productos cuyo nombre contenga el texto indicado (sin distinguir mayusculas)
     * @param nombre
     * @return lista de coincidencias (vacia si no hay)
     */
    public ArrayList<Producto> buscarPorNombre(String nombre) {
        ArrayList<Producto> resultado = new ArrayList<>();
        ArrayList<Producto> lista = productoDao.getListadoProductos();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(lista.get(i));
            }
        }
        return resultado;
    }

    /**
     * Busca productos cuya descripcion contenga el texto indicado (sin distinguir mayusculas)
     * @param descripcion
     * @return lista de coincidencias (vacia si no hay)
     */
    public ArrayList<Producto> buscarPorDescripcion(String descripcion) {
        ArrayList<Producto> resultado = new ArrayList<>();
        ArrayList<Producto> lista = productoDao.getListadoProductos();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getDescripcion().toLowerCase().contains(descripcion.toLowerCase())) {
                resultado.add(lista.get(i));
            }
        }
        return resultado;
    }

    /**
     * Busca productos cuyo precio este dentro de un rango (inclusive)
     * @param precioMin
     * @param precioMax
     * @return lista de coincidencias (vacia si no hay)
     */
    public ArrayList<Producto> buscarPorRangoPrecio(double precioMin, double precioMax) {
        ArrayList<Producto> resultado = new ArrayList<>();
        ArrayList<Producto> lista = productoDao.getListadoProductos();
        for (int i = 0; i < lista.size(); i++) {
            double precio = lista.get(i).getPrecio();
            if (precio >= precioMin && precio <= precioMax) {
                resultado.add(lista.get(i));
            }
        }
        return resultado;
    }

    /**
     * Devuelve los productos que tienen stock disponible (mayor a 0)
     * @return lista de productos con stock
     */
    public ArrayList<Producto> buscarConStock() {
        ArrayList<Producto> resultado = new ArrayList<>();
        ArrayList<Producto> lista = productoDao.getListadoProductos();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getStock() > 0) {
                resultado.add(lista.get(i));
            }
        }
        return resultado;
    }
}
