package com.devsu.prueba.model.dto.response;

import com.devsu.prueba.model.enums.TipoCuenta;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CuentaResponse {

    private Long id;
    private Long numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private BigDecimal limiteDiario;
    private String estado;
    private ClienteResponse cliente;
}
