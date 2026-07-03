# Biblioteca - Sistema de Gerenciamento de Livraria

Projeto para disciplina de POO <br>04/05/2026

## Descrição

Mini sistema desenvolvido em Java para gerenciar o acervo de uma livraria/biblioteca.
Permite **cadastrar**, **pesquisar por autor**, **remover** e **persistir em arquivo**
os livros do acervo, através de uma interface gráfica simples construída com Swing.

## Funcionalidades

- Cadastro de livros (ISBN, título, autor, preço)
- Pesquisa de livros por autor
- Remoção de livros por ISBN, com tratamento de exceção quando o livro não existe
- Persistência dos dados em arquivo, via serialização de objetos
  (`ObjectOutputStream` / `ObjectInputStream`)
- Interface gráfica (Swing / `JFrame`) com menu de operações