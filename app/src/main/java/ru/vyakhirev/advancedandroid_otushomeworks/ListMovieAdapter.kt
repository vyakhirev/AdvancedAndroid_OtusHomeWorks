package ru.vyakhirev.advancedandroid_otushomeworks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_item.view.*

class ListMoviesAdapter(private var movies: ArrayList<MovieResponse.Movie>) :
    RecyclerView.Adapter<ListMoviesAdapter.MoviesViewHolder>() {
    fun updateMovieList(newMovieList: List<MovieResponse.Movie>) {
        movies.clear()
        movies.addAll(newMovieList)
        notifyDataSetChanged()
    }

    class MoviesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.movie_item, parent, false)
        return MoviesViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.itemView.movie_title.text = movies[position].title
        holder.itemView.movie_rate.text = movies[position].vote_average.toString()
        holder.itemView.movie_posterPath.loadImage(IMAGE_BASE_URL + movies[position].poster_path)
        holder.itemView.movie_date.text = movies[position].release_date
    }

    companion object {
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w185"
    }

    private fun ImageView.loadImage(uri: String?) {
        val options = RequestOptions()
            .error(R.mipmap.ic_launcher_round)
        Glide.with(this.context)
            .setDefaultRequestOptions(options)
            .load(uri)
            .into(this)
    }
}