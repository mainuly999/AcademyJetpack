package com.ainul.academy.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ainul.academy.data.ContentEntity
import com.ainul.academy.data.CourseEntity
import com.ainul.academy.data.ModuleEntity
import com.ainul.academy.data.source.remote.RemoteDataSource
import com.ainul.academy.data.source.remote.response.ContentResponse
import com.ainul.academy.data.source.remote.response.CourseResponse
import com.ainul.academy.data.source.remote.response.ModuleResponse

class AcademyRepository private constructor(private val remoteDataSource: RemoteDataSource): AcademyDataSource {
    companion object{
        @Volatile
        private var instance: AcademyRepository? = null

        fun getInstance(remoteData: RemoteDataSource): AcademyRepository =
                instance ?: synchronized(this){
                    instance ?: AcademyRepository(remoteData)
                }
    }

    override fun getAllCourses(): LiveData<List<CourseEntity>> {
        //semua course
        val courseResult = MutableLiveData<List<CourseEntity>>()
        remoteDataSource.getAllCourses(object: RemoteDataSource.LoadCoursesCallback{
            override fun onAllCoursesReceived(coursesResponses: List<CourseResponse>) {
                val courseList = ArrayList<CourseEntity>()
                for(response in coursesResponses){
                    val course = CourseEntity(response.id,
                            response.title,
                            response.description,
                            response.date,
                            false,
                            response.imagePath)
                    courseList.add(course)
                }
                courseResult.postValue(courseList)
            }
        })
        return courseResult
    }

    override fun getBookmarkedCourses(): LiveData<List<CourseEntity>> {
        val courseResults = MutableLiveData<List<CourseEntity>>()
        remoteDataSource.getAllCourses(object : RemoteDataSource.LoadCoursesCallback {
            override fun onAllCoursesReceived(courseResponses: List<CourseResponse>) {
                val courseList = java.util.ArrayList<CourseEntity>()
                for (response in courseResponses) {
                    val course = CourseEntity(response.id,
                            response.title,
                            response.description,
                            response.date,
                            false,
                            response.imagePath)
                    courseList.add(course)
                }
                courseResults.postValue(courseList)
            }
        })
        return courseResults
    }

    override fun getCoursesWithModules(courseId: String): LiveData<CourseEntity> {
        //detail course
        val courseResult = MutableLiveData<CourseEntity>()

        remoteDataSource.getAllCourses(object: RemoteDataSource.LoadCoursesCallback{
            override fun onAllCoursesReceived(coursesResponses: List<CourseResponse>) {
                lateinit var course: CourseEntity
                for(response in coursesResponses){
                    if(response.id == courseId){
                        course = CourseEntity(response.id,
                                response.title,
                                response.description,
                                response.date,
                                false,
                                response.imagePath)
                    }
                }
                courseResult.postValue(course)
            }
        })
        return courseResult
    }

    override fun getAllModulesByCourse(courseId: String): LiveData<List<ModuleEntity>> {
        val moduleResults = MutableLiveData<List<ModuleEntity>>()

        remoteDataSource.getModules(courseId, object : RemoteDataSource.LoadModulesCallback {
            override fun onAllModulesReceived(moduleResponses: List<ModuleResponse>) {
                val moduleList = ArrayList<ModuleEntity>()
                for (response in moduleResponses) {
                    val course = ModuleEntity(response.moduleId,
                            response.courseId,
                            response.title,
                            response.position,
                            false)

                    moduleList.add(course)
                }
                moduleResults.postValue(moduleList)
            }
        })

        return moduleResults
    }


    override fun getContent(courseId: String, moduleId: String): LiveData<ModuleEntity> {
        //modul
        val moduleResult = MutableLiveData<ModuleEntity>()

        remoteDataSource.getModules(courseId, object: RemoteDataSource.LoadModulesCallback{
            override fun onAllModulesReceived(moduleResponses: List<ModuleResponse>) {
                lateinit var module: ModuleEntity
                for(response in moduleResponses){
                    if(response.moduleId == moduleId){
                        module = ModuleEntity(response.moduleId,
                                response.courseId,
                                response.title,
                                response.position,
                                false)
                        remoteDataSource.getContent(moduleId, object: RemoteDataSource.LoadContentCallback{
                            override fun onContentReceived(contentResponse: ContentResponse) {
                                module.content = ContentEntity(contentResponse.content)
                                moduleResult.postValue(module)
                            }
                        })
                        break
                    }
                }
            }
        })
        return moduleResult
    }
}