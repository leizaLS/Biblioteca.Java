public class Request {
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
