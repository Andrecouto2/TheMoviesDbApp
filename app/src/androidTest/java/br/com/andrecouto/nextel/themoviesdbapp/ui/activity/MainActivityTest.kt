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
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import br.com.andrecouto.nextel.themoviesdbapp.R
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.filters.LargeTest
import android.support.v7.widget.RecyclerView
import android.widget.FrameLayout
import org.hamcrest.Matchers

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val mActivityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    @Throws(Exception::class)
    fun progressBar_Displayed() {
        val progressBar = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.mainProgress),
                        childAtPosition(
                                childAtPosition(
                                        ViewMatchers.withId(android.R.id.content),
                                        0),
                                1),
                        ViewMatchers.isDisplayed()))
        progressBar.check(ViewAssertions.doesNotExist())
    }
    @Test
    @Throws(Exception::class)
    fun recyclerViewAndItem_Displayed() {
        val recyclerView = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.mainRecycler),
                        childAtPosition(
                                childAtPosition(
                                        ViewMatchers.withId(android.R.id.content),
                                        0),
                                0),
                        ViewMatchers.isDisplayed()))
        recyclerView.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        val linearLayout = Espresso.onView(
                Matchers.allOf(childAtPosition(
                        childAtPosition(
                                ViewMatchers.withId(R.id.mainRecycler),
                                0),
                        0),
                        ViewMatchers.isDisplayed()))
        linearLayout.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        val linearLayout2 = Espresso.onView(
                Matchers.allOf(childAtPosition(
                        childAtPosition(
                                ViewMatchers.withId(R.id.mainRecycler),
                                1),
                        0),
                        ViewMatchers.isDisplayed()))
        linearLayout2.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        val linearLayout3 = Espresso.onView(
                Matchers.allOf(childAtPosition(
                        childAtPosition(
                                ViewMatchers.withId(R.id.mainRecycler),
                                2),
                        0),
                        ViewMatchers.isDisplayed()))
        linearLayout3.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
    @Test
    @Throws(Exception::class)
    fun searchMoviesClickAndDisplayedDetailsMovieScreenTest() {
        val appCompatImageView = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.search_button), ViewMatchers.withContentDescription("Search"),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withId(R.id.search_bar),
                                        childAtPosition(
                                                ViewMatchers.withId(R.id.action_search),
                                                0)),
                                1),
                        ViewMatchers.isDisplayed()))
        appCompatImageView.perform(ViewActions.click())
        val searchAutoComplete = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.search_src_text),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withId(R.id.search_plate),
                                        childAtPosition(
                                                ViewMatchers.withId(R.id.search_edit_frame),
                                                1)),
                                0),
                        ViewMatchers.isDisplayed()))
        searchAutoComplete.perform(ViewActions.replaceText("thor"), ViewActions.closeSoftKeyboard())
        val searchAutoComplete2 = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.search_src_text), ViewMatchers.withText("thor"),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withId(R.id.search_plate),
                                        childAtPosition(
                                                ViewMatchers.withId(R.id.search_edit_frame),
                                                1)),
                                0),
                        ViewMatchers.isDisplayed()))
        searchAutoComplete2.perform(ViewActions.pressImeActionButton())
        val textView = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.tResultSearch), ViewMatchers.withText("We found 02 results:"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.instanceOf<View>(FrameLayout::class.java),
                                        0),
                                0),
                        ViewMatchers.isDisplayed()))
        textView.check(ViewAssertions.matches(ViewMatchers.withText("We found 02 results:")))
        val linearLayout = Espresso.onView(
                Matchers.allOf(childAtPosition(
                        childAtPosition(
                                ViewMatchers.withId(R.id.searchRecycler),
                                0),
                        0),
                        ViewMatchers.isDisplayed()))
        linearLayout.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        val linearLayout2 = Espresso.onView(
                Matchers.allOf(childAtPosition(
                        childAtPosition(
                                ViewMatchers.withId(R.id.searchRecycler),
                                1),
                        0),
                        ViewMatchers.isDisplayed()))
        linearLayout2.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    @Throws(Exception::class)
    fun itemMovieClickTest() {
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