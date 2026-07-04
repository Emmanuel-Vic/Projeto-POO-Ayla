package br.com.biblioteca;

import br.com.biblioteca.exceptions.LivroInexistenteException;
import br.com.biblioteca.models.Livro;
import br.com.biblioteca.service.Livraria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class LivrariaTest {

    private Livraria livraria;

    @BeforeEach
    void setUp() {
        livraria = new Livraria("dados_teste.txt");
        livraria.cadastrarLivro("111", "Dom Casmurro", "Machado de Assis", 39.90);
        livraria.cadastrarLivro("222", "Memórias Póstumas", "Machado de Assis", 45.00);
        livraria.cadastrarLivro("333", "O Cortiço", "Aluísio Azevedo", 29.90);
    }

    @Test
    void deveCadastrarLivroComSucesso() {
        assertEquals(3, livraria.totalDeLivros());

        Livro livro = livraria.buscarPorIsbn("111");
        assertNotNull(livro);
        assertEquals("Dom Casmurro", livro.getTitulo());
        assertEquals("Machado de Assis", livro.getAutor());
    }

    @Test
    void deveCadastrarEEncontrarLivroPesquisandoPorAutor() {
        Livraria outraLivraria = new Livraria("dados_teste2.txt");

        outraLivraria.cadastrarLivro("978-1", "Clean Code", "Martin", 89.90);

        Collection<Livro> resultado = outraLivraria.pesquisarPorAutor("Martin");

        assertFalse(resultado.isEmpty(), "Deveria encontrar o livro cadastrado");
        assertEquals("Clean Code", resultado.iterator().next().getTitulo());
    }

    @Test
    void devePesquisarPorAutorCorretamente() {
        Collection<Livro> resultado = livraria.pesquisarPorAutor("Machado de Assis");
        assertEquals(2, resultado.size());

        Collection<Livro> semResultado = livraria.pesquisarPorAutor("Autor Inexistente");
        assertTrue(semResultado.isEmpty());
    }

    @Test
    void deveRemoverLivroExistente() throws LivroInexistenteException {
        livraria.removerLivro("333");

        assertEquals(2, livraria.totalDeLivros());
        assertNull(livraria.buscarPorIsbn("333"));
    }

    @Test
    void deveLancarExcecaoAoRemoverLivroInexistente() {
        assertThrows(LivroInexistenteException.class, () -> livraria.removerLivro("999"));
    }
}
