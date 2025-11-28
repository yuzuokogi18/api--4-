data class Notification(
    val id: Int? = null,
    val userId: Int,
    val requestId: Int?,
    val mensaje: String,
    val leida: Boolean = false
)
