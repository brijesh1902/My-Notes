package com.brizzs.mynotes.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.brizzs.mynotes.models.Notes

@Dao
interface NotesDao {

    @Insert(entity = Notes::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(notes : Notes)

    @Delete
    suspend fun deleteNote(notes: Notes)

    @Update
    suspend fun updateNote(notes: Notes)

    @Query("SELECT * FROM notes GROUP BY id ORDER BY date DESC")
    fun getNotes() : LiveData<List<Notes>>

}