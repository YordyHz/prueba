package com.devsu.prueba.model.service;

import com.devsu.prueba.model.dto.ClienteDTO;
import com.devsu.prueba.model.dto.request.ClienteRequest;
import com.devsu.prueba.model.dto.response.ClienteResponse;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
public interface ClienteService {

    ClienteResponse crearCliente(ClienteRequest clienteRequest);

    ClienteResponse actualizarCliente(Long clienteId, ClienteRequest clienteRequest);

    ClienteResponse actualizarClienteParcial(Long clienteId, ClienteRequest clienteRequest);

    void eliminarCliente(Long clienteId);

    ClienteDTO obtenerClientePorId(Long clienteId);

    Iterable<ClienteDTO> obtenerTodosLosClientes();
}
