package pwm.ar.arpacinema.profile.wallet

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pwm.ar.arpacinema.Session
import pwm.ar.arpacinema.repository.RetrofitClient
import pwm.ar.arpacinema.repository.DTO.*

class WalletViewModel : ViewModel() {

    private val _responseStatus = MutableLiveData(Stat.DEFAULT)
    val responseStatus get() = _responseStatus

    private val _balance = MutableLiveData(0.0)
    val balance get() = _balance

    private val _fullName = MutableLiveData("")
    val fullName get() = _fullName

    private val _inputAmount = MutableLiveData<String?>(null)
    val inputAmount get() = _inputAmount

    val api = RetrofitClient.service

    init {
        _balance.postValue(69.420) // todo: fetch from server
        _fullName.postValue(Session.user?.name + " " + Session.user?.surname)

    }

    suspend fun increaseBalance() {
        if (inputAmount.value == null) {
            _responseStatus.postValue(Stat.UNFILLED)
            return
        }
        try {
            val balanceUpdateRequest = BalanceUpdateRequest(Session.user?.id.toString(), inputAmount.value!!.toDouble())
            val response = api.updateUserBalance(balanceUpdateRequest)

            if (!response.isSuccessful) {
                _responseStatus.postValue(Stat.SERVER_ERROR)
                return
            }

            val error = response.body()?.error
            if (error != null) {
                Log.e("WalletViewModel", "Server error: $error")
                _responseStatus.postValue(Stat.SERVER_ERROR)
                return
            }

            _responseStatus.postValue(Stat.SUCCESS)
            _balance.postValue(_balance.value!! + inputAmount.value!!.toDouble())

        } catch (e: Exception) {
            _responseStatus.postValue(Stat.NETWORK_ERROR)
        }
    }


}