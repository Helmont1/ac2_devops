package ac2_project.example.ac2_ca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ac2_project.example.ac2_ca.entity.CursoStatus;
import ac2_project.example.ac2_ca.entity.CursoUsuario;
import ac2_project.example.ac2_ca.entity.User;

public interface CursoUsuarioRepository extends JpaRepository<CursoUsuario, Long> {

    long countByUserAndStatus(User user, CursoStatus concluido);
    
}
