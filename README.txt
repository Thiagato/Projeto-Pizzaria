# Sistema de Pizzaria

Este é um aplicativo Android para gerenciar pedidos de uma pizzaria. O sistema permite o cadastro de clientes, produtos e pedidos, utilizando a arquitetura MVVM, e implementa a persistência de dados com o Room.

## Funcionalidades Implementadas

1. **Cadastro de Cliente**: A funcionalidade permite registrar informações de clientes como nome, telefone e endereço. As informações ficam armazenadas no banco de dados local.
2. **Cadastro de Produtos**: Funcionalidade de cadastro de pizzas com dados como nome, ingredientes, preço e tamanho.
3. **Tela de Login**: A tela de login permite que o usuário acesse o sistema de forma segura.
4. **Tela Principal**: A tela principal serve como ponto de navegação para as outras telas do aplicativo, como Cadastro de Cliente, Lista de Produtos e Cadastro de Pedido.
5. **Cadastro de Pedido**: Permite selecionar um cliente e um conjunto de produtos para criar um pedido. O total do pedido é calculado automaticamente.

## Arquitetura

A arquitetura escolhida para o sistema é o **MVVM** (Model-View-ViewModel), que ajuda a manter o código organizado e facilita a manutenção.

- **Model**: Representa os dados e a lógica de negócio, incluindo a persistência.
- **ViewModel**: Responsável pela lógica de apresentação e interação com os dados.
- **View**: Responsável pela interface do usuário, sendo alimentada pelos dados fornecidos pelo ViewModel.

## Banco de Dados (Room)

O banco de dados é implementado usando o **Room**, permitindo a persistência local das informações. As principais entidades são:

- **Cliente**: Representa os clientes cadastrados no sistema.
  - Campos: `id`, `nome`, `telefone`, `endereco`.

- **Produto**: Representa as pizzas disponíveis para venda.
  - Campos: `id`, `nome`, `ingredientes`, `preco`, `tamanho`.

- **Pedido**: Representa um pedido realizado por um cliente.
  - Campos: `id`, `clienteId`, `data`, `total`.

- **PedidoProduto**: Representa a relação entre pedidos e os produtos selecionados.
  - Campos: `pedidoId`, `produtoId`, `quantidade`.

### Relacionamentos no Banco de Dados

- **Cliente 1:N Pedido**: Um cliente pode ter vários pedidos.
- **Pedido N:M Produto**: Um pedido pode conter múltiplos produtos e um produto pode ser parte de vários pedidos.

## Funcionalidades Faltantes

Ainda precisamos implementar as seguintes funcionalidades no sistema de pizzaria:

1. **Cadastro de Pedidos**: A funcionalidade de cadastro de pedidos, permitindo que o cliente selecione pizzas e finalizando o pedido com o valor total.
2. **Validação de Formulários**: Implementar validações para garantir que os dados inseridos nos cadastros de cliente, produto e pedido sejam válidos, além de exibir mensagens de erro quando necessário.
3. **Melhorias na UI**: Ajustes na interface do usuário para torná-la mais amigável e responsiva, com foco na usabilidade.
4. **Persistência de Dados**: Completar as operações de CRUD no banco de dados utilizando o Room para garantir que os dados do cliente, produto e pedido sejam persistidos corretamente.
5. **Exibição de Listas**: Criar as listas de produtos disponíveis e pedidos realizados, com filtros e detalhes.
6. **Integração Completa**: Conectar todas as partes do sistema, garantindo uma navegação fluída entre as telas de cadastro e visualização de dados.

## Tecnologias Usadas

- **Kotlin**: Linguagem de programação.
- **Jetpack Compose**: Framework para construção de interfaces no Android.
- **Room**: Biblioteca para persistência de dados no Android.
- **MVVM**: Arquitetura escolhida para separar a lógica de negócios da interface do usuário.

## Como Rodar o Projeto

1. Clone o repositório.
2. Abra o projeto no Android Studio.
3. Compile e execute o aplicativo no emulador ou dispositivo físico.
4. Navegue pelas funcionalidades implementadas, como cadastro de clientes, cadastro de produtos e pedidos.

## Próximas Etapas

Ainda precisamos implementar as seguintes funcionalidades no sistema de pizzaria:

- **Cadastro de Produtos**: Tela para cadastrar pizzas com informações como nome, ingredientes, preço e tamanho.
- **Cadastro de Pedidos**: Tela para criar pedidos e permitir que o cliente selecione pizzas.
- **Persistência de Dados com Room**: Implementação de entidades para `Cliente`, `Produto` e `Pedido`, e seus respectivos relacionamentos.
- **Validação de Formulários**: Garantir que os dados inseridos sejam válidos e exibir mensagens de erro quando necessário.
- **Melhorias na UI**: Ajustes na interface para tornar o sistema mais amigável e responsivo.

Esses são os próximos passos para concluir o desenvolvimento do sistema de pizzaria.
