package com.example.clientesoriontek.data.local.dao

import androidx.room.*
import com.example.clientesoriontek.data.local.entity.Address
import com.example.clientesoriontek.data.local.entity.Client
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClient(client: Client): Long

    @Update
    suspend fun updateClient(client: Client)

    @Delete
    suspend fun deleteClient(client: Client)

    @Query("SELECT * FROM clients ORDER BY firstName ASC")
    fun getAllClients(): Flow<List<Client>>

    @Query("SELECT * FROM clients WHERE id = :clientId")
    suspend fun getClientById(clientId: Long): Client?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddress(address: Address): Long

    @Update
    suspend fun updateAddress(address: Address)

    @Delete
    suspend fun deleteAddress(address: Address)

    @Query("SELECT * FROM addresses WHERE clientId = :clientId")
    fun getAddressesForClient(clientId: Long): Flow<List<Address>>
}
