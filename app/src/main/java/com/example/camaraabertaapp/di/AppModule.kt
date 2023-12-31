package com.example.camaraabertaapp.di

import android.content.Context
import com.example.camaraabertaapp.data.preferences.IPreferences
import com.example.camaraabertaapp.data.preferences.Preferences
import com.example.camaraabertaapp.data.references.client.ReferencesClient
import com.example.camaraabertaapp.data.references.datasource.IReferencesRemoteDatasource
import com.example.camaraabertaapp.data.references.datasource.ReferencesRemoteDatasource
import com.example.camaraabertaapp.data.references.repository.IReferencesRepository
import com.example.camaraabertaapp.data.references.repository.ReferencesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// TODO Get the base url from local properties
const val BASE_URL = "https://dadosabertos.camara.leg.br/api/v2/"

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideReferencesClient(): ReferencesClient {
        // TODO Refactor to provide for every client
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ReferencesClient::class.java)
    }

}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideReferencesRemoteDatasource(
        datasource: ReferencesRemoteDatasource
    ): IReferencesRemoteDatasource

    @Binds
    abstract fun provideReferencesRepository(
        datasource: ReferencesRepository
    ): IReferencesRepository

}

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Singleton
    @Provides
    fun providePreferences(@ApplicationContext app: Context): IPreferences = Preferences(app)

}