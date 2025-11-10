package br.com.api.reciclapp.reciclapp.controller;

import br.com.api.reciclapp.reciclapp.dto.BairroDadosDTO;
import br.com.api.reciclapp.reciclapp.dto.RuaDadosDTO;
import br.com.api.reciclapp.reciclapp.entity.Rua;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.api.reciclapp.reciclapp.entity.Bairro;
import br.com.api.reciclapp.reciclapp.service.BairroService;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getAllRuas() {
        List<Bairro> bairros = service.findAll();
        BairroDadosDTO dto = new BairroDadosDTO();
        return ResponseEntity.ok().body(dto.convertToDTO(bairros));
    }
}
