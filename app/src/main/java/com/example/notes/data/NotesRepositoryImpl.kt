package com.example.notes.data

import com.example.notes.domain.Note
import com.example.notes.domain.NotesRepository
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.snapshots
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesRepositoryImpl @Inject constructor() : NotesRepository {

    override fun addNote(noteItem: Note) : Boolean {
        val newItem = dataBaseReference.push()
        noteItem.id = newItem.key
        val result = newItem.setValue(noteItem)
        return result.isSuccessful
    }

    override fun deleteNote(noteItem: Note) : Boolean {
        val result = dataBaseReference.child(noteItem.id.toString()).removeValue()
        return result.isSuccessful
    }

    override fun editNote(noteItem: Note) : Boolean {
        val mapNote = mapOf(
            "id" to noteItem.id,
            "text" to noteItem.text,
            "dateEnd" to noteItem.dateEnd,
            "repeat" to noteItem.repeat,
            "isComplete" to noteItem.isComplete
        )
        val result = dataBaseReference.child(noteItem.id.toString()).updateChildren(mapNote)
        return result.isSuccessful
    }

    override fun getNote(noteId: Int): Note? {
        TODO("Not yet implemented")
    }

    override fun getFirebaseDbRef() = dataBaseReference

    companion object {
        private val firebaseDb = Firebase.database
        val dataBaseReference = firebaseDb.reference.child(Note.NOTE_CHILD)
    }
}