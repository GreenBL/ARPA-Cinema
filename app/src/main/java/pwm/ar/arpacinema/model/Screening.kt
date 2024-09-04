package pwm.ar.arpacinema.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Screening(
    @SerializedName("screening_id") val screeningId: String,
    @SerializedName("movie_id") val movieId: String,
    @SerializedName("screening_time") val screeningTime: String,
    @SerializedName("screening_date") val screeningDate: String,
    @SerializedName("theater") val theater: String, // sala => theater / auditorium
) : Parcelable