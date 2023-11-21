package com.devsu.prueba.model.dto.response;

import com.devsu.prueba.model.enums.Genero;
import lombok.*;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClienteResponse {
    
    private Long id;
    private String nombre;
    private Genero genero;
    private int edad;
    private String identificacion;
    private String direccion;
    private String telefono;
//    private String clienteId;
}
