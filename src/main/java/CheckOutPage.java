import Logic.BuyerBackend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import static Logic.BuyerBackend.buyerFullName;

public class CheckOutPage extends JFrame{

    private ArrayList<String> cart;

    JPanel panel = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JLabel nameLabel = new JLabel("Name :");
    JLabel billLabel = new JLabel("BILL");
    JLabel addressLabel = new JLabel("Address :");
    JLabel itemNameLabel = new JLabel("Item Name");
    JLabel priceLabel = new JLabel("Item Price");
    JLabel totalPriceLabel = new JLabel("Total Price");
    JButton confirmBtn = new JButton("Confirm Order");
    JButton GBBtn = new JButton("Cancel Payment");

    BuyerBackend buyerBackend;

    {
        try {
            buyerBackend = new BuyerBackend();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CheckOutPage() {

        panel.setBackground(Color.WHITE);
        panel.setBounds(0, 0, 1366, 107);
        add(panel);
        panel.setLayout(null);

        panel2.setLayout(null);
        panel2.setBackground(new Color(80, 245, 140));
        panel2.setBounds(0, 106, 1366, 86);
        add(panel2);

        billLabel.setFont(new Font(null, Font.PLAIN, 26));
        billLabel.setBounds(75, 38, 79, 26);
        panel2.add(billLabel);

        panel3.setBackground(Color.WHITE);
        panel3.setBounds(0, 192, 1366, 489);
        add(panel3);
        panel3.setLayout(null);

        nameLabel.setForeground(Color.BLACK);
        nameLabel.setFont(new Font(null, Font.BOLD, 15));
        nameLabel.setBounds(10, 62, 50, 19);
        panel3.add(nameLabel);
        JLabel uName = new JLabel();
        uName.setForeground(Color.BLACK);
        uName.setFont(new Font(null, Font.PLAIN, 15));
        uName.setBounds(100, 62, 150, 19);
        panel3.add(uName);
        uName.setText(buyerFullName);

        addressLabel.setForeground(Color.BLACK);
        addressLabel.setFont(new Font(null, Font.BOLD, 15));
        addressLabel.setBounds(10, 103, 70, 19);
        panel3.add(addressLabel);

        JLabel uAddress = new JLabel(buyerBackend.getDeliveryAddress());
        uAddress.setForeground(Color.BLACK);
        uAddress.setFont(new Font(null, Font.PLAIN, 15));
        uAddress.setBounds(100, 103, 400, 19);
        panel3.add(uAddress);

        itemNameLabel.setForeground(Color.BLACK);
        itemNameLabel.setFont(new Font(null, Font.BOLD, 15));
        itemNameLabel.setBounds(719, 11, 90, 19);
        panel3.add(itemNameLabel);

        priceLabel.setForeground(Color.BLACK);
        priceLabel.setFont(new Font(null, Font.BOLD, 15));
        priceLabel.setBounds(930, 11, 75, 19);
        panel3.add(priceLabel);

        totalPriceLabel.setForeground(Color.BLACK);
        totalPriceLabel.setFont(new Font(null, Font.BOLD, 15));
        totalPriceLabel.setBounds(472, 372, 87, 19);
        panel3.add(totalPriceLabel);

        JLabel line= new JLabel("--------------------------------------------------------------------------------------------------------------------------------------------");
        line.setForeground(Color.BLACK);
        line.setFont(new Font(null, Font.BOLD, 15));
        line.setBounds(472, 362, 700, 19);
        panel3.add(line);

        confirmBtn.setFont(new Font(null, Font.BOLD, 15));
        confirmBtn.setBackground(Color.lightGray);
        confirmBtn.setFocusable(false);
        confirmBtn.setBounds(575, 415, 160, 25);
        panel3.add(confirmBtn);

        buyerBackend.getCart();
        cart = BuyerBackend.buyerItemName;
        JLabel []itemName =new JLabel[cart.size()];
        JLabel []itemPrice = new JLabel[cart.size()];

        int y_axis = 0;
        double totalPrice=0;
        ArrayList<Integer> cartPrice = BuyerBackend.buyerItemPrice;
        for (int i = 0; i < cart.size() ; i++) {

            itemName[i]= new JLabel(cart.get(i));
            itemPrice[i]= new JLabel(cartPrice.get(i) +" PKR.");
            itemName[i].setBounds(710, 30 + y_axis, 200, 19);
            itemPrice[i].setBounds(930,30 + y_axis, 80, 19);
            y_axis = y_axis + 20;

            panel3.add(itemPrice[i]);
            panel3.add(itemName[i]);
        }
        JLabel totalPriceLabel=new JLabel(buyerBackend.getTotalPrice()+" PKR.");
        totalPriceLabel.setBounds(930, 372, 87, 19);
        panel3.add(totalPriceLabel);

        GBBtn.setBackground(Color.lightGray);
        GBBtn.setFocusable(false);
        GBBtn.setFont(new Font(null, Font.BOLD, 15));
        GBBtn.setBounds(1000, 26, 175, 36);
        panel2.add(GBBtn);

        confirmBtn.addActionListener(new myAction());
        GBBtn.addActionListener(new myAction());

        setBounds(100, 100, 1366, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setTitle("Online Shopping");
        setVisible(true);
    }

    public class myAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getActionCommand().equalsIgnoreCase("Confirm order")){

                if (cart.isEmpty()){
                    JOptionPane.showMessageDialog(null,"Your cart is empty, Add product to cart");

                }
                else {
                    if (JOptionPane.showConfirmDialog(null,"Are you sure you want to confirm order?","Sure?",JOptionPane.YES_NO_OPTION)==0){
                        JOptionPane.showMessageDialog(null,"Your order has been confirmed. Thank you for using our Online Shop.");
                        buyerBackend.truncateCart();
                        new MenuPage();
                        dispose();
                    }
                }
            }
            else if (e.getActionCommand().equalsIgnoreCase("Cancel Payment")){
                if (JOptionPane.showConfirmDialog(null,"Are you sure you want to cancel Payment?","Sure?",JOptionPane.YES_NO_OPTION)==0){
                    new MenuPage();
                    dispose();
                }
            }
        }
    }
}
