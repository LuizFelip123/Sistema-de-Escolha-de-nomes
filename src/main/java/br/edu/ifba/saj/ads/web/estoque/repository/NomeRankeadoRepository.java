package br.edu.ifba.saj.ads.web.estoque.repository;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.edu.ifba.saj.ads.web.estoque.model.NomeEscolhido;
import br.edu.ifba.saj.ads.web.estoque.model.NomeRankeado;
import br.edu.ifba.saj.ads.web.estoque.model.Usuario;

@Repository
public interface NomeRankeadoRepository  extends  CrudRepository<NomeRankeado, Long>{
    List<NomeRankeado> findByUsuario(Usuario usuario);
    NomeRankeado findByNomeEscolhido(NomeEscolhido nomeEscolhido);
    List<NomeRankeado> findByUsuarioOrderByRankAsc(Usuario usuario);    
}
