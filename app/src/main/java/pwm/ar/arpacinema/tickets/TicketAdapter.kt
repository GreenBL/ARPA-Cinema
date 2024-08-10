package pwm.ar.arpacinema.tickets

import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.TicketItemBinding
import pwm.ar.arpacinema.dev.TicketItem


import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class TicketAdapter(
    private val menuItems: List<TicketItem>,
    private val onItemClick: (TicketItem, ShapeableImageView) -> Unit
) : RecyclerView.Adapter<TicketAdapter.TicketViewHolder>() {

    inner class TicketViewHolder(val binding: TicketItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClick(menuItems[adapterPosition], binding.moviePoster)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val binding = TicketItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TicketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val ticketItem = menuItems[position]

        holder.binding.filmDate.text = ticketItem.date
        holder.binding.movieTime.text = ticketItem.time
        holder.binding.movieTitle.text = ticketItem.title
        holder.binding.moviePoster.transitionName = "shared_poster_${ticketItem.title}"
    }

    override fun getItemCount(): Int = menuItems.size
}