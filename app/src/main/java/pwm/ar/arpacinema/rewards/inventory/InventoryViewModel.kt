package pwm.ar.arpacinema.rewards.inventory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pwm.ar.arpacinema.Session
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
                    val items = response.body()?.items ?: emptyList()
                    val discounts = parseDiscountsToRewards()
                    val list = items.toMutableList()
                    list.addAll(discounts)
                    _redeemedItems.value = list
                } else {
                    // todo
                }
            } catch (e: Exception) {
                // todo
            }
        }
    }

    private suspend fun parseDiscountsToRewards() : List<Reward> {
        var discountCount = 0
        var freeCount = 0
        val tempList = mutableListOf<Reward>()

        try {
            val response = withContext(Dispatchers.IO) {
                RetrofitClient.service.getRewards(DTO.UserIdPost(Session.userIdStr))
            }

            if (!response.isSuccessful) {
                throw Exception("Error fetching rewards")
            }

            discountCount = response.body()?.ticketDiscounts!!
            freeCount = response.body()?.rewards!! // its actually free tickets, mistake on my part but no time to refactor

            for (i in 1..discountCount) {
                tempList.add(Reward(0, "Sconto", "(50%)", 700, ""))
            }
            for (i in 1..freeCount) {
                tempList.add(Reward(0, "Sconto", "(100%)", 700, ""))
            }

        } catch (e: Exception) {
            Log.e("InventoryViewModel", "Error fetching rewards", e)
        }

        return tempList
    }
}
