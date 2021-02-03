package com.ainul.academy.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ainul.academy.R
import com.ainul.academy.data.CourseEntity
import com.ainul.academy.databinding.ActivityDetailCourseBinding
import com.ainul.academy.databinding.ContentDetailCourseBinding
import com.ainul.academy.ui.reader.CourseReaderActivity
import com.ainul.academy.utils.DataDummy
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class DetailCourseActivity : AppCompatActivity() {

    private lateinit var detailContentBinding: ContentDetailCourseBinding

    companion object{
        const val EXTRA_COURSE = "extra_course"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityDetailCourseBinding = ActivityDetailCourseBinding.inflate(layoutInflater)
        detailContentBinding = activityDetailCourseBinding.detailContent

        setContentView(activityDetailCourseBinding.root)

        setSupportActionBar(activityDetailCourseBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adapter = DetailCourseAdapter()
        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailCourseViewModel::class.java]

        val extras = intent.extras
        if(extras != null){
            val courseId = extras.getString(EXTRA_COURSE)
            if(courseId != null){
                //Dengan bantuan kelas ViewModel, courseId akan dipertahankan sampai Activity masuk ke state onDestroy.
                viewModel.setSelectedCourse(courseId)
                val modules = viewModel.getModules()
                adapter.setModules(modules)
                populateCourse(viewModel.getCourse() as CourseEntity)
            }
        }

        with(detailContentBinding.rvModule){
            //supaya recyle viewnya tidak bisa discroll
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(this@DetailCourseActivity)
            setHasFixedSize(true)
            this.adapter = adapter
            val dividerItemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }
    }

    private fun populateCourse(course: CourseEntity) {
        detailContentBinding.textTitle.text = course.title
        detailContentBinding.textDescription.text = course.description
        detailContentBinding.textDate.text = resources.getString(R.string.deadline_date, course.deadline)

        Glide.with(this)
                .load(course.imagePath)
                .transform(RoundedCorners(20))
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .into(detailContentBinding.imagePoster)

        detailContentBinding.btnStart.setOnClickListener {
            var intent = Intent(this@DetailCourseActivity, CourseReaderActivity::class.java)
            intent.putExtra(CourseReaderActivity.EXTRA_COURSE_ID, course.courseId)
            startActivity(intent)
        }
    }
}