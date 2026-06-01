package br.com.ProvaUnidade1.models;

import java.util.Objects;

public class Funcionario {
    private String cpf;
    private String nome;
    private TipoFuncionario tipo;
    private double salario;

    public Funcionario(String cpf, String nome, TipoFuncionario tipo, double salario) {
        this.cpf = cpf;
        this.nome = nome;
        this.tipo = tipo;
        this.salario = salario;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoFuncionario getTipo() {
        return tipo;
    }

    public void setTipo(TipoFuncionario tipo) {
        this.tipo = tipo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Funcionario that = (Funcionario) o;
        return Double.compare(salario, that.salario) == 0 && Objects.equals(cpf, that.cpf) && Objects.equals(nome, that.nome) && tipo == that.tipo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf, nome, tipo, salario);
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", tipo=" + tipo +
                ", salario=" + salario +
                '}';
    }
}
