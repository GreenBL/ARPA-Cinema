package pwm.ar.arpacinema.profile.account

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pwm.ar.arpacinema.Session
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.RetrofitClient

class EditPasswordViewModel : ViewModel() {

    private val _password = MutableLiveData<String>()
    val password: MutableLiveData<String> = _password

    suspend fun updatePassword(): Boolean {
        val editPasswordRequest = DTO.EditPasswordRequest(
            id = Session.user?.id.toString(),
            password = _password.value
        )
        return try {
            val response = RetrofitClient.service.editPassword(editPasswordRequest)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody?.status == DTO.Stat.SUCCESS) {
                    // Password updated successfully, handle success
                    Log.d("EditPasswordViewModel", "Password updated successfully")
                    return true
                } else {
                    // Error message from the server
                    Log.e("EditPasswordViewModel", "Server Error: ${responseBody?.error ?: "Unknown error"}")
                    return false
                }
            } else {
                // Response is not successful (e.g., 4xx or 5xx HTTP error codes)
                Log.e("EditPasswordViewModel", "Response Error: ${response.errorBody()?.string() ?: "Unknown error"}")
                return false
            }
        } catch (e: Exception) {
            // Exception caught (e.g., network failure)
            Log.e("EditPasswordViewModel", "Exception: ${e.message}", e)
            return false
        }
    }

    fun setPassword(newPassword: String) {
        _password.value = newPassword
    }
}
