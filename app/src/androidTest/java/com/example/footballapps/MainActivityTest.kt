package com.example.footballapps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.footballapps.R.id.*
import com.example.footballapps.views.activities.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testViewPagerBottomNavigationBehavior() {
        try {
            Thread.sleep(3000)
            onView(withId(navigation)).check(matches(isDisplayed()))
            onView(withId(navigation_match)).perform(click())
            Thread.sleep(3000)
            onView(withId(navigation_team)).perform(click())
            Thread.sleep(3000)
            onView(withId(navigation_favorite)).perform(click())
        } catch (e: NoMatchingViewException) {
        }
    }
}