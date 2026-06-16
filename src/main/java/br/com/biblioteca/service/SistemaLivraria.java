package br.com.biblioteca.service;

import br.com.biblioteca.exceptions.LivroInexistenteException;
import br.com.biblioteca.models.*;

import java.io.IOException;
import java.util.Collection;

/**
 * Define as operações principais do sistema de livraria.
 *
 * <p>Permite cadastrar, pesquisar e remover livros do acervo,
 * além de persistir e recuperar os dados em arquivo.</p>
 *
 * @author seu nome
 * @version 1.0
 */
public interface SistemaLivraria {

    /**
     * Cadastra um novo livro no acervo.
     *
     * @param isbn   identificador único do livro
     * @param titulo título do livro
     * @param autor  nome do autor
     * @param preco  preço do livro em reais
     */
    public void cadastrarLivro(String isbn, String titulo, String autor, double preco);

    /**
     * Pesquisa todos os livros de um determinado autor.
     *
     * @param autor nome do autor a ser pesquisado
     * @return coleção de livros encontrados, vazia se nenhum for encontrado
     */
    Collection<Livro> pesquisarPorAutor(String autor);

    /**
     * Remove um livro do acervo pelo ISBN.
     *
     * @param isbn identificador único do livro a ser removido
     * @throws LivroInexistenteException se o ISBN não estiver cadastrado
     */
    public void removerLivro(String isbn) throws LivroInexistenteException;

    /**
     * Salva os dados do acervo em arquivo.
     *
     * @throws IOException se ocorrer erro ao gravar o arquivo
     */
    void salvarDados() throws IOException;

    /**
     * Recupera os dados do acervo a partir do arquivo.
     *
     * @throws IOException se ocorrer erro ao ler o arquivo
     */
    void recuperarDados() throws IOException;
}