package com.example.clientesoriontek.data.remote.model

data class ClientDto(
    val id: Long = 0,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val addresses: List<AddressDto> = emptyList()
)

data class CreateClientDto(
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String
)

data class UpdateClientDto(
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String
)
