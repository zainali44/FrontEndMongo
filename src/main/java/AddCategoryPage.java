import Logic.SellerBackend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddCategoryPage extends JFrame {

    private JPanel panel,panel2;
    private JLabel addCategLabel, categNameLabel, logo;
    private JTextField categText;
    private JButton addBtn, GBBtn;

     AddCategoryPage() {

        panel = new JPanel();
        panel.setBackground(new Color(80, 245, 140));
        panel.setBounds(0, 100, 1264, 581);
        panel.setLayout(null);

        panel2 = new JPanel();
        panel2.setBackground(new Color(80, 245, 140));
        panel2.setBounds(0, 0, 1366, 101);
        panel2.setLayout(null);

        logo = new JLabel("");
        logo.setIcon(new ImageIcon("image.png"));
        logo.setBounds(780, 50, 500, 500);
        panel.add(logo);

        GBBtn = new JButton("Go Back");
        GBBtn.setBounds(1150, 26, 100, 35);
        GBBtn.setFont(new Font(null,Font.BOLD,15));
        GBBtn.setBackground(Color.lightGray);
        GBBtn.setFocusable(false);
        panel2.add(GBBtn);



        categNameLabel = new JLabel("Category Name");
        categNameLabel.setFont(new Font(null, Font.PLAIN, 15));
        categNameLabel.setBounds(411, 175, 120, 19);
        panel.add(categNameLabel);

        addCategLabel = new JLabel("ADD CATEGORY");
        addCategLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addCategLabel.setFont(new Font(null, Font.PLAIN, 30));
        addCategLabel.setBounds(453, 11, 321, 36);
        panel.add(addCategLabel);

        categText = new JTextField();
        categText.setColumns(10);
        categText.setBounds(580, 175, 216, 20);
        panel.add(categText);

        addBtn = new JButton("ADD");
        addBtn.setFont(new Font(null, Font.BOLD, 15));
        addBtn.setBackground(Color.lightGray);
        addBtn.setBounds(550, 480, 100, 35);
        addBtn.setFocusable(false);
        panel.add(addBtn);

        add(panel);
        add(panel2);
        setVisible(true);
        setResizable(false);
        setTitle("Online Shopping");
        setSize(1366, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addBtn.addActionListener(new myAction());
        GBBtn.addActionListener(new myAction());
    }

    public class myAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getActionCommand().equalsIgnoreCase("Add")){

                if (!(categText.getText().isBlank())){

                    try {
                        SellerBackend sellerBackend = new SellerBackend();
                        if (sellerBackend.addToCategory(categText.getText())) {

                            JOptionPane.showMessageDialog(null,"Category Added","Success",JOptionPane.PLAIN_MESSAGE);
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Error");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    dispose();
                    new SellerPage();
                }
                else{
                    JOptionPane.showMessageDialog(null,"Please fill all given fields");
                }

            }
            else if (e.getActionCommand().equalsIgnoreCase("Go Back")){
                if (JOptionPane.showConfirmDialog(null,"Are you sure you want to cancel ","confirm",JOptionPane.YES_NO_OPTION)==0){
                    new SellerPage();
                    dispose();
                }
            }
        }
    }
}
