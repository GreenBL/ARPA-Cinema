package pwm.ar.arpacinema.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import pwm.ar.arpacinema.model.User

import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.RetrofitClient


class LoginViewModel : ViewModel() {

    private var _userEmail = MutableLiveData("")
    val userEmail : MutableLiveData<String>
        get() = _userEmail

    private var _userPassword = MutableLiveData("")
    val userPassword : MutableLiveData<String>
        get() = _userPassword

    private val api = RetrofitClient.service

    private lateinit var loginRequest: DTO.LoginRequest


    private val _loginResult = MutableLiveData<String?>()
    val loginResult: MutableLiveData<String?> = _loginResult

    // TODO: Approccio un po confusionario si deve decidere come gestire lo status

    suspend fun execLogin(): User? {
        val loginRequest = DTO.LoginRequest(userEmail.value, userPassword.value)
        val response = api.loginUser(loginRequest)
        if (!response.isSuccessful) {
            _loginResult.postValue("")
            return null
        }

        val status = response.body()?.status
        _loginResult.postValue(status!!)

        // Log the entire response body
        Log.d("LoginViewModel", "Response Body: ${response.body()}")

        response.body()?.let { loginResponse ->
            when (status) {
                "SUCCESS" -> {
                    _loginResult.postValue(status)
                    // Return the user from the response
                    return loginResponse.user
                }
                "USER_NOT_REGISTERED" -> _loginResult.postValue(status)
                "PSW_ERROR" -> _loginResult.postValue(status)
                else -> _loginResult.postValue("UNKNOWN_ERROR")
            }
        }

        return null
    }
}


//private val authAPI: Call<JsonObject> = Auth.retrofit.login(AuthAPI::class.java)

//    fun login(email: String, password: String) {
//        val json = JsonObject().apply {
//            addProperty("email", email)
//            addProperty("password", password)
//        }
//
//        viewModelScope.launch {
//            val call = authAPI.login(json)
//            call.enqueue(object : Callback<JsonObject> {
//                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
//                    if (response.isSuccessful) {
//                        val jsonResponse = response.body()
//                        jsonResponse?.let {
//                            when (it.get("status").asString) {
//                                "SUCCESS" -> _loginResult.postValue(LoginResult.Success(it))
//                                "USER_NOT_REGISTERED" -> _loginResult.postValue(LoginResult.Error("User not registered"))
//                                "PSW_ERROR" -> _loginResult.postValue(LoginResult.Error("Incorrect password"))
//                                else -> _loginResult.postValue(LoginResult.Error("Unknown error"))
//                            }
//                        }
//                    } else {
//                        _loginResult.postValue(LoginResult.Error("Login failed"))
//                    }
//                }
//
//
//
//                override fun onFailure(t: Throwable?) {
//                    TODO("Not yet implemented")
//                    _loginResult.postValue(LoginResult.Error("Network error: ${t.message}"))
//                }
//
//            })
//        }
//    }

//    sealed class LoginResult {
//        data class Success(val userId: Int) : LoginResult()
//        data class Error(val message: String) : LoginResult()
//    }



