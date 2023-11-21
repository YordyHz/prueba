package com.devsu.prueba.model.service;

import com.devsu.prueba.model.dto.request.MovimientosRequest;
import com.devsu.prueba.model.dto.response.MovimientosResponse;
import java.time.LocalDate;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
public interface MovimientosService {

    MovimientosResponse registrarMovimiento(MovimientosRequest movimientosRequest);

    MovimientosResponse actualizarMovimiento(Long id, MovimientosRequest movimientosRequest);

    void eliminarMovimiento(Long id);

    Iterable<MovimientosResponse> obtenerMovimientosPorCuenta(Long idCuenta);

    Iterable<MovimientosResponse> obtenerMovimientosPorCliente(Long idCliente);

    Iterable<MovimientosResponse> obtenerMovimientosPorClienteYFecha(Long idCliente, LocalDate fechaInicio, LocalDate fechaFin);

}
