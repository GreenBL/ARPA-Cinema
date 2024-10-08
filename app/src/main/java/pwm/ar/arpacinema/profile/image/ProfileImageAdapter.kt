package pwm.ar.arpacinema.profile.image

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.Session
import pwm.ar.arpacinema.databinding.ProfileImageItemBinding
import pwm.ar.arpacinema.model.ProfileImage

class ProfileImageAdapter(
    private var images : List<ProfileImage>,
    private val onImageClick : (ProfileImage) -> Unit
) : RecyclerView.Adapter<ProfileImageAdapter.ProfileImageViewHolder>() {

    inner class ProfileImageViewHolder(val binding : ProfileImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            init {
                binding.root.setOnClickListener {
                    onImageClick(images[adapterPosition])
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileImageViewHolder {
        val binding = ProfileImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileImageViewHolder(binding)
    }

    override fun getItemCount(): Int = images.size

    fun setDataList(newImages : List<ProfileImage>) {
        images = newImages
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ProfileImageViewHolder, position: Int) {
        val binding = holder.binding
        ////////////////////////////
        val imageView = binding.imageView
        ////////////////////////////
        val placeholder = CircularProgressDrawable(imageView.context)
        placeholder.strokeWidth = 5f
        placeholder.centerRadius = 30f
        placeholder.start()

        Glide.with(imageView.context)
            .load(images[position].imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(placeholder)
            .into(imageView)

        // we check if the url is stored the same, we set the border to some color
        // if it is not, we remove the border

        if (images[position].imageUrl == Session.userImageURL) {
            binding.imageWrap.strokeWidth = 12
            binding.imageWrap.strokeColor = imageView.context.getColor(R.color.md_theme_primary)
            binding.root.isClickable = false
        } else {
            binding.imageWrap.strokeWidth = 2
        }




    }
}