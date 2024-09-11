package pwm.ar.arpacinema.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pwm.ar.arpacinema.model.Questions
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.DTO.Stat.*
import pwm.ar.arpacinema.repository.RetrofitClient
import pwm.ar.arpacinema.util.TextValidator

class PasswordRecoveryViewModel : ViewModel() {

    // 2WAY EMAIL
    private val _email = MutableLiveData("")
    val email: MutableLiveData<String> = _email

    private val _status = MutableLiveData(DEFAULT)
    val status: MutableLiveData<DTO.Stat> = _status

    private val _question = MutableLiveData("")
    val question: MutableLiveData<String> = _question

    private val _answer = MutableLiveData("")
    val answer: MutableLiveData<String> = _answer

    private val _newPassword = MutableLiveData("")
    val newPassword: MutableLiveData<String> = _newPassword

    var compareAnswer : String? = null
    var userId : String? = null


    private val service = RetrofitClient.service

    suspend fun sendEmail() {
        if (email.value.isNullOrEmpty()) {
            _status.postValue(UNFILLED)
            return
        }

        val request = DTO.EmailPost(email.value)

        try {
            val response = service.getSecurityQuestion(request)

            if (!response.isSuccessful) {
                _status.postValue(NETWORK_ERROR)
                return
            }

            if (response.body()?.status != SUCCESS) {
                _status.postValue(USER_NOT_REGISTERED)
                return
            }

            val question = response.body()?.question
            val answer = response.body()?.answer

            if (answer.isNullOrEmpty() || question.isNullOrEmpty()) {
                _status.postValue(ERROR)
                return
            }

            // from da enum to da stringa
            val actualQuestion = Questions.getQuestionById(question.toInt())
            compareAnswer = answer
            userId = response.body()?.userId

            _question.postValue(actualQuestion)
            _status.postValue(SUCCESS)
        } catch (e: Exception) {
            Log.e("PasswordRecoveryViewModel", "sendEmail: ${e.message}")
            _status.postValue(NETWORK_ERROR)
        }

    }

    suspend fun assertAnswer() {

        if (answer.value.isNullOrEmpty()) {
            return
        }

        if (newPassword.value.isNullOrEmpty()) {
            return
        }

        if (answer.value != compareAnswer) {
            _status.postValue(PSW_ERROR)
            return
        }

        val request = DTO.EditPasswordRequest(userId, newPassword.value)

        try {
            val response = service.editPassword(request)

            if (!response.isSuccessful) {
                _status.postValue(NETWORK_ERROR)
                return
            }

            if (response.body()?.status != SUCCESS) {
                _status.postValue(SERVER_ERROR)
                return
            }

            _status.postValue(PASSWORD_EDITED)



        } catch (e: Exception) {
            Log.e("PasswordRecoveryViewModel", "assertAnswer: ${e.message}")
            _status.postValue(NETWORK_ERROR)
        }
    }

    fun checkFields() : Boolean{
        // if answer is empty, return false
        if (answer.value.isNullOrEmpty()) {
            return false
        }
        // if new password suks false
        val validator = TextValidator.Companion::B_isValidPassword

        if (newPassword.value.isNullOrEmpty()) {
            return false
        }

        if (!validator(newPassword.value!!)) {
            return false
        }

        return true
    }

}
