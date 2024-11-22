package com.example.pizzariaproject.ui.viewmodel

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pizzaria.model.Cliente
import com.example.pizzaria.model.Pizza
import com.example.pizzaria.model.Pedido
import com.example.pizzaria.model.PedidoProduto
import com.example.pizzaria.ui.theme.PizzariaTheme
import com.example.pizzaria.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CadastroPedidoActivity : ComponentActivity() {
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PizzariaTheme {
                CadastroPedidoScreen()
            }
        }

        // Inicializa o banco de dados
        database = AppDatabase.getDatabase(applicationContext)
    }

    @Composable
    fun CadastroPedidoScreen() {
        val scope = rememberCoroutineScope() // Coloque dentro do @Composable
        var selectedClientId by remember { mutableStateOf(0) }
        var selectedProducts by remember { mutableStateOf(listOf<Pizza>()) }
        var totalValue by remember { mutableStateOf(0.0) }


        val clientes = listOf(
            Cliente(clienteId = 1, nome = "brito", telefone = "123456789", endereco = "Rua A"),
            Cliente(clienteId = 2, nome = "julia", telefone = "987654321", endereco = "Rua B")

        )

        val pizzas = listOf(
            Pizza(id = 1, nome = "Pizza Margherita", ingredientes = "Tomate, Queijo", preco = 25.0, tamanho = "Médio"),
            Pizza(id = 2, nome = "Pizza Calabreso", ingredientes = "Calabreso, Queijo", preco = 30.0, tamanho = "Grande"),
            Pizza(id = 3, nome = "Pizza Portuguesa", ingredientes = "Presunto, Ovo, Queijo", preco = 35.0, tamanho = "Grande"),
            Pizza(id = 4, nome = "Pizza Niggherita", ingredientes = "Presunto, Ovo, Queijo", preco = 35.0, tamanho = "Grande")
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Cadastro de Pedido")

            Spacer(modifier = Modifier.height(16.dp))


            Text("Selecione o Cliente:")
            clientes.forEach { cliente ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = cliente.nome)
                    Button(onClick = { selectedClientId = cliente.clienteId }) {
                        Text("Selecionar")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            Text("Selecione as Pizzas:")
            pizzas.forEach { pizza ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = pizza.nome)
                    Text(text = "R$ ${pizza.preco}")
                    Button(onClick = {
                        selectedProducts = selectedProducts + pizza
                        totalValue += pizza.preco
                    }) {
                        Text("Adicionar")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))


            Text("Total: R$ $totalValue")

            Spacer(modifier = Modifier.height(16.dp))


            Button(onClick = {
                if (selectedClientId != 0 && selectedProducts.isNotEmpty()) {

                    val pedido = Pedido(clienteId = selectedClientId, data = "2024-11-21", valorTotal = totalValue)

                    //corrotina para salvar no banco de dados
                    scope.launch {
                        // Inserir o pedido
                        val pedidoId = withContext(Dispatchers.IO) {
                            database.pedidoDao().insertPedido(pedido)
                        }

                        // Associar produtos ao pedido
                        selectedProducts.forEach { pizza ->
                            val pedidoProduto = PedidoProduto(pedidoId = pedidoId.toInt(), produtoId = pizza.id, quantidade = 1)
                            withContext(Dispatchers.IO) {
                                database.pedidoProdutoDao().insertPedidoProduto(pedidoProduto)
                            }
                        }


                        Toast.makeText(
                            this@CadastroPedidoActivity,
                            "Pedido registrado com sucesso! Total: R$ $totalValue",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(this@CadastroPedidoActivity, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Finalizar Pedido")
            }
        }
    }
}