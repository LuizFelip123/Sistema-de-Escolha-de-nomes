package br.edu.ifba.saj.ads.web.estoque.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class NomeEscolhido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(unique=true)
    @NotBlank(message="Nome é um campo obrigatório")
    private String nome;
    
    @ManyToOne
    @JoinColumn(name="origem_id")
    private Origem origem;
    
    private String  significado;


}
