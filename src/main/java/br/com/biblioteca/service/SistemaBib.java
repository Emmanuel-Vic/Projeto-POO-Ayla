package br.com.biblioteca.service;

import br.com.biblioteca.models.Livro;

import java.util.ArrayList;

public class SistemaBib implements InterfaceBib{

    private ArrayList<Livro> listaLivros;

    public SistemaBib(){
        this.listaLivros = new ArrayList<>();
    }
}
