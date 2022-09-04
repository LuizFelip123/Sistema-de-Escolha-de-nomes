package br.edu.ifba.saj.ads.web.estoque.service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import br.edu.ifba.saj.ads.web.estoque.model.NomeEscolhido;
import br.edu.ifba.saj.ads.web.estoque.model.NomeRankeado;
import br.edu.ifba.saj.ads.web.estoque.model.OAuth2UserGoogle;
import br.edu.ifba.saj.ads.web.estoque.model.Usuario;
import br.edu.ifba.saj.ads.web.estoque.repository.NomeRankeadoRepository;
import br.edu.ifba.saj.ads.web.estoque.repository.UsuarioRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;




@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired 
    private NomeRankeadoRepository nomeRankeadoRepository;

    public boolean isAdministrador() {
        OAuth2User principal = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Usuario usuario = usuarioRepository.findByEmail(principal.getAttribute("email"));
        
        if (usuario != null) {
            return usuario.isAdmin();
        }
        return false;
    }

    public void addAdminRole(OAuth2AuthenticationToken authentication) {
        Collection<SimpleGrantedAuthority> oldAuthorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder
                .getContext().getAuthentication().getAuthorities();
                
        List<SimpleGrantedAuthority> newAuthorities = oldAuthorities.stream().collect(Collectors.toList());
        newAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        
        SecurityContextHolder.getContext()
                .setAuthentication(new OAuth2AuthenticationToken(
                        (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                        newAuthorities, authentication.getAuthorizedClientRegistrationId()));
    }

    public void setGoogleUserSecurityContext(OAuth2AuthenticationToken authentication) {
        OAuth2User principal = (OAuth2User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        OAuth2UserGoogle oAuth2UserGoogle = new OAuth2UserGoogle(principal);

        SecurityContextHolder.getContext().setAuthentication(
                new OAuth2AuthenticationToken(
                        oAuth2UserGoogle,
                        oAuth2UserGoogle.getAuthorities(), 
                        authentication.getAuthorizedClientRegistrationId()));

    }
    public void verficarUsuario(){
        OAuth2User principal = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(usuarioRepository.existsByEmail(principal.getAttribute("email"))==false){
        Usuario usuario = new Usuario();
        usuario.setNome(principal.getName());
        usuario.setAdmin(false);
        usuario.setEmail(principal.getAttribute("email"));
        usuario.setNomesRankeados( new ArrayList<>());
        usuarioRepository.save(usuario); 
       }
    }
    public List<Usuario> listarOutrosUsuarios(){
       OAuth2User principal = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();
       Usuario usuario = usuarioRepository.findByEmail(principal.getAttribute("email"));
       usuarios.remove(usuario);
       return usuarios;
    }

    public NomeEscolhido realizarMatch(long id){
        OAuth2User principal = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario usuario= usuarioRepository.findByEmail(principal.getAttribute("email"));

         Usuario outroUsuario =   usuarioRepository.findById(id)
               .orElseThrow(()-> new IllegalArgumentException("Id usuário invalido"+id));

           List<NomeRankeado> minhaLista = nomeRankeadoRepository.findByUsuarioOrderByRankAsc(usuario);
           List<NomeRankeado> outralista  = nomeRankeadoRepository.findByUsuarioOrderByRankAsc(outroUsuario); 
            int pontos = 10000;
            int auxpontos =pontos;
            int anterior = 0;
           NomeEscolhido nomeEscolhido = null;
           if(!minhaLista.isEmpty() && !outralista.isEmpty()){
            
                for (int i = 0; i< minhaLista.size(); i++) {
                 NomeRankeado nomeRankeado =  minhaLista.get(i);
                    for (int j = 0; j< outralista.size(); j++) {
                        NomeRankeado outroNomeRankeado = outralista.get(j);
                         if(nomeRankeado.getNomeEscolhido().getId() ==  outroNomeRankeado.getNomeEscolhido().getId()){
                            auxpontos = auxpontos - (int)(nomeRankeado.getRank()+ outroNomeRankeado.getRank()); 
                          
                            if(j == 0 ){
                                nomeEscolhido = nomeRankeado.getNomeEscolhido();
                                anterior = auxpontos;
                             }
                             if(auxpontos>anterior){
                                nomeEscolhido = nomeRankeado.getNomeEscolhido();
                             }


                         }
                    }
                }
                return nomeEscolhido;
           }else{
                if(!minhaLista.isEmpty()){
                    int index = 0;
                    nomeEscolhido = minhaLista.get(index).getNomeEscolhido();
                    return nomeEscolhido;
                }

           }
           
           return nomeEscolhido;
         
    }
}
