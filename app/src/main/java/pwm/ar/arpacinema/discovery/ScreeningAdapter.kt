package pwm.ar.arpacinema.discovery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.TransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.LoadScreeningBinding
import pwm.ar.arpacinema.databinding.ProjectionItemBinding
import pwm.ar.arpacinema.dev.ShowingItem
import pwm.ar.arpacinema.model.Movie
import pwm.ar.arpacinema.util.PlaceholderDrawable

class ScreeningAdapter(
    private var showingItems: List<Movie>,
    private val onItemClick: (Movie) -> Unit
) : RecyclerView.Adapter<ScreeningAdapter.ScreeningViewHolder>() {

    private var shouldAnimate = true

    inner class ScreeningViewHolder(val binding: ProjectionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // todo
            binding.root.setOnClickListener {
                onItemClick(showingItems[adapterPosition])
            }
        }
    }

    // layout inflation and binding of the views
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScreeningAdapter.ScreeningViewHolder {
       val binding = ProjectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
       return ScreeningViewHolder(binding)

    }

    // connecting the view to the model...
    override fun onBindViewHolder(holder: ScreeningAdapter.ScreeningViewHolder, position: Int) {

        val showingItem = showingItems[position]
        val binding = holder.binding

        val root = binding.root



        val name = binding.movietitle
        val rating = binding.ratingBar
        val category = binding.categoryQualifier
        val producer = binding.producer

        name.text = showingItem.title
        rating.rating = showingItem.rating.toFloat()
        category.text = showingItem._categories
        producer.text = showingItem.producer

        val image = binding.poster


        Glide.with(image.context)
            .load(showingItem.posterUrl)
            .placeholder(PlaceholderDrawable.getPlaceholderDrawable())
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(image)

        if (shouldAnimate) {
            // Trigger your fade-in animation here
            holder.itemView.alpha = 0f
            holder.itemView.animate().alpha(1f).setDuration(300).start()
        }

        // Reset the animation flag after the first binding
        if (position == showingItems.size - 1) {
            shouldAnimate = false
        }
    }

    override fun getItemCount(): Int = showingItems.size

    fun updateData(newData: List<Movie>) {
        showingItems = newData
        notifyDataSetChanged()

    }

}