package com.devsu.prueba.model.mapper;

import com.devsu.prueba.model.dto.ClienteDTO;
import com.devsu.prueba.model.dto.request.ClienteRequest;
import com.devsu.prueba.model.dto.response.ClienteResponse;
import com.devsu.prueba.model.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    //toEntity
    Cliente toEntity(ClienteDTO clienteDTO);

    Cliente toEntity(ClienteResponse response);

    Cliente toEntity(ClienteRequest request);

    //fromEntity
    ClienteDTO toDTO(Cliente cliente);

    ClienteResponse toResponse(Cliente cliente);

    ClienteRequest toRequest(Cliente cliente);
}
