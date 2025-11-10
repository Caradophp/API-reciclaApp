package br.com.api.reciclapp.reciclapp.service;

import br.com.api.reciclapp.reciclapp.entity.Rua;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.reciclapp.reciclapp.entity.Bairro;
import br.com.api.reciclapp.reciclapp.repository.BairroRepository;

import java.util.List;

@Service
public class BairroService {

    @Autowired
    private BairroRepository repository;

    public Bairro save(Bairro bairro) {
        return repository.save(bairro);
    }

    public List<Bairro> findAll() {
        return repository.findAll();
    }
}
