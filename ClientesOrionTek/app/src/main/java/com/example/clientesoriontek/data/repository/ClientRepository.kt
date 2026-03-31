package com.example.clientesoriontek.data.repository

import com.example.clientesoriontek.data.local.dao.ClientDao
import com.example.clientesoriontek.data.local.entity.Address
import com.example.clientesoriontek.data.local.entity.Client
import kotlinx.coroutines.flow.Flow

class ClientRepository(private val clientDao: ClientDao) {

    val allClients: Flow<List<Client>> = clientDao.getAllClients()

    suspend fun insertClient(client: Client): Long {
        return clientDao.insertClient(client)
    }

    suspend fun updateClient(client: Client) {
        clientDao.updateClient(client)
    }

    suspend fun deleteClient(client: Client) {
        clientDao.deleteClient(client)
    }

    suspend fun getClientById(clientId: Long): Client? {
        return clientDao.getClientById(clientId)
    }

    fun getAddressesForClient(clientId: Long): Flow<List<Address>> {
        return clientDao.getAddressesForClient(clientId)
    }

    suspend fun insertAddress(address: Address) {
        clientDao.insertAddress(address)
    }

    suspend fun updateAddress(address: Address) {
        clientDao.updateAddress(address)
    }

    suspend fun deleteAddress(address: Address) {
        clientDao.deleteAddress(address)
    }
}
