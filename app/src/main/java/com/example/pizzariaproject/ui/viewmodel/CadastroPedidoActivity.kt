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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.example.pizzaria.model.Pizza
import com.example.pizzaria.model.Pedido
import com.example.pizzaria.model.PedidoProduto
import com.example.pizzaria.ui.theme.PizzariaTheme
import com.example.pizzariaproject.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.TimeZone

class CadastroPedidoActivity : ComponentActivity() {
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        database = AppDatabase.getDatabase(applicationContext)


        val clienteId = intent.getIntExtra("clienteId", 0)
        val clienteNome = intent.getStringExtra("nome") ?: "Cliente"

        setContent {
            PizzariaTheme {
                CadastroPedidoScreen(clienteId, clienteNome)
            }
        }
    }

    @Composable
    fun CadastroPedidoScreen(clienteId: Int, clienteNome: String) {
        val scope = rememberCoroutineScope()
        var selectedProducts by remember { mutableStateOf(mutableListOf<Pizza>()) }
        var totalValue by remember { mutableStateOf(0.0) }
        var expandedPizzaId by remember { mutableStateOf<Int?>(null) } // ID da pizza com detalhes expandidos

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.HOUR_OF_DAY, -3)
        val novaData = calendar.time
        val context = LocalContext.current

        val pizzas = listOf(
            Pizza(id = 1, nome = "Pizza Margherita", ingredientes = "Tomate, Queijo", preco = 25.0, tamanho = "Médio"),
            Pizza(id = 2, nome = "Pizza Calabresa", ingredientes = "Calabresa, Queijo", preco = 30.0, tamanho = "Grande"),
            Pizza(id = 3, nome = "Pizza Portuguesa", ingredientes = "Presunto, Ovo, Queijo", preco = 35.0, tamanho = "Grande"),
            Pizza(id = 5, nome = "Pizza 4 Queijos", ingredientes = "Queijo, Queijo ,Queijo, Queijo ", preco = 14.0, tamanho = "Grande")
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Exibe o nome do cliente logado
            Text("Bem-vindo, $clienteNome!")

            Spacer(modifier = Modifier.height(16.dp))

            // Seção para seleção de pizzas
            Text("Selecione as Pizzas:")
            pizzas.forEach { pizza ->
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = pizza.nome)
                        Text(text = "R$ ${pizza.preco}")
                        Button(onClick = {
                            selectedProducts.add(pizza)
                            totalValue += pizza.preco

                        }) {
                            Text("Adicionar")
                        }
                    }

//                    Spacer(modifier = Modifier.height(8.dp))

                    // Botão para exibir ou ocultar detalhes
                    Button(onClick = {
                        expandedPizzaId = if (expandedPizzaId == pizza.id) null else pizza.id
                    }) {
                        Text(if (expandedPizzaId == pizza.id) "Ocultar Detalhes" else "Ver Detalhes")
                    }

                    // Detalhes da pizza (exibidos se a pizza estiver expandida)
                    if (expandedPizzaId == pizza.id) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        ) {
                            Text("Ingredientes: ${pizza.ingredientes}")
                            Text("Tamanho: ${pizza.tamanho}")
                            Text("Preço: R$ ${pizza.preco}")
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                }
            }


            Spacer(modifier = Modifier.height(8.dp))

            // mostra o valor total
            Text("Total: R$ $totalValue")

            Spacer(modifier = Modifier.height(8.dp))

            // Botão para finalizar o pedido
            Button(onClick = {
                if (selectedProducts.isNotEmpty()) {
                    val pedido = Pedido(clienteId = clienteId, data = novaData.toString().toString(), valorTotal = totalValue)

                    scope.launch {
                        val pedidoId = withContext(Dispatchers.IO) {
                            database.pedidoDao().insertPedido(pedido)
                        }

                       val pizzaQuantities = mutableMapOf<Int, Int>()

                        selectedProducts.forEach { pizza ->
                            pizzaQuantities[pizza.id] = pizzaQuantities.getOrDefault(pizza.id, 0) + 1
                        }

                        pizzaQuantities.forEach { (pizzaId, quantidade) ->
                            val pizza = pizzas.find { it.id == pizzaId }
                            if (pizza != null) {
                                val pedidoProduto = PedidoProduto(
                                    pedidoId = pedidoId.toInt(),
                                    produtoId = pizza.id,
                                    produtoNome = pizza.nome,
                                    quantidade = quantidade
                                )
                                // Insere no banco
                                withContext(Dispatchers.IO) {
                                    database.pedidoProdutoDao().insertPedidoProduto(pedidoProduto)
                                }
                            }
                        }


                        Toast.makeText(
                            this@CadastroPedidoActivity,
                            "Pedido registrado com sucesso!",
                            Toast.LENGTH_LONG
                        ).show()

                        // Limpa
                        selectedProducts.clear()
                        totalValue = 0.0
                    }
                } else {
                    Toast.makeText(this@CadastroPedidoActivity, "Selecione pelo menos uma pizza", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Finalizar Pedido")
            }

            Button(onClick = {
                (context as? android.app.Activity)?.finish() // Chama finish() na Activity
            }) {
                Text(text = "Voltar")
            }


            // mostra os items do pedido
            if (selectedProducts.isNotEmpty()) {
                Text("Seu Pedido:")
                Spacer(modifier = Modifier.height(8.dp))
                selectedProducts.forEach { pizza ->
                    Text("- ${pizza.nome} (${pizza.tamanho}): R$ ${pizza.preco}")
                }
                Spacer(modifier = Modifier.height(8.dp))
            } else {
                Text("Nenhuma pizza adicionada ainda.")
            }
        }
    }
}