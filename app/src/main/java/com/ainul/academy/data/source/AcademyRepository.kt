package com.ainul.academy.data.source

import com.ainul.academy.data.ContentEntity
import com.ainul.academy.data.CourseEntity
import com.ainul.academy.data.ModuleEntity
import com.ainul.academy.data.source.remote.RemoteDataSource

class AcademyRepository private constructor(private val remoteDataSource: RemoteDataSource): AcademyDataSource {
    companion object{
        @Volatile
        private var instance: AcademyRepository? = null

        fun getInstance(remoteData: RemoteDataSource): AcademyRepository =
                instance ?: synchronized(this){
                    instance ?: AcademyRepository(remoteData)
                }
    }

    override fun getAllCourses(): ArrayList<CourseEntity> {
        //semua course
        val courseResponses = remoteDataSource.getAllCourses()
        val courseList = ArrayList<CourseEntity>()
        for(response in courseResponses){
            val course = CourseEntity(
                    response.id,
                    response.title,
                    response.description,
                    response.date,
                    false,
                    response.imagePath)
            courseList.add(course)
        }
        return courseList
    }

    override fun getBookmarkedCourses(): ArrayList<CourseEntity> {
        val courseResponses = remoteDataSource.getAllCourses()
        val courseList = ArrayList<CourseEntity>()
        for(response in courseResponses){
            val course = CourseEntity(response.id,response.title,response.description, response.date, false, response.imagePath)
            courseList.add(course)
        }
        return courseList
    }

    override fun getCoursesWithModules(courseId: String): CourseEntity {
        //detail course
        val courseResponse = remoteDataSource.getAllCourses()
        lateinit var course: CourseEntity
        for(response in courseResponse){
            if(response.id == courseId){
                course = CourseEntity(response.id,response.title,response.description,response.date,false,response.imagePath)
            }
        }
        return course
    }

    override fun getAllModulesByCourse(courseId: String): ArrayList<ModuleEntity> {
        //semua modul dalam course
        val moduleResponses = remoteDataSource.getModules(courseId)
        val moduleList = ArrayList<ModuleEntity>()
        for(response in moduleResponses){
            val course = ModuleEntity(response.moduleId, response.courseId,response.title,response.position, false)
            moduleList.add(course)
        }
        return moduleList
    }

    override fun getContent(courseId: String, moduleId: String): ModuleEntity {
        //modul
        val moduleResponses = remoteDataSource.getModules(courseId)
        lateinit var module: ModuleEntity
        for(response in moduleResponses){
            if(response.moduleId == moduleId){
                module = ModuleEntity(response.moduleId,response.courseId,response.title,response.position,false)
                module.content = ContentEntity(remoteDataSource.getContent(moduleId).content)
                break
            }
        }
        return module
    }
}