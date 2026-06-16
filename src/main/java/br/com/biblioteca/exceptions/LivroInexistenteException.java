package br.com.biblioteca.exceptions;

public class LivroInexistenteException extends Exception {

    public LivroInexistenteException(String isbn) {
        super("Livro com ISBN '" + isbn + "' não encontrado.");
    }
}
