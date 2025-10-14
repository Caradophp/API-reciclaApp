package br.com.api.reciclapp.reciclapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.api.reciclapp.reciclapp.entity.Solicitacao;

@Repository
public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {

    String query = "select s.id_solicitacao, s.status, s.id_usuario, \n" + //
                "b.nome as bairro, r.nome as rua, numero from solicitacoes s\n" + //
                "inner join usuarios u on u.id_usuario = s.id_usuario\n" + //
                "inner join endereco e on e.id_endereco = u.id_endereco\n" + //
                "inner join rua r on r.id_rua = e.id_rua\n" + //
                "inner join bairro b on b.id_bairro = r.id_bairro\n" + //
                "where s.id_usuario = :idUsuario";

    @org.springframework.data.jpa.repository.Query(value = query, nativeQuery = true)
    List<Solicitacao> findByUsuario(@Param("idUsuario") Long idUsuario);

    
} 
