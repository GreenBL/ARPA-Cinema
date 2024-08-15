package pwm.ar.arpacinema.tickets

import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.TicketItemBinding
import pwm.ar.arpacinema.dev.TicketItem


import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomappbar.BottomAppBarTopEdgeTreatment
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.CornerTreatment
import com.google.android.material.shape.CutCornerTreatment
import com.google.android.material.shape.MarkerEdgeTreatment
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.OffsetEdgeTreatment
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.shape.TriangleEdgeTreatment
import pwm.ar.arpacinema.util.CircleEdgeTreatment

class TicketAdapter(
    private val menuItems: List<TicketItem>,
    private val onItemClick: (TicketItem, ShapeableImageView) -> Unit
) : RecyclerView.Adapter<TicketAdapter.TicketViewHolder>() {

    inner class TicketViewHolder(val binding: TicketItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClick(menuItems[adapterPosition], binding.moviePoster)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val binding = TicketItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        val bottomCard = binding.bottomCard
        val topCard = binding.topCard
        val poster = binding.moviePoster

        val shapeAppearanceModelTop = ShapeAppearanceModel.builder()
            .setTopLeftCorner(CornerFamily.ROUNDED, 80f)
            .setTopRightCorner(CornerFamily.ROUNDED, 80f)
            .setBottomLeftCorner(CircleEdgeTreatment(50f))
            .setBottomRightCorner(CircleEdgeTreatment(50f))
            .build()

        val shapeAppearanceModelBottom = ShapeAppearanceModel.builder()
            .setTopLeftCorner(CircleEdgeTreatment(50f))
            .setTopRightCorner(CircleEdgeTreatment(50f))
            .setBottomLeftCorner(CornerFamily.ROUNDED, 80f)
            .setBottomRightCorner(CornerFamily.ROUNDED, 80f)
            .build()


        poster.shapeAppearanceModel = shapeAppearanceModelTop
        bottomCard.shapeAppearanceModel = shapeAppearanceModelBottom
        topCard.shapeAppearanceModel = shapeAppearanceModelTop
        return TicketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val ticketItem = menuItems[position]

        holder.binding.filmDate.text = ticketItem.date
        holder.binding.movieTime.text = ticketItem.time
        holder.binding.movieTitle.text = ticketItem.title
        holder.binding.moviePoster.transitionName = "shared_poster_${ticketItem.title}"
    }

    override fun getItemCount(): Int = menuItems.size
}