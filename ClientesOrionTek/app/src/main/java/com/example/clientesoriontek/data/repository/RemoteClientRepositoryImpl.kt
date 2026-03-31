package com.example.clientesoriontek.data.repository

import com.example.clientesoriontek.data.remote.api.ClientApiService
import com.example.clientesoriontek.data.remote.api.RetrofitClient
import com.example.clientesoriontek.data.remote.model.*

class RemoteClientRepositoryImpl(
    private val apiService: ClientApiService = RetrofitClient.clientApiService
) : RemoteClientRepository {

    override suspend fun getClients(): Result<List<ClientDto>> {
        return try {
            val response = apiService.getClients()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getClient(id: Long): Result<ClientDto> {
        return try {
            val response = apiService.getClient(id)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Client not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createClient(client: CreateClientDto): Result<ClientDto> {
        return try {
            val response = apiService.createClient(client)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error creating client: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateClient(id: Long, client: UpdateClientDto): Result<ClientDto> {
        return try {
            val response = apiService.updateClient(id, client)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error updating client: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteClient(id: Long): Result<Unit> {
        return try {
            val response = apiService.deleteClient(id)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error deleting client: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
