package br.edu.ifba.saj.ads.web.estoque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import br.edu.ifba.saj.ads.web.estoque.model.Usuario;
import br.edu.ifba.saj.ads.web.estoque.repository.UsuarioRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProdutoApplication {

	private static Logger LOG = LoggerFactory.getLogger(ProdutoApplication.class);

 

    @Autowired
    private UsuarioRepository usuarioRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ProdutoApplication.class, args);
	}

	@Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            if(usuarioRepository.findByEmail("20192tadssaj0015@ifba.edu.br") == null){
                Usuario admin = usuarioRepository.save(Usuario.builder().nome("Luiz Felipe").email("20192tadssaj0015@ifba.edu.br").admin(true).build());
            }            
        };
    }
}
