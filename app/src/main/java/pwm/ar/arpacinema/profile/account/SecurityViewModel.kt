package pwm.ar.arpacinema.profile.account

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.Session
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.RetrofitClient

class SecurityViewModel : ViewModel() {
    private val service = RetrofitClient.service
    private val _securityQuestionResult = MutableLiveData<DTO.GenericResponse>()
    val securityQuestionResult: LiveData<DTO.GenericResponse> = _securityQuestionResult

    fun addSecurityQuestionAndAnswer(userId: Int, questionId: Int, answer: String) {
        viewModelScope.launch {
            try {
                val request = DTO.SecurityQuestionRequest(userId, questionId, answer)
                Log.d("SecurityViewModel", "Sending request: $request")
                val response = service.addSecurityQuestionAndAnswer(request)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("SecurityViewModel", "Received response: $responseBody")
                    _securityQuestionResult.value = responseBody ?: DTO.GenericResponse(status = DTO.Stat.ERROR, error = "Empty response body")
                } else {
                    Log.d("SecurityViewModel", "API call failed: ${response.code()}")
                    _securityQuestionResult.value = DTO.GenericResponse(status = DTO.Stat.ERROR, error = "API call failed: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.d("SecurityViewModel", "Exception occurred: ${e.message}")
                _securityQuestionResult.value = DTO.GenericResponse(status = DTO.Stat.ERROR, error = e.message)
            }
        }
    }
}