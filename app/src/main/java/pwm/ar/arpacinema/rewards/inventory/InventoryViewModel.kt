package pwm.ar.arpacinema.rewards.inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.model.Reward
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.RetrofitClient

class InventoryViewModel : ViewModel() {
    private val _redeemedItems = MutableLiveData<List<Reward>>()
    val redeemedItems: LiveData<List<Reward>> get() = _redeemedItems

    fun fetchRedeemedItems(userId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.service.getRedeemedItems(DTO.UserIdPost(userId.toString()))
                if (response.isSuccessful) {
                    _redeemedItems.value = response.body()?.items ?: emptyList()
                } else {
                    // Handle error response
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }
}
