import com.google.firebase.database.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Admin extends JFrame {

    private List<RequestWrapper> allRequests = new ArrayList<>();
    private JPanel requestsPanel;
    private JTextField filterField;

    public Admin() {
        setSize(850, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUI();
        loadRequests();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        //topPanel.setBackground(Color.red);

        JLabel label = new JLabel("Filtrar por usuario:");
        filterField = new JTextField();
        filterField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterRequests(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filterRequests(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filterRequests(); }
        });

        JButton logoutBtn = new JButton("Cerrar sesión");
        logoutBtn.setBackground(new Color(250, 70, 35));
        logoutBtn.addActionListener(e -> {
            dispose();
            new Main().setVisible(true);
        });

        JPanel filterPanel = new JPanel(new BorderLayout(5, 5));
        filterPanel.add(label, BorderLayout.WEST);
        filterPanel.add(filterField, BorderLayout.CENTER);

        topPanel.add(filterPanel, BorderLayout.CENTER);
        topPanel.add(logoutBtn, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        requestsPanel = new JPanel();
        requestsPanel.setBackground(Color.GRAY);
        requestsPanel.setLayout(new BoxLayout(requestsPanel, BoxLayout.Y_AXIS));
        requestsPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // margen interno (top, left, bottom, right)
        JScrollPane scrollPane = new JScrollPane(requestsPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadRequests() {
        DatabaseReference requestsRef = FirebaseDatabase.getInstance().getReference("requests");

        requestsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                allRequests.clear();

                for (DataSnapshot reqSnap : snapshot.getChildren()) {
                    Request req = reqSnap.getValue(Request.class);
                    if (req != null) {
                        allRequests.add(new RequestWrapper(req, reqSnap.getKey()));
                    }
                }

                SwingUtilities.invokeLater(() -> filterRequests());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                SwingUtilities.invokeLater(() -> {
                    requestsPanel.removeAll();
                    requestsPanel.add(new JLabel("Error al cargar solicitudes: " + error.getMessage()));
                    requestsPanel.revalidate();
                    requestsPanel.repaint();
                });
            }
        });
    }

    private void filterRequests() {
        String filter = filterField.getText().trim().toLowerCase();

        requestsPanel.removeAll();

        for (RequestWrapper request : allRequests) {
            if (request.user.toLowerCase().contains(filter)) {
                JPanel panel = createRequestPanel(request);
                requestsPanel.add(panel);
                requestsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        requestsPanel.revalidate();
        requestsPanel.repaint();
    }

    private JPanel createRequestPanel(RequestWrapper request) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(750, 100));
        panel.setMaximumSize(new Dimension(750, 100));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        JTextArea textArea = new JTextArea(
            "Usuario: " + request.user + "\n" +
            "ISBN: " + request.isbn + "\n" +
            "Fecha: " + request.date
        );
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(textArea, BorderLayout.CENTER);

        JButton deleteBtn = new JButton("Eliminar");
        deleteBtn.setBackground(new Color(205, 92, 92));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFocusPainted(false);
        deleteBtn.setFont(new Font("Arial", Font.BOLD, 14));
        
        //Confirmar
        deleteBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar esta solicitud?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                deleteRequest(request.key);
            }
        });

        JPanel btnPanel = new JPanel();
        btnPanel.add(deleteBtn);
        panel.add(btnPanel, BorderLayout.EAST);

        return panel;
    }

    private void deleteRequest(String key) {
        DatabaseReference requestsRef = FirebaseDatabase.getInstance().getReference("requests");
        requestsRef.child(key).removeValue((error, ref) -> {
            if (error != null) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + error.getMessage());
            } else {
                JOptionPane.showMessageDialog(this, "Solicitud eliminada.");
                loadRequests();
            }
        });
    }

    // Clase interna para Request con la key de Firebase
    public static class RequestWrapper extends Request {
        public String key;

        public RequestWrapper() {
            super();
        }

        public RequestWrapper(Request request, String key) {
            super(request.user, request.isbn, request.date);
            this.key = key;
        }
    }
}
