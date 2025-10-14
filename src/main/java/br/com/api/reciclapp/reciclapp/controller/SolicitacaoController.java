package br.com.api.reciclapp.reciclapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public void criarSolicitacao(@RequestBody Solicitacao solicitacao) {

        if (solicitacao.getUsuario().getTipoUsuario() == "COLETOR") {
            throw new RuntimeException("Usuário do tipo COLETOR não pode criar solicitações.");
        }

        service.save(solicitacao);
    }

    @GetMapping({"/{idUsuario}"})
    public List<Solicitacao> listaSolicitacoesPorUsuario(@PathVariable Long idUsuario) {
        return service.findByUsuario(idUsuario);
    }

    @GetMapping("/todas")
    public List<Solicitacao> listaTodasSolicitacoes() {
        return service.findAll();
    }
}
