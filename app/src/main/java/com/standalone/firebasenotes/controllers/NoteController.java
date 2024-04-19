package com.standalone.firebasenotes.controllers;

import com.google.firebase.database.DatabaseReference;
import com.standalone.firebasenotes.models.Note;

public class NoteController {
    final static String PATH = "notes";
    DatabaseReference db;

    public NoteController(DatabaseReference db) {
        this.db = db;
    }

    public DatabaseReference createNote(Note note) {
        db.child(PATH).push().setValue(note);
        return db;
    }

    public DatabaseReference updateNote(Note note) {
        db.child(PATH).child(note.getId()).setValue(note);
        return db;
    }
}
