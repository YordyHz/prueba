package com.devsu.prueba.model.service;

import com.devsu.prueba.model.dto.CuentaDTO;
import com.devsu.prueba.model.dto.request.CuentaRequest;
import com.devsu.prueba.model.dto.response.CuentaResponse;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
public interface CuentaService {

    CuentaResponse crearCuenta(CuentaRequest cuentaRequest);

    CuentaResponse actualizarCuenta(Long id, CuentaRequest cuentaRequest);

    CuentaResponse actualizarCuentaParcial(Long id, CuentaRequest cuentaRequest);

    void eliminarCuenta(Long id);

    CuentaDTO obtenerCuentaPorId(Long id);

    Iterable<CuentaResponse> obtenerTodasLasCuentas();
}
