package com.example.roomdemo.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.roomdemo.FunFact

@Entity(tableName = "tasks")
data class TaskEntity(
    val text: FunFact,
    @PrimaryKey(autoGenerate = true) val id: Int =0 )


