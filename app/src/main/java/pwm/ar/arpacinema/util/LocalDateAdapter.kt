package pwm.ar.arpacinema.util

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateAdapter : TypeAdapter<LocalDate>() {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    override fun write(out: JsonWriter, value: LocalDate?) {
        out.value(value?.format(formatter)) // Format LocalDate to ISO 8601 string
    }

    override fun read(reader: JsonReader): LocalDate? {
        return if (reader.peek() == com.google.gson.stream.JsonToken.NULL) {
            reader.nextNull()
            null
        } else {
            LocalDate.parse(reader.nextString(), formatter) // Parse ISO 8601 string to LocalDate
        }
    }
}