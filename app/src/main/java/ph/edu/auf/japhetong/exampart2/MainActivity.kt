package ph.edu.auf.japhetong.exampart2


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import ph.edu.auf.japhetong.exampart2.adapters.NotesAdapter
import ph.edu.auf.japhetong.exampart2.databinding.ActivityMainBinding
import ph.edu.auf.japhetong.exampart2.dialog.AddNoteDialog
import ph.edu.auf.japhetong.exampart2.helpers.PrefsHelper
import ph.edu.auf.japhetong.exampart2.models.NoteModel


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NotesAdapter
    private var notes = mutableListOf<NoteModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Load saved notes
        notes = PrefsHelper.loadNotes(this)
        adapter = NotesAdapter(notes)


        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter


        // Search notes
        binding.etSearch.addTextChangedListener { text ->
            val filtered = notes.filter { it.title.contains(text.toString(), ignoreCase = true) }
            adapter.filterList(filtered)
        }


        // FAB -> Add note dialog
        binding.fabAdd.setOnClickListener {
            val dialog = AddNoteDialog { newNote ->
                // add and persist
                notes.add(0, newNote)
                adapter.addNewItem(newNote)
                PrefsHelper.saveNotes(this, notes)
                binding.recyclerView.scrollToPosition(0)
            }
            dialog.show(supportFragmentManager, "AddNoteDialog")
        }




        binding.btnOpenAbout.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }




        binding.btnOpenWebsite.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, "https://developer.android.com".toUri())
            startActivity(intent)
        }


        binding.fabAdd.setOnClickListener {
            val dialog = AddNoteDialog { newNote ->


                notes.add(0, newNote)




                PrefsHelper.saveNotes(this, notes)




                adapter.filterList(notes)


                binding.recyclerView.scrollToPosition(0)
            }
            dialog.show(supportFragmentManager, "AddNoteDialog")
        }


    }
}
