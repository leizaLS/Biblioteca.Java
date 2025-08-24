import com.google.api.core.ApiFuture;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.util.concurrent.ExecutionException;

public class UserService {
    private static final String DATABASE_URL = "https://library-db-aed0e-default-rtdb.firebaseio.com/";

    public interface Callback {
        void onComplete(boolean success, String message);
    }

    public void checkUserExists(String username, Callback callback) {
        new Thread(() -> {
            try {
                String urlString = DATABASE_URL + "users/" + username + ".json";
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    String jsonResponse = response.toString();

                    if (jsonResponse.equals("null")) {
                        callback.onComplete(false, "Usuario no existe");
                    } else {
                        callback.onComplete(true, "Logueado correctamente");
                    }
                } else {
                    callback.onComplete(false, "Error al consultar usuario: Código " + responseCode);
                }
                conn.disconnect();
            } catch (Exception e) {
                callback.onComplete(false, "Error en checkUserExists: " + e.getMessage());
            }
        }).start();
    }

    public void addUser(String username, Callback callback) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users");
        ApiFuture<Void> future = dbRef.child(username).setValueAsync("dummyPassword");

        new Thread(() -> {
            try {
                future.get();
                callback.onComplete(true, "Usuario registrado correctamente");
            } catch (InterruptedException | ExecutionException e) {
                callback.onComplete(false, e.getMessage());
            }
        }).start();
    }
}
