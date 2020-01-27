package i.numan.uitesting.util

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResources {

    const val TASKS = "GLOBAL"
    @JvmField val countingIdlingResource = CountingIdlingResource(TASKS)

    fun increment() {
        countingIdlingResource.increment()
    }

    // if the task is not zero decrement it
    fun decrement() {
        if(!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }

    }


}