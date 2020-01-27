package i.numan.uitesting.data.source

import i.numan.uitesting.data.Movie


interface MoviesDataSource {

    fun getMovie(movieId: Int): Movie?

    fun getMovies(): List<Movie>
}