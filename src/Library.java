import com.google.firebase.database.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Library extends JFrame {
    private final String user;
    private JPanel booksPanel;

    public Library(String user) {
        this.user = user;
        initUI();
        loadBooks();
    }

    private void initUI() {     
        setSize(850, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Bienvenido: " + user + ". Selecciona un libro...", SwingConstants.CENTER);
        welcomeLabel.setOpaque(true);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBackground(Color.BLACK);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setPreferredSize(new Dimension(800, 40));
        add(welcomeLabel, BorderLayout.NORTH);

        booksPanel = new JPanel();
        booksPanel.setLayout(new BoxLayout(booksPanel, BoxLayout.Y_AXIS));
        booksPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(booksPanel);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void loadBooks() {
        DatabaseReference booksRef = FirebaseDatabase.getInstance().getReference("books");

        booksRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                SwingUtilities.invokeLater(() -> {
                    if (!snapshot.exists()) {
                        booksPanel.add(new JLabel("No hay libros disponibles."));
                    } else {
                        for (DataSnapshot bookSnap : snapshot.getChildren()) {
                            Book book = parseBook(bookSnap);
                            if (book != null) {
                                JPanel bookPanel = createBookPanel(book);
                                booksPanel.add(bookPanel);
                                booksPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                            }
                        }
                    }
                    booksPanel.revalidate();
                    booksPanel.repaint();
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
                SwingUtilities.invokeLater(() -> {
                    booksPanel.add(new JLabel("Error al cargar libros: " + error.getMessage()));
                    booksPanel.revalidate();
                    booksPanel.repaint();
                });
            }
        });
    }

    private Book parseBook(DataSnapshot snap) {
        String isbn = snap.child("isbn").getValue(String.class);
        String title = snap.child("title").getValue(String.class);
        String author = snap.child("author").getValue(String.class);
        String genre = snap.child("genre").getValue(String.class);
        String coverUrl = snap.child("coverUrl").getValue(String.class);

        if (isbn != null && title != null && author != null && genre != null && coverUrl != null) {
            return new Book(isbn, title, author, genre, coverUrl);
        }
        return null;
    }

    private JPanel createBookPanel(Book book) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(750, 160));
        panel.setMaximumSize(new Dimension(750, 160));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        panel.add(createCoverPanel(book), BorderLayout.WEST);
        panel.add(createInfoPanel(book), BorderLayout.CENTER);
        panel.add(createButtonPanel(book), BorderLayout.EAST);

        return panel;
    }

    private JPanel createCoverPanel(Book book) {
        JPanel coverPanel = new JPanel(new BorderLayout());
        coverPanel.setPreferredSize(new Dimension(120, 160));

        ImageIcon icon;
        try {
            Image img = new ImageIcon(new URL(book.getCoverUrl())).getImage().getScaledInstance(120, 160, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);
        } catch (Exception e) {
            icon = new ImageIcon(new BufferedImage(120, 160, BufferedImage.TYPE_INT_ARGB));
        }

        JLabel label = new JLabel(icon);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        coverPanel.add(label, BorderLayout.CENTER);

        return coverPanel;
    }

    private JPanel createInfoPanel(Book book) {
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBorder(new EmptyBorder(10, 10, 10, 10));

        info.add(createText(book.getTitle(), Font.BOLD, 18));
        info.add(createText("Autor: " + book.getAuthor(), Font.PLAIN, 14));
        info.add(createText("ISBN: " + book.getIsbn(), Font.PLAIN, 14));
        info.add(createText("Género: " + book.getGenre(), Font.ITALIC, 14));

        return info;
    }

    private JTextArea createText(String text, int style, int size) {
        JTextArea area = new JTextArea(text);
        area.setFont(new Font("Arial", style, size));
        area.setEditable(false);
        area.setOpaque(false);
        return area;
    }

    private JPanel createButtonPanel(Book book) {
        JButton btn = new JButton("Pedir préstamo");
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(new Color(135, 206, 235));
        btn.setFocusPainted(false);

        btn.addActionListener(e -> {
            RequestService service = new RequestService();
            service.addRequest(user, book.getIsbn(), (success, message) ->
                SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(this, message)
                )
            );
        });

        JPanel btnWrapper = new JPanel(new BorderLayout());
        JPanel inner = new JPanel(new BorderLayout());
        inner.setOpaque(false);
        inner.add(btn, BorderLayout.SOUTH);
        btnWrapper.setPreferredSize(new Dimension(150, 20));
        btnWrapper.add(inner, BorderLayout.CENTER);

        return btnWrapper;
    }
}
