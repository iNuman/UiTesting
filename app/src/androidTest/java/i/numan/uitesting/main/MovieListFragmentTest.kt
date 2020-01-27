package i.numan.uitesting.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import i.numan.uitesting.R
import i.numan.uitesting.adapter.MoviesListAdapter.MovieViewHolder
import i.numan.uitesting.data.FakeMovieData
import i.numan.uitesting.espressoIdling_resource.EspressoIdlingResourcesRule
import i.numan.uitesting.utils.EspressoIdlingResources
import org.hamcrest.Matchers.not
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4ClassRunner::class)
class MovieListFragmentTest {

    // let say we're testing movie number 4
    val LIST_ITEM_IN_TEST = 4
    val MOVIE_IN_TEST = FakeMovieData.movies[LIST_ITEM_IN_TEST]


    @get: Rule
    val activityScenario = ActivityScenarioRule(MainActivity::class.java)

    @get: Rule
    val espressoIdlingResource = EspressoIdlingResourcesRule()
    // instead of these we can use our custom test rules like above
//    @Before
//    fun registerIdlingResource() {
//        IdlingRegistry.getInstance().register(EspressoIdlingResources.countingIdlingResource)
//    }
//    @After
//    fun unregisterIdlingResource() {
//        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.countingIdlingResource)
//    }

    //    Recyclerview comes into view
    @Test
    fun a_test_isListFragmentVisible_onAppLaunch() {

        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())))
    }

    // Next Test is to Click the list item press back
    // And Navigate to DetailFragment
    // Correct Movie is In View
    @Test
    fun test_selectListitem_isDetailFragmentVisible() {
        onView(withId(R.id.recycler_view))
            .perform(actionOnItemAtPosition<MovieViewHolder>(LIST_ITEM_IN_TEST, click()))
        onView(withId(R.id.movie_title)).check(matches(withText(MOVIE_IN_TEST.title)))
    }

    // Next Test is to Click the list item
    // And Navigate to DetailFragment
    // press back

    @Test
    fun test_backNavigation_toMovieListFragment() {
        onView(withId(R.id.recycler_view))
            .perform(actionOnItemAtPosition<MovieViewHolder>(LIST_ITEM_IN_TEST, click()))
        onView(withId(R.id.movie_title)).check(matches(withText(MOVIE_IN_TEST.title)))

        pressBack()
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
    }

    // Next Test is to Click the list item
    // And Navigate to DetailFragment
    // Select Directors textView, navigate to Directors Fragment
    @Test
    fun test_navDirectorsFragment_validateDirectorsList() {

        onView(withId(R.id.recycler_view))
            .perform(actionOnItemAtPosition<MovieViewHolder>(LIST_ITEM_IN_TEST, click()))

        // Confirm nav to DetailFragment and display description
        onView(withId(R.id.movie_description))
            .check(matches(withText(MOVIE_IN_TEST.description)))

        // Nav to DirectorsFragment
        onView(withId(R.id.movie_directiors)).perform(click())

        // Confirm correct directors are visible
        onView(withId(R.id.directors_text))
            .check(matches(withText(
                DirectorsFragment.stringBuilderForDirectors(MOVIE_IN_TEST.directors!!)
            )))
    }

    // Next Test is to Click the list item
    // And Navigate to StarsFragment
    // Select Stars textView, navigate to Stars Fragment

    @Test
    fun test_navStarActorsFragment_validateStarActorsList() {
        onView(withId(R.id.recycler_view))
            .perform(actionOnItemAtPosition<MovieViewHolder>(LIST_ITEM_IN_TEST, click()))
        onView(withId(R.id.movie_title)).check(matches(withText(MOVIE_IN_TEST.title)))
        onView(withId(R.id.movie_star_actors)).perform(click())

        val actors = MOVIE_IN_TEST.star_actors
        val verifyActorsValue = StarActorsFragment.stringBuilderForStarActors(actors = actors!!)
        onView(withId(R.id.star_actors_text)).check(matches(withText(verifyActorsValue)))
    }
}