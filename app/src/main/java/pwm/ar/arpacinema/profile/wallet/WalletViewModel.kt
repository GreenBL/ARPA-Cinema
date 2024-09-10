package pwm.ar.arpacinema.profile.wallet

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pwm.ar.arpacinema.Session
import pwm.ar.arpacinema.repository.DTO
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

        _fullName.postValue(Session.user?.name + " " + Session.user?.surname)

    }

    suspend fun fetchBalance() {
        try {
            val userId = Session.user?.id.toString()
            val response = api.getAmount(DTO.UserIdPost(userId))

            if (!response.isSuccessful) {
                Log.e("WalletViewModel", "Error fetching balance: ${response.message()}")
                _responseStatus.postValue(Stat.SERVER_ERROR)
                return
            }

            val balance = response.body()?.amount
            if (balance != null) {
                _balance.postValue(balance)
                _responseStatus.postValue(Stat.SUCCESS)
            } else {
                Log.e("WalletViewModel", "Error: Balance is null")
                _responseStatus.postValue(Stat.SERVER_ERROR)
            }
        } catch (e: Exception) {
            Log.e("WalletViewModel", "Network error: ${e.message}")
            _responseStatus.postValue(Stat.NETWORK_ERROR)
        }
    }

    suspend fun increaseBalance() {
        Log.d("WalletViewModel", "increaseBalance called")

        val amountString = inputAmount.value
        Log.d("WalletViewModel", "inputAmount: $amountString")

        if (amountString.isNullOrEmpty()) {
            _responseStatus.postValue(Stat.UNFILLED)
            Log.e("WalletViewModel", "Amount is null or empty.")
            return
        }

        try {
            val amount = amountString.toDouble()
            Log.d("WalletViewModel", "Parsed amount: $amount")

            val balanceUpdateRequest = BalanceUpdateRequest(Session.user?.id.toString(), amount)
            Log.d("WalletViewModel", "Sending balance update request: $balanceUpdateRequest")

            val response = api.updateUserBalance(balanceUpdateRequest)
            Log.d("WalletViewModel", "Response received: ${response.code()}")

            if (!response.isSuccessful) {
                val errorBody = response.errorBody()?.string()
                Log.e("WalletViewModel", "Server error: $errorBody")
                _responseStatus.postValue(Stat.SERVER_ERROR)
                return
            }

            val error = response.body()?.error
            if (error != null) {
                Log.e("WalletViewModel", "Server error: $error")
                _responseStatus.postValue(Stat.SERVER_ERROR)
                return
            }

            Log.d("WalletViewModel", "Update successful. Current balance: ${_balance.value}, Amount added: $amount")
            _responseStatus.postValue(Stat.SUCCESS)
            _balance.postValue((_balance.value ?: 0.0) + amount)

        } catch (e: NumberFormatException) {
            Log.e("WalletViewModel", "Number format error: ${e.message}", e)
            _responseStatus.postValue(Stat.SERVER_ERROR)
        } catch (e: Exception) {
            Log.e("WalletViewModel", "Network error: ${e.message}", e)
            _responseStatus.postValue(Stat.NETWORK_ERROR)
        }
    }

}