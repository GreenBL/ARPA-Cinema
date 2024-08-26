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
import pwm.ar.arpacinema.profile.image.ProfileImageAdapter

class MovieDateAdapter(
    private val dates : List<ScreeningDate>,
    private val onImageClick : (ScreeningDate) -> Unit
) : RecyclerView.Adapter<MovieDateAdapter.MovieDateViewHolder>() {

    inner class MovieDateViewHolder(val binding : DateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onImageClick(dates[adapterPosition])
            }
        }
    }

    data class ScreeningDate(
        val date: String = "Marzo", // TODO // TODO
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieDateViewHolder {
        val binding = DateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieDateViewHolder(binding)
    }

    override fun getItemCount(): Int = dates.size

    override fun onBindViewHolder(holder: MovieDateViewHolder, position: Int) {
        val binding = holder.binding
        ////////////////////////////



    }
}