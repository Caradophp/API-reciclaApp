package br.com.api.reciclapp.reciclapp.entity;

// import br.com.api.reciclapp.reciclapp.enums.SolicitacaoEnum;
import br.com.api.reciclapp.reciclapp.enums.SolicitacaoEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "solicitacoes")
@Getter
@Setter
public class Solicitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitacao")    
    private long id;

    @Column(name = "quantidade_em_quilos",columnDefinition = "int default 0")
    private int quantidadeEmQuilos;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SolicitacaoEnum status;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
