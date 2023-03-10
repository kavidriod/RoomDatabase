package com.ninjanotes.mynotes.db

import androidx.room.*

@Dao
interface NoteDao {

    /**
     * Daos for AsyncTask
     */
/*
    @Insert
    fun addNote(note: Note)

    @Query("SELECT * FROM note")
    fun getAllNotes() : List<Note>

    @Insert
    fun addMultipleNotes(vararg note: Note)
*/

    /**
     * suspend added for coroutine
     */
    @Insert
    suspend fun addNote(note: Note)

    @Query("SELECT * FROM note ORDER BY id DESC")
    suspend fun getAllNotes() : List<Note>

    @Insert
    suspend fun addMultipleNotes(vararg note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}