import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RequestService {
    private static final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("requests");

    public interface Callback {
        void onComplete(boolean success, String message);
    }

    public void addRequest(String user, String isbn, Callback callback) {
        String id = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault()).format(new Date());
        String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());

        Request request = new Request(user, isbn, date);

        dbRef.child(id).setValue(request, (error, ref) -> {
            if (error == null) {
                callback.onComplete(true, "Préstamo solicitado correctamente!");
            } else {
                callback.onComplete(false, "Error al solicitar préstamo: " + error.getMessage());
            }
        });
    }

    public static class Request {
        public String user;
        public String isbn;
        public String date;

        public Request() {} // Constructor requerido por Firebase

        public Request(String user, String isbn, String date) {
            this.user = user;
            this.isbn = isbn;
            this.date = date;
        }
    }
}
