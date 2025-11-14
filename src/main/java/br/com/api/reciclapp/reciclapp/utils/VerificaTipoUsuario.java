package br.com.api.reciclapp.reciclapp.utils;

import br.com.api.reciclapp.reciclapp.entity.Solicitacao;
import br.com.api.reciclapp.reciclapp.enums.UsuarioEnum;
import br.com.api.reciclapp.reciclapp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerificaTipoUsuario {

    @Autowired
    private UsuarioService service;

    public UsuarioEnum getTipo(Solicitacao solicitacao) {
        return service.findById(solicitacao.getUsuario().getId()).getTipoUsuario();
    }

}
