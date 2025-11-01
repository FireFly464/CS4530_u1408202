package com.example.roomdemo.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [funFactEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
