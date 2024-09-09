package pwm.ar.arpacinema.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ticket(
    @SerializedName("purchase_id") val ticketId: String,
    @SerializedName("screening_date") val screeningDate: String,
    @SerializedName("screening_time") val screeningTime: String,
    @SerializedName("theater") val screeningTheater: String,
    @SerializedName("film_title") val filmTitle: String,
    @SerializedName("film_url") val posterUrl: String,
    @SerializedName("seat") val seatNumber: String,
) : Parcelable {
    val seatRow: String
        get() = seatNumber[0].toString()

    val seatCol: String
        get() = seatNumber[1].toString()

}