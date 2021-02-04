package com.ainul.academy.ui.bookmark

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class BookmarkViewModelTest {
    private lateinit var viewModel: BookmarkViewModel

    @Before
    fun setUp() {
        viewModel = BookmarkViewModel()
    }

    @Test
    fun getBookmarks() {
        val courseEntity = viewModel.getBookmarks()
        assertNotNull(courseEntity)
        assertEquals(5, courseEntity.size)
    }
}