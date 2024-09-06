package pwm.ar.arpacinema.booking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pwm.ar.arpacinema.databinding.DateItemBinding
import pwm.ar.arpacinema.databinding.TimeItemBinding
import pwm.ar.arpacinema.model.ScreeningDate

class MovieTimeAdapter(
    private val times: List<ScreeningDate>,
    private val onTimeClick: (ScreeningDate) -> Unit
) : RecyclerView.Adapter<MovieTimeAdapter.MovieTimeViewHolder>() {


    private var selectedPosition = RecyclerView.NO_POSITION

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


        fun bind(date: ScreeningDate, isSelected: Boolean) {

            binding.time.text = date.formattedTime
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
}