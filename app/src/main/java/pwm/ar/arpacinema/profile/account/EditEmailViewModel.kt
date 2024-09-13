package pwm.ar.arpacinema.profile.account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.RetrofitClient

class EditEmailViewModel : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: MutableLiveData<String> = _email

    suspend fun updateEmail() {
        val newEmail = email.value ?: return

        // Burada ID'yi ve diğer gerekli bilgileri ayarlamanız gerekiyor.
        val userId = "user-id" // Kullanıcı ID'sini buradan alabilirsiniz veya ekleyebilirsiniz
        val response = RetrofitClient.service.updateEmail(
            DTO.EditEmailRequest(id = userId, email = newEmail)
        )

        if (!response.isSuccessful) {
            throw Exception("Errore durante l'aggiornamento dell'email")
        }
    }
}
