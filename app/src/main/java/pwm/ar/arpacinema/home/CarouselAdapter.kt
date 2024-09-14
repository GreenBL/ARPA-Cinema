package pwm.ar.arpacinema.home

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.CarouselItemBinding
import pwm.ar.arpacinema.databinding.MenuItemBinding
import pwm.ar.arpacinema.model.Movie
import pwm.ar.arpacinema.util.PlaceholderDrawable

// TODO: THIS IS AN OLD IMPLEMENTATION OF MINE FROM ANOTHER PROJECT, FIX AND REFACTOR BEFORE APP DELIVERY // done but im too lazy to delete the comment

class CarouselAdapter(
    private var heroList: List<Movie>,
    private val onItemClick: (Movie) -> Unit): RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    // ViewHolder holds the view refs

    inner class CarouselViewHolder(val binding: CarouselItemBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClick(heroList[adapterPosition])
            }
        }
    }

    // on creation inflate the layout and return the view holder

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CarouselViewHolder {
        val binding = CarouselItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CarouselViewHolder(binding)
    }

    // bind view holder with data from the list using position as index [this will be done for each item]

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val currentHero = heroList[position]

        val binding = holder.binding
        val imageViuh = binding.movieImage

        val placeholder = PlaceholderDrawable.getPlaceholderDrawable()

        Glide.with(holder.itemView.context)
            .load(currentHero.posterUrl)
            .placeholder(placeholder)
            .downsample(DownsampleStrategy.CENTER_OUTSIDE)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageViuh)
    }

    override fun getItemCount(): Int = heroList.size

    fun updateData(movieList: List<Movie>) {
        heroList = movieList
        notifyDataSetChanged() // Notify the adapter that the data has changed
    }

}