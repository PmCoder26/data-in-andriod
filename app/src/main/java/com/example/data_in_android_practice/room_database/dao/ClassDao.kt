package com.example.data_in_android_practice.room_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.example.data_in_android_practice.room_database.entity.Class


@Dao
interface ClassDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertClass(Class: Class)

    @Delete
    fun deleteClass(Class: Class)

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun updateClass(Class: Class)
    
}