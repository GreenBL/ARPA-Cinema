package pwm.ar.arpacinema.repository

import okhttp3.ResponseBody
import pwm.ar.arpacinema.model.ProfileImage
import retrofit2.Response
import retrofit2.http.Body
import pwm.ar.arpacinema.repository.DTO.*
import retrofit2.http.DELETE

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Url

interface Service {

   // @GET("pwm/users/{userId}")
   // suspend fun getUser(@Path("userId") id: Int): Deferred<User>

    // AUTO LOGIN
    @POST("pwm/get_user_info")
    suspend fun autoLogin(@Body idPost: IdPost): Response<LoginResponse>

    @GET("pwm/load_promo_movie")
    suspend fun getPromotions(): Response<PromotionResponse>

    @GET("pwm/movie_of_the_week")
    suspend fun getMoviesOfTheWeek(): Response<NewMoviesResponse>

    @POST("pwm/get_user_image")
    suspend fun getUserImage(@Body userIdPost: UserIdPost): Response<ImageURLResponse>

    @POST("pwm/signup")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<GenericResponse>
0

    @POST("pwm/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>


    @POST("pwm/delete_user")
    suspend fun deleteUser(@Body deleteRequest: DeleteUserRequest): Response<GenericResponse>

    @POST("pwm/update_user")
    suspend fun updateUser(@Body updateUserRequest: UpdateUserRequest): Response<GenericResponse>

    @POST("pwm/update_saldo")
    suspend fun updateUserBalance(@Body balanceUpdateRequest: BalanceUpdateRequest): Response<GenericResponse>

    @GET("pwm/load_images")
    suspend fun loadProfileImages(): Response<ImageListResponse>

    @POST("pwm/download_pdf")
    suspend fun ticketPDF(@Body printTicketRequest: PrintTicketRequest): ResponseBody

    // ====================================================================================
    //                                     DEBUG
    @GET("pwm/ping")
    suspend fun ack() : Response<GenericResponse>
    // ====================================================================================
}