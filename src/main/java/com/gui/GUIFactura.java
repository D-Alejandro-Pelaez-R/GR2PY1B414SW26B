/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gui;

import javax.swing.*;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author DELL
 */

public class GUIFactura extends JFrame{
    
    private String cedulaEncargado;
    private JFrame ventanaMenu;

    // Componentes visuales
    private JTextField txtCedulaCliente;
    private JTextField txtIdProducto;
    private JTextField txtCantidad;
    
    private JButton btnAniadirProducto;
    private JButton btnCrearFactura;
    private JButton btnImprimirFactura;
    private JButton btnRegresar;
    
    private JTextArea txtAreaDetalle;
    
    public GUIFactura(String cedulaEncargado, JFrame ventanaMenu){
        this.cedulaEncargado = cedulaEncargado;
        this.ventanaMenu = ventanaMenu;
        configurarVentana();
        inicializarComponentes();
    }
    
    private void configurarVentana(){
        setTitle("Sistema de Facturacion");
        setSize(450, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
    }
    
    private void inicializarComponentes(){
        //Cedula
        JLabel lblCedula = new JLabel("Cedula Cliente:");
        lblCedula.setBounds(30, 25, 120, 25);
        add(lblCedula);
        
        txtCedulaCliente =  new JTextField();
        txtCedulaCliente.setBounds(150,25,230,25);
        add(txtCedulaCliente);
        
        //Producto
        JLabel lblProducto = new JLabel("ID Producto:");
        lblProducto.setBounds(30,65,120,25);
        add(lblProducto);
        
        txtIdProducto =  new JTextField();
        txtIdProducto.setBounds(150,65,230,25);
        add(txtIdProducto);
        
        //Cantidad
        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setBounds(30,105,120,25);
        add(lblCantidad);
        
        txtCantidad =  new JTextField();
        txtCantidad.setBounds(150,105,230,25);
        add(txtCantidad);
        
        // Boton AñadirProducto
        btnAniadirProducto = new JButton("Añadir Producto:");
        btnAniadirProducto.setBounds(30,150,170,35);
        add(btnAniadirProducto);
        
        // Boton Regresar Menu
        btnRegresar = new JButton("Regresar al Menu");
        btnRegresar.setBounds(210,150,170,35);
        add(btnRegresar);
        
        txtAreaDetalle = new JTextArea();
        txtAreaDetalle.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaDetalle);
        scrollPane.setBounds(30, 205, 350, 150);
        add(scrollPane);
                
        // Boton Crear Factura
        btnCrearFactura = new JButton("Crear Factura");
        btnCrearFactura.setBounds(120,375,200,35);
        add(btnCrearFactura);
        
        
        // Boton Imprimir Factura
        btnImprimirFactura = new JButton("Imprimir Factura");
        btnImprimirFactura.setBounds(120,375,200,35);
        add(btnImprimirFactura);
    }
    
    // Metodos getters para el controlador
    public String getCedulaCliene(){
        return txtCedulaCliente.getText().trim();
    }
    
    public String getIdProducto() {
        return txtIdProducto.getText().trim();
    }

    public String getCantidad() {
        return txtCantidad.getText().trim();
    }

    public JTextArea getTextAreaDetalle() {
        return txtAreaDetalle;
    }

    public String getCedulaEncargado() {
        return cedulaEncargado;
    }

    // MÉTODOS PARA ASOCIAR LOS EVENTOS DESDE EL CONTROLADOR

    public void agregarListenerAniadir(java.awt.event.ActionListener listener) {
        btnAniadirProducto.addActionListener(listener);
    }

    public void agregarListenerCrear(java.awt.event.ActionListener listener) {
        btnCrearFactura.addActionListener(listener);
    }

    public void agregarListenerImprimir(java.awt.event.ActionListener listener) {
        btnImprimirFactura.addActionListener(listener);
    }
    
    public void agregarListenerRegresar(java.awt.event.ActionListener listener) {
        btnRegresar.addActionListener(listener);
    }
}
