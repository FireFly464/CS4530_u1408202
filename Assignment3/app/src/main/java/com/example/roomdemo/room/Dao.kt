package com.example.roomdemo.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse (course: CourseEntity)

    @Delete
    suspend fun deleteCourse(course: CourseEntity)
    @Query("select * from courses order by id desc")
    fun getAllCourses(): Flow<List<CourseEntity>>
}


