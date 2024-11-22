package com.example.pizzaria.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pizzaria.model.Pizza

@Dao
interface PizzaDao {
    @Insert
    suspend fun inserir(pizza: Pizza)

    @Query("SELECT * FROM pizza_table")
    suspend fun obterTodasPizzas(): List<Pizza>

    @Query("SELECT * FROM pizza_table WHERE id = :id")
    suspend fun obterPizzaPorId(id: Int): Pizza
}

