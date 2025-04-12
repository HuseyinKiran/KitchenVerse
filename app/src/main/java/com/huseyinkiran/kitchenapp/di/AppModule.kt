package com.huseyinkiran.kitchenapp.di

import android.content.Context
import androidx.room.Room
import com.huseyinkiran.kitchenapp.data.local.MealDao
import com.huseyinkiran.kitchenapp.data.local.MealDatabase
import com.huseyinkiran.kitchenapp.data.remote.api.MealsAPI
import com.huseyinkiran.kitchenapp.data.repository.MealsLocalRepositoryImpl
import com.huseyinkiran.kitchenapp.data.repository.MealsRemoteRepositoryImpl
import com.huseyinkiran.kitchenapp.data.repository.MealsRepositoryImpl
import com.huseyinkiran.kitchenapp.domain.repository.MealsLocalRepository
import com.huseyinkiran.kitchenapp.domain.repository.MealsRemoteRepository
import com.huseyinkiran.kitchenapp.domain.repository.MealsRepository
import com.huseyinkiran.kitchenapp.common.ApiConstants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @[Singleton Provides]
    fun provideRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, MealDatabase::class.java, "MealsDB")
            .build()

    @[Singleton Provides]
    fun provideMealsAPI(): MealsAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealsAPI::class.java)
    }

    @[Singleton Provides]
    fun provideDao(database: MealDatabase) = database.mealDao()

    @[Singleton Provides]
    fun provideLocalRepository(dao: MealDao): MealsLocalRepository = MealsLocalRepositoryImpl(dao)

    @[Singleton Provides]
    fun provideRemoteRepository(api: MealsAPI): MealsRemoteRepository =
        MealsRemoteRepositoryImpl(api)

    @[Singleton Provides]
    fun provideMealsRepository(
        localRepository: MealsLocalRepository,
        remoteRepository: MealsRemoteRepository
    ): MealsRepository = MealsRepositoryImpl(localRepository, remoteRepository)

}