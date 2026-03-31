package com.example.clientesoriontek

import android.app.Application
import com.example.clientesoriontek.data.local.AppDatabase
import com.example.clientesoriontek.data.repository.ClientRepository

class MainApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { ClientRepository(database.clientDao()) }
}
