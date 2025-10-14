package br.com.api.reciclapp.reciclapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.reciclapp.reciclapp.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{   

    Usuario findByEmailAndSenha(String email, String senha);

}
