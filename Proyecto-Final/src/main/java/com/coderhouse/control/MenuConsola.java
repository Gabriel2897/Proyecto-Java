package com.coderhouse.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.coderhouse.entidades.Cliente;
import com.coderhouse.entidades.ItemVenta;
import com.coderhouse.entidades.Producto;
import com.coderhouse.entidades.Venta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class MenuConsola implements CommandLineRunner {

	@Autowired
	private ControlCliente controlCliente;

	@Autowired
	private ControlProducto controlProducto;

	@Autowired
	private ControlVenta controlVenta;

	private Scanner scanner;

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		int opcion;

		do {
			System.out.println("Menú:");
			System.out.println("1. Gestionar Clientes");
			System.out.println("2. Gestionar Productos");
			System.out.println("3. Realizar una Venta");
			System.out.println("4. Gestionar Ventas");
			System.out.println("0. Salir");
			System.out.print("Ingrese su opción: ");
			opcion = scanner.nextInt();

			switch (opcion) {
			case 1:
				gestionarClientes();
				break;
			case 2:
				gestionarProductos();
				break;
			case 3:
				realizarVenta();
				break;
			case 4:
				gestionarVentas();
				break;
			case 0:
				System.out.println("Saliendo del menú...");
				break;
			default:
				System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
			}
		} while (opcion != 0);
	}

	private void gestionarClientes() {
		Scanner scanner = new Scanner(System.in);
		int opcion;

		do {
			System.out.println("\nMenú de Gestión de Clientes:");
			System.out.println("1. Ver todos los clientes");
			System.out.println("2. Agregar nuevo cliente");
			System.out.println("3. Actualizar cliente");
			System.out.println("4. Eliminar cliente");
			System.out.println("0. Volver al menú principal");
			System.out.print("Ingrese su opción: ");
			opcion = scanner.nextInt();

			switch (opcion) {
			case 1:
				verTodosClientes();
				break;
			case 2:
				agregarNuevoCliente();
				break;
			case 3:
				actualizarCliente();
				break;
			case 4:
				eliminarCliente();
				break;
			case 0:
				System.out.println("Volviendo al menú principal...");
				break;
			default:
				System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
			}
		} while (opcion != 0);
	}

	private void verTodosClientes() {
		// Llama al método del controlador para obtener todos los clientes
		ResponseEntity<List<Cliente>> responseEntity = controlCliente.obtenerTodosClientes();

		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			// Si la respuesta es exitosa, obtén la lista de clientes del cuerpo de la
			// respuesta
			List<Cliente> clientes = responseEntity.getBody();
			if (clientes.isEmpty()) {
				System.out.println("No hay clientes registrados.");
			} else {
				System.out.println("Lista de Clientes:");
				for (Cliente cliente : clientes) {
					System.out.println(cliente);
				}
			}
		} else {
			// Si no se puede obtener la lista de clientes, muestra un mensaje de error
			// adecuado
			System.out.println("Error al obtener la lista de clientes.");
		}
	}

	private void agregarNuevoCliente() {
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

		// Crear una nueva instancia de Cliente y establecer los valores
		Cliente nuevoCliente = new Cliente();
		nuevoCliente.setNombre(nombre);
		nuevoCliente.setApellido(apellido);
		nuevoCliente.setDni(dni);
		nuevoCliente.setEmail(email);
		nuevoCliente.setDireccion(direccion);
		nuevoCliente.setTelefono(telefono);

		// Guardar el nuevo cliente utilizando el servicio
		ResponseEntity<Cliente> response = controlCliente.guardarCliente(nuevoCliente);

		// Verificar si la solicitud fue exitosa
		if (response.getStatusCode() == HttpStatus.CREATED) {
			Cliente clienteGuardado = response.getBody();
			System.out.println("Nuevo cliente agregado con ID: " + clienteGuardado.getId());
		} else {
			// Manejar el caso en que la solicitud no fue exitosa
			System.out.println("Error al agregar el cliente: " + response.getStatusCode());
		}
	}

	private void actualizarCliente() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Ingrese el ID del cliente a actualizar: ");
		Long id = scanner.nextLong();
		scanner.nextLine(); // Limpiar el buffer del scanner

		// Obtener el cliente del servicio utilizando su ID
		ResponseEntity<Cliente> response = controlCliente.obtenerClientePorId(id);

		// Verificar si el cliente fue encontrado
		if (response.getStatusCode() == HttpStatus.OK) {
			Cliente cliente = response.getBody();

			// Solicitar al usuario que ingrese los nuevos datos del cliente
			System.out.println("Ingrese los nuevos datos del cliente:");

			System.out.print("Nombre: ");
			String nombre = scanner.nextLine();

			System.out.print("Apellido: ");
			String apellido = scanner.nextLine();

			System.out.print("DNI: ");
			String dni = scanner.nextLine();

			System.out.print("Email: ");
			String email = scanner.nextLine();

			System.out.print("Dirección: ");
			String direccion = scanner.nextLine();

			System.out.print("Teléfono: ");
			String telefono = scanner.nextLine();

			// Actualizar el cliente con los nuevos datos
			cliente.setNombre(nombre);
			cliente.setApellido(apellido);
			cliente.setDni(dni);
			cliente.setEmail(email);
			cliente.setDireccion(direccion);
			cliente.setTelefono(telefono);

			// Actualizar el cliente utilizando el servicio
			ResponseEntity<Cliente> responseActualizacion = controlCliente.actualizarCliente(id, cliente);

			// Verificar si la solicitud de actualización fue exitosa
			if (responseActualizacion.getStatusCode() == HttpStatus.OK) {
				System.out.println("Cliente actualizado correctamente.");
			} else {
				// Manejar el caso en que la solicitud no fue exitosa
				System.out.println("Error al actualizar el cliente: " + responseActualizacion.getStatusCode());
			}
		} else {
			System.out.println("No se encontró ningún cliente con el ID proporcionado.");
		}
	}

	private void eliminarCliente() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Ingrese el ID del cliente a eliminar: ");
		Long id = scanner.nextLong();
		controlCliente.eliminarCliente(id);
		System.out.println("Cliente eliminado correctamente.");
	}

	private void gestionarProductos() {
		Scanner scanner = new Scanner(System.in);
		int opcion;

		do {
			System.out.println("\nMenú de Gestión de Productos:");
			System.out.println("1. Ver todos los productos");
			System.out.println("2. Agregar nuevo producto");
			System.out.println("3. Actualizar producto");
			System.out.println("4. Eliminar producto");
			System.out.println("0. Volver al menú principal");
			System.out.print("Ingrese su opción: ");
			opcion = scanner.nextInt();

			switch (opcion) {
			case 1:
				verTodosProductos();
				break;
			case 2:
				agregarNuevoProducto();
				break;
			case 3:
				actualizarProducto();
				break;
			case 4:
				eliminarProducto();
				break;
			case 0:
				System.out.println("Volviendo al menú principal...");
				break;
			default:
				System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
			}
		} while (opcion != 0);
	}

	private void verTodosProductos() {
		// Obtener la lista de todos los productos utilizando el servicio
		ResponseEntity<List<Producto>> responseEntity = controlProducto.obtenerTodosProductos();

		// Verificar si la respuesta fue exitosa
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			// Obtener la lista de productos del cuerpo de la respuesta
			List<Producto> productos = responseEntity.getBody();

			// Verificar si la lista está vacía
			if (productos.isEmpty()) {
				System.out.println("No hay productos registrados.");
			} else {
				// Mostrar la lista de productos
				System.out.println("Lista de Productos:");
				for (Producto producto : productos) {
					System.out.println("ID: " + producto.getId());
					System.out.println("Nombre: " + producto.getProducto());
					System.out.println("Precio: " + producto.getPrecio());
					System.out.println("Cantidad: " + producto.getCantidad());
					System.out.println("-------------------------------------");
				}
			}
		} else {
			// Mostrar un mensaje de error si no se puede obtener la lista de productos
			System.out.println("Error al obtener la lista de productos.");
		}
	}

	private void agregarNuevoProducto() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Ingrese el nombre del producto: ");
		String nombre = scanner.nextLine();
		System.out.print("Ingrese el precio del producto: ");
		double precio = scanner.nextDouble();
		System.out.print("Ingrese la cantidad del producto: ");
		int cantidad = scanner.nextInt();

		// Crear una nueva instancia de Producto y establecer los valores
		Producto nuevoProducto = new Producto();
		nuevoProducto.setProducto(nombre);
		nuevoProducto.setPrecio(precio);
		nuevoProducto.setCantidad(cantidad);

		// Guardar el nuevo producto utilizando el servicio
		ResponseEntity<Producto> response = controlProducto.guardarProducto(nuevoProducto);

		// Verificar si la solicitud fue exitosa
		if (response.getStatusCode() == HttpStatus.CREATED) {
			Producto productoGuardado = response.getBody();
			System.out.println("Nuevo producto agregado con ID: " + productoGuardado.getId());
		} else {
			// Manejar el caso en que la solicitud no fue exitosa
			System.out.println("Error al agregar el producto: " + response.getStatusCode());
		}
	}

	private void actualizarProducto() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Ingrese el ID del producto a actualizar: ");
		Long id = scanner.nextLong();
		scanner.nextLine(); // Limpiar el buffer del scanner

		// Obtener el producto del servicio utilizando su ID
		ResponseEntity<Producto> response = controlProducto.obtenerProductoPorId(id);

		// Verificar si el producto fue encontrado
		if (response.getStatusCode() == HttpStatus.OK) {
			Producto producto = response.getBody();

			// Solicitar al usuario que ingrese los nuevos datos del producto
			System.out.println("Ingrese los nuevos datos del producto:");

			System.out.print("Nombre: ");
			String nombre = scanner.nextLine();

			System.out.print("Precio: ");
			double precio = scanner.nextDouble();

			System.out.print("Cantidad: ");
			int cantidad = scanner.nextInt();

			// Actualizar el producto con los nuevos datos
			producto.setProducto(nombre);
			producto.setPrecio(precio);
			producto.setCantidad(cantidad);

			// Actualizar el producto utilizando el servicio
			ResponseEntity<Producto> responseActualizacion = controlProducto.actualizarProducto(id, producto);

			// Verificar si la solicitud de actualización fue exitosa
			if (responseActualizacion.getStatusCode() == HttpStatus.OK) {
				System.out.println("Producto actualizado correctamente.");
			} else {
				// Manejar el caso en que la solicitud no fue exitosa
				System.out.println("Error al actualizar el producto: " + responseActualizacion.getStatusCode());
			}
		} else {
			System.out.println("No se encontró ningún producto con el ID proporcionado.");
		}
	}

	private void eliminarProducto() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Ingrese el ID del producto a eliminar: ");
		Long id = scanner.nextLong();
		controlProducto.eliminarProducto(id);
		System.out.println("Producto eliminado correctamente.");
	}

	public void mostrarClientes() {
	    // Llama al método obtenerTodosClientes() del ControlCliente para obtener la lista de clientes
	    ResponseEntity<List<Cliente>> response = controlCliente.obtenerTodosClientes();
	    
	    if (response.getStatusCode() == HttpStatus.OK) {
	        List<Cliente> clientes = response.getBody();
	        for (Cliente cliente : clientes) {
	            System.out.println(cliente.getId() + ": " + cliente.getNombre() + " " + cliente.getApellido());
	        }
	    } else {
	        System.out.println("No se pudo obtener la lista de clientes.");
	    }
	}

	public void realizarVenta() {
	    Scanner scanner = new Scanner(System.in);
	    System.out.println("Realizar una venta:");

	    // Mostrar la lista de clientes disponibles
	    System.out.println("\nLista de Clientes:");
	    mostrarClientes();
	    System.out.print("Seleccione el ID del cliente: ");
	    Long clienteId = scanner.nextLong();
	    scanner.nextLine(); // Consumir el salto de línea

	    // Obtener el cliente seleccionado utilizando el servicio
	    ResponseEntity<Cliente> responseCliente = controlCliente.obtenerClientePorId(clienteId);
	    if (responseCliente.getStatusCode() == HttpStatus.OK) {
	        Cliente cliente = responseCliente.getBody();

	        // Mostrar la lista de productos disponibles
	        System.out.println("\nLista de Productos:");
	        verTodosProductos();

	        // Inicializar variables para la venta
	        List<ItemVenta> items = new ArrayList<>();
	        double totalVenta = 0;

	        // Proceso de selección de productos
	        boolean agregarMasProductos = true;
	        while (agregarMasProductos) {
	            System.out.print("\nSeleccione el ID del producto: ");
	            Long productoId = scanner.nextLong();
	            scanner.nextLine(); // Consumir el salto de línea

	            System.out.print("Ingrese la cantidad: ");
	            int cantidad = scanner.nextInt();
	            scanner.nextLine(); // Consumir el salto de línea

	            // Obtener el producto seleccionado utilizando el servicio
	            ResponseEntity<Producto> responseProducto = controlProducto.obtenerProductoPorId(productoId);
	            if (responseProducto.getStatusCode() == HttpStatus.OK) {
	                Producto producto = responseProducto.getBody();
	                // Verificar si hay suficiente cantidad de producto disponible
	                if (cantidad > producto.getCantidad()) {
	                    System.out.println("No hay suficiente cantidad disponible para este producto.");
	                } else {
	                    // Calcular el subtotal y agregar el item a la lista de items
	                    double subtotal = cantidad * producto.getPrecio();
	                    totalVenta += subtotal;
	                    ItemVenta itemVenta = new ItemVenta(producto, cantidad, subtotal);
	                    items.add(itemVenta);
	                }
	            } else {
	                System.out.println("El producto seleccionado no existe.");
	            }

	            // Preguntar al usuario si desea agregar más productos
	            System.out.print("¿Desea agregar otro producto? (S/N): ");
	            String respuesta = scanner.nextLine().trim().toLowerCase();
	            agregarMasProductos = respuesta.equals("s");
	        }

	        // Mostrar el resumen de la venta
	        System.out.println("\nResumen de la Venta:");
	        System.out.println("Cliente: " + cliente.getNombre() + " " + cliente.getApellido());
	        for (ItemVenta item : items) {
	            System.out.println("Producto: " + item.getProducto().getProducto());
	            System.out.println("Cantidad: " + item.getCantidad());
	            System.out.println("Subtotal: " + item.getTotal());
	        }
	        System.out.println("Total: " + totalVenta);

	        // Construir el cuerpo de la solicitud
	        Map<String, Object> requestBody = new HashMap<>();
	        Map<String, Object> clienteMap = new HashMap<>();
	        clienteMap.put("clienteid", cliente.getId());
	        requestBody.put("cliente", clienteMap);
	        
	        List<Map<String, Object>> lineas = new ArrayList<>();
	        for (ItemVenta item : items) {
	            Map<String, Object> linea = new HashMap<>();
	            linea.put("productoid", item.getProducto().getId());
	            linea.put("cantidad", item.getCantidad());
	            lineas.add(linea);
	        }
	        requestBody.put("lineas", lineas);

	        // Guardar la venta y los items de venta en la base de datos utilizando el servicio
	        ResponseEntity<Object> responseVenta = controlVenta.ejecutarVenta(requestBody);
	        if (responseVenta.getStatusCode() == HttpStatus.OK) {
	            System.out.println("Venta realizada exitosamente.");
	        } else {
	            System.out.println("Error al realizar la venta: " + responseVenta.getBody());
	        }
	    } else {
	        System.out.println("El cliente seleccionado no existe.");
	    }
	}

	private void gestionarVentas() {
		Scanner scanner = new Scanner(System.in);
		int opcion;

		do {
			System.out.println("Gestión de Ventas");
			System.out.println("1. Mostrar todas las ventas");
			System.out.println("2. Eliminar una venta");
			System.out.println("3. Volver al menú principal");
			System.out.print("Ingrese su opción: ");
			opcion = scanner.nextInt();

			switch (opcion) {
			case 1:
				verTodasLasVentas();
				break;
			case 2:
				eliminarVenta();
				break;
			case 3:
				System.out.println("Volviendo al menú principal...");
				break;
			default:
				System.out.println("Opción inválida. Por favor, ingrese una opción válida.");
			}
		} while (opcion != 3);
	}

	public void verTodasLasVentas() {
	    // Llamar al método del controlador para obtener la lista de ventas
	    List<Venta> ventas = controlVenta.listarVentas();

	    // Verificar si hay ventas disponibles
	    if (ventas != null && !ventas.isEmpty()) {
	        System.out.println("Listado de Ventas:");
	        for (Venta venta : ventas) {
	            System.out.println("ID: " + venta.getId());
	            System.out.println("Fecha: " + venta.getFecha());
	            System.out.println("Cliente: " + venta.getCliente().getNombre() + " " + venta.getCliente().getApellido());
	            System.out.println("Total: " + venta.getTotalVenta());
	            System.out.println("--------------------------------------");
	        }
	    } else {
	        System.out.println("No hay ventas disponibles.");
	    }
	}

	public void eliminarVenta() {
        System.out.println("Eliminar una venta:");

        // Solicitar al usuario que ingrese el ID de la venta a eliminar
        System.out.print("Ingrese el ID de la venta a eliminar: ");
        Long ventaId = scanner.nextLong();
        scanner.nextLine(); // Consumir el salto de línea

        // Llamar al método del controlador para eliminar la venta
        ResponseEntity<Object> responseEntity = controlVenta.eliminarVenta(ventaId);

        // Verificar si la solicitud fue exitosa (código de estado 200)
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            System.out.println("Venta eliminada exitosamente.");
        } else {
            System.out.println("Error al eliminar la venta. Detalles: " + responseEntity.getBody());
        }
    }
}