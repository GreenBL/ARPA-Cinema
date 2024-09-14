package pwm.ar.arpacinema.booking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pwm.ar.arpacinema.databinding.SeatCheckoutItemBinding
import pwm.ar.arpacinema.databinding.SmallTicketItemBinding
import pwm.ar.arpacinema.dev.Selection
import pwm.ar.arpacinema.model.Seat
import pwm.ar.arpacinema.util.SeatInterpreter
import java.util.Locale

class CheckoutAdapter (
    private val seatSelection: List<Int>,
) : RecyclerView.Adapter<CheckoutAdapter.SelectionViewHolder>() {

    inner class SelectionViewHolder(val binding: SeatCheckoutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(seat: Seat) {
                binding.root.text = seat.identifier.uppercase()
            }
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionViewHolder {
        val binding = SeatCheckoutItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SelectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectionViewHolder, position: Int) {
        val seatIndex = seatSelection[position]
        val seat = SeatInterpreter.getSeatObject(seatIndex)
        holder.bind(seat)
    }

    override fun getItemCount(): Int = seatSelection.size
}