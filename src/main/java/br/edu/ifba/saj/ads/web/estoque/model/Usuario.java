package br.edu.ifba.saj.ads.web.estoque.model;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Usuario {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @NotBlank(message = "Nome é um campo obrigatório")
    private String nome;
    
    @Column(unique=true)
    @NotBlank(message = "Email é um campo  obrigatório")
    private String email;

    private boolean admin;

    @OneToMany(mappedBy = "usuario")
    List<NomeRankeado> nomesRankeados;
}
