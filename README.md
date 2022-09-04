# Projeto web com SpringBoot e Thymeleaf

## Criando o projeto com VSCode

1. Com o Spring Boot Extension Pack instalado no vscode pressione [[F1]] e escolha a opção *Spring initializr: Create a Maven Project* -> *2.6.7* -> *java*
1. Escolha o pacote da aplicação (br.edu.ifba.saj.ads.web)
1. Escolha o Artifact Id (appname)
1. Selecione Jar para *packaging type*
1. Para java version selecione a mais recente que vc tiver ( >= 11)

## Selecionando as Dependências 

1. Selecione as seguintes dependências:
    1. Spring Web
    1. Spring JPA
    1. Thymeleaf 
    1. H2 database
    1. Lombok

## Camana de Modelo 

Definir as classes que serão persistidas seguindo o modelo abaixo:

```java

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Produto{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Descrição é obrigatório")
    private String descricao;

}
```

## A Camada do Repositório

O Spring Data JPA é um componente-chave do spring-boot-starter-data-jpa do Spring Boot que torna mais fácil adicionar a funcionalidade CRUD 

Para fornecer ao nosso aplicativo a funcionalidade CRUD básica, tudo o que precisamos fazer é estender a interface do CrudRepository :


```java
@Repository
public interface ProdutoRepository extends CrudRepository<Produto, Long> {}
```


## A camada de controlador

A classe do controlador depende de alguns dos principais recursos do Spring MVC. Vamos começar com os métodos `mostrarFormularioProduto()` e `addProduto()` do controlador .


## A Camada de Visualização

Na pasta src/main/resources/templates , precisamos criar os modelos HTML necessários para exibir o formulário de inscrição, o formulário de atualização e renderizar a lista de entidades de `Produto` persistentes. Para isso usaremos o Thymeleaf.