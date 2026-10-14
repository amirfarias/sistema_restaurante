package com.restaurante;

public class Mesa {

    private int numero;
    private boolean ocupada;
    private Pedido pedidoAtual;

    public Mesa(int numero) {
        this.numero = numero;
        this.ocupada = false; 
        this.pedidoAtual = null;
    }

    public int getNumero() {
        return numero;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void ocupar() {
        this.ocupada = true;
    }

    public void liberar() {
        this.ocupada = false;
    }

    public Pedido getPedidoAtual() {
        return pedidoAtual;
    }

    public void setPedidoAtual(Pedido pedidoAtual) {
        this.pedidoAtual = pedidoAtual;
    }

}