package br.com.api.reciclapp.reciclapp.dto;

import br.com.api.reciclapp.reciclapp.entity.Rua;

import java.util.ArrayList;
import java.util.List;

public record RuaDadosDTO(
        long id,
        String nome
) {

    public RuaDadosDTO() {
        this(0, "");
    }

    public List<RuaDadosDTO> convertToDTO(List<Rua> ruas) {
        List<RuaDadosDTO> listDTO = new ArrayList<>();

        ruas.forEach(r -> listDTO.add(new RuaDadosDTO(r.getId(), r.getNome())));

        return listDTO;
    }

}
