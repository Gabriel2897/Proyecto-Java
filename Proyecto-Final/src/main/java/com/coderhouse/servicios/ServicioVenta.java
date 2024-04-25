package com.coderhouse.servicios;

import com.coderhouse.entidades.Cliente;
import com.coderhouse.entidades.ItemVenta;
import com.coderhouse.entidades.Producto;
import com.coderhouse.entidades.Venta;
import com.coderhouse.repositorios.RepoCliente;
import com.coderhouse.repositorios.RepoProducto;
import com.coderhouse.repositorios.RepoVenta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class ServicioVenta {

    @Autowired
    private RepoVenta ventaRepository;

    @Autowired
    private RepoCliente clienteRepository;

    @Autowired
    private RepoProducto productoRepository;

    public ResponseEntity<Object> ejecutarVenta(Map<String, Object> requestBody) {
        try {
            // Extraer cliente del requestBody
            if (requestBody.get("cliente") instanceof Map) {
                Map<String, Object> clienteMap = (Map<String, Object>) requestBody.get("cliente");
                Cliente cliente = clienteRepository.findById(Long.parseLong(clienteMap.get("clienteid").toString())).orElse(null);

                // Validar cliente existente
                if (cliente == null) {
                    return ResponseEntity.badRequest().body("Cliente no encontrado");
                }

                // Extraer líneas de productos del requestBody
                if (requestBody.get("lineas") instanceof List) {
                    List<Map<String, Object>> lineas = (List<Map<String, Object>>) requestBody.get("lineas");

                    // Validar productos existentes y calcular total
                    double totalVenta = 0;
                    List<ItemVenta> items = new ArrayList<>();
                    for (Map<String, Object> linea : lineas) {
                        Long productoId = Long.parseLong(linea.get("productoid").toString());
                        int cantidad = Integer.parseInt(linea.get("cantidad").toString());

                        Optional<Producto> productoOptional = productoRepository.findById(productoId);
                        if (productoOptional.isEmpty()) {
                            return ResponseEntity.badRequest().body("Producto no encontrado: " + productoId);
                        }
                        Producto producto = productoOptional.get();
                        // Validar cantidad de productos y reducir stock
                        if (cantidad > producto.getCantidad()) {
                            return ResponseEntity.badRequest().body("Stock insuficiente para el producto: " + producto.getProducto());
                        }
                        totalVenta += cantidad * producto.getPrecio();
                        producto.setCantidad(producto.getCantidad() - cantidad);
                        productoRepository.save(producto);

                        // Crear y agregar itemVenta
                        ItemVenta itemVenta = new ItemVenta();
                        itemVenta.setProducto(producto);
                        itemVenta.setCantidad(cantidad);
                        itemVenta.setPrecioUnitario(producto.getPrecio());
                        itemVenta.setTotal(cantidad * producto.getPrecio());
                        items.add(itemVenta);
                    }

                    // Obtener la fecha del servicio REST http://worldclockapi.com/api/json/utc/now
                    Date fecha = obtenerFechaDesdeServicio();
                    if (fecha == null) {
                        // En caso de error, usar la fecha actual
                        fecha = new Date();
                    }

                    // Crear y guardar la venta
                    Venta venta = new Venta();
                    venta.setCliente(cliente);
                    venta.setItems(items);
                    venta.setFecha(fecha);
                    venta.setTotalVenta(totalVenta);
                    ventaRepository.save(venta);

                    return ResponseEntity.ok().body("Venta realizada exitosamente");
                } else {
                    return ResponseEntity.badRequest().body("Error en el formato de las líneas de productos");
                }
            } else {
                return ResponseEntity.badRequest().body("Error en el formato del cliente");
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Error en el formato de los datos proporcionados");
        }
    }


    public List<Venta> listarTodasLasVentas() {
        return ventaRepository.findAll();
    }

    public Optional<Venta> buscarVentaPorId(Long id) {
        return ventaRepository.findById(id);
    }

    public ResponseEntity<Object> eliminarVenta(Long id) {
        Optional<Venta> ventaOptional = ventaRepository.findById(id);
        if (ventaOptional.isPresent()) {
            ventaRepository.deleteById(id);
            return ResponseEntity.ok().body("Venta eliminada exitosamente");
        } else {
            return ResponseEntity.badRequest().body("No se encontró la venta con el ID proporcionado: " + id);
        }
    }

    private Date obtenerFechaDesdeServicio() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange("http://worldclockapi.com/api/json/utc/now", HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, Object>>() {});
            if (response.getStatusCode().is2xxSuccessful()) {
                Map<String, Object> body = response.getBody();
                String currentDateTimeString = (String) body.get("currentDateTime");
                // Formato esperado de la cadena de fecha: "YYYY-MM-DDTHH:MM:SSZ"
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                Date fecha = dateFormat.parse(currentDateTimeString);
                return fecha;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
