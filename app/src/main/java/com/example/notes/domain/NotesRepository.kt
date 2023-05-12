package com.example.notes.domain

import com.google.firebase.database.DatabaseReference

interface NotesRepository {

    fun addNote(noteItem: Note) : Boolean
    fun deleteNote(noteItem: Note) : Boolean
    fun editNote(noteItem: Note) : Boolean
    fun getNote(noteId: Int) : Note?

    fun getFirebaseDbRef() : DatabaseReference

}