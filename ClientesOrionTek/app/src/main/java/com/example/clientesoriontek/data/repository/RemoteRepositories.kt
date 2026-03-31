package com.example.clientesoriontek.data.repository

import com.example.clientesoriontek.data.remote.model.*

interface RemoteClientRepository {
    suspend fun getClients(): Result<List<ClientDto>>
    suspend fun getClient(id: Long): Result<ClientDto>
    suspend fun createClient(client: CreateClientDto): Result<ClientDto>
    suspend fun updateClient(id: Long, client: UpdateClientDto): Result<ClientDto>
    suspend fun deleteClient(id: Long): Result<Unit>
}

interface RemoteAddressRepository {
    suspend fun getAddresses(): Result<List<AddressDto>>
    suspend fun getAddress(id: Long): Result<AddressDto>
    suspend fun getAddressesByClient(clientId: Long): Result<List<AddressDto>>
    suspend fun createAddress(address: CreateAddressDto): Result<AddressDto>
    suspend fun updateAddress(id: Long, address: UpdateAddressDto): Result<AddressDto>
    suspend fun deleteAddress(id: Long): Result<Unit>
}
