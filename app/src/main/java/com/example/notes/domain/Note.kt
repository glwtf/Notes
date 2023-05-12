package com.example.notes.domain

data class Note(
    var id: String? = null,
    var text: String? = null,
    var dateEnd: String? = null,
    var timeEnd: String? = null,
    var repeat: Repeat = Repeat.NONE,
    var isComplete: Boolean = false
) {
    companion object {
        const val NOTE_CHILD = "note"
    }
}