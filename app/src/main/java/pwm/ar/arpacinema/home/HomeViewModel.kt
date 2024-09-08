package pwm.ar.arpacinema.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.model.Movie
import pwm.ar.arpacinema.model.Promotion
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.RetrofitClient


class HomeViewModel : ViewModel() {

    private val scope = viewModelScope
    // movie list bottom
    private val _movies = MutableLiveData<List<Movie>?>(null)
    val movies: LiveData<List<Movie>?> = _movies

    private val _promos = MutableLiveData<List<Promotion>?>(null)
    val promos: LiveData<List<Promotion>?> = _promos

    // retro
    private val service = RetrofitClient.service

    // init
    init {
        getMovies()
        getPromos()
    }

    // get promo list
    private fun getPromos() {
        scope.launch {
            try {
                val response = service.getPromotions()

                if (!response.isSuccessful) {
                    throw Exception("Network error")
                }

                if (response.body()?.status != DTO.Stat.SUCCESS) {
                    throw Exception("Server error")
                }

                val promos = response.body()?.promotions
                _promos.postValue(promos)
                Log.d("HomeViewModel", "Promos: $promos")
            } catch (e: Exception) {
                // todo
            }
        }

    }
    // get movie list
    private fun getMovies() {
        scope.launch {
            try {
                val response = service.getMoviesOfTheWeek()

                if (!response.isSuccessful) {
                    throw Exception("Network error")
                }

                if (response.body()?.status != DTO.Stat.SUCCESS) {
                    throw Exception("Server error")
                }

                val movies = response.body()?.movies
                _movies.postValue(movies)
                Log.d("HomeViewModel", "Movies: $movies")
            } catch (e: Exception) {
                // todo
            }
        }

    }
}