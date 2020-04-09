package ru.vyakhirev.advancedandroid_otushomeworks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ListViewModel
    private val movieListAdapter = ListMoviesAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listMoviesRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieListAdapter
        }

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.movies.observe(this, Observer { movies ->
            movieListAdapter.updateMovieList(movies.body()!!.movies)
        })
    }
}


class ListViewModel : ViewModel() {

    val movies = MutableLiveData<Response<MovieResponse>>()

    private val movieService = TmdbApiService().apiService
    var job: Job? = null

    fun refresh() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = movieService.getPopular(BuildConfig.TMDB_API_KEY, 1)
            withContext(Dispatchers.Main) {
                movies.value = response
            }
        }
    }
}