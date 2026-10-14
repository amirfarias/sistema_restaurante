package com.restaurante;
import java.util.ArrayList;

public class Garcom {
    private String nome;
    private String matricula;

    ArrayList<Pedido> listaPedido = new ArrayList<>();
    
    public Garcom(String nome, String matricula) {
        this.nome = nome;
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
