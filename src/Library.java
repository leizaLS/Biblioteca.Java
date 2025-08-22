import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Library extends JFrame{
    public Library(String user) {
        initComponents(user);
    }

    private void initComponents(String user) {
        setTitle("Biblioteca");
        setSize(600, 600); setResizable(false);
        setLocationRelativeTo(null); //centrar ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        JLabel label = new JLabel("Bienvenido: " + user, SwingConstants.CENTER);
        label.setForeground(Color.white);
        label.setBackground(Color.black);
        label.setOpaque(true);
        
        
        label.setBounds(0, 5, 600, 40);
        label.setFont(new Font("Arial", Font.BOLD, 18));

        // Agregar al JFrame
        add(label);
    }
}
