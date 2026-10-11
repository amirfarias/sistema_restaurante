package com.restaurante;

public class Pagamento {
    private String formaPagamento;
    private double valorFinal;
    private Pedido pedido;

    public Pagamento(String formaPagamento, double valorFinal, Pedido pedido) {
        this.formaPagamento = formaPagamento;
        this.valorFinal = valorFinal;
        this.pedido = pedido;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public double getValorFinal() {
        return valorFinal;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public void setValorFinal(double valorFinal) {
        this.valorFinal = valorFinal;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

}
