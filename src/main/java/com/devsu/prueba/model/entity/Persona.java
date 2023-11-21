package com.devsu.prueba.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.*;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "persona")
@Valid
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    @NotNull(message = "El nombre no puede ser un valor nulo")
    @Size(min = 2, max = 50, message = "El nombre debe tener de 2 a 50 caracteres")
    private String nombre;

    @Column(length = 1)
    @Size(max = 1, message = "El género no es válido")
    private String genero;

    private int edad;

    @NotNull(message = "La identificación no puede ser un valor nulo")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "La identificación debe contener solo caracteres alfanuméricos")
    private String identificacion;

    @NotNull(message = "La dirección no puede ser un valor nulo")
    private String direccion;

    @NotNull(message = "El teléfono no puede ser un valor nulo")
    private String telefono;
}
