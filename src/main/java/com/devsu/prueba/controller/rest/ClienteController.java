package com.devsu.prueba.controller.rest;

import com.devsu.prueba.common.util.GeneralLogger;
import com.devsu.prueba.model.dto.ClienteDTO;
import com.devsu.prueba.model.dto.request.ClienteRequest;
import com.devsu.prueba.model.dto.response.ClienteResponse;
import com.devsu.prueba.model.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@RestController
@RequestMapping("clientes")
@CrossOrigin(origins = "http://localhost:4200")
public class ClienteController {

    private final ClienteService clienteService;
    private final GeneralLogger clienteLogger;

    @Autowired
    public ClienteController(ClienteService clienteService, GeneralLogger clienteLogger) {
        this.clienteService = clienteService;
        this.clienteLogger = clienteLogger;
    }

    @GetMapping
    public Iterable<ClienteDTO> obtenerTodosLosClientes() {
        clienteLogger.logInfo("Consultando listado de todos los clientes");
        return clienteService.obtenerTodosLosClientes();
    }

    @GetMapping("/{id}")
    public ClienteDTO obtenerClientePorId(@PathVariable Long id) {
        clienteLogger.logInfo("Consultando cliente con ID: " + id);
        return clienteService.obtenerClientePorId(id);
    }

    @PostMapping
    public ClienteResponse crearCliente(@RequestBody ClienteRequest clienteRequest) {
        clienteLogger.logInfo("Creando nuevo cliente");
        return clienteService.crearCliente(clienteRequest);
    }

    @PutMapping("/{id}")
    public ClienteResponse actualizarCliente(@PathVariable Long id, @RequestBody ClienteRequest request) {
        clienteLogger.logInfo("Actualizando cliente con ID: " + id);
        return clienteService.actualizarCliente(id, request);
    }

    @PatchMapping("/{id}")
    public ClienteResponse actualizarClienteParcial(@PathVariable Long id, @RequestBody ClienteRequest request) {
        clienteLogger.logInfo("Actualizando parcialmente cliente con ID: " + id);
        return clienteService.actualizarClienteParcial(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminarCliente(@PathVariable Long id) {
        clienteLogger.logInfo("Eliminando cliente con ID: " + id);
        clienteService.eliminarCliente(id);
    }
}
