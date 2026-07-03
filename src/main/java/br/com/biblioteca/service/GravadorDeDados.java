package br.com.biblioteca.service;
import br.com.biblioteca.models.*;

import java.io.*;
import java.util.Map;


/**
 * Responsável por gravar e recuperar o mapa de livros em arquivo
 * usando serialização de objetos (ObjectOutputStream / ObjectInputStream).
 */
public class GravadorDeDados {

    private final String caminhoArquivo;

    /**
     * @param caminhoArquivo caminho do arquivo onde os dados serão salvos
     */
    public GravadorDeDados(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    /**
     * Grava o mapa de livros no arquivo.
     *
     * @param dados mapa ISBN → Livro a ser salvo
     * @throws IOException se ocorrer erro de escrita
     */
    public void gravar(Map<String, Livro> dados) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(caminhoArquivo))) {
            oos.writeObject(dados);
        }
    }

    /**
     * Recupera o mapa de livros do arquivo.
     *
     * @return mapa ISBN → Livro recuperado do arquivo
     * @throws IOException se ocorrer erro de leitura ou o arquivo não existir
     */
    @SuppressWarnings("unchecked")
    public Map<String, Livro> recuperar() throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(caminhoArquivo))) {
            return (Map<String, Livro>) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Erro ao desserializar os dados: " + e.getMessage());
        }
    }
}
