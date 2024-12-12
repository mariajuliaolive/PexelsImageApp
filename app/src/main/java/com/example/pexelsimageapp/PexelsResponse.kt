data class PexelsResponse(
    val photos: List<Photo>
)

data class Photo(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val photographer_url: String,
    val src: Src
)

data class Src(
    val original: String,
    val large: String,
    val medium: String,
    val small: String
)
