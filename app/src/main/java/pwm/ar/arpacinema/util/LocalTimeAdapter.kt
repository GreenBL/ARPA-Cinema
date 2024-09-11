package pwm.ar.arpacinema.util

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class LocalTimeAdapter : TypeAdapter<LocalTime>() {
    private val formatter = DateTimeFormatter.ISO_LOCAL_TIME

    override fun write(out: JsonWriter, value: LocalTime?) {
        out.value(value?.format(formatter)) // Serialize LocalTime to ISO 8601 string
    }

    override fun read(reader: JsonReader): LocalTime? {
        return if (reader.peek() == com.google.gson.stream.JsonToken.NULL) {
            reader.nextNull() // Handle null values in JSON
            null
        } else {
            LocalTime.parse(reader.nextString(), formatter) // Deserialize JSON string to LocalTime
        }
    }
}