package pwm.ar.arpacinema.tickets

import pwm.ar.arpacinema.databinding.TicketItemBinding
import pwm.ar.arpacinema.dev.TicketItem


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.Session
import pwm.ar.arpacinema.model.Ticket
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.RetrofitClient
import pwm.ar.arpacinema.util.CircleEdgeTreatment
import pwm.ar.arpacinema.util.PlaceholderDrawable
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

private const val radius = 80f

class TicketAdapter(
    private val tickets: MutableList<Ticket>,
    private val onItemClick: (Ticket) -> Unit
) : RecyclerView.Adapter<TicketAdapter.TicketViewHolder>() {

    inner class TicketViewHolder(val binding: TicketItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.bottomCard.setOnClickListener {
                onItemClick(tickets[adapterPosition])
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
            .setBottomLeftCorner(CircleEdgeTreatment(radius))
            .setBottomRightCorner(CircleEdgeTreatment(radius))
            .build()

        val shapeAppearanceModelBottom = ShapeAppearanceModel.builder()
            .setTopLeftCorner(CircleEdgeTreatment(radius))
            .setTopRightCorner(CircleEdgeTreatment(radius))
            .setBottomLeftCorner(CornerFamily.ROUNDED, 80f)
            .setBottomRightCorner(CornerFamily.ROUNDED, 80f)
            .build()


        poster.shapeAppearanceModel = shapeAppearanceModelTop
        bottomCard.shapeAppearanceModel = shapeAppearanceModelBottom
        topCard.shapeAppearanceModel = shapeAppearanceModelTop
        return TicketViewHolder(binding)
    }

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val ticketItem = tickets[position]

        holder.binding.filmDate.text = ticketItem.formattedDate
        holder.binding.movieTime.text = ticketItem.formattedTime
        holder.binding.movieTitle.text = ticketItem.filmTitle
        holder.binding.filmSeat.text = ticketItem.seatComposition
        holder.binding.filmSala.text = ticketItem.auditoriumComposition
//        holder.binding.moviePoster.transitionName = "shared_poster_${ticketItem.title}"

        val shapeableImage = holder.binding.moviePoster

        val shimmer = PlaceholderDrawable.getPlaceholderDrawable()

        Glide.with(holder.itemView.context)
            .load(ticketItem.posterUrl)
            .apply(bitmapTransform(BlurTransformation(8, 5)))
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(shimmer)
            .into(shapeableImage)

        holder.binding.shareButton.setOnClickListener {
            scope.launch {
                val pdfFile = getPDFFile(holder.itemView.context, ticketItem)
                shareTicket(holder.itemView.context, pdfFile)
            }
        }
    }

    fun updateTickets(newTickets: List<Ticket>) {
        // use the mutable list interface
        Log.d("TicketAdapter", "updateTickets: $newTickets")
        tickets.apply {
            clear()
            addAll(newTickets)
        }
        notifyDataSetChanged()
    }

    private var shareStr : String = ""

    private fun shareTicket(context: Context, pdf: File) {
        val pdfURI: Uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", pdf)
        val sharingIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_STREAM, pdfURI)
            type = "application/pdf"
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        val shareMenu = Intent.createChooser(sharingIntent, "Condividi Biglietto")
        context.startActivity(shareMenu)
    }

    override fun getItemCount(): Int = tickets.size

    private suspend fun getPDFFile(context: Context, ticket : Ticket): File {
        val service = RetrofitClient.service
        lateinit var response: ResponseBody
        try {
            response = service.ticketPDF(
                DTO.PrintTicketRequest(
                    Session.getUserId(context),
                    ticket.seatNumber,
                    ticket.ticketId
                )
            ) // TODO
        } catch (e: Exception) {
            Log.e("TicketAdapter", "Error: ${e.message}", e)
        }
        val titleWithUnderscores = ticket.filmTitle.replace(" ", "_").trim()
        return cachePDF(context, response, "${titleWithUnderscores}_biglietto_cinema.pdf")
    }

    private fun cachePDF(context: Context, body: ResponseBody, fileName: String): File {

        val cacheDirectory = context.cacheDir
        val pdfFile = File(cacheDirectory, fileName)


        // we cant do the finally block otherwise
        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null

        try {
            inputStream = body.byteStream()
            outputStream = FileOutputStream(pdfFile)

            inputStream.copyTo(outputStream)

            outputStream.flush()

        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
            outputStream?.close()
        }

        return pdfFile

    }

}