package pwm.ar.arpacinema.repository

import pwm.ar.arpacinema.model.User
import retrofit2.Response
import retrofit2.http.*
import pwm.ar.arpacinema.repository.DTO.*

interface Service {

    @POST("pwm/signup")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<GenericResponse>

    @POST("pwm/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("pwm/delete_user")
    suspend fun deleteUser(@Body deleteRequest: DeleteUserRequest): Response<GenericResponse>

    @POST("pwm/update_user")
    suspend fun updateUser(@Body updateUserRequest: UpdateUserRequest): Response<GenericResponse>

    @POST("pwm/update_saldo")
    suspend fun updateUserBalance(@Body balanceUpdateRequest: BalanceUpdateRequest): Response<GenericResponse>


    // ====================================================================================
    //                                     DEBUG
    @GET("pwm/ack")
    suspend fun ack(): Response<GenericResponse>
    // ====================================================================================
}
