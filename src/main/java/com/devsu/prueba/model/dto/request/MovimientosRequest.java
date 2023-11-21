package com.devsu.prueba.model.dto.request;

import com.devsu.prueba.model.enums.TipoMovimiento;
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
public class MovimientosRequest {

//    private TipoMovimiento tipoMovimiento;
    private BigDecimal valor;
//    private BigDecimal saldoInicial;
//    private BigDecimal saldoDisponible;
    private Long CuentaId;
}
