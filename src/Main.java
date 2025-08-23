//package com.mycompany.library.maven;

import javax.swing.*;
import java.awt.*;
import com.google.firebase.FirebaseApp;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main extends JFrame {

    JPanel panel = new JPanel();
    JTextField user;
    JPasswordField pswrd;

    public Main() {
        setTitle("Biblioteca");
        setSize(600, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        panel();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void panel() {
        panel.setLayout(null);
        this.getContentPane().add(panel);

        JLabel etiqueta = new JLabel("Ingrese su usuario y contraseña", SwingConstants.CENTER);
        etiqueta.setForeground(Color.white);
        etiqueta.setBackground(Color.black);
        etiqueta.setOpaque(true);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 18));
        etiqueta.setBounds(0, 20, 600, 45);

        ImageIcon img = new ImageIcon("biblioteca.png");
        JLabel img_label = new JLabel();
        img_label.setBounds(200, 1, 400, 400);
        img_label.setIcon(new ImageIcon(img.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));

        panel.add(img_label);
        panel.add(etiqueta);

        buttons();
        textBoxs();
    }

    public void buttons() {
        JButton button = new JButton("Ingresar");
        button.setBackground(new Color(217, 221, 241));
        button.setFocusPainted(false);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        button.setBounds(220, 450, 150, 40);
        panel.add(button);

        button.addActionListener(e -> {
            String user_ = user.getText();

            if (!user_.isEmpty()) {
                try {
                    FirebaseApp app = FirebaseInitializer.initialize();

                    UserService userService = new UserService();

                    userService.checkUserExists(user_, new UserService.Callback() {
                        @Override
                        public void onComplete(boolean exists, String message) {
                            SwingUtilities.invokeLater(() -> {
                                if (exists) {
                                    // Usuario ya existe, mostramos mensaje de login
                                    JOptionPane.showMessageDialog(null, message);
                                    dispose();
                                    // Aquí puedes abrir la ventana principal de la biblioteca si la tienes
                                } else {
                                    // Usuario no existe, registramos
                                    userService.addUser(user_, new UserService.Callback() {
                                        @Override
                                        public void onComplete(boolean success, String regMessage) {
                                            SwingUtilities.invokeLater(() -> {
                                                if (success) {
                                                    JOptionPane.showMessageDialog(null, regMessage);
                                                    dispose();
                                                    // Aquí puedes abrir la ventana principal de la biblioteca si la tienes
                                                } else {
                                                    JOptionPane.showMessageDialog(null, "Error: " + regMessage);
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al conectar con Firebase");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor ingrese usuario");
            }
        });
    }

    public void textBoxs() {
        user = new JTextField();
        user.setFont(new Font("Arial", Font.PLAIN, 15));

        pswrd = new JPasswordField();
        pswrd.setFont(new Font("Arial", Font.PLAIN, 15));

        JButton showPswrd = new JButton("👁");
        showPswrd.setFocusPainted(false);
        showPswrd.setMargin(new Insets(0, 0, 0, 0));

        showPswrd.addActionListener(new java.awt.event.ActionListener() {
            private boolean mostrar = false;

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                mostrar = !mostrar;
                if (mostrar) {
                    pswrd.setEchoChar((char) 0);
                    showPswrd.setText("🚫");
                } else {
                    pswrd.setEchoChar('●');
                    showPswrd.setText("👁");
                }
            }
        });

        user.setBounds(180, 350, 240, 28);
        pswrd.setBounds(180, 400, 210, 28);
        showPswrd.setBounds(395, 400, 25, 27);

        panel.add(user);
        panel.add(pswrd);
        panel.add(showPswrd);
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.setVisible(true);
    }
}
