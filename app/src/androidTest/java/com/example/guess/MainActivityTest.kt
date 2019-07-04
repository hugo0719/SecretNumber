package com.example.guess

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.*
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.w3c.dom.Text


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)


    @Test
    fun guessWrong() {
        val secret = activityTestRule.activity.answer
        val resource = activityTestRule.activity.resources

        for (n in 1..10){
            if (n != secret) {
                onView(withId(R.id.ed_number)).perform(clearText())
                onView(withId(R.id.ed_number)).perform(typeText(n.toString()))
                onView(withId(R.id.button)).perform(click())

                val message = when{
                    n < secret -> R.string.bigger
                    else -> R.string.smaller
                }


                onView(withText(message)).check(matches(isDisplayed()))
                onView(withText(resource.getString(R.string.ok))).perform(click())
            }

        }

    }

    @Test
    fun barTest() {
        onView(withId(R.id.fab)).perform(click())
        onView(withText(R.string.yes)).perform(click())
        onView(withId(R.id.counter)).check(matches(withText("0")))

    }
}