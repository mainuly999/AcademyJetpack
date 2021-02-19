package com.ainul.academy.data.source

import com.ainul.academy.data.CourseEntity
import com.ainul.academy.data.ModuleEntity

interface AcademyDataSource {
    fun getAllCourses(): List<CourseEntity>

    fun getBookmarkedCourses(): List<CourseEntity>

    fun getCoursesWithModules(courseId: String): CourseEntity

    fun getAllModulesByCourse(courseId: String): List<ModuleEntity>

    fun getContent(courseId: String, moduleId: String): ModuleEntity
}