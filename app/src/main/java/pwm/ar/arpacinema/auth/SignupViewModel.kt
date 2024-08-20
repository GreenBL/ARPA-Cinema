package pwm.ar.arpacinema.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.RetrofitClient

class SignupViewModel: ViewModel() {

    // all fields of the sign up form (2way binding) + verification flags

    private val _userEmail = MutableLiveData<String>()
    val userEmail: MutableLiveData<String> = _userEmail
    private val emailFlag : Boolean = false

    private val _userPassword = MutableLiveData<String>()
    val userPassword: MutableLiveData<String> = _userPassword
    private val passwordFlag : Boolean = false

    private val _userSurname = MutableLiveData<String>()
    val userSurname: MutableLiveData<String> = _userSurname
    private val surnameFlag : Boolean = false

    private val _userName = MutableLiveData<String>()
    val userName: MutableLiveData<String> = _userName
    private val nameFlag : Boolean = false

    private val _userPhone = MutableLiveData<String>()
    val userPhone: MutableLiveData<String> = _userPhone
    private val phoneFlag : Boolean = false

    val service = RetrofitClient.service

    suspend fun signUp() : DTO.GenericResponse{

        // TODO VALIDATE DATA BEFORE POST!

        val request = DTO.SignUpRequest(
            _userEmail.value,
            _userPassword.value,
            _userSurname.value,
            _userName.value,
            _userPhone.value
        )

        val response = service.signUp(request)
        return response.body()!!
    }


}
