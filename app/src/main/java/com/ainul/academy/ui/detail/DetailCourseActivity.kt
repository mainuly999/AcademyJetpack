package com.ainul.academy.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ainul.academy.R

class DetailCourseActivity : AppCompatActivity() {

    private lateinit var detailContentBinding: ContentDe

    companion object{
        const val EXTRA_COURSE = "extra_course"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_course)
        setSupportActionBar(findViewById(R.id.toolbar))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}