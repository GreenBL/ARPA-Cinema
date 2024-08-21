package pwm.ar.arpacinema.profile

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
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
        _isSecurityEnabled.value = false // NEED TO QUERY THE SERVER FOR THIS TODO TODO TODO TODO
    }

    // LiveData to handle messages
    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: MutableLiveData<String>
        get() = _toastMessage


    fun deleteUserAccount(userId: Int) {
        viewModelScope.launch {
            try {
                val response: Response<DTO.GenericResponse> = service.deleteUser(userId)
                if (response.isSuccessful) {
                    val message = response.body()?.message ?: "Account deleted successfully"
                    _toastMessage.postValue(message)
                } else {
                    _toastMessage.postValue("Failed to delete account")
                }
            } catch (e: Exception) {
                _toastMessage.postValue("Network error: ${e.message}")
            }
        }
    }

}