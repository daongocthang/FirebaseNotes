package com.standalone.firebasenotes.controllers;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.standalone.firebasenotes.models.BaseModel;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;

public class FireStoreHelper<T extends BaseModel> {
    final String TAG = this.getClass().getSimpleName();
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
        collection().document(key).set(t.toMap());
    }

    public void remove() {

    }

    public void fetch() {
        collection().get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (DocumentSnapshot ds : task.getResult()) {

                }
            }
        });
    }

    public CollectionReference collection() {
        return db.collection("FirebaseTuts").document(user.getUid()).collection(path);
    }

    private T getValue(DocumentSnapshot snapshot, Class<T> valueType) {
        Map<String, Object> map = snapshot.getData();
        assert map != null;

        try {
            T t=valueType.newInstance();
            for(Field field:t.getClass().getDeclaredFields()){
                field.set(t,map.get(field.getName()));
            }

        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
