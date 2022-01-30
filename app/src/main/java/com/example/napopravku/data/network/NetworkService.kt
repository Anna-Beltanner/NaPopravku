package com.example.napopravku.data.network

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.waitMillis
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class NetworkService {
    //настройка ретрофита

    companion object {

        val baseURl = "https://api.github.com"

        fun getRetrofitInstance(context: Context): Retrofit {

            //берем текущее соединение, если нет никакого соединения, оно null

            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetwork
            val isConnected = activeNetwork != null


//проверяем, есть ли интернет
            val headerInterceptor = Interceptor { chain ->
                if (isConnected == false) {
                    throw IOException("Нет интернет-соединения")

                }

                val builder = chain.request().newBuilder()
                chain.proceed(builder.build())

            }

            //логирование
            val logging = HttpLoggingInterceptor()
            logging.level = (HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
            client.addInterceptor(logging)
            client.addInterceptor(headerInterceptor)

            return Retrofit.Builder()
                .baseUrl(baseURl)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        }
    }

}