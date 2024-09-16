package pwm.ar.arpacinema.rewards

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.Session
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.RetrofitClient.service

class OptionsViewModel : ViewModel() {

    private val _userPointsAndLevel = MutableLiveData<DTO.UserPointsAndLevelResponse>()
    val userPointsAndLevel: LiveData<DTO.UserPointsAndLevelResponse> get() = _userPointsAndLevel

    private val _status = MutableLiveData(DTO.Stat.DEFAULT)
    val status: LiveData<DTO.Stat> get() = _status

    init {
        fetchUserPointsAndLevel(Session.user!!.id)
    }

    fun wakeUp() {

    }

    private fun fetchUserPointsAndLevel(userId: Int){
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
                _status.value = DTO.Stat.NETWORK_ERROR
            }
        }
    }

    // this is horrible

    fun redeemCombo(rewardId: Int){
        viewModelScope.launch {
            val request = DTO.ComboRequest(Session.user!!.id.toString(), rewardId)

            try {
                val response = service.buyCombo(request)

                if (!response.isSuccessful) {
                    throw Exception("Error redeeming combo")
                }

                val body = response.body()

                if (body?.status != DTO.Stat.SUCCESS) {
                    throw Exception("Error redeeming combo")
                }
                fetchUserPointsAndLevel(Session.user!!.id)
                _status.value = body.status
            } catch (e: Exception) {
                Log.e("RewardsViewModel", "Error redeeming combo", e)
                _status.value = DTO.Stat.NETWORK_ERROR
            }
        }

    }

    fun redeemDrink(rewardId: Int){
        viewModelScope.launch {
            val request = DTO.DrinkRequest(Session.user!!.id.toString(), rewardId)

            try {
                val response = service.buyDrink(request)

                if (!response.isSuccessful) {
                    throw Exception("Error redeeming drink")
                }

                val body = response.body()

                if (body?.status != DTO.Stat.SUCCESS) {
                    throw Exception("Error redeeming drink")
                }
                fetchUserPointsAndLevel(Session.user!!.id)
                _status.value = body.status
            } catch (e: Exception) {
                Log.e("RewardsViewModel", "Error redeeming drk", e)
                _status.value = DTO.Stat.NETWORK_ERROR
            }
        }
    }

    fun redeemPopcorn(rewardId: Int){
        viewModelScope.launch {
            val request = DTO.PopcornRequest(Session.user!!.id.toString(), rewardId)

            try {
                val response = service.buyPopcorn(request)

                if (!response.isSuccessful) {
                    throw Exception("Error redeeming combo")
                }

                val body = response.body()

                if (body?.status != DTO.Stat.SUCCESS) {
                    throw Exception("Error redeeming combo")
                }
                fetchUserPointsAndLevel(Session.user!!.id)
                _status.value = body.status
            } catch (e: Exception) {
                Log.e("RewardsViewModel", "Error redeeming combo", e)
                _status.value = DTO.Stat.NETWORK_ERROR
            }
        }

    }

    fun resetStatus() {
        _status.value = DTO.Stat.DEFAULT
    }



}