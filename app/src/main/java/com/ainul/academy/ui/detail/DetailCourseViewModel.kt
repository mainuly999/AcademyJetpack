package com.ainul.academy.ui.detail

import androidx.lifecycle.ViewModel
import com.ainul.academy.data.CourseEntity
import com.ainul.academy.data.ModuleEntity
import com.ainul.academy.utils.DataDummy

class DetailCourseViewModel: ViewModel() {
    private lateinit var courseId: String

    fun setSelectedCourse(courseIdz: String){
        this.courseId = courseIdz
    }

    fun getCourse(): CourseEntity{
        lateinit var course: CourseEntity
        val coursesEntities = DataDummy.generateDummyCourses()
        for(courseEntity in coursesEntities){
            if(courseEntity.courseId == courseId){
                course = courseEntity
            }
        }
        return course
    }
    fun getModules(): List<ModuleEntity> = DataDummy.generateDummyModules(courseId)
}