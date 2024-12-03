package ac2_project.example.ac2_ca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ac2_project.example.ac2_ca.dto.CursoUsuarioDTO;
import ac2_project.example.ac2_ca.dto.UserDTO;
import ac2_project.example.ac2_ca.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }
    
    @PostMapping("/fazer-curso") 
    public ResponseEntity fazerCurso(@RequestBody CursoUsuarioDTO cursoUsuarioDTO) {
        userService.fazerCurso(cursoUsuarioDTO);
        return ResponseEntity.noContent().build(); 
    }


}   