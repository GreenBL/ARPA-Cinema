package pwm.ar.arpacinema.repository

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import pwm.ar.arpacinema.util.LocalDateAdapter
import pwm.ar.arpacinema.util.LocalTimeAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.LocalTime
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val DEBUG_MODE = false

    private val SERVER_URL = if (DEBUG_MODE) {
        "http://192.168.1.134:9000"  // URL for debugging
    } else {
        "http://10.0.2.2:9000"  // URL for production
    }

    val interloper = Sentinel() // we need only one instance of this btw

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(interloper)
        .connectTimeout(2, TimeUnit.SECONDS)
        .build()

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
        .registerTypeAdapter(LocalTime::class.java, LocalTimeAdapter())
        .create()

    private val converter = GsonConverterFactory.create()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    // x aurora : aggiungiamo direttamente qui l'interfaccia Service
    // inizializzata con lazy invece di andarcela a fare ogni volta che la usiamo

    val service: Service by lazy {
        retrofit.create(Service::class.java)
    }

    suspend fun checkConnection(): Boolean {
        return try {
            val response = service.ack()
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }
}