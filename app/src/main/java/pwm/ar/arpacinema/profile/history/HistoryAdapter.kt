package pwm.ar.arpacinema.profile.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pwm.ar.arpacinema.databinding.SmallTicketItemBinding
import pwm.ar.arpacinema.dev.Selection
import java.util.Locale

class HistoryAdapter (
    private val selectionItems: List<Selection>,
) : RecyclerView.Adapter<HistoryAdapter.SmallTicketViewHolder>() {

    inner class SmallTicketViewHolder(val binding: SmallTicketItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(selection: Selection) {
            binding.elTitoloDelFilm.text = selection.movieTitle
            binding.seatString.text = selection.seatCustomString
            binding.elCosto.visibility = View.GONE
            binding.elSymbuloDeEuros.visibility = View.GONE
            binding.date.text = selection.showDate
            binding.timeScreen.text = selection.showTime
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
}