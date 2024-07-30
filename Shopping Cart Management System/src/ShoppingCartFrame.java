import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;




public class ShoppingCartFrame extends JFrame {

    private User user;
    private ShoppingCart shoppingCart;
    private DefaultTableModel tableModel;
    private JLabel totalPriceLabel,discountLabel,finalTotalLabel,newUserDiscountLabel;
    private JButton purchaseButton;



    public ShoppingCartFrame(ShoppingCart shoppingCart,User user) {
        this.user=user;
        this.shoppingCart = shoppingCart;
        setTitle("Shopping Cart");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnNames = {"Product", "Quantity", "Price"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable cartTable = new JTable(tableModel);

        // Custom cell renderer to add padding
        cartTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                ((JLabel) c).setText((value == null) ? "" : value.toString());
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(cartTable);
        add(scrollPane, BorderLayout.CENTER);

        // Create panel for price details
        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.Y_AXIS));
        pricePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        totalPriceLabel = new JLabel("Total: $0.00");
        discountLabel = new JLabel("Three items in same category discount (20%): $0.00");
        newUserDiscountLabel = new JLabel("10% discount for the very first purchase : $0.00");
        finalTotalLabel = new JLabel("Final total: $0.00");
        purchaseButton = new JButton("Purchase");

        pricePanel.add(totalPriceLabel);
        pricePanel.add(discountLabel);
        pricePanel.add(newUserDiscountLabel);
        pricePanel.add(finalTotalLabel);
        pricePanel.add(purchaseButton);

        add(pricePanel, BorderLayout.SOUTH);

        updateTable();

        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.setIsFirstPurchase(false);
                shoppingCart.clear();
                System.out.println(user.getIsFirstPurchase());
            }
        });
    }



    public void updateTable() {
        tableModel.setRowCount(0); // Clear existing rows
        for (Map.Entry<Product, Integer> entry : shoppingCart.getProducts().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double price = product.getPrice() * quantity;

            // Determine product info based on type
            String info;
            if (product instanceof Electronic) {
                Electronic electronic = (Electronic) product;
                info = electronic.getBrand() + " , " + electronic.getWarrantyPeriod() + " Warranty";
            } else if (product instanceof Clothing) {
                Clothing clothing = (Clothing) product;
                info = clothing.getSize() + " , " + clothing.getColor();
            } else {
                info = "Unknown Product Type";
            }

            // Create formatted product details
            String productDetails = product.getProductId() + " , " + product.getProductName() + " , " + info;

            tableModel.addRow(new Object[]{productDetails, quantity, price});
        }

        // Update the total price, discount, and final total labels
        double totalPrice = shoppingCart.calculateTotal();
        double discount = shoppingCart.calculateDiscount();

        double finalTotal;
        double newUserDiscount = 0;

        if(user.getIsFirstPurchase()){
            newUserDiscount=shoppingCart.calculateTotal()*0.10;
            finalTotal = shoppingCart.calculateFinalTotal()-newUserDiscount;

        }
        else {
            finalTotal = shoppingCart.calculateFinalTotal();
        }

        totalPriceLabel.setText("Total: " + String.format("%.2f", totalPrice));
        discountLabel.setText("Three items in same category discount (20%): " + String.format("%.2f", discount));
        newUserDiscountLabel.setText("10% discount for the very first purchase : "+String.format("%.2f", newUserDiscount));
        finalTotalLabel.setText("Final total: " + String.format("%.2f", finalTotal));
    }


}

