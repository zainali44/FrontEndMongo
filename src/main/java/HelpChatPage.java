import Logic.BuyerBackend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class HelpChatPage extends JFrame {

    BuyerBackend buyerBackend;

    {
        try {
            buyerBackend = new BuyerBackend();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ButtonGroup buttonGroup = new ButtonGroup();

    JPanel panel = new JPanel();  // the main panel
    JLabel imageLabel = new JLabel("");  // an empty label just for image purpose
    JLabel helpLabel = new JLabel("HELP CHAT PANEL");  // help label
    JLabel msg = new JLabel("Enter Message");
    JTextField msgField = new JTextField();
    JLabel msgtitle = new JLabel("Enter Title");
    JTextField msgTitleField = new JTextField();
    JLabel seller = new JLabel("Send to");
    JTextField sellerField = new JTextField();
    JButton sendBtn = new JButton("Send Message");  // send button
    JButton GBBtn = new JButton("Go Back");

    HelpChatPage() {

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

        helpLabel.setFont(new Font(null, Font.PLAIN, 28));
        helpLabel.setForeground(Color.BLACK);
        helpLabel.setHorizontalAlignment(SwingConstants.CENTER);
        helpLabel.setBounds(370, 330, 600, 30);
        panel.add(helpLabel);

        msg.setFont(new Font(null, Font.PLAIN, 14));
        msg.setForeground(Color.BLACK);
        msg.setBounds(470, 400, 600, 30);
        panel.add(msg);

        msgField = new JTextField();
        msgField.setBackground(Color.WHITE);
        msgField.setBounds(570, 400, 200, 30);
        panel.add(msgField);
        msgField.setColumns(10);

        msgtitle.setFont(new Font(null, Font.PLAIN, 14));
        msgtitle.setForeground(Color.BLACK);
        msgtitle.setBounds(470,450, 600, 30);
        panel.add(msgtitle);

        msgTitleField = new JTextField();
        msgTitleField.setBackground(Color.WHITE);
        msgTitleField.setBounds(570, 450, 200, 30);
        panel.add(msgTitleField);
        msgTitleField.setColumns(10);

        seller.setFont(new Font(null, Font.PLAIN, 14));
        seller.setForeground(Color.BLACK);
        seller.setBounds(470, 500, 600, 30);
        panel.add(seller);

        sellerField = new JTextField();
        sellerField.setBackground(Color.WHITE);
        sellerField.setBounds(570, 500, 200, 30);
        panel.add(sellerField);
        sellerField.setColumns(10);

        sendBtn.setBackground(Color.lightGray);
        sendBtn.setFocusable(false);
        sendBtn.setForeground(Color.BLACK);
        sendBtn.setFont(new Font(null, Font.BOLD, 15));
        sendBtn.setBounds(650, 550, 180, 30);
        panel.add(sendBtn);
        buttonGroup.add(sendBtn);

        GBBtn.setBackground(Color.lightGray);
        GBBtn.setFocusable(false);
        GBBtn.setForeground(Color.BLACK);
        GBBtn.setFont(new Font(null, Font.BOLD, 15));
        GBBtn.setBounds(450, 550, 180, 30);
        panel.add(GBBtn);
        buttonGroup.add(GBBtn);

        add(panel);
        setVisible(true);
        setResizable(false);
        Action action = new Action();
        sendBtn.addActionListener(action);
        GBBtn.addActionListener(action);

    }


    class Action implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equalsIgnoreCase("Go Back")) {
                new MenuPage();
                HelpChatPage.this.dispose();
            }
            if (e.getActionCommand().equalsIgnoreCase("Send Message")) {
                if (!(msgField.getText().isBlank() || msgTitleField.getText().isBlank() || sellerField.getText().isBlank())) {
                    try {

                        String messageTitle = msgField.getText();
                        String msg = msgTitleField.getText();
                        String seller = sellerField.getText();
                            if (buyerBackend.addMessage(msg,messageTitle,seller)){
                                JOptionPane.showMessageDialog(null, "Message sent Successfully");
                            }
                            else JOptionPane.showMessageDialog(null, "ERROR");
                    } catch (NumberFormatException el) {
                        JOptionPane.showMessageDialog(null, "Invalid something", "Invalid input", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill all given fields", "Error", JOptionPane.WARNING_MESSAGE);

                }
            }
        }
    }
}
