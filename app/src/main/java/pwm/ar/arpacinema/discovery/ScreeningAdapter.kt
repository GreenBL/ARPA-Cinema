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
    private val isLoading : Boolean,
    private val onItemClick: (ShowingItem) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1

    }

    // sets listener on the cats
    inner class ShowingViewHolder(val binding: ProjectionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // todo
            binding.root.setOnClickListener {
                onItemClick(showingItems[adapterPosition])
            }
        }
    }

    inner class LoadingViewHolder(val binding: LoadScreeningBinding) :
        RecyclerView.ViewHolder(binding.root)


    // layout inflation and binding of the views
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
       // val binding = ProjectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
       // return ShowingViewHolder(binding)
        val binding = LoadScreeningBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadingViewHolder(binding)
    }

    // connecting the view to the model...
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ShowingViewHolder) {
            val showingItem = showingItems[position]
            val binding = holder.binding

        }

        //val binding = holder.binding
//        val categoryItem = categoryItems[position]
//
//        // assigns the provided icon
//        holder.binding.catIco.setImageResource(categoryItem.categoryIconResId)
//
//        // assigns the provided cat name (note: longest string I tried is "Avventura" and it fits)
//        holder.binding.catTitle.text = categoryItem.categoryName
        //val image = binding.shapeableImageView2

//
//        Glide.with(image.context)
//            .load("https://picsum.photos/150/150")
//            .transition(DrawableTransitionOptions.withCrossFade())
//            .into(image)

    }

    override fun getItemCount(): Int = showingItems.size

}