package com.example.clientesoriontek.ui.viewmodel

import androidx.lifecycle.*
import com.example.clientesoriontek.data.remote.model.*
import com.example.clientesoriontek.data.repository.RemoteAddressRepository
import com.example.clientesoriontek.data.repository.RemoteClientRepository
import com.example.clientesoriontek.data.repository.RemoteClientRepositoryImpl
import com.example.clientesoriontek.data.repository.RemoteAddressRepositoryImpl
import kotlinx.coroutines.launch

class RemoteClientViewModel(
    private val clientRepository: RemoteClientRepository = RemoteClientRepositoryImpl(),
    private val addressRepository: RemoteAddressRepository = RemoteAddressRepositoryImpl()
) : ViewModel() {

    private val _clients = MutableLiveData<List<ClientDto>>()
    val clients: LiveData<List<ClientDto>> = _clients

    private val _selectedClient = MutableLiveData<ClientDto?>()
    val selectedClient: LiveData<ClientDto?> = _selectedClient

    private val _addresses = MutableLiveData<List<AddressDto>>()
    val addresses: LiveData<List<AddressDto>> = _addresses

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadClients() = viewModelScope.launch {
        _isLoading.value = true
        _error.value = null
        clientRepository.getClients()
            .onSuccess { _clients.value = it }
            .onFailure { _error.value = it.message }
        _isLoading.value = false
    }

    fun loadClient(id: Long) = viewModelScope.launch {
        _isLoading.value = true
        _error.value = null
        clientRepository.getClient(id)
            .onSuccess { _selectedClient.value = it }
            .onFailure { _error.value = it.message }
        _isLoading.value = false
    }

    fun createClient(createClientDto: CreateClientDto, onSuccess: (ClientDto) -> Unit) = viewModelScope.launch {
        _isLoading.value = true
        _error.value = null
        clientRepository.createClient(createClientDto)
            .onSuccess {
                loadClients()
                onSuccess(it)
            }
            .onFailure { _error.value = it.message }
        _isLoading.value = false
    }

    fun updateClient(id: Long, updateClientDto: UpdateClientDto) = viewModelScope.launch {
        _isLoading.value = true
        _error.value = null
        clientRepository.updateClient(id, updateClientDto)
            .onSuccess {
                loadClients()
                _selectedClient.value = it
            }
            .onFailure { _error.value = it.message }
        _isLoading.value = false
    }

    fun deleteClient(id: Long) = viewModelScope.launch {
        _isLoading.value = true
        _error.value = null
        clientRepository.deleteClient(id)
            .onSuccess { loadClients() }
            .onFailure { _error.value = it.message }
        _isLoading.value = false
    }

    fun loadAddressesByClient(clientId: Long) = viewModelScope.launch {
        _isLoading.value = true
        _error.value = null
        addressRepository.getAddressesByClient(clientId)
            .onSuccess { _addresses.value = it }
            .onFailure { _error.value = it.message }
        _isLoading.value = false
    }

    fun createAddress(createAddressDto: CreateAddressDto, onSuccess: (AddressDto) -> Unit) = viewModelScope.launch {
        _isLoading.value = true
        _error.value = null
        addressRepository.createAddress(createAddressDto)
            .onSuccess {
                loadAddressesByClient(createAddressDto.clientId)
                onSuccess(it)
            }
            .onFailure { _error.value = it.message }
        _isLoading.value = false
    }

    fun updateAddress(id: Long, updateAddressDto: UpdateAddressDto, clientId: Long) = viewModelScope.launch {
        _isLoading.value = true
        _error.value = null
        addressRepository.updateAddress(id, updateAddressDto)
            .onSuccess { loadAddressesByClient(clientId) }
            .onFailure { _error.value = it.message }
        _isLoading.value = false
    }

    fun deleteAddress(id: Long, clientId: Long) = viewModelScope.launch {
        _isLoading.value = true
        _error.value = null
        addressRepository.deleteAddress(id)
            .onSuccess { loadAddressesByClient(clientId) }
            .onFailure { _error.value = it.message }
        _isLoading.value = false
    }

    fun clearError() {
        _error.value = null
    }
}

class RemoteClientViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RemoteClientViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RemoteClientViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
