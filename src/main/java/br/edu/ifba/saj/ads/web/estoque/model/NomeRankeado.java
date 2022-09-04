package br.edu.ifba.saj.ads.web.estoque.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class NomeRankeado   {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name ="usuario_id")
    private Usuario usuario;
    
    @OneToOne
    @JoinColumn(name = "nomeEscolhido_id")
    private NomeEscolhido nomeEscolhido;
    
    private long rank;

 

}
