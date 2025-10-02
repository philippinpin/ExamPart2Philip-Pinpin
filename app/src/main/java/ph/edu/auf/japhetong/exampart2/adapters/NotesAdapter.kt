
package ph.edu.auf.japhetong.exampart2.adapters


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ph.edu.auf.japhetong.exampart2.databinding.ItemNoteBinding
import ph.edu.auf.japhetong.exampart2.models.NoteModel


class NotesAdapter(private var notes: MutableList<NoteModel>) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {


    inner class NoteViewHolder(val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.binding.tvTitle.text = note.title
        holder.binding.tvContent.text = note.content


        // Example long-press to remove an item (optional). Keep or remove if you don't want it.
        holder.binding.root.setOnLongClickListener {
            notes.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
            true
        }
    }


    override fun getItemCount() = notes.size


    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filtered: List<NoteModel>) {
        notes = filtered.toMutableList()
        notifyDataSetChanged()
    }


    fun addNewItem(note: NoteModel) {
        notes.add(0, note)                // add to top of list
        notifyItemInserted(0)
    }
}
