package pwm.ar.arpacinema.profile.account

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pwm.ar.arpacinema.Session
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.RetrofitClient

class EditEmailViewModel : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: MutableLiveData<String> = _email


    fun init() {
        _email.value = Session.user?.email
    }

    suspend fun updateEmail(): Boolean {
        val editEmailRequest = DTO.EditEmailRequest(
            id = Session.user?.id.toString(),
            email = _email.value
        )
        try {
            val response = RetrofitClient.service.updateEmail(editEmailRequest)
            if (response.isSuccessful) {
                Session.user?.email = _email.value.toString()
                return true
            } else {
                return false
            }

        }catch (e: Exception) {
            Log.e("EditEmailViewModel", "Error updating email")

        }
        return false
    }
    override fun onCleared() {
        super.onCleared()
        Log.d("EditEmailViewModel", "EditEmailViewModel cleared")

    }
}