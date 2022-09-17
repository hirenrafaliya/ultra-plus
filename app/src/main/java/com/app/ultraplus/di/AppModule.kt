package com.app.ultraplus.di

import android.content.Context
import android.content.res.Resources
import com.app.ultraplus.App
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
/**
 * This provides single instance of [App], [Context] and [Resources]
 * It can be used anywhere using [javax.inject.Inject] annotation
 */
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): App = app as App


    @Singleton
    @Provides
    fun provideContext(application: App): Context = application.applicationContext


    @Singleton
    @Provides
    fun provideResources(application: App): Resources = application.resources
}