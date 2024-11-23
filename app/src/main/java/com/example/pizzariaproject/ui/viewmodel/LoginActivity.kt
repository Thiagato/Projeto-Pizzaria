package com.example.pizzariaproject.ui.viewmodel

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import com.example.pizzaria.database.AppDatabase
import com.example.pizzaria.ui.theme.PizzariaTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PizzariaTheme {
                LoginScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    val context = LocalContext.current

    var nome by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var mensagemErro by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Bem-vindo ao Sistema de Pizzaria")

        Spacer(modifier = Modifier.height(16.dp))


        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))


        OutlinedTextField(
            value = telefone,
            onValueChange = { telefone = it },
            label = { Text("Telefone") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (nome.isNotEmpty() && telefone.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    val database = AppDatabase.getDatabase(context)
                    val clienteDao = database.clienteDao()

                    val cliente = clienteDao.obterTodosClientes().find {
                        it.nome == nome && it.telefone == telefone
                    }

                    if (cliente != null) {
                        // Login bem-sucedido, redirecionar para CadastroPedidoActivity
                        withContext(Dispatchers.Main) {
                            val intent = Intent(context, CadastroPedidoActivity::class.java).apply {
                                putExtra("clienteId", cliente.clienteId)
                                putExtra("nome", cliente.nome)
                                putExtra("telefone", cliente.telefone)
                            }
                            context.startActivity(intent)
                        }
                    } else {
                        // Exibir mensagem de erro
                        withContext(Dispatchers.Main) {
                            mensagemErro = "Nome ou telefone incorretos."
                        }
                    }
                }
            } else {
                mensagemErro = "Preencha todos os campos!"
            }
        }) {
            Text(text = "Fazer Login")
        }
    }
}