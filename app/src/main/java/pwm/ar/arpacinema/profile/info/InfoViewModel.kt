package pwm.ar.arpacinema.profile.info

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.Session
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.RetrofitClient.service

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

    suspend fun updateUser() : Boolean {
        val updateUserRequest = DTO.UpdateUserRequest(
            id = Session.user?.id ?: 0,
            name = _userName.value,
            surname = _userSurname.value,
            phone = _userPhone.value
        )
        try {
            val response = service.updateUser(updateUserRequest)
            if (response.isSuccessful) {
                Session.user?.name = _userName.value.toString()
                Session.user?.surname = _userSurname.value.toString()
                Session.user?.phone = _userPhone.value.toString()
                return true
            } else {
                return false
            }
        } catch (e: Exception) {
            Log.e("InfoViewModel", "Error updating user")
        }
        return false
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("InfoViewModel", "InfoViewModel cleared")
    }
}
