package pwm.ar.arpacinema.repository

import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.ApiStatus.Internal

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
        @SerializedName("phone_number") val phoneNumber: String?,
        @SerializedName("email") val email: String?,
        @SerializedName("password") val password: String?
    )

    data class LoginRequest(
        @SerializedName("email") val email: String?,
        @SerializedName("password") val password: String?
    )

    data class LoginResponse(
        @SerializedName("status") val status: String?,
        @SerializedName("id") val userId: Int?,
        @SerializedName("name") val name: String?,
        @SerializedName("surname") val surname: String?,
        @SerializedName("phone_number") val phoneNumber: String?,
        @SerializedName("email") val email: String?,
        @SerializedName("password") val password: String?
    )

    // do we need this? i don't know but writing code is free
    data class SerializedBool(
        @SerializedName("boolean") val bool: Boolean?
    )

    @Internal
    data class GenericResponse(
        @SerializedName("message") val message: String? = null,
        @SerializedName("error") val error: String? = null
    )

}