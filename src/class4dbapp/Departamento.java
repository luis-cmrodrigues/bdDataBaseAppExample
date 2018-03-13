/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package class4dbapp;

/**
 *
 * @author luis_
 */
public class Departamento {
    private int num;
    private String nome;
    private String local;

    public Departamento(int num, String nome, String local) {
        this.num = num;
        this.nome = nome;
        this.local = local;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    @Override
    public String toString() {
        return "Departamento{" + "num=" + num + ", nome=" + nome + ", local=" + local + '}';
    }
    
    
}
