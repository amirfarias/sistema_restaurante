
package com.restaurante;
public class Pagamento {
    private String formaPagamento;
    private double valorFinal;
    private Pedido pedido;

    public Pagamento(String formaPagamento, Pedido pedido) {
        this.formaPagamento = formaPagamento;
        this.pedido = pedido;
        this.valorFinal = processarPagamento();
    }

    public double processarPagamento() {
        if (this.pedido == null) {
            return 0.0;
        }
        double subtotal = this.pedido.calcularTotalPedido();
        double taxaServico = subtotal * 0.10;
        double total = subtotal + taxaServico;

        if (subtotal > 150.00) {
            total = total * 0.90;
        }

        this.pedido.setStatus("Pago");
        if (this.pedido.getMesa() != null) {
            this.pedido.getMesa().liberar();
        }
        return total;
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