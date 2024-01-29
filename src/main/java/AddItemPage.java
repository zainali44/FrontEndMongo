import Logic.SellerBackend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddItemPage extends JFrame {

    private JPanel mainPanel, panel2;
    private JLabel itemLabel, itemPriceLabel,itemDescLabel, categName,logo, addLabel;
    private JTextField itemText, itemPriceText,itemDescText,categText;
    private JButton addBtn, GBBtn;
    private JSpinner jSpinnerCategory;

    SellerBackend sellerBackend;

    {
        try {
            sellerBackend = new SellerBackend();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    AddItemPage() {
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(80, 245, 140));
        mainPanel.setForeground(new Color(80, 245, 140));
        mainPanel.setBounds(0, 0, 1366, 101);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainPanel.setLayout(null);

        addLabel = new JLabel("ADD ITEM");
        addLabel.setBounds(600,50,350,60);
        addLabel.setFont(new Font(null, Font.PLAIN, 30));
        addLabel.setForeground(Color.BLACK);
        mainPanel.add(addLabel);

        GBBtn = new JButton("Go Back");
        GBBtn.setBounds(1150, 26, 100, 35);
        GBBtn.setFont(new Font(null,Font.BOLD,15));
        GBBtn.setBackground(Color.lightGray);
        GBBtn.setFocusable(false);
        mainPanel.add(GBBtn);

        panel2 = new JPanel();
        panel2.setBackground(new Color(80, 245, 140));
        panel2.setBounds(0, 100, 1366, 581);

        logo = new JLabel("");
        logo.setIcon(new ImageIcon("image.png"));
        logo.setBounds(780, 50, 500, 500);
        panel2.add(logo);
        panel2.setLayout(null);

        categName = new JLabel("Category Name");
        categName.setFont(new Font(null, Font.PLAIN, 15));
        categName.setBounds(411, 145, 120, 19);
        panel2.add(categName);


        jSpinnerCategory = new JSpinner();
        jSpinnerCategory.setModel(new SpinnerListModel(sellerBackend.getCategoryName()));
        jSpinnerCategory.setBounds(580, 145, 216, 20);
        panel2.add(jSpinnerCategory);

        itemLabel = new JLabel("Product Name");
        itemLabel.setFont(new Font(null, Font.PLAIN, 15));
        itemLabel.setBounds(411, 174, 100, 19);
        panel2.add(itemLabel);

        itemText = new JTextField();
        itemText.setColumns(10);
        itemText.setBounds(580, 175, 216, 20);
        panel2.add(itemText);


        itemPriceLabel = new JLabel("Product Price");
        itemPriceLabel.setFont(new Font(null, Font.PLAIN, 15));
        itemPriceLabel.setBounds(411, 205, 100, 19);
        panel2.add(itemPriceLabel);

        itemPriceText = new JTextField();
        itemPriceText.setColumns(10);
        itemPriceText.setBounds(580, 206, 216, 20);
        panel2.add(itemPriceText);

        itemDescLabel = new JLabel("Product Description");
        itemDescLabel.setFont(new Font(null, Font.PLAIN, 15));
        itemDescLabel.setBounds(411, 236, 120, 19);
        panel2.add(itemDescLabel);

        itemDescText = new JTextField();
        itemDescText.setColumns(10);
        itemDescText.setBounds(580, 237, 216, 20);
        panel2.add(itemDescText);

        addBtn = new JButton("ADD");
        addBtn.setFont(new Font(null, Font.BOLD, 15));
        addBtn.setBackground(Color.lightGray);
        addBtn.setBounds(550, 480, 100, 35);
        panel2.add(addBtn);
        addBtn.addActionListener(new myAction());
        GBBtn.addActionListener(new myAction());

        add(mainPanel);
        add(panel2);
        setTitle("Online Shopping");
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1366, 720);
    }

    public class myAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equalsIgnoreCase("Add")) {
                if (!(itemText.getText().isBlank() || itemPriceText.getText().isBlank() || itemDescText.getText().isBlank())) {
                    try {

                        int price = Integer.parseInt(itemPriceText.getText());
                        String productName = itemText.getText();
                        String productDesc = itemDescText.getText();
                        if (price >= 0 ) {
                            if (sellerBackend.addItem(productName,price,productDesc, String.valueOf(jSpinnerCategory.getValue()))){
                                JOptionPane.showMessageDialog(null, "Product Added Successfully");
                            }
                            else JOptionPane.showMessageDialog(null, "ERROR");
//
                        } else {
                            JOptionPane.showMessageDialog(null, "Product id or Product price cannot be negative", "Invalid input type", JOptionPane.WARNING_MESSAGE);

                        }
                    } catch (NumberFormatException el) {
                        JOptionPane.showMessageDialog(null, "Invalid input in Product id or Product price", "Invalid input type", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill all given fields", "Error", JOptionPane.WARNING_MESSAGE);

                }
                dispose();
                new SellerPage();
            }

            else if (e.getActionCommand().equalsIgnoreCase("Go Back")){
                if (JOptionPane.showConfirmDialog(null,"Are you sure you want to cancel? ","confirm",JOptionPane.YES_NO_OPTION)==0){
                    new SellerPage();
                    dispose();
                }
            }
        }
    }
}
