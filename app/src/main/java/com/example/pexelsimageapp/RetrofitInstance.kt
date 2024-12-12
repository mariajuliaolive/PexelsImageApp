import okhttp3.OkHttpClient
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private const val BASE_URL = "https://api.pexels.com/v1/"

    // Interceptor para monitoramento
    private val monitoringInterceptor = Interceptor { chain ->
        val request = chain.request()
        val startTime = System.nanoTime() // Marca o início da requisição

        try {
            val response = chain.proceed(request)
            val endTime = System.nanoTime() // Marca o fim da requisição

            // Calcula o tempo de resposta
            val durationMs = (endTime - startTime) / 1_000_000
            println("Request URL: ${request.url}")
            println("Response Code: ${response.code}")
            println("Response Time: $durationMs ms")

            // Alertar sobre falhas ou tempo de resposta lento
            if (response.code !in 200..299) {
                println("ALERTA: Requisição falhou com código ${response.code}")
            }
            if (durationMs > 2000) { // Exemplo: tempo maior que 2 segundos
                println("ALERTA: Tempo de resposta alto ($durationMs ms)")
            }

            response // Retorna a resposta
        } catch (e: Exception) {
            println("Erro durante a requisição: ${e.message}")
            throw e // Propaga a exceção
        }
    }

    // Configuração do Retrofit com OkHttpClient
    private val client = OkHttpClient.Builder()
        .addInterceptor(monitoringInterceptor) // Adiciona o interceptor
        .connectTimeout(30, TimeUnit.SECONDS) // Tempo limite de conexão
        .readTimeout(30, TimeUnit.SECONDS) // Tempo limite de leitura
        .build()

    val api: PexelsApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client) // Configura o cliente
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PexelsApi::class.java)
    }
}
