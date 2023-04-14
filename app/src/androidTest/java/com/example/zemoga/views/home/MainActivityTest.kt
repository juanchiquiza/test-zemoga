package com.example.zemoga.views.home

import android.R
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.closeSoftKeyboard
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import android.support.test.espresso.ViewInteraction
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule

import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.closeSoftKeyboard
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
//import java.util.EnumSet.allOf


@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    var mActivityTestRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java)

    @Test
    @Throws(InterruptedException::class)
    fun emptyTest() {
        clickInputSearch()
        keypressInputSearch("empty")
        Thread.sleep(600)
        onView(allOf(withText("List is empty"))).check(matches(withText("List is empty")))
    }

    @Test
    @Throws(InterruptedException::class)
    fun ervinTest() {
        clickInputSearch()
        keypressInputSearch("Ervin")
        Thread.sleep(600)
        onView(allOf(withId(R.id.name))).check(matches(withText("Ervin Howell")))
        onView(allOf(withId(R.id.phone))).check(matches(withText("010-692-6593 x09125")))
        onView(allOf(withId(R.id.email))).check(matches(withText("Shanna@melissa.tv")))
    }

    @Test
    @Throws(InterruptedException::class)
    fun leanneTest() {
        clickInputSearch()
        keypressInputSearch("Leanne")
        Thread.sleep(600)
        onView(allOf(withId(R.id.name))).check(matches(withText("Leanne Graham")))
        onView(allOf(withId(R.id.phone))).check(matches(withText("1-770-736-8031 x56442")))
        onView(allOf(withId(R.id.email))).check(matches(withText("Sincere@april.biz")))
    }

    @Test
    @Throws(InterruptedException::class)
    fun leannePostClickVerPublicacionesTest() {
        clickInputSearch()
        keypressInputSearch("Leanne")
        Thread.sleep(600)
        onView(allOf(withId(R.id.btn_view_post))).perform(click())
        Thread.sleep(4000)
        onView(allOf(withId(R.id.name))).check(matches(withText(Matchers.endsWith("Leanne Graham"))))
        onView(allOf(withId(R.id.phone))).check(matches(withText("1-770-736-8031 x56442")))
        onView(
            allOf(
                withId(R.id.title),
                withText("sunt aut facere repellat provident occaecati excepturi optio reprehenderit")
            )
        ).check(matches(withText("sunt aut facere repellat provident occaecati excepturi optio reprehenderit")))
    }

    private fun keypressInputSearch(valueToWrite: String) {
        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.editTextSearch),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.textInputLayoutSearch),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText(valueToWrite), closeSoftKeyboard())
    }

    private fun clickInputSearch() {
        val appCompatEditText = onView(
            allOf(
                withId(R.id.editTextSearch),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.textInputLayoutSearch),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(click())
    }

    companion object {
        private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int
        ): Matcher<View> {
            return object : TypeSafeMatcher<View>() {
                override fun describeTo(description: Description) {
                    description.appendText("Child at position $position in parent ")
                    parentMatcher.describeTo(description)
                }

                public override fun matchesSafely(view: View): Boolean {
                    val parent = view.parent
                    return (parent is ViewGroup && parentMatcher.matches(parent)
                            && view == parent.getChildAt(position))
                }
            }
        }
    }
}