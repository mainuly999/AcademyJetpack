package com.ainul.academy.ui.academy

import androidx.lifecycle.ViewModel
import com.ainul.academy.data.CourseEntity
import com.ainul.academy.data.source.AcademyRepository
import com.ainul.academy.utils.DataDummy

class AcademyViewModel(private val academyRepository: AcademyRepository) : ViewModel() {
    fun getCourses(): List<CourseEntity> = academyRepository.getAllCourses()
}