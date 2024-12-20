package com.example.pizzariaproject.ui.viewmodel

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import com.example.pizzariaproject.database.AppDatabase
import com.example.pizzaria.model.Cliente
import com.example.pizzaria.ui.theme.PizzariaTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CadastroClienteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PizzariaTheme {
                CadastroClienteScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroClienteScreen() {
    val context = LocalContext.current


    var nome by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    var mensagemErro by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = telefone,
            onValueChange = { telefone = it },
            label = { Text("Telefone") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = endereco,
            onValueChange = { endereco = it },
            label = { Text("Endereço") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))

        if (mensagemErro.isNotEmpty()) {
            Text(text = mensagemErro, color = androidx.compose.ui.graphics.Color.Red)
            Spacer(modifier = Modifier.height(10.dp))
        }

        Button(onClick = {
            if (nome.length > 3 && telefone.length == 11 && telefone.all { it.isDigit() } && endereco.isNotEmpty()) {
                val novoCliente = Cliente(
                    nome = nome,
                    telefone = telefone,
                    endereco = endereco
                )

                CoroutineScope(Dispatchers.IO).launch {
                    val database = AppDatabase.getDatabase(context)
                    val clienteDao = database.clienteDao()

                    clienteDao.inserir(novoCliente)

                    // Ir para login
                    withContext(Dispatchers.Main) {
                        val intent = Intent(context, LoginActivity::class.java)
                        context.startActivity(intent)
                    }
                }
            } else {
                // Mensagens de erro específicas com Toast
                if (nome.length <= 3) {
                    Toast.makeText(context, "O nome precisa ter mais de 3 caracteres!", Toast.LENGTH_SHORT).show()
                } else if (telefone.length != 11 || telefone.any { !it.isDigit() }) {
                    Toast.makeText(context, "O telefone precisa ter 11 dígitos numéricos!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Todos os campos são obrigatórios!", Toast.LENGTH_SHORT).show()
                }
            }
        }) {
            Text(text = "Cadastrar")
        }

        Button(onClick = {
            (context as? android.app.Activity)?.finish() // Chama finish() na Activity
        }) {
            Text(text = "Voltar")
        }
    }
}
