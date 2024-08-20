package pwm.ar.arpacinema.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pwm.ar.arpacinema.Session

class AccountViewModel : ViewModel() {

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



}