import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateAdapter : TypeAdapter<LocalDate>() {
    @RequiresApi(Build.VERSION_CODES.O)
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    override fun write(out: JsonWriter, value: LocalDate) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            out.value(value.format(formatter))
        } else {
            throw UnsupportedOperationException("LocalDate is not supported on this API level")
        }
    }

    override fun read(`in`: JsonReader): LocalDate {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.parse(`in`.nextString(), formatter)
        } else {
            throw UnsupportedOperationException("LocalDate is not supported on this API level")
        }
    }
}

class LocalDateTimeAdapter : TypeAdapter<LocalDateTime>() {
    @RequiresApi(Build.VERSION_CODES.O)
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    override fun write(out: JsonWriter, value: LocalDateTime) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            out.value(value.format(formatter))
        } else {
            throw UnsupportedOperationException("LocalDateTime is not supported on this API level")
        }
    }

    override fun read(`in`: JsonReader): LocalDateTime {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.parse(`in`.nextString(), formatter)
        } else {
            throw UnsupportedOperationException("LocalDateTime is not supported on this API level")
        }
    }
}


object GsonProvider {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
            .create()
    }
}