package pwm.ar.arpacinema.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pwm.ar.arpacinema.model.User

class ProfileMenuViewModel : ViewModel() {

    private var _userName = MutableLiveData("")
    val userName : LiveData<String>
        get() = _userName

    private var _userSurname = MutableLiveData("")
    val userSurname : LiveData<String>
        get() = _userSurname

    private var _userLevel = MutableLiveData(0)
    val userLevel : LiveData<Int>
        get() = _userLevel

    private var _userPoints = MutableLiveData(0)
    val userPoints : LiveData<Int>
        get() = _userPoints

    fun setUser(user: User) {
        _userName.value = user.name
        _userSurname.value = user.surname
        _userLevel.value = user.level
        _userPoints.value = user.stars
    }

    fun clearUser() { // debug only
        _userName.value = ""
        _userSurname.value = ""
        _userLevel.value = 0
        _userPoints.value = 0
    }

}