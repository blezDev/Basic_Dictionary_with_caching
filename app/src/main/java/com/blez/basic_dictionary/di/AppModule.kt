package com.blez.basic_dictionary.di

import android.app.Application
import androidx.room.Room
import com.blez.basic_dictionary.feature_dictionary.data.local.Converters
import com.blez.basic_dictionary.feature_dictionary.data.local.WordInfoDao
import com.blez.basic_dictionary.feature_dictionary.data.local.WordInfoDatabase
import com.blez.basic_dictionary.feature_dictionary.data.remote.DictionaryApi
import com.blez.basic_dictionary.feature_dictionary.data.repository.WordInfoRepositoryImpl
import com.blez.basic_dictionary.feature_dictionary.data.util.GsonParser
import com.blez.basic_dictionary.feature_dictionary.domain.repository.WordInfoRepository
import com.blez.basic_dictionary.feature_dictionary.domain.use_case.GetWordInfoUseCase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDictionaryAPI(okHttpClient: OkHttpClient): DictionaryApi {
        return Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(DictionaryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES) // write timeout
            .readTimeout(1, TimeUnit.MINUTES) // read timeout
            .build()

    }

    @Provides
    @Singleton
    fun providesWordInfoDatabase(app : Application) : WordInfoDatabase{
        return Room.databaseBuilder(app,WordInfoDatabase::class.java,"word_db")
            .addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }




    @Provides
    @Singleton
    fun providesWordInfoRepository(api: DictionaryApi, db: WordInfoDatabase): WordInfoRepository {
        return WordInfoRepositoryImpl(api = api, dao = db.dao)
    }


    @Provides
    @Singleton
    fun providesGetWordInfoUseCase(repository: WordInfoRepository): GetWordInfoUseCase {
        return GetWordInfoUseCase(repository = repository)
    }


}