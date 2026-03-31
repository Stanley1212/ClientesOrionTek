package com.example.clientesoriontek.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.clientesoriontek.data.local.dao.ClientDao
import com.example.clientesoriontek.data.local.entity.Address
import com.example.clientesoriontek.data.local.entity.Client

@Database(entities = [Client::class, Address::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun clientDao(): ClientDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "oriontek_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
