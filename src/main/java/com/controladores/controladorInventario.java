package com.controladores;

import com.model.ProductoDAO;
import com.model.Producto;

public class controladorInventario {
    
    private ProductoDAO productoDao = new ProductoDAO();
    
    //METODOS
    
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
    
    
}
