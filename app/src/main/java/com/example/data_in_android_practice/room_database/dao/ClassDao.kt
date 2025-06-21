package com.example.data_in_android_practice.room_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data_in_android_practice.room_database.entity.Class
import com.example.data_in_android_practice.room_database.relation.ClassesWithStudents
import kotlinx.coroutines.flow.Flow


@Dao
interface ClassDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    // Note - don't forget to mention 'suspend', if not mentioned room will execute on main thread which is blocking call.
    // either mention 'suspend'(room will handle threads automatically) or use in the viewModelScope.launch(Dispatchers.IO) manually.
    // using both Dispatchers.IO and suspend is recommended and best practice
    suspend fun insertClass(Class: Class)

    @Delete
    suspend fun deleteClass(Class: Class)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateClass(Class: Class)

    @Query(value = "SELECT * FROM classes")
    fun getAllClasses(): Flow<List<Class>>

    @Query(value = "SELECT EXISTS(SELECT className FROM classes WHERE className = :className) LIMIT 1")
    suspend fun classAlreadyExists(className: String): Boolean

    @Query(value = "SELECT * FROM classes")
    fun getClassesWithStudents(): Flow<List<ClassesWithStudents>>

}