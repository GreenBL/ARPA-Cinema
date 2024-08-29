package pwm.ar.arpacinema.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import pwm.ar.arpacinema.common.LargeMenuAdapter
import pwm.ar.arpacinema.common.MenuItem
import pwm.ar.arpacinema.databinding.LargeMenuItemBinding
import pwm.ar.arpacinema.databinding.PopularItemBinding

class PopularAdapter(
    private val popularItems: List<CarouselItem>,
    private val onItemClick: (CarouselItem) -> Unit
) : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {

    inner class PopularViewHolder(val binding: PopularItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClick(popularItems[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val binding = PopularItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PopularViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val binding = holder.binding

        val popularItem = popularItems[position]

        binding.movieTitle.text = popularItem.movieTitle

        Glide.with(holder.itemView.context)
            .load(popularItem.movieImageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.movieImage)

        // to refactor

        val movieLogo = binding.movieLogo

        Glide.with(holder.itemView.context)
            .load("https://cdn2.steamgriddb.com/logo/bc7ee059ae7fecbf11cc7a8855671b6e.png")
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(movieLogo)
    }

    override fun getItemCount(): Int = popularItems.size
}