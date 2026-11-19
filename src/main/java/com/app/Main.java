package com.app;

import com.Pantallas.Pantalla_Login;
import com.Pantallas.Pantalla_Menu;
import com.model.*;
import com.controladores.*;

public class Main {
    public static void main(String[] args) {
        // 1. Instancias compartidas de los DAOS de la ferreteria
        ProductoDAO productoDAO = new ProductoDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        FacturaDAO facturaDAO = new FacturaDAO();
        
        cargarProductosIniciales(productoDAO);
        cargarClientesIniciales(clienteDAO);
        
        // 2. Controladores conectados a los DAOS compartidos
        controladorInventario ctrlInventario = new controladorInventario(productoDAO);
        generarFactura ctrlFactura = new generarFactura(facturaDAO, clienteDAO, productoDAO);
        
        // 3. Controlador central de navegación
        ControladorMenu ctrlMenu = new ControladorMenu(ctrlInventario, ctrlFactura);

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
    
    private static void cargarClientesIniciales(ClienteDAO dao) {
        dao.agregarCliente(new Cliente("0401112313", "Carlos Pérez", "0991234567", "Av. Colón y 10 de Agosto", "carlos.perez@email.com"));
        dao.agregarCliente(new Cliente("1101815510", "María Gómez", "0987654321", "Av. Amazonas N34-12", "maria.gomez@email.com"));
        dao.agregarCliente(new Cliente("0605625599", "Misael Cuenca", "0992557104", "Av. Colón y 10 de Agosto", "carlos.perez@email.com"));

    }
    
}
