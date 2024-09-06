package pwm.ar.arpacinema.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

// le getter personalzzate convertono dal formato tipo 2007-09-04 al formato stringale "Lun 4"

data class ScreeningDate(
    val date: LocalDate,
    val time: LocalTime
) {
    val dayOfWeek: String
        get() = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ITALY) // lun, mar, mer...

    val dayOfMonth: String
        get() = date.dayOfMonth.toString()

    val formattedTime: String
        get() = time.format(DateTimeFormatter.ofPattern("HH:mm"))
}