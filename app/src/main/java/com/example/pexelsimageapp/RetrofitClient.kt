import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "https://api.pexels.com/"

    val pexelsApi: PexelsApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PexelsApi::class.java)
    }
}

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val start = System.currentTimeMillis()
        val request = chain.request()
        val response = chain.proceed(request)
        val end = System.currentTimeMillis()

        // Logando o tempo de resposta
        println("Request: ${request.url} | Time: ${end - start}ms | Status: ${response.code}")
        return response
    }
}

object Retrofitinstance {
    private val client = OkHttpClient.Builder()
        .addInterceptor(LoggingInterceptor())
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.pexels.com/v1/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: PexelsApi = retrofit.create(PexelsApi::class.java)
}
