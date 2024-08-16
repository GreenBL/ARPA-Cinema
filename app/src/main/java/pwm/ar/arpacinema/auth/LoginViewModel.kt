package pwm.ar.arpacinema.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.RetrofitClient
import retrofit2.Response

class LoginViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    private var _userEmail = MutableLiveData("")
    val userEmail : MutableLiveData<String>
        get() = _userEmail

    private var _userPassword = MutableLiveData("")
    val userPassword : MutableLiveData<String>
        get() = _userPassword

    private val api = RetrofitClient.service

    private lateinit var loginRequest: DTO.LoginRequest

    suspend fun execLogin() : String? {
        // TODO: implement login

            val response = api.ack()
            return response.body()?.message


    }

}