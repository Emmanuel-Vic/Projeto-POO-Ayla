package br.com.biblioteca.service;

import br.com.biblioteca.exceptions.LivroInexistenteException;
import br.com.biblioteca.models.*;

import java.io.IOException;
import java.util.Collection;

/**
 * Interface que define as operações principais de um sistema de gerenciamento
 * de livraria/biblioteca.
 * <p>
 * Reúne as funcionalidades de cadastro, pesquisa, remoção e persistência de
 * livros que devem ser implementadas por qualquer classe que represente um
 * sistema de livraria concreto (ex.: {@link Livraria}).
 */
public interface SistemaLivraria {

    /**
     * Cadastra um novo livro no acervo.
     *
     * @param isbn   código ISBN do livro (chave única de identificação)
     * @param titulo título do livro
     * @param autor  autor do livro
     * @param preco  preço de venda do livro
     */
    void cadastrarLivro(String isbn, String titulo, String autor, double preco);

    /**
     * Pesquisa todos os livros de um determinado autor.
     *
     * @param autor nome do autor a ser pesquisado
     * @return coleção de livros encontrados (pode ser vazia, nunca nula)
     */
    Collection<Livro> pesquisarPorAutor(String autor);

    /**
     * Remove um livro do acervo a partir do seu ISBN.
     *
     * @param isbn código ISBN do livro a ser removido
     * @throws LivroInexistenteException se não existir livro cadastrado com o ISBN informado
     */
    void removerLivro(String isbn) throws LivroInexistenteException;

    /**
     * Persiste o acervo atual em arquivo, utilizando serialização de objetos.
     *
     * @throws IOException se ocorrer erro durante a gravação
     */
    void salvarDados() throws IOException;

    /**
     * Recupera o acervo salvo anteriormente em arquivo.
     *
     * @throws IOException se ocorrer erro durante a leitura
     */
    void recuperarDados() throws IOException;
}
