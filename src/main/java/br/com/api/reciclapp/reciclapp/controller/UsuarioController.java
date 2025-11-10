package br.com.api.reciclapp.reciclapp.controller;

import java.util.*;

import br.com.api.reciclapp.reciclapp.dto.CadastroUsuarioDTO;
import br.com.api.reciclapp.reciclapp.enums.UsuarioEnum;
import br.com.api.reciclapp.reciclapp.utils.CriaSession;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

    @PersistenceContext
    private EntityManager em;

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

        var headerAuthorization = request.getHeader("Authorization");

        String base64Credentials = headerAuthorization.substring(6);
        byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
        String decodedString = new String(decodedBytes);
        String[] credentials = decodedString.split(":", 2);

        String useremail = credentials[0];

        long idUsuarioLogado = service.findByEmail(useremail);

        Cookie cookie = cs.criarSessao(useremail);
        response.addCookie(cookie);

        Map<String, String> params = new HashMap<>();
        params.put("Aviso", "Logado com sucesso");
        params.put("ID", String.valueOf(idUsuarioLogado));

        return ResponseEntity.status(HttpStatus.OK).body(params);
    }

    @PostMapping
    @ResponseBody
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return service.save(usuario);
    }

    @PostMapping("/procedure")
    public ResponseEntity<?> cadViaProcedure(@RequestBody CadastroUsuarioDTO dto) {
        service.cadSolicitacao(dto);
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

    @GetMapping("/obter-usuario-logado/{idSessao}")
    @ResponseBody
    public long obterIdUsuarioPorIdSessao(@PathVariable UUID idSessao) {
        Usuario usuarioLogado = em.createQuery("select s.usuario from Session s where id = :id", Usuario.class)
                .setParameter("id", idSessao)
                .getSingleResult();

        return usuarioLogado.getId();
    }

}
