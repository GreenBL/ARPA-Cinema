package pwm.ar.arpacinema.discovery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pwm.ar.arpacinema.databinding.CategoryItemBinding
import pwm.ar.arpacinema.databinding.ProjectionItemBinding
import pwm.ar.arpacinema.dev.ShowingItem
import pwm.ar.arpacinema.home.CategoryItem

class ShowingAdapter(
    private val showingItems: List<ShowingItem>,
    private val onItemClick: (ShowingItem) -> Unit
) : RecyclerView.Adapter<ShowingAdapter.ShowingViewHolder>() {

    // sets listener on the cats
    inner class ShowingViewHolder(val binding: ProjectionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // todo
            binding.textView18.setOnClickListener {
                onItemClick(showingItems[adapterPosition])
            }
        }
    }

    // layout inflation and binding of the views
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShowingAdapter.ShowingViewHolder {
        val binding = ProjectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowingViewHolder(binding)
    }

    // connecting the view to the model...
    override fun onBindViewHolder(holder: ShowingAdapter.ShowingViewHolder, position: Int) {
//        val categoryItem = categoryItems[position]
//
//        // assigns the provided icon
//        holder.binding.catIco.setImageResource(categoryItem.categoryIconResId)
//
//        // assigns the provided cat name (note: longest string I tried is "Avventura" and it fits)
//        holder.binding.catTitle.text = categoryItem.categoryName
    }

    override fun getItemCount(): Int = showingItems.size

}