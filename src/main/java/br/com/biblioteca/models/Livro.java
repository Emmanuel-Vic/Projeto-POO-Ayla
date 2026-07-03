package br.com.biblioteca.models;

import java.io.Serializable;

public class Livro implements Serializable {

    private String isbn;
    private String titulo;
    private String autor;
    private double preco;

    public Livro(String isbn, String titulo, String autor, double preco) {
        this.isbn   = isbn;
        this.titulo = titulo;
        this.autor  = autor;
        this.preco  = preco;
    }

    public String getIsbn()   { return isbn; }
    public String getTitulo() { return titulo; }
    public String getAutor()  { return autor; }
    public double getPreco()  { return preco; }

    @Override
    public String toString() {
        return "Livro: "+titulo+", ISBN: "+isbn+", Autor: "+autor+", Preço: R$"+preco;
    }
}