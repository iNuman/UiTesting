package i.numan.uitesting.main


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import i.numan.uitesting.R
import i.numan.uitesting.TopSpacingItemDecoration
import i.numan.uitesting.adapter.MoviesListAdapter
import i.numan.uitesting.data.FakeMovieData.FAKE_NETWORK_DELAY
import i.numan.uitesting.data.Movie
import i.numan.uitesting.data.source.MoviesDataSource
import i.numan.uitesting.utils.EspressoIdlingResources
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch
import java.lang.ClassCastException


// Constructor Injection
class MovieListFragment(
    val moviesDataSource: MoviesDataSource
) : Fragment(),
    MoviesListAdapter.Interaction {
    override fun onItemSelected(position: Int, item: Movie) {
        activity?.run {
            val bundle = Bundle()
            bundle.putInt("movie_id", item.id)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MovieDetailFragment::class.java, bundle)
                .addToBackStack("MovieDetailFragment")
                .commit()
        }
    }

    lateinit var listAdapter: MoviesListAdapter
    lateinit var uiCommunicationListener: UICommunicationListener
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_list, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initRecyclerView()
        getData()
    }

    private fun getData() {
        // TODO: https://developer.android.com/training/testing/espresso/idling-resource
        // as i had made the class singleton so i can directly call its methods like below
        EspressoIdlingResources.increment()
        uiCommunicationListener.loading(isLoading = true)
        val job = GlobalScope.launch(IO) {
            delay(FAKE_NETWORK_DELAY)
        }
        job.invokeOnCompletion {
            GlobalScope.launch(Main) {
                uiCommunicationListener.loading(isLoading = false)
                listAdapter.submitList(moviesDataSource.getMovies())
                EspressoIdlingResources.decrement()
            }
        }

    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            removeItemDecoration(TopSpacingItemDecoration(30))
            addItemDecoration(TopSpacingItemDecoration(30))
            listAdapter = MoviesListAdapter(this@MovieListFragment)
            adapter = listAdapter
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            uiCommunicationListener = context as UICommunicationListener
        } catch (e: ClassCastException) {
            Log.e("ffnet", "Must implement interface in $activity: ${e.message}")
        }
    }

}
