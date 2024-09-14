package pwm.ar.arpacinema.repository

import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.ApiStatus.Internal
import pwm.ar.arpacinema.model.Movie
import pwm.ar.arpacinema.model.ProfileImage
import pwm.ar.arpacinema.model.Promotion
import pwm.ar.arpacinema.model.Purchase
import pwm.ar.arpacinema.model.ScreeningDate
import pwm.ar.arpacinema.model.ScreeningTime
import pwm.ar.arpacinema.model.Ticket
import pwm.ar.arpacinema.model.User
import pwm.ar.arpacinema.util.SeatInterpreter
import java.time.LocalDate
import java.time.LocalTime

/**
 * This class holds the serializable data classes to be sent to the server
 * which do not fit in the model class **aka** Data Transfer Objects.
 * EXAMPLE: [LoginRequest] is a class that holds the data to be sent to the server when logging in.
 * Also Flask could respond with those too! So use them accordingly.
 * Also Sicily is pretty hot lately not gonna lie.
 */

class DTO {

    data class SecurityQuestionRequest(
        @SerializedName("user_id") val userId: Int,
        @SerializedName("security_question") val securityQuestion: Int,
        @SerializedName("security_answer") val securityAnswer: String
    )


    data class EditEmailRequest(
        @SerializedName("user_id") val id: String?,
        @SerializedName("email") val email: String?
    )

    data class BuyTicketRequest(
        @SerializedName("user_id") val userId: String?,
        @SerializedName("film_id") val filmId: String?,
        @SerializedName("theater_id") val theaterId: String?,
        @SerializedName("screening_date") val screeningDate: LocalDate?,
        @SerializedName("screening_time") val screeningTime: LocalTime?,
        @SerializedName("selected_seats") val seatStrList: List<String>,
        //@SerializedName("price") val price: Double?
    )

    data class RedSeatsResponse(
        @SerializedName("status") val status: Stat?,
        @SerializedName("occupied_seats") val seats: List<String>?
    ) {
        val intList: List<Int>
            get() = SeatInterpreter.convertListToInteger(seats)
    }

    data class RedSeatsRequest(
        @SerializedName("theater_id") val theater: String?,
        @SerializedName("screening_date") val screeningDate: LocalDate?,
        @SerializedName("screening_time") val screeningTime: LocalTime?,
    )

    data class MovieTimePost(
        @SerializedName("film_id") val filmId: String?,
        @SerializedName("screening_date") val screeningDate: LocalDate?
    )

    data class ScreeningWrapper(
        @SerializedName("status") val status: Stat?,
        @SerializedName("screening_start") val screeningTimes: List<ScreeningTime>?
    )

    @Deprecated("Use ScreeningTime model instead", ReplaceWith("ScreeningTime"), DeprecationLevel.WARNING)
    data class ScreeningResponse(
        @SerializedName("time") val time: LocalTime?,
        @SerializedName("theater_id") val sala: String?
    )

    data class EditPasswordRequest(
        @SerializedName("user_id") val id: String?,
        @SerializedName("password") val password: String?
    )

    data class EmailPost(
        @SerializedName("email") val email: String?
    )

    data class QuestionResponse(
        @SerializedName("status") val status: Stat?,
        @SerializedName("security_question") val question: String?,
        @SerializedName("security_answer") val answer: String?,
        @SerializedName("user_id") val userId: String?
    )

    data class MovieIdPost(
        @SerializedName("film_id") val movieId: String?,
    )

    data class MovieDatesResponse(
        @SerializedName("status") val status: Stat?,
        @SerializedName("screening_dates") val dates: List<ScreeningDate>?
    )

    data class PurchasesResponse(
        @SerializedName("status") val status: Stat?,
        @SerializedName("purchases") val purchases: List<Purchase>?
    )

    data class TicketResponse(
        @SerializedName("status") val status: Stat?,
        @SerializedName("tickets") val ticket: List<Ticket>?
    )

    data class CategoryPost(
        @SerializedName("category") val category: String?,
    )

    data class PromotionResponse(
        @SerializedName("status") val status: Stat?,
        @SerializedName("films") val promotions: List<Promotion>?
    )

    data class UserIdPost(
        @SerializedName("user_id") val id: String?,
    )

    data class IdPost(
        @SerializedName("id") val id: String?,
    )

    data class NewMoviesResponse(
        @SerializedName("status") val status: Stat?,
        @SerializedName("films") val movies: List<Movie>?
    )

    data class MoviesResponse(
        @SerializedName("status") val status: Stat?,
        @SerializedName("films") val movies: List<Movie>?
    )

    data class ImageURLResponse(
        @SerializedName("status") val status: Stat?,
        @SerializedName("image_url") val image: String?
    )

    data class SignUpRequest(
        @SerializedName("name") val name: String?,
        @SerializedName("surname") val surname: String?,
        @SerializedName("phone") val phone: String?,
        @SerializedName("email") val email: String?,
        @SerializedName("password") val password: String?
    )

    data class LoginRequest(
        @SerializedName("email") val email: String?,
        @SerializedName("password") val password: String?
    )

    data class LoginResponse(
        @SerializedName("status") val status: Stat?,
        @SerializedName("user") val user: User?
    )



    data class DeleteUserRequest(
        @SerializedName("user") val user: User
    )

    // do we need this? i don't know but writing code is free - LOL :)
    data class SerializedBool(
        @SerializedName("boolean") val bool: String?
    )

    data class UpdateUserRequest(
        @SerializedName("id") val id: Int?,
        @SerializedName("name") val name: String?,
        @SerializedName("surname") val surname: String?,
        @SerializedName("phone") val phone: String?
    )

    data class BalanceUpdateRequest(
        @SerializedName("user_id") val id: String?,
        @SerializedName("amount") val amount: Double?
    )
    data class BalanceResponse(
        @SerializedName("amount") val amount: Double?
    )

    data class ImageListResponse(
        @SerializedName("status") val status: Stat?,
        @SerializedName("images") val imageList: List<ProfileImage>?
    )

    data class PrintTicketRequest(
        @SerializedName("user_id") val id: String?,
        @SerializedName("seat_code") val seatCode: String?,
        @SerializedName("purchase_id") val purchaseID: String?,
    )


    data class UserPointsAndLevelResponse(
        @SerializedName("status") val status: String?,
        @SerializedName("points") val points: Int?,
        @SerializedName("level") val level: Int?
    )


    @Internal
    data class GenericResponse(
        @SerializedName("status") val status: Stat? = null,
        @SerializedName("error") val error: String? = null
    )

    // status enum for responses

    enum class Stat(val status: String) {
        SUCCESS("SUCCESS"), // WHEN OP SUCCEEDS
        DEFAULT("DEFAULT"), // WHEN NO REQUEST HAS BEEN MADE YET
        UNFILLED("UNFILLED"), // WHEN SOME FIELD IS EMPTY / NULL
        ERROR("ERROR"),// GENERIC ERROR
        NETWORK_ERROR("NETWORK_ERROR"), // WHEN NETWORK ERROR
        SERVER_ERROR("SERVER_ERROR"), // WHEN SERVER ERROR
        USER_NOT_REGISTERED("USER_NOT_REGISTERED"), // WHEN USER IS NOT REGISTERED
        PSW_ERROR("PSW_ERROR"), // WHEN PASSWORD IS INCORRECT
        PASSWORD_EDITED("PASSWORD_EDITED"), // WHEN PASSWORD IS EDITED CORRECTLY
        UNKNOWN_ERROR("UNKNOWN_ERROR") // WHEN SOMETHING ELSE HAPPENS WE DONT KNOW ABOUT
    }

}