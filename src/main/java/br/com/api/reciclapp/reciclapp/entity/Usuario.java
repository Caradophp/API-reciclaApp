package br.com.api.reciclapp.reciclapp.entity;

import br.com.api.reciclapp.reciclapp.enums.UsuarioEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private long id;

    private String nome;
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario")
    private UsuarioEnum tipoUsuario;
    private String senha;
    private String img;

    @OneToOne
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;
}
