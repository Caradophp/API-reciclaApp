package br.com.api.reciclapp.reciclapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.reciclapp.reciclapp.entity.Endereco;
import br.com.api.reciclapp.reciclapp.repository.EnderecoRepository;

@Service
public class EnderecoService {
    
    @Autowired
    private EnderecoRepository service;

    public Endereco save(Endereco endereco) {
        return service.save(endereco);
    }
}
