package pwm.ar.arpacinema.repository

import pwm.ar.arpacinema.model.User
import retrofit.Response
import retrofit.http.GET
import retrofit.http.POST
import retrofit.http.Path

interface Service {

    @GET("pwm/users/{userId}")
    suspend fun getUser(@Path("userId") id: Int): Response<User>

    @POST("pwm/users/new")
    suspend fun createUser(user: User): Response<Boolean>

}