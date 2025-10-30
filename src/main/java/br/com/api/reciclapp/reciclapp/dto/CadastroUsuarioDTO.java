package br.com.api.reciclapp.reciclapp.dto;

import br.com.api.reciclapp.reciclapp.enums.UsuarioEnum;

public record CadastroUsuarioDTO(
        String nome,
        String email,
        String senha,
        UsuarioEnum tipoUsuario,
        long idRua,
        String numero,
        String img
) {

    @Override
    public String email() {
        return email;
    }

    @Override
    public String nome() {
        return nome;
    }

    @Override
    public String senha() {
        return senha;
    }

    @Override
    public UsuarioEnum tipoUsuario() {
        return tipoUsuario;
    }

    @Override
    public long idRua() {
        return idRua;
    }

    @Override
    public String numero() {
        return numero;
    }

    @Override
    public String img() {
        return img;
    }

    @Override
    public String toString() {
        return "CadastroUsuarioDTO{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", tipoUSuario=" + tipoUsuario +
                ", idRua=" + idRua +
                ", numero='" + numero + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}