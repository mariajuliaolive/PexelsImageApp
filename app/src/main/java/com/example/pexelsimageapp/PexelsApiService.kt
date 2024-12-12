import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PexelsApiService {
    @GET("v1/search")
    suspend fun searchImages(
        @Query("query") query: String,
        @Query("per_page") perPage: Int = 15
    ): Response<PexelsResponse>
}

