package br.edu.ifba.saj.ads.web.estoque.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifba.saj.ads.web.estoque.model.Origem;
import br.edu.ifba.saj.ads.web.estoque.repository.OrigemRepository;

@Service
public class OrigemService {
  @Autowired
  OrigemRepository origemRepository; 
  
  public void salvarOrigem(Origem origem){
    origemRepository.save(origem);
  }
  
  public List<Origem> listarOrigem(){
    return origemRepository.findAllByOrderByDescricaoAsc();
  }
}
