import Logic.BuyerBackend;
import Logic.SellerBackend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class SignUpPage extends JFrame implements ActionListener{

    JLabel logo = new JLabel("");
    JLabel fNameLabel = new JLabel("First name");  // first name label
    JLabel lNameLabel = new JLabel("Last name");  // last name label
    JLabel phNoLabel = new JLabel("Phone no.");  // phone no. label
    JLabel passLabel = new JLabel("Password");
    JLabel adrsLabel = new JLabel("Address");
    JLabel confirmPassLabel = new JLabel("Confirm Pass");
    JLabel genLabel = new JLabel("Gender");
    JLabel emailLabel = new JLabel("Email");
    JCheckBox chkBox = new JCheckBox("Agree with Terms And Conditions");  // Check box for agreement
    JButton signUpBtn = new JButton("SignUp");  // signUp Button
    JButton GBBtn = new JButton("Go Back");  // Go Back button

    BuyerBackend buyerBackend;
    SellerBackend sellerBackend;

    {
        try {
            buyerBackend = new BuyerBackend();
            sellerBackend = new SellerBackend();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    JTextField lNameText, fNameText, phNoText, adrsText, emailText;  // the text fields for each labels
    JPasswordField passText, pass2Text;  // password fields
    JComboBox c1,c2,c3, gender,signUpAs;  // Combo boxes for multiple choices

    JPanel panel = new JPanel();

    SignUpPage() {

        setSize(1366,720);
        setResizable(false);
        setTitle("Online Shopping");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel.setBackground(new Color(80, 245, 140));
        panel.setLayout(null);

        logo.setIcon(new ImageIcon("image.png"));
        logo.setBounds(380, 50, 1904, 260);
        panel.add(logo);

        fNameLabel.setForeground(Color.BLACK);
        fNameLabel.setBackground(new Color(80, 245, 140));
        fNameLabel.setFont(new Font(null, Font.PLAIN, 15));
        fNameLabel.setBounds(450, 345, 74, 19);
        panel.add(fNameLabel);

        fNameText = new JTextField();
        fNameText.setBounds(550, 345, 150, 20);
        panel.add(fNameText);
        fNameText.setColumns(10);

        lNameLabel.setForeground(Color.BLACK);
        lNameLabel.setFont(new Font(null, Font.PLAIN, 15));
        lNameLabel.setBackground(new Color(80, 245, 140));
        lNameLabel.setBounds(730, 345, 71, 19);
        panel.add(lNameLabel);

        lNameText = new JTextField();
        lNameText.setColumns(10);
        lNameText.setBounds(820, 345, 150, 20);
        panel.add(lNameText);

        phNoLabel.setForeground(Color.BLACK);
        phNoLabel.setFont(new Font(null, Font.PLAIN, 15));
        phNoLabel.setBackground(new Color(80, 245, 140));
        phNoLabel.setBounds(450, 503, 133, 19);
        panel.add(phNoLabel);

        phNoText = new JTextField();
        phNoText.setColumns(10);
        phNoText.setBounds(550, 504, 259, 20);
        panel.add(phNoText);

        passLabel.setForeground(Color.BLACK);
        passLabel.setFont(new Font(null, Font.PLAIN, 15));
        passLabel.setBackground(new Color(80, 245, 140));
        passLabel.setBounds(450, 533, 133, 19);
        panel.add(passLabel);

        passText = new JPasswordField();
        passText.setBounds(550, 535, 259, 20);
        panel.add(passText);

        confirmPassLabel.setForeground(Color.BLACK);
        confirmPassLabel.setFont(new Font(null, Font.PLAIN, 15));
        confirmPassLabel.setBackground(new Color(80, 245, 140));
        confirmPassLabel.setBounds(450, 563, 133, 19);
        panel.add(confirmPassLabel);

        pass2Text = new JPasswordField();
        pass2Text.setBounds(550, 565, 259, 20);
        panel.add(pass2Text);

        chkBox.setForeground(Color.BLACK);
        chkBox.setBackground(new Color(80, 245, 140));
        chkBox.setFont(new Font(null, Font.PLAIN, 15));
        chkBox.setBounds(550, 600, 309, 27);
        panel.add(chkBox);

        signUpBtn.setBackground(Color.lightGray);
        signUpBtn.setFocusable(false);
        signUpBtn.setForeground(Color.BLACK);
        signUpBtn.setFont(new Font(null, Font.BOLD, 15));
        signUpBtn.setBounds(630, 640, 125, 27);
        panel.add(signUpBtn);

        adrsLabel.setForeground(Color.BLACK);
        adrsLabel.setFont(new Font(null, Font.PLAIN, 15));
        adrsLabel.setBackground(new Color(80, 245, 140));
        adrsLabel.setBounds(450, 477, 95, 19);
        panel.add(adrsLabel);

        adrsText = new JTextField();
        adrsText.setColumns(10);
        adrsText.setBounds(550, 478, 259, 20);
        panel.add(adrsText);

        genLabel.setForeground(Color.BLACK);
        genLabel.setFont(new Font(null, Font.PLAIN, 15));
        genLabel.setBackground(new Color(80, 245, 140));
        genLabel.setBounds(450, 425, 71, 19);
        panel.add(genLabel);

        GBBtn.setBounds(15, 26, 100, 36);
        GBBtn.setForeground(Color.black);
        GBBtn.setBackground(Color.lightGray);
        GBBtn.setFocusable(false);
        panel.add(GBBtn);

        gender = new JComboBox();
        gender.setFont(new Font(null, Font.PLAIN, 15));
        gender.setModel(new DefaultComboBoxModel(new String[]{"Male", "Female", "Other"}));
        gender.setMaximumRowCount(3);
        gender.setEditable(false);
        gender.setBackground(Color.lightGray);
        gender.setBounds(550, 425, 100, 20);
        panel.add(gender);

        signUpAs = new JComboBox();
        signUpAs.setFont(new Font(null, Font.PLAIN, 15));
        signUpAs.setModel(new DefaultComboBoxModel(new String[]{"Buyer", "Seller"}));
        signUpAs.setMaximumRowCount(3);
        signUpAs.setEditable(false);
        signUpAs.setBackground(Color.lightGray);
        signUpAs.setBounds(660, 425, 100, 20);
        panel.add(signUpAs);

        emailLabel.setForeground(Color.BLACK);
        emailLabel.setFont(new Font(null, Font.PLAIN, 15));
        emailLabel.setBackground(new Color(80, 245, 140));
        emailLabel.setBounds(450, 451, 133, 19);
        panel.add(emailLabel);

        emailText = new JTextField();
        emailText.setColumns(10);
        emailText.setBounds(550, 452, 259, 20);
        panel.add(emailText);

        add(panel);
        setVisible(true);

        signUpBtn.addActionListener(this);
        GBBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("SignUp")) {
            if (chkBox.isSelected()) {
                if (lNameText.getText().isEmpty() || fNameText.getText().isEmpty() || emailText.getText().isEmpty() || adrsText.getText().isEmpty() || emailText.getText().isEmpty() || passText.getText().isEmpty() || pass2Text.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(new JFrame("Error"), "Please Fill All The Given Fields");

                } else {
                    if (passText.getText().equals(pass2Text.getText())) {

                            boolean sign = false;
                            if (String.valueOf(signUpAs.getSelectedItem()).equals("Buyer")){
                                sign = buyerBackend.signupBuyer(fNameText.getText(),lNameText.getText(),emailText.getText(),phNoText.getText(), String.valueOf(gender.getSelectedItem()),adrsText.getText(), String.valueOf(passText.getPassword()));
                            }
                            else {
                                sign = sellerBackend.signupSeller(fNameText.getText(),lNameText.getText(),emailText.getText(),phNoText.getText(), String.valueOf(gender.getSelectedItem()),adrsText.getText(), String.valueOf(passText.getPassword()));
                            }
                            if (sign) {
                                JOptionPane.showMessageDialog(new JFrame("Success"), "You Have signed up ");
                                SignUpPage.this.dispose();
                                new WelcomePage();
                            }
                            else {
                                JOptionPane.showMessageDialog(this,"ERROR");
                            }

                        }
                        else{
                            JOptionPane.showMessageDialog(new JFrame(), "Invalid Email format","Error",JOptionPane.WARNING_MESSAGE);

                    }

                }
            } else {
                JOptionPane.showMessageDialog(new JFrame("Error"), "Please Agree To Terms And Conditions");
            }
        }
        else if (e.getActionCommand().equalsIgnoreCase("Go back")){
            if (JOptionPane.showConfirmDialog(null,"Are you sure you want to cancel Sign up? ","confirm",JOptionPane.YES_NO_OPTION)==0){
                new WelcomePage();
                dispose();
            }
        }
    }
}


