package com.standalone.firebasenotes.controllers;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.standalone.firebasenotes.models.BaseModel;

import java.util.UUID;

public class FireStoreHelper<T extends BaseModel> {
    FirebaseFirestore db;
    FirebaseUser user;

    final String path;

    public FireStoreHelper(String path) {
        this.path = path;

        db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }

    public void create(T t) {
        String key = UUID.randomUUID().toString();
        root().document(key).set(t.toMap());
    }

    public void remove() {

    }



    public CollectionReference root() {
        return db.collection("FirebaseTuts").document(user.getUid()).collection(path);
    }

}
