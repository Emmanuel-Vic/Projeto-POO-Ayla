package br.com.biblioteca.view;

import br.com.biblioteca.exceptions.LivroInexistenteException;
import br.com.biblioteca.models.Livro;
import br.com.biblioteca.service.Livraria;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.IOException;
import java.util.Collection;

public class LivrariaGUI extends JFrame {

    /* ── Paleta ─────────────────────────────────────────── */
    private static final Color BG        = new Color(0xF7F4EF);
    private static final Color SURFACE   = Color.WHITE;
    private static final Color ACCENT    = new Color(0x2D5016);   // verde-floresta
    private static final Color ACCENT2   = new Color(0x6B8F3A);   // verde claro
    private static final Color DANGER    = new Color(0xB33A3A);
    private static final Color TEXT      = new Color(0x1A1A1A);
    private static final Color TEXT_MUTED= new Color(0x6B6B6B);
    private static final Color BORDER    = new Color(0xDDDAD4);

    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font FONT_LABEL = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_BOLD  = new Font("Segoe UI", Font.BOLD,  13);
    private static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 11);

    /* ── Estado ─────────────────────────────────────────── */
    private final Livraria livraria = new Livraria("dados.dat");

    /* ── Campos do formulário ───────────────────────────── */
    private JTextField tfIsbn, tfTitulo, tfAutor, tfPreco, tfBusca;

    /* ── Tabela ─────────────────────────────────────────── */
    private DefaultTableModel tableModel;
    private JTable tabela;

    /* ── Status bar ─────────────────────────────────────── */
    private JLabel lblStatus;

    // ────────────────────────────────────────────────────────────────────────────
    public LivrariaGUI() {
        setTitle("Biblioteca");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 620);
        setMinimumSize(new Dimension(750, 500));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout(0, 0));

        add(buildHeader(),     BorderLayout.NORTH);
        add(buildCenter(),     BorderLayout.CENTER);
        add(buildStatusBar(),  BorderLayout.SOUTH);

        tentarRecuperarDados();
        atualizarTabela();
    }

    // ── Header ───────────────────────────────────────────────────────────────
    private JPanel buildHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(ACCENT);
        p.setBorder(new EmptyBorder(18, 24, 18, 24));

        JLabel titulo = new JLabel("📚  Biblioteca");
        titulo.setFont(FONT_TITLE);
        titulo.setForeground(Color.WHITE);
        p.add(titulo, BorderLayout.WEST);

        JPanel acoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        acoes.setOpaque(false);

        JButton btnSalvar    = buildBtn("Salvar",    ACCENT2,     Color.WHITE);
        JButton btnRecuperar = buildBtn("Carregar",  new Color(0x3A7DC9), Color.WHITE);

        btnSalvar.addActionListener(e -> salvarDados());
        btnRecuperar.addActionListener(e -> carregarDados());

        acoes.add(btnRecuperar);
        acoes.add(btnSalvar);
        p.add(acoes, BorderLayout.EAST);
        return p;
    }

    // ── Centro: formulário + tabela ───────────────────────────────────────────
    private JSplitPane buildCenter() {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                buildFormPanel(), buildTablePanel());
        split.setDividerLocation(310);
        split.setDividerSize(1);
        split.setBorder(null);
        split.setBackground(BORDER);
        return split;
    }

    // ── Formulário ───────────────────────────────────────────────────────────
    private JPanel buildFormPanel() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(SURFACE);
        outer.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, BORDER));

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(SURFACE);
        p.setBorder(new EmptyBorder(20, 20, 20, 20));

        p.add(sectionLabel("Cadastrar livro"));
        p.add(Box.createVerticalStrut(12));

        tfIsbn   = addField(p, "ISBN");
        tfTitulo = addField(p, "Título");
        tfAutor  = addField(p, "Autor");
        tfPreco  = addField(p, "Preço (R$)");

        p.add(Box.createVerticalStrut(14));

        JButton btnCadastrar = buildBtn("Cadastrar", ACCENT, Color.WHITE);
        btnCadastrar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnCadastrar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        btnCadastrar.addActionListener(e -> cadastrarLivro());
        p.add(btnCadastrar);

        p.add(Box.createVerticalStrut(28));
        p.add(separator());
        p.add(Box.createVerticalStrut(20));

        p.add(sectionLabel("Remover livro"));
        p.add(Box.createVerticalStrut(12));

        JPanel removeRow = new JPanel(new BorderLayout(6, 0));
        removeRow.setOpaque(false);
        removeRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        removeRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        JTextField tfRemove = new JTextField();
        styleField(tfRemove);
        JButton btnRemover = buildBtn("Remover", DANGER, Color.WHITE);
        btnRemover.setPreferredSize(new Dimension(80, 34));
        removeRow.add(tfRemove,  BorderLayout.CENTER);
        removeRow.add(btnRemover, BorderLayout.EAST);
        p.add(new FieldWrapper("ISBN", removeRow));

        btnRemover.addActionListener(e -> removerLivro(tfRemove.getText().trim()));

        p.add(Box.createVerticalStrut(28));
        p.add(separator());
        p.add(Box.createVerticalStrut(20));

        p.add(sectionLabel("Buscar por autor"));
        p.add(Box.createVerticalStrut(12));

        JPanel buscaRow = new JPanel(new BorderLayout(6, 0));
        buscaRow.setOpaque(false);
        buscaRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        buscaRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        tfBusca = new JTextField();
        styleField(tfBusca);
        JButton btnBuscar = buildBtn("Buscar", new Color(0x3A7DC9), Color.WHITE);
        btnBuscar.setPreferredSize(new Dimension(70, 34));
        buscaRow.add(tfBusca,  BorderLayout.CENTER);
        buscaRow.add(btnBuscar, BorderLayout.EAST);
        p.add(new FieldWrapper("Autor", buscaRow));

        JButton btnLimpar = new JButton("Ver todos");
        btnLimpar.setFont(FONT_SMALL);
        btnLimpar.setForeground(TEXT_MUTED);
        btnLimpar.setBorderPainted(false);
        btnLimpar.setContentAreaFilled(false);
        btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLimpar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnLimpar.addActionListener(e -> { atualizarTabela(); status("Mostrando todos os livros."); });
        p.add(Box.createVerticalStrut(4));
        p.add(btnLimpar);

        btnBuscar.addActionListener(e -> buscarPorAutor(tfBusca.getText().trim()));

        outer.add(new JScrollPane(p,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
        return outer;
    }

    // ── Tabela ───────────────────────────────────────────────────────────────
    private JPanel buildTablePanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(SURFACE);

        // Cabeçalho da tabela
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG);
        header.setBorder(new EmptyBorder(12, 16, 12, 16));
        JLabel lbl = new JLabel("Acervo");
        lbl.setFont(FONT_BOLD);
        lbl.setForeground(TEXT);
        header.add(lbl, BorderLayout.WEST);
        p.add(header, BorderLayout.NORTH);

        String[] colunas = {"ISBN", "Título", "Autor", "Preço (R$)"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabela = new JTable(tableModel);
        tabela.setFont(FONT_LABEL);
        tabela.setRowHeight(32);
        tabela.setShowGrid(false);
        tabela.setIntercellSpacing(new Dimension(0, 0));
        tabela.setBackground(SURFACE);
        tabela.setSelectionBackground(new Color(0xE8F0DA));
        tabela.setSelectionForeground(TEXT);
        tabela.getTableHeader().setFont(FONT_BOLD);
        tabela.getTableHeader().setBackground(BG);
        tabela.getTableHeader().setForeground(TEXT_MUTED);
        tabela.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER));

        // Zebra
        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v,
                                                           boolean sel, boolean focus, int row, int col) {
                super.getTableCellRendererComponent(t, v, sel, focus, row, col);
                setBorder(new EmptyBorder(0, 12, 0, 12));
                if (!sel) setBackground(row % 2 == 0 ? SURFACE : new Color(0xFBFAF7));
                return this;
            }
        });

        // Larguras
        tabela.getColumnModel().getColumn(0).setPreferredWidth(110);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(150);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(90);

        p.add(new JScrollPane(tabela), BorderLayout.CENTER);
        return p;
    }

    // ── Status bar ───────────────────────────────────────────────────────────
    private JPanel buildStatusBar() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 6));
        p.setBackground(new Color(0xECE9E2));
        p.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER));
        lblStatus = new JLabel("Pronto.");
        lblStatus.setFont(FONT_SMALL);
        lblStatus.setForeground(TEXT_MUTED);
        p.add(lblStatus);
        return p;
    }

    // ── Lógica de negócio ────────────────────────────────────────────────────
    private void cadastrarLivro() {
        String isbn   = tfIsbn.getText().trim();
        String titulo = tfTitulo.getText().trim();
        String autor  = tfAutor.getText().trim();
        String precoTxt = tfPreco.getText().trim().replace(",", ".");

        if (isbn.isEmpty() || titulo.isEmpty() || autor.isEmpty() || precoTxt.isEmpty()) {
            status("⚠  Preencha todos os campos antes de cadastrar.");
            return;
        }
        double preco;
        try {
            preco = Double.parseDouble(precoTxt);
        } catch (NumberFormatException ex) {
            status("⚠  Preço inválido — use apenas números (ex: 49.90).");
            return;
        }
        livraria.cadastrarLivro(isbn, titulo, autor, preco);
        atualizarTabela();
        limparFormulario();
        status("✓  \"" + titulo + "\" cadastrado com sucesso. Total: " + livraria.totalDeLivros() + " livro(s).");
    }

    private void removerLivro(String isbn) {
        if (isbn.isEmpty()) { status("⚠  Informe o ISBN do livro a remover."); return; }
        try {
            livraria.removerLivro(isbn);
            atualizarTabela();
            status("✓  Livro com ISBN \"" + isbn + "\" removido.");
        } catch (LivroInexistenteException ex) {
            status("⚠  " + ex.getMessage());
        }
    }

    private void buscarPorAutor(String autor) {
        if (autor.isEmpty()) { status("⚠  Informe o nome do autor."); return; }
        Collection<Livro> resultado = livraria.pesquisarPorAutor(autor);
        tableModel.setRowCount(0);
        for (Livro l : resultado) addRow(l);
        status("Busca por \"" + autor + "\": " + resultado.size() + " resultado(s).");
    }

    private void salvarDados() {
        try {
            livraria.salvarDados();
            status("✓  Dados salvos com sucesso.");
        } catch (IOException ex) {
            status("✗  Erro ao salvar: " + ex.getMessage());
        }
    }

    private void carregarDados() {
        try {
            livraria.recuperarDados();
            atualizarTabela();
            status("✓  Dados carregados. Total: " + livraria.totalDeLivros() + " livro(s).");
        } catch (IOException ex) {
            status("✗  Erro ao carregar: " + ex.getMessage());
        }
    }

    private void tentarRecuperarDados() {
        try { livraria.recuperarDados(); } catch (IOException ignored) { /* arquivo pode não existir ainda */ }
    }

    // ── Helpers de UI ────────────────────────────────────────────────────────
    private void atualizarTabela() {
        tableModel.setRowCount(0);
        for (Livro l : livraria.listarTodos()) addRow(l);
    }

    private void addRow(Livro l) {
        tableModel.addRow(new Object[]{
                l.getIsbn(), l.getTitulo(), l.getAutor(),
                String.format("%.2f", l.getPreco())
        });
    }

    private void limparFormulario() {
        tfIsbn.setText(""); tfTitulo.setText(""); tfAutor.setText(""); tfPreco.setText("");
    }

    private void status(String msg) { lblStatus.setText(msg); }

    private JTextField addField(JPanel parent, String label) {
        JTextField tf = new JTextField();
        styleField(tf);
        parent.add(new FieldWrapper(label, tf));
        parent.add(Box.createVerticalStrut(10));
        return tf;
    }

    private void styleField(JTextField tf) {
        tf.setFont(FONT_LABEL);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(6, 10, 6, 10)));
        tf.setBackground(SURFACE);
    }

    private JLabel sectionLabel(String text) {
        JLabel l = new JLabel(text.toUpperCase());
        l.setFont(new Font("Segoe UI", Font.BOLD, 10));
        l.setForeground(TEXT_MUTED);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JSeparator separator() {
        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER);
        sep.setAlignmentX(Component.LEFT_ALIGNMENT);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        return sep;
    }

    private JButton buildBtn(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BOLD);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(8, 16, 8, 16));
        return btn;
    }

    // ── Wrapper label + campo ─────────────────────────────────────────────────
    private static class FieldWrapper extends JPanel {
        FieldWrapper(String label, JComponent field) {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setOpaque(false);
            setAlignmentX(Component.LEFT_ALIGNMENT);
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));

            JLabel lbl = new JLabel(label);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lbl.setForeground(new Color(0x3A3A3A));
            lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            add(lbl);
            add(Box.createVerticalStrut(4));

            field.setAlignmentX(Component.LEFT_ALIGNMENT);
            field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
            add(field);
        }
    }

    // ── Entry point ──────────────────────────────────────────────────────────
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) {}
            new LivrariaGUI().setVisible(true);
        });
    }
}
