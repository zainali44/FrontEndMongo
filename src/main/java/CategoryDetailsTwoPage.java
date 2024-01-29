import Logic.BuyerBackend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDetailsTwoPage extends JFrame {


    private DefaultListModel model;
    private JList list;


    JPanel panel = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JButton cartBtn = new JButton("Cart");
    JButton GBBtn = new JButton("Go Back");
    private BuyerBackend buyerBackend;

    {
        try {
            buyerBackend = new BuyerBackend();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CategoryDetailsTwoPage(String category) {

        panel.setBackground(Color.WHITE);
        panel.setBounds(0, 0, 1366, 76);
        panel.setLayout(null);
        add(panel);

        panel2.setBackground(new Color(80, 245, 140));
        panel2.setBounds(0, 76, 1366, 86);
        panel2.setLayout(null);

        cartBtn.setBackground(Color.lightGray);
        cartBtn.setFont(new Font(null, Font.BOLD, 15));
        cartBtn.setBounds(1150, 15, 125, 30);
        cartBtn.setFocusable(false);
        panel.add(cartBtn);

        JLabel itemList = new JLabel(category + " | ITEM LIST");
        itemList.setFont(new Font(null, Font.PLAIN, 26));
        itemList.setBounds(75, 38, 400, 24);
        panel2.add(itemList);

        GBBtn.setBounds(1050, 30, 125, 30);
        GBBtn.setFont(new Font(null, Font.BOLD, 15));
        GBBtn.setBackground(Color.lightGray);
        GBBtn.setFocusable(false);
        panel2.add(GBBtn);
        add(panel2);

        model = new DefaultListModel();

        ArrayList<String> categoryItem = buyerBackend.categoryItems(category);
        for (int i = 0; i < categoryItem.size(); i++) {
            model.addElement(categoryItem.get(i));
        }

        list = new JList(model);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(0, 160, 1366, 521);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);

        list.setVisibleRowCount(10);
//        list.addMouseListener(new myAction());

        myAction action = new myAction();
        GBBtn.addActionListener(action);
        cartBtn.addActionListener(action);

        panel3.setLayout(null);
        panel3.setBounds(0, 160, 1366, 521);

        panel3.add(scrollPane);
        add(panel3);

        setVisible(true);
        setResizable(false);
        setTitle("Online Shopping");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1366, 720);

        list.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int A = list.getSelectedIndex();
                String name = (String) model.get(A);
                String desc = buyerBackend.getProductDescription(name);
                System.out.println(desc);

                if (!desc.isEmpty()) {
                    int qty = 0;
                    try {
                        qty = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Quantity"));
                        if (qty != 0) {
                            if (buyerBackend.addToCart(desc, qty)) {
                                JOptionPane.showMessageDialog(null, "Add to cart Successful");
                            } else JOptionPane.showMessageDialog(null, "Add to card failed");
                        }

                    } catch (Exception ex) {
                        System.out.println("Error: Numeric");
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
        });

    }


    public class myAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equalsIgnoreCase("Go Back")) {
                new MenuPage();
                dispose();
            } else if (e.getActionCommand().equalsIgnoreCase("Cart")) {
                new CartAreaPage();
                dispose();
            }
        }

//        @Override
//        public void mouseClicked(MouseEvent e) {
//            int A = list.getSelectedIndex();
//            String name = (String) model.get(A);
//            ArrayList<String> arr = buyerBackend.getCategoryName();
//            for (int i = 0; i < arr.size(); i++) {
//                if (arr.get(i).equals(name)) {
//                    if (JOptionPane.showConfirmDialog(null, "Are you sure you want to add Item to cart", "add to cart", JOptionPane.YES_NO_OPTION) == 0) {
////                        cart.add(category.getItems().get(i));
//
//                    }
//                }
//            }
//        }


//
//
//        @Override
//        public void mousePressed(MouseEvent e) {
//        }
//
//        @Override
//        public void mouseReleased(MouseEvent e) {
//        }
//
//        @Override
//        public void mouseEntered(MouseEvent e) {
//        }
//
//        @Override
//        public void mouseExited(MouseEvent e) {
    }
}


