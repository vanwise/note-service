package errors

class NoteNotFoundException(noteId: Int) : RuntimeException("Заметка с id $noteId не найдена")