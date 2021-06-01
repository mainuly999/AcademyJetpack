package com.ainul.academy.ui.academy

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ainul.academy.data.CourseEntity
import com.ainul.academy.data.source.AcademyRepository
import com.ainul.academy.utils.DataDummy
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AcademyViewModelTest {
    private lateinit var viewModel: AcademyViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    //Anda perlu menambahkan InstantTaskExecutorRule karena pengujiannya berupa proses asynchronous.

    @Mock
    private lateinit var academyRepository: AcademyRepository

    @Mock
    private lateinit var observer: Observer<List<CourseEntity>>

    @Before
    fun setUp(){
        viewModel = AcademyViewModel(academyRepository)
    }

    @Test
    fun getCourses() {
        val dummyCourses = DataDummy.generateDummyCourses()
        val courses = MutableLiveData<List<CourseEntity>>()
        courses.value = dummyCourses

        `when`(academyRepository.getAllCourses()).thenReturn(courses)
        val courseEntities = viewModel.getCourses().value
        verify<AcademyRepository>(academyRepository).getAllCourses()
        assertNotNull(courseEntities)
        assertEquals(5, courseEntities?.size)

        viewModel.getCourses().observeForever(observer)
        //observeForever digunakan untuk meng-observe secara terus menerus
        verify(observer).onChanged(dummyCourses)
        //ketika sudah di-observe karena kode di atas, maka selanjutnya onChanged digunakan untuk memastikan terjadi perubahan data di LiveData
    }
}