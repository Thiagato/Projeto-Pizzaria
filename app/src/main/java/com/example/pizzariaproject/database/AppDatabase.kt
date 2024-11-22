package com.example.pizzaria.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pizzaria.model.Cliente
import com.example.pizzaria.model.Pedido
import com.example.pizzaria.model.PedidoProduto
import com.example.pizzaria.model.Pizza

@Database(entities = [Cliente::class, Pizza::class, Pedido::class, PedidoProduto::class], version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun clienteDao(): ClienteDao
    abstract fun pizzaDao(): PizzaDao
    abstract fun pedidoDao(): PedidoDao
    abstract fun pedidoProdutoDao(): PedidoProdutoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pizzaria_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
