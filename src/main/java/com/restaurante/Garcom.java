package com.restaurante;

import java.util.ArrayList;

public class Garcom {
    private String nome;
    private String matricula;
    private ArrayList<Pedido> listaPedido;

    public Garcom(String nome, String matricula) {
        this.nome = nome;
        this.matricula = matricula;
        this.listaPedido = new ArrayList<>();
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

    public ArrayList<Pedido> getListaPedido() {
        return listaPedido;
    }

    public void adicionarPedido(Pedido pedido) {
        this.listaPedido.add(pedido);
    }

    // Retorna a quantidade de mesas/pedidos atualmente em aberto para este garçom
    public int getQuantidadeMesasAtivas() {
        int ativas = 0;
        for (Pedido p : listaPedido) {
            if (p.getStatus().equalsIgnoreCase("Aberto")) {
                ativas++;
            }
        }
        return ativas;
    }

    public double calcularComissao() {
        double totalVendas = 0.0;
        for (Pedido p : listaPedido) {
            if (p.getStatus().equalsIgnoreCase("Pago")) {
                totalVendas += p.calcularTotalPedido();
            }
        }
        return totalVendas * 0.10;
    }
}