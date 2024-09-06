package pwm.ar.arpacinema.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ticket(
    @SerializedName("ticket_id") val ticketId: String,
    @SerializedName("movie_id") val movieId: String,
    @SerializedName("movie_title") val movieTitle: String,
    @SerializedName("show_date") val showDate: String,
    @SerializedName("show_time") val showTime: String,
    @SerializedName("seat_row_col") val seatNumber: String,
) : Parcelable {
    val seatRow: String
        get() = seatNumber[0].toString()

    val seatCol: String
        get() = seatNumber[1].toString()

}