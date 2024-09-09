package pwm.ar.arpacinema.tickets

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.Session
import pwm.ar.arpacinema.model.Ticket
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.RetrofitClient
import retrofit2.Response

class TicketsViewModel : ViewModel() {

    private val _tickets = MutableLiveData<List<Ticket>?>(null)
    val tickets: LiveData<List<Ticket>?> = _tickets

    val service = RetrofitClient.service
    private val scope = viewModelScope

    init {
        Log.d("TicketsViewModel", "init")
        fetchTickets()
    }

    private fun fetchTickets() {
        scope.launch {

            val userIdPost = DTO.UserIdPost(Session.user?.id.toString())

            lateinit var response: Response<DTO.TicketResponse>
            try {
                response = service.getTickets(userIdPost)

                if (!response.isSuccessful) {
                    throw Exception("Error fetching tickets: ${response.code()}")
                }

                if (response.body()?.status != DTO.Stat.SUCCESS) {
                    throw Exception("Error fetching tickets: ${response.body()?.status}")
                }

                val ticketList = response.body()?.ticket//s
                Log.d("TicketsViewModel", "Fetched tickets: $ticketList")
                _tickets.postValue(ticketList)

            } catch (e: Exception) {
                Log.e("TicketsViewModel", "Error fetching tickets", e)
            }

        }

    }


}