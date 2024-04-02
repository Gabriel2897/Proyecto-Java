package com.coderhouse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.coderhouse.entidades.Cliente;
import com.coderhouse.entidades.Compra;
import com.coderhouse.entidades.Producto;
import com.coderhouse.control.ControlCliente;
import com.coderhouse.control.ControlCompra;
import com.coderhouse.control.ControlProducto;

@SpringBootApplication
public class ProyectoFinalApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ProyectoFinalApplication.class, args);
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        ControlCompra controlCompra = context.getBean(ControlCompra.class);
        ControlProducto controlProducto = context.getBean(ControlProducto.class);
        ControlCliente controlCliente = context.getBean(ControlCliente.class);
        //Menu
        while (!salir) {
            System.out.println("Menú Principal");
            System.out.println("1. Realizar una compra");
            System.out.println("2. Gestionar productos");
            System.out.println("3. Gestionar clientes");
            System.out.println("4. Gestionar compras");
            System.out.println("5. Finalizar el sistema");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();

            switch (opcion) {
                // Opcion 1 Compra
                case 1:
                    System.out.println("Realizando una compra...");

                    // Mostrar la lista de clientes disponibles y permitir al usuario seleccionar uno
                    System.out.println("Lista de clientes disponibles:");
                    List<Cliente> clientesDisponiblesCompra = controlCliente.listarClientes();
                    for (Cliente cliente : clientesDisponiblesCompra) {
                        System.out.println("ID: " + cliente.getId() + ", Nombre: " + cliente.getNombre() + " "
                                + cliente.getApellido());
                    }
                    System.out.print("Ingrese el ID del cliente: ");
                    Long idClienteCompra = scanner.nextLong();

                    // Verificar si el cliente seleccionado es válido
                    ResponseEntity<Cliente> responseClienteCompra = controlCliente.buscarClientePorId(idClienteCompra);
                    if (responseClienteCompra.getStatusCode() != HttpStatus.OK) {
                        System.out.println("No se encontró ningún cliente con el ID proporcionado. Ingrese un ID valido");
                        break;
                    }
                    Cliente clienteSeleccionadoCompra = responseClienteCompra.getBody();

                    // Mostrar la lista de productos disponibles y permitir al usuario agregar productos al carrito
                    boolean seguirComprando = true;
                    List<Producto> productosDisponiblesCompra = controlProducto.listarProductos();
                    List<Compra> carritoCompra = new ArrayList<>();
                    while (seguirComprando) {
                        System.out.println("Lista de productos disponibles:");
                        for (Producto producto : productosDisponiblesCompra) {
                            System.out.println("ID: " + producto.getId() + ", Nombre: " + producto.getNombre()
                                    + ", Precio: " + producto.getPrecio() + ", Cantidad: " + producto.getCantidad());
                        }
                        System.out.print(
                                "Ingrese el ID del producto que desea agregar al carrito (o ingrese 0 para finalizar la compra): ");
                        Long idProductoCompra = scanner.nextLong();
                        if (idProductoCompra == 0) {
                            seguirComprando = false;
                            break;
                        }

                        // Verificar si el producto seleccionado es válido
                        ResponseEntity<Producto> responseProductoCompra = controlProducto.buscarProductoPorId(idProductoCompra);
                        if (responseProductoCompra.getStatusCode() != HttpStatus.OK) {
                            System.out.println("No se encontró ningún producto con el ID proporcionado. Ingrese un ID valido ");
                            continue;
                        }
                        Producto productoSeleccionadoCompra = responseProductoCompra.getBody();

                        // Solicitar la cantidad de productos a comprar
                        System.out.print("Ingrese la cantidad de productos a comprar: ");
                        int cantidadCompra = scanner.nextInt();

                        // Crear una instancia de Compra con los datos obtenidos
                        Compra compra = new Compra(clienteSeleccionadoCompra, productoSeleccionadoCompra, cantidadCompra,
                                productoSeleccionadoCompra.getPrecio() * cantidadCompra, LocalDateTime.now());
                        carritoCompra.add(compra);
                        System.out.println("Producto agregado al carrito.");
                    }

                    // Etapa 3: Finalizar la compra
                    double totalCompra = 0;
                    for (Compra compra : carritoCompra) {
                        totalCompra += compra.getTotal();
                    }
                    System.out.println("Total de la compra: " + totalCompra);

                    // Etapa 4: Mostrar el detalle de la compra
                    for (Compra compra : carritoCompra) {
                        System.out.println("Detalle de la compra:");
                        System.out.println(
                                "Cliente: " + compra.getCliente().getNombre() + " " + compra.getCliente().getApellido());
                        System.out.println("Producto: " + compra.getProducto().getNombre());
                        System.out.println("Cantidad: " + compra.getCantidadComprada());
                        System.out.println("Total: " + compra.getTotal());
                        System.out.println("Fecha de compra: " + compra.getFechaCompra());
                        System.out.println();
                    }

                    // Realizar la compra
                    for (Compra compra : carritoCompra) {
                        controlCompra.realizarCompra(compra);
                    }
                    System.out.println("Compra realizada con éxito.");
                    break;

                // Opcion 2 Gestion de productos
                case 2:
                    System.out.println("Gestionando productos...");
                    System.out.println("Seleccione una opción:");
                    System.out.println("1. Mostrar lista de productos");
                    System.out.println("2. Agregar un nuevo producto");
                    System.out.println("3. Actualizar un producto existente");
                    System.out.println("4. Eliminar un producto");
                    int opcionProducto = scanner.nextInt();
                    switch (opcionProducto) {
                        case 1:
                            // Mostrar lista de productos
                            System.out.println("Lista de productos disponibles:");
                            List<Producto> productos = controlProducto.listarProductos();
                            for (Producto producto : productos) {
                                System.out.println("ID: " + producto.getId() + ", Nombre: " + producto.getNombre()
                                        + ", Precio: " + producto.getPrecio() + ", Cantidad: " + producto.getCantidad());
                            }
                            break;
                        case 2:
                            // Agregar un nuevo producto
                            System.out.println("Ingrese el nombre del nuevo producto:");
                            String nombreProducto = scanner.next();
                            System.out.println("Ingrese el precio del nuevo producto:");
                            double precioProducto = scanner.nextDouble();
                            System.out.println("Ingrese la cantidad del nuevo producto:");
                            int cantidadProducto = scanner.nextInt();
                            Producto nuevoProducto = new Producto(nombreProducto, precioProducto, cantidadProducto);
                            controlProducto.guardarProducto(nuevoProducto);
                            System.out.println("Producto agregado con éxito.");
                            break;
                        case 3:
                            // Actualizar un producto existente
                            System.out.println("Ingrese el ID del producto que desea actualizar:");
                            Long idProductoActualizar = scanner.nextLong();
                            System.out.println("Ingrese el nuevo nombre del producto:");
                            String nuevoNombreProducto = scanner.next();
                            System.out.println("Ingrese el nuevo precio del producto:");
                            double nuevoPrecioProducto = scanner.nextDouble();
                            System.out.println("Ingrese la nueva cantidad del producto:");
                            int nuevaCantidadProducto = scanner.nextInt();
                            Producto productoActualizado = new Producto(nuevoNombreProducto, nuevoPrecioProducto,
                                    nuevaCantidadProducto);
                            controlProducto.actualizarProducto(idProductoActualizar, productoActualizado);
                            System.out.println("Producto actualizado con éxito.");
                            break;
                        case 4:
                            // Eliminar un producto
                            System.out.println("Ingrese el ID del producto que desea eliminar:");
                            Long idProductoEliminar = scanner.nextLong();
                            controlProducto.eliminarProducto(idProductoEliminar);
                            System.out.println("Producto eliminado con éxito.");
                            break;
                        default:
                            System.out.println("Opción no válida. Intente nuevamente.");
                    }
                    break;

                case 3:
                    // Gestionar clientes
                    System.out.println("Gestionando clientes...");
                    System.out.println("Seleccione una opción:");
                    System.out.println("1. Listar clientes");
                    System.out.println("2. Agregar un nuevo cliente");
                    System.out.println("3. Actualizar un cliente existente");
                    System.out.println("4. Eliminar un cliente");
                    int opcionCliente = scanner.nextInt();
                    switch (opcionCliente) {
                        case 1:
                            // Mostrar lista de clientes
                            System.out.println("Mostrando lista de clientes...");
                            List<Cliente> clientesDisponibles = controlCliente.listarClientes(); // Aquí se declara la variable clientesDisponibles
                            for (Cliente cliente : clientesDisponibles) {
                                System.out.println("ID: " + cliente.getId() + ", Nombre: " + cliente.getNombre()
                                        + ", Apellido: " + cliente.getApellido() + ", DNI: " + cliente.getDni()
                                        + ", Dirección: " + cliente.getDireccion() + ", Teléfono: " + cliente.getTelefono()
                                        + ", Email: " + cliente.getEmail());
                            }
                            break;

                        case 2:
                            // Agregar un nuevo cliente
                            System.out.println("Agregando un nuevo cliente...");
                            Cliente nuevoCliente = new Cliente();
                            System.out.print("Ingrese el nombre del cliente: ");
                            nuevoCliente.setNombre(scanner.next());
                            System.out.print("Ingrese el apellido del cliente: ");
                            nuevoCliente.setApellido(scanner.next());
                            System.out.print("Ingrese el DNI del cliente: ");
                            nuevoCliente.setDni(scanner.next());
                            System.out.print("Ingrese la dirección del cliente: ");
                            nuevoCliente.setDireccion(scanner.next());
                            System.out.print("Ingrese el teléfono del cliente: ");
                            nuevoCliente.setTelefono(scanner.next());
                            System.out.print("Ingrese el correo electrónico del cliente: ");
                            nuevoCliente.setEmail(scanner.next());
                            controlCliente.guardarCliente(nuevoCliente);
                            System.out.println("Cliente agregado con éxito.");
                            break;

                        case 3:
                            // Actualizar un cliente existente
                            System.out.println("Actualizando un cliente existente...");
                            System.out.print("Ingrese el ID del cliente que desea actualizar: ");
                            Long idClienteActualizar = scanner.nextLong();
                            ResponseEntity<Cliente> responseCliente = controlCliente.buscarClientePorId(idClienteActualizar);
                            if (responseCliente.getStatusCode() == HttpStatus.OK) {
                                Cliente clienteExistente = responseCliente.getBody();
                                System.out.println("Información actual del cliente:");
                                System.out.println(clienteExistente);
                                System.out.print("Ingrese el nuevo nombre del cliente: ");
                                clienteExistente.setNombre(scanner.next());
                                System.out.print("Ingrese el nuevo apellido del cliente: ");
                                clienteExistente.setApellido(scanner.next());
                                System.out.print("Ingrese el nuevo DNI del cliente: ");
                                clienteExistente.setDni(scanner.next());
                                System.out.print("Ingrese la nueva dirección del cliente: ");
                                clienteExistente.setDireccion(scanner.next());
                                System.out.print("Ingrese el nuevo teléfono del cliente: ");
                                clienteExistente.setTelefono(scanner.next());
                                System.out.print("Ingrese el nuevo correo electrónico del cliente: ");
                                clienteExistente.setEmail(scanner.next());
                                controlCliente.actualizarCliente(idClienteActualizar, clienteExistente);
                                System.out.println("Cliente actualizado con éxito.");
                            } else {
                                System.out.println("No se encontró ningún cliente con el ID proporcionado.");
                            }
                            break;

                        case 4:
                            // Eliminar un cliente
                            System.out.println("Eliminando un cliente...");
                            System.out.print("Ingrese el ID del cliente que desea eliminar: ");
                            Long idClienteEliminar = scanner.nextLong();
                            controlCliente.eliminarCliente(idClienteEliminar);
                            System.out.println("Cliente eliminado con éxito.");
                            break;

                        default:
                            System.out.println("Opción no válida. Intente nuevamente.");
                            break;
                    }
                    break;

                case 4:
                    System.out.println("Gestionando compras...");
                    System.out.println("1. Mostrar lista de compras");
                    System.out.println("2. Eliminar una compra");
                    System.out.print("Seleccione una opción: ");
                    int opcionCompra = scanner.nextInt();

                    switch (opcionCompra) {
                        case 1:
                            // Mostrar la lista de compras realizadas
                            ResponseEntity<List<Compra>> responseCompras = controlCompra.listarCompras();

                            if (responseCompras.getStatusCode() == HttpStatus.OK) {
                                List<Compra> comprasRealizadas = responseCompras.getBody();

                                System.out.println("Lista de compras realizadas:");
                                for (Compra compra : comprasRealizadas) {
                                    System.out.println("ID: " + compra.getId() + ", Cliente: " + compra.getCliente().getNombre() +
                                                       ", Producto: " + compra.getProducto().getNombre() + ", Cantidad: " + compra.getCantidadComprada() +
                                                       ", Total: " + compra.getTotal() + ", Fecha: " + compra.getFechaCompra());
                                }
                            } else {
                                System.out.println("Error al obtener la lista de compras realizadas.");
                            }
                            break;

                        case 2:
                            // Eliminar una compra
                            System.out.print("Ingrese el ID de la compra que desea eliminar: ");
                            Long idCompraEliminar = scanner.nextLong();

                            ResponseEntity<String> responseEliminar = controlCompra.eliminarCompra(idCompraEliminar);
                            if (responseEliminar.getStatusCode() == HttpStatus.OK) {
                                System.out.println(responseEliminar.getBody());
                            } else {
                                System.out.println("Error al eliminar la compra.");
                            }
                            break;

                        default:
                            System.out.println("Opción no válida. Intente nuevamente.");
                    }
                    break;

                case 5:
                    System.out.println("Finalizando el sistema...");
                    salir = true;
                    break;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
        scanner.close();
        SpringApplication.exit(context);
    }
}
