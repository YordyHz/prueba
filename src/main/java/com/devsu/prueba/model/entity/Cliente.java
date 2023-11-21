package com.devsu.prueba.model.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.*;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cliente")
@Valid
public class Cliente extends Persona {

    private static final long serialVersionUID = 1L;

//    @NotNull(message = "El clienteId no puede ser un valor nulo")
//    private String clienteId;
    @NotNull(message = "La contraseña no puede ser un valor nulo")
    private String contraseña;

    //@Column(columnDefinition = "BOOLEAN DEFAULT true")
    private boolean estado;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cuenta> cuentas = new ArrayList<>();

    public void setInitialValues() {
        this.estado = true;
    }
}
