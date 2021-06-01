package com.ainul.academy.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.ainul.academy.R
import com.ainul.academy.utils.DataDummy
import com.ainul.academy.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class HomeActivityTest{
    private val dummyCourse = DataDummy.generateDummyCourses()

    //*di sini terdapat kode RecyclerViewActions yang digunakan untuk pengujian pada RecyclerView

    @Before
    fun setUp(){
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadCourses(){
        //cek apakah rv_acedemy dari package acedemy ditampilkan
        onView(withId(R.id.rv_academy)).check(matches(isDisplayed()))
        //sccrooll sampai pada psoisi tertentu(dummyCourse.size)
        onView(withId(R.id.rv_academy)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyCourse.size))
    }

    @Test
    fun loadDetailCourses(){
        //klik pada rcyclerView index 0
        onView(withId(R.id.rv_academy)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        //cek apakah title terlihat
        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
        //cek apakah titlenya sama dengan yang ada pada val dummyCourse
        onView(withId(R.id.text_title)).check(matches(withText(dummyCourse[0].title)))
        onView(withId(R.id.text_date)).check(matches(isDisplayed()))
        onView(withId(R.id.text_date)).check(matches(withText("Deadline ${dummyCourse[0].deadline}")))
    }

    @Test
    fun loadModule(){
        //cek apakah rv module dari course ditampilkan atau tidak
        onView(withId(R.id.rv_academy)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.btn_start)).perform(click())
        onView(withId(R.id.rv_module)).check(matches(isDisplayed()))
    }

    @Test
    fun loadDetailModule(){
        //klik module dari rv module dan cek apakah detail modulenya ditampilkkan atau tidak
        onView(withId(R.id.rv_academy)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.btn_start)).perform(click())
        onView(withId(R.id.rv_module)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.web_view)).check(matches(isDisplayed()))
    }

    @Test
    fun loadBookmarks(){
        //mengklik tabs dengan tulisan Bookmark
        onView(withText("Bookmark")).perform(click())
        onView(withId(R.id.rv_bookmark)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_bookmark)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyCourse.size))
    }
}