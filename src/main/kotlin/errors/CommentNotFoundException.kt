package errors

class CommentNotFoundException(commentId: Int) : RuntimeException("Комментарий с id $commentId не найден")