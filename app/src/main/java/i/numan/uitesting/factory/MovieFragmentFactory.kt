package i.numan.uitesting.factory

import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.request.RequestOptions
import i.numan.uitesting.main.DirectorsFragment
import i.numan.uitesting.main.MovieDetailFragment
import i.numan.uitesting.main.StarActorsFragment
import i.numan.uitesting.data.source.MoviesDataSource
import i.numan.uitesting.main.MovieListFragment

class MovieFragmentFactory(
    private val requestOptions: RequestOptions? = null,
    private val moviesDataSource: MoviesDataSource? = null
) : FragmentFactory() {

    private val TAG: String = "AppDebug"

    override fun instantiate(classLoader: ClassLoader, className: String) =

        when (className) {

            MovieListFragment::class.java.name -> {
                if (moviesDataSource != null) {
                    MovieListFragment(
                        moviesDataSource
                    )
                } else {
                    super.instantiate(classLoader, className)
                }
            }
            MovieDetailFragment::class.java.name -> {
                if (requestOptions != null
                    && moviesDataSource != null) {
                    MovieDetailFragment(
                        requestOptions,
                        moviesDataSource
                    )
                } else {
                    super.instantiate(classLoader, className)
                }
            }

            DirectorsFragment::class.java.name -> {
                DirectorsFragment()
            }

            StarActorsFragment::class.java.name -> {
                StarActorsFragment()
            }

            else -> {
                super.instantiate(classLoader, className)
            }
        }
}













