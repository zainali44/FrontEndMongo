import Logic.SellerBackend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDetailsPage extends JFrame {

    private DefaultListModel model;
    private JList list;

    JPanel panel = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3= new JPanel();
    JLabel imageLabel = new JLabel("");
    JLabel labelShow = new JLabel("Seller : Show Categories Details");
    JButton GBBtn = new JButton("Go Back");

    SellerBackend sellerBackend;

    {
        try {
            sellerBackend = new SellerBackend();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CategoryDetailsPage(String category){

        panel.setBackground(Color.WHITE);
        panel.setBounds(0, 0, 1366, 76);
        panel.setLayout(null);

        labelShow.setFont(new Font(null,Font.BOLD,25));
        labelShow.setBounds(500,15,450,60);
        panel.add(labelShow);

        GBBtn.setBounds(1150, 25, 100, 36);
        GBBtn.setFont(new Font(null,Font.BOLD,15));
        GBBtn.setBackground(Color.lightGray);
        GBBtn.setFocusable(false);
        panel.add(GBBtn);

        add(panel);

        panel2.setBackground(Color.WHITE);
        panel2.setBounds(0, 76, 1366, 86);
        panel2.setLayout(null);

        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setIcon(new ImageIcon("image 2.png"));
        imageLabel.setBounds(510, -20, 290, 100);
        panel2.add(imageLabel);

        JLabel itemList = new JLabel(category.toUpperCase()+" | ITEM LIST");
        itemList.setForeground(Color.WHITE);
        itemList.setFont(new Font(null, Font.PLAIN, 22));
        itemList.setBounds(75, 38, 400, 24);
        panel2.add(itemList);


        add(panel2);

        model = new DefaultListModel();
        ArrayList<String> categories = sellerBackend.retrieveProducts(category);
        for (int i = 0; i < categories.size(); i++) {
            model.addElement(categories.get(i));

        }
        list = new JList(model);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(0, 160, 1366, 521);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);

        list.setVisibleRowCount(10);

    myAction action= new myAction();
        GBBtn.addActionListener(action);

        panel3.setLayout(null);
        panel3.setBounds(0, 160, 1366, 521);
        panel3.setBackground(new Color(80, 245, 140));
        panel3.add(scrollPane);
        add(panel3);

        setVisible(true);
        setResizable(false);
        setTitle("Online Shopping");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1366, 720);
    }

    public class myAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equalsIgnoreCase("Go Back")) {
                new ShowCategoriesPage();
                dispose();
            }
        }
    }
}
