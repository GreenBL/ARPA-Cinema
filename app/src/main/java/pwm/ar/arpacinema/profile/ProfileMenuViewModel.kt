package pwm.ar.arpacinema.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pwm.ar.arpacinema.Session
import pwm.ar.arpacinema.model.User

class ProfileMenuViewModel : ViewModel() {

    private val _loggedUser : MutableLiveData<User> by lazy {
        MutableLiveData<User>(Session.loggedInUser)
    }
    val loggedUser : LiveData<User>
        get() = _loggedUser

}