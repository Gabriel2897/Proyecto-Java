package com.coderhouse.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.coderhouse.entidades.Cliente;
import com.coderhouse.entidades.DetalleVenta;
import com.coderhouse.entidades.Producto;
import com.coderhouse.entidades.Venta;


@Component
public class MenuConsola {

    @Autowired
    private ControlCliente controlCliente;
    
    @Autowired
    private ControlProducto controlProducto;
    
    @Autowired
    private ControlVenta controlVenta;
    
    public void mostrarMenuConsola() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        try {
            do {
                System.out.println("======= Menú Principal =======");
                System.out.println("1. Gestionar Clientes");
                System.out.println("2. Gestionar Productos");
                System.out.println("3. Realizar Venta");
                System.out.println("4. Gestionar Ventas");
                System.out.println("5. Salir");
                System.out.print("Ingrese su opción: ");
                opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        mostrarMenuClientes();
                        break;
                    case 2:
                        mostrarMenuProductos();
                        break;
                    case 3:
                        realizarVenta();
                        break;
                    case 4:
                        gestionarVentas();
                        break;
                    case 5:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción inválida. Por favor, ingrese una opción válida.");
                }
            } while (opcion != 5);
        } catch (NoSuchElementException e) {
            System.out.println("Se ha producido un error al leer la entrada del usuario. Saliendo del programa...");
        } finally {
        }
    }

    
    private void mostrarMenuClientes() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        while (true) {
            System.out.println("======= Menú Clientes =======");
            System.out.println("1. Listar Clientes");
            System.out.println("2. Crear Cliente");
            System.out.println("3. Actualizar Cliente");
            System.out.println("4. Eliminar Cliente");
            System.out.println("5. Volver al Menú Principal");
            System.out.print("Ingrese su opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    listarClientes();
                    break;
                case 2:
                    crearCliente();
                    break;
                case 3:
                    actualizarCliente();
                    break;
                case 4:
                    eliminarCliente();
                    break;
                case 5:
                    System.out.println("Volviendo al Menú Principal...");
                    return; // Salir del método y regresar al menú principal
                default:
                    System.out.println("Opción inválida. Por favor, ingrese una opción válida.");
            }
        }
    }

    private void listarClientes() {
        List<Cliente> clientes = controlCliente.getAllClientes();
        if (!clientes.isEmpty()) {
            System.out.println("\n=== LISTA DE CLIENTES ===");
            for (Cliente cliente : clientes) {
                System.out.println("ID: " + cliente.getId());
                System.out.println("Nombre completo: " + cliente.getNombre() + " " + cliente.getApellido());
                System.out.println("DNI: " + cliente.getDni());
                System.out.println("Email: " + cliente.getEmail());
                System.out.println("Dirección: " + cliente.getDireccion());
                System.out.println("Teléfono: " + cliente.getTelefono());
                System.out.println("-------------------------");
            }
        } else {
            System.out.println("No hay clientes registrados.");
        }
    }

   
    private void crearCliente() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el nombre del cliente: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el apellido del cliente: ");
        String apellido = scanner.nextLine();
        System.out.print("Ingrese el DNI del cliente: ");
        String dni = scanner.nextLine();
        System.out.print("Ingrese el email del cliente: ");
        String email = scanner.nextLine();
        System.out.print("Ingrese la dirección del cliente: ");
        String direccion = scanner.nextLine();
        System.out.print("Ingrese el teléfono del cliente: ");
        String telefono = scanner.nextLine();
        
        Cliente nuevoCliente = new Cliente(nombre, apellido, dni, email, direccion, telefono);
        controlCliente.crearCliente(nuevoCliente);
        System.out.println("Cliente creado con éxito.");
    }

    
    private void actualizarCliente() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el ID del cliente a actualizar: ");
        long id = scanner.nextLong();
        scanner.nextLine(); // Consumir el salto de línea pendiente

        try {
            System.out.print("Ingrese el nuevo nombre del cliente: ");
            String nombre = scanner.nextLine();
            System.out.print("Ingrese el nuevo apellido del cliente: ");
            String apellido = scanner.nextLine();
            System.out.print("Ingrese el nuevo DNI del cliente: ");
            String dni = scanner.nextLine();
            System.out.print("Ingrese el nuevo email del cliente: ");
            String email = scanner.nextLine();
            System.out.print("Ingrese la nueva dirección del cliente: ");
            String direccion = scanner.nextLine();
            System.out.print("Ingrese el nuevo teléfono del cliente: ");
            String telefono = scanner.nextLine();
            
            // Crear un nuevo objeto Cliente utilizando el constructor por defecto
            Cliente clienteActualizado = new Cliente();
            
            // Establecer los valores de los atributos utilizando los métodos setters
            clienteActualizado.setId(id);
            clienteActualizado.setNombre(nombre);
            clienteActualizado.setApellido(apellido);
            clienteActualizado.setDni(dni);
            clienteActualizado.setEmail(email);
            clienteActualizado.setDireccion(direccion);
            clienteActualizado.setTelefono(telefono);
            
            // Llamar al método actualizarCliente del controlador de clientes
            controlCliente.actualizarCliente(id, clienteActualizado);
            System.out.println("Cliente actualizado con éxito.");
        } catch (RuntimeException e) {
            System.out.println("Error: Cliente no encontrado con ID: " + id + ". Por favor, ingrese un ID válido.");
        }
    }

    private void eliminarCliente() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el ID del cliente a eliminar: ");
        long id = scanner.nextLong();
        controlCliente.eliminarCliente(id);
        System.out.println("Cliente eliminado con éxito.");
    }


    
    private void mostrarMenuProductos() {
        int opcion;

        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\n*** Menú de Gestión de Productos ***");
            System.out.println("1. Listar todos los productos");
            System.out.println("2. Crear un nuevo producto");
            System.out.println("3. Actualizar un producto existente");
            System.out.println("4. Eliminar un producto");
            System.out.println("5. Volver al menú principal");
            System.out.print("Ingrese la opción deseada: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    listarProductos();
                    break;
                case 2:
                    crearProducto();
                    break;
                case 3:
                    actualizarProducto();
                    break;
                case 4:
                    eliminarProducto();
                    break;
                case 5:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
            }
        } while (opcion != 5);
        
    }
    private void listarProductos() {
        System.out.println("\n=== LISTA DE PRODUCTOS ===");
        List<Producto> productos = controlProducto.getAllProductos();
        if (!productos.isEmpty()) {
            for (Producto producto : productos) {
                System.out.println("ID: " + producto.getId() + ", Nombre: " + producto.getNombre() + ", Precio: $" + producto.getPrecio() + ", Cantidad: " + producto.getCantidad());
            }
        } else {
            System.out.println("No hay productos registrados.");
        }
    }

    private void crearProducto() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== CREAR NUEVO PRODUCTO ===");
        System.out.print("Ingrese el nombre del producto: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el precio del producto: ");
        double precio = scanner.nextDouble();
        System.out.print("Ingrese la cantidad en stock del producto: ");
        int cantidad = scanner.nextInt();

        // Crear el objeto Producto
        Producto nuevoProducto = new Producto(nombre, precio, cantidad);

        // Llamar al método del controlador para crear el producto
        ResponseEntity<Producto> response = controlProducto.crearProducto(nuevoProducto);

        // Verificar la respuesta del controlador
        if (response.getStatusCode() == HttpStatus.CREATED) {
            System.out.println("Producto creado con éxito.");
        } else {
            System.out.println("Error al crear el producto. Por favor, inténtelo de nuevo.");
        }
    }


    private void actualizarProducto() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== ACTUALIZAR PRODUCTO ===");
        System.out.print("Ingrese el ID del producto a actualizar: ");
        long id = scanner.nextLong();
        scanner.nextLine();
        System.out.print("Ingrese el nuevo nombre del producto: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el nuevo precio del producto: ");
        double precio = scanner.nextDouble();
        System.out.print("Ingrese la nueva cantidad en stock del producto: ");
        int cantidad = scanner.nextInt();

        // Crear el objeto Producto actualizado
        Producto productoActualizado = new Producto(nombre, precio, cantidad);

        // Llamar al método del controlador para actualizar el producto
        ResponseEntity<Producto> response = controlProducto.actualizarProducto(id, productoActualizado);

        // Verificar la respuesta del controlador
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Producto actualizado con éxito.");
        } else {
            System.out.println("Error al actualizar el producto. Por favor, inténtelo de nuevo.");
        }
    }


    private void eliminarProducto() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== ELIMINAR PRODUCTO ===");
        System.out.print("Ingrese el ID del producto a eliminar: ");
        long id = scanner.nextLong();

        // Llamar al método del controlador para eliminar el producto
        ResponseEntity<Void> response = controlProducto.eliminarProducto(id);

        // Verificar la respuesta del controlador
        if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            System.out.println("Producto eliminado con éxito.");
        } else {
            System.out.println("Error al eliminar el producto. Por favor, inténtelo de nuevo.");
        }
    }

    
    private void realizarVenta() {
        Scanner scanner = new Scanner(System.in);
        List<DetalleVenta> detallesVenta = new ArrayList<>();

        try {
        	 // Mostrar cliente
        	System.out.println("=== Lista de Clientes ===");
        	List<Cliente> clientes = controlCliente.getAllClientes();
        	if (!clientes.isEmpty()) {
        	    for (Cliente cliente : clientes) {
        	        System.out.println("ID:" + cliente.getId() + " - "+ cliente.getNombre() +" "+ cliente.getApellido());
        	    }
        	} else {
        	    System.out.println("No hay clientes registrados.");
        	}

            // Paso 1: Seleccionar un cliente
        	System.out.print("Ingrese el ID del cliente: ");
        	long idCliente = scanner.nextLong();
        	scanner.nextLine();
            ResponseEntity<Cliente> responseCliente = controlCliente.getClienteById(idCliente);
            if (responseCliente.getStatusCode() == HttpStatus.OK) {
                Cliente cliente = responseCliente.getBody();
                
                // Paso 2: Mostrar la lista de productos
                System.out.println("=== Lista de Productos ===");
                List<Producto> productos = controlProducto.getAllProductos();
                if (!productos.isEmpty()) {
                    for (Producto producto : productos) {
                        System.out.println("ID: - " + producto.getId()+ " "+ producto.getNombre() + ", Precio: $" + producto.getPrecio() + "   - Cantidad Disponible : " + producto.getCantidad());
                    }
                    // Paso 3: Comprar productos
                    boolean comprarMasProductos = true;
                    while (comprarMasProductos) {
                        System.out.print("Ingrese el ID del producto que desea comprar: ");
                        long idProducto = scanner.nextLong();
                        ResponseEntity<Producto> responseProducto = controlProducto.getProductoById(idProducto);
                        if (responseProducto.getStatusCode() == HttpStatus.OK) {
                            Producto producto = responseProducto.getBody();
                            System.out.print("Ingrese la cantidad que desea comprar: ");
                            int cantidad = scanner.nextInt();
                            DetalleVenta detalleVenta = new DetalleVenta();
                            detalleVenta.setProducto(producto);
                            detalleVenta.setCantidad(cantidad);
                            detallesVenta.add(detalleVenta);
                            System.out.print("¿Desea comprar otro producto? (S/N): ");
                            String respuesta = scanner.next();
                            comprarMasProductos = respuesta.equalsIgnoreCase("S");
                        } else {
                            System.out.println("Producto no encontrado.");
                        }
                    }
                 // Paso 4: Realizar la venta
                    Venta venta = new Venta();
                    venta.setCliente(cliente);
                    venta.setDetalles(detallesVenta);
                    ResponseEntity<Venta> responseVenta = controlVenta.crearVenta(venta);
                    if (responseVenta.getStatusCode() == HttpStatus.CREATED) {
                        // Venta realizada con éxito
                        Venta ventaCreada = responseVenta.getBody();
                        System.out.println("Venta realizada con éxito:");
                        System.out.println("ID de Venta: " + ventaCreada.getId());
                        System.out.println("Fecha de Venta: " + ventaCreada.getFecha());
                        System.out.println("Cliente: " + ventaCreada.getCliente().getNombre() + " " + ventaCreada.getCliente().getApellido());
                        System.out.println("Total de Venta: $" + ventaCreada.getTotal());

                        System.out.println("Productos comprados:");
                        for (DetalleVenta detalle : ventaCreada.getDetalles()) {
                            Producto producto = detalle.getProducto();
                            System.out.println("   - Nombre: " + producto.getNombre() + ", Precio: $" + producto.getPrecio() + ", Cantidad: " + detalle.getCantidad());
                        }
                    } else {
                        // Error al realizar la venta
                        System.out.println("Error al realizar la venta.");
                    }
                } else {
                    System.out.println("No hay productos disponibles. Cancelando venta.");
                }
            } else {
                System.out.println("Cliente no encontrado. Cancelando venta.");
            }
        } catch (InputMismatchException ex) {
            System.out.println("Error: Entrada inválida. Asegúrese de ingresar un número.");
        } catch (NoSuchElementException ex) {
            System.out.println("Error: Entrada no encontrada.");
        } catch (Exception ex) {
            System.out.println("Error inesperado: " + ex.getMessage());
        } finally {
           
        }
    }
    
    private void gestionarVentas() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n======= Menú de Gestión de Ventas =======");
            System.out.println("1. Listar todas las ventas");
            System.out.println("2. Actualizar venta");
            System.out.println("3. Eliminar venta");
            System.out.println("4. Volver al Menú Principal");
            System.out.print("Ingrese su opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    listarVentas();
                    break;
                case 2:
                    actualizarVenta();
                    break;
                case 3:
                    eliminarVenta();
                    break;
                case 4:
                    System.out.println("Volviendo al Menú Principal...");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, ingrese una opción válida.");
            }
        } while (opcion != 4);
    }
    private void listarVentas() {
        List<Venta> ventas = controlVenta.getAllVentas();
        if (!ventas.isEmpty()) {
            System.out.println("\n=== LISTA DE VENTAS ===");
            for (Venta venta : ventas) {
                System.out.println("ID de Venta: " + venta.getId());
                System.out.println("Fecha de Venta: " + venta.getFecha());
                System.out.println("Cliente: " + venta.getCliente().getNombre() + " " + venta.getCliente().getApellido());
                System.out.println("Total de Venta: $" + venta.getTotal());

                List<DetalleVenta> detalles = venta.getDetalles();
                System.out.println("Productos comprados:");
                if (!detalles.isEmpty()) {
                    for (DetalleVenta detalle : detalles) {
                        Producto producto = detalle.getProducto();
                        System.out.println("   - Nombre: " + producto.getNombre() + ", Precio: $" + producto.getPrecio() + ", Cantidad: " + detalle.getCantidad());
                    }
                } else {
                    System.out.println("   No hay productos comprados.");
                }
                System.out.println("-------------------------");
                System.out.println("-------------------------");
            }
        } else {
            System.out.println("No hay ventas registradas.");
        }
    }
 //No funciona bien cambiar
    private void actualizarVenta() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el ID de la venta a actualizar: ");
        long idVenta = scanner.nextLong();
        scanner.nextLine();

        ResponseEntity<Venta> responseVenta = controlVenta.getVentaById(idVenta);

        if (responseVenta.getStatusCode() == HttpStatus.OK) {
            Venta venta = responseVenta.getBody();

         // Mostrar los detalles actuales de la venta
            System.out.println("Detalles de la venta a actualizar:");
            System.out.println("ID: " + venta.getId());
            System.out.println("Fecha: " + venta.getFecha());
            System.out.println("Cliente: " + venta.getCliente().getNombre() + " " + venta.getCliente().getApellido());
            System.out.println("Total: $" + venta.getTotal());

            // Mostrar los detalles de los productos comprados
            System.out.println("Productos comprados:");
            for (DetalleVenta detalle : venta.getDetalles()) {
                Producto producto = detalle.getProducto();
                System.out.println("   - Producto: " + producto.getNombre());
                System.out.println("     Precio unitario: $" + producto.getPrecio());
                System.out.println("     Cantidad: " + detalle.getCantidad());
                System.out.println("     Total parcial: $" + detalle.getCantidad() * producto.getPrecio());
                System.out.println("--------------------------------------------");
            }

            // Mostrar opciones adicionales para agregar, cambiar o actualizar productos
            System.out.println("\nOpciones adicionales:");
            System.out.println("1. Agregar producto");
            System.out.println("2. Cambiar producto");
            System.out.println("3. Cambiar cantidad de producto");
            System.out.println("4. Eliminar producto");
            System.out.println("5. Continuar sin cambios");
            System.out.print("Ingrese la opción deseada: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
             //Agregar un nuevo producto
            case 1:
                System.out.print("Ingrese el ID del nuevo producto: ");
                long idNuevoProducto = scanner.nextLong();
                scanner.nextLine();
                System.out.print("Ingrese la cantidad del nuevo producto: ");
                int cantidadNuevoProducto = scanner.nextInt();
                scanner.nextLine();

                // Obtener el producto utilizando el ControlProducto
                ResponseEntity<Producto> responseProducto = controlProducto.getProductoById(idNuevoProducto);

                if (responseProducto.getStatusCode() == HttpStatus.OK) {
                    Producto nuevoProducto = responseProducto.getBody();
                    DetalleVenta nuevoDetalle = new DetalleVenta();
                    nuevoDetalle.setProducto(nuevoProducto);
                    nuevoDetalle.setCantidad(cantidadNuevoProducto);
                    venta.getDetalles().add(nuevoDetalle);
                    System.out.println("Producto agregado con éxito a la venta.");

                 // Mostrar la venta actualizada
                 System.out.println("\nVenta actualizada:");
                 mostrarDetallesVentaActualizada(venta);
                 System.out.println("Venta actualizada con éxito.");
                 System.out.println("-------------------------");
                } else {
                    System.out.println("No se encontró ningún producto con el ID proporcionado.");
                }
                break;

            case 2:
                // Cambiar producto
                System.out.println("Productos actuales en la venta:");
                for (DetalleVenta detalle : venta.getDetalles()) {
                    System.out.println("ID: " + detalle.getProducto().getId() + ", Nombre: " + detalle.getProducto().getNombre() + ", Cantidad: " + detalle.getCantidad());
                }
                System.out.print("Ingrese el ID del producto que desea cambiar: ");
                long idProductoACambiar = scanner.nextLong();
                scanner.nextLine();
                System.out.print("Ingrese el nuevo ID del producto: ");
                long nuevoIdProducto = scanner.nextLong();
                scanner.nextLine();

                // Obtener el nuevo producto utilizando el ControlProducto
                ResponseEntity<Producto> responseNuevoProducto = controlProducto.getProductoById(nuevoIdProducto);

                if (responseNuevoProducto.getStatusCode() == HttpStatus.OK) {
                    Producto productoActualizado = responseNuevoProducto.getBody();
                    // Buscar el detalle del producto a cambiar en la lista de detalles de la venta
                    for (DetalleVenta detalle : venta.getDetalles()) {
                        if (detalle.getProducto().getId() == idProductoACambiar) {
                            detalle.setProducto(productoActualizado);
                            System.out.println("Producto cambiado con éxito en la venta.");
                            return;
                        }
                    }
                    System.out.println("No se encontró ningún producto con el ID proporcionado en la venta.");
                } else {
                    System.out.println("No se encontró ningún producto con el nuevo ID proporcionado.");
                }
                break;

            case 3:
                // Cambiar cantidad de producto
                System.out.println("Productos actuales en la venta:");
                for (DetalleVenta detalle : venta.getDetalles()) {
                    System.out.println("ID: " + detalle.getProducto().getId() + ", Nombre: " + detalle.getProducto().getNombre() + ", Cantidad: " + detalle.getCantidad());
                }
                System.out.print("Ingrese el ID del producto del que desea cambiar la cantidad: ");
                long idProductoACambiarCantidad = scanner.nextLong();
                scanner.nextLine(); 
                System.out.print("Ingrese la nueva cantidad: ");
                int nuevaCantidad = scanner.nextInt();

                // Buscar el detalle del producto a cambiar en la lista de detalles de la venta
                for (DetalleVenta detalle : venta.getDetalles()) {
                    if (detalle.getProducto().getId() == idProductoACambiarCantidad) {
                        detalle.setCantidad(nuevaCantidad);
                        System.out.println("Cantidad del producto cambiada con éxito en la venta.");
                        return;
                    }
                }
                System.out.println("No se encontró ningún producto con el ID proporcionado en la venta.");
                break;

            case 4:
                // Eliminar producto
                System.out.println("Productos actuales en la venta:");
                for (DetalleVenta detalle : venta.getDetalles()) {
                    System.out.println("ID: " + detalle.getProducto().getId() + ", Nombre: " + detalle.getProducto().getNombre() + ", Cantidad: " + detalle.getCantidad());
                }
                System.out.print("Ingrese el ID del producto que desea eliminar: ");
                long idProductoAEliminar = scanner.nextLong();

                // Buscar el detalle del producto a eliminar en la lista de detalles de la venta
                DetalleVenta detalleAEliminar = null;
                for (DetalleVenta detalle : venta.getDetalles()) {
                    if (detalle.getProducto().getId() == idProductoAEliminar) {
                        detalleAEliminar = detalle;
                        break;
                    }
                }

                if (detalleAEliminar != null) {
                    // Eliminar el detalle del producto de la lista de detalles de la venta
                    venta.getDetalles().remove(detalleAEliminar);
                    System.out.println("Producto eliminado con éxito de la venta.");
                } else {
                    System.out.println("No se encontró ningún producto con el ID proporcionado en la venta.");
                }
                break;

            case 5:
                // Continuar sin cambios
                return;
                default:
                    System.out.println("Opción inválida. Volviendo al menú principal.");
                    return;
            }

            ResponseEntity<Venta> responseActualizacion = controlVenta.actualizarVenta(idVenta, venta);

            if (responseActualizacion.getStatusCode() == HttpStatus.OK) {
                System.out.println("Venta actualizada con éxito.");
            } else {
                System.out.println("Error al actualizar la venta. Por favor, inténtelo de nuevo.");
            }
        } else {
            System.out.println("No se encontró ninguna venta con el ID proporcionado.");
        }
    }
    public void eliminarVenta() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el ID de la venta que desea eliminar: ");
        long idVentaAEliminar = scanner.nextLong();
        scanner.nextLine(); // Consumir el salto de línea pendiente

        // Llamar al método del controlador para eliminar la venta
        ResponseEntity<Void> responseEliminarVenta = controlVenta.eliminarVenta(idVentaAEliminar);

        // Verificar la respuesta del controlador
        if (responseEliminarVenta.getStatusCode() == HttpStatus.NO_CONTENT) {
            System.out.println("Venta eliminada con éxito.");
        } else {
            System.out.println("Error al eliminar la venta. Por favor, inténtelo de nuevo.");
        }
    }
    private void mostrarDetallesVentaActualizada(Venta venta) {
        System.out.println("\nDetalles de la venta actualizada:");
        System.out.println("ID: " + venta.getId());
        System.out.println("Fecha: " + venta.getFecha());
        System.out.println("Cliente: " + venta.getCliente().getNombre() + " " + venta.getCliente().getApellido());
        System.out.println("Total: $" + venta.getTotal());

        // Mostrar los detalles de los productos comprados
        System.out.println("Productos comprados:");
        for (DetalleVenta detalle : venta.getDetalles()) {
            Producto producto = detalle.getProducto();
            System.out.println("   - Producto: " + producto.getNombre());
            System.out.println("     Precio unitario: $" + producto.getPrecio());
            System.out.println("     Cantidad: " + detalle.getCantidad());
            System.out.println("     Total parcial: $" + detalle.getCantidad() * producto.getPrecio());
            System.out.println("--------------------------------------------");
        }
    }

}

