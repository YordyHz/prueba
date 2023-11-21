package com.devsu.prueba;

import com.devsu.prueba.controller.rest.ClienteController;
import com.devsu.prueba.model.dto.request.ClienteRequest;
import com.devsu.prueba.model.enums.Genero;
import com.devsu.prueba.model.service.ClienteService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.Charset;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = PruebaApplication.class)
@AutoConfigureMockMvc
class PruebaApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteController clienteController;

    @Test
    public void testCrearCliente() throws Exception {

        String nombre = "Jose Lema";
        Genero genero = Genero.M;
        int edad = 28;
        String identificacion = "12345";
        String direccion = "Otavalo sn y principal";
        String telefono = "098254785";
        String contraseña = "1234";

        ClienteRequest clienteRequest = new ClienteRequest();
        clienteRequest.setNombre(nombre);
        clienteRequest.setGenero(genero);
        clienteRequest.setEdad(edad);
        clienteRequest.setIdentificacion(identificacion);
        clienteRequest.setDireccion(direccion);
        clienteRequest.setTelefono(telefono);
        clienteRequest.setContraseña(contraseña);

        ResultActions resultActionsPost = mockMvc.perform(MockMvcRequestBuilders
                .post("/clientes")
                .content(objectMapper.writeValueAsString(clienteRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());//200

        String responseContent = resultActionsPost.andReturn().getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseContent);

        assertTrue(jsonNode.has("id"));
        Integer idCliente = jsonNode.get("id").asInt();
        assertTrue(jsonNode.has("nombre"));
        assertTrue(jsonNode.has("genero"));
        assertTrue(jsonNode.has("edad"));
        assertTrue(jsonNode.has("identificacion"));
        assertTrue(jsonNode.has("direccion"));
        assertTrue(jsonNode.has("telefono"));

        ResultActions resultActionsGet = mockMvc.perform(MockMvcRequestBuilders
                .get("/clientes/{id}", idCliente)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        responseContent = resultActionsGet.andReturn().getResponse().getContentAsString();
        jsonNode = objectMapper.readTree(responseContent);

        assertEquals(jsonNode.get("id").asInt(), idCliente);
        assertEquals(jsonNode.get("nombre").asText(), nombre);
        assertEquals(jsonNode.get("genero").asText(), genero.name());
        assertEquals(jsonNode.get("edad").asInt(), edad);
        assertEquals(jsonNode.get("identificacion").asText(), identificacion);
        assertEquals(jsonNode.get("direccion").asText(), direccion);
        assertEquals(jsonNode.get("telefono").asText(), telefono);
        assertEquals(jsonNode.get(new String(
                "contraseña".getBytes(Charset.forName("UTF-8")),
                "ISO-8859-1"
        )).asText(), contraseña);
        assertEquals(jsonNode.get("estado").asBoolean(), true);
    }

}
