package pwm.ar.arpacinema.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import okhttp3.Interceptor
import okhttp3.Response
import java.net.SocketTimeoutException

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
        } catch (e: SocketTimeoutException) { // in the event of a connection timeout
            _globalStatus.postValue(false)
            Log.e("Status", "Network timeout:", e)
            throw e
        }
    }
}