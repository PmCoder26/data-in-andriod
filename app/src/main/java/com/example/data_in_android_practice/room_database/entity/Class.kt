package com.example.data_in_android_practice.room_database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "classes",
    indices = [Index(value = ["className"], unique = true)]
)
data class Class(
    @PrimaryKey
    val className: String = "A"
)
