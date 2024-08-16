package pwm.ar.arpacinema.auth.retrofit

import com.google.gson.JsonObject
import retrofit.Call
import retrofit.http.Body
import retrofit.http.POST


interface AuthAPI {
    @POST("pwm/login")
    fun login(@Body json: Class<AuthAPI>): Call<JsonObject>


    @POST("pwm/signup")
    fun signup(@Body json: JsonObject): Call<JsonObject>


    companion object {
        const val BASE_URL = "http://192.168.1.134:9000/"
    }
}