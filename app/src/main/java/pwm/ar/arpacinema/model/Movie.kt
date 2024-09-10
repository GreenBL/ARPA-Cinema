package pwm.ar.arpacinema.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import pwm.ar.arpacinema.home.CarouselItem
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Parcelize
data class Movie(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("duration") val duration: String, // 3h 1m for example, we dont use it
    @SerializedName("categories") val _categories: String,
    @SerializedName("producer") val producer: String,
    @SerializedName("vote") val rating: Double = 0.0,
    @SerializedName("plot") val description: String,
    @SerializedName("url") val posterUrl: String
): Parcelable {
    // convert _categories from comma separated strings to list string
    val categories: List<String>
        get() = _categories.split(",").map { it.trim() }

    fun getCarouselItem(): CarouselItem {
        return CarouselItem(title, posterUrl)
    }

    val year: String
        get() {
            val date = LocalDate.parse(releaseDate)
            return date.year.toString()
        }

    val formattedDate: String
        get() {
            val date = LocalDate.parse(releaseDate)
            return "${date.dayOfMonth} ${date.month.getDisplayName(TextStyle.FULL, Locale.ITALIAN)} ${date.year}"
        }
}
