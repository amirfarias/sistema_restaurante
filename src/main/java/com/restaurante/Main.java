package com.restaurante;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Mesa> mesas = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            mesas.add(new Mesa(i));
        }

        ArrayList<Prato> cardapio = new ArrayList<>();
        cardapio.add(new Prato("Lasanha", 40.0, true));
        cardapio.add(new Prato("Filé Mignon", 60.0, true));
        cardapio.add(new Prato("Suco Natural", 10.0, true));
        cardapio.add(new Prato("Sobremesa Especial", 25.0, false));

        ArrayList<Garcom> garcons = new ArrayList<>();
        garcons.add(new Garcom("Carlos", "G01"));
        garcons.add(new Garcom("Ana", "G02"));

        ArrayList<Pedido> pedidos = new ArrayList<>();
        ArrayList<Pagamento> pagamentos = new ArrayList<>();

        boolean executando = true;
        int contadorPedido = 1;

        while (executando) {
            exibirMenu();
            
            int opcao = lerOpcaoMenuValida(scanner);

            switch (opcao) {
                case 1:
                    System.out.println("\n--- MESAS DISPONÍVEIS ---");
                    for (Mesa m : mesas) {
                        System.out.println("Mesa " + m.getNumero() + " - " + (m.isOcupada() ? "[OCUPADA]" : "[LIVRE]"));
                    }
                    System.out.print("Digite o número da mesa: ");
                    int numMesa = lerInteiroValido(scanner);

                    Mesa mesaSelecionada = null;
                    for (Mesa m : mesas) {
                        if (m.getNumero() == numMesa) {
                            mesaSelecionada = m;
                            break;
                        }
                    }

                    if (mesaSelecionada == null || mesaSelecionada.isOcupada()) {
                        System.out.println("Mesa inválida ou já ocupada!");
                    } else {
                        boolean algumGarcomDisponivel = false;
                        for (Garcom g : garcons) {
                            if (g.getQuantidadeMesasAtivas() < 2) {
                                algumGarcomDisponivel = true;
                                break;
                            }
                        }

                        if (!algumGarcomDisponivel) {
                            System.out.println("Todos os garçons já estão atendendo o limite máximo de 2 mesas! Encerre uma conta antes de abrir um novo pedido.");
                            break;
                        }

                        Garcom garcomSel = lerGarcomValido(scanner, garcons);

                        Pedido novoPedido = new Pedido(contadorPedido++, "Aberto", mesaSelecionada);
                        garcomSel.adicionarPedido(novoPedido);
                        pedidos.add(novoPedido);
                        System.out.println("\n-> Pedido #" + novoPedido.getNumero() + " aberto com sucesso para a Mesa " + mesaSelecionada.getNumero() + " (Garçom: " + garcomSel.getNome() + ")");

                        boolean adicionandoItens = true;
                        while (adicionandoItens) {
                            System.out.println("\n--- CARDÁPIO ---");
                            for (int i = 0; i < cardapio.size(); i++) {
                                Prato pr = cardapio.get(i);
                                System.out.println((i + 1) + ". " + pr.getNome() + " - R$ " + String.format("%.2f", pr.getPreco()) + " (" + (pr.isDisponivel() ? "Disponível" : "Indisponível") + ")");
                            }
                            System.out.print("Escolha o prato desejado: ");
                            int pOpt = lerInteiroValido(scanner);

                            if (pOpt >= 1 && pOpt <= cardapio.size()) {
                                Prato pratoSel = cardapio.get(pOpt - 1);
                                System.out.print("Quantidade: ");
                                int qtd = lerInteiroValido(scanner);

                                ItemPedido item = new ItemPedido(qtd, pratoSel);
                                novoPedido.adicionarItem(item);

                                do {
                                    System.out.print("\nDeseja adicionar outro prato a este pedido? (s/n): ");
                                    String resp = scanner.nextLine().trim().toLowerCase();

                                    if (resp.startsWith("s")) {
                                        adicionandoItens = true;
                                        break;                                    } 
                                    else if (resp.startsWith("n")) {
                                        adicionandoItens = false;
                                        break;
                                    } 
                                    else {
                                        System.out.println("Resposta inválida, digite (s/n)");
                                    }
                                } while (true);

                            } else {
                                System.out.println("Prato inválido!");
                            }
                        }
                    }
                    break;

                case 2:
                    System.out.println("\n--- PEDIDOS ABERTOS ---");
                    boolean temAberto = false;
                    for (Pedido p : pedidos) {
                        if (p.getStatus().equalsIgnoreCase("Aberto")) {
                            System.out.println("Pedido #" + p.getNumero() + " | Mesa " + p.getMesa().getNumero());
                            temAberto = true;
                        }
                    }

                    if (!temAberto) {
                        System.out.println("Não há pedidos abertos no momento.");
                        break;
                    }

                    System.out.print("Digite o número do pedido: ");
                    int numPed = lerInteiroValido(scanner);

                    Pedido pedSel = null;
                    for (Pedido p : pedidos) {
                        if (p.getNumero() == numPed && p.getStatus().equalsIgnoreCase("Aberto")) {
                            pedSel = p;
                            break;
                        }
                    }

                    if (pedSel == null) {
                        System.out.println("Pedido em aberto não encontrado!");
                    } else {
                        System.out.println("\n--- CARDÁPIO ---");
                        for (int i = 0; i < cardapio.size(); i++) {
                            Prato pr = cardapio.get(i);
                            System.out.println((i + 1) + ". " + pr.getNome() + " - R$ " + String.format("%.2f", pr.getPreco()) + " (" + (pr.isDisponivel() ? "Disponível" : "Indisponível") + ")");
                        }
                        System.out.print("Escolha o prato: ");
                        int pOpt = lerInteiroValido(scanner);

                        if (pOpt >= 1 && pOpt <= cardapio.size()) {
                            Prato pratoSel = cardapio.get(pOpt - 1);
                            System.out.print("Quantidade: ");
                            int qtd = lerInteiroValido(scanner);

                            ItemPedido item = new ItemPedido(qtd, pratoSel);
                            pedSel.adicionarItem(item);
                        } else {
                            System.out.println("Prato inválido!");
                        }
                    }
                    break;

                case 3:
                    System.out.println("\n--- PEDIDOS ABERTOS ---");
                    boolean temAbertoRemov = false;
                    for (Pedido p : pedidos) {
                        if (p.getStatus().equalsIgnoreCase("Aberto")) {
                            System.out.println("Pedido #" + p.getNumero() + " | Mesa " + p.getMesa().getNumero());
                            temAbertoRemov = true;
                        }
                    }

                    if (!temAbertoRemov) {
                        System.out.println("Não há pedidos abertos no momento.");
                        break;
                    }

                    System.out.print("Digite o número do pedido: ");
                    int numPedRemov = lerInteiroValido(scanner);

                    Pedido pedSelRemov = null;
                    for (Pedido p : pedidos) {
                        if (p.getNumero() == numPedRemov && p.getStatus().equalsIgnoreCase("Aberto")) {
                            pedSelRemov = p;
                            break;
                        }
                    }

                    if (pedSelRemov == null || pedSelRemov.getListaItemPedidos().isEmpty()) {
                        System.out.println("Pedido não encontrado ou sem itens!");
                    } else {
                        System.out.println("\nItens no pedido:");
                        for (int i = 0; i < pedSelRemov.getListaItemPedidos().size(); i++) {
                            ItemPedido item = pedSelRemov.getListaItemPedidos().get(i);
                            System.out.println((i + 1) + ". " + item.getPrato().getNome() + " (Qtd: " + item.getQuantidade() + ")");
                        }
                        System.out.print("Escolha o número do item para remover: ");
                        int idx = lerInteiroValido(scanner);
                        if (idx >= 1 && idx <= pedSelRemov.getListaItemPedidos().size()) {
                            pedSelRemov.removerItem(pedSelRemov.getListaItemPedidos().get(idx - 1));
                        } else {
                            System.out.println("Item inválido.");
                        }
                    }
                    break;

                case 4:
                    System.out.println("\n--- CONSULTAR CARDÁPIO ---");
                    System.out.println("1. Pesquisar prato por nome");
                    System.out.println("2. Ordenar cardápio por preço (crescente)");
                    System.out.print("Escolha uma opção: ");
                    int subOpt = lerInteiroValido(scanner);

                    if (subOpt == 1) {
                        System.out.print("Digite o nome ou parte do nome: ");
                        String busca = scanner.nextLine().toLowerCase();
                        boolean achou = false;
                        for (Prato pr : cardapio) {
                            if (pr.getNome().toLowerCase().contains(busca)) {
                                System.out.println("- " + pr.getNome() + " | R$ " + String.format("%.2f", pr.getPreco()) + " [" + (pr.isDisponivel() ? "Disponível" : "Indisponível") + "]");
                                achou = true;
                            }
                        }
                        if (!achou) System.out.println("Nenhum prato encontrado.");
                    } else if (subOpt == 2) {
                        cardapio.sort(Comparator.comparingDouble(Prato::getPreco));
                        System.out.println("\nCardápio ordenado por preço:");
                        for (Prato pr : cardapio) {
                            System.out.println("- " + pr.getNome() + " | R$ " + String.format("%.2f", pr.getPreco()));
                        }
                    } else {
                        System.out.println("Opção inválida!");
                    }
                    break;

                case 5:
                    System.out.println("\n--- MESAS COM PEDIDOS EM ABERTO ---");
                    ArrayList<Pedido> pedidosAbertos = new ArrayList<>();
                    for (Pedido p : pedidos) {
                        if (p.getStatus().equalsIgnoreCase("Aberto")) {
                            pedidosAbertos.add(p);
                        }
                    }

                    if (pedidosAbertos.isEmpty()) {
                        System.out.println("Não existem mesas ou pedidos em aberto para encerramento.");
                        break;
                    }

                    for (int i = 0; i < pedidosAbertos.size(); i++) {
                        Pedido p = pedidosAbertos.get(i);
                        System.out.println((i + 1) + ". Mesa " + p.getMesa().getNumero() + " (Pedido #" + p.getNumero() + ")");
                    }
                    System.out.print("Escolha a mesa para encerrar a conta: ");
                    int mOpt = lerInteiroValido(scanner);

                    if (mOpt >= 1 && mOpt <= pedidosAbertos.size()) {
                        Pedido pedSelPag = pedidosAbertos.get(mOpt - 1);
                        double subtotal = pedSelPag.calcularTotalPedido();
                        double taxaServico = subtotal * 0.10;
                        double valorComTaxa = subtotal + taxaServico;
                        double desconto = 0.0;

                        if (subtotal > 150.00) {
                            desconto = valorComTaxa * 0.10;
                        }

                        double valorFinalCalculado = valorComTaxa - desconto;

                        System.out.println("\n==================================================");
                        System.out.println("            EXTRATO DE CONTA / MESA " + pedSelPag.getMesa().getNumero());
                        System.out.println("            Pedido #" + pedSelPag.getNumero());
                        System.out.println("==================================================");
                        System.out.printf("%-22s %-6s %-10s %-10s\n", "ITEM", "QTD", "PREÇO UN", "SUBTOTAL");
                        System.out.println("--------------------------------------------------");

                        for (ItemPedido item : pedSelPag.getListaItemPedidos()) {
                            System.out.printf("%-22s %-6d R$ %-7.2f R$ %-7.2f\n",
                                    item.getPrato().getNome(),
                                    item.getQuantidade(),
                                    item.getPrato().getPreco(),
                                    item.calcularSubTotal());
                        }

                        System.out.println("--------------------------------------------------");
                        System.out.printf("Subtotal do Consumo:                R$ %.2f\n", subtotal);
                        System.out.printf("Taxa de Serviço (10%%):              R$ %.2f\n", taxaServico);
                        if (desconto > 0.0) {
                            System.out.printf("Desconto Especial (> R$150):       -R$ %.2f\n", desconto);
                        } else {
                            System.out.println("Desconto Especial (> R$150):        R$ 0,00");
                        }
                        System.out.println("==================================================");
                        System.out.printf("VALOR TOTAL A PAGAR:                R$ %.2f\n", valorFinalCalculado);
                        System.out.println("==================================================");

                        String forma = lerFormaPagamentoValida(scanner);

                        Pagamento pg = new Pagamento(forma, pedSelPag);
                        pagamentos.add(pg);

                        System.out.println("\n[✓] Pagamento em (" + forma + ") efetuado com sucesso!");

                        boolean emitirNota = lerOpcaoNotaFiscalValida(scanner);
                        if (emitirNota) {
                            exibirNotaFiscalEletronica(pedSelPag, subtotal, taxaServico, desconto, valorFinalCalculado, forma, scanner);
                        } else {
                            System.out.println("[✓] Nota Fiscal dispensada pelo cliente.");
                        }

                        System.out.println("[✓] Mesa " + pedSelPag.getMesa().getNumero() + " foi liberada!");
                    } else {
                        System.out.println("Mesa inválida!");
                    }
                    break;

                case 6:
                    double faturamentoTotal = 0.0;
                    for (Pagamento pg : pagamentos) {
                        faturamentoTotal += pg.getValorFinal();
                    }

                    double ticketMedio = pagamentos.isEmpty() ? 0.0 : faturamentoTotal / pagamentos.size();

                    Prato maisCaro = null;
                    Prato maisBarato = null;
                    for (Prato pr : cardapio) {
                        if (maisCaro == null || pr.getPreco() > maisCaro.getPreco()) {
                            maisCaro = pr;
                        }
                        if (maisBarato == null || pr.getPreco() < maisBarato.getPreco()) {
                            maisBarato = pr;
                        }
                    }

                    System.out.println("\n=== RELATÓRIOS E INDICADORES ===");
                    System.out.printf("1. Total Faturamento Bruto: R$ %.2f\n", faturamentoTotal);
                    System.out.printf("2. Média Ticket Médio: R$ %.2f\n", ticketMedio);
                    if (maisCaro != null) {
                        System.out.printf("3. Maior Valor: %s (R$ %.2f)\n", maisCaro.getNome(), maisCaro.getPreco());
                    }
                    if (maisBarato != null) {
                        System.out.printf("4. Menor Valor: %s (R$ %.2f)\n", maisBarato.getNome(), maisBarato.getPreco());
                    }

                    System.out.println("\n--- Comissão dos Garçons (10%) ---");
                    for (Garcom g : garcons) {
                        System.out.printf("Garçom %s (%s): R$ %.2f\n", g.getNome(), g.getMatricula(), g.calcularComissao());
                    }

                    System.out.println("\n--- Ranking: Pratos Mais Solicitados ---");
                    ArrayList<Prato> ranking = new ArrayList<>(cardapio);
                    ranking.sort((p1, p2) -> Integer.compare(p2.getQuantidadePedida(), p1.getQuantidadePedida()));
                    for (int i = 0; i < ranking.size(); i++) {
                        Prato pr = ranking.get(i);
                        System.out.println((i + 1) + "º Lugar: " + pr.getNome() + " - " + pr.getQuantidadePedida() + " pedido(s)");
                    }

                    try (PrintWriter writer = new PrintWriter(new FileWriter("relatorio_vendas.csv"))) {
                        writer.println("Pedido,Mesa,FormaPagamento,ValorFinal");
                        for (Pagamento pg : pagamentos) {
                            writer.println(pg.getPedido().getNumero() + "," + pg.getPedido().getMesa().getNumero() + "," + pg.getFormaPagamento() + "," + pg.getValorFinal());
                        }
                        System.out.println("\n[✓] Relatório salvo em 'relatorio_vendas.csv'!");
                    } catch (IOException e) {
                        System.out.println("Erro ao salvar arquivo: " + e.getMessage());
                    }
                    break;

                case 0:
                    System.out.println("Encerrando o sistema...");
                    executando = false;
                    break;
            }
        }

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n=== RESTAURANTE RATATOUILLE ===");
        System.out.println("1. Abertura de Mesa, Garçom e Pedido");
        System.out.println("2. Adicionar Item a Pedido Existente");
        System.out.println("3. Remover Item do Pedido");
        System.out.println("4. Consultar / Buscar no Cardápio");
        System.out.println("5. Encerramento e Pagamento (Conta Detalhada)");
        System.out.println("6. Relatórios, Indicadores e Salvar CSV");
        System.out.println("0. Sair");
    }

    private static int lerOpcaoMenuValida(Scanner scanner) {
        while (true) {
            System.out.print("Escolha uma opção: ");
            String entrada = scanner.nextLine().trim();
            try {
                int valor = Integer.parseInt(entrada);
                if (valor >= 0 && valor <= 6) {
                    return valor;
                }
                System.out.println("Opção inválida! Digite um número de 0 a 6.");
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida! Você deve digitar um número.");
            }
        }
    }

    private static int lerInteiroValido(Scanner scanner) {
        while (true) {
            String entrada = scanner.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.print("Valor inválido! Digite um número inteiro: ");
            }
        }
    }

    private static Garcom lerGarcomValido(Scanner scanner, ArrayList<Garcom> garcons) {
        while (true) {
            System.out.println("\nGARÇONS:");
            for (int i = 0; i < garcons.size(); i++) {
                Garcom g = garcons.get(i);
                int mesasAtivas = g.getQuantidadeMesasAtivas();
                System.out.println((i + 1) + ". " + g.getNome() + " (Mesas ativas: " + mesasAtivas + "/2)");
            }
            System.out.print("Escolha o garçom responsável: ");
            String entrada = scanner.nextLine().trim();
            try {
                int opcao = Integer.parseInt(entrada);
                if (opcao >= 1 && opcao <= garcons.size()) {
                    Garcom gSel = garcons.get(opcao - 1);
                    if (gSel.getQuantidadeMesasAtivas() >= 2) {
                        System.out.println("O garçom " + gSel.getNome() + " já está no limite máximo de 2 mesas ativas! Escolha outro garçom.");
                    } else {
                        return gSel;
                    }
                } else {
                    System.out.println("Opção inválida! Por favor, escolha um garçom entre 1 e " + garcons.size() + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida! Por favor, tente de novo.");
            }
        }
    }

    private static String lerFormaPagamentoValida(Scanner scanner) {
        while (true) {
            System.out.println("\nForma de Pagamento:");
            System.out.println("1. Cartão");
            System.out.println("2. PIX");
            System.out.println("3. Dinheiro");
            System.out.print("Escolha a opção (1-3): ");

            String entrada = scanner.nextLine().trim();
            try {
                int opcao = Integer.parseInt(entrada);
                switch (opcao) {
                    case 1:
                        return "Cartão";
                    case 2:
                        return "PIX";
                    case 3:
                        return "Dinheiro";
                    default:
                        System.out.println("Opção inválida! Por favor, tente de novo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida! Por favor, tente de novo.");
            }
        }
    }

    private static boolean lerOpcaoNotaFiscalValida(Scanner scanner) {
        while (true) {
            System.out.println("\nDeseja receber a Nota Fiscal?");
            System.out.println("1. Sim");
            System.out.println("2. Não");
            System.out.print("Escolha a opção (1 ou 2): ");

            String entrada = scanner.nextLine().trim();
            try {
                int opcao = Integer.parseInt(entrada);
                if (opcao == 1) {
                    return true;
                } else if (opcao == 2) {
                    return false;
                } else {
                    System.out.println("Opção inválida! Por favor, tente de novo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida! Por favor, tente de novo.");
            }
        }
    }

    private static void exibirNotaFiscalEletronica(Pedido pedido, double subtotal, double taxaServico, double desconto, double valorFinal, String formaPagamento, Scanner scanner) {
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        System.out.println("\n==================================================================");
        System.out.println("            NOTA FISCAL DE CONSUMIDOR ELETRÔNICA - NFC-e           ");
        System.out.println("                     RESTAURANTE RATATOUILLE LTDA                 ");
        System.out.println("                     CNPJ: 12.345.678/0001-90                     ");
        System.out.println("             Av. Gusteau, 1000 - Centro, Paris - FR               ");
        System.out.println("==================================================================");
        System.out.println(" Data/Hora Emissão: " + agora.format(formatador));
        System.out.println(" Número do Pedido : #" + pedido.getNumero() + "  |  Mesa: " + pedido.getMesa().getNumero());
        System.out.println("------------------------------------------------------------------");
        System.out.printf("%-30s %-6s %-12s %-12s\n", "ITEM / DESCRIÇÃO", "QTD", "VALOR UN", "TOTAL ITEM");
        System.out.println("------------------------------------------------------------------");

        for (ItemPedido item : pedido.getListaItemPedidos()) {
            System.out.printf("%-30s %-6d R$ %-9.2f R$ %-9.2f\n",
                    item.getPrato().getNome(),
                    item.getQuantidade(),
                    item.getPrato().getPreco(),
                    item.calcularSubTotal());
        }

        System.out.println("------------------------------------------------------------------");
        System.out.printf(" Subtotal dos Produtos:                       R$ %10.2f\n", subtotal);
        System.out.printf(" Taxa de Serviço (10%%):                       R$ %10.2f\n", taxaServico);
        if (desconto > 0.0) {
            System.out.printf(" Desconto Concedido (> R$150):               -R$ %10.2f\n", desconto);
        } else {
            System.out.println(" Desconto Concedido (> R$150):                R$       0.00");
        }
        System.out.println("------------------------------------------------------------------");
        System.out.printf(" VALOR TOTAL PAGO:                            R$ %10.2f\n", valorFinal);
        System.out.printf(" FORMA DE PAGAMENTO:                          %s\n", formaPagamento.toUpperCase());
        System.out.println("==================================================================");
        System.out.println("             'Qualquer um pode cozinhar!' - Chef Gusteau          ");
        System.out.println("==================================================================");

        System.out.print("\nPressione ENTER para retornar ao menu principal do sistema...");
        scanner.nextLine();
    }
}