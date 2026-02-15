import errors.CommentNotFoundException
import errors.NoteNotFoundException

object NoteService {
    private var notes = mutableListOf<Note>()
    private var deletedComments = mutableListOf<Comment>()

    fun add(text: String): Note {
        notes += Note(id = generateId(), text, comments = emptyList())
        return notes.last()
    }

    fun createComment(noteId: Int, text: String): Comment {
        val note = getById(noteId)
        val newComments = note.comments.toMutableList()
        val newComment = Comment(id = generateId(), noteId, text, date = System.currentTimeMillis())

        newComments += newComment
        edit(note.copy(comments = newComments))
        return newComment
    }

    fun get(): List<Note> {
        return notes
    }

    fun getById(noteId: Int): Note {
        for (note in notes) {
            if (note.id == noteId) {
                return note
            }
        }
        throw NoteNotFoundException(noteId)
    }

    fun getComments(noteId: Int): List<Comment> {
        return getById(noteId).comments
    }

    fun edit(newNote: Note): Note {
        var editedNote: Note? = null
        val newNotes = mutableListOf<Note>()

        for (note in notes) {
            if (note.id == newNote.id) {
                newNotes += newNote
                editedNote = newNotes.last()
            } else {
                newNotes += note
            }
        }

        if (editedNote == null) {
            throw NoteNotFoundException(newNote.id)
        } else {
            notes = newNotes
            return editedNote
        }
    }

    fun editComment(newComment: Comment): Comment {
        val note = getById(newComment.noteId)
        var editedComment: Comment? = null
        val newComments = mutableListOf<Comment>()

        for (comment in note.comments) {
            if (comment.id == newComment.id) {
                newComments += newComment
                editedComment = newComments.last()
            } else {
                newComments += comment
            }
        }

        if (editedComment == null) {
            throw CommentNotFoundException(newComment.id)
        } else {
            edit(note.copy(comments = newComments))
            return editedComment
        }
    }

    fun delete(noteId: Int): Note {
        var deletedNote: Note? = null
        val newNotes = mutableListOf<Note>()

        for (note in notes) {
            if (note.id == noteId) {
                deletedNote = note
            } else {
                newNotes += note
            }
        }

        if (deletedNote == null) {
            throw NoteNotFoundException(noteId)
        } else {
            notes = newNotes
            return deletedNote
        }
    }

    fun deleteComment(noteId: Int, commentId: Int): Comment {
        val note = getById(noteId)
        var deletedComment: Comment? = null
        val newComments = mutableListOf<Comment>()

        for (comment in note.comments) {
            if (comment.id == commentId) {
                deletedComment = comment
            } else {
                newComments += comment
            }
        }

        if (deletedComment == null) {
            throw CommentNotFoundException(commentId)
        } else {
            deletedComments += deletedComment
            edit(note.copy(comments = newComments))
            return deletedComment
        }
    }

    fun restoreComment(noteId: Int, commentId: Int): Comment {
        var restoredComment: Comment? = null
        val newDeletedComments = mutableListOf<Comment>()

        for (comment in deletedComments) {
            if (comment.id == commentId) {
                val note = getById(noteId)
                val newComments = note.comments.toMutableList()

                newComments += comment
                edit(note.copy(comments = newComments))
                restoredComment = comment
            } else {
                newDeletedComments += comment
            }
        }

        if (restoredComment == null) {
            throw CommentNotFoundException(commentId)
        } else {
            deletedComments = newDeletedComments
            return restoredComment
        }
    }

    fun clear() {
        notes = mutableListOf()
        deletedComments = mutableListOf()
    }

    private fun generateId(): Int {
        return Math.random().toInt()
    }
}