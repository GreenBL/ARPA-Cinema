package pwm.ar.arpacinema.profile.account

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.Session
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.RetrofitClient
import retrofit2.Response

class AccountViewModel : ViewModel() {
    private val service = RetrofitClient.service
    private var _userEmail = MutableLiveData("")
    val userEmail : MutableLiveData<String?>
        get() = _userEmail

    private var _isSecurityEnabled = MutableLiveData(false)
    val isSecurityEnabled : MutableLiveData<Boolean?>
        get() = _isSecurityEnabled

    fun init() {
        _userEmail.value = Session.user?.email
        assert2FA()
    }

    fun refresh2FA() {
        assert2FA()
    }

    private val api = RetrofitClient.service

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    private fun assert2FA() {
        viewModelScope.launch {
            try {
                val response = api.checkSecurityQuestion(DTO.UserIdPost(Session.userIdStr))

                if (!response.isSuccessful) {
                    throw Exception("Request failed with code: ${response.code()}")
                }

                val body = response.body() ?: throw Exception("Response body is null")

                if (body.status != DTO.Stat.SUCCESS) {
                    throw Exception("Request failed with status: ${body.status}")
                }

                val message = body.message

                when (message) {
                    "Security question is set" -> {
                        _isSecurityEnabled.postValue(true)
                    }
                    "No security question set" -> {
                        _isSecurityEnabled.postValue(false)
                    }
                }

            } catch (e: Exception) {
                Log.e("AccountViewModel", "2FA ERROR: ${e.message}")
            }
        }
    }

        // TODO
    fun deleteUserAccount(context: Context, navController: NavController) {
        viewModelScope.launch {
            val user = Session.user

            if (user == null || user.id == 0) {
                val errorMessage = if (user == null) {
                    "Utente non trovato nella sessione."
                } else {
                    "ID utente non valido. Utente: $user"
                }
                _toastMessage.postValue(errorMessage)
                return@launch
            }

            try {
                val deleteRequest = DTO.DeleteUserRequest(user)
                val response: Response<DTO.GenericResponse> = service.deleteUser(deleteRequest)

                if (response.isSuccessful) {
                    val message = response.body()?.status ?: "SUCCESS"
                    _toastMessage.postValue("Account eliminato con successo.")
                    Session.invalidateUser(context)

                    // Navigate to HomeFragment
                    navController.navigate(R.id.homeFragment)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = "Cancellazione dell'account fallita, Response Code: ${response.code()}, Error: $errorBody"
                    _toastMessage.postValue(errorMessage)
                }
            } catch (e: Exception) {
                _toastMessage.postValue("Errore di rete: ${e.message}")
            }
        }
    }


}

