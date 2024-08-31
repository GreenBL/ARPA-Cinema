package pwm.ar.arpacinema.booking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pwm.ar.arpacinema.databinding.DateItemBinding
import pwm.ar.arpacinema.databinding.SelectionItemBinding

class SelectionAdapter (
    private val selection : List<String>,
    //private val onImageClick : (ScreeningDate) -> Unit
) : RecyclerView.Adapter<SelectionAdapter.SelectionViewHolder>() {

    inner class SelectionViewHolder(val binding : SelectionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
//            binding.root.setOnClickListener {
//                onImageClick(dates[adapterPosition])
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionViewHolder {
        val binding = SelectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectionViewHolder(binding)
    }

    override fun getItemCount(): Int = selection.size

    override fun onBindViewHolder(holder: SelectionViewHolder, position: Int) {
        val binding = holder.binding
        ////////////////////////////

    }
}