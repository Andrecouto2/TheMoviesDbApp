package br.com.andrecouto.nextel.themoviesdbapp.ui.activity

import android.support.test.espresso.Espresso
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.runner.RunWith
import br.com.andrecouto.nextel.themoviesdbapp.R
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.filters.LargeTest
import android.support.v7.widget.RecyclerView
import org.hamcrest.Matchers
import org.junit.Test

@RunWith(AndroidJUnit4::class)
@LargeTest
class DetailsMovieActivityTest {


    @get:Rule
    val mActivityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Throws(Exception::class)
    @Test
    fun detailsMovieActivity_OpenVideo() {
        Espresso.onView(ViewMatchers.withId(R.id.mainRecycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        val scrollView = Espresso.onView(
                Matchers.allOf(childAtPosition(
                        childAtPosition(
                                ViewMatchers.withId(android.R.id.content),
                                0),
                        1),
                        ViewMatchers.isDisplayed()))
        scrollView.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        val appCompatImageView = Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.imgVideo), isDisplayed()))
        appCompatImageView.check(ViewAssertions.doesNotExist())
    }

    fun childAtPosition(parentMatcher: Matcher<View>, position:Int): Matcher<View> {
        return object: TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position " + position + " in parent ")
                parentMatcher.describeTo(description)
            }
            override fun matchesSafely(view: View):Boolean {
                val parent = view.getParent()
                return (parent is ViewGroup && parentMatcher.matches(parent)
                        && view.equals((parent as ViewGroup).getChildAt(position)))
            }
        }
    }
}