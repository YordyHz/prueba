package com.devsu.prueba.controller.rest;

import com.devsu.prueba.common.util.GeneralLogger;
import com.devsu.prueba.model.dto.CuentaDTO;
import com.devsu.prueba.model.dto.request.CuentaRequest;
import com.devsu.prueba.model.dto.response.CuentaResponse;
import com.devsu.prueba.model.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@RestController
@RequestMapping("cuentas")
@CrossOrigin(origins = "http://localhost:4200")
public class CuentaController {

    private final CuentaService cuentaService;
    private final GeneralLogger cuentaLogger;

    @Autowired
    public CuentaController(CuentaService cuentaService, GeneralLogger cuentaLogger) {
        this.cuentaService = cuentaService;
        this.cuentaLogger = cuentaLogger;
    }

    @GetMapping
    public Iterable<CuentaResponse> obtenerTodasLasCuentas() {
        cuentaLogger.logInfo("Consultando listado de todas las cuentas");
        return cuentaService.obtenerTodasLasCuentas();
    }

    @GetMapping("/{id}")
    public CuentaDTO obtenerCuentaPorId(@PathVariable Long id) {
        cuentaLogger.logInfo("Consultando cuenta con ID: " + id);
        return cuentaService.obtenerCuentaPorId(id);
    }

    @PostMapping
    public CuentaResponse crearCuenta(@RequestBody CuentaRequest cuentaRequest) {
        cuentaLogger.logInfo("Creando nueva cuenta");
        return cuentaService.crearCuenta(cuentaRequest);
    }

    @PutMapping("/{id}")
    public CuentaResponse actualizarCuenta(@PathVariable Long id, @RequestBody CuentaRequest request) {
        cuentaLogger.logInfo("Actualizando cuenta con ID: " + id);
        return cuentaService.actualizarCuenta(id, request);
    }

    @PatchMapping("/{id}")
    public CuentaResponse actualizarCuentaParcial(@PathVariable Long id, @RequestBody CuentaRequest request) {
        cuentaLogger.logInfo("Actualizando parcialmente cuenta con ID: " + id);
        return cuentaService.actualizarCuentaParcial(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminarCuenta(@PathVariable Long id) {
        cuentaLogger.logInfo("Eliminando cuenta con ID: " + id);
        cuentaService.eliminarCuenta(id);
    }
}
