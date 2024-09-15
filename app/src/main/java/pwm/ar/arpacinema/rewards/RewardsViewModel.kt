package pwm.ar.arpacinema.rewards

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.Session
import pwm.ar.arpacinema.model.Reward
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.RetrofitClient
import pwm.ar.arpacinema.repository.RetrofitClient.service
import pwm.ar.arpacinema.repository.Service
import retrofit2.Response

class RewardsViewModel : ViewModel() {
    private val _userPointsAndLevel = MutableLiveData<DTO.UserPointsAndLevelResponse>()
    val userPointsAndLevel: LiveData<DTO.UserPointsAndLevelResponse> get() = _userPointsAndLevel

    private val _redeemResponse = MutableLiveData<DTO.RedeemResponse?>()
    val redeemResponse: LiveData<DTO.RedeemResponse?> get() = _redeemResponse

    private val service: Service = RetrofitClient.service

    fun fetchUserPointsAndLevel(userId: Int) {
        viewModelScope.launch {
            try {
                val response = service.getUserPointsAndLevel(DTO.UserIdPost(userId.toString()))
                if (response.isSuccessful) {
                    _userPointsAndLevel.value = response.body()
                } else {
                    // Gestisci errori
                }
            } catch (e: Exception) {
                // Gestisci eccezioni
            }
        }
    }

    fun redeemReward(userId: Int, reward: Reward) {
        viewModelScope.launch {
            try {
                Log.d("RewardsViewModel", "Redeem reward called for user: $userId, reward: ${reward.category}")
                val rewardType = when (reward.category) {
                    "Sconto biglietto (50%)" -> "ticket_discount"  // Ensure this matches backend expectation
                    // Add other reward types if necessary
                    else -> ""
                }

                if (rewardType.isNotEmpty()) {
                    val request = DTO.RedeemRequest(userId, rewardType)
                    val response = service.selectDiscounts(request)
                    Log.d("RewardsViewModel", "API response: ${response.body()}")
                    if (response.isSuccessful) {
                        _redeemResponse.postValue(response.body())
                    } else {
                        Log.e("RewardsViewModel", "Error response: ${response.errorBody()?.string()}")
                    }
                } else {
                    Log.e("RewardsViewModel", "Invalid reward type: ${reward.category}")
                }
            } catch (e: Exception) {
                Log.e("RewardsViewModel", "Exception during redeeming reward", e)
            }
        }
    }




}
