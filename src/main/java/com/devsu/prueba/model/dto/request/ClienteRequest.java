package com.devsu.prueba.model.dto.request;

import com.devsu.prueba.model.enums.Genero;
import lombok.*;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClienteRequest {

    private String nombre;
    private Genero genero;
    private int edad;
    private String identificacion;
    private String direccion;
    private String telefono;
//    private String clienteId;
    private String contraseña;
    private boolean estado;
}
