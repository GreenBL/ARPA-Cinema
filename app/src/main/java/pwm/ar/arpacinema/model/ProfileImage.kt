package pwm.ar.arpacinema.model

import com.google.gson.annotations.SerializedName

data class ProfileImage(
    @SerializedName("id") val imageId : Int = 0,
    @SerializedName("url") val imageUrl : String = "",
)
