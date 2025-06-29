package com.example.data_in_android_practice.room_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data_in_android_practice.room_database.entity.Student
import com.example.data_in_android_practice.room_database.relation.StudentWithSubjects
import kotlinx.coroutines.flow.Flow


@Dao
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertStudent(student: Student)

    @Delete
    suspend fun deleteStudent(student: Student)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateStudent(student: Student)

    @Query(value = "SELECT * FROM students")
    fun getAllStudents(): Flow<List<Student>>

    @Query(value = "SELECT * FROM students")
    fun getAllStudentsWithSubjects(): Flow<List<StudentWithSubjects>>

    @Query(value = "SELECT EXISTS(SELECT studentId FROM students WHERE studentId = :studentId LIMIT 1)")
    suspend fun studentAlreadyExists(studentId: Long): Boolean

    @Query(value = "SELECT * FROM students WHERE studentId = :studentId")
    fun getStudentWithSubjectsById(studentId: Long): StudentWithSubjects

}