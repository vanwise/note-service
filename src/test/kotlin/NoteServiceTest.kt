import errors.CommentNotFoundException
import errors.NoteNotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class NoteServiceTest {
    @BeforeEach
    fun clearBeforeTest() {
        NoteService.clear()
    }

    @Test
    fun createCommentShouldReturnNewComment() {
        val note = NoteService.add("Note")
        val comment = NoteService.createComment(note.id, "Note")
        assertEquals(comment.text, "Note")
    }

    @Test
    fun getByIdShouldThrowNotFoundException() {
        assertThrows(NoteNotFoundException::class.java) {
            NoteService.getById(2)
        }
    }

    @Test
    fun getCommentsShouldReturnComments() {
        val note = NoteService.add("Note2")
        NoteService.createComment(note.id, "First")
        NoteService.createComment(note.id, "Second")
        assertEquals(NoteService.getComments(note.id).size, 2)
    }

    @Test
    fun editShouldThrowNotFoundException() {
        assertThrows(NoteNotFoundException::class.java) {
            NoteService.edit(Note(id = 1, text = "Note", comments = emptyArray()))
        }
    }

    @Test
    fun editCommentShouldCorrectEdit() {
        val note = NoteService.add("Note")
        val comment = NoteService.createComment(note.id, "First")
        val newComment = comment.copy(text = "New comment")
        assertEquals(NoteService.editComment(newComment), newComment)
    }

    @Test
    fun editCommentShouldThrowNotFoundException() {
        assertThrows(CommentNotFoundException::class.java) {
            val note = NoteService.add("Note")
            NoteService.editComment(Comment(id = 1, noteId = note.id, text = "First", date = 1))
        }
    }

    @Test
    fun deleteShouldCorrectDeleteNote() {
        val note = NoteService.add("Note")
        assertEquals(NoteService.delete(note.id), note)
        assertArrayEquals(NoteService.get(), emptyArray<Note>())
    }

    @Test
    fun deleteShouldThrowNoteNotFoundException() {
        assertThrows(NoteNotFoundException::class.java) {
            NoteService.delete(1)
        }
    }

    @Test
    fun deleteCommentShouldCorrectDeleted() {
        val note = NoteService.add("Note")
        val comment = NoteService.createComment(note.id, "First")
        assertEquals(NoteService.deleteComment(note.id, comment.id), comment)
    }

    @Test
    fun deleteCommentShouldThrowCommentNotFoundException() {
        assertThrows(CommentNotFoundException::class.java) {
            val note = NoteService.add("Note")
            NoteService.deleteComment(note.id, 1)
        }
    }

    @Test
    fun restoreCommentShouldCorrectRestoring() {
        val note = NoteService.add("Note")
        val comment = NoteService.createComment(note.id, "First")
        NoteService.deleteComment(note.id, comment.id)
        assertEquals(NoteService.restoreComment(note.id, comment.id), comment)
    }

    @Test
    fun restoreCommentThrowCommentNotFoundException() {
        assertThrows(CommentNotFoundException::class.java) {
            val note = NoteService.add("note")
            NoteService.restoreComment(note.id, 2)
        }
    }
}