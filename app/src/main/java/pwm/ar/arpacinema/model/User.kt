package pwm.ar.arpacinema.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("userId") val id: Int,
    @SerializedName("imageIndex") val imageIndex: Int,
    @SerializedName("name") val name: String,
    @SerializedName("surname") val surname: String,
    @SerializedName("email") val email: String,
    @SerializedName("level") val level: Int,
    @SerializedName("starPoints") val stars: Int
) : Parcelable
