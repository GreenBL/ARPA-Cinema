package pwm.ar.arpacinema.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.net.SocketTimeoutException


class Sentinel : Interceptor {


    private val _networkStatus = MutableLiveData(NetStat.ONLINE)
    val networkStatus get() = _networkStatus


    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            chain.proceed(chain.request())
        } catch (e: SocketTimeoutException) { // in the event of a connection timeout

            Log.d("Sentinel", "Connection timed out")
            _networkStatus.postValue(NetStat.OFFLINE)

            throw e
        }
    }

    enum class NetStat(val networkStatus: String) {
        ONLINE("ONLINE"),
        OFFLINE("OFFLINE")
    }
}

