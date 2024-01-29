import Logic.SellerBackend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class SellerPage extends JFrame implements ActionListener {

    JPanel panel = new JPanel();
    JLabel logo = new JLabel("");
    JPanel panel2 = new JPanel();
    JLabel mainLabel = new JLabel("SELLER PANEL");
    JButton addItemBtn = new JButton("ADD ITEM");
    JButton delItemBtn = new JButton("DELETE ITEM");
    JButton showMsgBtn = new JButton("SHOW MESSAGES");
    JButton showCategoriesBtn = new JButton("SHOW CATEGORIES");
    JButton logoutBtn = new JButton("LOG OUT");

    SellerBackend sellerBackend;

    {
        try {
            sellerBackend = new SellerBackend();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ButtonGroup buttonGroup = new ButtonGroup();

    SellerPage() {

        panel.setBackground(Color.WHITE);
        panel.setBounds(0, 0, 1350, 100);
        panel.setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainLabel.setForeground(Color.BLACK);
        mainLabel.setFont(new Font(null, Font.PLAIN, 30));
        mainLabel.setBounds(515, 27, 263, 36);

        logo.setIcon(new ImageIcon("image 2.png"));
        logo.setBounds(10, 15, 320, 70);

        panel2.setBackground(Color.WHITE);
        panel2.setBounds(0, 93, 1274, 600);
        panel2.setLayout(null);

        showMsgBtn.setFont(new Font(null, Font.BOLD, 15));
        showMsgBtn.setBackground(Color.lightGray);
        showMsgBtn.setFocusable(false);
        showMsgBtn.setBounds(1100, 55, 200, 35);
        buttonGroup.add(showMsgBtn);
        panel.add(showMsgBtn);

        buttonGroup.add(addItemBtn);
        addItemBtn.setFont(new Font(null, Font.BOLD, 15));
        addItemBtn.setBackground(Color.lightGray);
        addItemBtn.setFocusable(false);
        addItemBtn.setBounds(544, 214, 200, 35);

        buttonGroup.add(delItemBtn);
        delItemBtn.setFont(new Font(null, Font.BOLD, 15));
        delItemBtn.setBackground(Color.lightGray);
        delItemBtn.setFocusable(false);

        delItemBtn.setBounds(544, 271, 200, 35);
        // delItemBtn.setBounds(544, 329, 200, 35);

        buttonGroup.add(showCategoriesBtn);
        showCategoriesBtn.setFont(new Font(null, Font.BOLD, 15));
        showCategoriesBtn.setBackground(Color.lightGray);
        showCategoriesBtn.setFocusable(false);
        showCategoriesBtn.setBounds(544, 329, 200, 35);
//        showCategoriesBtn.setBounds(544, 386, 200, 35);

        buttonGroup.add(logoutBtn);
        logoutBtn.setFont(new Font(null, Font.BOLD, 15));
        logoutBtn.setBackground(Color.lightGray);
        logoutBtn.setFocusable(false);
        logoutBtn.setBounds(544, 386, 200, 35);

        setResizable(false);
        setVisible(true);
        setTitle("Online Shopping");
        setSize(1366, 720);

        panel2.add(logoutBtn);
        panel2.add(showCategoriesBtn);
        panel.add(mainLabel);
        panel.add(logo);

        panel2.add(addItemBtn);
//        panel2.add(delCategoryBtn);
        panel2.add(delItemBtn);
        panel2.setBackground(new Color(80, 245, 140));
        add(panel);
        add(panel2);

        addItemBtn.addActionListener(this);
        showMsgBtn.addActionListener(this);
        showCategoriesBtn.addActionListener(this);
//        delCategoryBtn.addActionListener(this);
        delItemBtn.addActionListener(this);
        logoutBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("Show Messages")) {
            new ShowMessagePage();
            SellerPage.this.dispose();
        }
        if (e.getActionCommand().equalsIgnoreCase("Add Category")) {
            new AddCategoryPage();
            SellerPage.this.dispose();
        } else if (e.getActionCommand().equalsIgnoreCase("Add Item")) {
            new AddItemPage();
            SellerPage.this.dispose();
        } else if (e.getActionCommand().equalsIgnoreCase("Delete item")) {
            String categoryName = JOptionPane.showInputDialog(null, "Enter Category Name", "Delete category", JOptionPane.QUESTION_MESSAGE);
            String itemName = JOptionPane.showInputDialog(null, "Enter Item Name", "Delete item", JOptionPane.QUESTION_MESSAGE);
            ArrayList<String> categories = sellerBackend.showCategory();
            System.out.println("Category Size: " + categories.size());

            boolean check = true;
            boolean check2 = true;
            for (int i = 0; i < categories.size(); i++) {
                if (categories.get(i).equalsIgnoreCase(categoryName)) {
                    System.out.println("Category Found: " + categories.get(i));
                    check = false;
                    sellerBackend.retrieveProducts(categoryName);
                    ArrayList<String> productN = SellerBackend.productName;

                    for (int j = 0; j < productN.size(); j++) {

                        if (productN.get(j).equals(itemName)) {
                            check2 = false;
                            if (JOptionPane.showConfirmDialog(null, "Item Found! Are you sure You want to Delete Item from the Category? ", "Confirm", JOptionPane.YES_NO_OPTION) == 0) {
                                if (sellerBackend.deleteItem(categoryName, itemName)) {
                                    JOptionPane.showMessageDialog(null, "Item deleted successfully");
                                } else JOptionPane.showMessageDialog(null, "ERROR");
                            }
                            break;
                        }
                    }

                    if (check2) {
                        JOptionPane.showMessageDialog(null, "Item Not found", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
                }

            }
            if (check) {
                JOptionPane.showMessageDialog(null, "Category Not found", "Error", JOptionPane.WARNING_MESSAGE);

            }
        } else if (e.getActionCommand().equalsIgnoreCase("Show Categories")) {
            new ShowCategoriesPage();
            dispose();
        } else if (e.getActionCommand().equalsIgnoreCase("Log Out")) {

            if (JOptionPane.showConfirmDialog(null, "Are you sure you want to Log out?", "Confirm", JOptionPane.YES_NO_OPTION) == 0) {
                new LoginPage();
                SellerPage.this.dispose();
            }
        }
    }
}


