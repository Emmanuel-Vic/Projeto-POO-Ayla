package br.com.biblioteca.service;
import br.com.biblioteca.exceptions.LivroInexistenteException;
import br.com.biblioteca.models.*;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Livraria implements SistemaLivraria {

    private Map<String, Livro> acervo;
    private GravadorDeDados gravador;

    public Livraria(String caminhoArquivo) {
        this.acervo = new HashMap<>();
        this.gravador = new GravadorDeDados(caminhoArquivo);
    }

    @Override
    public void cadastrarLivro(String isbn, String titulo, String autor, double preco) {
        acervo.put(isbn, new Livro(isbn, titulo, autor, preco));
    }

    @Override
    public Collection<Livro> pesquisarPorAutor(String autor) {
        return acervo.values().stream()
                .filter(l -> l.getAutor().equalsIgnoreCase(autor))
                .collect(Collectors.toList());
    }

    @Override
    public void removerLivro(String isbn) throws LivroInexistenteException {
        if (!acervo.containsKey(isbn)) {
            throw new LivroInexistenteException(isbn);
        }
        acervo.remove(isbn);
    }

    @Override
    public void salvarDados() throws IOException {
        gravador.gravar(acervo);
    }

    @Override
    public void recuperarDados() throws IOException {
        acervo = gravador.recuperar();
    }

    public Collection<Livro> listarTodos() {
        return acervo.values();
    }

    public int totalDeLivros() {
        return acervo.size();
    }
}