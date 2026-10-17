# Sistema de Gerenciamento para Restaurantes

##  Integrantes e Responsabilidades
* **Amir De Sousa Farias:** Responsável pelas classes `Pedido`, `ItemPedido` e tecnologias externas.
* **Augusto Pereira Ribeiro Ferreira:** Responsável pelas classes `Mesa` e `Pagamento`.
* **Filipi Cavalcante Brasil Dutra:** Responsável pelas classes `Prato` e `Garçom`.

---

##  Descrição do Problema
A ineficiência econômica de restaurantes ocorre quando os estabelecimentos não utilizam seus recursos financeiros, materiais e humanos de maneira adequada, causando desperdícios e reduzindo a lucratividade. Com isso, nosso grupo se reuniu para sanar essa problemática através da automatização e controle dos processos.

---

##  Funcionalidades
* Cadastrar garçons
* Cadastrar pratos
* Cadastrar mesas
* Criar pedidos
* Adicionar pratos aos pedidos
* Calcular o valor total de um pedido
* Cálculo de comissão do garçom
* Controle de disponibilidade de mesas
* Controle de disponibilidade de pratos

---

##  Principais Classes
* `Garçom`
* `ItemPedido`
* `Mesa`
* `Pagamento`
* `Pedido`
* `Prato`

---

##  Regras de Negócio
* **Cadastro de pratos:** Todo prato cadastrado deve possuir nome, preço e informações sobre sua disponibilidade. O preço não pode ser negativo ou igual a zero.
* **Disponibilidade dos pratos:** Somente pratos disponíveis podem ser adicionados a novos pedidos.
* **Cadastro de garçons:** Cada garçom deve possuir nome e matrícula.
* **Ocupação das mesas:** Uma mesa deve ser marcada como ocupada quando um pedido for iniciado e liberada após o encerramento do atendimento.
* **Registro de pedidos:** Todo pedido deve estar associado a uma mesa e a um garçom responsável pelo atendimento.
* **Adição de pratos aos pedidos:** Um pedido pode conter um ou mais pratos, e o sistema deve calcular automaticamente o valor total com base nos preços e nas quantidades solicitadas.
* **Consulta de preços:** O sistema deve permitir consultar os preços dos pratos cadastrados para facilitar o atendimento e a conferência dos valores.
* **Encerramento do pedido:** Ao finalizar um pedido, o sistema deve registrar o valor total e atualizar a situação da mesa para livre.

---

##  Tecnologias Utilizadas
* **CSV**
* **JUnit**

---

##  Instruções de Configuração
1. A criação de pelo menos 1 garçom contendo nome e matrícula.
2. Cadastro de prato com nome, valor e disponibilidade.
3. Cadastro de mesa com um número.
4. Criação de listas para armazenamento dos cadastros e de pagamentos.

---

##  Instruções de Utilização
* `adicionarPedido()`: Adiciona um novo pedido.
* `getQuantidadeMesasAtivas()`: Vê a quantidade de mesas ativas.
* `calcularComissao()`: Calcula a comissão do garçom.
* `calcularSubTotal()`: Calcula o subtotal de um prato/item.
* `ocupar()`: Ocupa uma mesa.
* `liberar()`: Libera a mesa.
* `processarPagamento()`: Processa o pagamento, e se o valor necessário for alcançado, aplica eventuais regras e libera a mesa.
* `adicionarItem()`: Adiciona um item ao pedido.
* `removerItem()`: Remove um item do pedido.
* `calcularTotalPedido()`: Calcula o total do pedido.

---

##  Exemplos de Referência / Contexto de Uso
* Modelos inspirados em operações de estabelecimentos como Outback Steakhouse, Coco Bambu e McDonald's.

---

##  Utilização de IA
* **Gemini:** Utilizado para treino, consulta de dúvidas e apoio durante o desenvolvimento.
