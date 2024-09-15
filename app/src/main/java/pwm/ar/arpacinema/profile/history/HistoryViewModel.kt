package pwm.ar.arpacinema.profile.history

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

class HistoryViewModel : ViewModel() {

    private val _tickets = MutableLiveData<List<Ticket>?>(null)
    val tickets: LiveData<List<Ticket>?> = _tickets

    private val _compressedTickets = MutableLiveData<List<Ticket>?>(null)
    val compressedTickets: LiveData<List<Ticket>?> = _compressedTickets

    val service = RetrofitClient.service

    init {
        fetchTickets()
    }

    private fun fetchTickets() {
        val scope = viewModelScope
        scope.launch {

            val userIdPost = DTO.UserIdPost(Session.user?.id.toString())

            lateinit var response: Response<DTO.TicketResponse>
            try {
                response = service.getTickets(userIdPost)

                if (!response.isSuccessful) {
                    throw Exception("Error fetching tickets: ${response.code()}")
                }

                if (response.body()?.status != DTO.Stat.SUCCESS) {
                    throw Exception("No tickets available, but server returned status: ${response.body()?.status}")
                }

                val ticketList = response.body()?.ticket//s
                Log.d("TicketsViewModel", "Fetched tickets: $ticketList")
                _tickets.postValue(ticketList)

                val compressedTicketsList = ticketList?.toMutableList()
                    ?: throw Exception("No tickets available")

                val uniqueTickets = compressedTicketsList?.distinctBy { ticket ->
                    Pair(ticket.filmTitle, ticket.screeningDate)
                }


                // and finally sort this mess
                uniqueTickets?.sortedBy {
                    it.screeningDate
                }

                uniqueTickets?.let {
                    _compressedTickets.postValue(it)
                }



            } catch (e: Exception) {
                _tickets.postValue(null)
                Log.e("TicketsViewModel", "Error fetching tickets", e)
            }

        }

    }

}