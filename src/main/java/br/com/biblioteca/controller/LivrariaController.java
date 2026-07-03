package br.com.biblioteca.controller;

import br.com.biblioteca.exceptions.LivroInexistenteException;
import br.com.biblioteca.models.Livro;
import br.com.biblioteca.service.SistemaLivraria;

import java.io.IOException;
import java.util.Collection;

/**
 * Controller responsável por intermediar as chamadas entre a interface
 * gráfica ({@code FrontEnd}) e a camada de serviço ({@link SistemaLivraria}).
 */
public class LivrariaController {

    private final SistemaLivraria sistemaLivraria;

    public LivrariaController(SistemaLivraria sistemaLivraria) {
        this.sistemaLivraria = sistemaLivraria;
    }

    public void cadastrar(String isbn, String titulo, String autor, double preco) {
        sistemaLivraria.cadastrarLivro(isbn, titulo, autor, preco);
    }

    public Collection<Livro> pesquisarPorAutor(String autor) {
        return sistemaLivraria.pesquisarPorAutor(autor);
    }

    public void remover(String isbn) throws LivroInexistenteException {
        sistemaLivraria.removerLivro(isbn);
    }

    public void salvar() throws IOException {
        sistemaLivraria.salvarDados();
    }

    public void recuperar() throws IOException {
        sistemaLivraria.recuperarDados();
    }
}
