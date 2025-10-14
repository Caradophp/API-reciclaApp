package br.com.api.reciclapp.reciclapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.reciclapp.reciclapp.entity.Rua;
import br.com.api.reciclapp.reciclapp.repository.RuaRepository;

@Service
public class RuaService {
    
    @Autowired
    private RuaRepository repository;

    public Rua save(Rua rua) {
        return repository.save(rua);
    }

}
