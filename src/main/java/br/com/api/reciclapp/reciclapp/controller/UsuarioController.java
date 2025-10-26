package br.com.api.reciclapp.reciclapp.controller;

import java.util.*;

import br.com.api.reciclapp.reciclapp.enums.UsuarioEnum;
import br.com.api.reciclapp.reciclapp.utils.CriaSession;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    @Autowired
    private CriaSession cs;

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(Authentication authentication, HttpServletResponse response, HttpServletRequest request) {

        if (!request.getRequestURI().equals("/usuarios/login")) {
            if (authentication == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("erro", "Credenciais inválidas"));
            }
        }

        String s = "Login efetuado com sucesso";

//        Cookie cookie = new Cookie("useridsession", String.valueOf(UUID.randomUUID()));
//
//        cookie.setHttpOnly(true);
//        cookie.setSecure(false);
//        cookie.setPath("/");
//        cookie.setMaxAge(60 * 60);
        Cookie cookie = cs.criarSessao();
        response.addCookie(cookie);

        return ResponseEntity.ok(s);
    }

    @PostMapping
    @ResponseBody
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return service.save(usuario);
    }

    @PostMapping("/procedure")
    public ResponseEntity<?> cadViaProcedure(@RequestBody String json) {

        List<String> values = new ArrayList<>();

        if (json.isEmpty()) {
            throw new IllegalArgumentException("Json enviado para api não pode ser vazio");
        }

        json = json.replace("{", "");
        json = json.replace("}", "");
        json = json.trim();
        List<String> jsonF = List.of(json.split(","));

        for (String j : jsonF) {
            j = List.of(List.of(j.split(":")).get(1).trim().split("\"")).toString();
            values.add(j);
        }

        List<String> formatJson = new ArrayList<>();
        int count = 0;
        for (String v : values) {
            String[] split = v.split(",");
            if (count == 4) {
                v = split[0].replace("]", "");
            } else {
                v = split[1].replace("]", "");
            }

            String n;
            if (v.charAt(0) == '[') {
                n = v.replace("[", "");
                formatJson.add(n.trim());
            } else {
                formatJson.add(v.trim());
            }
            count++;
        }

        if (Objects.equals(formatJson.get(3), "COLETOR")) {
            service.cadSolicitacao(formatJson.get(0), formatJson.get(1), formatJson.get(2), UsuarioEnum.COLETOR, Long.valueOf(formatJson.get(4)), formatJson.get(5), formatJson.get(6));
        } else {
            service.cadSolicitacao(formatJson.get(0), formatJson.get(1), formatJson.get(2), UsuarioEnum.COMUM, Long.valueOf(formatJson.get(4)), formatJson.get(5), formatJson.get(6));
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();

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
