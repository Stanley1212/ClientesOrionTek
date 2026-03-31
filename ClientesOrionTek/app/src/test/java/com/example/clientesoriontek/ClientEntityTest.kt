package com.example.clientesoriontek

import com.example.clientesoriontek.data.local.entity.Client
import com.example.clientesoriontek.data.local.entity.Address
import org.junit.Assert.assertEquals
import org.junit.Test

class ClientEntityTest {

    @Test
    fun client_initialization_isCorrect() {
        val client = Client(
            id = 1L,
            firstName = "Juan",
            lastName = "Perez",
            phone = "8095551234",
            email = "juan.perez@example.com"
        )

        assertEquals(1L, client.id)
        assertEquals("Juan", client.firstName)
        assertEquals("Perez", client.lastName)
        assertEquals("8095551234", client.phone)
        assertEquals("juan.perez@example.com", client.email)
    }

    @Test
    fun address_initialization_isCorrect() {
        val address = Address(
            addressId = 10L,
            clientId = 1L,
            street = "Calle Falsa 123",
            city = "Santo Domingo",
            sector = "Ensanche Naco",
            houseNumber = "45"
        )

        assertEquals(10L, address.addressId)
        assertEquals(1L, address.clientId)
        assertEquals("Calle Falsa 123", address.street)
        assertEquals("Santo Domingo", address.city)
        assertEquals("Ensanche Naco", address.sector)
        assertEquals("45", address.houseNumber)
    }

    @Test
    fun client_copy_isCorrect() {
        val client = Client(1L, "Juan", "Perez", "123", "juan@mail.com")
        val updatedClient = client.copy(firstName = "Pedro")

        assertEquals(1L, updatedClient.id)
        assertEquals("Pedro", updatedClient.firstName)
        assertEquals("Perez", updatedClient.lastName)
    }
}
