package pwm.ar.arpacinema.auth.retrofit

import pwm.ar.arpacinema.auth.retrofit.AuthAPI.Companion.BASE_URL
import retrofit.GsonConverterFactory
import retrofit.Retrofit

object Auth {
  val retrofit : AuthAPI by lazy {
      Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .build()
          .create(AuthAPI::class.java)
  }
}