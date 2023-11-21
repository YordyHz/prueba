package com.devsu.prueba.model.service.impl;

import com.devsu.prueba.common.exception.ClienteException;
import com.devsu.prueba.common.util.ClienteConstant;
import com.devsu.prueba.common.util.GeneralLogger;
import com.devsu.prueba.model.dto.ClienteDTO;
import com.devsu.prueba.model.dto.request.ClienteRequest;
import com.devsu.prueba.model.dto.response.ClienteResponse;
import com.devsu.prueba.model.entity.Cliente;
import com.devsu.prueba.model.mapper.ClienteMapper;
import com.devsu.prueba.model.repository.ClienteRepository;
import com.devsu.prueba.model.service.ClienteService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final GeneralLogger clienteLogger;

    @Autowired
    public ClienteServiceImpl(
            ClienteRepository clienteRepository,
            ClienteMapper clienteMapper,
            GeneralLogger clienteLogger
    ) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
        this.clienteLogger = clienteLogger;
    }

    @Override
    public ClienteResponse crearCliente(ClienteRequest clienteRequest) {
        Cliente cliente = clienteMapper.toEntity(clienteRequest);
        cliente.setInitialValues();
        cliente = clienteRepository.save(cliente);
        clienteLogger.logInfo("Cliente creado exitosamente.");
        return clienteMapper.toResponse(cliente);
    }

    @Override
    public ClienteResponse actualizarCliente(Long clienteId, ClienteRequest clienteRequest) {
        Cliente clienteExistente = clienteRepository.findByIdAndEstadoTrue(clienteId)
                .orElseThrow(() -> new ClienteException(
                HttpStatus.NOT_FOUND, //404 
                String.format(ClienteConstant.CLIENTE_NO_EXISTE_MESSAGE_ERROR,
                        clienteRequest.getIdentificacion(),
                        clienteRequest.getNombre())
        ));

        Cliente clienteActualizado = clienteMapper.toEntity(clienteRequest);
        clienteActualizado.setId(clienteId); // Asignar el ID al cliente actualizado
        clienteExistente = clienteRepository.save(clienteActualizado);
        clienteLogger.logInfo("Cliente actualizado exitosamente.");
        return clienteMapper.toResponse(clienteExistente);
    }

    @Override
    public ClienteResponse actualizarClienteParcial(Long clienteId, ClienteRequest clienteRequest) {
        Cliente clienteExistente = clienteRepository.findByIdAndEstadoTrue(clienteId)
                .orElseThrow(() -> new ClienteException(
                HttpStatus.NOT_FOUND, //404 
                String.format(ClienteConstant.CLIENTE_NO_ENCONTRADO_MESSAGE_ERROR,
                        clienteId)
        ));

        if (clienteRequest.getNombre() != null) {
            clienteExistente.setNombre(clienteRequest.getNombre());
        }
        if (clienteRequest.getGenero() != null) {
            clienteExistente.setGenero(clienteRequest.getGenero().name());
        }
        if (clienteRequest.getEdad() != 0) {
            clienteExistente.setEdad(clienteRequest.getEdad());
        }
        if (clienteRequest.getIdentificacion() != null) {
            clienteExistente.setIdentificacion(clienteRequest.getIdentificacion());
        }
        if (clienteRequest.getDireccion() != null) {
            clienteExistente.setDireccion(clienteRequest.getDireccion());
        }
        if (clienteRequest.getTelefono() != null) {
            clienteExistente.setTelefono(clienteRequest.getTelefono());
        }
        if (clienteRequest.getContraseña() != null) {
            clienteExistente.setContraseña(clienteRequest.getContraseña());
        }

        clienteRepository.save(clienteExistente);
        clienteLogger.logInfo("Cliente actualizado exitosamente.");
        return clienteMapper.toResponse(clienteExistente);
    }

    @Override
    public void eliminarCliente(Long clienteId) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(clienteId);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            cliente.setEstado(false);  //No se elimina fisicamente
            clienteRepository.save(cliente);
            clienteLogger.logInfo("Cliente eliminado exitosamente.");
        } else {
            throw new ClienteException(HttpStatus.NOT_FOUND,
                    String.format(ClienteConstant.CLIENTE_NO_ELIMINADO_MESSAGE_ERROR,
                            clienteId));
        }
    }

    @Override
    public ClienteDTO obtenerClientePorId(Long clienteId) {
        Optional<Cliente> clienteOptional = clienteRepository.findByIdAndEstadoTrue(clienteId);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            return clienteMapper.toDTO(cliente);
        } else {
            throw new ClienteException(HttpStatus.NOT_FOUND,
                    String.format(ClienteConstant.CLIENTE_NO_ENCONTRADO_MESSAGE_ERROR,
                            clienteId));
        }
    }

    @Override
    public Iterable<ClienteDTO> obtenerTodosLosClientes() {
        Iterable<Cliente> clientes = clienteRepository.findByEstadoTrue();
        List<ClienteDTO> clienteResponses = new ArrayList<>();

        for (Cliente cliente : clientes) {
            clienteResponses.add(clienteMapper.toDTO(cliente));
        }

        return clienteResponses;
    }
}
