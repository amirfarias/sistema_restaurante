
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
        if (this.prato == null) {
            return 0.0;
        }
        return this.quantidade * this.prato.getPreco();
    }
}