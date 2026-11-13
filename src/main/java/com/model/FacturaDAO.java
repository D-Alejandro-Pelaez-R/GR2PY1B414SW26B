package com.model;

import java.util.ArrayList;

public class FacturaDAO {
    
    //ATRIBUROS ASOCIADOS
    private ArrayList<Factura> listadoFacturas;
    
    //CONSTRUCTOR
    public FacturaDAO() {
        this.listadoFacturas = new ArrayList<>(); //Composición de factura
    }
    
    //METODOS CRUD y otros METODOS de UTILIDAD
    
    /**
     * Agrega una nueva factura a la lista de facturas
     * @param factura
     */
    public void agregarFactura(Factura factura){ //CREATE
        for (int i = 0; i < listadoFacturas.size(); i++) {
            if (listadoFacturas.get(i).getIdFactura() == factura.getIdFactura()) {
                return; //Si ya existe una factura con esa id.
            }
        }
        this.listadoFacturas.add(factura);
    }
    
    /**
     * Busca la factura asociada con un id y la devuelve, sino encuaentra devuelve NULL.
     * @param idFactura
     * @return factyra
     */
    public Factura obtenerFactura(int idFactura){
        for (int i = 0; i < listadoFacturas.size(); i++) {
            if (listadoFacturas.get(i).getIdFactura() == idFactura) {
                return listadoFacturas.get(i);
            }
        }
        return null;
    }
    
    public int obtenerIndiceFactura(int idFactura){
        for (int i = 0; i < listadoFacturas.size(); i++) {
            if (listadoFacturas.get(i).getIdFactura() == idFactura) {
                return i;
            }
        }
        return -1;
    }
    
    public ArrayList<Factura> getListadoFacturas(){ //READ
        return this.listadoFacturas;
    }
    
    public void actualizarFactura(int idFactura){
        
    }
    
    public void eliminarFactura(int idFactura){
        for (int i = 0; i < listadoFacturas.size(); i++) {
            if (listadoFacturas.get(i).getIdFactura() == idFactura) {
                listadoFacturas.remove(i);
                return;
            }
        }
    }
    
}
