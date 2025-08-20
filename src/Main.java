import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Main extends JFrame {
    JPanel panel = new JPanel();
    JTextField user;
    
    public Main() {
        setTitle("Biblioteca");
        setSize(600, 600);
        setResizable(false);
        setLocationRelativeTo(null); //centrar ventana
        //setMinimumSize(new Dimension(600, 600));
        
        //Llamar al panel
        panel();
        
        //Cierre
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    
    //Panel
    public void panel() {
        panel.setLayout(null);
        this.getContentPane().add(panel);
        
        //Label
        JLabel etiqueta = new JLabel("Ingrese su usuario y contraseña", SwingConstants.CENTER);
        etiqueta.setForeground(Color.white);
        etiqueta.setBackground(Color.black);
        etiqueta.setOpaque(true);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 18));
        etiqueta.setBounds(0,20,600,45);
        
        //Image
        ImageIcon img  = new ImageIcon("biblioteca.png");
        JLabel img_label = new JLabel();
        img_label.setBounds(200,1,400,400);
        img_label.setIcon(new ImageIcon(img.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
        
        panel.add(img_label);
        panel.add(etiqueta);
        
        //Add elements
        buttons();
        textBoxs();
    }
    
    public void buttons() {
        JButton button = new JButton("Ingresar");
        button.setBackground(new Color(217, 221, 241));
        button.setFocusPainted(false);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBorder(new RoundedBorder(12));
        
        button.setBounds(220,450,150,40);
        panel.add(button);
        
        //Button Listener
        ActionListener event = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Testing");
                //**User Validation
                String user_ = user.getText();
                if (!user_.isEmpty()){
                    Library library = new Library(user_);
                    library.setVisible(true);
                    dispose(); //cerrar ventana actual
                }
                else {
                    JOptionPane.showMessageDialog(null, "Por favor ingrese usuario y contraseña");
                }                    
            }
        };      
        button.addActionListener(event);
    }
    
    public void textBoxs(){
        user = new JTextField();
        TextPrompt p = new TextPrompt("Usuario", user); //Placeholder
        user.setFont(new Font("Arial", Font.PLAIN, 15));
        
        //Password
        JPasswordField pswrd = new JPasswordField();
        TextPrompt p_ = new TextPrompt("Contraseña", pswrd);
        pswrd.setFont(new Font("Arial", Font.PLAIN, 15));
        
        //Button Password
        JButton showPswrd = new JButton("👁");
        showPswrd.setFocusPainted(false);
        showPswrd.setMargin(new Insets(0, 0, 0, 0)); // margen interno mínimo
        
        // Lógica para mostrar/ocultar
        showPswrd.addActionListener(new ActionListener() {
            private boolean mostrar = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                mostrar = !mostrar;
                if (mostrar) {
                    pswrd.setEchoChar((char) 0); 
                    showPswrd.setText("🚫");
                } else {
                    pswrd.setEchoChar('●'); // Ocultar texto con ●
                    showPswrd.setText("👁");
                }
            }
        });
        
        user.setBounds(180,350,240,28);
        pswrd.setBounds(180, 400, 210, 28);
        showPswrd.setBounds(395, 400, 25, 27);
        
        panel.add(user);
        panel.add(pswrd);
        panel.add(showPswrd);
    }
}
