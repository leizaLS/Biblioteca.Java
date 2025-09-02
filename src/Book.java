public class Book {
    private final String isbn, title, author, genre, coverUrl;

    public Book(String isbn, String title, String author, String genre, String coverUrl) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.coverUrl = coverUrl;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public String getCoverUrl() { return coverUrl; }
}
