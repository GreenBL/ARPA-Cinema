package pwm.ar.arpacinema.repository

import androidx.lifecycle.MutableLiveData
import okhttp3.Interceptor
import okhttp3.Response

class Status : Interceptor {

    private val _status = MutableLiveData(true)
    val status get() = _status

    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            val catchedResponse = chain.proceed(chain.request())

            // we pass the response value to thje LD so we can **observe it**
            if (catchedResponse.code() == 404) {
                _status.postValue(false)
            }
            //_status.postValue(catchedResponse.isSuccessful)

            catchedResponse
        } catch (e: Exception) {
            _status.postValue(false)
            throw e
        }
    }
}