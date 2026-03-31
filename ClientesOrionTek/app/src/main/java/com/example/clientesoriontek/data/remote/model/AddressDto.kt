package com.example.clientesoriontek.data.remote.model

data class AddressDto(
    val addressId: Long = 0,
    val clientId: Long,
    val street: String,
    val city: String,
    val sector: String,
    val houseNumber: String
)

data class CreateAddressDto(
    val clientId: Long,
    val street: String,
    val city: String,
    val sector: String,
    val houseNumber: String
)

data class UpdateAddressDto(
    val street: String,
    val city: String,
    val sector: String,
    val houseNumber: String
)
