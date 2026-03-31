package com.example.clientesoriontek.data.repository

import com.example.clientesoriontek.data.remote.api.AddressApiService
import com.example.clientesoriontek.data.remote.api.RetrofitClient
import com.example.clientesoriontek.data.remote.model.*

class RemoteAddressRepositoryImpl(
    private val apiService: AddressApiService = RetrofitClient.addressApiService
) : RemoteAddressRepository {

    override suspend fun getAddresses(): Result<List<AddressDto>> {
        return try {
            val response = apiService.getAddresses()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAddress(id: Long): Result<AddressDto> {
        return try {
            val response = apiService.getAddress(id)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Address not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAddressesByClient(clientId: Long): Result<List<AddressDto>> {
        return try {
            val response = apiService.getAddressesByClient(clientId)
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createAddress(address: CreateAddressDto): Result<AddressDto> {
        return try {
            val response = apiService.createAddress(address)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error creating address: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateAddress(id: Long, address: UpdateAddressDto): Result<AddressDto> {
        return try {
            val response = apiService.updateAddress(id, address)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error updating address: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteAddress(id: Long): Result<Unit> {
        return try {
            val response = apiService.deleteAddress(id)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error deleting address: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
