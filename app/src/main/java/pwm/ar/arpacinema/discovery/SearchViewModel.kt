package pwm.ar.arpacinema.discovery

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.model.Movie
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.RetrofitClient
import java.util.Collections

class SearchViewModel : ViewModel() {

    private val _searchString = MutableLiveData("")
    val searchString: MutableLiveData<String> = _searchString


    private val _movies = MutableLiveData<List<Movie>?>(null)
    val movies: LiveData<List<Movie>?> = _movies

    private val _searchResults = MutableLiveData<List<Movie>?>(null)
    val searchResults: LiveData<List<Movie>?> = _searchResults

    private val scope = viewModelScope

    private val api = RetrofitClient.service

    init {
        fetchMovies()
    }

    fun searchMovies(query: String) {
        // filter by name of the movie
        if (query.isEmpty()) {
            _searchResults.postValue(_movies.value) // show all
        }

        val filteredMovies = movies.value?.filter { it.title.contains(query, ignoreCase = true) }
        _searchResults.postValue(filteredMovies)

    }

    private fun fetchMovies() {
       scope.launch(Dispatchers.IO) {
            try {
                val response = api.getMovies()

                if (!response.isSuccessful) {
                    throw Exception("Network error")
                }

                if (response.body()?.status != DTO.Stat.SUCCESS) {
                    throw Exception("Server error")
                }

                val moviesInTheResponse = response.body()?.movies

                // sorting dataset now
                val sorted = moviesInTheResponse?.sortedBy {
                    it.title
                }

                Log.d("SearchViewModel", "fetchMovies: $moviesInTheResponse")
                _movies.postValue(sorted) // unaltered dataset
                _searchResults.postValue(sorted) // altered dataset

            } catch (e: Exception) {
                // todo

            }
       }
    }

}