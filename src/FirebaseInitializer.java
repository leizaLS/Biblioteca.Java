//package com.mycompany.library.maven;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.InputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

public class FirebaseInitializer {

    private static FirebaseApp firebaseApp;

    public static FirebaseApp initialize() throws IOException {
        if (firebaseApp == null) {
            InputStream serviceAccount = FirebaseInitializer.class.getClassLoader()
                .getResourceAsStream("firebase-json/library-db-aed0e-firebase-adminsdk-fbsvc-08cde7a4c7.json");

            if (serviceAccount == null) {
                throw new FileNotFoundException("Archivo de credenciales Firebase no encontrado en recursos");
            }

            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://library-db-aed0e-default-rtdb.firebaseio.com/")  // Cambia esta URL
                .build();

            firebaseApp = FirebaseApp.initializeApp(options);
        }
        return firebaseApp;
    }
}
