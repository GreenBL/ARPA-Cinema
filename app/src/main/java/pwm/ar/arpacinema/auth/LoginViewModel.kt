package pwm.ar.arpacinema.auth

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


    private val _loginResult = MutableLiveData<String>()
    val loginResult: LiveData<String> = _loginResult

    // TODO: Approccio un po confusionario si deve decidere come gestire lo status

    suspend fun execLogin() : User? {
        // TODO: implement login
        val loginRequest = DTO.LoginRequest(userEmail.value, userPassword.value)
        val response = api.loginUser(loginRequest)

//        if (!response.isSuccessful) {
//            _loginResult.postValue("FAILED")
//            return null
//        }
        val status = response.body()?.status
        _loginResult.postValue(status!!)

        response.body()?.status.let {
            when (it) {
                "SUCCESS" -> {
                    _loginResult.postValue(it)
                    response.body()?.let { response ->
                        val gson = Gson()
                        val user = gson.fromJson(gson.toJson(response), User::class.java)
                        return user
                    }
                }
                "USER_NOT_REGISTERED" -> _loginResult.postValue(it)
                "PSW_ERROR" -> _loginResult.postValue(it)
                else -> {
                    _loginResult.postValue("UNKNOWN_ERROR")
                }
            }
        }

        return null

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
}
