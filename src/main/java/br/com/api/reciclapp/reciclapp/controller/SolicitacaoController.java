package br.com.api.reciclapp.reciclapp.controller;

import java.util.ArrayList;
import java.util.List;
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

    @PostMapping("/procedure")
    public void cadViaProcedure(@RequestBody String json) {

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

        formatJson.forEach( j -> System.out.println(j));

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
