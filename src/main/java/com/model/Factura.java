package com.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Factura {
    
    //ATRIBUTOS PROPIOS
    private int idFactura;
    private LocalDateTime fechaHoraEmisionFactura;
    private double totalFactura;
    
    //ATRIBUTOS ASOCIADOS
    private Cliente clienteFactura;
    private ArrayList<Producto> productosFactura;
    
    //CONSTRUCTOR
    public Factura(Cliente cliente, ArrayList<Producto> prodcutos) {//Agregación de cliente y productos
        this.fechaHoraEmisionFactura = LocalDateTime.now();
        this.clienteFactura = cliente;
        this.productosFactura = prodcutos;
    }
    
    //GETTERS Y SETTERS
    
    /**
     * Obtiene el id de la factura
     * @return id -> int
     */
    public int getIdFactura() {
        return idFactura;
    }
    
    /**
     * Establece/modifica el id de la factura
     * @param idFactura: valor int para el id de la factura
     */
    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }
    
    /**
     * Obtiene la fecha y hora de emisión de la factura
     * @return fechaHoraEmision -> LocalDateTime
     */
    public LocalDateTime getFechaHoraEmisionFactura() {
        return fechaHoraEmisionFactura;
    }
    
    /**
     * Obtiene el costo total calculado de los productos de la factura
     * @return total -> double
     */
    public double getTotalFactura() {
        return totalFactura;
    }
    
    /**
     * Establece/Mofica el costo total calculado de los productos de la factura
     * @param totalFactura: valor double del costo calculado
     */
    public void setTotalFactura(double totalFactura) {
        this.totalFactura = totalFactura;
    }

    /**
     * Obtiene el cliente asociado a la factura
     * @return cliente -> Cliente
     */
    public Cliente getClienteFactura(){
        return this.clienteFactura;
    }
     
    /**
    * Obtiene el listado de productos asociados a la factura
    * @return productos -> ArrayList(Producto)
    */
    public ArrayList<Producto> getProductosFactura(){
        return this.productosFactura;
    }
}
