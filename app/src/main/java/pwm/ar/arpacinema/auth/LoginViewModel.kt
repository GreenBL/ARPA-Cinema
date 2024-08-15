package pwm.ar.arpacinema.auth

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.repository.RequestModels
import pwm.ar.arpacinema.repository.RetrofitClient

class LoginViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    private var _userEmail = MutableLiveData("")
    val userEmail : MutableLiveData<String>
        get() = _userEmail

    private var _userPassword = MutableLiveData("")
    val userPassword : MutableLiveData<String>
        get() = _userPassword

    private val api = RetrofitClient.service

    private lateinit var loginRequest: RequestModels.LoginRequest

    fun execLogin() {
        // TODO: implement login
        viewModelScope.launch {
            try {
                val request = RequestModels.LoginRequest(userEmail.value, userPassword.value)
                val response = api.loginUser(request).body()
                Log.e("LOGIN", "execLogin: $response")
            } catch (e: Exception) {
                Log.e("LOGIN", "execLogin: ", e)
            }
        }
        Log.i(
            "LOGIN", "execLogin: " + "email: " + _userEmail.value + " password: " + _userPassword.value
        )
    }

}