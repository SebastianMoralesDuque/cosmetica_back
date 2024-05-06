package com.uniquindio.software.clinica;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uniquindio.software.clinica.modelo.Administrador;
import com.uniquindio.software.clinica.servicios.UsuarioService;
import com.uniquindio.software.clinica.servicios.implementaciones.UsuarioServiceImpl;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Utiliza UsuarioServiceImpl en lugar de UsuarioService
    @MockBean
    private UsuarioServiceImpl usuarioService;

    @Test
    public void verificarLoginAdmin_LoginExitoso() throws Exception {
        // Datos de prueba
        Map<String, Object> loginData = new HashMap<>();
        loginData.put("email", "admin@example.com");
        loginData.put("password_admin", "contrasena");

        // Mockear el servicio para que devuelva un Administrador válido
        Administrador admin = new Administrador();
        // Aquí puedes ajustar el objeto 'admin' según sea necesario para tus pruebas

        when(usuarioService.obtenerAdminPorCorreo("admin@example.com")).thenReturn(admin);
        when(usuarioService.verificarContrasenaAdmin("admin@example.com", "contrasena")).thenReturn(true);

        // Ejecutar la solicitud HTTP de prueba
        mockMvc.perform(MockMvcRequestBuilders.post("/usuarios/gestion/login/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(loginData)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Inicio de sesión exitoso"));
    }

    @Test
    public void verificarLoginAdmin_LoginFallido() throws Exception {
        // Datos de prueba
        Map<String, Object> loginData = new HashMap<>();
        loginData.put("email", "admin@example.com");
        loginData.put("password_admin", "contrasena_incorrecta");

        // Mockear el servicio para que devuelva null, simulando un usuario no encontrado
        when(usuarioService.obtenerAdminPorCorreo("admin@example.com")).thenReturn(null);

        // Ejecutar la solicitud HTTP de prueba
        mockMvc.perform(MockMvcRequestBuilders.post("/usuarios/gestion/login/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(loginData)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized()); // Esperamos un código 401 (Unauthorized)
    }


    // Método auxiliar para convertir objetos Java a JSON
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
