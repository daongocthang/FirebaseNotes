package com.standalone.firebasenotes.controllers;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.standalone.firebasenotes.models.BaseModel;

import java.util.ArrayList;
import java.util.Objects;

public class FirebaseHelper<T extends BaseModel> {
    String path;
    DatabaseReference db;

    public FirebaseHelper(String path) {
        this.path = path;
        this.db = FirebaseDatabase.getInstance().getReference();
    }

    public void create(T t) {
        db.child(path).push().setValue(t);
    }

    public void update(T t) {
        db.child(path).child(t.getKey()).setValue(t);
    }

    public void remove(String key) {
        db.child(path).child(key).removeValue();
    }

    public ArrayList<T> fetchAll(@NonNull DataSnapshot snapshot, Class<T> tClass) {
        ArrayList<T> itemList = new ArrayList<>();
        for (DataSnapshot child : snapshot.getChildren()) {
            T t = child.getValue(tClass);
            Objects.requireNonNull(t).setKey(child.getKey());
            itemList.add(t);
        }

        return itemList;
    }

    public void addDataChangedListener(ValueEventListener listener) {
        db.child(path).addValueEventListener(listener);
    }
}
