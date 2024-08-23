package pwm.ar.arpacinema.profile.account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditPasswordViewModel : ViewModel() {

    private val _password = MutableLiveData<String>()
    val password: MutableLiveData<String> = _password


    suspend fun updatePassword() {
        //TODO("implement me or i will crash your app")
    }


}