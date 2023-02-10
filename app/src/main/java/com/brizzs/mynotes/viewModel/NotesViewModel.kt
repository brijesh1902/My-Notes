package com.brizzs.mynotes.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.brizzs.mynotes.dao.NotesDao
import com.brizzs.mynotes.database.NotesDatabase
import com.brizzs.mynotes.models.Notes
import com.brizzs.mynotes.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    var notesDao: NotesDao
    private var repository: NotesRepository

    init {
        notesDao = NotesDatabase.getDatabase(application).getNoteDao()
        repository = NotesRepository(notesDao)
    }

    fun getNotes() : LiveData<List<Notes>> {
        return repository.allNotes
    }

    fun insertNote(notes: Notes) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(notes)
    }

    fun updateNote(notes: Notes) = viewModelScope.launch(Dispatchers.IO){
        repository.update(notes)
    }

    fun deleteNote(notes: Notes) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(notes)
    }

}