package pwm.ar.arpacinema.booking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import pwm.ar.arpacinema.databinding.DateItemBinding
import pwm.ar.arpacinema.databinding.ProfileImageItemBinding
import pwm.ar.arpacinema.model.ProfileImage
import pwm.ar.arpacinema.model.ScreeningDate
import pwm.ar.arpacinema.profile.image.ProfileImageAdapter

class MovieDateAdapter(
    private val dates: MutableList<ScreeningDate>,
    private val onDayClick: (ScreeningDate) -> Unit
) : RecyclerView.Adapter<MovieDateAdapter.MovieDateViewHolder>() {


    var selectedPosition : Int = RecyclerView.NO_POSITION

    inner class MovieDateViewHolder(val binding: DateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

            binding.root.setOnClickListener {
                val clickedPosition = adapterPosition

                if (selectedPosition == clickedPosition) return@setOnClickListener


                val previousSelectedPosition = selectedPosition
                selectedPosition = clickedPosition
                notifyItemChanged(previousSelectedPosition)
                notifyItemChanged(selectedPosition)
                onDayClick(dates[clickedPosition])

            }
        }


        fun bind(date: ScreeningDate, isSelected: Boolean) {

            binding.dayName.text = date.dayOfWeek
            binding.dayNum.text = date.dayOfMonth
            binding.root.isChecked = isSelected

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieDateViewHolder {
        val binding = DateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieDateViewHolder(binding)
    }

    override fun getItemCount(): Int = dates.size

    override fun onBindViewHolder(holder: MovieDateViewHolder, position: Int) {

        val isSelected = position == selectedPosition
        holder.bind(dates[position], isSelected)

    }

    fun setSelectionPosition(position: Int) {
        selectedPosition = position
        notifyDataSetChanged()
    }

    fun updateData(newDates: List<ScreeningDate>) {
        dates.clear()
        dates.addAll(newDates)
        notifyDataSetChanged()
    }

    fun selectFirstItem() {
        if (dates.isNotEmpty() && selectedPosition != 0) {
            val previousSelectedPosition = selectedPosition
            selectedPosition = 0 // Select the first item
            notifyItemChanged(previousSelectedPosition) // Deselect previous item
            notifyItemChanged(selectedPosition) // Select the first item
            onDayClick(dates[0])
        }
    }
}