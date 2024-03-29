package com.ainul.academy.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ainul.academy.data.CourseEntity
import com.ainul.academy.data.source.AcademyRepository
import com.ainul.academy.utils.DataDummy

class BookmarkViewModel(private val academyRepository: AcademyRepository): ViewModel() {
    fun getBookmarks(): LiveData<List<CourseEntity>> = academyRepository.getBookmarkedCourses()
}