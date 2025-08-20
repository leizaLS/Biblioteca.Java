import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Main extends JFrame {
    JPanel panel = new JPanel();
    
    public Main() {
        setTitle("Biblioteca");
        setSize(600, 600);
        setResizable(false);
        //setMinimumSize(new Dimension(600, 600));
        //*getContentPane().setBackground(Color.GRAY);
        
        //Llamar al panel
        panel();
        
        //Cierre
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    
    //Panel
    public void panel() {
        panel.setLayout(null);
        this.getContentPane().add(panel);
        
        //Etiqueta
        JLabel etiqueta = new JLabel("Ingrese su usuario y contraseña", SwingConstants.CENTER);
        etiqueta.setForeground(Color.white);
        etiqueta.setBackground(Color.black);
        etiqueta.setOpaque(true);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 18));
        etiqueta.setBounds(0,20,600,45);
        
        //Imagen
        ImageIcon img  = new ImageIcon("biblioteca.png");
        JLabel img_label = new JLabel();
        img_label.setBounds(200,1,400,400);
        img_label.setIcon(new ImageIcon(img.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
        
        panel.add(img_label);
        panel.add(etiqueta);
        
        //Añadir elementos
        buttons();
        textBoxs();
    }
    
    public void buttons() {
        JButton button = new JButton("Hacer click");
        
        button.setBounds(220,450,150,40);
        panel.add(button);
        
        //Accion boton
        ActionListener event = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Hola");
                Login login = new Login();
                login.setVisible(true);
                dispose(); //cerrar ventana actual
            }
        };      
        button.addActionListener(event);
    }
    
    public void textBoxs(){
        JTextField text = new JTextField("Usuario");
        JTextField text2 = new JTextField("Contraseña");
        
        text.setBounds(180,350,240,28);
        text2.setBounds(180,400,240,28);
        
        panel.add(text);
        panel.add(text2);
    }
}
