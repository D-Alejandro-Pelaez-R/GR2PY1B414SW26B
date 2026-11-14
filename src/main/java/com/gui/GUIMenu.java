/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gui;

import javax.swing.*;

/**
 *
 * @author DELL
 */
public class GUIMenu extends JFrame {
    
    //componentes de interfaz
    private final ControlMenu controlador;
    private JButton btnFacturar;
    private JButton btnInventario;
    private JButton btnSalir;
    
    public GUIMenu(String cedulaEncargado){
        this.controlador = new ControlMenu(cedulaEncargado);
        initComponents(cedulaEncargado);
    }
    
    private void initComponents(String cedulaEncargado){
        setTitle("Menu Principal - Encargado: " + cedulaEncargado);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        
        JLabel lblTitulo = new JLabel("Seleccione una opcion:");
        lblTitulo.setBounds(120,30,200,30);
        add(lblTitulo);
        
        // Boton Facturar
        btnFacturar = new JButton("Realizar Venta / Factura");
        btnFacturar.setBounds(100, 80, 200, 40);
        btnFacturar.addActionListener(e -> controlador.abrirGUIFactura(GUIMenu.this));
        add(btnFacturar);
        
        // Boton Inventario
        btnInventario = new JButton("Gestionar Inventario");
        btnInventario.setBounds(100, 140, 200, 40);
        btnInventario.addActionListener(e -> controlador.abrirGUIInventario(GUIMenu.this));
        add(btnInventario);
        
        // Boton Salir
        btnSalir = new JButton("Salir");
        btnSalir.setBounds(100, 200, 200, 30);
        btnSalir.addActionListener(e -> controlador.cerrarSesion(GUIMenu.this));
        add(btnSalir);
    }    
}
