package com.example.roomdemo

import com.example.roomdemo.room.CourseDao
import com.example.roomdemo.room.CourseEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class Repository (val scope: CoroutineScope, private val dao: CourseDao) {

    val allCourses: Flow<List<CourseEntity>> = dao.getAllCourses()

    fun addCourse(item: CourseEntity) {
        scope.launch {
            delay(1000) // simulates network delay

            dao.insertCourse(item)
        }
    }

    fun deleteCourse(item: CourseEntity) {
        scope.launch {
            delay(1000) // simulates network delay

            dao.deleteCourse(item)
        }
    }
}