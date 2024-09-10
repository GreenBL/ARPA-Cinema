package pwm.ar.arpacinema.repository

import okhttp3.ResponseBody
import pwm.ar.arpacinema.model.ProfileImage
import pwm.ar.arpacinema.model.ScreeningDate
import retrofit2.Response
import retrofit2.http.Body
import pwm.ar.arpacinema.repository.DTO.*
import retrofit2.http.DELETE

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Url

interface Service {


    @POST("pwm/get_security_question")
    suspend fun getSecurityQuestion(@Body emailPost: EmailPost): Response<QuestionResponse>

    // LOAD MOVIE DATES -- RIGA 956 SERVER
    @POST("pwm/get_screening_dates")
    suspend fun getMovieDates(@Body movieId: MovieIdPost): Response<MovieDatesResponse>

    // GET USER TICKETS
    @POST("pwm/chronology")
    suspend fun getTickets(@Body userIdPost: UserIdPost): Response<TicketResponse>

    @GET("pwm/load_films")
    suspend fun getMovies(): Response<MoviesResponse>

    @POST("/pwm/films_by_category")
    suspend fun getMoviesByCategory(@Body categoryPost: CategoryPost): Response<MoviesResponse>

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