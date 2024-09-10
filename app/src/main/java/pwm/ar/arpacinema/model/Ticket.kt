package pwm.ar.arpacinema.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

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

    val seatComposition: String
        get() = "Fila $seatRow, Posto $seatCol"

    val auditoriumComposition: String
        get() = "Sala $screeningTheater"

    val formattedDate: String
        get() {
            val date = LocalDate.parse(screeningDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            return date.format(DateTimeFormatter.ofPattern("dd MMMM", Locale.ITALIAN))

        }

    val formattedTime: String
        get() = screeningTime.substring(0, 5)

}