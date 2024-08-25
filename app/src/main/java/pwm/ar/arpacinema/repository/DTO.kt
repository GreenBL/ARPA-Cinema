package pwm.ar.arpacinema.repository

import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.ApiStatus.Internal
import pwm.ar.arpacinema.model.User

/**
 * This class holds the serializable data classes to be sent to the server
 * which do not fit in the model class **aka** Data Transfer Objects.
 * EXAMPLE: [LoginRequest] is a class that holds the data to be sent to the server when logging in.
 * Also Flask could respond with those too! So use them accordingly.
 * Also Sicily is pretty hot lately not gonna lie.
 */

class DTO {

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
        @SerializedName("status") val status: String?,
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



    @Internal
    data class GenericResponse(
        @SerializedName("message") val message: String? = null,
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
        UNKNOWN_ERROR("UNKNOWN_ERROR") // WHEN SOMETHING ELSE HAPPENS WE DONT KNOW ABOUT
    }

}