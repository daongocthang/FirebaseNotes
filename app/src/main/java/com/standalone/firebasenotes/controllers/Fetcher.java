package com.standalone.firebasenotes.controllers;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.standalone.firebasenotes.models.BaseModel;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Map;

public class Fetcher<T extends BaseModel> {
    Class<T> classType;
    CollectionReference collectionReference;

    public Fetcher(CollectionReference cr) {
        this.collectionReference = cr;
        classType = getClassType();
    }

    public void fetch() {
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<T> data = new ArrayList<>();
                Class<T> classType = getClassType();

                for (DocumentSnapshot ds : task.getResult()) {
                    T t = make(ds);
                    if (t == null) continue;

                    t.setKey(ds.getId());
                    data.add(t);
                }

                complete(data);
            }
        });
    }

    public void complete(ArrayList<T> data) {
    }

    private T make(DocumentSnapshot snapshot) {
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

    @SuppressWarnings("unchecked")
    private Class<T> getClassType() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        assert type != null;
        return (Class<T>) type.getActualTypeArguments()[0];
    }
}
