package pwm.ar.arpacinema.parcels

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Parcelize
data class TicketDetailsParcel(
    val ticketID: String,
    val movieTitle: String,
    val movieDate: String,
    val movieTime: String,
    val movieTheater: String,
    val seatString: String
) : Parcelable {
    val seatRow: String
        get() = seatString[0].toString()

    val seatCol: String
        get() = seatString[1].toString()

    val seatComposition: String
        get() = "Fila $seatRow, Posto $seatCol"

    val auditoriumComposition: String
        get() = "Sala $movieTheater"

    val formattedDate: String
        get() {
            val date = LocalDate.parse(movieDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            return date.format(DateTimeFormatter.ofPattern("dd MMMM", Locale.ITALIAN))

        }

    val formattedTime: String
        get() = movieTime.substring(0, 5)

}