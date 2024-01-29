import Logic.BuyerBackend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
//import src/Buyer/BuyerBackend.java;

public class MenuPage extends JFrame {

    private DefaultListModel model;
    private JList list;


    public BuyerBackend  buyerBackend;

    {
        try {
            buyerBackend = new BuyerBackend();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    JPanel panel = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JLabel deliverTo = new JLabel("DELIVERING TO :");
    JLabel categoryList = new JLabel("CATEGORY LIST");
    TextField toWhomDeliver = new TextField();
    JButton cartBtn = new JButton("Cart");
    JButton logOutBtn = new JButton("Logout");
    JButton helpBtn = new JButton("Help Chat");
    
    MenuPage() {

        panel.setBackground(Color.WHITE);
        panel.setBounds(0, 0, 1366, 76);
        panel.setLayout(null);


        deliverTo.setFont(new Font(null, Font.PLAIN, 15));
        deliverTo.setBounds(400, 46, 127, 19);
        panel.add(deliverTo);

        toWhomDeliver.setForeground(Color.BLACK);
        toWhomDeliver.setBackground(Color.WHITE);
        toWhomDeliver.setEditable(false);
//        toWhomDeliver.setText(u.getFirstName()+" "+u.getLastName()+" | "+u.getAddress());
        toWhomDeliver.setText(buyerBackend.getDeliveryAddress());

        toWhomDeliver.setBounds(533, 43, 580, 22);
        panel.add(toWhomDeliver);
        add(panel);

        panel2.setBackground(new Color(80, 245, 140));
        panel2.setBounds(0, 76, 1366, 86);
        panel2.setLayout(null);

        cartBtn.setBackground(Color.lightGray);
        cartBtn.setFont(new Font(null, Font.BOLD, 15));
        cartBtn.setBounds(1150,20,125,30);
        cartBtn.setFocusable(false);
        panel.add(cartBtn);

        helpBtn.setBounds(1000, 26, 125, 30);
        helpBtn.setFont(new Font(null, Font.BOLD, 15));
        helpBtn.setBackground(Color.lightGray);
        helpBtn.setFocusable(false);
        panel2.add(helpBtn);
        add(panel2);

        categoryList.setFont(new Font(null, Font.PLAIN, 26));
        categoryList.setBounds(75, 38, 350, 24);
        panel2.add(categoryList);

        logOutBtn.setBounds(1150, 26, 125, 30);
        logOutBtn.setFont(new Font(null,Font.BOLD, 15));
        logOutBtn.setBackground(Color.lightGray);
        logOutBtn.setFocusable(false);
        panel2.add(logOutBtn);
        add(panel2);

        ArrayList<String> categories = buyerBackend.getCategoryName();
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
        list.addMouseListener(new myAction());

        myAction action= new myAction();

        logOutBtn.addActionListener(action);
        cartBtn.addActionListener(action);
        helpBtn.addActionListener(action);

        panel3.setLayout(null);
        panel3.setBounds(0, 160, 1366, 521);

        panel3.add(scrollPane);
        add(panel3);
        setVisible(true);
        setResizable(false);
        setTitle("Online Shopping");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1366, 720);
    }


    class myAction implements MouseListener, ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getActionCommand().equalsIgnoreCase("help chat")){
                new HelpChatPage();
                dispose();
            }

            if(e.getActionCommand().equalsIgnoreCase("logout")){
                if(JOptionPane.showConfirmDialog(null,"Are you sure you want to logout?","Are you sure",JOptionPane.YES_NO_OPTION)==0){
                    new LoginPage();
                    dispose();
                }

            }
            else if (e.getActionCommand().equalsIgnoreCase("cart")){
                new CartAreaPage();
                dispose();
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int A = list.getSelectedIndex();
            ArrayList<String> categories = buyerBackend.getCategoryName();

            for (int i = 0; i < categories.size() ; i++) {
                if (categories.get(i).equalsIgnoreCase(model.get(A).toString())){
                    new CategoryDetailsTwoPage(categories.get(i));
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
