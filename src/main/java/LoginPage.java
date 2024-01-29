import Logic.BuyerBackend;
import Logic.SellerBackend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class LoginPage extends JFrame {

    private ButtonGroup buttonGroup = new ButtonGroup();
    BuyerBackend buyerBackend;
    SellerBackend sellerBackend;

    {
        try {
            buyerBackend = new BuyerBackend();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    {
        try {
            sellerBackend = new SellerBackend();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    JPanel panel = new JPanel();
    JTextField loginField;
    JPasswordField passwordField;
    JLabel imageLabel = new JLabel("");
    JLabel usernameLabel = new JLabel("Email");
    JLabel passwordLabel = new JLabel("Password");
    JLabel ifNotLabel = new JLabel("Not A User? Get Registered");
    JRadioButton radioBtn1, radioBtn2;
    JButton loginBtn = new JButton("Login");

    LoginPage() {

        setSize(1366, 720);

        setBackground(new Color(80, 245, 140));
        setTitle("Online Shopping");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel.setBounds(0, 0, 1904, 1041);
        panel.setBackground(new Color(80, 245, 140));
        panel.setLayout(null);


        imageLabel.setIcon(new ImageIcon("image.png"));
        imageLabel.setBounds(380, 50, 1904, 260);

        panel.add(imageLabel);

        usernameLabel.setForeground(Color.BLACK);
        usernameLabel.setFont(new Font(null, Font.PLAIN, 20));
        usernameLabel.setBounds(467, 349, 92, 24);
        panel.add(usernameLabel);

        loginField = new JTextField();
        loginField.setBounds(619, 354, 233, 20);
        panel.add(loginField);
        loginField.setColumns(10);

        passwordLabel.setForeground(Color.BLACK);
        passwordLabel.setFont(new Font(null, Font.PLAIN, 20));
        passwordLabel.setBounds(467, 417, 100, 24);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(619, 422, 233, 20);
        panel.add(passwordField);

        radioBtn1 = new JRadioButton("Login As Seller");
        buttonGroup.add(radioBtn1);
        radioBtn1.setForeground(Color.BLACK);
        radioBtn1.setBackground(new Color(80, 245, 140));
        radioBtn1.setFont(new Font(null, Font.BOLD, 13));
        radioBtn1.setBounds(619, 453, 130, 25);
        panel.add(radioBtn1);

        radioBtn2 = new JRadioButton("Login As Buyer");
        buttonGroup.add(radioBtn2);
        radioBtn2.setForeground(Color.BLACK);
        radioBtn2.setFont(new Font(null, Font.BOLD, 13));
        radioBtn2.setBackground(new Color(80, 245, 140));
        radioBtn2.setSelected(true);

        radioBtn2.setBounds(754, 453, 121, 25);
        panel.add(radioBtn2);
        add(panel);

        loginBtn.setBackground(Color.lightGray);
        loginBtn.setForeground(Color.BLACK);
        loginBtn.setFocusable(false);
        loginBtn.setFont(new Font(null, Font.BOLD, 15));
        loginBtn.setBounds(655, 542, 125, 27);
        panel.add(loginBtn);
//        loginBtn.addActionListener(this);

        setResizable(false);

        setSize(1366, 720);

        setVisible(true);


        ifNotLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ifNotLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                SignUpPage signUp = new SignUpPage();
                LoginPage.this.dispose();
            }
        });

        ifNotLabel.setForeground(Color.BLACK);
        ifNotLabel.setFont(new Font(null, Font.PLAIN, 15));
        ifNotLabel.setBounds(760, 483, 193, 19);
        panel.add(ifNotLabel);


        loginBtn.addActionListener(e -> {
            if (radioBtn1.isSelected()) {
                String emailAddress = loginField.getText();
                String password = String.valueOf(passwordField.getPassword());

                if (sellerBackend.validateSeller(emailAddress, password)) {
                    JOptionPane.showMessageDialog(this, "Logged In Successfully");
                    new SellerPage();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Not Logged In");
                }

            }

            if (radioBtn2.isSelected()) {
                String emailAddress = loginField.getText();
                String password = String.valueOf(passwordField.getPassword());

                if (buyerBackend.validateBuyer(emailAddress, password)) {
                    JOptionPane.showMessageDialog(this, "Logged In Successfully");
                    new MenuPage();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Not Logged In");
                }

            }
        });

    }
}

