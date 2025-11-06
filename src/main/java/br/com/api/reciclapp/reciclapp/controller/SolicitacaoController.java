package br.com.api.reciclapp.reciclapp.controller;

import java.util.List;
import java.util.Map;

import br.com.api.reciclapp.reciclapp.enums.UsuarioEnum;
import br.com.api.reciclapp.reciclapp.exceptions.ColetorSolicitacaoException;
import br.com.api.reciclapp.reciclapp.service.UsuarioService;
import br.com.api.reciclapp.reciclapp.utils.VerificaTipoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.api.reciclapp.reciclapp.entity.Solicitacao;
import br.com.api.reciclapp.reciclapp.service.SolicitacaoService;

@RestController
@RequestMapping("/solicitacoes")
public class SolicitacaoController {

    private final int QUANTIDADE_MINIMA_EM_QUILOS = 3;
    
    @Autowired
    private SolicitacaoService service;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> criarSolicitacao(@RequestBody Solicitacao solicitacao) {

       if (VerificaTipoUsuario.getTipo(solicitacao) == UsuarioEnum.COLETOR) {
            throw new ColetorSolicitacaoException("Usuário 'COLETOR' não pode fazer solicitações");
       }

       if (solicitacao.getQuantidadeEmQuilos() < QUANTIDADE_MINIMA_EM_QUILOS) {
            return ResponseEntity.badRequest().body("A quantidatade minima de resíduos precisa ser de no minímo " + QUANTIDADE_MINIMA_EM_QUILOS + " Quilos");
       }

       service.save(solicitacao);
       return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping({"/{idUsuario}"})
    public List<Map<String, Object>> listaSolicitacoesPorUsuario(@PathVariable Long idUsuario) {
        return service.findByUsuario(idUsuario);
    }

    @GetMapping("/todas")
    public List<Solicitacao> listaTodasSolicitacoes() {
        return service.findAll();
    }

    @PatchMapping("/atualizar-status/{idSolicitacao}")
    public ResponseEntity<?> atuazarSolicitacao(@PathVariable long idSolicitacao) {

        if (service.atualizarStatus(idSolicitacao)) {
            return ResponseEntity.ok(Map.of("Aviso", "Atualizado com sucesso"));
        }

        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Erro","Solicitação não encontrada ou já finalizada"));

    }
}
