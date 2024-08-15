package pwm.ar.arpacinema.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class LoginViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    private var _userEmail = MutableLiveData<String>("TEST")
    val userEmail : LiveData<String>
        get() = _userEmail

    private var _userPassword = MutableLiveData<String>("PWD TEST!")
    val userPassword : LiveData<String>
        get() = _userPassword

    suspend fun execLogin() {
        // TODO: implement login
        Log.i(
            "LOGIN", "execLogin: " + "email: " + _userEmail.value + " password: " + _userPassword.value
        )
    }
}