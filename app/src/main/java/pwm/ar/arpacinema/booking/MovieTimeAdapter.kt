package pwm.ar.arpacinema.booking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pwm.ar.arpacinema.databinding.DateItemBinding
import pwm.ar.arpacinema.databinding.TimeItemBinding
import pwm.ar.arpacinema.model.ScreeningDate
import pwm.ar.arpacinema.model.ScreeningTime

class MovieTimeAdapter(
    private val times: MutableList<ScreeningTime>,
    private val onTimeClick: (ScreeningTime) -> Unit
) : RecyclerView.Adapter<MovieTimeAdapter.MovieTimeViewHolder>() {


    var selectedPosition = RecyclerView.NO_POSITION

    inner class MovieTimeViewHolder(val binding: TimeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

            binding.root.setOnClickListener {
                val clickedPosition = adapterPosition

                if (selectedPosition == clickedPosition) return@setOnClickListener

                onTimeClick(times[clickedPosition])
                val previousSelectedPosition = selectedPosition
                selectedPosition = clickedPosition
                notifyItemChanged(previousSelectedPosition)
                notifyItemChanged(selectedPosition)

            }
        }


        fun bind(time: ScreeningTime, isSelected: Boolean) {

            binding.time.text = time.formattedTime
            binding.sala.text = time.auditorium
            binding.root.isChecked = isSelected

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTimeViewHolder {
        val binding = TimeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieTimeViewHolder(binding)
    }

    override fun getItemCount(): Int = times.size

    override fun onBindViewHolder(holder: MovieTimeViewHolder, position: Int) {

        val isSelected = position == selectedPosition
        holder.bind(times[position], isSelected)

    }

    fun setSelectionPosition(position: Int) {
        selectedPosition = position
        notifyDataSetChanged()
    }

    fun updateData(newDates: List<ScreeningTime>) {
        times.clear()
        times.addAll(newDates)
        notifyDataSetChanged()
    }

    fun selectFirstItem() {
        if (times.isNotEmpty() && selectedPosition != 0) {
            val previousSelectedPosition = selectedPosition
            selectedPosition = 0 // Select the first item
            notifyItemChanged(previousSelectedPosition) // Deselect previous item
            notifyItemChanged(selectedPosition) // Select the first item
            onTimeClick(times[0])
        }
    }
}