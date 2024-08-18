package pwm.ar.arpacinema.repository

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val DEBUG_MODE = true

    private val SERVER_URL = if (DEBUG_MODE) {
        "https://arpa-api.onrender.com"  // URL for debugging
    } else {
        "http://10.0.2.2:9000"  // URL for production
    }

    val interloper = Status() // we need only one instance of this btw

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(interloper)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
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