package pwm.ar.arpacinema.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pwm.ar.arpacinema.Session

class InfoViewModel : ViewModel() {

    private var _userName = MutableLiveData("")
    val userName : MutableLiveData<String?>
        get() = _userName

    private var _userSurname = MutableLiveData("")
    val userSurname : MutableLiveData<String?>
        get() = _userSurname

    private var _userPhone = MutableLiveData("")
    val userPhone : MutableLiveData<String?>
        get() = _userPhone

    fun init() {
        _userName.value = Session.user?.name
        _userSurname.value = Session.user?.surname
        _userPhone.value = Session.user?.phone
    }


}