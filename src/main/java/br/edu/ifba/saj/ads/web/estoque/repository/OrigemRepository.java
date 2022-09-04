package br.edu.ifba.saj.ads.web.estoque.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifba.saj.ads.web.estoque.model.Origem;

@Repository
public interface OrigemRepository extends CrudRepository<Origem, Long>{
    @Query("select p from Origem as p order by LOWER(p.descricao)")
    List<Origem> findAllByOrderByDescricaoAsc();
}
