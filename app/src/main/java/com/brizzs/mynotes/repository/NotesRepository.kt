package com.brizzs.mynotes.repository

import androidx.lifecycle.LiveData
import com.brizzs.mynotes.dao.NotesDao
import com.brizzs.mynotes.models.Notes

class NotesRepository(private val notesDao: NotesDao) {

    val allNotes : LiveData<List<Notes>> = notesDao.getNotes()

    suspend fun insert(notes: Notes){
        notesDao.insertNote(notes)
    }

    suspend fun delete(notes: Notes){
        notesDao.deleteNote(notes)
    }

    suspend fun update(notes: Notes){
        notesDao.updateNote(notes)
    }

}