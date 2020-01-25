package i.numan.uitesting.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.request.RequestOptions
import i.numan.uitesting.R
import i.numan.uitesting.data.source.MoviesDataSource
import i.numan.uitesting.data.source.MoviesRemoteDataSource
import i.numan.uitesting.factory.MovieFragmentFactory
import kotlinx.android.synthetic.main.activity_main.*


const val REQUEST_IMAGE_CAPTURE = 1234
const val KEY_IMAGE_DATA = "data" // retrieving data from intent

class MainActivity : AppCompatActivity(),UICommunicationListener {

    override fun loading(isLoading: Boolean) {
        if (isLoading)
            progress_bar.visibility = View.VISIBLE
        else
            progress_bar.visibility = View.INVISIBLE
    }
    // dependencies (typically would be injected with dagger)
    lateinit var requestOptions: RequestOptions
    lateinit var moviesDataSource: MoviesDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        initDependencies()
        supportFragmentManager.fragmentFactory = MovieFragmentFactory(
            requestOptions,
            moviesDataSource
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      init()

    }


    private fun init(){
        if(supportFragmentManager.fragments.size == 0){
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MovieListFragment::class.java, null)
                .commit()
        }
    }

    private fun initDependencies(){

        if(!::requestOptions.isInitialized){
            // glide
            requestOptions = RequestOptions
                .placeholderOf(R.drawable.default_image)
                .error(R.drawable.default_image)
        }
        if(!::moviesDataSource.isInitialized) {
            // Data Source
            moviesDataSource = MoviesRemoteDataSource()
        }
    }


}
