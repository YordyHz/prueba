package com.devsu.prueba.common.exception;

import com.devsu.prueba.common.util.GeneralLogger;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
public class MovimientosException extends ResponseStatusException {

    private final GeneralLogger clienteLogger = new GeneralLogger();

    public MovimientosException(HttpStatus status) {
        super(status);
        clienteLogger.logError("Excepción de movimientos con status: " + status);
    }

    public MovimientosException(HttpStatus status, String reason) {
        super(status, reason);
        clienteLogger.logError("Excepción de movimientos: " + reason + " con status: " + status);
    }

    public MovimientosException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
        clienteLogger.logError("Excepción de movimientos: " + reason + " con status: " + status, cause);
    }
}
