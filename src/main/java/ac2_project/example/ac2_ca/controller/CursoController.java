package ac2_project.example.ac2_ca.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ac2_project.example.ac2_ca.dto.CursoDTO;
import ac2_project.example.ac2_ca.entity.Curso;
import ac2_project.example.ac2_ca.repository.CursoRepository;
import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
@RequestMapping("/curso")
public class CursoController {
    private final CursoRepository cursoRepository;

    @PostMapping()
    public Curso criarCurso(@RequestBody CursoDTO entity) {
        return cursoRepository.save(entity.fromDTO());
    }

    @GetMapping()
    public List<Curso> getCursos() {
        return cursoRepository.findAll();
    }
}
