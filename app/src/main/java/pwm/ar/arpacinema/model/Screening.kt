package pwm.ar.arpacinema.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalTime

@Parcelize
data class Screening(
    @SerializedName("screening_id") val screeningId: String,
    @SerializedName("movie_id") val movieId: String,
    @SerializedName("screening_time") val screeningTime: LocalTime,
    @SerializedName("screening_date") val screeningDate: LocalDate,
    @SerializedName("theater") val theater: String, // sala => theater / auditorium
) : Parcelable