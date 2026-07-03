package br.com.biblioteca;

import br.com.biblioteca.exceptions.LivroInexistenteException;
import br.com.biblioteca.models.Livro;
import br.com.biblioteca.service.Livraria;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class LivrariaTest {

    private Livraria livraria;
    private static final String ARQUIVO_TESTE = "dados_teste.dat";

    @BeforeEach
    void setUp() {
        livraria = new Livraria(ARQUIVO_TESTE);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(ARQUIVO_TESTE));
    }

    @Test
    void deveCadastrarLivroEEncontrarNoAcervo() {
        livraria.cadastrarLivro("978-1", "Clean Code", "Martin", 89.90);

        Collection<Livro> resultado = livraria.pesquisarPorAutor("Martin");

        assertFalse(resultado.isEmpty(), "Deveria encontrar o livro cadastrado");
        assertEquals("Clean Code", resultado.iterator().next().getTitulo());
    }

    @Test
    void deveRemoverLivroExistente() throws LivroInexistenteException {
        livraria.cadastrarLivro("978-2", "Sapiens", "Harari", 49.90);

        livraria.removerLivro("978-2");

        assertEquals(0, livraria.totalDeLivros(), "Acervo deveria estar vazio após remoção");
    }

    @Test
    void deveLancarExcecaoAoRemoverLivroInexistente() {
        assertThrows(LivroInexistenteException.class,
                () -> livraria.removerLivro("isbn-invalido"),
                "Deveria lançar exceção para ISBN inexistente");
    }

    @Test
    void deveSalvarERecuperarDados() throws IOException {
        livraria.cadastrarLivro("978-3", "O Hobbit", "Tolkien", 39.90);
        livraria.salvarDados();

        Livraria novaLivraria = new Livraria(ARQUIVO_TESTE);
        novaLivraria.recuperarDados();

        assertEquals(1, novaLivraria.totalDeLivros(), "Deveria recuperar 1 livro do arquivo");
    }

    @Test
    void devePesquisarPorAutorRetornarVazioSeNaoEncontrado() {
        Collection<Livro> resultado = livraria.pesquisarPorAutor("Autor Inexistente");

        assertTrue(resultado.isEmpty(), "Deveria retornar coleção vazia");
    }
}