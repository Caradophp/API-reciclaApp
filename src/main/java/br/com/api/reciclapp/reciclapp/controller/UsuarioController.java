package br.com.api.reciclapp.reciclapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.reciclapp.reciclapp.entity.Usuario;
import br.com.api.reciclapp.reciclapp.service.UsuarioService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("erro", "Credenciais inválidas"));
        }

        String s = "Login efetuado com sucesso";
        var usuario = Map.of(
                "username", authentication.getName(),
                "mensagem", s
        );

        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    @ResponseBody
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return service.save(usuario);
    }
    
    @GetMapping
    @ResponseBody
    public List<Usuario> listarUsuarios() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Usuario buscarUsuarioPorId(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Usuario atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return service.update(id, usuario);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deletarUsuario(@PathVariable Long id) {
        service.delete(id);
    }

}
