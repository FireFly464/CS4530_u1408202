package com.example.roomdemo.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "tasks")
data class funFactEntity(
    var text:String,
    var source_url:String?=null,
//    val text: FunFact,
     @PrimaryKey(autoGenerate = false) val id: String )


