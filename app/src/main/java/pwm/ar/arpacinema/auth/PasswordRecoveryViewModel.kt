package pwm.ar.arpacinema.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pwm.ar.arpacinema.model.Questions
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.DTO.Stat.*
import pwm.ar.arpacinema.repository.RetrofitClient

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

            if (question.isNullOrEmpty()) {
                _status.postValue(USER_NOT_REGISTERED)
                return
            }

            // from da enum to da stringa
            val actualQuestion = Questions.getQuestionById(question.toInt())

            _question.postValue(actualQuestion)
            _status.postValue(SUCCESS)
        } catch (e: Exception) {
            _status.postValue(NETWORK_ERROR)
        }

    }

}