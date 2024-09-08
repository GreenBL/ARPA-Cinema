package pwm.ar.arpacinema.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.model.Movie
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.RetrofitClient


class HomeViewModel : ViewModel() {

    private val scope = viewModelScope
    // movie list bottom
    private val _movies = MutableLiveData<List<Movie>?>(null)
    val movies: LiveData<List<Movie>?> = _movies

    // retro
    private val service = RetrofitClient.service

    // init
    init {
        getMovies()
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