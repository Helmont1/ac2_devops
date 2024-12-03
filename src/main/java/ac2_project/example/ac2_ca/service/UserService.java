package ac2_project.example.ac2_ca.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac2_project.example.ac2_ca.dto.CursoUsuarioDTO;
import ac2_project.example.ac2_ca.dto.UserDTO;
import ac2_project.example.ac2_ca.entity.Curso;
import ac2_project.example.ac2_ca.entity.CursoStatus;
import ac2_project.example.ac2_ca.entity.CursoUsuario;
import ac2_project.example.ac2_ca.entity.Plano;
import ac2_project.example.ac2_ca.entity.User;
import ac2_project.example.ac2_ca.repository.CursoRepository;
import ac2_project.example.ac2_ca.repository.CursoUsuarioRepository;
import ac2_project.example.ac2_ca.repository.User_Repository;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class UserService {

    private final User_Repository userRepository;
    private final CursoRepository cursoRepository;
    private final CursoUsuarioRepository cursoUsuarioRepository;

    // Método para listar todos os usuários
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                    .map(UserDTO::fromEntity)
                    .collect(Collectors.toList());
    }



    public void fazerCurso(CursoUsuarioDTO cursoUsuarioDTO) {
        User user = userRepository.findById(cursoUsuarioDTO.getId_usuario())
                                  .orElseThrow(() -> new RuntimeException("User not found"));
        Curso curso = cursoRepository.findById(cursoUsuarioDTO.getId_curso())
                                     .orElseThrow(() -> new RuntimeException("Course not found"));

        if (user.getPlano().equals(Plano.of("PREMIUM"))) {
            user.setMoedas(user.getMoedas() + 3);
            user.setVouchers(user.getVouchers() + 1);
        } else if (user.getPlano().equals(Plano.of("BASICO"))) {
            long cursosCompletados = cursoUsuarioRepository.countByUserAndStatus(user, CursoStatus.of("COMPLETED"));
            if (cursosCompletados >= 12) {
                user.setPlano(Plano.of("PREMIUM"));
            }
        }

        userRepository.save(user);

        CursoUsuario cursoUsuario = new CursoUsuario();
        cursoUsuario.setUser(user);
        cursoUsuario.setCurso(curso);
        cursoUsuario.setStatus(CursoStatus.of("IN_PROGRESS"));
        cursoUsuarioRepository.save(cursoUsuario);
    }


    
}