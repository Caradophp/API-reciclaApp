package br.com.api.reciclapp.reciclapp.dto;

import br.com.api.reciclapp.reciclapp.enums.SolicitacaoEnum;
import br.com.api.reciclapp.reciclapp.enums.UsuarioEnum;

public record SolicitacaoDTO(
        int quantidadeEmQuilos,
        SolicitacaoEnum status,
        long idUsuario
) {
}
