package pwm.ar.arpacinema

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import pwm.ar.arpacinema.databinding.MenuItemBinding

class MenuAdapter(
    private val menuItems: List<MenuItem>,
    private val onItemClick: (MenuItem) -> Unit
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    inner class MenuViewHolder(val binding: MenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClick(menuItems[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menuItem = menuItems[position]
        holder.binding.menuItemIcon.setImageResource(menuItem.iconResId)
        if(menuItem.title == "Logout") {
            val textView = holder.binding.menuItemTitle
            val color = ContextCompat.getColor(holder.itemView.context, R.color.md_theme_error)
            textView.setTextColor(color)
            holder.binding.menuItemIcon.setColorFilter(color)
        }
        holder.binding.menuItemTitle.text = menuItem.title
    }

    override fun getItemCount(): Int = menuItems.size
}

