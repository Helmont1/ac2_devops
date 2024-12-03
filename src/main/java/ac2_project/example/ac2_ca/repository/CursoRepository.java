package ac2_project.example.ac2_ca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ac2_project.example.ac2_ca.entity.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    
}
