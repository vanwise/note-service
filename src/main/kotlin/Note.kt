data class Note(
    val id: Int,
    val text: String,
    val comments: Array<Comment>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Note

        if (id != other.id) return false
        if (text != other.text) return false
        if (!comments.contentEquals(other.comments)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + text.hashCode()
        result = 31 * result + comments.contentHashCode()
        return result
    }
}
