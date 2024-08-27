package pwm.ar.arpacinema.discovery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pwm.ar.arpacinema.databinding.LoadScreeningBinding
import pwm.ar.arpacinema.databinding.ProjectionItemBinding
import pwm.ar.arpacinema.dev.ShowingItem

class LoadingScreenAdapter(
    private val showingItems: List<ShowingItem>,
) : RecyclerView.Adapter<LoadingScreenAdapter.LoadingViewHolder>() {


    inner class LoadingViewHolder(val binding: LoadScreeningBinding) :
        RecyclerView.ViewHolder(binding.root)


    // layout inflation and binding of the views
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LoadingScreenAdapter.LoadingViewHolder {
        val binding = LoadScreeningBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadingViewHolder(binding)
    }

    // connecting the view to the model...
    override fun onBindViewHolder(holder: LoadingScreenAdapter.LoadingViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = showingItems.size

}