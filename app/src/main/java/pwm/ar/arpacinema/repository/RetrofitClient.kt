package pwm.ar.arpacinema.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val DEBUG_MODE = false

    private val SERVER_URL = if (DEBUG_MODE) {
        "https://arpa-api.onrender.com"  // URL for debugging
    } else {
        "http://10.0.2.2:9000"  // URL for production
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // x aurora : aggiungiamo direttamente qui l'interfaccia Service
    // inizializzata con lazy invece di andarcela a fare ogni volta che la usiamo

    val service: Service by lazy {
        retrofit.create(Service::class.java)
    }
}