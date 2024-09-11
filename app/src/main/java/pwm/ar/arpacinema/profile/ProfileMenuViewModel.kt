package pwm.ar.arpacinema.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.model.User
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.RetrofitClient
import pwm.ar.arpacinema.repository.Service

class ProfileMenuViewModel : ViewModel() {

    private var _userName = MutableLiveData("")
    val userName: LiveData<String>
        get() = _userName

    private var _userSurname = MutableLiveData("")
    val userSurname: LiveData<String>
        get() = _userSurname

    private var _userLevel = MutableLiveData(0)
    val userLevel: LiveData<Int>
        get() = _userLevel

    private var _userPoints = MutableLiveData(0)
    val userPoints: LiveData<Int>
        get() = _userPoints

    private val apiService: Service = RetrofitClient.service // Assicurati che RetrofitClient.service sia inizializzato correttamente

    fun fetchUserPointsAndLevel(userId: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getUserPointsAndLevel(DTO.UserIdPost(userId))
                if (response.isSuccessful && response.body()?.status == "SUCCESS") {
                    response.body()?.let {
                        val points = it.points ?: 0
                        Log.d("ProfileMenuViewModel", "Fetched points: $points")
                        _userPoints.postValue(points)
                        _userLevel.postValue(it.level ?: 0)
                    }
                } else {
                    Log.e("API Error", "Failed to fetch user data")
                }
            } catch (e: Exception) {
                Log.e("API Error", "Exception: ${e.message}")
            }
        }
    }


    fun setUser(user: User) {
        _userName.value = user.name
        _userSurname.value = user.surname
        fetchUserPointsAndLevel(user.id.toString())
    }

    fun clearUser() {
        _userName.value = ""
        _userSurname.value = ""
        _userLevel.value = 0
        _userPoints.value = 0
    }
}
