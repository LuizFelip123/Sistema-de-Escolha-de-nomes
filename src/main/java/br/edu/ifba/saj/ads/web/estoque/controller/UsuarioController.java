package br.edu.ifba.saj.ads.web.estoque.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import br.edu.ifba.saj.ads.web.estoque.service.UsuarioService;



@PreAuthorize("isAuthenticated()")
@Controller
@RequestMapping("/Usuario/")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

  
    @GetMapping("Autenticar")
    public String autenticarUsuario(){

        return"redirect:/Nomes/MinhaLista";
    }

    @GetMapping("Lista")
    public String mostrarListas(Model model){
   
      model.addAttribute("usuarios", usuarioService.listarOutrosUsuarios());
      return "nomes/listas";
    }

    @GetMapping("ListaMatch")
    public String mostrarListaMatch( Model model ){
      model.addAttribute("usuarios", usuarioService.listarOutrosUsuarios());

      return"usuario/listamatch";
    }

    @GetMapping("Match/{id}")
    public String realizarMatch(@PathVariable("id") long id , Model model){
      model.addAttribute("nomeEscolhido", usuarioService.realizarMatch(id));
      return"nomes/sobre";
    }
    
}