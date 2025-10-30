package br.com.api.reciclapp.reciclapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
public class Session {

        @Id
        UUID id;
        Timestamp create_at;
        Timestamp expired_at;
        @ManyToOne
        @JoinColumn(name = "id_usuario")
        Usuario usuario;

        public Session() {
        }

        public Session(
                UUID id, Timestamp create_at, Timestamp expired_at, Usuario usuario) {
                this.id = id;
                this.create_at = create_at;
                this.expired_at = expired_at;
                this.usuario = usuario;
        }
}
