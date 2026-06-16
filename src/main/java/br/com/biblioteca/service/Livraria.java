package br.com.biblioteca.service;
import br.com.biblioteca.exceptions.LivroInexistenteException;
import br.com.biblioteca.models.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;


public class Livraria implements SistemaLivraria {


    private Map<String, Livro> acervo;

    private GravadorDeDados gravador = new GravadorDeDados("dados.txt");

    public Livraria(String caminhoArquivo) {

    }

    @Override
    public void cadastrarLivro(String isbn, String titulo, String autor, double preco) {

    }

    @Override
    public Collection<Livro> pesquisarPorAutor(String autor) {
        return null;
    }

    @Override
    public void removerLivro(String isbn) throws LivroInexistenteException {

    }

    @Override
    public void salvarDados() throws IOException {

    }

    @Override
    public void recuperarDados() throws IOException {

    }
    public Collection<Livro> listarTodos() {
        return null;
    }

    public int totalDeLivros() {
        return 0;
    }
}