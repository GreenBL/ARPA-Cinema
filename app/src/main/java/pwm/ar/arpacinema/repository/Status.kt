package pwm.ar.arpacinema.repository

import androidx.lifecycle.MutableLiveData
import okhttp3.Interceptor
import okhttp3.Response

class Status : Interceptor {

    private val _globalStatus = MutableLiveData(true)
    val globalStatus get() = _globalStatus

    private val _status = MutableLiveData(true)
    val status get() = _status

    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            val catchedResponse = chain.proceed(chain.request())

            // we pass the response value to thje LD so we can **observe it**
            _status.postValue(catchedResponse.isSuccessful)
            _globalStatus.postValue(true)

            catchedResponse
        } catch (e: Exception) { // such as timeouts, etc.
            _globalStatus.postValue(false)

            chain.proceed(chain.request())
        }
    }
}