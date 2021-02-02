package com.ainul.academy.ui.bookmark

import com.ainul.academy.data.CourseEntity

interface BookmarkFragmentCallback {
    fun onShareClick(course: CourseEntity)
}
