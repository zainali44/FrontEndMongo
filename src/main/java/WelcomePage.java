import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePage extends JFrame {

    private ButtonGroup buttonGroup = new ButtonGroup();

    JPanel panel = new JPanel();  // the main panel
    JLabel imageLabel = new JLabel("");  // an empty label just for image purpose
    JLabel Welcome = new JLabel("WELCOME TO ONLINE SHOPPING SYSTEM");  // welcome label
    JButton loginBtn= new JButton("Login");  // login button if you want to log in or
    JButton signupBtn = new JButton("SignUp");  // signup button

    WelcomePage() {

        setTitle("Online Shopping");
        setSize(1366, 720);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel.setBounds(0, 0, 1904, 1041);
        panel.setBackground(new Color(80, 245, 140));

        panel.setLayout(null);

        imageLabel.setIcon(new ImageIcon("image.png"));
        imageLabel.setBounds(380, 50, 1904, 260);
        panel.add(imageLabel);

        Welcome.setFont(new Font(null, Font.PLAIN, 28));
        Welcome.setForeground(Color.BLACK);
        Welcome.setHorizontalAlignment(SwingConstants.CENTER);
        Welcome.setBounds(370, 330, 600, 30);
        panel.add(Welcome);

        loginBtn.setFont(new Font(null, Font.BOLD, 15));
        loginBtn.setBackground(Color.lightGray);
        loginBtn.setFocusable(false);
        loginBtn.setForeground(Color.BLACK);
        loginBtn.setBounds(600, 469, 125, 50);
        panel.add(loginBtn);

        signupBtn.setBackground(Color.lightGray);
        signupBtn.setFocusable(false);
        signupBtn.setForeground(Color.BLACK);
        signupBtn.setFont(new Font(null, Font.BOLD, 15));
        signupBtn.setBounds(600, 546, 125, 50);
        panel.add(signupBtn);
        buttonGroup.add(loginBtn);
        buttonGroup.add(signupBtn);

        add(panel);
        setVisible(true);
        setResizable(false);
        Action action = new Action();
        loginBtn.addActionListener(action);
        signupBtn.addActionListener(action);

    }

    class Action implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equalsIgnoreCase("Login")) {
                new LoginPage();
                WelcomePage.this.dispose();
            } else if (e.getActionCommand().equalsIgnoreCase("Signup")) {
                new SignUpPage();
                WelcomePage.this.dispose();
            }
        }
    }
}
