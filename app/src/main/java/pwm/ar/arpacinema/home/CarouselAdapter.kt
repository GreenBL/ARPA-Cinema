package pwm.ar.arpacinema.home

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.CarouselItemBinding

// TODO: THIS IS AN OLD IMPLEMENTATION OF MINE FROM ANOTHER PROJECT, FIX AND REFACTOR BEFORE APP DELIVERY // done but im too lazy to delete the comment

class CarouselAdapter(
    private val heroList: List<CarouselItem>,
    private val onItemClick: (CarouselItem) -> Unit): RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    // ViewHolder holds the view refs

    inner class CarouselViewHolder(val binding: CarouselItemBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            // stuff
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

        Glide.with(holder.itemView.context)
            .load(currentHero.movieImageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageViuh)
    }

    override fun getItemCount(): Int = heroList.size

}