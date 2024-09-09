package pwm.ar.arpacinema.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Purchase(
    @SerializedName("screening_date") val screeningDate: String,
    @SerializedName("screening_time") val screeningTime: String,
    @SerializedName("theater") val auditorium: String,
    @SerializedName("film_title") val movieTitle: String,
    @SerializedName("seat") val seatString: String,
    @SerializedName("purchase_id") val purchaseID: String
) : Parcelable
