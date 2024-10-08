package pwm.ar.arpacinema.rewards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import pwm.ar.arpacinema.MenuAdapter
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.common.MenuItem
import pwm.ar.arpacinema.databinding.MenuItemBinding
import pwm.ar.arpacinema.databinding.OptionBinding
import pwm.ar.arpacinema.model.Reward

class OptionsAdapter(
    private var menuItems: List<Reward>,
    private val showPoints: Boolean = true,
    private val onItemClick: (Reward) -> Unit = {}
) : RecyclerView.Adapter<OptionsAdapter.RewardViewHolder>() {

    inner class RewardViewHolder(val binding: OptionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClick(menuItems[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RewardViewHolder {
        val binding = OptionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RewardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RewardViewHolder, position: Int) {
        val menuItem = menuItems[position]
        val displayText = "${menuItem.category}: ${menuItem.size}"
        val displayText2 = "${menuItem.size}"

        if (showPoints) {
            holder.binding.cost.visibility = View.VISIBLE
            holder.binding.cost.text = menuItem.points.toString()
            holder.binding.optionTitle.text = displayText2
        } else {
            holder.binding.cost.visibility = View.GONE
            holder.binding.optionTitle.text = displayText
        }



    }

    override fun getItemCount(): Int = menuItems.size

    // Method to update items and notify adapter
    fun updateItems(newItems: List<Reward>) {
        menuItems = newItems
        notifyDataSetChanged()
    }
}