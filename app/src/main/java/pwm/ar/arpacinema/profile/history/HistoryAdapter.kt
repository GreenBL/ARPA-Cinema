package pwm.ar.arpacinema.profile.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pwm.ar.arpacinema.databinding.SmallTicketItemBinding
import pwm.ar.arpacinema.dev.Selection
import pwm.ar.arpacinema.model.Ticket
import java.time.format.DateTimeFormatter
import java.util.Locale

class HistoryAdapter (
    private val selectionItems: MutableList<Ticket>,
) : RecyclerView.Adapter<HistoryAdapter.SmallTicketViewHolder>() {

    inner class SmallTicketViewHolder(val binding: SmallTicketItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(selection: Ticket) {
            binding.elTitoloDelFilm.text = selection.filmTitle
            binding.textView35.text = "${selection.formattedDate} ${selection.year}"
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallTicketViewHolder {
        val binding = SmallTicketItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SmallTicketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SmallTicketViewHolder, position: Int) {
        val menuItem = selectionItems[position]
        holder.bind(menuItem)
    }

    override fun getItemCount(): Int = selectionItems.size

    fun updateList(newList: List<Ticket>) {
        selectionItems.clear()
        selectionItems.addAll(newList)
        notifyDataSetChanged()
    }
}