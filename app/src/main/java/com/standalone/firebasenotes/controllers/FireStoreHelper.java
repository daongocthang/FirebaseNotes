package com.standalone.firebasenotes.controllers;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.standalone.firebasenotes.models.BaseModel;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class FireStoreHelper<T extends BaseModel> {
    final String TAG = this.getClass().getSimpleName();
    FirebaseFirestore db;
    FirebaseAuth auth;

    public FireStoreHelper() {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    public void create(T t) {
        String key = UUID.randomUUID().toString();
        reference().document(key).set(t.toMap());
    }

    public void remove() {

    }

    public void fetch(Class<T> classType, OnFetchCompleteListener<T> onFetchCompleteListener) {

        reference().get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<T> data = new ArrayList<>();

                for (DocumentSnapshot ds : task.getResult()) {
                    T t = make(ds, classType);
                    if (t == null) continue;

                    t.setKey(ds.getId());
                    data.add(t);
                }

                onFetchCompleteListener.onFetchComplete(data);
            }
        });
    }

    public CollectionReference reference() {
        return db.collection(Objects.requireNonNull(auth.getUid()));
    }

    private T make(DocumentSnapshot snapshot, Class<T> classType) {
        Map<String, Object> map = snapshot.getData();
        if (map == null) return null;

        try {
            T t = classType.newInstance();
            for (Field field : t.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                field.set(t, map.get(field.getName()));
            }

            return t;

        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    public interface OnFetchCompleteListener<T> {
        void onFetchComplete(ArrayList<T> data);
    }
}
