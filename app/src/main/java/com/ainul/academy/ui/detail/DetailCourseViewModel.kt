package com.ainul.academy.ui.detail

import androidx.lifecycle.ViewModel
import com.ainul.academy.data.CourseEntity
import com.ainul.academy.data.ModuleEntity
import com.ainul.academy.data.source.AcademyRepository
import com.ainul.academy.utils.DataDummy

class DetailCourseViewModel(private val academyRepository: AcademyRepository): ViewModel() {
    private lateinit var courseId: String

    fun setSelectedCourse(courseIdz: String){
        this.courseId = courseIdz
    }

    fun getCourse(): CourseEntity = academyRepository.getCoursesWithModules(courseId)
    fun getModules(): List<ModuleEntity> = academyRepository.getAllModulesByCourse(courseId)
}