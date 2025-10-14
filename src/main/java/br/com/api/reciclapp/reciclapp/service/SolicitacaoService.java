package br.com.api.reciclapp.reciclapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.reciclapp.reciclapp.entity.Solicitacao;
import br.com.api.reciclapp.reciclapp.repository.SolicitacaoRepository;

@Service
public class SolicitacaoService {
 
    @Autowired
    private SolicitacaoRepository repository;

    public Solicitacao save(Solicitacao solicitacao) {
        return repository.save(solicitacao);
    }

    public List<Solicitacao> findByUsuario(Long idUsuario) {
        return repository.findByUsuario(idUsuario);
    }

}