package i.numan.uitesting


import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

//  Declared the Activity globally
    @get: Rule
    val testRule_MainActivity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_isActivity_View() {

        onView(withId(R.id.main)).check(matches(isDisplayed()))
    }

    // Test for the visibility of the title and button
    @Test
    fun test_isActivity_title_nextBtn_Visible() {

        onView(withId(R.id.activity_main_title)).check(matches(isDisplayed()))
        onView(withId(R.id.button_next_activity)).check(matches(isDisplayed()))// method 1
        onView(withId(R.id.button_next_activity)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))// method 2
    }

    // Test for if the title is displayed or not
    @Test
    fun test_isTitleTextDisplayed() {

        onView(withId(R.id.activity_main_title)).check(matches(withText(R.string.text_mainactivity)))
    }

    // Test for onClick Activity
    @Test
    fun test_onClickMoveToSecondaryActivity() {

        // test button is Clicked
        onView(withId(R.id.button_next_activity)).perform(click())
        // test secondary activity is displayed or not
        onView(withId(R.id.secondary)).check(matches(isDisplayed()))
    }

    @Test
    fun test_onClickBackMoveToMainActivity() {

        // val activityTest = ActivityScenario.launch(MainActivity::class.java)
        // Make sure button next is clicked and secondary activity is displayed
        onView(withId(R.id.button_next_activity)).perform(click())
        onView(withId(R.id.secondary)).check(matches(isDisplayed()))

        // now there're two ways we apply back press one is back button we made
        // second is the back button available on mobile devices

//        onView(withId(R.id.button_back)).perform(click()) method 1
        pressBack() // method 2

        // finally check main activity is displayed or not in view
        onView(withId(R.id.main)).check(matches(isDisplayed()))
    }
}