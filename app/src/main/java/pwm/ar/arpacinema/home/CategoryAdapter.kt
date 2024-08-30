package pwm.ar.arpacinema.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pwm.ar.arpacinema.databinding.CategoryItemBinding
import pwm.ar.arpacinema.model.Categories

class CategoryAdapter(
    private val categoryItems: List<CategoryItem>,
    private val onItemClick: (CategoryItem) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    // sets listener on the cats
    inner class CategoryViewHolder(val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            init {
                binding.chip8.setOnClickListener {
                    onItemClick(categoryItems[adapterPosition])
                }
            }
    }

    // layout inflation and binding of the views
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.CategoryViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    // connecting the view to the model...
    override fun onBindViewHolder(holder: CategoryAdapter.CategoryViewHolder, position: Int) {
        val categoryItem = categoryItems[position]

        // assigns the provided icon
        //holder.binding.catIco.setImageResource(categoryItem.categoryIconResId)
        holder.binding.chip8.text = categoryItem.category.category
        holder.binding.chip8.setChipIconResource(categoryItem.categoryIconResId)

        // assigns the provided cat name (note: longest string I tried is "Avventura" and it fits)
        //holder.binding.catTitle.text = categoryItem.categoryName
    }

    override fun getItemCount(): Int = categoryItems.size

}