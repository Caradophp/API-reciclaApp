package br.com.api.reciclapp.reciclapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.reciclapp.reciclapp.entity.Rua;

@Repository
public interface RuaRepository extends JpaRepository<Rua, Long> {
    
}
