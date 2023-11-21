package com.devsu.prueba.model.service.impl;

import com.devsu.prueba.common.exception.MovimientosException;
import com.devsu.prueba.common.util.GeneralLogger;
import com.devsu.prueba.common.util.MovimientosConstant;
import com.devsu.prueba.common.util.Utilidades;
import com.devsu.prueba.model.dto.request.MovimientosRequest;
import com.devsu.prueba.model.dto.response.MovimientosResponse;
import com.devsu.prueba.model.entity.Cuenta;
import com.devsu.prueba.model.entity.Movimientos;
import com.devsu.prueba.model.enums.TipoMovimiento;
import com.devsu.prueba.model.mapper.MovimientosMapper;
import com.devsu.prueba.model.repository.CuentaRepository;
import com.devsu.prueba.model.repository.MovimientosRepository;
import com.devsu.prueba.model.service.MovimientosService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
public class MovimientosServiceImpl implements MovimientosService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MovimientosRepository movimientosRepository;

    @Autowired
    private MovimientosMapper movimientosMapper;

    @Autowired
    private GeneralLogger generalLogger;

    @Override
    public MovimientosResponse registrarMovimiento(MovimientosRequest movimientosRequest) {
        Long cuentaId = movimientosRequest.getCuentaId();
        Cuenta cuenta = cuentaRepository.findByIdAndEstadoTrue(cuentaId)
                .orElseThrow(() -> new MovimientosException(
                HttpStatus.NOT_FOUND,
                String.format(MovimientosConstant.CUENTA_ASOCIADA_NO_ENCONTRADA_MESSAGE_ERROR)
        ));

        // Crear el movimiento
        Movimientos movimiento = crearMovimiento(cuenta, movimientosRequest.getValor());

        // Guardar el movimiento en la base de datos
        movimiento = movimientosRepository.save(movimiento);

        //Actualizar saldo en la cuenta
        cuenta.setSaldoInicial(movimiento.getSaldoDisponible());
        cuentaRepository.save(cuenta);

        // Devolver la respuesta mapeada
        generalLogger.logInfo("Movimiento creado exitosamente.");
        return movimientosMapper.toResponse(movimiento);
    }

    @Override
    public MovimientosResponse actualizarMovimiento(Long id, MovimientosRequest movimientosRequest) {
        // Obtener el movimiento existente por su ID
        Movimientos movimientoExistente = obtenerMovimientoPorId(id);

        // Actualizar los campos del movimiento
        actualizarCamposMovimiento(movimientoExistente, movimientosRequest);

        // Guardar el movimiento actualizado en la base de datos
        Movimientos movimientoActualizado = movimientosRepository.save(movimientoExistente);

        //Actualizar saldo en la cuenta
        movimientoActualizado.getCuenta().setSaldoInicial(movimientoActualizado.getSaldoDisponible());
        cuentaRepository.save(movimientoActualizado.getCuenta());

        // Devolver la respuesta mapeada
        generalLogger.logInfo("Movimiento actualizado exitosamente.");
        return movimientosMapper.toResponse(movimientoActualizado);
    }

    @Override
    public void eliminarMovimiento(Long id) {
        Optional<Movimientos> movimientosOptional = movimientosRepository.findById(id);
        if (movimientosOptional.isPresent()) {
            movimientosRepository.deleteById(id);
            generalLogger.logInfo("Movimiento eliminado exitosamente.");
        } else {
            throw new MovimientosException(HttpStatus.NOT_FOUND,
                    String.format(MovimientosConstant.MOVIMIENTO_NO_ELIMINADO_MESSAGE_ERROR,
                            id));
        }
    }

    @Override
    public Iterable<MovimientosResponse> obtenerMovimientosPorCuenta(Long idCuenta) {
        Iterable<Movimientos> movimientoss = movimientosRepository.findByCuenta(idCuenta);
        List<MovimientosResponse> movimientosResponses = new ArrayList<>();

        for (Movimientos movimientos : movimientoss) {
            movimientosResponses.add(movimientosMapper.toResponse(movimientos));
        }

        return movimientosResponses;
    }

    @Override
    public Iterable<MovimientosResponse> obtenerMovimientosPorCliente(Long idCliente) {
        Iterable<Movimientos> movimientos = movimientosRepository.findByCliente(idCliente);
        List<MovimientosResponse> movimientosResponses = new ArrayList<>();

        for (Movimientos movimiento : movimientos) {
            movimientosResponses.add(movimientosMapper.toResponse(movimiento));
        }

        return movimientosResponses;
    }

    @Override
    public Iterable<MovimientosResponse> obtenerMovimientosPorClienteYFecha(Long idCliente, LocalDate fechaInicio, LocalDate fechaFin) {
        Date fechaInicioDate = Date.from(fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fechaFinDate = Date.from(fechaFin.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Iterable<Movimientos> movimientos = movimientosRepository.findByClienteAndFechaBetween(idCliente, fechaInicioDate, fechaFinDate);
        List<MovimientosResponse> movimientosResponses = new ArrayList<>();

        for (Movimientos movimiento : movimientos) {
            movimientosResponses.add(movimientosMapper.toResponse(movimiento));
        }

        return movimientosResponses;
    }

    private void validarLimiteDiario(Cuenta cuenta, BigDecimal valor, String tipoMovimiento) {
        if (tipoMovimiento.equals(TipoMovimiento.C.name())) {
            return;
        }
        BigDecimal limiteDiario = cuenta.getLimiteDiario();

        LocalDate today = LocalDate.now();
        Date startOfDay = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endOfDay = Date.from(today.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());

        BigDecimal totalRetirosDiarios = movimientosRepository
                .sumValorByCuentaAndFecha(cuenta, startOfDay, endOfDay, TipoMovimiento.D.name())
                .orElse(BigDecimal.ZERO);

        if (limiteDiario.compareTo(totalRetirosDiarios.add(valor.abs())) < 0) {
            throw new MovimientosException(HttpStatus.BAD_REQUEST,
                    MovimientosConstant.LIMITE_DIARIO_ALCANZADO_MESSAGE_ERROR);
        }
    }

    private void actualizarSaldosMovimiento(Movimientos movimiento) {
        BigDecimal saldoInicial = movimiento.getCuenta().getSaldoInicial();
        BigDecimal saldoDisponible = saldoInicial.add(movimiento.getValor());

        // Validar si el saldo disponible es cero y el movimiento es de tipo débito
        if (Utilidades.isZeroOrLess(saldoDisponible) && movimiento.getTipoMovimiento().equals(TipoMovimiento.D.name())) {
            throw new MovimientosException(HttpStatus.BAD_REQUEST,
                    MovimientosConstant.SALDO_NO_DISPONIBLE_MESSAGE_ERROR);
        }

        movimiento.setSaldoInicial(saldoInicial);
        movimiento.setSaldoDisponible(saldoDisponible);
    }

    private Movimientos crearMovimiento(Cuenta cuenta, BigDecimal valor) {
        String tipoMovimiento = Utilidades.tipoMovimiento(valor).name();
        // Validar el límite diario de retiro
        validarLimiteDiario(cuenta, valor, tipoMovimiento);

        Movimientos movimiento = new Movimientos();
        movimiento.setCuenta(cuenta);
        movimiento.setValor(valor);

        // Configurar tipo de movimiento y actualizar saldos
        movimiento.setTipoMovimiento(tipoMovimiento);
        actualizarSaldosMovimiento(movimiento);

        return movimiento;
    }

    private Movimientos obtenerMovimientoPorId(Long id) {
        // Obtener el movimiento existente por su ID
        Movimientos movimientoExistente = movimientosRepository.findById(id)
                .orElseThrow(() -> new MovimientosException(HttpStatus.NOT_FOUND,
                String.format(MovimientosConstant.MOVIMIENTO_NO_ENCONTRADO_MESSAGE_ERROR, id)
        ));
        return movimientoExistente;
    }

    private Cuenta validarMovimientoCuenta(Movimientos movimiento) {
        // Validar si el movimiento pertenece a la cuenta y obtener la cuenta asociada
        Cuenta cuenta = movimiento.getCuenta();
        if (cuenta == null || !cuenta.isEstado()) {
            throw new MovimientosException(HttpStatus.NOT_FOUND,
                    MovimientosConstant.CUENTA_ASOCIADA_NO_ENCONTRADA_MESSAGE_ERROR
            );
        }
        return cuenta;
    }

    private void actualizarCamposMovimiento(Movimientos movimiento, MovimientosRequest movimientosRequest) {
        Cuenta cuenta = validarMovimientoCuenta(movimiento);

        BigDecimal valor = movimientosRequest.getValor();
        String tipoMovimiento = Utilidades.tipoMovimiento(valor).name();

        validarLimiteDiario(cuenta, valor, tipoMovimiento);

        movimiento.setValor(valor);
        movimiento.setTipoMovimiento(tipoMovimiento);

        actualizarSaldosMovimiento(movimiento);
    }
}
