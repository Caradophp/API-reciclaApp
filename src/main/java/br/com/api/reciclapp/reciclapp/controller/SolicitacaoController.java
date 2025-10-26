package br.com.api.reciclapp.reciclapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import br.com.api.reciclapp.reciclapp.enums.UsuarioEnum;
import br.com.api.reciclapp.reciclapp.exceptions.ColetorSolicitacaoException;
import br.com.api.reciclapp.reciclapp.service.UsuarioService;
import br.com.api.reciclapp.reciclapp.utils.VerificaTipoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.reciclapp.reciclapp.entity.Solicitacao;
import br.com.api.reciclapp.reciclapp.service.SolicitacaoService;

@RestController
@RequestMapping("/solicitacoes")
public class SolicitacaoController {
    
    @Autowired
    private SolicitacaoService service;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public void criarSolicitacao(@RequestBody Solicitacao solicitacao) {

       if (VerificaTipoUsuario.getTipo(solicitacao) == UsuarioEnum.COLETOR) {
            throw new ColetorSolicitacaoException("Usuário 'COLETOR' não pode fazer solicitações");
        }

        service.save(solicitacao);
    }

    @GetMapping({"/{idUsuario}"})
    public List<Map<String, Object>> listaSolicitacoesPorUsuario(@PathVariable Long idUsuario) {
        return service.findByUsuario(idUsuario);
    }

    @GetMapping("/todas")
    public List<Solicitacao> listaTodasSolicitacoes() {
        return service.findAll();
    }
}
