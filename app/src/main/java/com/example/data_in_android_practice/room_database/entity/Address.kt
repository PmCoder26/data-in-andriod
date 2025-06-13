package com.example.data_in_android_practice.room_database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "addresses")
data class Address(
    @PrimaryKey(autoGenerate = true)
    val addressId: Long = 0,
    val city: String,
    val zipCode: Int
)
