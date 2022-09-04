package br.edu.ifba.saj.ads.web.estoque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifba.saj.ads.web.estoque.service.UsuarioService;


@Controller
@PreAuthorize("isAuthenticated()")
public class HomeController {
    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping("/")
    public String profileSelect(Model model, OAuth2AuthenticationToken authentication) {
        usuarioService.setGoogleUserSecurityContext(authentication);
        
        usuarioService.verficarUsuario();

        if(usuarioService.isAdministrador()){
            usuarioService.addAdminRole(authentication);
        }
        System.out.println(usuarioService.isAdministrador());


        return "redirect:/Usuario/Autenticar"; 
        
    }
}
