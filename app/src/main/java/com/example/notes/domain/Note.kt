package com.example.notes.domain

data class Note(
    var id: Long = UNSPECIFIED_ID,
    var text: String? = null,
    var dateEnd: String? = null,
    var repeat: Repeat = Repeat.NONE,
    var isComplete: Boolean = false
) {
    companion object {
        const val UNSPECIFIED_ID = -1L
        const val NOTE_CHILD = "note"
    }
}