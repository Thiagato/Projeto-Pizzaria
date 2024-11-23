package com.example.pizzaria.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pizzaria.model.Cliente

@Dao
interface ClienteDao {
    @Insert
    suspend fun inserir(cliente: Cliente)

    @Query("SELECT * FROM cliente_table")
    suspend fun obterTodosClientes(): List<Cliente>

    @Query("SELECT * FROM cliente_table WHERE clienteId = :id")
    suspend fun obterClientePorId(id: Int): Cliente?

    @Query("SELECT * FROM cliente_table WHERE nome = :nome AND telefone = :telefone")
    suspend fun obterClientePorNomeETelefone(nome: String, telefone: String): Cliente?
}
