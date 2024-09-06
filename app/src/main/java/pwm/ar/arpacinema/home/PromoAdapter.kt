package pwm.ar.arpacinema.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import pwm.ar.arpacinema.databinding.PopularItemBinding
import pwm.ar.arpacinema.model.Promotion

class PromoAdapter(
    private val promotions: List<Promotion>,
    private val onItemClick: (Promotion) -> Unit
) : RecyclerView.Adapter<PromoAdapter.PromoViewHolder>() {

    inner class PromoViewHolder(val binding: PopularItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClick(promotions[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromoViewHolder {
        val binding = PopularItemBinding.inflate( // its supposed to be popular item binding
            LayoutInflater.from(parent.context), parent, false
        )
        return PromoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PromoViewHolder, position: Int) {
        val binding = holder.binding

        val promo = promotions[position]

        val title = binding.proName
        val shortDesc = binding.proDesc

        title.text = promo.title
        shortDesc.text = promo.descriptionShort


        Glide.with(holder.itemView.context)
            .load(promo.imageUri)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.movieImage)


    }

    override fun getItemCount(): Int = promotions.size
}