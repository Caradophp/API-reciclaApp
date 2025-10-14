package br.com.api.reciclapp.reciclapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.reciclapp.reciclapp.entity.Bairro;
import br.com.api.reciclapp.reciclapp.service.BairroService;

@RestController
@RequestMapping("/bairros")
public class BairroController {
    
    @Autowired
    private BairroService service;

    @PostMapping
    @ResponseBody
    public Bairro createBairro(@RequestBody Bairro bairro) {
        return service.save(bairro);
    }

}
