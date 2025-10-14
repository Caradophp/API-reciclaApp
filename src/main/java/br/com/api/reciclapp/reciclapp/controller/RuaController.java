package br.com.api.reciclapp.reciclapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.reciclapp.reciclapp.entity.Rua;
import br.com.api.reciclapp.reciclapp.service.RuaService;

@RestController
@RequestMapping("/ruas")
public class RuaController {
    
    @Autowired
    private RuaService service;

    @PostMapping
    @ResponseBody
    public Rua createRua(@RequestBody Rua rua) {
        return service.save(rua);
    }
}
