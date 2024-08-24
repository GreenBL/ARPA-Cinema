package pwm.ar.arpacinema.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.RetrofitClient

class SignupViewModel : ViewModel() {

    private val _userEmail = MutableLiveData<String>()
    val userEmail: MutableLiveData<String> = _userEmail

    private val _userPassword = MutableLiveData<String>()
    val userPassword: MutableLiveData<String> = _userPassword

    private val _userSurname = MutableLiveData<String>()
    val userSurname: MutableLiveData<String> = _userSurname

    private val _userName = MutableLiveData<String>()
    val userName: MutableLiveData<String> = _userName

    private val _userPhone = MutableLiveData<String>()
    val userPhone: MutableLiveData<String> = _userPhone

    private val service = RetrofitClient.service

    suspend fun signUp(): DTO.GenericResponse {
        // Construct the request payload
        val request = DTO.SignUpRequest(
            _userName.value,
            _userSurname.value,
            _userPhone.value,
            _userEmail.value,
            _userPassword.value
        )

        // Log the request payload
        Log.d("SignUp", "Request Payload: $request")

        // Send the request to the server
        val response = service.signUp(request)
        return response.body() ?: DTO.GenericResponse(error = "No response from server")
    }
}
