package br.com.biblioteca.service;

import br.com.biblioteca.exceptions.LivroInexistenteException;
import br.com.biblioteca.models.*;

import java.io.IOException;
import java.util.Collection;

public interface SistemaLivraria {

    public void cadastrarLivro(String isbn, String titulo, String autor, double preco);
    Collection<Livro> pesquisarPorAutor(String autor);
    public void removerLivro(String isbn) throws LivroInexistenteException;
    void salvarDados() throws IOException;
    void recuperarDados() throws IOException;
}