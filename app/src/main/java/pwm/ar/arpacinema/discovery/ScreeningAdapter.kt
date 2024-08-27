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

class ScreeningAdapter(
    private val showingItems: List<ShowingItem>,
    private val onItemClick: (ShowingItem) -> Unit
) : RecyclerView.Adapter<ScreeningAdapter.ScreeningViewHolder>() {


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

        root.alpha = 0f
        root.animate().alpha(1f).setDuration(250).start()


        val image = binding.shapeableImageView2


        Glide.with(image.context)
            .load("https://picsum.photos/150/150")
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(image)

    }

    override fun getItemCount(): Int = showingItems.size

}