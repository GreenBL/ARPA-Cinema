package pwm.ar.arpacinema.repository

import retrofit2.Response
import retrofit2.http.Body
import pwm.ar.arpacinema.repository.DTO.*
import retrofit2.http.DELETE

import retrofit2.http.GET
import retrofit2.http.POST

interface Service {

   // @GET("pwm/users/{userId}")
   // suspend fun getUser(@Path("userId") id: Int): Deferred<User>


    @POST("pwm/signup")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<GenericResponse>


    @POST("pwm/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>


    @POST("pwm/delete_user")
    suspend fun deleteUser(@Body deleteRequest: DeleteUserRequest): Response<GenericResponse>




    // ====================================================================================
    //                                     DEBUG
    @GET("pwm/ack")
    suspend fun ack() : Response<GenericResponse>
    // ====================================================================================
}