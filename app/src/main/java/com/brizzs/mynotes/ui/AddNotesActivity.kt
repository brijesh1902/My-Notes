package com.brizzs.mynotes.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.brizzs.mynotes.R
import com.brizzs.mynotes.databinding.ActivityAddNotesBinding
import com.brizzs.mynotes.databinding.ActivityMainBinding
import com.brizzs.mynotes.models.Notes
import com.brizzs.mynotes.utils.CURRENT_NOTE
import com.brizzs.mynotes.utils.getTimestamp
import com.brizzs.mynotes.viewModel.NotesViewModel
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class AddNotesActivity : AppCompatActivity() {

    private val TAG = "AddNotesActivity"

    lateinit var bindings : ActivityAddNotesBinding
    lateinit var notes: Notes
    var isUpdate = false
    private lateinit var viewModel : NotesViewModel
    private lateinit var title : String
    private lateinit var note : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(bindings.root)

        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        try {
            notes = intent.getSerializableExtra(CURRENT_NOTE) as Notes

            bindings.etTitle.setText(notes.title)
            bindings.etNotes.setText(notes.notes)

            isUpdate = true

        } catch (e : Exception) {
            Log.e(TAG, "onCreate: "+e.message )
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {

        title = bindings.etTitle.text.toString()
        note = bindings.etNotes.text.toString()

        if (title.isNotEmpty() || note.isNotEmpty()) {
            if (isUpdate) viewModel.updateNote(Notes(notes.id, title, note, getTimestamp(), notes.randomColor))
            else viewModel.insertNote(Notes(null, title, note, getTimestamp(), randomColor()))

            finish()
        }
        super.onBackPressed()

    }

    @SuppressLint("SimpleDateFormat")
    override fun onResume() {
        super.onResume()

        if (isUpdate) bindings.delete.visibility = View.VISIBLE

        bindings.back.setOnClickListener{
            title = bindings.etTitle.text.toString()
            note = bindings.etNotes.text.toString()
            if (title.isNotEmpty() || note.isNotEmpty()) {
                if (isUpdate) viewModel.updateNote(Notes(notes.id, title, note, getTimestamp(), notes.randomColor))
                else viewModel.insertNote(Notes(null, title, note, getTimestamp(), randomColor()))

            }
            finish()
        }

        bindings.delete.setOnClickListener{
            viewModel.deleteNote(notes)
            finish()
        }

        bindings.save.setOnClickListener{

            title = bindings.etTitle.text.toString()
            note = bindings.etNotes.text.toString()

            if (title.isNotEmpty() || note.isNotEmpty()) {
                if (isUpdate) viewModel.updateNote(Notes(notes.id, title, note, getTimestamp(), notes.randomColor))
                 else viewModel.insertNote(Notes(null, title, note, getTimestamp(), randomColor()))

                finish()
            } else {
                Toast.makeText(this@AddNotesActivity, "Please enter some notes", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

        }

    }

    private fun randomColor(): Int {
        val list = ArrayList<Int>()
        list.add(R.color.teal_200)
        list.add(R.color.purple_200)
        list.add(R.color.color1)
        list.add(R.color.color2)
        list.add(R.color.color3)
        list.add(R.color.color4)
        list.add(R.color.color5)
        list.add(R.color.color6)
        list.add(R.color.color7)
        list.add(R.color.color8)
        list.add(R.color.color9)
        list.add(R.color.color10)
        list.add(R.color.color11)
        list.add(R.color.color12)
        list.add(R.color.color13)
        list.add(R.color.color14)
        list.add(R.color.color15)
        list.add(R.color.color16)
        list.add(R.color.color17)
        list.add(R.color.color18)
        list.add(R.color.color19)
        list.add(R.color.color20)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)

        return list[randomIndex]
    }


}