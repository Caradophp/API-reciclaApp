package br.com.api.reciclapp.reciclapp.utils;

import br.com.api.reciclapp.reciclapp.entity.Session;
import br.com.api.reciclapp.reciclapp.entity.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Component
public class CriaSession {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public Cookie criarSessao(String useremail) {
            UUID id = UUID.randomUUID();
            Timestamp inicio = Timestamp.from(Instant.now());
            Timestamp fim = Timestamp.from(Instant.now().plusSeconds(60 * 60)); // 1h

            // Cria cookie
            Cookie cookie = new Cookie("useridsession", id.toString());
            cookie.setHttpOnly(true);
            cookie.setSecure(false); // em produção, use true (HTTPS)
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60);

            Usuario usuario = em.createQuery("from Usuario u where u.email = :email", Usuario.class).setParameter("email", useremail).getSingleResult();

            // Persiste sessão no banco dentro da mesma transação
            Session session = new Session(id, inicio, fim, usuario);
            em.persist(session);

            return cookie;

    }

}
