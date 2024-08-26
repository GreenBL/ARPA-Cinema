package pwm.ar.arpacinema.booking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pwm.ar.arpacinema.databinding.DateItemBinding
import pwm.ar.arpacinema.databinding.TimeItemBinding

class MovieTimeAdapter(
    private val times : List<ScreeningTime>,
    private val onImageClick : (ScreeningTime) -> Unit
) : RecyclerView.Adapter<MovieTimeAdapter.MovieTimeViewHolder>() {

    inner class MovieTimeViewHolder(val binding : TimeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onImageClick(times[adapterPosition])
            }
        }
    }

    data class ScreeningTime(
        val time: String = "16:30", // TODO // TODO
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTimeViewHolder {
        val binding = TimeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieTimeViewHolder(binding)
    }

    override fun getItemCount(): Int = times.size

    override fun onBindViewHolder(holder: MovieTimeViewHolder, position: Int) {
        val binding = holder.binding
        ////////////////////////////



    }
}