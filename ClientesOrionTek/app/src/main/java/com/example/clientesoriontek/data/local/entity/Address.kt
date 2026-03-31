package com.example.clientesoriontek.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "addresses",
    foreignKeys = [
        ForeignKey(
            entity = Client::class,
            parentColumns = ["id"],
            childColumns = ["clientId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["clientId"])]
)
data class Address(
    @PrimaryKey(autoGenerate = true) val addressId: Long = 0,
    val clientId: Long,
    val street: String,
    val city: String,
    val sector: String,
    val houseNumber: String
)
