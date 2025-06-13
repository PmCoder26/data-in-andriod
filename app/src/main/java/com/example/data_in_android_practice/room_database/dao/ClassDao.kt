package com.example.data_in_android_practice.room_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data_in_android_practice.room_database.entity.Class
import kotlinx.coroutines.flow.Flow


@Dao
interface ClassDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertClass(Class: Class)

    @Delete
    fun deleteClass(Class: Class)

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun updateClass(Class: Class)

    @Query(value = "SELECT * FROM classes")
    fun getAllClasses(): Flow<List<Class>>

    @Query(value = "SELECT EXISTS(SELECT className FROM classes WHERE className = :className) LIMIT 1")
    fun classAlreadyExists(className: String): Boolean

}