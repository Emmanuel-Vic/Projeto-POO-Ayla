package br.com.ProvaUnidade1.service;

import br.com.ProvaUnidade1.exceptions.FuncionarioInexistenteException;
import br.com.ProvaUnidade1.exceptions.FuncionarioJaExisteException;
import br.com.ProvaUnidade1.models.Funcionario;
import br.com.ProvaUnidade1.models.TipoFuncionario;

import java.util.HashMap;
import java.util.Map;

public class SistemaFuncionariosBomPrato implements SistemaFuncionarios{
    private Map<String, Funcionario> funcionarios;


    public SistemaFuncionariosBomPrato() {
        this.funcionarios = new HashMap<String, Funcionario>();
    }

    @Override
    public void cadastrarFuncionario(String cpf, String nome, TipoFuncionario tipo, double salario) throws FuncionarioJaExisteException {
        if (this.funcionarios.containsKey(cpf)) {
            throw new FuncionarioJaExisteException(
                    "Já existe funcionário com o cpf " + cpf);
        } else {
            this.funcionarios.put(cpf, new Funcionario(cpf, nome, tipo,
                    salario));
        }
    }

    @Override
    public void cadastrarFuncionario(Funcionario funcionario) throws FuncionarioJaExisteException{

    }

    @Override
    public void alterarSalarioDeFuncionario(String cpfFuncionario, double novoSalario) throws FuncionarioInexistenteException {
        boolean conf = false;
        for (Funcionario f: funcionarios.values()){
            if (f.getCpf().equals(cpfFuncionario)){
                f.setSalario(novoSalario);
                conf = true;
            }
        if (conf==false){
            throw new FuncionarioInexistenteException("Usuario inexistente");
        }
    }

}
