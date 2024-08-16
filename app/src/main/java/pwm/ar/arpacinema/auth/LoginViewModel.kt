package pwm.ar.arpacinema.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject

import kotlinx.coroutines.launch
import pwm.ar.arpacinema.auth.retrofit.Auth
import pwm.ar.arpacinema.auth.retrofit.AuthAPI
import retrofit.Call
import retrofit.Callback
import retrofit.Response


class LoginViewModel : ViewModel() {

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val authAPI: Call<JsonObject> = Auth.retrofit.login(AuthAPI::class.java)

    fun login(email: String, password: String) {
        val json = JsonObject().apply {
            addProperty("email", email)
            addProperty("password", password)
        }

        viewModelScope.launch {
            val call = authAPI.login(json)
            call.enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        val jsonResponse = response.body()
                        jsonResponse?.let {
                            when (it.get("status").asString) {
                                "SUCCESS" -> _loginResult.postValue(LoginResult.Success(it))
                                "USER_NOT_REGISTERED" -> _loginResult.postValue(LoginResult.Error("User not registered"))
                                "PSW_ERROR" -> _loginResult.postValue(LoginResult.Error("Incorrect password"))
                                else -> _loginResult.postValue(LoginResult.Error("Unknown error"))
                            }
                        }
                    } else {
                        _loginResult.postValue(LoginResult.Error("Login failed"))
                    }
                }



                override fun onFailure(t: Throwable?) {
                    TODO("Not yet implemented")
                    _loginResult.postValue(LoginResult.Error("Network error: ${t.message}"))
                }

            })
        }
    }

    sealed class LoginResult {
        data class Success(val data: JsonObject) : LoginResult()
        data class Error(val message: String) : LoginResult()
    }
}
