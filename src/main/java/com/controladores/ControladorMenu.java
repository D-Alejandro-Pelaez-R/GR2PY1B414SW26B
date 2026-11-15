/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controladores;
import com.Pantallas.Pantalla_Login;
import com.Pantallas.Pantalla_Factura;
import javax.swing.JOptionPane;
import javax.swing.JFrame;


/**
 *
 * @author DELL
 */

public class ControladorMenu {
    
    private final String cedulaEncargado;
    
    public ControladorMenu(String cedulaEncargado) {
        this.cedulaEncargado = cedulaEncargado;
        
    }

    public void abrirGUIFactura(JFrame ventanaMenu) {
        // Ocultamos el menú principal
        ventanaMenu.setVisible(false);
        
        // Abrimos la Factura pasándole la cédula y la referencia del menú para poder volver
        Pantalla_Factura ventanaFactura = new Pantalla_Factura(cedulaEncargado, ventanaMenu);
 
        //conexion del boton Regresar
        ventanaFactura.agregarListenerRegresar(e -> {
            if (ventanaMenu != null) {
                ventanaMenu.setVisible(true); // Vuelve a mostrar el menú principal
            }
            ventanaFactura.dispose(); // Cierra y destruye la ventana de factura actual
        });
  
        ventanaFactura.setVisible(true);
    }

    public void abrirGUIInventario(JFrame ventanaMenu) {
        ventanaMenu.setVisible(false);
        
        // Simulación o apertura de la GUI de Inventario
        JOptionPane.showMessageDialog(ventanaMenu, "Abriendo GUI de Inventario...");
        // GUIInventario ventanaInventario = new GUIInventario(ventanaMenu);
        // ventanaInventario.setVisible(true);
        
        ventanaMenu.setVisible(true); // Se muestra de nuevo para el flujo
    }

    public void cerrarSesion(JFrame ventanaMenu) {
        int confirmacion = JOptionPane.showConfirmDialog(
            ventanaMenu, 
            "¿Está seguro que desea cerrar sesión?", 
            "Confirmar Salida", 
            JOptionPane.YES_NO_OPTION
        );

       if (confirmacion == JOptionPane.YES_OPTION) {
            Pantalla_Login login = new Pantalla_Login();
            login.setVisible(true);
            ventanaMenu.dispose(); // regresa al login
        }
    }
}
