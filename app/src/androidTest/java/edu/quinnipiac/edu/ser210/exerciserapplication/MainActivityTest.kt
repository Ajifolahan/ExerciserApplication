//@Authors: Camryn Keller and Momoreoluwa Ayinde

package edu.quinnipiac.edu.ser210.exerciserapplication
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.hamcrest.CoreMatchers.anything
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        EspressoIdlingResource.increment()
        // 3 second network request
        val job = GlobalScope.launch {
            delay(3000)
        }
        job.invokeOnCompletion {
            // network call ended!
            EspressoIdlingResource.decrement()
        }
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    // will check to see if the splash screen will appear when application starts
    @Test
    fun test_isSplashScreen_onAppLaunch() {
        onView(withId(R.id.welcome)).check(matches(isDisplayed()))
    }

    //will click the start button on the splash screen
    @Test
    fun test_SplashScreen_toSearchFragment() {
        onView(withId(R.id.start_button)).perform(click())
    }

    // will click the second spinner, choose the 5th item and click the search button
    @Test
    fun test_Navigation_toResultFragment() {
        // call the splash screen to navigate to the search fragment test
        test_SplashScreen_toSearchFragment()

        onView(withId(R.id.spinner2)).perform(click())
        onData(anything()).atPosition(4).perform(click())
        // once entered, move to the result fragment
        onView(withId(R.id.search)).perform(click())
    }

    @Test
    fun test_Navigation_toDetailFragment() {
        // call the splash screen to the result fragment test
        test_Navigation_toResultFragment()

        //sleep to avoid animation issues
        Thread.sleep(1000)

        // Click on the second item in the RecyclerView
        onView(withId(R.id.recyclerview)).perform(
            actionOnItemAtPosition<RecyclerAdapter.MyViewHolder>(1, click())
        )
    }

    @Test
    fun test_Navigation_toFullDetailFragment() {
        // call the splash screen to the detailFragment test
        test_Navigation_toDetailFragment()

    }

}