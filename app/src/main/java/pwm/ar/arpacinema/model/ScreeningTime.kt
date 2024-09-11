package pwm.ar.arpacinema.model

import com.google.gson.annotations.SerializedName
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class ScreeningTime(
    @SerializedName("scrining_start") val time: LocalTime,
    @SerializedName("theater") val auditorium: String
) {
    val formattedTime: String
        get() = time.format(DateTimeFormatter.ofPattern("HH:mm"))

    val formattedAuditorium: String
        get() = "Sala $auditorium"
}
