package com.example.pizzaria.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.pizzaria.ui.theme.PizzariaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PizzariaTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título "Bem-vindo"
        Text(text = "Bem-vindo ao Sistema de Pizzaria")

        Spacer(modifier = Modifier.height(32.dp))


        Button(onClick = {
            val intent = Intent(context, LoginActivity::class.java)  // ir tela de login
            context.startActivity(intent)
        }) {
            Text(text = "Ir para Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botão para Cadastro de Cliente
        Button(onClick = {
            val intent = Intent(context, CadastroClienteActivity::class.java)
            context.startActivity(intent)
        }) {
            Text(text = "Cadastrar Cliente")
        }
    }
}
