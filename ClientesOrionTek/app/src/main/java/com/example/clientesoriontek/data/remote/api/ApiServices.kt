package com.example.clientesoriontek.data.remote.api

import com.example.clientesoriontek.data.remote.model.*
import retrofit2.Response
import retrofit2.http.*

interface ClientApiService {

    @GET("api/clients")
    suspend fun getClients(): Response<List<ClientDto>>

    @GET("api/clients/{id}")
    suspend fun getClient(@Path("id") id: Long): Response<ClientDto>

    @POST("api/clients")
    suspend fun createClient(@Body client: CreateClientDto): Response<ClientDto>

    @PUT("api/clients/{id}")
    suspend fun updateClient(@Path("id") id: Long, @Body client: UpdateClientDto): Response<ClientDto>

    @DELETE("api/clients/{id}")
    suspend fun deleteClient(@Path("id") id: Long): Response<Unit>
}

interface AddressApiService {

    @GET("api/addresses")
    suspend fun getAddresses(): Response<List<AddressDto>>

    @GET("api/addresses/{id}")
    suspend fun getAddress(@Path("id") id: Long): Response<AddressDto>

    @GET("api/addresses/client/{clientId}")
    suspend fun getAddressesByClient(@Path("clientId") clientId: Long): Response<List<AddressDto>>

    @POST("api/addresses")
    suspend fun createAddress(@Body address: CreateAddressDto): Response<AddressDto>

    @PUT("api/addresses/{id}")
    suspend fun updateAddress(@Path("id") id: Long, @Body address: UpdateAddressDto): Response<AddressDto>

    @DELETE("api/addresses/{id}")
    suspend fun deleteAddress(@Path("id") id: Long): Response<Unit>
}
