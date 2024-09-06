package pwm.ar.arpacinema.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import pwm.ar.arpacinema.dev.Selection
import pwm.ar.arpacinema.util.SeatInterpreter
import java.time.LocalDate

class BookingViewModel : ViewModel() {

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

    fun clearEverything() {
        _selectedSeats.postValue(listOf())
        _selectionObjects.postValue(listOf())
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
                Selection("temp", selectedDate.value.toString(), "temp", seatStr, price.toString())
            )

            sum += price

        }
        _total.postValue(sum)
    }
}