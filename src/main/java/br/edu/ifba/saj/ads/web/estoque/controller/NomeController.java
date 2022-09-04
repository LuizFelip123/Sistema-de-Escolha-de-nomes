package br.edu.ifba.saj.ads.web.estoque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import br.edu.ifba.saj.ads.web.estoque.model.NomeEscolhido;
import br.edu.ifba.saj.ads.web.estoque.service.NomeService;
import br.edu.ifba.saj.ads.web.estoque.service.OrigemService;

import javax.validation.Valid;

@PreAuthorize("isAuthenticated()")
@Controller
@RequestMapping("/Nomes/")
public class NomeController {
    

    @Autowired
    private NomeService nomeService;
    
    @Autowired 
    OrigemService origemService;


    @GetMapping("MinhaLista")
    public String mostraMinhaLista(Model model) {
        
        model.addAttribute("nomesRankeados",nomeService.buscarLista());
        return "nomes/minhalista";
    } 

    @PostMapping("AdicionarNome")
    public String adicionarNome(@Valid NomeEscolhido nomeEscolhido,BindingResult result, Model mode) {
        if (result.hasErrors()) {
            return "redirect:/Nomes/MostrarAdicionar";
        }
  
        nomeService.saveNomeEscolhido(nomeEscolhido);    
   
        return "redirect:/Nomes/Geral";
    }

    //colocar para o  origemService
    @GetMapping("MostrarAdicionar")
    public String showAddForm(NomeEscolhido nomeEscolhido,  Model model) {
        model.addAttribute("origens",origemService.listarOrigem());
        return "nomes/adicionar";
    }

    @GetMapping("DeletarNome/{posicao}")
    public String deletarNome(@PathVariable("posicao") int posicao, Model model) {
        
        nomeService.deletarNomeList(posicao);

        return "redirect:/Nomes/MinhaLista";
    }

    @GetMapping("OutrasListas")
    public String buscarListas( Model model) {

        return "nomes/listas";
    }

    @GetMapping("SobreNome/{id}")
    public String descricaoNome(@PathVariable("id") long id, Model model) {
       
        model.addAttribute("nomeEscolhido", nomeService.mostrarSobre(id));

        return "nomes/sobre";
    }
    @GetMapping("DescerNome/{rank}")
    public String descerNomeLisa(@PathVariable("rank") long rank){

        int index = (int)rank;

        nomeService.descerNome(index);
      
        return "redirect:/Nomes/MinhaLista";
    }

    //colocar isso em usuárioController
 
    @GetMapping("SubirNome/{rank}")
    public String subirNome(@PathVariable("rank") long rank){
      
        int index = (int)rank;
        nomeService.subirNome(index);

        return "redirect:/Nomes/MinhaLista";
    }
    @GetMapping("ListaNomes/{id}")
    public String mostrarOutrosListas(@PathVariable("id") long id, Model model){
     
        model.addAttribute("nomeRankeados",  nomeService.listarNomes(id));
        return "nomes/nomeslista";
    }
 
    @GetMapping("OutraLista/{id}")
    public String addOutroNome(@PathVariable("id") long id){
        nomeService.addNomeOutraL(id);
        return"redirect:/Nomes/MinhaLista";
    }
    @GetMapping("AddNome/{id}")
    public String addNome(@PathVariable("id") long id){
        nomeService.adicionarNome(id);
       return"redirect:/Nomes/MinhaLista";
    }

    @GetMapping("NomesGerais")
    public String mostrarGeral(Model model){
        model.addAttribute("nomesEscolhidos", nomeService.mostrarEscolhidos());
        return"nomes/nomesgeral";
    }
 
    @GetMapping("NomeProprio")
    public String mostrarNomeProprio(NomeEscolhido nomeEscolhido, Model model){
        model.addAttribute("origens",origemService.listarOrigem());
        return "nomes/addproprioNome";
    }
    @PostMapping("AddProprioNome")
    public String adicionar(@Valid NomeEscolhido nomeEscolhido){
        nomeService.addNomeProprio(nomeEscolhido);
        return"redirect:/Nomes/MinhaLista";
    }
    @GetMapping("Geral")
    public String ListarGeral(Model model){
        model.addAttribute("nomesEscolhidos", nomeService.mostrarEscolhidos());
        return"nomeAdm/lista";
    }
}