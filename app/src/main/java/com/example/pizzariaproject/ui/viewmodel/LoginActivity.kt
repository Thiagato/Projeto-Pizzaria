package com.example.pizzariaproject.ui.viewmodel

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import com.example.pizzaria.ui.theme.PizzariaTheme

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

@Composable
fun LoginScreen() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Bem-vindo ao Sistema de Pizzaria")

        Spacer(modifier = Modifier.height(32.dp))  // Espaço entre o titulo e o botao

        // ir para cadastro
        Button(onClick = {
            val intent = Intent(context, CadastroClienteActivity::class.java)
            context.startActivity(intent)
        }) {
            Text(text = "Cadastrar Cliente")
        }

        Spacer(modifier = Modifier.height(16.dp))  // Espaço entre os botões


        Button(onClick = {
            // Lógica para login
        }) {
            Text(text = "Fazer Login")
        }
    }
}
