package com.example.data_in_android_practice.room_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.data_in_android_practice.room_database.entity.Subject
import com.example.data_in_android_practice.room_database.relation.SubjectsWithStudents
import kotlinx.coroutines.flow.Flow


@Dao
interface SubjectDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertSubject(Subject: Subject)

    @Delete
    suspend fun deleteSubject(Subject: Subject)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateSubject(Subject: Subject)

    @Query(value = "SELECT * FROM subjects")
    fun getAllSubjects(): Flow<List<Subject>>

    @Query(value = "SELECT * FROM subjects")
    fun getAllSubjectsWithStudents(): Flow<List<SubjectsWithStudents>>

    @Query(value = "SELECT EXISTS(SELECT subjectId FROM subjects WHERE name = :name LIMIT 1 )")
    suspend fun subjectAlreadyExists(name: String): Boolean

}
