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
import pwm.ar.arpacinema.model.RewardType
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.RetrofitClient.service
import pwm.ar.arpacinema.repository.Service
import retrofit2.Response

class RewardsViewModel : ViewModel() {
    private val _userPointsAndLevel = MutableLiveData<DTO.UserPointsAndLevelResponse>()
    val userPointsAndLevel: LiveData<DTO.UserPointsAndLevelResponse> get() = _userPointsAndLevel

    private val _status = MutableLiveData(DTO.Stat.DEFAULT)
    val status: LiveData<DTO.Stat> get() = _status

    fun fetchUserPointsAndLevel(userId: Int) {
        viewModelScope.launch {
            try {
                val response = service.getUserPointsAndLevel(DTO.UserIdPost(userId.toString()))
                if (response.isSuccessful) {
                    _userPointsAndLevel.value = response.body()
                } else {
                    throw Exception("Error fetching user points and level")
                }

            } catch (e: Exception) {
                Log.e("RewardsViewModel", "Error fetching user points and level", e)
                _status.value = DTO.Stat.ERROR
            }
        }
    }

    fun redeemDiscount(reward: Reward) {
        viewModelScope.launch {
            val userId = Session.userIdStr

            val rewardType = when (reward.size) {
                "Sconto biglietto (50%)" -> RewardType.ticket_discount
                else -> RewardType.free_ticket
            }
            val redeemDiscountRequest = DTO.RedeemDiscountRequest(userId, rewardType)

            try {
                val response = service.redeemDiscount(redeemDiscountRequest)
                if (!response.isSuccessful) {
                    throw Exception("Error redeeming discount")
                }

                if (response.body()?.status != DTO.Stat.SUCCESS) {
                    Log.e("RewardsViewModel", "Error redeeming discount: ${response.body()?.message}")
                    throw Exception("Error redeeming discount")
                }

                _status.value = DTO.Stat.SUCCESS
                fetchUserPointsAndLevel(userId.toInt())
                _status.postValue(DTO.Stat.DEFAULT)

            } catch (e: Exception) {
                Log.e("RewardsViewModel", "Error redeeming discount", e)
                _status.value = DTO.Stat.ERROR
            }



        }
    }


}

