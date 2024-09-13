package pwm.ar.arpacinema.model

import com.google.gson.annotations.SerializedName
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class ScreeningTime(
    @SerializedName("time") val time: LocalTime,
    @SerializedName("theater_id") val auditorium: String
) {
    val formattedTime: String
        get() = time.format(DateTimeFormatter.ofPattern("HH:mm"))

    val formattedAuditorium: String
        get() = "Sala $auditorium"
}
