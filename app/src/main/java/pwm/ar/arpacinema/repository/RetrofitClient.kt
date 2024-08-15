package pwm.ar.arpacinema.repository

import retrofit.GsonConverterFactory
import retrofit.Retrofit

object RetrofitClient {

    private const val SERVER_URL = "SERVER URL" // TODO : Add actual server URL to flaskize

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