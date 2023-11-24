package nz.co.test.transactions.di.network

import nz.co.test.transactions.services.TransactionsService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api : TransactionsService by lazy {
        Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TransactionsService::class.java)
    }
}