package br.edu.ifba.saj.ads.web.estoque.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import br.edu.ifba.saj.ads.web.estoque.model.NomeEscolhido;
import br.edu.ifba.saj.ads.web.estoque.model.NomeRankeado;
import br.edu.ifba.saj.ads.web.estoque.model.Usuario;
import br.edu.ifba.saj.ads.web.estoque.repository.NomeEscolhidoRepository;
import br.edu.ifba.saj.ads.web.estoque.repository.NomeRankeadoRepository;
import br.edu.ifba.saj.ads.web.estoque.repository.OrigemRepository;
import br.edu.ifba.saj.ads.web.estoque.repository.UsuarioRepository;


@Service
public class NomeService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private NomeEscolhidoRepository nomeEscolhidoRepository;

    @Autowired
    private NomeRankeadoRepository nomeRankeadoRepository;
    
    @Autowired
    private OrigemRepository origemRepository;

    public List<NomeRankeado> buscarLista(){
        OAuth2User principal = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario usuario = usuarioRepository.findByEmail(principal.getAttribute("email"));
        return nomeRankeadoRepository.findByUsuarioOrderByRankAsc(usuario);
    }
    public void descerNome(int rank){
        OAuth2User principal = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Usuario usuario = usuarioRepository.findByEmail(principal.getAttribute("email"));
        List<NomeRankeado> nomeRankeados = nomeRankeadoRepository.findByUsuarioOrderByRankAsc(usuario);
        int index = rank-1;
 
        if(nomeRankeados.size()>=2 && index+1!=nomeRankeados.size()){
           
          NomeRankeado nomeRankeado = nomeRankeados.get(index);
          System.out.println(nomeRankeado.getRank());
          long novoRank = nomeRankeado.getRank()+(long)1;
          nomeRankeado.setRank(novoRank);
          System.out.println(nomeRankeado.getRank());
          NomeRankeado nomeRankeado2 =nomeRankeados.get(index+1);
          nomeRankeado2.setRank(nomeRankeado2.getRank()-(long)1);
          nomeRankeadoRepository.saveAll(nomeRankeados);
 
        }
    }
    public void subirNome(int rank){
        OAuth2User principal = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Usuario usuario = usuarioRepository.findByEmail(principal.getAttribute("email"));
        List<NomeRankeado> nomeRankeados = nomeRankeadoRepository.findByUsuarioOrderByRankAsc(usuario);
        int index = rank-1;
 
        if(nomeRankeados.size()>=2 && index!=0){
        
          NomeRankeado nomeRankeado = nomeRankeados.get(index);
          System.out.println(nomeRankeado.getRank());
          long novoRank = nomeRankeado.getRank()-(long)1;
          nomeRankeado.setRank(novoRank);
          System.out.println(nomeRankeado.getRank());
          NomeRankeado nomeRankeado2 =nomeRankeados.get(index-1);
          nomeRankeado2.setRank(nomeRankeado2.getRank()+(long)1);
          nomeRankeadoRepository.saveAll(nomeRankeados);
        }
    }
    public List<NomeRankeado> listarNomes(long id){
        Usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Id Inválido para Usuário: " + id));
        List<NomeRankeado> nomeRankeados = nomeRankeadoRepository.findByUsuarioOrderByRankAsc(usuario);
        return nomeRankeados;
    }
    public void deletarNomeList(int posicao){
        OAuth2User principal = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Usuario usuario = usuarioRepository.findByEmail(principal.getAttribute("email"));       
         List<NomeRankeado> nomesRankeados = nomeRankeadoRepository.findByUsuarioOrderByRankAsc(usuario);
        NomeRankeado nomeRankeado= nomesRankeados.remove(posicao-1);
        //descendo o rank de todos os nomesRankados 
        for(int posProximo = posicao-1; posProximo < nomesRankeados.size(); posProximo++){
            NomeRankeado nomeRankeado2 = nomesRankeados.get(posProximo);
            nomeRankeado2.setRank(nomeRankeado2.getRank()-(long)1);
        }
        nomeRankeadoRepository.delete(nomeRankeado);
        nomeRankeadoRepository.saveAll(nomesRankeados);
    }

    public void adicionarNome(long id){
        NomeEscolhido nomeEscolhido = nomeEscolhidoRepository.findById(id)
                             .orElseThrow(()-> new IllegalArgumentException("Id inválido Nome Escolhido: "+ id));

      OAuth2User principal = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      Usuario usuario = usuarioRepository.findByEmail(principal.getAttribute("email"));
      
      NomeRankeado nomeRankeado = new NomeRankeado();
      nomeRankeado.setNomeEscolhido(nomeEscolhido);
      nomeRankeado.setUsuario(usuario);

      nomeEscolhidoRepository.save(nomeEscolhido);
      List<NomeRankeado> nomesRankeados = nomeRankeadoRepository.findByUsuario(usuario);
      long tamanhoLista  = (long)nomesRankeados.size();
      nomeRankeado.setRank(tamanhoLista+(long)1);
      
      nomeRankeadoRepository.save(nomeRankeado);
    }

    public void addNomeOutraL(long id){
        NomeEscolhido nomeEscolhido = nomeEscolhidoRepository.findById(id)
        .orElseThrow(()-> new IllegalArgumentException("Id Inválido para nomeEscolhido"));

        NomeRankeado nomeRankeado = new NomeRankeado();
        OAuth2User principal = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Usuario usuario = usuarioRepository.findByEmail(principal.getAttribute("email"));
        
            nomeRankeado.setNomeEscolhido(nomeEscolhido);
            nomeRankeado.setUsuario(usuario);  
            nomeRankeado.setRank(usuario.getNomesRankeados().size()+1);
    
            nomeRankeadoRepository.save(nomeRankeado); 
        
       
    }

    public NomeEscolhido mostrarSobre(long id){
        NomeEscolhido nomeEscolhido = nomeEscolhidoRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Id Inválido para Nome:" + id));
        return nomeEscolhido;
    }

    public void updateNome(NomeEscolhido nomeEscolhido ){
        if(nomeEscolhido.getOrigem()!= null)
        origemRepository.save(nomeEscolhido.getOrigem());

        nomeEscolhidoRepository.save(nomeEscolhido);
    }

    

    public void saveNomeEscolhido(NomeEscolhido nomeEscolhido){
   
        nomeEscolhidoRepository.save(nomeEscolhido);
   
    }

    public NomeEscolhido editarNomeEscolhido(long id){
        NomeEscolhido nomeEscolhido = nomeEscolhidoRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Id Inválido para NomeEscolhido:" + id));
        return nomeEscolhido;
    }

    public List<NomeEscolhido> mostrarEscolhidos(){
        return nomeEscolhidoRepository.findAllByOrderByNomeAsc();
    }

    public void addNomeProprio(NomeEscolhido nomeEscolhido){
        OAuth2User principal = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
        Usuario usuario = usuarioRepository.findByEmail(principal.getAttribute("email"));
        NomeRankeado nomeRankeado = new NomeRankeado();
        nomeRankeado.setNomeEscolhido(nomeEscolhido);
        saveNomeEscolhido(nomeEscolhido);
        nomeRankeado.setUsuario(usuario);
        
        List<NomeRankeado> nomesRankeados = nomeRankeadoRepository.findByUsuario(usuario);
        long tamanhoLista  = (long)nomesRankeados.size();
        nomeRankeado.setRank(tamanhoLista+(long)1);
        
        nomeRankeadoRepository.save(nomeRankeado);
    }
}
