package br.com.api.reciclapp.reciclapp.controller;

import br.com.api.reciclapp.reciclapp.dto.RuaDadosDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.api.reciclapp.reciclapp.entity.Rua;
import br.com.api.reciclapp.reciclapp.service.RuaService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getAllRuas() {
        List<Rua> ruas = service.findAll();
        RuaDadosDTO dto = new RuaDadosDTO();
        return ResponseEntity.ok().body(dto.convertToDTO(ruas));
    }
}
