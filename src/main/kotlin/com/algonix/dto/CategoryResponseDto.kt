import java.time.LocalDateTime

data class CategoryResponseDto(
    val id: Long,
    val name: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
