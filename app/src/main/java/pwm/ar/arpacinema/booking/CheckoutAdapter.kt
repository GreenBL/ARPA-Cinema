package pwm.ar.arpacinema.booking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pwm.ar.arpacinema.databinding.SmallTicketItemBinding
import pwm.ar.arpacinema.dev.Selection
import java.util.Locale

class CheckoutAdapter (
    private val selectionItems: List<Selection>,
) : RecyclerView.Adapter<CheckoutAdapter.SelectionViewHolder>() {

    inner class SelectionViewHolder(val binding: SmallTicketItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(selection: Selection) {
                binding.elTitoloDelFilm.text = selection.movieTitle
                binding.seatString.text = selection.seatCustomString
                val fromattedString = String.format(Locale.ITALY,"%.2f", selection.price.toDouble())
                binding.elCosto.text = fromattedString
                binding.date.text = selection.showDate
                binding.timeScreen.text = selection.showTime
            }
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionViewHolder {
        val binding = SmallTicketItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SelectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectionViewHolder, position: Int) {
        val menuItem = selectionItems[position]
        holder.bind(menuItem)
    }

    override fun getItemCount(): Int = selectionItems.size
}