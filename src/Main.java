import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Panel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Main extends JFrame {
    public Main() {
        setTitle("Biblioteca");
        setSize(600, 600);
        setMinimumSize(new Dimension(500, 500));
        getContentPane().setBackground(Color.GRAY);
        
        //Llamar al panel
        Panel();
        
       //Cierre
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    
    //Panel
    public void Panel() {
        JPanel panel = new JPanel();
        //panel.setBackground(Color.BLUE);
        panel.setLayout(null);
        this.getContentPane().add(panel);
        JLabel etiqueta = new JLabel("Manager Biblioteca", SwingConstants.CENTER);
        etiqueta.setForeground(Color.white);
        etiqueta.setBackground(Color.black);
        etiqueta.setOpaque(true);
        
        //Mensaje
        etiqueta.setBounds(10,10,500,30);
        
        //Imagen
        ImageIcon img  = new ImageIcon("biblioteca.png");
        JLabel img_label = new JLabel();
        panel.add(img_label);
        img_label.setBounds(180,1,400,400);
        //Tamaño img
        img_label.setIcon(new ImageIcon(img.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
        
        panel.add(etiqueta);
    }
}
