package pwm.ar.arpacinema.home

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pwm.ar.arpacinema.R

// TODO: THIS IS AN OLD IMPLEMENTATION OF MINE FROM ANOTHER PROJECT, FIX AND REFACTOR BEFORE APP DELIVERY

class CarouselAdapter(private val heroList: List<CarouselItem>): RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    // ViewHolder holds the view refs

    class CarouselViewHolder(carouselItem: View): RecyclerView.ViewHolder(carouselItem) {
        val image: ImageView = carouselItem.findViewById(R.id.movie_image)
        val title: TextView = carouselItem.findViewById(R.id.movie_title)
    }

    // on creation inflate the layout and return the view holder

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CarouselViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.carousel_item, parent, false)
        return CarouselViewHolder(view)
    }

    // bind view holder with data from the list using position as index [this will be done for each item]

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val currentHero = heroList[position]

        // get image with glide
        Glide.with(holder.itemView.context)
            .load(currentHero.movieImageUrl)
            .into(holder.image)

        holder.title.text = currentHero.movieTitle
    }

    override fun getItemCount(): Int = heroList.size

}