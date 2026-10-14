package com.restaurante;
import java.util.ArrayList;

public class Pedido {
    private int numero;
    private String status;
    private Mesa mesa;

    ArrayList<ItemPedido> listaItemPedidos = new ArrayList<>();

    public Pedido(int numero, String status, Mesa mesa) {
        this.numero = numero;
        this.status = status;
        this.mesa = mesa;

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

    public void adicionarItem(ItemPedido item) {
        if (this.status.equalsIgnoreCase("Pago") || this.status.equalsIgnoreCase("Finalizado")) {
            System.out.println("Pedido já realizado.");
            return; 
        }
    
        if (item.getPrato() != null && !item.getPrato().isDisponivel()) {
            System.out.println("Prato escolhido não está disponível no momento.");
            return;
        }

        this.listaItemPedidos.add(item);
    }

    public void removerItem(ItemPedido item) {
    if (this.status.equalsIgnoreCase("Pago") || this.status.equalsIgnoreCase("Finalizado")) {
        System.out.println("Não é possível remover itens. O pedido já foi finalizado.");
        return;
    }

    this.listaItemPedidos.remove(item);
    }

    public double calcularTotalPedido() {
        double total = 0;
        for (int i = 0; i < listaItemPedidos.size(); i++) {
            ItemPedido item = listaItemPedidos.get(i);
        
            total += item.calcularSubTotal();
        }
    
        return total;
    }
}