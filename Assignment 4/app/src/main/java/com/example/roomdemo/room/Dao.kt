package com.example.roomdemo.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask (task: funFactEntity)

    @Query("select * from tasks order by id desc")
    fun getAllTasks(): Flow<List<funFactEntity>>
}


