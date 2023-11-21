package com.devsu.prueba.model.mapper;

import com.devsu.prueba.model.dto.CuentaDTO;
import com.devsu.prueba.model.dto.request.CuentaRequest;
import com.devsu.prueba.model.dto.response.CuentaResponse;
import com.devsu.prueba.model.entity.Cuenta;
import com.devsu.prueba.model.enums.TipoCuenta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@Mapper(componentModel = "spring")
public interface CuentaMapper {

    CuentaMapper INSTANCE = Mappers.getMapper(CuentaMapper.class);

    //toEntity
    Cuenta toEntity(CuentaDTO cuentaDTO);

    Cuenta toEntity(CuentaResponse response);

    Cuenta toEntity(CuentaRequest request);

    //fromEntity
    @Mapping(source = "cuenta.cliente.id", target = "clienteId")
    CuentaDTO toDTO(Cuenta cuenta);
    
    @Mapping(target = "tipoCuenta", expression = "java(getDescripcionTipoCuenta(cuenta))")
    CuentaResponse toResponse(Cuenta cuenta);
    
    default String getDescripcionTipoCuenta(Cuenta cuenta){
        return TipoCuenta.getDescripcion(cuenta.getTipoCuenta());
    }
    
    @Mapping(source = "cuenta.cliente.id", target = "clienteId")
    CuentaRequest toRequest(Cuenta cuenta);
}
