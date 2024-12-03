package ac2_project.example.ac2_ca.dto;

import ac2_project.example.ac2_ca.entity.CursoStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CursoUsuarioDTO {
    private Long id; 

    private Long id_usuario;

    private Long id_curso;

    private CursoStatus cursoStatus;

}
