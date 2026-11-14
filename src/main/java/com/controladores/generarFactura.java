package com.controladores;

import java.util.ArrayList;

import com.model.FacturaDAO;
import com.model.ClienteDAO;
import com.model.ProductoDAO;
import com.model.Producto;
import com.model.Cliente;
import com.model.Factura;

public class generarFactura {
    
    private FacturaDAO facturaDao = new FacturaDAO();
    private ClienteDAO clienteDao = new ClienteDAO();
    private ProductoDAO productoDao = new ProductoDAO();
    
    /**
     * Obtiene un listado de productos pasados por un String
     * @param productos
     * @return 
     */
    public ArrayList<Producto> obtenerListadoProductos(String productos){
        return null;
    }
    
    /**
     * Obtiene un Cliente a partir de su cedula
     * @param cedula
     * @return 
     */
    public Cliente obtenerCliente(String cedula){
        return clienteDao.obtenerCliente(cedula);
    }
    
    /**
     * Imprime todo el detalle de la factura
     * @param factura
     * @return 
     */
    public void imprimirFactura(String factura){
        //Algo para imrpimir
    }
    
    /**
     * Devuelve una Factura y la añade al listado de facturas
     * @param cedula
     * @param productos
     * @return 
     */
    public Factura generarFactura(String cedula, String productos){
        
        Cliente clienteTemporal = obtenerCliente(cedula);
        ArrayList<Producto> listadoProductos = obtenerListadoProductos(productos);
        
        Factura facturaTemporal = new Factura(clienteTemporal,listadoProductos);
        facturaDao.agregarFactura(facturaTemporal);
        
        return facturaTemporal;
    }
    
    
    
    
    
}
