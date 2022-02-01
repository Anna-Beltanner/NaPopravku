package com.example.napopravku

import android.app.Application
import android.content.Context
import com.example.napopravku.data.network.Api
import com.example.napopravku.data.network.NetworkService

class App: Application() {
    //корневой класс, синглтон, срабатывает при запуске приложения 1 раз и создает 1 экземпляр; инициализация Retrofit

    val retrofitInstance by lazy {NetworkService.getRetrofitInstance(applicationContext).create(Api::class.java) }

    override fun onCreate() {
        super.onCreate()

    }


}