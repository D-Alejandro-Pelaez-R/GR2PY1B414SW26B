package com.controladores;

import java.util.ArrayList;

import com.model.ClienteDAO;
import com.model.FacturaDAO;
import com.model.ProductoDAO;

import com.model.Cliente;
import com.model.Factura;
import com.model.Producto;

import java.time.format.DateTimeFormatter;

public class generarFactura {
    
    private final FacturaDAO facturaDao;
    private final ClienteDAO clienteDao;
    private final ProductoDAO productoDao;
    
    // Atributo de estado de la factura
    private ArrayList<Producto> productosSeleccionados;
    private Factura ultimaFacturaGenerada;
    private int contadorIdFactura;
    
    /**
     * Constructor que inicializa el controlador de facturación con 
     * los DAOs compartidos desde Main.
     *
     * @param facturaDao DAO compartido para almacenar y consultar las facturas emitidas
     * @param clienteDao DAO compartido para consultar y registrar clientes
     * @param productoDao DAO compartido para consultar disponibilidad y actualizar stock de productos
     */
    public generarFactura(FacturaDAO facturaDao, ClienteDAO clienteDao, ProductoDAO productoDao) {
        this.facturaDao = facturaDao;
        this.clienteDao = clienteDao;
        this.productoDao = productoDao;
        this.productosSeleccionados = new ArrayList<>();
        this.contadorIdFactura = facturaDao.getListadoFacturas().size() + 1;
    }
    
    /**
     * Reinicia la lista temporal de productos seleccionados y 
     * limpia la referencia a la última factura generada al 
     * iniciar una nueva operación de facturación.
     */
    public void iniciarNuevaFactura() {
        this.productosSeleccionados = new ArrayList<>();
        this.ultimaFacturaGenerada = null;
    }
    
    /**
     * Busca el producto en el inventario, valida que exista stock 
     * suficiente y lo añade o acumula en la lista temporal de productos 
     * de la factura en curso.
     *
     * @param idProducto identificador único del producto a añadir
     * @param cantidad cantidad de unidades que se desean agregar a la factura
     * @return true si el producto existe y hay stock suficiente para añadirlo; false en caso contrario
     */
    public boolean aniadirProducto(String idProducto, int cantidad) {
        if (cantidad <= 0) {
            return false;
        }
        Producto productoInventario = productoDao.obtenerProducto(idProducto);
        if (productoInventario == null) {
            return false;
        }

        int cantidadYaAgregada = 0;
        for (int i = 0; i < productosSeleccionados.size(); i++) {
            if (productosSeleccionados.get(i).getIdProducto().equals(idProducto)) {
                cantidadYaAgregada += productosSeleccionados.get(i).getStock();
            }
        }

        if (cantidad + cantidadYaAgregada > productoInventario.getStock()) {
            return false;
        }

        for (int i = 0; i < productosSeleccionados.size(); i++) {
            Producto item = productosSeleccionados.get(i);
            if (item.getIdProducto().equals(idProducto)) {
                item.setStock(item.getStock() + cantidad);
                return true;
            }
        }

        // Se guarda una copia donde 'stock' representa la cantidad comprada en esta factura
        Producto itemFactura = new Producto(
                productoInventario.getIdProducto(),
                productoInventario.getNombre(),
                productoInventario.getDescripcion(),
                productoInventario.getPrecio(),
                cantidad
        );
        productosSeleccionados.add(itemFactura);
        return true;
    }
    
    /**
     * Consulta un producto registrado en el inventario mediante 
     * su identificador.
     *
     * @param idProducto identificador único del producto a buscar
     * @return objeto Producto encontrado en el DAO, o null si no existe
     */
    public Producto buscarProductoPorId(String idProducto) {
        return productoDao.obtenerProducto(idProducto);
    }

    /**
     * Extrae y devuelve una copia de la lista de productos añadidos 
     * hasta el momento en la factura en curso.
     *
     * @return ArrayList de objetos Producto seleccionados para factura actual
     */
    public ArrayList<Producto> obtenerListadoProductos() {
        return new ArrayList<>(this.productosSeleccionados);
    }
    
    /**
     * Convierte una cadena de texto con pares de identificador y 
     * cantidad en una lista de productos.
     *
     * @param productos cadena con formato "ID:CANTIDAD,ID:CANTIDAD"
     * @return ArrayList de objetos Producto encontrados en el 
     * inventario con sus respectivas cantidades
     */
    public ArrayList<Producto> obtenerListadoProductos(String productos) {
        ArrayList<Producto> lista = new ArrayList<>();
        if (productos == null || productos.trim().isEmpty()) {
            return lista;
        }
        String[] pares = productos.split(",");
        for (int i = 0; i < pares.length; i++) {
            String[] partes = pares[i].trim().split(":");
            if (partes.length == 2) {
                Producto p = productoDao.obtenerProducto(partes[0].trim());
                if (p != null) {
                    int cant = Integer.parseInt(partes[1].trim());
                    lista.add(new Producto(p.getIdProducto(), p.getNombre(), p.getDescripcion(), p.getPrecio(), cant));
                }
            }
        }
        return lista;
    }

    /**
     * Busca y obtiene un cliente registrado a partir de su número de cédula.
     *
     * @param cedula número de cédula del cliente a consultar
     * @return objeto Cliente correspondiente a la cédula, o null si no 
     * está registrado
     */
    public Cliente obtenerCliente(String cedula) {
        return clienteDao.obtenerCliente(cedula);
    }

    /**
     * Registra un nuevo cliente en el ClienteDAO compartido siempre que 
     * no exista previamente.
     *
     * @param cliente objeto Cliente con los datos completos a registrar
     * @return true si el cliente fue registrado correctamente; false si es nulo o su cédula ya existe
     */
    public boolean registrarCliente(Cliente cliente) {
        if (cliente != null && clienteDao.obtenerCliente(cliente.getCedula()) == null) {
            clienteDao.agregarCliente(cliente);
            return true;
        }
        return false;
    }

    /**
     * Calcula el monto total a pagar sumando el producto entre precio unitario y cantidad
     * de cada ítem de la lista.
     *
     * @param productos lista de productos incluidos en la factura
     * @return valor total acumulado de los productos en formato double
     */
    public double calcularTotal(ArrayList<Producto> productos) {
        double total = 0.0;
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            total += p.getPrecio() * p.getStock();
        }
        return total;
    }

    /**
     * Genera una nueva Factura con el cliente indicado y los productos 
     * acumulados en la sesión, descuenta las unidades vendidas del stock 
     * del inventario y registra la factura en FacturaDAO.
     *
     * @param cedula número de cédula del cliente al que se emite la factura
     * @return objeto Factura generado y almacenado, o null si el cliente
     * no existe
     */
    public Factura generarFactura(String cedula) {
        Cliente clienteTemporal = obtenerCliente(cedula);
        ArrayList<Producto> listadoProductos = obtenerListadoProductos();

        if (clienteTemporal == null || listadoProductos.isEmpty()) {
            return null;
        }

        for (int i = 0; i < listadoProductos.size(); i++) {
            Producto item = listadoProductos.get(i);
            Producto prodInventario = productoDao.obtenerProducto(item.getIdProducto());
            if (prodInventario != null) {
                prodInventario.setStock(prodInventario.getStock() - item.getStock());
                productoDao.actualizarProducto(prodInventario);
            }
        }

        Factura facturaTemporal = new Factura(clienteTemporal, listadoProductos);
        facturaTemporal.setIdFactura(contadorIdFactura++);
        facturaTemporal.setTotalFactura(calcularTotal(listadoProductos));

        facturaDao.agregarFactura(facturaTemporal);
        this.ultimaFacturaGenerada = facturaTemporal;
        this.productosSeleccionados.clear();

        return facturaTemporal;
    }

    /**
     * Genera una nueva Factura a partir de la cédula del cliente y una 
     * cadena de productos, y la añade al listado de facturas del DAO.
     *
     * @param cedula número de cédula del cliente
     * @param productos cadena de productos con formato "ID:CANTIDAD,ID:CANTIDAD"
     * @return objeto Factura generado y registrado en el DAO
     */
    public Factura generarFactura(String cedula, String productos) {
        Cliente clienteTemporal = obtenerCliente(cedula);
        ArrayList<Producto> listadoProductos = obtenerListadoProductos(productos);

        Factura facturaTemporal = new Factura(clienteTemporal, listadoProductos);
        facturaTemporal.setIdFactura(contadorIdFactura++);
        facturaTemporal.setTotalFactura(calcularTotal(listadoProductos));
        facturaDao.agregarFactura(facturaTemporal);

        this.ultimaFacturaGenerada = facturaTemporal;
        return facturaTemporal;
    }

    /**
     * Devuelve la última factura emitida durante la sesión actual de 
     * facturación.
     *
     * @return objeto Factura más reciente, o null si aún no se ha creado
     * ninguna
     */
    public Factura getUltimaFacturaGenerada() {
        return ultimaFacturaGenerada;
    }

    /**
     * Construye el texto formateado de una factura incluyendo encabezado, 
     * fecha, encargado, datos completos del cliente, detalle de productos y total a pagar.
     *
     * @param factura objeto Factura del cual se extraerán los datos para 
     * el comprobante
     * @param cedulaEncargado número de cédula del usuario encargado que 
     * emite la factura
     * @return cadena de texto formateada lista para visualizarse o imprimirse
     */
    public String generarTextoFactura(Factura factura, String cedulaEncargado) {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        Cliente c = factura.getClienteFactura();

        StringBuilder sb = new StringBuilder();
        sb.append("==================================================\n");
        sb.append("              FERRETERÍA - FACTURA                \n");
        sb.append("==================================================\n");
        sb.append("Factura N° : ").append(String.format("%05d", factura.getIdFactura())).append("\n");
        sb.append("Fecha/Hora : ").append(factura.getFechaHoraEmisionFactura().format(formatoFecha)).append("\n");
        sb.append("Encargado  : ").append(cedulaEncargado).append("\n");
        sb.append("--------------------------------------------------\n");
        sb.append("DATOS DEL CLIENTE\n");
        sb.append("Cédula     : ").append(c.getCedula()).append("\n");
        sb.append("Nombre     : ").append(c.getNombre()).append("\n");
        sb.append("Teléfono   : ").append(c.getTelefono()).append("\n");
        sb.append("Dirección  : ").append(c.getDireccion()).append("\n");
        sb.append("Correo     : ").append(c.getCorreo()).append("\n");
        sb.append("--------------------------------------------------\n");
        sb.append(String.format("%-6s %-18s %5s %8s %9s\n", "ID", "PRODUCTO", "CANT", "P.UNIT", "SUBTOTAL"));
        sb.append("--------------------------------------------------\n");

        ArrayList<Producto> items = factura.getProductosFactura();
        for (int i = 0; i < items.size(); i++) {
            Producto p = items.get(i);
            double subtotal = p.getPrecio() * p.getStock();
            sb.append(String.format("%-6s %-18s %5d %8.2f %9.2f\n",
                    p.getIdProducto(),
                    p.getNombre().length() > 18 ? p.getNombre().substring(0, 18) : p.getNombre(),
                    p.getStock(),
                    p.getPrecio(),
                    subtotal));
        }

        sb.append("==================================================\n");
        sb.append(String.format("TOTAL A PAGAR: $%.2f\n", factura.getTotalFactura()));
        sb.append("==================================================\n");
        return sb.toString();
    }

    /**
     * Imprime en la salida estándar el detalle completo de la factura 
     * formateada.
     *
     * @param factura cadena de texto con el contenido formateado de la factura a imprimir
     */
    public void imprimirFactura(String factura) {
        System.out.println(factura);
    }
}
