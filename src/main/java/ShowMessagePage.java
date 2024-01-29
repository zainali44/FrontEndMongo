import Logic.SellerBackend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShowMessagePage extends JFrame {

    JPanel mainPanel = new JPanel();
    JPanel panel2 = new JPanel();
    JLabel label = new JLabel("");
    JLabel msgLabel = new JLabel("Seller : Show Messages");
    JLabel messageList = new JLabel("MESSAGES");
    JButton GBBtn = new JButton("Go Back");

    SellerBackend sellerBackend;

    {
        try {
            sellerBackend = new SellerBackend();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DefaultListModel model;
    private JList list;
    ArrayList<String> messages = sellerBackend.showMessages();

    public ShowMessagePage() {

        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBounds(0, 0, 1366, 150);
        mainPanel.setLayout(null);

        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setIcon(new ImageIcon("image 2.png"));
        label.setBounds(0, 40, 290, 55);
        mainPanel.add(label);

        msgLabel.setFont(new Font(null, Font.PLAIN, 25));
        msgLabel.setBounds(525, 45, 350, 60);
        msgLabel.setForeground(Color.BLACK);
        mainPanel.add(msgLabel);

        add(mainPanel);

        messageList.setForeground(Color.BLACK);
        messageList.setFont(new Font(null, Font.PLAIN, 22));
        messageList.setBounds(75, 100, 250, 24);
        mainPanel.add(messageList);

        GBBtn.setBounds(1150, 26, 100, 36);
        GBBtn.setFont(new Font(null, Font.BOLD, 15));
        GBBtn.setFocusable(false);
        GBBtn.setBackground(Color.lightGray);
        mainPanel.add(GBBtn);
        add(mainPanel);

        model = new DefaultListModel();

        for (int i = 0; i < messages.size(); i++) {
            model.addElement(messages.get(i));
        }
        list = new JList(model);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(0, 160, 1366, 521);

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.setVisibleRowCount(10);

        panel2.setLayout(null);
        panel2.setBounds(0, 160, 1366, 520);
        panel2.setBackground(new Color(80, 245, 140));

        panel2.add(scrollPane);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(panel2);
        setVisible(true);
        setResizable(false);
        setSize(1366, 720);
        setTitle("Online Shopping");
        GBBtn.addActionListener(new myAction());
        list.addMouseListener(new myAction());
    }

    public class myAction implements ActionListener, MouseListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equalsIgnoreCase("Go Back")) {
                new SellerPage();
                dispose();
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int A = list.getSelectedIndex();
            ArrayList<String> messages = sellerBackend.showMessages();

            for (int i = 0; i < messages.size(); i++) {
                if (messages.get(i).equalsIgnoreCase(model.get(A).toString())) {
//                    new CategoryDetailsPage(messages.get(i));
                    dispose();
                    break;

                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}
