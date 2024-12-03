package ac2_project.example.ac2_ca.service;

import ac2_project.example.ac2_ca.dto.CursoUsuarioDTO;
import ac2_project.example.ac2_ca.dto.UserDTO;
import ac2_project.example.ac2_ca.entity.Curso;
import ac2_project.example.ac2_ca.entity.CursoStatus;
import ac2_project.example.ac2_ca.entity.CursoUsuario;
import ac2_project.example.ac2_ca.entity.Plano;
import ac2_project.example.ac2_ca.entity.User;
import ac2_project.example.ac2_ca.entity.User_Email;
import ac2_project.example.ac2_ca.repository.CursoRepository;
import ac2_project.example.ac2_ca.repository.CursoUsuarioRepository;
import ac2_project.example.ac2_ca.repository.User_Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private User_Repository userRepository;

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private CursoUsuarioRepository cursoUsuarioRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");
        user1.setEmail(new User_Email("user1@example.com"));
        user1.setMoedas(0L);
        user1.setVouchers(0L);
        user1.setPlano(Plano.of("BASICO"));

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");
        user2.setEmail(new User_Email("user2@example.com"));
        user2.setMoedas(0L);
        user2.setVouchers(0L);
        user2.setPlano(Plano.of("PREMIUM"));

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<UserDTO> users = userService.getAllUsers();

        assertEquals(2, users.size());
        assertEquals("user1", users.get(0).getUsername());
        assertEquals("user2", users.get(1).getUsername());
    }

    @Test
    void testFazerCursoWithPremiumUser() {
        User user = new User();
        user.setId(1L);
        user.setPlano(Plano.of("PREMIUM"));
        user.setMoedas(0L);
        user.setVouchers(0L);

        Curso curso = new Curso();
        curso.setId(1L);

        CursoUsuarioDTO dto = new CursoUsuarioDTO();
        dto.setId_usuario(1L);
        dto.setId_curso(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(cursoUsuarioRepository.save(any(CursoUsuario.class))).thenReturn(new CursoUsuario());

        userService.fazerCurso(dto);

        assertEquals(3L, user.getMoedas());
        assertEquals(1L, user.getVouchers());
        verify(userRepository).save(user);
        verify(cursoUsuarioRepository).save(argThat(cu -> 
            cu.getUser().equals(user) && 
            cu.getCurso().equals(curso) && 
            cu.getStatus().equals(CursoStatus.of("IN_PROGRESS"))
        ));
    }

    @Test
    void testFazerCursoWithBasicUserLessThan12Courses() {
        User user = new User();
        user.setId(1L);
        user.setPlano(Plano.of("BASICO"));
        user.setMoedas(0L);
        user.setVouchers(0L);

        Curso curso = new Curso();
        curso.setId(1L);

        CursoUsuarioDTO dto = new CursoUsuarioDTO();
        dto.setId_usuario(1L);
        dto.setId_curso(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        when(cursoUsuarioRepository.countByUserAndStatus(user, CursoStatus.of("COMPLETED"))).thenReturn(11L);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(cursoUsuarioRepository.save(any(CursoUsuario.class))).thenReturn(new CursoUsuario());

        userService.fazerCurso(dto);

        assertEquals(Plano.of("BASICO"), user.getPlano());
        verify(userRepository).save(user);
        verify(cursoUsuarioRepository).save(argThat(cu -> 
            cu.getUser().equals(user) && 
            cu.getCurso().equals(curso) && 
            cu.getStatus().equals(CursoStatus.of("IN_PROGRESS"))
        ));
    }

    @Test
    void testFazerCursoWithBasicUserMoreThan12Courses() {
        User user = new User();
        user.setId(1L);
        user.setPlano(Plano.of("BASICO"));
        user.setMoedas(0L);
        user.setVouchers(0L);

        Curso curso = new Curso();
        curso.setId(1L);

        CursoUsuarioDTO dto = new CursoUsuarioDTO();
        dto.setId_usuario(1L);
        dto.setId_curso(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        when(cursoUsuarioRepository.countByUserAndStatus(user, CursoStatus.of("COMPLETED"))).thenReturn(12L);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(cursoUsuarioRepository.save(any(CursoUsuario.class))).thenReturn(new CursoUsuario());

        userService.fazerCurso(dto);

        assertEquals(Plano.of("PREMIUM"), user.getPlano());
        verify(userRepository).save(user);
        verify(cursoUsuarioRepository).save(argThat(cu -> 
            cu.getUser().equals(user) && 
            cu.getCurso().equals(curso) && 
            cu.getStatus().equals(CursoStatus.of("IN_PROGRESS"))
        ));
    }

    @Test
    void testFazerCursoUserNotFound() {
        CursoUsuarioDTO dto = new CursoUsuarioDTO();
        dto.setId_usuario(1L);
        dto.setId_curso(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.fazerCurso(dto));
        verify(userRepository).findById(1L);
        verifyNoMoreInteractions(cursoRepository, cursoUsuarioRepository);
    }

    @Test
    void testFazerCursoCourseNotFound() {
        User user = new User();
        user.setId(1L);
        user.setPlano(Plano.of("BASICO"));

        CursoUsuarioDTO dto = new CursoUsuarioDTO();
        dto.setId_usuario(1L);
        dto.setId_curso(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cursoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.fazerCurso(dto));
        verify(userRepository).findById(1L);
        verify(cursoRepository).findById(1L);
        verifyNoMoreInteractions(cursoUsuarioRepository);
    }
}