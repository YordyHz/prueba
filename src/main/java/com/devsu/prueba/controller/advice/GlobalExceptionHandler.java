package com.devsu.prueba.controller.advice;

import com.devsu.prueba.common.exception.ClienteException;
import com.devsu.prueba.common.exception.CuentaException;
import com.devsu.prueba.common.exception.MovimientosException;
import com.devsu.prueba.common.util.ApiError;
import com.devsu.prueba.common.util.GeneralLogger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final GeneralLogger clienteLogger = new GeneralLogger();

    @ExceptionHandler(ClienteException.class)
    public ResponseEntity<ApiError> handleEmptyInput(ClienteException ex) {
        ApiError apiError = new ApiError(ex.getReason());
        clienteLogger.logError("Error no controlado en clientes: " + ex.getMessage());
        return new ResponseEntity<>(apiError, ex.getStatus());
    }

    @ExceptionHandler(CuentaException.class)
    public ResponseEntity<ApiError> handleCuentaException(CuentaException ex) {
        ApiError apiError = new ApiError(ex.getReason());
        clienteLogger.logError("Error no controlado en cuentas: " + ex.getMessage());
        return new ResponseEntity<>(apiError, ex.getStatus());
    }

    @ExceptionHandler(MovimientosException.class)
    public ResponseEntity<ApiError> handleMovimientosException(MovimientosException ex) {
        ApiError apiError = new ApiError(ex.getReason());
        clienteLogger.logError("Error no controlado en movimientos: " + ex.getMessage());
        return new ResponseEntity<>(apiError, ex.getStatus());
    }

}
