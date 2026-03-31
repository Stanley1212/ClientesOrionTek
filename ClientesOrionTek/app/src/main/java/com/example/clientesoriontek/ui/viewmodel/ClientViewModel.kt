package com.example.clientesoriontek.ui.viewmodel

import androidx.lifecycle.*
import com.example.clientesoriontek.data.local.entity.Address
import com.example.clientesoriontek.data.local.entity.Client
import com.example.clientesoriontek.data.repository.ClientRepository
import kotlinx.coroutines.launch

class ClientViewModel(private val repository: ClientRepository) : ViewModel() {

    val allClients: LiveData<List<Client>> = repository.allClients.asLiveData()

    fun insertClient(client: Client, onResult: (Long) -> Unit) = viewModelScope.launch {
        val id = repository.insertClient(client)
        onResult(id)
    }

    fun updateClient(client: Client) = viewModelScope.launch {
        repository.updateClient(client)
    }

    fun deleteClient(client: Client) = viewModelScope.launch {
        repository.deleteClient(client)
    }

    fun getClientById(clientId: Long): LiveData<Client?> {
        val clientLiveData = MutableLiveData<Client?>()
        viewModelScope.launch {
            clientLiveData.value = repository.getClientById(clientId)
        }
        return clientLiveData
    }

    fun getAddressesForClient(clientId: Long): LiveData<List<Address>> {
        return repository.getAddressesForClient(clientId).asLiveData()
    }

    fun insertAddress(address: Address) = viewModelScope.launch {
        repository.insertAddress(address)
    }

    fun updateAddress(address: Address) = viewModelScope.launch {
        repository.updateAddress(address)
    }

    fun deleteAddress(address: Address) = viewModelScope.launch {
        repository.deleteAddress(address)
    }
}

class ClientViewModelFactory(private val repository: ClientRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClientViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ClientViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
