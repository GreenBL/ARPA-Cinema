package pwm.ar.arpacinema.repository

import kotlinx.coroutines.Deferred
import pwm.ar.arpacinema.model.User
import retrofit2.Response
import retrofit2.http.Body
import pwm.ar.arpacinema.repository.DTO.*

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Service {

   // @GET("pwm/users/{userId}")
   // suspend fun getUser(@Path("userId") id: Int): Deferred<User>

   // @POST("pwm/users/new")
   // suspend fun createUser(user: User): Deferred<Boolean>

    @POST("dev/signup")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<GenericResponse>

    @POST("pwm/users/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    // ====================================================================================
    //                                     DEBUG
    @GET("dev/ack")
    suspend fun ack() : Response<GenericResponse>
    // ====================================================================================
}