package br.com.api.reciclapp.reciclapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.api.reciclapp.reciclapp.entity.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    Optional<Usuario> getUserInfoByEmail(String userName);
    Usuario findByEmail(String email);

    String procedureCall = "call cad_user_address(?1, ?2, ?3, ?4, ?5, ?6, ?7)";

    @Query(value = procedureCall, nativeQuery = true)
    void CadastrarUsuarioViaProcedure(
            @Param("nome") String nome,
            @Param("email") String email,
            @Param("senha") String senha,
            @Param("tipoUsuario") String tipoUSuario,
            @Param("idRua") long idRua,
            @Param("numero") String numero,
            @Param("img") String img
    );

}
