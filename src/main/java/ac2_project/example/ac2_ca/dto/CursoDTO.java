package ac2_project.example.ac2_ca.dto;

import ac2_project.example.ac2_ca.entity.Curso;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CursoDTO {
    private Long id; 

    private String nome;


    public Curso fromDTO() {
        var curso = new Curso();
        curso.setId(this.getId());
        curso.setNome(this.getNome());
        
        return curso;
    }
}
