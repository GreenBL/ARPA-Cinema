package pwm.ar.arpacinema.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pwm.ar.arpacinema.Session
import pwm.ar.arpacinema.repository.DTO.BalanceUpdateRequest
import pwm.ar.arpacinema.repository.DTO.GenericResponse
import pwm.ar.arpacinema.repository.RetrofitClient.service
import pwm.ar.arpacinema.model.User
import retrofit2.Response

class WalletViewModel : ViewModel() {

    private val _userName = MutableLiveData<String>()
    val userName: MutableLiveData<String>
        get() = _userName

    private val _userSurname = MutableLiveData<String>()
    val userSurname: MutableLiveData<String>
        get() = _userSurname

    init {
        init()
    }

    fun init() {
        _userName.value = Session.user?.name ?: ""
        _userSurname.value = Session.user?.surname ?: ""
    }



    suspend fun getBalance(id: String): Float {
        return withContext(Dispatchers.IO) {
            // Replace this with the actual implementation
            3.99f
        }
    }


    suspend fun updateBalance(id: String, amount: Float): Response<GenericResponse> {
        return withContext(Dispatchers.IO) {
            val balanceUpdateRequest = BalanceUpdateRequest(id, null, null, amount) // `name` and `surname` are optional
            service.updateUserBalance(balanceUpdateRequest)
        }
    }

}

