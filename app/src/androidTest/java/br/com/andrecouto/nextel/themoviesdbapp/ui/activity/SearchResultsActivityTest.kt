package br.com.andrecouto.nextel.themoviesdbapp.ui.activity

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.*
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import br.com.andrecouto.nextel.themoviesdbapp.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Matchers.allOf
import android.support.test.filters.LargeTest
import org.hamcrest.Matchers

@RunWith(AndroidJUnit4::class)
@LargeTest
class SearchResultsActivityTest {

    @get:Rule
    val searchActivityTestRule: ActivityTestRule<SearchResultsActivity> = ActivityTestRule(SearchResultsActivity::class.java)
    @get:Rule
    val mActivityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)


    @Test
    @Throws(Exception::class)
    fun searchActionInnerSearchResultsActivityTest() {
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
                allOf(withId(R.id.search_src_text),
                        childAtPosition(
                                Matchers.allOf(withId(R.id.search_plate),
                                        childAtPosition(
                                                withId(R.id.search_edit_frame),
                                                1)),
                                0),
                        ViewMatchers.isDisplayed()))
        searchAutoComplete.perform(replaceText("thor"), closeSoftKeyboard())
        val searchAutoComplete2 = onView(
                allOf(withId(R.id.search_src_text), withText("thor"),
                        childAtPosition(
                                allOf(withId(R.id.search_plate),
                                        childAtPosition(
                                                withId(R.id.search_edit_frame),
                                                1)),
                                0),
                        isDisplayed()))
        searchAutoComplete2.perform(pressImeActionButton())
        val searchAutoComplete3 = onView(
                allOf(withId(R.id.search_src_text),
                        childAtPosition(
                                allOf(withId(R.id.search_plate),
                                        childAtPosition(
                                                withId(R.id.search_edit_frame),
                                                1)),
                                0),
                        isDisplayed()))
        searchAutoComplete3.perform(replaceText("thor: r"), closeSoftKeyboard())
        val searchAutoComplete4 = onView(
                allOf(withId(R.id.search_src_text), withText("thor: r"),
                        childAtPosition(
                                allOf(withId(R.id.search_plate),
                                        childAtPosition(
                                                withId(R.id.search_edit_frame),
                                                1)),
                                0),
                        isDisplayed()))
        searchAutoComplete4.perform(pressImeActionButton())
        val textView = onView(
                allOf(withId(R.id.tResultSearch), withText("We found 01 result:"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.instanceOf<View>(android.widget.FrameLayout::class.java),
                                        0),
                                0),
                        isDisplayed()))
        textView.check(matches(withText("We found 01 result:")))
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