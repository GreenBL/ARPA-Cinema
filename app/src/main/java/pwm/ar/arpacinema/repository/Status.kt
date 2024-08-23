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
            val response = chain.proceed(chain.request())

            _status.postValue(response.isSuccessful)
            _globalStatus.postValue(true)

            response
        } catch (e: Exception) {
            _globalStatus.postValue(false)
            throw e
        }
    }
}
