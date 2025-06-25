package com.example.data_in_android_practice.room_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.example.data_in_android_practice.room_database.entity.Address


@Dao
interface AddressDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAddress(address: Address): Long       // by default returns id.

    @Delete
    suspend fun deleteAddress(address: Address)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateAddress(address: Address)


}