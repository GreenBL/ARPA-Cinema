package pwm.ar.arpacinema.discovery.category

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pwm.ar.arpacinema.model.Movie
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.RetrofitClient

class CategoryViewModel : ViewModel() {

    // movie list
    private val _movies = MutableLiveData<List<Movie>?>(null)
    val movies: LiveData<List<Movie>?> = _movies

    val api = RetrofitClient.service

    suspend fun getMoviesByCategory(category: String) {

        // preparing the request
        val categoryPost = DTO.CategoryPost(category)
        Log.d("CategoryViewModel", "getMoviesByCategory: ${categoryPost.category}")
        try {
            val response = api.getMoviesByCategory(categoryPost)


            if (!response.isSuccessful) {
                Log.d("CategoryViewModel", "getMoviesByCategory: ${response.errorBody()}")
                throw Exception("Error")
            }

            if (response.body()?.status != DTO.Stat.SUCCESS) {
                Log.d("CategoryViewModel", "getMoviesByCategory: ${response.errorBody()}")
                throw Exception("Error")
            }

            if (response.body()?.movies != null) {
                Log.d("CategoryViewModel", "${response.body()?.movies}")
            }

            val movies = response.body()?.movies//s
            Log.d("CategoryViewModel", "LIST OF MOVIES: $movies")
            if (movies == null) {
                throw Exception("Error")
            }
            val newList = assertMovieCategory(movies, category)
            _movies.postValue(newList)

        } catch (e: Exception) {
            Log.e("CategoryViewModel", "getMoviesByCategory: $e", e)
        }
    }

    private fun assertMovieCategory(movies : List<Movie>, category: String) : List<Movie> {
        val mutableListOfMovies = movies.toMutableList()
        mutableListOfMovies.removeAll {
            category !in it.categories
        }
        return mutableListOfMovies
    }

}