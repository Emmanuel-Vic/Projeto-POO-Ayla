package br.com.biblioteca;

import br.com.biblioteca.exceptions.LivroInexistenteException;
import br.com.biblioteca.models.Livro;
import br.com.biblioteca.service.Livraria;

import java.util.Collection;

public class SistemaLivrariaTest {

    public static void main(String[] args) {
        Livraria sistema = new Livraria("dados.dat");

        // Cadastro
        sistema.cadastrarLivro("111", "Dom Casmurro", "Machado de Assis", 32.90);
        sistema.cadastrarLivro("222", "Memórias Póstumas", "Machado de Assis", 28.00);
        System.out.println("Total cadastrados: " + sistema.totalDeLivros());

        // Pesquisa
        Collection<Livro> encontrados = sistema.pesquisarPorAutor("Machado de Assis");
        System.out.println("Livros encontrados: " + encontrados.size());

        // Remoção
        try {
            sistema.removerLivro("111");
            System.out.println("Após remoção: " + sistema.totalDeLivros());
            sistema.removerLivro("ISBN-INVALIDO");
        } catch (LivroInexistenteException e) {
            System.out.println("Exceção capturada: " + e.getMessage());
        }
    }
}