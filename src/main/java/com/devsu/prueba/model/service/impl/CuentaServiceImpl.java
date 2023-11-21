package com.devsu.prueba.model.service.impl;

import com.devsu.prueba.common.exception.CuentaException;
import com.devsu.prueba.common.util.ClienteConstant;
import com.devsu.prueba.common.util.CuentaConstant;
import com.devsu.prueba.common.util.GeneralLogger;
import com.devsu.prueba.model.dto.CuentaDTO;
import com.devsu.prueba.model.dto.request.CuentaRequest;
import com.devsu.prueba.model.dto.response.CuentaResponse;
import com.devsu.prueba.model.entity.Cliente;
import com.devsu.prueba.model.entity.Cuenta;
import com.devsu.prueba.model.mapper.CuentaMapper;
import com.devsu.prueba.model.repository.ClienteRepository;
import com.devsu.prueba.model.repository.CuentaRepository;
import com.devsu.prueba.model.service.CuentaService;
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
public class CuentaServiceImpl implements CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CuentaMapper cuentaMapper;

    @Autowired
    private GeneralLogger generalLogger;

    @Override
    public CuentaResponse crearCuenta(CuentaRequest cuentaRequest) {
        Long clienteId = cuentaRequest.getClienteId();
        Cliente cliente = clienteRepository.findByIdAndEstadoTrue(clienteId)
                .orElseThrow(() -> new CuentaException(
                HttpStatus.NOT_FOUND, //404 
                String.format(ClienteConstant.CLIENTE_NO_ENCONTRADO_MESSAGE_ERROR,
                        clienteId)
        ));

        Cuenta cuenta = new Cuenta();
        cuenta.setInitialValues();
        cuenta.setNumeroCuenta(cuentaRequest.getNumeroCuenta());
        cuenta.setTipoCuenta(cuentaRequest.getTipoCuenta().name());
        cuenta.setSaldoInicial(cuentaRequest.getSaldoInicial());

        cuenta.setCliente(cliente);

        cuenta = cuentaRepository.save(cuenta);

        generalLogger.logInfo("Cuenta creada exitosamente.");
        return cuentaMapper.toResponse(cuenta);
    }

    @Override
    public CuentaResponse actualizarCuenta(Long idCuenta, CuentaRequest cuentaRequest) {
        Cuenta cuenta = cuentaRepository.findById(idCuenta)
                .orElseThrow(() -> new CuentaException(
                HttpStatus.NOT_FOUND, //404 
                String.format(CuentaConstant.CUENTA_NO_EXISTE_MESSAGE_ERROR,
                        cuentaRequest.getNumeroCuenta(),
                        cuentaRequest.getTipoCuenta().getDescripcion())
        ));

        Long clienteId = cuentaRequest.getClienteId();
        Cliente cliente = clienteRepository.findByIdAndEstadoTrue(clienteId)
                .orElseThrow(() -> new CuentaException(
                HttpStatus.NOT_FOUND, //404 
                String.format(ClienteConstant.CLIENTE_NO_ENCONTRADO_MESSAGE_ERROR,
                        clienteId)
        ));

        cuenta.setNumeroCuenta(cuentaRequest.getNumeroCuenta());
        cuenta.setTipoCuenta(cuentaRequest.getTipoCuenta().name());
        cuenta.setSaldoInicial(cuentaRequest.getSaldoInicial());
        cuenta.setLimiteDiario(cuentaRequest.getLimiteDiario());
        cuenta.setEstado(cuentaRequest.isEstado());
        cuenta.setCliente(cliente);

        cuenta = cuentaRepository.save(cuenta);

        generalLogger.logInfo("Cuenta actualizada exitosamente.");
        return cuentaMapper.toResponse(cuenta);
    }

    @Override
    public CuentaResponse actualizarCuentaParcial(Long idCuenta, CuentaRequest cuentaRequest) {
        Cuenta cuenta = cuentaRepository.findById(idCuenta)
                .orElseThrow(() -> new CuentaException(
                HttpStatus.NOT_FOUND, //404 
                String.format(CuentaConstant.CUENTA_NO_EXISTE_MESSAGE_ERROR,
                        cuentaRequest.getNumeroCuenta(),
                        cuentaRequest.getTipoCuenta().getDescripcion())
        ));

        Long clienteId = cuentaRequest.getClienteId();
        if (clienteId != null) {
            Cliente cliente = clienteRepository.findByIdAndEstadoTrue(clienteId)
                    .orElseThrow(() -> new CuentaException(
                    HttpStatus.NOT_FOUND, //404 
                    String.format(ClienteConstant.CLIENTE_NO_ENCONTRADO_MESSAGE_ERROR,
                            clienteId)
            ));
            cuenta.setCliente(cliente);
        }

        if (cuentaRequest.getNumeroCuenta() != null) {
            cuenta.setNumeroCuenta(cuentaRequest.getNumeroCuenta());
        }
        if (cuentaRequest.getTipoCuenta() != null) {
            cuenta.setTipoCuenta(cuentaRequest.getTipoCuenta().name());
        }
        if (cuentaRequest.getSaldoInicial() != null) {
            cuenta.setSaldoInicial(cuentaRequest.getSaldoInicial());
        }
        if (cuentaRequest.getLimiteDiario() != null) {
            cuenta.setLimiteDiario(cuentaRequest.getLimiteDiario());
        }

        cuenta = cuentaRepository.save(cuenta);

        generalLogger.logInfo("Cuenta actualizada exitosamente.");
        return cuentaMapper.toResponse(cuenta);
    }

    @Override
    public void eliminarCuenta(Long cuentaId) {
        Optional<Cuenta> cuentaOptional = cuentaRepository.findById(cuentaId);
        if (cuentaOptional.isPresent()) {
            Cuenta cuenta = cuentaOptional.get();
            cuenta.setEstado(false);  //No se elimina fisicamente
            cuentaRepository.save(cuenta);
            generalLogger.logInfo("Cuenta eliminada exitosamente.");
        } else {
            throw new CuentaException(HttpStatus.NOT_FOUND,
                    String.format(CuentaConstant.CUENTA_NO_ELIMINADA_MESSAGE_ERROR,
                            cuentaId));
        }
    }

    @Override
    public CuentaDTO obtenerCuentaPorId(Long cuentaId) {
        Optional<Cuenta> cuentaOptional = cuentaRepository.findById(cuentaId);
        if (cuentaOptional.isPresent()) {
            Cuenta cuenta = cuentaOptional.get();
            return cuentaMapper.toDTO(cuenta);
        } else {
            throw new CuentaException(HttpStatus.NOT_FOUND,
                    String.format(CuentaConstant.CUENTA_NO_ENCONTRADA_MESSAGE_ERROR,
                            cuentaId));
        }
    }

    @Override
    public Iterable<CuentaResponse> obtenerTodasLasCuentas() {
        Iterable<Cuenta> cuentas = cuentaRepository.findAll();
        List<CuentaResponse> cuentaResponses = new ArrayList<>();

        for (Cuenta cuenta : cuentas) {
            cuentaResponses.add(cuentaMapper.toResponse(cuenta));
        }

        return cuentaResponses;
    }
}
