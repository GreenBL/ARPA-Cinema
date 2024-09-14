package pwm.ar.arpacinema.booking

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.Session
import pwm.ar.arpacinema.dev.Selection
import pwm.ar.arpacinema.model.Movie
import pwm.ar.arpacinema.model.ScreeningDate
import pwm.ar.arpacinema.model.ScreeningTime
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.RetrofitClient
import pwm.ar.arpacinema.util.SeatInterpreter
import java.time.LocalDate
import java.time.LocalTime

class BookingViewModel : ViewModel() {

    // whole movie as arg
    private val _movie = MutableLiveData<Movie>()
    val movie: MutableLiveData<Movie> = _movie

    // user ID arg from the fragm
    private val _userId = MutableLiveData<String?>(null)
    val userId: MutableLiveData<String?> = _userId

    // movie id same
    private val _movieId = MutableLiveData<String?>(null)
    val movieId: MutableLiveData<String?> = _movieId

    // dates fetched
    private val _dates = MutableLiveData(listOf<ScreeningDate>())
    val dates: LiveData<List<ScreeningDate>> = _dates

    // times fetched
    private val _times = MutableLiveData(listOf<ScreeningTime>())
    val times: LiveData<List<ScreeningTime>> = _times

    // preserve RV state
    var datePosition: Int = RecyclerView.NO_POSITION
    var timePosition: Int = RecyclerView.NO_POSITION

    // date selection state
    private val _selectedDate = MutableLiveData<LocalDate?>()
    val selectedDate: MutableLiveData<LocalDate?> = _selectedDate

    // time selection state
    private val _selectedTime = MutableLiveData<ScreeningTime?>()
    val selectedTime: MutableLiveData<ScreeningTime?> = _selectedTime

    // seat selection
    private val _selectedSeats = MutableLiveData(listOf<Int>())
    val selectedSeats: MutableLiveData<List<Int>> = _selectedSeats

    private val _selectionObjects = MutableLiveData(listOf<Selection>())
    val selectionObjects: MutableLiveData<List<Selection>> = _selectionObjects

    // red seats
    private val _redSeats = MutableLiveData(listOf<Int>())
    val redSeats: MutableLiveData<List<Int>> = _redSeats


    // discounts
    private val _discount = MutableLiveData(false)
    val discounts: MutableLiveData<Boolean> = _discount

    private val _free = MutableLiveData(false)
    val free: MutableLiveData<Boolean> = _free

    // price

    private val _price = MutableLiveData(8.0)
    val price: LiveData<Double> = _price

    private val _total = MutableLiveData(0.0)
    val total: LiveData<Double> = _total

    // points
    private val _points = MutableLiveData(0)
    val points: LiveData<Int> = _points

    // level
    private val _level = MutableLiveData(0)
    val level: LiveData<Int> = _level

    suspend fun fetchUserLevelAndPoints() {
        val userId = Session.userIdStr
        val response = RetrofitClient.service.getUserPointsAndLevel(DTO.UserIdPost(userId))
        try {
            if (!response.isSuccessful) {
                Log.e("BookingViewModel", response.message())
                _status.postValue(DTO.Stat.ERROR)
                return
            }
            val body = response.body()

            body?.let {
                _points.postValue(it.points)
                _level.postValue(it.level)
            }
        } catch (e: Exception) {
            _status.postValue(DTO.Stat.NETWORK_ERROR)
            Log.e("BookingViewModel", e.message.toString(), e)
        }
    }

    // status

    private val _status = MutableLiveData(DTO.Stat.DEFAULT)
    val status: MutableLiveData<DTO.Stat> = _status

    fun clearEverything() { // clears seats and selections, NOT DATES OR TIMES
        _selectedSeats.postValue(listOf())
        _selectionObjects.postValue(listOf())
    }

    fun resetViewModel() {
        _userId.postValue(null)
        _movieId.postValue(null)
        _dates.postValue(listOf())
        _times.postValue(listOf())
        _selectedDate.postValue(null)
        _selectedTime.postValue(null)
        _selectedSeats.postValue(listOf())
        _selectionObjects.postValue(listOf())
        _redSeats.postValue(listOf())
        _discount.postValue(false)
        _free.postValue(false)
        _price.postValue(8.0)
    }

    val service = RetrofitClient.service

    fun buyTickets() {

        if (selectedSeats.value.isNullOrEmpty()) return

        viewModelScope.launch {

            val seats = selectedSeats.value
            val seatAsStringList = mutableListOf<String>()

            seats?.forEach {
                    it -> SeatInterpreter.getSeatName(it).also { seatAsStringList.add(it) }
            }
            val buyTicketRequest = DTO.BuyTicketRequest(
                userId.value,
                movieId.value,
                selectedTime.value?.auditorium,
                selectedDate.value,
                selectedTime.value?.time,
                seatAsStringList
            )

            try {
                val response = service.buyTickets(buyTicketRequest)

                if (!response.isSuccessful) {
                    Log.e("BookingViewModel", response.message())
                    _status.postValue(DTO.Stat.ERROR)
                    return@launch
                }

                val body = response.body()

                if (body?.status == DTO.Stat.ERROR) {
                    Log.e("BookingViewModel", body.status.toString())
                    _status.postValue(body.status)
                    return@launch
                }

                if (body?.status == DTO.Stat.PURCHASE_FAIL) {
                    Log.e("BookingViewModel", body.status.toString())
                    _status.postValue(body.status)
                    return@launch
                }

                if (body?.status == DTO.Stat.PURCHASE_COMPLETE) {
                    Log.e("BookingViewModel", body.status.toString())
                    _status.postValue(body.status)
                    return@launch
                }

                Log.d("BookingViewModel", body.toString())
                _status.postValue(body?.status)

            } catch (e: Exception) {
                Log.e("BookingViewModel", e.message.toString())
                _status.postValue(DTO.Stat.NETWORK_ERROR)
            }
        }
    }

    private fun clearDirtyDateList(list: List<ScreeningDate>) : List<ScreeningDate> {
        // remove duplicates and sort by date
        val sortedList = list.sortedBy { it.date }
        return sortedList.distinctBy { it.date }
    }

    private fun clearDirtyTimeList(list: List<ScreeningTime>) : List<ScreeningTime> {
        // remove duplicates and sort by date
        val sortedList = list.sortedBy { it.time }
        return sortedList.distinctBy { it.time }
    }

    suspend fun fetchDates(movieId: String) {
        viewModelScope.launch {

            // serialized request
            val idPost = DTO.MovieIdPost(movieId)

            try {
                val response = service.getMovieDates(idPost)

                if (!response.isSuccessful) {
                    Log.e("BookingViewModel", response.message())
                    _status.postValue(DTO.Stat.ERROR)
                    return@launch
                }

                val body = response.body()

                if (body?.status != DTO.Stat.SUCCESS) {
                    Log.e("BookingViewModel", body?.status.toString())
                    _status.postValue(body?.status)
                    return@launch
                }
                Log.d("BookingViewModel", body.toString())

                if (body.dates.isNullOrEmpty()) {
                    Log.e("BookingViewModel", "No dates found")
                    _status.postValue(DTO.Stat.ERROR)
                    return@launch
                }

                // cleaning the list...
                _dates.postValue(clearDirtyDateList(body.dates))
                _status.postValue(body.status)

            } catch (e: Exception) {
                Log.e("BookingViewModel", e.message.toString())
                _status.postValue(DTO.Stat.NETWORK_ERROR)
            }

        }
    }

    suspend fun fetchTimes(movieId: String) {

        val selectedDate = selectedDate.value

        viewModelScope.launch {
            val movieTimePost = DTO.MovieTimePost(movieId, selectedDate)

            try {
                val response = service.getMovieTimes(movieTimePost)

                if (!response.isSuccessful) {
                    Log.e("BookingViewModel", response.message())
                    _status.postValue(DTO.Stat.ERROR)
                    return@launch
                }

                val body = response.body()

                if (body?.status != DTO.Stat.SUCCESS) {
                    Log.e("BookingViewModel", body?.status.toString())
                    _status.postValue(body?.status)
                }

                Log.d("BookingViewModel", body.toString())

                if (body?.screeningTimes.isNullOrEmpty()) {
                    Log.e("BookingViewModel", "No times found")
                    _status.postValue(DTO.Stat.ERROR)
                    return@launch
                }

                _status.postValue(body?.status)

                val sortedList = clearDirtyTimeList(body?.screeningTimes!!)
                _times.postValue(sortedList)
            } catch (e: Exception) {
                Log.e("BookingViewModel", e.message.toString())
                _status.postValue(DTO.Stat.NETWORK_ERROR)
            }
        }
    }

    suspend fun getRedSeats() {

        if (selectedDate.value == null || selectedTime.value == null) {
            Log.e("Fatal error", "date or time is null")
            return
        }
        val selectedDate = selectedDate.value
        val selectedTime = selectedTime.value

        viewModelScope.launch {
            Log.d("BookingViewModel", "Getting red seats for $selectedDate $selectedTime ${selectedTime?.auditorium}")

            val redSeatsRequest = DTO.RedSeatsRequest(selectedTime?.auditorium, selectedDate, selectedTime?.time)

            try {
                val response = service.getRedSeats(redSeatsRequest)

                if (!response.isSuccessful) {
                    Log.e("BookingViewModel", response.message())
                    _status.postValue(DTO.Stat.ERROR)
                    return@launch
                }

                val body = response.body()

                if (body?.status != DTO.Stat.SUCCESS) {
                    Log.e("BookingViewModel", body?.status.toString())
                    _status.postValue(body?.status)
                    return@launch
                }

                Log.d("BookingViewModel", body.toString())

                val parsed = body.intList

                _redSeats.postValue(parsed)
            } catch (e: Exception) {
                Log.e("BookingViewModel", e.message.toString())
                _status.postValue(DTO.Stat.NETWORK_ERROR)
            }
        }
    }

    fun updateList(list: List<Int>) {

        if (list.isEmpty()) {
            // if list empty clear the live datah
            _selectionObjects.postValue(listOf())
            return
        }

        _selectedSeats.postValue(list)

        // clear the previous selection objects
        _selectionObjects.value = listOf()

        val price = _price.value!!

        if (_discount.value!!) {
            _price.value = price * 0.5
        } else if (_free.value!!) {
            _price.value = 0.0
        }

        var sum = 0.0

        for (i in list) {
            val seatStr = SeatInterpreter.getSeatName(i)

            // add the temp selection object
            _selectionObjects.value = _selectionObjects.value?.plus(
                Selection("temp", selectedDate.value.toString(), "temp", "Sala 3", seatStr, price.toString())
            )

            sum += price

        }
        _total.postValue(sum)
    }
}