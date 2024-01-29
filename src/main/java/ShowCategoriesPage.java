import Logic.SellerBackend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShowCategoriesPage extends JFrame {

    JPanel mainPanel = new JPanel();
    JPanel panel2 = new JPanel();
    JLabel label = new JLabel("");
    JLabel labelShow = new JLabel("Seller : Show Category");
    JLabel categoryList = new JLabel("CATEGORIES");
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
    ArrayList<String> categories = sellerBackend.showCategory();

    public ShowCategoriesPage() {

        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBounds(0, 0, 1366, 150);
        mainPanel.setLayout(null);

        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setIcon(new ImageIcon("image 2.png"));
        label.setBounds(0, 40, 290, 55);
        mainPanel.add(label);

        labelShow.setFont(new Font(null, Font.PLAIN, 25));
        labelShow.setBounds(525, 45, 350, 60);
        labelShow.setForeground(Color.BLACK);
        mainPanel.add(labelShow);

        add(mainPanel);

        categoryList.setForeground(Color.BLACK);
        categoryList.setFont(new Font(null, Font.PLAIN, 22));
        categoryList.setBounds(75, 100, 250, 24);
        mainPanel.add(categoryList);

        GBBtn.setBounds(1150, 26, 100, 36);
        GBBtn.setFont(new Font(null, Font.BOLD, 15));
        GBBtn.setFocusable(false);
        GBBtn.setBackground(Color.lightGray);
        mainPanel.add(GBBtn);
        add(mainPanel);

        model = new DefaultListModel();

        for (int i = 0; i < categories.size(); i++) {
            model.addElement(categories.get(i));
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
            ArrayList<String> categories = sellerBackend.showCategory();

            for (int i = 0; i < categories.size(); i++) {
                if (categories.get(i).equalsIgnoreCase(model.get(A).toString())) {
                    new CategoryDetailsPage(categories.get(i));
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
