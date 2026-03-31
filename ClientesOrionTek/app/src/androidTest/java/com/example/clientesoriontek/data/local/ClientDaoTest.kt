package com.example.clientesoriontek.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.clientesoriontek.data.local.dao.ClientDao
import com.example.clientesoriontek.data.local.entity.Client
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ClientDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var clientDao: ClientDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        clientDao = database.clientDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAndGetClient() = runBlocking {
        val client = Client(1L, "Test", "User", "123456", "test@test.com")
        clientDao.insertClient(client)

        val allClients = clientDao.getAllClients().first()
        assertEquals(1, allClients.size)
        assertEquals("Test", allClients[0].firstName)
    }

    @Test
    fun deleteClient() = runBlocking {
        val client = Client(1L, "Test", "User", "123456", "test@test.com")
        clientDao.insertClient(client)
        clientDao.deleteClient(client)

        val allClients = clientDao.getAllClients().first()
        assertEquals(0, allClients.size)
    }
}
