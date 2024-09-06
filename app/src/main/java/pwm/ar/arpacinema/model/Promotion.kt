package pwm.ar.arpacinema.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Promotion(
    @SerializedName("title") val title: String,
    @SerializedName("short_description") val descriptionShort: String,
    @SerializedName("long_description") val descriptionLong: String,
    @SerializedName("url") val imageUri: String,
) : Parcelable
