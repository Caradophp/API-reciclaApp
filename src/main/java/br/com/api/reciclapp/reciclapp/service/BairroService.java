package br.com.api.reciclapp.reciclapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.reciclapp.reciclapp.entity.Bairro;
import br.com.api.reciclapp.reciclapp.repository.BairroRepository;

@Service
public class BairroService {

    @Autowired
    private BairroRepository repository;

    public Bairro save(Bairro bairro) {
        return repository.save(bairro);
    }
    
}
