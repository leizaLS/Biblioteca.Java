//package com.mycompany.library.maven;

import com.google.api.core.ApiFuture;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.ExecutionException;

public class UserService {

    public interface Callback {
        void onComplete(boolean success, String message);
    }

    public void addUser(String username, Callback callback) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("usuarios");

        // setValueAsync devuelve ApiFuture<Void>
        ApiFuture<Void> future = dbRef.child(username).setValueAsync("dummyPassword");

        new Thread(() -> {
            try {
                // Espera que la operación termine o falle
                future.get();
                callback.onComplete(true, "Usuario agregado correctamente");
            } catch (InterruptedException | ExecutionException e) {
                callback.onComplete(false, e.getMessage());
            }
        }).start();
    }
}
