package pwm.ar.arpacinema.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ticket(
    @SerializedName("screening_date") val ticketId: String,
    @SerializedName("screening_time") val movieId: String,
    @SerializedName("theater") val movieTitle: String,
    @SerializedName("film_title") val showDate: String,
    @SerializedName("film_url") val showTime: String,
    @SerializedName("seat") val seatNumber: String,
) : Parcelable {
    val seatRow: String
        get() = seatNumber[0].toString()

    val seatCol: String
        get() = seatNumber[1].toString()

}