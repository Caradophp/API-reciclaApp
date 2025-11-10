package br.com.api.reciclapp.reciclapp.dto;

import br.com.api.reciclapp.reciclapp.entity.Bairro;

import java.util.ArrayList;
import java.util.List;

public record BairroDadosDTO(
        long id,
        String nome
) {

    public BairroDadosDTO() {
        this(0, "");
    }

    public List<BairroDadosDTO> convertToDTO(List<Bairro> bairros) {
        List<BairroDadosDTO> listDTO = new ArrayList<>();

        bairros.forEach(b -> listDTO.add(new BairroDadosDTO(b.getId(), b.getNome())));

        return listDTO;
    }
}
