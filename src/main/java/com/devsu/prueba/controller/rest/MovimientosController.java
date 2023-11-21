package com.devsu.prueba.controller.rest;

import com.devsu.prueba.common.util.GeneralLogger;
import com.devsu.prueba.model.dto.request.MovimientosRequest;
import com.devsu.prueba.model.dto.response.MovimientosResponse;
import com.devsu.prueba.model.service.MovimientosService;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@RestController
@RequestMapping("movimientos")
@CrossOrigin(origins = "http://localhost:4200")
public class MovimientosController {

    private final MovimientosService movimientosService;
    private final GeneralLogger movimientosLogger;

    @Autowired
    public MovimientosController(MovimientosService movimientosService, GeneralLogger movimientosLogger) {
        this.movimientosService = movimientosService;
        this.movimientosLogger = movimientosLogger;
    }

    @GetMapping("/cuenta/{id}")
    public Iterable<MovimientosResponse> obtenerMovimientosPorCuenta(@PathVariable Long id) {
        movimientosLogger.logInfo("Consultando movimientos con ID de cuenta: " + id);
        return movimientosService.obtenerMovimientosPorCuenta(id);
    }
    
    @GetMapping("/cliente/{id}")
    public Iterable<MovimientosResponse> obtenerMovimientosPorCliente(@PathVariable Long id) {
        movimientosLogger.logInfo("Consultando movimientos con ID de cliente: " + id);
        return movimientosService.obtenerMovimientosPorCliente(id);
    }

    @GetMapping("/reportes")
    public Iterable<MovimientosResponse> obtenerMovimientosPorClienteYFecha(
            @RequestParam Long clienteId,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fechaFin) {
        movimientosLogger.logInfo("Consultando movimientos con ID de cliente: " + clienteId);
        return movimientosService.obtenerMovimientosPorClienteYFecha(clienteId, fechaInicio, fechaFin);
    }

    @PostMapping
    public MovimientosResponse registrarMovimiento(@RequestBody MovimientosRequest movimientosRequest) {
        movimientosLogger.logInfo("Creando nuevo movimiento");
        return movimientosService.registrarMovimiento(movimientosRequest);
    }

    @PutMapping("/{id}")
    public MovimientosResponse actualizarMovimiento(@PathVariable Long id, @RequestBody MovimientosRequest request) {
        movimientosLogger.logInfo("Actualizando movimiento con ID: " + id);
        return movimientosService.actualizarMovimiento(id, request);
    }

    @PatchMapping("/{id}")
    public MovimientosResponse actualizarMovimientoParcial(@PathVariable Long id, @RequestBody MovimientosRequest request) {
        movimientosLogger.logInfo("Actualizando parcialmente movimiento con ID: " + id);
        return movimientosService.actualizarMovimiento(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminarMovimiento(@PathVariable Long id) {
        movimientosLogger.logInfo("Eliminando movimiento con ID: " + id);
        movimientosService.eliminarMovimiento(id);
    }
}
