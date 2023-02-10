package com.brizzs.mynotes.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.brizzs.mynotes.adapter.NotesAdapter
import com.brizzs.mynotes.databinding.ActivityMainBinding
import com.brizzs.mynotes.models.Notes
import com.brizzs.mynotes.viewModel.NotesViewModel
import kotlinx.coroutines.flow.merge

class MainActivity : AppCompatActivity() {

    lateinit var bindings : ActivityMainBinding
    lateinit var viewModel: NotesViewModel
    lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindings.root)

        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        bindings.recyclerView.setHasFixedSize(true)

        adapter = NotesAdapter()
        bindings.recyclerView.adapter = adapter

    }

    private fun setAdapter() {
        viewModel.getNotes().observe(this){list ->
            list?.let { adapter.submitList(list) }
        }
    }

    override fun onResume() {
        super.onResume()

        setAdapter()

        bindings.fabAdd.setOnClickListener{
            val intent = Intent(this, AddNotesActivity::class.java)
            startActivity(intent)
        }

        bindings.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrBlank())
                    adapter.filter(newText)
                else setAdapter()

                return true
            }
        })

    }

}