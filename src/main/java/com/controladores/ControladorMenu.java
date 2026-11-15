package com.controladores;

import com.Pantallas.Pantalla_Factura;
import com.Pantallas.Pantalla_Inventario;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author DELL
 */

public class ControladorMenu {
    
    private String cedulaEncargado;
    private final controladorInventario ctrlInventario;
    private Runnable onCerrarSesion;
    
    public ControladorMenu(controladorInventario ctrlInventario) {
        this.ctrlInventario = ctrlInventario;
    }
    
    public void setCedulaEncargado(String cedulaEncargado) {
        this.cedulaEncargado = cedulaEncargado;
    }

    public String getCedulaEncargado() {
        return cedulaEncargado;
    }

    public void setOnCerrarSesion(Runnable onCerrarSesion) {
        this.onCerrarSesion = onCerrarSesion;
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
        
        Pantalla_Inventario ventanaInventario = new Pantalla_Inventario(this.ctrlInventario);
        
        //Cerrar Inventario, vuelve a mostrar el menú sin perder datos
        ventanaInventario.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (ventanaMenu != null) {
                    ventanaMenu.setVisible(true);
                }
            }
        });
        
        ventanaInventario.setVisible(true);
    }

    public void cerrarSesion(JFrame ventanaMenu) {
        int confirmacion = JOptionPane.showConfirmDialog(
            ventanaMenu, 
            "¿Está seguro que desea cerrar sesión?", 
            "Confirmar Salida", 
            JOptionPane.YES_NO_OPTION
        );

       if (confirmacion == JOptionPane.YES_OPTION) {
            ventanaMenu.dispose(); //Cierra el menú actual
            if (onCerrarSesion != null) {
                onCerrarSesion.run(); //Vuelve a abrir Pantalla_Login desde Main
            }
        }
    }
}
