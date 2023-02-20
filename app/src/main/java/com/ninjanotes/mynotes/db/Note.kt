package com.ninjanotes.mynotes.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
@Entity
data  class Note (
        @PrimaryKey(autoGenerate = true)
    val id:Int,
       // @ColumnInfo(name = "note_title")
    val title:String,
    val note:String
        )*/


@Entity
data  class Note (
    val title:String,
    val note:String
):java.io.Serializable{
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}

