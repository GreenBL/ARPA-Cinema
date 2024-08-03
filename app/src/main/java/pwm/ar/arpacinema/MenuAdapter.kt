package pwm.ar.arpacinema

import android.view.LayoutInflater
import android.view.ViewGroup
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
        holder.binding.menuItemTitle.text = menuItem.title
    }

    override fun getItemCount(): Int = menuItems.size
}

