# Sistema de Pizzaria

Integrantes:

Gustavo de Brito Ponciano
Thiago Vinicius Oliveira de Souza
Jean Augusto Przybysz
Julio Henrique Lage Michaliszen


Este é um aplicativo Android desenvolvido para gerenciar pedidos de uma pizzaria. O sistema implementa funcionalidades essenciais, como cadastro de clientes, produtos e pedidos, utilizando a arquitetura MVVM e persistência de dados com Room.

## Funcionalidades Implementadas

1. **Tela Inicial**: 
   - Contém dois botões: **Login** e **Cadastro**.
2. **Cadastro de Usuário**:
   - Permite registrar um novo usuário com informações básicas. Após o cadastro, o usuário é automaticamente redirecionado para a tela de login.
3. **Login**:
   - Tela para autenticação do usuário registrado. Ao realizar o login com sucesso, o sistema direciona para a tela de gerenciamento de pedidos.
4. **Gerenciamento de Pedidos**:
   - Funcionalidade para criar pedidos, selecionando produtos disponíveis. O pedido é finalizado na mesma tela e seu total é calculado automaticamente.

## Arquitetura

O sistema utiliza a arquitetura **MVVM** (Model-View-ViewModel) para organizar o código e facilitar a manutenção:

- **Model**: Representa os dados e a lógica de negócios, incluindo a persistência de dados.
- **ViewModel**: Gerencia a lógica de apresentação e interação com os dados.
- **View**: Responsável pela interface do usuário, alimentada pelo ViewModel.

## Banco de Dados (Room)

O banco de dados é implementado com **Room**, permitindo a persistência local dos dados. As principais entidades e seus relacionamentos são:

- **Cliente**:
  - Campos: `id`, `nome`, `telefone`, `endereco`.
- **Produto**:
  - Campos: `id`, `nome`, `ingredientes`, `preco`, `tamanho`.
- **Pedido**:
  - Campos: `id`, `clienteId`, `data`, `total`.
- **PedidoProduto**:
  - Representa a relação entre pedidos e produtos.
  - Campos: `pedidoId`, `produtoId`, `produtoNome`, `quantidade`.

### Relacionamentos no Banco de Dados

- **Cliente 1:N Pedido**: Um cliente pode ter vários pedidos.
- **Pedido N:M Produto**: Um pedido pode conter vários produtos, e um produto pode estar em diversos pedidos.

## Tecnologias Usadas

- **Kotlin**: Linguagem de programação.
- **Jetpack Compose**: Framework para construção de interfaces no Android.
- **Room**: Biblioteca para persistência de dados.
- **MVVM**: Arquitetura para separação de responsabilidades.

## Como Rodar o Projeto

1. Clone este repositório.
2. Abra o projeto no Android Studio.
3. Compile o aplicativo.
4. Execute-o em um emulador ou dispositivo físico.
5. Navegue pelas telas na seguinte sequência:
    - **Tela Inicial**: Escolha entre **Cadastro** ou **Login**.
    - Após o **login**, vá para a tela de **Gerenciamento de Pedidos** para criar e finalizar pedidos.
