package com.brizzs.mynotes.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.brizzs.mynotes.adapter.NotesAdapter
import com.brizzs.mynotes.databinding.ActivityMainBinding
import com.brizzs.mynotes.models.Notes
import com.brizzs.mynotes.utils.InAppUpdate
import com.brizzs.mynotes.viewModel.NotesViewModel
import kotlinx.coroutines.flow.merge

class MainActivity : AppCompatActivity() {

    lateinit var bindings : ActivityMainBinding
    lateinit var viewModel: NotesViewModel
    lateinit var adapter: NotesAdapter
    private lateinit var inAppUpdate: InAppUpdate
    var currentList: List<Notes> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindings.root)

        inAppUpdate = InAppUpdate(this)
        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        bindings.recyclerView.setHasFixedSize(true)

        adapter = NotesAdapter()
        bindings.recyclerView.adapter = adapter

    }

    private fun setAdapter() {
        viewModel.getNotes().observe(this){list ->
            if (list.isNotEmpty()) {
                currentList = list
                list?.let { adapter.submitList(list) }
                bindings.tvNot.visibility = View.GONE
                bindings.recyclerView.visibility = View.VISIBLE
            } else {
                bindings.recyclerView.visibility = View.GONE
                bindings.tvNot.visibility = View.VISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        inAppUpdate.onResume()

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
                if (newText?.isNotEmpty() == true)
                    filter(newText)
                else setAdapter()

                return true
            }
        })

    }

    fun filter(search: String) {
        val list = ArrayList<Notes>()
        for (item in currentList){
            if (item.title?.lowercase()?.contains(search.lowercase())!! || item.notes?.lowercase()?.contains(search.lowercase()) == true)
                list.add(item)
            adapter.submitList(list)
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        inAppUpdate.onActivityResult(requestCode,resultCode, data)
    }
    override fun onDestroy() {
        super.onDestroy()
        inAppUpdate.onDestroy()
    }

}