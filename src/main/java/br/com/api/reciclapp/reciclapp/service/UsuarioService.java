package br.com.api.reciclapp.reciclapp.service;

import java.util.List;

import br.com.api.reciclapp.reciclapp.enums.UsuarioEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.api.reciclapp.reciclapp.entity.Usuario;
import br.com.api.reciclapp.reciclapp.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario login(String email, String senha) {
        return null;
    }

    public void cadSolicitacao(
            String nome,
            String email,
            String senha,
            UsuarioEnum tipoUsuario,
            long idRua,
            String numero,
            String img
    ) {

        String senhaHash = passwordEncoder.encode(senha);

        if (tipoUsuario.equals(UsuarioEnum.COLETOR)) {
            repository.CadastrarUsuarioViaProcedure(
                    nome, email, senhaHash, "COLETOR",
                    idRua, numero, img
            );
        } else if (tipoUsuario.equals(UsuarioEnum.COMUM)) {
            repository.CadastrarUsuarioViaProcedure(
                    nome, email, senhaHash, "COMUM",
                    idRua, numero, img
            );
        } else {
            throw new IllegalArgumentException("Tipo de usuário não conhecido");
        }

    }

    public Usuario save(Usuario usuario) {
        return repository.save(usuario);
    }

    public List<Usuario> findAll() {
        return repository.findAll();
    }

    public Usuario findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Usuario update(Long id, Usuario usuario) {
        Usuario existingUsuario = repository.findById(id).orElse(null);
        if (existingUsuario != null) {
            existingUsuario.setNome(usuario.getNome());
            existingUsuario.setEmail(usuario.getEmail());
            existingUsuario.setSenha(usuario.getSenha());
            return repository.save(existingUsuario);
        }
        return null;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
