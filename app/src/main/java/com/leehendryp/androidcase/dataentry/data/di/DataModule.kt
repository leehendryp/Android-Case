package com.leehendryp.androidcase.dataentry.data.di

import com.leehendryp.androidcase.core.apis.GeoApi
import com.leehendryp.androidcase.core.apis.TicTacApi
import com.leehendryp.androidcase.dataentry.data.RepositoryImpl
import com.leehendryp.androidcase.dataentry.data.local.LocalDataSource
import com.leehendryp.androidcase.dataentry.data.local.LocalDataSourceImpl
import com.leehendryp.androidcase.dataentry.data.remote.RemoteDataSource
import com.leehendryp.androidcase.dataentry.data.remote.RemoteDataSourceImpl
import com.leehendryp.androidcase.dataentry.domain.Repository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    fun provideGeoApiNetworkService(okHttpClient: OkHttpClient): GeoApi = Retrofit.Builder()
        .baseUrl(GeoApi.GEO_API_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GeoApi::class.java)

    @Singleton
    @Provides
    fun provideTicTacApiNetworkService(okHttpClient: OkHttpClient): TicTacApi = Retrofit.Builder()
        .baseUrl(TicTacApi.TIC_TAC_API_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TicTacApi::class.java)

    @Provides
    fun provideRemoteDataSource(
        geoApi: GeoApi,
        ticTacApi: TicTacApi
    ): RemoteDataSource = RemoteDataSourceImpl(geoApi, ticTacApi)

    @Provides
    fun provideLocalDataSource(): LocalDataSource = LocalDataSourceImpl()

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): Repository = RepositoryImpl(remoteDataSource, localDataSource)
}