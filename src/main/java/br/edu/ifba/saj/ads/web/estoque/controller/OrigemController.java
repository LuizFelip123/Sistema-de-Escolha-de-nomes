package br.edu.ifba.saj.ads.web.estoque.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifba.saj.ads.web.estoque.model.Origem;
import br.edu.ifba.saj.ads.web.estoque.service.OrigemService;

import javax.validation.Valid;

@PreAuthorize("isAuthenticated()")
@Controller
@RequestMapping("/Origem/")
public class OrigemController {
    @Autowired
    OrigemService origemService;

    @GetMapping("Adicionar")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String mostrarAdicionar(Model model, Origem origem){

        return"origem/adicionar";
    }

    @PostMapping("Salvar")
    public String salvarOrigem(@Valid Origem origem, BindingResult result){
      
        if(result.hasErrors()){
            return"redirect:/Origem/Adicionar";
        }
        origemService.salvarOrigem(origem);
        
        return"redirect:/Origem/ListaOrigem";
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("ListaOrigem")
    public String listarOrigem(Model model){ 
        model.addAttribute("origens", origemService.listarOrigem());
        
        return"origem/listagem";
    }
    
   
    
}
