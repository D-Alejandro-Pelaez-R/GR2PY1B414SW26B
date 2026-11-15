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
    private final generarFactura crtlFactura;
    private Runnable onCerrarSesion;
    
    public ControladorMenu(controladorInventario ctrlInventario, generarFactura ctrlFactura) {
        this.ctrlInventario = ctrlInventario;
        this.crtlFactura = ctrlFactura;
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
        // Se oculta el menú principal
        ventanaMenu.setVisible(false);
        
        // Abrimos la Factura pasándole la cédula y la referencia del menú para poder volver
        Pantalla_Factura ventanaFactura = new Pantalla_Factura(this.crtlFactura, cedulaEncargado, ventanaMenu);
 
        ventanaFactura.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (ventanaMenu != null && !ventanaMenu.isVisible()) {
                    ventanaMenu.setVisible(true);
                }
            }
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
