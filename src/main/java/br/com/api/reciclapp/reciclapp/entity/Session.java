package br.com.api.reciclapp.reciclapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class Session {

        @Id
        UUID id;
        Timestamp create_at;
        Timestamp expired_at;

        public Session() {
        }

        public Session(
                UUID id, Timestamp create_at, Timestamp expired_at) {
                this.id = id;
                this.create_at = create_at;
                this.expired_at = expired_at;
        }
}
