package br.com.api.reciclapp.reciclapp.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import br.com.api.reciclapp.reciclapp.entity.Usuario;
import br.com.api.reciclapp.reciclapp.enums.SolicitacaoEnum;
import br.com.api.reciclapp.reciclapp.enums.UsuarioEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

    public List<Map<String, Object>> findByUsuario(Long idUsuario) {
        return repository.findByUsuario(idUsuario);
    }

    public List<Map<String, Object>> findAll() {
        return repository.findAllSolicitacoes();
    }

    public boolean atualizarStatus(long id) {
        Optional<Solicitacao> solicitacao = repository.findById(id);

        if (!solicitacao.isEmpty()) {
            Solicitacao s = solicitacao.get();
            SolicitacaoEnum status = s.getStatus();

            if (status.equals(SolicitacaoEnum.PENDENTE)) {
                s.setStatus(SolicitacaoEnum.ANDAMENTO);
            }

            if (status.equals(SolicitacaoEnum.ANDAMENTO)) {
                s.setStatus(SolicitacaoEnum.CONCLUIDA);
            }

            repository.save(s);
            return true;
        }

        return false;
    }

}