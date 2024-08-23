package pwm.ar.arpacinema.profile.account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pwm.ar.arpacinema.repository.DTO

class EditEmailViewModel : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: MutableLiveData<String> = _email


    suspend fun updateEmail() {
        TODO("implement me or i will crash your app")
    }

}