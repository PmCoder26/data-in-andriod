package com.example.data_in_android_practice.room_database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.data_in_android_practice.room_database.entity.Address
import com.example.data_in_android_practice.room_database.entity.Student


data class StudentWithAddress(
    @Embedded
    val student: Student,
    @Relation(
        parentColumn = "addressId",         // here, parent is Student
        entityColumn = "addressId"
    )
    val address: Address
)