package edu.quinnipiac.edu.ser210.exerciserapplication
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import edu.quinnipiac.edu.ser210.exerciserapplication.EspressoIdlingResource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
            // our network call ended!
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
        onView(withId(R.id.start_button)).check(matches(isDisplayed()))
    }

    @Test
    fun test_SplashScreen_toSearchFragment() {
        onView(withId(R.id.start_button)).perform(click())
    }

    // will type in tesla's stock symbol and navigate user to next screen
    @Test
    fun test_Navigation_toResultFragment() {
        // call the splash screen to search stock fragment test
        test_SplashScreen_toSearchFragment()

        onView(withId(R.id.spinner2)).perform(click())
        onData(withText("biceps")).perform(click())
        // once entered, move to the stock details fragment
        onView(withId(R.id.search)).perform(click())
    }

    @Test
    fun test_Navigation_toDetailFragment() {
        // call the splash screen to search stock fragment test
        test_Navigation_toResultFragment()

        // this might work, it goes too fast during testing
        onView(withId(R.id.recyclerview)).perform(swipeDown())
    //    onView(withId(R.id.recyclerview)).perform(click())
        onData(withText("Incline Hammer Curls")).perform(click())
    }

    @Test
    fun test_Navigation_toFullDetailFragment() {
        // call the splash screen to search stock fragment test
        test_Navigation_toDetailFragment()

    }

}