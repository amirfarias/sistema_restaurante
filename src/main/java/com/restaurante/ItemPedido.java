package com.restaurante;

public class ItemPedido {
    private int quantidade;
    private Prato prato;

    public ItemPedido(int quantidade, Prato prato) {
        this.quantidade = quantidade;
        this.prato = prato;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Prato getPrato() {
        return prato;
    }

    public void setPrato(Prato prato) {
        this.prato = prato;
    }
    
    public double calcularSubTotal() {
        double total = 0;
        total = this.quantidade * prato.getPreco();
        return total;
    }
}