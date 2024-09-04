package pwm.ar.arpacinema.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("duration") val duration: String, // 3h 1m for example, we dont use it
    @SerializedName("genre") val genre: List<String>,
    @SerializedName("producer") val producer: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("summary") val description: String,
    @SerializedName("poster_url") val posterUrl: String
) : Parcelable