package com.onefinance.customerapp.di

import android.content.Context
import android.content.SharedPreferences
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.onefinance.customerapp.BuildConfig
import com.onefinance.customerapp.core.TOKEN
import com.onefinance.customerapp.core.presentation.extensions.getEncryptedSharedPreference
import com.onefinance.customerapp.data.AppRepository
import com.onefinance.customerapp.data.AppRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSharedPref(@ApplicationContext app: Context): SharedPreferences {
        return app.getEncryptedSharedPreference()
    }

    @Provides
    @Singleton
    fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        val chuckerCollector =
            ChuckerCollector(context = context, retentionPeriod = RetentionManager.Period.ONE_HOUR)
        return ChuckerInterceptor.Builder(context)
            .collector(chuckerCollector)
            .maxContentLength(250_000L)
            .alwaysReadResponseBody(true)
            .build()
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        sharedPreferences: SharedPreferences,
        @ApplicationContext context: Context,
        chuckerInterceptor: ChuckerInterceptor,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG)
            builder.addInterceptor(OkHttpProfilerInterceptor())
//        trustAllBypassCertificationLogic(builder)
        return builder
            .addInterceptor {
                val token = sharedPreferences.getString(TOKEN, "")
                val modifiedRequest = it.request().newBuilder()
                    .addHeader("Authorization", if (!token.isNullOrEmpty()) "Bearer $token" else "")
                    .addHeader(
                        "lang",
                        Locale.getDefault().language
                    )
                    .build()
                it.proceed(modifiedRequest)
            }
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .addInterceptor(chuckerInterceptor)
            .build()
    }

    @Provides
    fun provideAppRepository(sharedPreferences: SharedPreferences): AppRepository {
        return AppRepositoryImpl(sharedPreferences)
    }
}