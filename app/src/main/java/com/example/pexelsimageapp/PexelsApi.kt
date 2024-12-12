import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PexelsApi {
    @GET("v1/search")
    fun searchImages(
        @Query("query") query: String,
        @Query("per_page") perPage: Int = 15, // Número de imagens por página
        @Query("page") page: Int = 1, // Número da página
        @Query("apikey") apiKey: String = "HWGhM0QGxmpmoNbLjA23qVtPE9KGbzHEBE7PQp1lHcYbjYa0KaG9k8dD" // Substitua pela sua chave de API
    ): Call<PexelsResponse>
}
