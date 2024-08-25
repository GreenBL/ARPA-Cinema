package pwm.ar.arpacinema.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import pwm.ar.arpacinema.MenuAdapter
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.LargeMenuItemBinding
import pwm.ar.arpacinema.databinding.MenuItemBinding

class LargeMenuAdapter(
    private val menuItems: List<MenuItem>,
    private val onItemClick: (MenuItem) -> Unit
) : RecyclerView.Adapter<LargeMenuAdapter.LargeMenuViewHolder>() {

    inner class LargeMenuViewHolder(val binding: LargeMenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClick(menuItems[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LargeMenuViewHolder {
        val binding = LargeMenuItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LargeMenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LargeMenuViewHolder, position: Int) {
        val menuItem = menuItems[position]
        holder.binding.drawableResource.setImageResource(menuItem.iconResId)
        if(!menuItem.showChevron) {
            holder.binding.chevron.visibility = View.GONE
        }
        holder.binding.menuItemTitle.text = menuItem.label
    }

    override fun getItemCount(): Int = menuItems.size
}