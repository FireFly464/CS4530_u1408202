package com.example.roomdemo

import com.example.roomdemo.room.TaskDao
import com.example.roomdemo.room.funFactEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class Repository (val scope: CoroutineScope, private val dao: TaskDao) {

    val allTasks: Flow<List<funFactEntity?>> = dao.getAllTasks()

    fun addTask(task: funFactEntity) {
        scope.launch {
            delay(1000) // simulates network delay
            val taskObj = task
            dao.insertTask(taskObj)
        }
    }
}