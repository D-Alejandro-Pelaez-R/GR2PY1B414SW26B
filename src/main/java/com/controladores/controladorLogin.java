/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controladores;

import com.model.UsuarioEncargado;
import com.model.UsuarioEncargadoDAO;

/**
 * Controlador para autenticacion del encargado
 * @author DELL
 */

public class controladorLogin {
    private UsuarioEncargadoDAO usuarioEncargadoDAO;
    
    public controladorLogin(){
        this.usuarioEncargadoDAO = new UsuarioEncargadoDAO();
    }
    
    /**
     * Valida credenciales del encargado buscando en DAO
     * Diagrama de secuencia LOGIN 2msg
     * @param cedula del encargado
     * @param clave Contraseña del encargado
     * @return true si el acceso es concedido.
     */
    
    public boolean validarEncargado(String cedula, String clave){
        // leer cedula
        UsuarioEncargado encargado = usuarioEncargadoDAO.read(cedula);
        
        if(encargado != null && encargado.getContrasena().equals(clave)){
            return true;
        }
        return false;
    }
}
