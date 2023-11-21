package com.devsu.prueba.model.mapper;

import com.devsu.prueba.model.dto.MovimientosDTO;
import com.devsu.prueba.model.dto.request.MovimientosRequest;
import com.devsu.prueba.model.dto.response.MovimientosResponse;
import com.devsu.prueba.model.entity.Movimientos;
import com.devsu.prueba.model.enums.TipoCuenta;
import java.text.SimpleDateFormat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@Mapper(componentModel = "spring")
public interface MovimientosMapper {

    MovimientosMapper INSTANCE = Mappers.getMapper(MovimientosMapper.class);

    //toEntity
    Movimientos toEntity(MovimientosDTO movimientosDTO);

    Movimientos toEntity(MovimientosResponse response);

    Movimientos toEntity(MovimientosRequest request);

    //fromEntity
    @Mapping(source = "cuenta.id", target = "cuentaId")
    MovimientosDTO toDTO(Movimientos movimientos);

    @Mapping(source = "cuenta.cliente.nombre", target = "cliente")
    @Mapping(source = "cuenta.numeroCuenta", target = "numeroCuenta")
    @Mapping(target = "tipoCuenta", expression = "java(getDescripcionTipoCuenta(movimientos))")
    @Mapping(source = "cuenta.estado", target = "estado")
    @Mapping(source = "valor", target = "movimiento")
    @Mapping(target = "fecha", expression = "java(getFormattedFecha(movimientos))")
    MovimientosResponse toResponse(Movimientos movimientos);

    default String getFormattedFecha(Movimientos movimientos) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(movimientos.getFecha());
    }
    
    default String getDescripcionTipoCuenta(Movimientos movimientos){
        return TipoCuenta.getDescripcion(movimientos.getCuenta().getTipoCuenta());
    }
    
    @Mapping(source = "cuenta.id", target = "cuentaId")
    MovimientosRequest toRequest(Movimientos movimientos);
}
