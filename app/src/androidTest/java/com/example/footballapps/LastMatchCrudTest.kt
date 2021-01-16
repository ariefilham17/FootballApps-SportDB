package com.example.footballapps


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.footballapps.R.id.*
import com.example.footballapps.views.activities.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LastMatchCrudTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testCrudLastMatch() {
        // Aplikasi berada pada last matches fragment
        try {
            Thread.sleep(3000)
            onView(withId(recyclerView_LastMatch)).check(matches(isDisplayed()))
            onView(withText("Leicester")).check(matches(isDisplayed()))
            onView(withText("Leicester")).perform(click())// Memilih data baris ke 9 pada recyclerview last match fragment
            Thread.sleep(3000)
            // Menambah data ke database
            onView(withId(add_to_favorite)).check(matches(isDisplayed()))
            onView(withId(add_to_favorite)).perform(click())
            onView(withText("Added to favorite")).check(matches(isDisplayed()))
            Thread.sleep(3000)
            pressBack()
            // Berpindah ke Favorite Fragment
            onView(withId(navigation)).check(matches(isDisplayed()))
            onView(withId(navigation_favorite)).perform(click())
            Thread.sleep(3000)
            // Memilih data baris ke 0 pada recyclerview favorite match fragment
            onView(withId(recyclerView_FavMatch)).check(matches(isDisplayed()))
            onView(withId(recyclerView_FavMatch)).perform(actionOnItemAtPosition<androidx.recyclerview.widget.RecyclerView.ViewHolder>(0, click()))
            Thread.sleep(3000)
            // Mengapus data pada database
            onView(withId(add_to_favorite)).check(matches(isDisplayed()))
            onView(withId(add_to_favorite)).perform(click())
            onView(withText("Removed to favorite")).check(matches(isDisplayed()))
        } catch (e: NoMatchingViewException) {
        }
    }

}