package br.edu.ifba.saj.ads.web.estoque.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifba.saj.ads.web.estoque.model.NomeEscolhido;

@Repository
public interface NomeEscolhidoRepository extends CrudRepository<NomeEscolhido, Long>{
    @Query("select p from NomeEscolhido as p order by LOWER(p.nome)")
    List<NomeEscolhido> findAllByOrderByNomeAsc();
}
