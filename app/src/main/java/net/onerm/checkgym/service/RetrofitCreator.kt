package net.onerm.checkgym.service

import net.onerm.checkgym.BuildConfig
import net.onerm.checkgym.const.CheckGymConsts
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitCreator {

    companion object {
        private fun defaultRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(CheckGymConsts.CHECK_GYM_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(createOkHttpClient())
                .build()
        }

        fun <T> create(service: Class<T>): T {
            return defaultRetrofit().create(service)
        }

        private fun createOkHttpClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                interceptor.level = HttpLoggingInterceptor.Level.BODY
            } else {
                interceptor.level = HttpLoggingInterceptor.Level.NONE
            }

            return OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .build()
        }

    }
}