package com.example.roomdemo.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class CourseEntity(val courseName: String,
                        val department: String,
                        val courseNum: String,
                        val loc: String,
                        @PrimaryKey(autoGenerate = true) val id:Int=0 )
//data class TaskEntity(val text: String,
//                      @PrimaryKey(autoGenerate = true) val id:Int=0 )
//
//
