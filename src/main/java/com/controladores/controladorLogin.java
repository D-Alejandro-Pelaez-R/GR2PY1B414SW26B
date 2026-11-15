package com.controladores;

import com.model.UsuarioEncargado;
import com.model.UsuarioEncargadoDAO;
import java.util.function.Consumer;

/**
 * Controlador para autenticacion del encargado
 * @author DELL
 */
public class controladorLogin {
    private UsuarioEncargadoDAO usuarioEncargadoDAO;
    private final Consumer<String> onLoginExitoso;
    
    public controladorLogin(Consumer<String> onLoginExitoso) {
        this.usuarioEncargadoDAO = new UsuarioEncargadoDAO();
        this.onLoginExitoso = onLoginExitoso;
    }
    
    /**
     * Valida credenciales del encargado buscando en DAO
     * Diagrama de secuencia LOGIN 2msg
     * @param cedula del encargado
     * @param clave Contraseña del encargado
     * @return true si el acceso es concedido.
     */
    public boolean validarEncargado(String cedula, String clave){
        //leer cedula
        UsuarioEncargado encargado = usuarioEncargadoDAO.obtenerEncargado(cedula);
        
        if(encargado != null && encargado.getContrasena().equals(clave)){
            return true;
        }
        return false;
    }
    
    /**
     * Notifica al ensamblador (Main) para abrir el menú con la cédula autenticada
     */
    public void continuarAlMenu(String cedulaEncargado) {
        if (onLoginExitoso != null) {
            onLoginExitoso.accept(cedulaEncargado);
        }
    }
}
