package br.com.biblioteca.view;

import br.com.biblioteca.controller.LivrariaController;
import br.com.biblioteca.exceptions.LivroInexistenteException;
import br.com.biblioteca.models.Livro;
import br.com.biblioteca.service.Livraria;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Collection;

/**
 * Tela principal do sistema de livraria.
 * <p>
 * Disponibiliza um menu com as operações de Cadastrar, Pesquisar, Apagar
 * e Salvar, delegando cada ação para {@link LivrariaController}.
 */
public class FrontEnd extends JFrame {

    private final LivrariaController controller;
    private final JTextArea areaResultados;

    public FrontEnd() {
        super("Sistema de Livraria");

        controller = new LivrariaController(new Livraria("dados.txt"));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        add(new JScrollPane(areaResultados), BorderLayout.CENTER);

        add(criarPainelBotoes(), BorderLayout.SOUTH);
        setJMenuBar(criarMenu());
    }

    private JMenuBar criarMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuOperacoes = new JMenu("Operações");

        JMenuItem itemCadastrar = new JMenuItem("Cadastrar...");
        itemCadastrar.addActionListener(e -> cadastrarLivro());

        JMenuItem itemPesquisar = new JMenuItem("Pesquisar...");
        itemPesquisar.addActionListener(e -> pesquisarLivro());

        JMenuItem itemApagar = new JMenuItem("Apagar...");
        itemApagar.addActionListener(e -> apagarLivro());

        JMenuItem itemSalvar = new JMenuItem("Salvar");
        itemSalvar.addActionListener(e -> salvarDados());

        JMenuItem itemRecuperar = new JMenuItem("Recuperar dados salvos");
        itemRecuperar.addActionListener(e -> recuperarDados());

        menuOperacoes.add(itemCadastrar);
        menuOperacoes.add(itemPesquisar);
        menuOperacoes.add(itemApagar);
        menuOperacoes.addSeparator();
        menuOperacoes.add(itemSalvar);
        menuOperacoes.add(itemRecuperar);

        menuBar.add(menuOperacoes);
        return menuBar;
    }

    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel();

        JButton botaoCadastrar = new JButton("Cadastrar...");
        botaoCadastrar.addActionListener(e -> cadastrarLivro());

        JButton botaoPesquisar = new JButton("Pesquisar...");
        botaoPesquisar.addActionListener(e -> pesquisarLivro());

        JButton botaoApagar = new JButton("Apagar...");
        botaoApagar.addActionListener(e -> apagarLivro());

        JButton botaoSalvar = new JButton("Salvar");
        botaoSalvar.addActionListener(e -> salvarDados());

        painel.add(botaoCadastrar);
        painel.add(botaoPesquisar);
        painel.add(botaoApagar);
        painel.add(botaoSalvar);

        return painel;
    }

    private void cadastrarLivro() {
        JTextField campoIsbn = new JTextField();
        JTextField campoTitulo = new JTextField();
        JTextField campoAutor = new JTextField();
        JTextField campoPreco = new JTextField();

        Object[] mensagem = {
                "ISBN:", campoIsbn,
                "Título:", campoTitulo,
                "Autor:", campoAutor,
                "Preço:", campoPreco
        };

        int opcao = JOptionPane.showConfirmDialog(this, mensagem,
                "Cadastrar Livro", JOptionPane.OK_CANCEL_OPTION);

        if (opcao == JOptionPane.OK_OPTION) {
            try {
                double preco = Double.parseDouble(campoPreco.getText().replace(",", "."));
                controller.cadastrar(campoIsbn.getText(), campoTitulo.getText(),
                        campoAutor.getText(), preco);
                areaResultados.setText("Livro cadastrado com sucesso!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Preço inválido.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void pesquisarLivro() {
        String autor = JOptionPane.showInputDialog(this, "Nome do autor:");
        if (autor == null) {
            return;
        }

        Collection<Livro> livros = controller.pesquisarPorAutor(autor);
        if (livros.isEmpty()) {
            areaResultados.setText("Nenhum livro encontrado para o autor: " + autor);
        } else {
            StringBuilder sb = new StringBuilder();
            for (Livro livro : livros) {
                sb.append(livro).append("\n");
            }
            areaResultados.setText(sb.toString());
        }
    }

    private void apagarLivro() {
        String isbn = JOptionPane.showInputDialog(this, "ISBN do livro a apagar:");
        if (isbn == null) {
            return;
        }

        try {
            controller.remover(isbn);
            areaResultados.setText("Livro removido com sucesso!");
        } catch (LivroInexistenteException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarDados() {
        try {
            controller.salvar();
            areaResultados.setText("Dados salvos com sucesso!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar dados: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void recuperarDados() {
        try {
            controller.recuperar();
            areaResultados.setText("Dados recuperados com sucesso!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao recuperar dados: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
