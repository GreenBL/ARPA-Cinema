package pwm.ar.arpacinema.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("imageIndex") val imageIndex: Int,
    @SerializedName("name") var name: String,
    @SerializedName("surname") var surname: String,
    @SerializedName("email") var email: String,
    @SerializedName("phone") var phone: String,
    @SerializedName("level") val level: Int,
    @SerializedName("starPoints") val stars: Int
) : Parcelable
