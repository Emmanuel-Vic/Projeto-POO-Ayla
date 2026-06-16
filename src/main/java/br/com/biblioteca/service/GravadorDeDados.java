package br.com.biblioteca.service;
import br.com.biblioteca.models.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class GravadorDeDados {

    private final String caminhoArquivo;


    public GravadorDeDados(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public void gravar(Map<String, Livro> dados) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(caminhoArquivo))) {
            oos.writeObject(dados);
        }
    }

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