package com.example.composefoodrecipes.di

import android.content.Context
import com.example.composefoodrecipes.repository.FoodRepository
import com.example.composefoodrecipes.service.FoodAPI
import com.example.composefoodrecipes.service.FoodDao
import com.example.composefoodrecipes.service.FoodDatabase
import com.example.composefoodrecipes.util.Constants.BASE_URL
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


    @Singleton
    @Provides
    fun provideFoodRepository(
        api: FoodAPI, @ApplicationContext context: Context, foodDao: FoodDao
    ) = FoodRepository(api, context, foodDao)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): FoodDatabase {
        return FoodDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideFoodDao(database: FoodDatabase): FoodDao {
        return database.foodDao()
    }

    @Singleton
    @Provides
    fun provideFoodAPI(): FoodAPI {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).build().create(FoodAPI::class.java)
    }

}