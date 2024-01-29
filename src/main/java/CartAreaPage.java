import Logic.BuyerBackend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class CartAreaPage extends JFrame {


    private DefaultListModel model;
    private JList list;


    JPanel panel = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JLabel cartList = new JLabel(" ITEM CART");
    JButton GBBtn = new JButton("Go Back");
    JButton checkoutBtn = new JButton("Checkout");

    public BuyerBackend buyerBackend;

    {
        try {
            buyerBackend = new BuyerBackend();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CartAreaPage() {

        panel.setBackground(Color.WHITE);
        panel.setBounds(0, 0, 1366, 76);
        panel.setLayout(null);
        add(panel);

        panel2.setBackground(new Color(80, 245, 140));
        panel2.setBounds(0, 76, 1366, 86);
        panel2.setLayout(null);

        cartList.setFont(new Font(null, Font.PLAIN, 26));
        cartList.setBounds(75, 38, 400, 24);
        panel2.add(cartList);

        GBBtn.setBounds(1150, 26, 100, 36);
        GBBtn.setFont(new Font(null, Font.BOLD, 15));
        GBBtn.setBackground(Color.lightGray);
        GBBtn.setFocusable(false);
        panel2.add(GBBtn);

        checkoutBtn.setBounds(1000, 26, 140, 36);
        checkoutBtn.setFont(new Font(null, Font.BOLD, 15));
        checkoutBtn.setBackground(Color.lightGray);
        checkoutBtn.setFocusable(false);
        panel2.add(checkoutBtn);

        add(panel2);

        model = new DefaultListModel();

        ArrayList<String> cart = buyerBackend.getCart();

        for (int i = 0; i < cart.size(); i++) {

            model.addElement(cart.get(i));

        }
        list = new JList(model);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(0, 160, 1366, 521);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);

        list.setVisibleRowCount(10);
        list.addMouseListener(new myAction());

        myAction action = new myAction();
        GBBtn.addActionListener(action);
        checkoutBtn.addActionListener(action);

        panel3.setLayout(null);
        panel3.setBounds(0, 160, 1366, 521);

        panel3.add(scrollPane);
        add(panel3);

//        this.buyer = u;
//        this.cart = items;
        setVisible(true);
        setResizable(false);
        setTitle("Online Shopping");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1366, 720);

    }

    class myAction implements MouseListener, ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getActionCommand().equalsIgnoreCase("Go Back")) {
                new MenuPage();
                dispose();
            } else if (e.getActionCommand().equalsIgnoreCase("Checkout")) {
                new CheckOutPage();
                dispose();
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int A = list.getSelectedIndex();
            String name = (String) model.get(A);
            int productID = buyerBackend.getProductID(name);

            if (JOptionPane.showConfirmDialog(null, "Are you sure you want to remove Product from cart", "remove", JOptionPane.YES_NO_OPTION) == 0) {
                buyerBackend.removeFromSpecificCart(productID);
                dispose();
                new CartAreaPage();

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

