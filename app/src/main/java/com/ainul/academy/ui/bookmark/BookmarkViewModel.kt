package com.ainul.academy.ui.bookmark

import androidx.lifecycle.ViewModel
import com.ainul.academy.data.CourseEntity
import com.ainul.academy.utils.DataDummy

class BookmarkViewModel: ViewModel() {
    fun getBookmarks(): List<CourseEntity> = DataDummy.generateDummyCourses()
}