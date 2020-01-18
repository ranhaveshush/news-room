package com.example.baseapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.baseapp.ui.fragment.ArticleListFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticleListFragmentTest {

    @Test
    fun launchFragmentAndVerifyUI() {
        // use launchInContainer to launch the fragment with UI
        val scenario = launchFragmentInContainer<ArticleListFragment>()
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.recyclerView_articles)).perform(click())
    }
}
