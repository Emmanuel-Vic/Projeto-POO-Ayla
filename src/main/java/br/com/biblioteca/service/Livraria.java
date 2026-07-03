package br.com.biblioteca.service;

import br.com.biblioteca.exceptions.LivroInexistenteException;
import br.com.biblioteca.models.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementação de {@link SistemaLivraria} que mantém o acervo de livros
 * em um {@link Map} (ISBN → {@link Livro}) e delega a persistência dos
 * dados para {@link GravadorDeDados}.
 */
public class Livraria implements SistemaLivraria {

    private Map<String, Livro> acervo;

    private final GravadorDeDados gravador;

    /**
     * @param caminhoArquivo caminho do arquivo usado para salvar/recuperar o acervo
     */
    public Livraria(String caminhoArquivo) {
        this.acervo = new HashMap<>();
        this.gravador = new GravadorDeDados(caminhoArquivo);
    }

    @Override
    public void cadastrarLivro(String isbn, String titulo, String autor, double preco) {
        Livro livro = new Livro(isbn, titulo, autor, preco);
        acervo.put(isbn, livro);
    }

    @Override
    public Collection<Livro> pesquisarPorAutor(String autor) {
        Collection<Livro> resultado = new ArrayList<>();
        for (Livro livro : acervo.values()) {
            if (livro.getAutor().equalsIgnoreCase(autor)) {
                resultado.add(livro);
            }
        }
        return resultado;
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

    /**
     * @return todos os livros atualmente cadastrados no acervo
     */
    public Collection<Livro> listarTodos() {
        return acervo.values();
    }

    /**
     * @return a quantidade total de livros cadastrados
     */
    public int totalDeLivros() {
        return acervo.size();
    }

    /**
     * @param isbn ISBN do livro procurado
     * @return o livro correspondente, ou {@code null} se não existir
     */
    public Livro buscarPorIsbn(String isbn) {
        return acervo.get(isbn);
    }
}
