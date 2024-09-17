package pwm.ar.arpacinema.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException


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
        } catch (e: UnknownHostException) {
            Log.d("Sentinel", "Unknown host")
            _networkStatus.postValue(NetStat.OFFLINE)
            throw e
        } catch (e: ConnectException) {
            Log.d("Sentinel", "Connection failed")
            _networkStatus.postValue(NetStat.OFFLINE)
            throw e
        } catch (e: SSLException) {
            Log.d("Sentinel", "SSL exception")
            _networkStatus.postValue(NetStat.OFFLINE)
            throw e
        } catch (e: SocketException) {
            Log.d("Sentinel", "Socket exception")
            _networkStatus.postValue(NetStat.OFFLINE)
            throw e
        } catch (e: IOException) {
            Log.d("Sentinel", "IO exception")
            _networkStatus.postValue(NetStat.OFFLINE)
            throw e
        }
    }

    enum class NetStat(val networkStatus: String) {
        ONLINE("ONLINE"),
        OFFLINE("OFFLINE")
    }
}

