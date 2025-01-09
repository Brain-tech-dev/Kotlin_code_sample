package com.adambulette.di

import com.adambulette.network.apiService.ApiService
import com.adambulette.utils.BaseURL
import com.adambulette.utils.Loading
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

const val baseURL= BaseURL

fun provideGson():Gson {

    return GsonBuilder().setLenient().create()
}

fun provideOkHTTPClient(): OkHttpClient {
    val loggingInterceptorp = HttpLoggingInterceptor()
    loggingInterceptorp.setLevel(HttpLoggingInterceptor.Level.HEADERS)
    loggingInterceptorp.setLevel(HttpLoggingInterceptor.Level.BODY)

    val requestInterceptor = Interceptor {
        val url = it.request()
            .url
            .newBuilder()
            .build()

        val request = it.request()
            .newBuilder()
            .url(url)
            .build()

        return@Interceptor it.proceed(request)
    }

    val trustAllCerts = arrayOf<TrustManager>(
        object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        }
    )

    val sslContext = SSLContext.getInstance("SSL").apply {
        init(null, trustAllCerts, java.security.SecureRandom())
    }

    val trustManager = trustAllCerts[0] as X509TrustManager

    return OkHttpClient
        .Builder()
        .sslSocketFactory(sslContext.socketFactory, trustManager) // Add insecure SSL factory
        .hostnameVerifier { _, _ -> true } // Trust all hostnames
        .addInterceptor(loggingInterceptorp)
        .addNetworkInterceptor(requestInterceptor)
        .build()
}

fun provideRetrofit( baseURL :String,gson: Gson,client: OkHttpClient): ApiService {
    return Retrofit.Builder()
        .baseUrl(baseURL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(ApiService::class.java)
}

val ApiModule= module {
    single { baseURL }
    single { provideGson() }
    single { provideOkHTTPClient() }
    single { provideRetrofit(get(),get(),get()) }
    single { Loading() }
}