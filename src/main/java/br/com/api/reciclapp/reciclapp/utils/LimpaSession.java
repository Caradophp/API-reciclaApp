package br.com.api.reciclapp.reciclapp.utils;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LimpaSession {

    @PersistenceContext
    EntityManager em;

    /*
    * Essa rotina roda a cada determinado periodo de tempo limpando a tabela de session
    * deletando aquelas sessões que já expireram, apenas para não ocupaar espaço no banco de dados
     */
    @Scheduled(fixedRate = 1 * 60 * 1000)
    public void limpar() {
        em.createQuery("delete from Session s where  s.expired_at < urrent_timestamp()").executeUpdate();
    }

}
