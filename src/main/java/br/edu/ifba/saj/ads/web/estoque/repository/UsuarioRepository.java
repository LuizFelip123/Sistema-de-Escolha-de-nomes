package br.edu.ifba.saj.ads.web.estoque.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifba.saj.ads.web.estoque.model.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    boolean existsByEmail(String email);

    Usuario findByEmail(String email);
    List<Usuario> findByNomeStartingWith(String nomePesquisado);
    

}

