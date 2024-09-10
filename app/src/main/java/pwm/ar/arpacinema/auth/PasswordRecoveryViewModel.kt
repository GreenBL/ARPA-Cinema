package pwm.ar.arpacinema.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.DTO.Stat.*

class PasswordRecoveryViewModel : ViewModel() {

    // 2WAY EMAIL
    private val _email = MutableLiveData("")
    val email: MutableLiveData<String> = _email

    private val _status = MutableLiveData(DEFAULT)
    val status: MutableLiveData<DTO.Stat> = _status

    suspend fun sendEmail() {
        if (email.value.isNullOrEmpty()) {
            _status.postValue(UNFILLED)
            return
        }
    }

}