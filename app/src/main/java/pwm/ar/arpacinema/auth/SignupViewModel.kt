package pwm.ar.arpacinema.auth

//import android.app.Application
//import android.util.Log
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import com.google.gson.Gson
//import com.google.gson.JsonObject
//import pwm.ar.arpacinema.auth.retrofit.Auth
//import pwm.ar.arpacinema.auth.retrofit.SignupRequest
//import retrofit.Response
//import retrofit.Call


//class SignupViewModel(application: Application) : AndroidViewModel(application) {
//
//    private val _signupStatus = MutableLiveData<String?>()
//    val signupStatus: LiveData<String?> get() = _signupStatus
//
//    private val _networkError = MutableLiveData<Boolean>()
//    val networkError: LiveData<Boolean> get() = _networkError
//
//    fun signup(signupRequest: SignupRequest) {
//        val gson = Gson()
//        val jsonObject = gson.toJsonTree(signupRequest).asJsonObject
//        Auth.retrofit.signup(jsonObject).enqueue(object : retrofit.Callback<JsonObject> {
//            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
//                if (response.isSuccessful) {
//                    _signupStatus.value = response.body()?.get("status")?.asString
//                } else {
//                    _signupStatus.value = "Errore nella registrazione"
//                }
//            }
//
//            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
//                _networkError.value = true
//                Log.e("SignupViewModel", "Network Error: ${t.message}")
//            }
//        })
//    }
//}
