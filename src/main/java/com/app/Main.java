package com.app;

import com.Pantallas.Pantalla_Login;
import com.Pantallas.Pantalla_Menu;
import com.model.*;
import com.controladores.*;

public class Main {
    public static void main(String[] args) {
        // 1. Instancia compartida de ProductoDAO con datos iniciales de la ferretería
        ProductoDAO productoDAO = new ProductoDAO();
        cargarProductosIniciales(productoDAO);

        // 2. Controlador de inventario conectado al ProductoDAO compartido
        controladorInventario ctrlInventario = new controladorInventario(productoDAO);

        // 3. Controlador central de navegación
        ControladorMenu ctrlMenu = new ControladorMenu(ctrlInventario);

        // 4. Inicio del flujo gráfico desde Login
        java.awt.EventQueue.invokeLater(() -> iniciarFlujoLogin(ctrlMenu));
    }
    
    private static void iniciarFlujoLogin(ControladorMenu ctrlMenu) {
        controladorLogin ctrlLogin = new controladorLogin(cedula -> abrirMenuPrincipal(cedula, ctrlMenu));
        new Pantalla_Login(ctrlLogin).setVisible(true);
    }

    private static void abrirMenuPrincipal(String cedulaEncargado, ControladorMenu ctrlMenu) {
        ctrlMenu.setCedulaEncargado(cedulaEncargado);
        ctrlMenu.setOnCerrarSesion(() -> iniciarFlujoLogin(ctrlMenu));
        new Pantalla_Menu(ctrlMenu).setVisible(true);
    }

    private static void cargarProductosIniciales(ProductoDAO dao) {
        dao.agregarProducto(new Producto("F001", "Martillo de uña", "Martillo de acero 16 oz, mango de fibra", 12.50, 25));
        dao.agregarProducto(new Producto("F002", "Clavos 2 pulgadas", "Clavos de acero vendidos por libra", 1.80, 200));
        dao.agregarProducto(new Producto("F003", "Cemento 50 kg", "Saco de cemento Portland", 8.90, 80));
        dao.agregarProducto(new Producto("F004", "Destornillador plano", "Punta plana de 6 mm", 3.25, 40));
        dao.agregarProducto(new Producto("F005", "Tubo PVC 1/2", "Tubo PVC de 1/2 pulgada x 3 m", 4.10, 60));
    }
}
