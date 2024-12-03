package ac2_project.example.ac2_ca.controller.test;

import ac2_project.example.ac2_ca.controller.UserController;
import ac2_project.example.ac2_ca.dto.CursoUsuarioDTO;
import ac2_project.example.ac2_ca.dto.UserDTO;
import ac2_project.example.ac2_ca.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class) // Carrega apenas o contexto da camada web
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService; // Injeta um mock do serviço, substituindo a necessidade do repositório

    @Test
    public void testGetUsers() throws Exception {
        // Configura o comportamento do mock para o método de serviço
        UserDTO mockUser = new UserDTO();
        mockUser.setId(1L);
        mockUser.setUsername("JohnDoe");
        mockUser.setEmail("john@example.com");
        
        List<UserDTO> mockUsers = List.of(mockUser);
        Mockito.when(userService.getAllUsers()).thenReturn(mockUsers);

        // Realiza a requisição e verifica a resposta
        mockMvc.perform(MockMvcRequestBuilders.get("/users") // Corrige o caminho da URL
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("JohnDoe")) // Corrige o nome do campo
                .andExpect(jsonPath("$[0].email").value("john@example.com"));
    }

    @Test
    void testFazerCurso() throws Exception {
        CursoUsuarioDTO dto = new CursoUsuarioDTO();
        dto.setId_usuario(1L);
        dto.setId_curso(1L);

        doNothing().when(userService).fazerCurso(any(CursoUsuarioDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/users/fazer-curso")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id_usuario\": 1, \"id_curso\": 1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(userService).fazerCurso(any(CursoUsuarioDTO.class));
    }

    @Test
    void testFazerCursoWhenServiceThrowsException() throws Exception {
        doThrow(new RuntimeException("User not found"))
            .when(userService).fazerCurso(any(CursoUsuarioDTO.class));

        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/users/fazer-curso")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"id_usuario\": 1, \"id_curso\": 1}")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        } catch (Exception e) {
            assertNotNull(e);
        }
    }
}