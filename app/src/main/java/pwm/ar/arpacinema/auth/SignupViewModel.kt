package pwm.ar.arpacinema.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.DTO.Stat.*
import pwm.ar.arpacinema.repository.RetrofitClient
import pwm.ar.arpacinema.util.TextValidator

class SignupViewModel: ViewModel() {

    // all fields of the sign up form (2way binding) + verification flags

    private val _userEmail = MutableLiveData<String>("")
    val userEmail: MutableLiveData<String> = _userEmail
    private var emailFlag : Boolean = false

    private val _userPassword = MutableLiveData<String>("")
    val userPassword: MutableLiveData<String> = _userPassword
    private var passwordFlag : Boolean = false

    private val _userSurname = MutableLiveData<String>("")
    val userSurname: MutableLiveData<String> = _userSurname
    private var surnameFlag : Boolean = false

    private val _userName = MutableLiveData<String>("")
    val userName: MutableLiveData<String> = _userName
    private var nameFlag : Boolean = false

    private val _userPhone = MutableLiveData<String>("")
    val userPhone: MutableLiveData<String> = _userPhone
    private var phoneFlag : Boolean = false

    private val _stat = MutableLiveData(DEFAULT)
    val stat: LiveData<DTO.Stat> = _stat

    private val service = RetrofitClient.service

    suspend fun signUp(){

        val validators = TextValidator.Companion

        // name valid
        nameFlag = validators.B_isValidName(userName.value!!)
        // surname valid
        surnameFlag = validators.B_isValidName(userSurname.value!!)
        // phone valid
        phoneFlag = validators.B_isValidPhone(userPhone.value!!)
        // email valid
        emailFlag = validators.B_isValidEmail(userEmail.value!!)
        // password valid
        passwordFlag = validators.B_isValidPassword(userPassword.value!!)

        Log.d("FLAGS", "name: $nameFlag, surname: $surnameFlag, phone: $phoneFlag, email: $emailFlag, password: $passwordFlag")

        // do we have everything checked? if no we post UNFILLED and return
        if(!(emailFlag && passwordFlag && surnameFlag && nameFlag && phoneFlag)){
            _stat.postValue(UNFILLED)
            return
        }


        val request = DTO.SignUpRequest(
            _userName.value,
            _userSurname.value,
            _userPhone.value,
            _userEmail.value,
            _userPassword.value
        )
        try {
            val response = service.signUp(request)
            val body = response.body()!!
        } catch (e: Exception) {
            _stat.postValue(ERROR)
            return
        }

    }


}
