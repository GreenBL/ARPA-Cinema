package pwm.ar.arpacinema.rewards

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.Session
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.RetrofitClient.service
import pwm.ar.arpacinema.repository.Service
import retrofit2.Response

class RewardsViewModel : ViewModel() {
    private val _userPointsAndLevel = MutableLiveData<DTO.UserPointsAndLevelResponse>()
    val userPointsAndLevel: LiveData<DTO.UserPointsAndLevelResponse> get() = _userPointsAndLevel

    fun fetchUserPointsAndLevel(userId: Int) {
        viewModelScope.launch {
            try {
                val response = service.getUserPointsAndLevel(DTO.UserIdPost(userId.toString()))
                if (response.isSuccessful) {
                    _userPointsAndLevel.value = response.body()
                } else {
                    // Handle error
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }
}

