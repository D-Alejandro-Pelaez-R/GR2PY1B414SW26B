package com.Pantallas;

import com.controladores.controladorInventario;
import com.model.Producto;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;


/**
 * Pantalla de inventario de la ferretería: muestra el listado de productos
 * y permite añadir, eliminar (por ID) y modificar (por ID) productos.
 */
public class Pantalla_Inventario extends javax.swing.JFrame {
    
    private final controladorInventario controlador;
    
    /**
     * Crea la pantalla recibiendo el controlador desde Main
     */
    public Pantalla_Inventario(controladorInventario controlador) {
        this.controlador = controlador;
        initComponents();
        setSize(850, 500);
        setLocationRelativeTo(null);
        cargarTabla();
    }

    /**
     * Vuelve a llenar la tabla con los productos actuales
     */
    private void cargarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) jTProductos.getModel();
        modelo.setRowCount(0);
        for (int i = 0; i < controlador.obtenerListadoProductos().size(); i++) {
            Producto p = controlador.obtenerListadoProductos().get(i);
            modelo.addRow(new Object[]{
                p.getIdProducto(),
                p.getNombre(),
                p.getDescripcion(),
                p.getPrecio(),
                p.getStock()
            });
        }
    }
    
    /**
 * Genera el siguiente ID disponible con formato F001, F002, ...
 */
private String generarNuevoId() {
    java.util.Set<Integer> usados = new java.util.HashSet<>();
    for (Producto p : controlador.obtenerListadoProductos()) {
        String id = p.getIdProducto();
        if (id != null && id.matches("F\\d+")) {
            usados.add(Integer.parseInt(id.substring(1)));
        }
    }
    int n = 1;
    while (usados.contains(n)) {
        n++;
    }
    return String.format("F%03d", n);
}
    /**
     * Abre un cuadro de diálogo modal para añadir (si producto es null)
     * o modificar (si producto no es null) un producto del inventario.
     */
    private Producto mostrarDialogoProducto(Producto producto) {
        final Producto[] resultado = { null };

        JDialog dialogo = new JDialog(this, true);
        dialogo.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialogo.setLayout(new BorderLayout());

        JPanel jPCampos = new JPanel(new GridLayout(5, 2, 10, 10));
        jPCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JTextField jTId = new JTextField();
        JTextField jTNombre = new JTextField();
        JTextField jTDescripcion = new JTextField();
        JTextField jTPrecio = new JTextField();
        JTextField jTStock = new JTextField();

        jPCampos.add(new JLabel("ID:"));
        jPCampos.add(jTId);
        jPCampos.add(new JLabel("Nombre:"));
        jPCampos.add(jTNombre);
        jPCampos.add(new JLabel("Descripción:"));
        jPCampos.add(jTDescripcion);
        jPCampos.add(new JLabel("Precio:"));
        jPCampos.add(jTPrecio);
        jPCampos.add(new JLabel("Stock:"));
        jPCampos.add(jTStock);

        JPanel jPBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton jBGuardar = new JButton("Guardar");
        JButton jBCancelar = new JButton("Cancelar");
        jPBotones.add(jBGuardar);
        jPBotones.add(jBCancelar);

        dialogo.add(jPCampos, BorderLayout.CENTER);
        dialogo.add(jPBotones, BorderLayout.SOUTH);

        if (producto != null) {
            dialogo.setTitle("Modificar producto");
            jTId.setText(producto.getIdProducto());
            jTId.setEditable(false);
            jTNombre.setText(producto.getNombre());
            jTDescripcion.setText(producto.getDescripcion());
            jTPrecio.setText(String.valueOf(producto.getPrecio()));
            jTStock.setText(String.valueOf(producto.getStock()));
        } else {
            dialogo.setTitle("Añadir producto");
            jTId.setText(generarNuevoId());
            jTId.setEditable(false);
        }

        jBGuardar.addActionListener(evt -> {
            String id = jTId.getText().trim();
            String nombre = jTNombre.getText().trim();
            String descripcion = jTDescripcion.getText().trim();

            if (id.isEmpty() || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(dialogo, "El ID y el nombre son obligatorios.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                double precio = Double.parseDouble(jTPrecio.getText().trim().replace(',', '.'));
                int stock = Integer.parseInt(jTStock.getText().trim());
                if (precio < 0 || stock < 0) {
                    JOptionPane.showMessageDialog(dialogo, "El precio y el stock no pueden ser negativos.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                resultado[0] = new Producto(id, nombre, descripcion, precio, stock);
                dialogo.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialogo,
                        "El precio debe ser un número y el stock un número entero.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        jBCancelar.addActionListener(evt -> {
            resultado[0] = null;
            dialogo.dispose();
        });

        dialogo.pack();
        dialogo.setSize(420, 320);
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);

        return resultado[0];
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLTitulo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTProductos = new javax.swing.JTable();
        jPBotones = new javax.swing.JPanel();
        jBAniadir = new javax.swing.JButton();
        jBEliminar = new javax.swing.JButton();
        jBModificar = new javax.swing.JButton();
        jBRegresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ferretería - Inventario");

        jLTitulo.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLTitulo.setText("INVENTARIO");
        jLTitulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 0, 10, 0));
        getContentPane().add(jLTitulo, java.awt.BorderLayout.NORTH);

        jTProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Descripción", "Precio", "Stock"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTProductos);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jBAniadir.setText("Añadir producto");
        jBAniadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAniadirActionPerformed(evt);
            }
        });
        jPBotones.add(jBAniadir);

        jBEliminar.setText("Eliminar producto");
        jBEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEliminarActionPerformed(evt);
            }
        });
        jPBotones.add(jBEliminar);

        jBModificar.setText("Modificar producto");
        jBModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBModificarActionPerformed(evt);
            }
        });
        jPBotones.add(jBModificar);

        jBRegresar.setText("Regresar al menu");
        jBRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRegresarActionPerformed(evt);
            }
        });
        jPBotones.add(jBRegresar);

        getContentPane().add(jPBotones, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBAniadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAniadirActionPerformed
        // Abre el diálogo con los campos vacíos
        Producto nuevo = mostrarDialogoProducto(null);
        if (nuevo == null) {
            return; // se canceló
        }
        if (controlador.existeProducto(nuevo.getIdProducto())) {
            JOptionPane.showMessageDialog(this, "Ya existe un producto con ese ID.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (controlador.aniadirProducto(nuevo)) {
            cargarTabla();
            JOptionPane.showMessageDialog(this, "Producto añadido correctamente.");
        }
    }//GEN-LAST:event_jBAniadirActionPerformed

    private void jBEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEliminarActionPerformed
        String id = JOptionPane.showInputDialog(this, "Ingrese el ID del producto a eliminar:");
        if (id == null) {
            return; // se canceló
        }
        id = id.trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un ID.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Producto p = controlador.buscarPorId(id);
        if (p == null) {
            JOptionPane.showMessageDialog(this, "No existe un producto con ese ID.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el producto \"" + p.getNombre() + "\"?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            controlador.eliminarProduct(id);
            cargarTabla();
            JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
        }
    }//GEN-LAST:event_jBEliminarActionPerformed

    private void jBModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBModificarActionPerformed
        String id = JOptionPane.showInputDialog(this, "Ingrese el ID del producto a modificar:");
        if (id == null) {
            return; // se canceló
        }
        id = id.trim();
        Producto p = controlador.buscarPorId(id);
        if (p == null) {
            JOptionPane.showMessageDialog(this, "No existe un producto con ese ID.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Abre el diálogo con la información actual del producto
        Producto modificado = mostrarDialogoProducto(p);
        if (modificado != null) {
            controlador.actualizarProducto(modificado);
            cargarTabla();
            JOptionPane.showMessageDialog(this, "Producto modificado correctamente.");
        }
    }//GEN-LAST:event_jBModificarActionPerformed

    private void jBRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRegresarActionPerformed
        // TODO add your handling code here:
        this.dispose(); // cierra la pantalla de inventario
    }//GEN-LAST:event_jBRegresarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAniadir;
    private javax.swing.JButton jBEliminar;
    private javax.swing.JButton jBModificar;
    private javax.swing.JButton jBRegresar;
    private javax.swing.JLabel jLTitulo;
    private javax.swing.JPanel jPBotones;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTProductos;
    // End of variables declaration//GEN-END:variables
}
