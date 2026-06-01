package br.com.biblioteca.models;

public class Livro {
    private String isbn;
    private String nome;
    private String autor;

    public Livro(String isbn, String nome, String autor){
        this.isbn = isbn;
        this.nome = nome;
        this.autor = autor;
    }

    public String getIsbn() {return isbn;}
    public void setIsbn(String isbn) {this.isbn = isbn;}

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public String getAutor() {return autor;}
    public void setAutor(String autor) {this.autor = autor;}

    @Override
    public String toString() {
        return "Livro{" +
                "isbn='" + isbn + '\'' +
                ", nome='" + nome + '\'' +
                ", autor='" + autor + '\'' +
                '}';
    }
}
