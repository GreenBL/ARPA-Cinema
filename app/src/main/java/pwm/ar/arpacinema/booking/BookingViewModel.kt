package pwm.ar.arpacinema.booking

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.dev.Selection
import pwm.ar.arpacinema.model.ScreeningDate
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.RetrofitClient
import pwm.ar.arpacinema.util.SeatInterpreter
import java.time.LocalDate

class BookingViewModel : ViewModel() {

    // dates fetched
    private val _dates = MutableLiveData(listOf<ScreeningDate>())
    val dates: LiveData<List<ScreeningDate>> = _dates

    // preserve RV state
    var datePosition: Int = RecyclerView.NO_POSITION

    // date selection state
    private val _selectedDate = MutableLiveData<LocalDate>()
    val selectedDate: MutableLiveData<LocalDate> = _selectedDate

    // seat selection
    private val _selectedSeats = MutableLiveData(listOf<Int>())
    val selectedSeats: MutableLiveData<List<Int>> = _selectedSeats

    private val _selectionObjects = MutableLiveData(listOf<Selection>())
    val selectionObjects: MutableLiveData<List<Selection>> = _selectionObjects

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

    // status

    private val _status = MutableLiveData(DTO.Stat.DEFAULT)
    val status: MutableLiveData<DTO.Stat> = _status

    fun clearEverything() {
        _selectedSeats.postValue(listOf())
        _selectionObjects.postValue(listOf())
    }

    val service = RetrofitClient.service

    private fun clearDirtyDateList(list: List<ScreeningDate>) : List<ScreeningDate> {
        // remove duplicates and sort by date
        val sortedList = list.sortedBy { it.date }
        return sortedList.distinctBy { it.date }
    }

    fun fetchDates(movieId: String) {
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