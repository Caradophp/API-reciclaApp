package br.com.api.reciclapp.reciclapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco")
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_rua")
    private Rua rua;
    private String numero;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Rua getRua() {
        return rua;
    }

    public void setRua(Rua rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
