package pwm.ar.arpacinema.tickets

import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.TicketItemBinding
import pwm.ar.arpacinema.dev.TicketItem


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
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
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

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

        holder.binding.shareButton.setOnClickListener {
            captureBitmapAndShareViaIntent(holder.itemView, holder.binding.root.context)
        }
    }

    private fun captureBitmapAndShareViaIntent(view: View, context: Context) {
        val bitmap = captureScreenshot(view)
        val imageFile = saveBitmapToFile(context, bitmap)
        if (imageFile != null) {
            shareTicket(context, imageFile)
        }
    }

    private fun shareTicket(context: Context, imageFile: File) {
        val imageUri: Uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", imageFile)
        val sharingIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Biglietto cinema")
            putExtra(Intent.EXTRA_STREAM, imageUri)
            type = "image/png"
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION // Allow recipient to read the file
        }
        val shareMenu = Intent.createChooser(sharingIntent, "Condividi biglietto")
        context.startActivity(shareMenu) // Use context directly
    }

    override fun getItemCount(): Int = menuItems.size

// DANGER ZONE // DANGER ZONE // DANGER ZONE // DANGER ZONE // DANGER ZONE // DANGER ZONE //

    private fun captureScreenshot(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = android.graphics.Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun saveBitmapToFile(context: Context, bitmap: Bitmap): File? {
        val file = File(context.cacheDir, "share_ticket.png")
        var outputStream: FileOutputStream? = null

        return try {
            outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            file
        } catch (e: IOException) {
            e.printStackTrace()
            null
        } finally {
            outputStream?.close() // Ensure outputStream is closed to avoid memory leaks
        }
    }
}