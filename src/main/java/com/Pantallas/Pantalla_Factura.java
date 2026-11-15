/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Pantallas;

import com.controladores.generarFactura;
import com.model.Cliente;
import com.model.Factura;
import com.model.Producto;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.*;


/**
 *
 * @author DELL
 */

public class Pantalla_Factura extends JFrame{
    
    private final generarFactura controlador;
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
    
    /**
     * Inicia la ventana de facturacion, conecta con el controlador y nueva
     * sesion de factura.
     * @param controlador instancia del controlador de facturacion 
     * @param cedulaEncargado
     * @param ventanaMenu 
     */
    public Pantalla_Factura(generarFactura controlador, String cedulaEncargado, JFrame ventanaMenu){
        this.controlador = controlador;
        this.cedulaEncargado = cedulaEncargado;
        this.ventanaMenu = ventanaMenu;
        configurarVentana();
        inicializarComponentes();
        configurarEventos();
    }
    
    // Titulo
    private void configurarVentana(){
        setTitle("Sistema de Facturacion");
        setSize(450, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
    }
    
    //Crea, posiciona y agrega todos los componentes graficos
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
        btnCrearFactura.setBounds(30,375,170,35);
        add(btnCrearFactura);
        
        
        // Boton Imprimir Factura
        btnImprimirFactura = new JButton("Imprimir Factura");
        btnImprimirFactura.setBounds(210,375,170,35);
        add(btnImprimirFactura);
    }
    
    /**
     * ActionListener internos de los botones
     * @return 
     */
    private void configurarEventos(){
        btnAniadirProducto.addActionListener(e -> accionAniadirProducto());
        btnCrearFactura.addActionListener(e -> accionCrearFactura());
        btnImprimirFactura.addActionListener(e -> mostrarVentanaImpresionFactura());
        btnRegresar.addActionListener(e -> {
            if (ventanaMenu != null) {
                ventanaMenu.setVisible(true);
            }
            dispose();
        });
    }
    /**
     * Captura el ID del producto y la cantidad ingresada, valida los datos 
     * mediante el controlador y actualiza la lista visual de productos añadidos.
     */
    private void accionAniadirProducto() {
        String idProducto = getIdProducto();
        String cantidadStr = getCantidad();

        if (idProducto.isEmpty() || cantidadStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el ID del producto y la cantidad.",
                    "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Producto p = controlador.buscarProductoPorId(idProducto);
        if (p == null) {
            JOptionPane.showMessageDialog(this, "No existe un producto con ese ID.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!controlador.aniadirProducto(idProducto, cantidad)) {
            JOptionPane.showMessageDialog(this,
                    "Stock insuficiente. Disponible: " + p.getStock(),
                    "Stock insuficiente", JOptionPane.WARNING_MESSAGE);
            return;
        }

        actualizarDetalleProductos();
        txtIdProducto.setText("");
        txtCantidad.setText("");
        txtIdProducto.requestFocus();
    }

    /**
     * Refresca el contenido del JTextArea mostrando los productos acumulados 
     * hasta el momento y el total parcial calculado.
     */
    private void actualizarDetalleProductos() {
        ArrayList<Producto> lista = controlador.obtenerListadoProductos();
        StringBuilder sb = new StringBuilder();
        sb.append("PRODUCTOS AÑADIDOS:\n");
        sb.append("------------------------------------------\n");
        for (int i = 0; i < lista.size(); i++) {
            Producto item = lista.get(i);
            double subtotal = item.getPrecio() * item.getStock();
            sb.append(String.format("%s - %s x%d ($%.2f)\n",
                    item.getIdProducto(), item.getNombre(), item.getStock(), subtotal));
        }
        sb.append("------------------------------------------\n");
        sb.append(String.format("TOTAL: $%.2f", controlador.calcularTotal(lista)));
        txtAreaDetalle.setText(sb.toString());
    }

    /**
     * Valida la cédula del cliente, gestiona su registro si no existe en 
     * el sistema y solicita al controlador la generación y almacenamiento.
     */
    private void accionCrearFactura() {
        String cedula = getCedulaCliene();
        if (cedula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese la cédula del cliente.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (controlador.obtenerListadoProductos().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe añadir al menos un producto.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente cliente = controlador.obtenerCliente(cedula);
        if (cliente == null) {
            int opcion = JOptionPane.showConfirmDialog(this,
                    "El cliente no está registrado. ¿Desea registrarlo ahora?",
                    "Cliente no encontrado", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                Cliente nuevo = mostrarDialogoNuevoCliente(cedula);
                if (nuevo == null) {
                    return;
                }
                controlador.registrarCliente(nuevo);
            } else {
                return;
            }
        }

        Factura factura = controlador.generarFactura(cedula);
        if (factura != null) {
            String detalle = controlador.generarTextoFactura(factura, cedulaEncargado);
            txtAreaDetalle.setText(detalle);
            JOptionPane.showMessageDialog(this,
                    "Factura #" + factura.getIdFactura() + " creada correctamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Abre un cuadro de diálogo modal para ingresar los datos personales de 
     * un nuevo cliente cuando su cédula no esta en el DAO.
     *
     * @param cedula número de cédula del cliente que se va a registrar
     * @return objeto Cliente creado con los datos ingresados, o null si el usuario cancela el registro
     */
    private Cliente mostrarDialogoNuevoCliente(String cedula) {
        final Cliente[] resultado = { null };

        JDialog dialogo = new JDialog(this, "Registrar Cliente", true);
        dialogo.setLayout(new BorderLayout());

        JPanel panelCampos = new JPanel(new GridLayout(5, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        JTextField txtCed = new JTextField(cedula);
        txtCed.setEditable(false);
        JTextField txtNombre = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtDireccion = new JTextField();
        JTextField txtCorreo = new JTextField();

        panelCampos.add(new JLabel("Cédula:"));
        panelCampos.add(txtCed);
        panelCampos.add(new JLabel("Nombre:"));
        panelCampos.add(txtNombre);
        panelCampos.add(new JLabel("Teléfono:"));
        panelCampos.add(txtTelefono);
        panelCampos.add(new JLabel("Dirección:"));
        panelCampos.add(txtDireccion);
        panelCampos.add(new JLabel("Correo:"));
        panelCampos.add(txtCorreo);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        btnGuardar.addActionListener(e -> {
            if (txtNombre.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialogo, "El nombre es obligatorio.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            resultado[0] = new Cliente(
                    cedula,
                    txtNombre.getText().trim(),
                    txtTelefono.getText().trim(),
                    txtDireccion.getText().trim(),
                    txtCorreo.getText().trim()
            );
            dialogo.dispose();
        });

        btnCancelar.addActionListener(e -> dialogo.dispose());

        dialogo.add(panelCampos, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        dialogo.setSize(380, 290);
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);

        return resultado[0];
    }

    /**
     * Abre una ventana modal (JDialog) que simula la vista previa 
     * impresa de la última factura generada con todos los datos del 
     * cliente, productos y total.
     */
    private void mostrarVentanaImpresionFactura() {
        Factura factura = controlador.getUltimaFacturaGenerada();
        if (factura == null) {
            JOptionPane.showMessageDialog(this,
                    "Primero debe crear una factura para poder imprimirla.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String textoFactura = controlador.generarTextoFactura(factura, cedulaEncargado);
        controlador.imprimirFactura(textoFactura);

        JDialog dialogoFactura = new JDialog(this, "Vista Previa de Factura #" + factura.getIdFactura(), true);
        dialogoFactura.setLayout(new BorderLayout(10, 10));

        JTextArea areaFactura = new JTextArea(textoFactura);
        areaFactura.setEditable(false);
        areaFactura.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaFactura.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JScrollPane scroll = new JScrollPane(areaFactura);
        dialogoFactura.add(scroll, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dialogoFactura.dispose());
        panelInferior.add(btnCerrar);

        dialogoFactura.add(panelInferior, BorderLayout.SOUTH);
        dialogoFactura.setSize(480, 520);
        dialogoFactura.setLocationRelativeTo(this);
        dialogoFactura.setVisible(true);
    }

    // Metodos getters para el controlador

    /**
     * Obtiene la cédula del cliente ingresada en el campo de texto.
     *
     * @return cadena de texto sin espacios laterales con la cédula del cliente
     */
    public String getCedulaCliene() {
        return txtCedulaCliente.getText().trim();
    }

    /**
     * Obtiene el identificador del producto ingresado en el campo de texto.
     *
     * @return cadena de texto sin espacios laterales con el ID del producto
     */
    public String getIdProducto() {
        return txtIdProducto.getText().trim();
    }

    /**
     * Obtiene la cantidad ingresada en el campo de texto.
     *
     * @return cadena de texto sin espacios laterales con la cantidad solicitada
     */
    public String getCantidad() {
        return txtCantidad.getText().trim();
    }

    /**
     * Devuelve el componente JTextArea donde se visualiza el detalle de la factura.
     *
     * @return componente JTextArea de detalle
     */
    public JTextArea getTextAreaDetalle() {
        return txtAreaDetalle;
    }

    /**
     * Devuelve la cédula del usuario encargado que tiene la sesión activa.
     *
     * @return cadena con la cédula del encargado
     */
    public String getCedulaEncargado() {
        return cedulaEncargado;
    }

    // MÉTODOS PARA ASOCIAR LOS EVENTOS DESDE EL CONTROLADOR

    /**
     * Asocia un escuchador de eventos al botón de añadir producto.
     *
     * @param listener objeto ActionListener que responderá al clic del botón
     */
    public void agregarListenerAniadir(java.awt.event.ActionListener listener) {
        btnAniadirProducto.addActionListener(listener);
    }

    /**
     * Asocia un escuchador de eventos al botón de crear factura.
     *
     * @param listener objeto ActionListener que responderá al clic del botón
     */
    public void agregarListenerCrear(java.awt.event.ActionListener listener) {
        btnCrearFactura.addActionListener(listener);
    }

    /**
     * Asocia un escuchador de eventos al botón de imprimir factura.
     *
     * @param listener objeto ActionListener que responderá al clic del botón
     */
    public void agregarListenerImprimir(java.awt.event.ActionListener listener) {
        btnImprimirFactura.addActionListener(listener);
    }

    /**
     * Asocia un escuchador de eventos al botón de regresar al menú principal.
     *
     * @param listener objeto ActionListener que responderá al clic del botón
     */
    public void agregarListenerRegresar(java.awt.event.ActionListener listener) {
        btnRegresar.addActionListener(listener);
    }
}
