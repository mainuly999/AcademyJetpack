package com.ainul.academy.ui.reader

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ainul.academy.data.ContentEntity
import com.ainul.academy.data.ModuleEntity
import com.ainul.academy.data.source.AcademyRepository
import com.ainul.academy.utils.DataDummy

class CourseReaderViewModel(private val academyRepository: AcademyRepository): ViewModel() {
    private lateinit var courseId: String
    private lateinit var moduleId: String

    fun setSelectedCourse(courseIdz: String){
        this.courseId = courseIdz
    }

    fun setSelectedModule(moduleIdz: String){
        this.moduleId = moduleIdz
    }

    fun getModules(): LiveData<List<ModuleEntity>> = academyRepository.getAllModulesByCourse(courseId)

    fun getSelectedModule(): LiveData<ModuleEntity> = academyRepository.getContent(courseId, moduleId)
}