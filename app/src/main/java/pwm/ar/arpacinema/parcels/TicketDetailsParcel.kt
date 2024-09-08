package pwm.ar.arpacinema.parcels

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TicketDetailsParcel(
    val ticketID: String,
    val movieTitle: String,
    val movieDate: String,
    val movieTime: String,
    val movieTheater: String,
    val seatString: String
) : Parcelable