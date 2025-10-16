package br.com.api.reciclapp.reciclapp.controller;

import java.util.List;

import br.com.api.reciclapp.reciclapp.enums.UsuarioEnum;
import br.com.api.reciclapp.reciclapp.exceptions.UsuarioDesconhecidoException;
import br.com.api.reciclapp.reciclapp.utils.VerificaTipoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/login")
    @ResponseBody
    public Usuario login(@RequestBody Usuario usuario, HttpSession session) {
        Usuario u =  service.login(usuario.getEmail(), usuario.getSenha());

        if (u != null) {
            session.setAttribute("usuarioLogado", u);
            return u;
        } else {
            return null;
        }
    }

    @PostMapping
    @ResponseBody
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
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
