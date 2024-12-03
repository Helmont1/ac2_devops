package ac2_project.example.ac2_ca.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ac2_project.example.ac2_ca.repository.CursoUsuarioRepository;

@Service
@RequiredArgsConstructor
public class CursoUsuarioService {

    private final CursoUsuarioRepository cursoUsuarioRepository;
    
}
