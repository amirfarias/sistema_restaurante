package com.restaurante;

public class Prato {
    private String nome;
    private double preco;
    private boolean disponivel;
    private int quantidadePedida;

    public Prato(String nome, double preco, boolean disponivel) {
        this.nome = nome;
        this.preco = preco;
        this.disponivel = disponivel;
        this.quantidadePedida = 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public int getQuantidadePedida() {
        return quantidadePedida;
    }

    public void adicionarQuantidadePedida(int qtd) {
        this.quantidadePedida += qtd;
    }
}