package com.ainul.academy.di

import android.content.Context
import com.ainul.academy.data.source.AcademyRepository
import com.ainul.academy.data.source.remote.RemoteDataSource
import com.ainul.academy.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): AcademyRepository{
        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))

        return AcademyRepository.getInstance(remoteDataSource)
    }
}