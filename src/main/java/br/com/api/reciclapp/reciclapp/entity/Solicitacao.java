package br.com.api.reciclapp.reciclapp.entity;

// import br.com.api.reciclapp.reciclapp.enums.SolicitacaoEnum;
import br.com.api.reciclapp.reciclapp.enums.SolicitacaoEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "solicitacoes")
public class Solicitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitacao")    
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SolicitacaoEnum status;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SolicitacaoEnum getStatus() {
        return status;
    }

    public void setStatus(SolicitacaoEnum status) {
        this.status = status;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
