package ru.vyakhirev.advancedandroid_otushomeworks

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class MovieResponse {

    @SerializedName(value = "page")
    @Expose
    var page: Int = 0

    @SerializedName(value = "results")
    @Expose
    var movies: List<Movie> = ArrayList()

    class Movie constructor(
        val title: String,
        val vote_average: Double,
        val poster_path: String,
        val release_date: String
    )
}

interface TmdbApi {
    @GET(value = "popular")
    suspend fun getPopular(
        @Query(
            value = "api_key"
        ) apiKey: String, @Query(
            value = "page"
        ) page: Int
    ): Response<MovieResponse>
}

class TmdbApiService {
    val BASE_URL = "https://api.themoviedb.org/3/movie/"

    private fun getServiceApi(retrofit: Retrofit) = retrofit.create(TmdbApi::class.java)

    fun getRetrofit() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    val apiService = getServiceApi(getRetrofit())
}
