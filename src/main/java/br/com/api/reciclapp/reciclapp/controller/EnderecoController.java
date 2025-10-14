package br.com.api.reciclapp.reciclapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.reciclapp.reciclapp.entity.Endereco;
import br.com.api.reciclapp.reciclapp.service.EnderecoService;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {
    
    @Autowired
    private EnderecoService service;

    @PostMapping
    @ResponseBody
    public Endereco createEndereco(@RequestBody Endereco endereco) {
        return service.save(endereco);
    }
}
