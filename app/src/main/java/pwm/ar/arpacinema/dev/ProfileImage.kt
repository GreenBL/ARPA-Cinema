package pwm.ar.arpacinema.dev

import com.google.gson.annotations.SerializedName

data class ProfileImage(
    @SerializedName("id") val imageId : Int = 0,
    @SerializedName("url") val imageUrl : String = "",
)
