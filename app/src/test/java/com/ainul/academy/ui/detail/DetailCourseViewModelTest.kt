package com.ainul.academy.ui.detail

import com.ainul.academy.data.ModuleEntity
import com.ainul.academy.data.source.AcademyRepository
import com.ainul.academy.utils.DataDummy
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailCourseViewModelTest {
    private lateinit var viewModel: DetailCourseViewModel
    private val dummyCourse = DataDummy.generateDummyCourses()[0]
    private val courseId = dummyCourse.courseId

    @Mock
    private lateinit var academyRepository: AcademyRepository

    @Before
    fun setUp() {
        viewModel = DetailCourseViewModel(academyRepository)
        viewModel.setSelectedCourse(courseId)
    }

    @Test
    fun getCourse() {
        `when`(academyRepository.getCoursesWithModules(courseId)).thenReturn(dummyCourse)
        //test saat di klik pada list course, apakah id yang dikirim dari academy adapter itu ada di dalam data
        viewModel.setSelectedCourse(dummyCourse.courseId)
        val courseEntity = viewModel.getCourse()
        verify(academyRepository).getCoursesWithModules(courseId)
        assertNotNull(courseEntity)
        assertEquals(dummyCourse.courseId, courseEntity.courseId)
        assertEquals(dummyCourse.deadline, courseEntity.deadline)
        assertEquals(dummyCourse.description, courseEntity.description)
        assertEquals(dummyCourse.imagePath, courseEntity.imagePath)
        assertEquals(dummyCourse.title, courseEntity.title)
    }

    @Test
    fun getModules() {
        `when`<ArrayList<ModuleEntity>>(academyRepository.getAllModulesByCourse(courseId)).thenReturn(DataDummy.generateDummyModules(courseId) as ArrayList<ModuleEntity>)
        val moduleEntities = viewModel.getModules()
        verify<AcademyRepository>(academyRepository).getAllModulesByCourse(courseId)
        assertNotNull(moduleEntities)
        assertEquals(7, moduleEntities.size.toLong())
    }
}