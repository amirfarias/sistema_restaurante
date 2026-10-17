package com.restaurante;

import java.util.ArrayList;

public class Pedido {
    private int numero;
    private String status;
    private Mesa mesa;
    private ArrayList<ItemPedido> listaItemPedidos;

    public Pedido(int numero, String status, Mesa mesa) {
        this.numero = numero;
        this.status = status;
        this.mesa = mesa;
        this.listaItemPedidos = new ArrayList<>();

        if (this.mesa != null) {
            this.mesa.ocupar();
            this.mesa.setPedidoAtual(this);
        }
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public ArrayList<ItemPedido> getListaItemPedidos() {
        return listaItemPedidos;
    }

    public void adicionarItem(ItemPedido item) {
        if (this.status.equalsIgnoreCase("Pago") || this.status.equalsIgnoreCase("Finalizado")) {
            System.out.println("Pedido já finalizado. Não é possível adicionar itens.");
            return;
        }

        if (item.getPrato() != null && !item.getPrato().isDisponivel()) {
            System.out.println("Prato " + item.getPrato().getNome() + " não está disponível.");
            return;
        }

        this.listaItemPedidos.add(item);
        if (item.getPrato() != null) {
            item.getPrato().adicionarQuantidadePedida(item.getQuantidade());
        }
        System.out.println("Item adicionado com sucesso!");
    }

    public void removerItem(ItemPedido item) {
        if (this.status.equalsIgnoreCase("Pago") || this.status.equalsIgnoreCase("Finalizado")) {
            System.out.println("Não é possível remover itens de um pedido finalizado.");
            return;
        }

        this.listaItemPedidos.remove(item);
        System.out.println("Item removido!");
    }

    public double calcularTotalPedido() {
        double total = 0.0;
        for (ItemPedido item : listaItemPedidos) {
            total += item.calcularSubTotal();
        }
        return total;
    }
}