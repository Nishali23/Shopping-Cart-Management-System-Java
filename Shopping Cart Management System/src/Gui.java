import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Gui {
    public static void main(User user) {
        SwingUtilities.invokeLater(() -> {
            MyFrame frame = new MyFrame(user);
            frame.setTitle("Westminster Shopping Center");
            frame.setSize(700, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    public static class MyFrame extends JFrame {

        private User user;
        JLabel label, productDetailsLabel;
        JComboBox<String> comboBox;
        JButton shoppingCart, addToCartButton;
        JTable table;
        DefaultTableModel tableModel;
        JPanel topPanel, detailsPanel;
        JScrollPane tableScrollPane;
        WestminsterShoppingManager manager;
        ShoppingCart shoppingCartInstance;
        ShoppingCartFrame shoppingCartFrame;

        MyFrame(User user) {

            this.user=user;
            manager = new WestminsterShoppingManager();
            shoppingCartInstance = user.getShoppingCart();

            String[] categories = {"All", "Electronics", "Clothing"};
            String[] columnNames = {"Product ID", "Name", "Category", "Price", "Info"};

            label = new JLabel("Select product Category");
            comboBox = new JComboBox<>(categories);
            shoppingCart = new JButton("Shopping Cart");

            tableModel = new DefaultTableModel(columnNames, 0);
            table = new JTable(tableModel);

            // Custom cell renderer to add margin and highlight low availability
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if (component instanceof JLabel) {
                        JLabel label = (JLabel) component;
                        label.setBorder(new EmptyBorder(5, 5, 5, 5)); // Add padding

                        // Check the availability and highlight the row if needed
                        if (column == 0) { // Assuming Product ID is in column 0
                            String productId = (String) value;
                            Product product = getProductById(productId);
                            if (product != null && product.getAvailableItems() < 3) {
                                component.setBackground(Color.RED);
                            } else {
                                component.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                            }
                        } else {
                            component.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                        }
                    }
                    return component;
                }
            };
            table.setDefaultRenderer(Object.class, cellRenderer);

            updateTableData("All");

            productDetailsLabel = new JLabel();

            addToCartButton = new JButton("Add to Cart");
            addToCartButton.setPreferredSize(new Dimension(120, 25));

            detailsPanel = new JPanel();
            detailsPanel.setLayout(new BorderLayout());
            detailsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

            detailsPanel.add(productDetailsLabel, BorderLayout.CENTER);
            detailsPanel.add(addToCartButton, BorderLayout.SOUTH);

            detailsPanel.setVisible(false);

            this.setLayout(new BorderLayout());
            topPanel = new JPanel(new FlowLayout());
            topPanel.add(label);
            topPanel.add(comboBox);
            topPanel.add(shoppingCart);

            tableScrollPane = new JScrollPane(table);

            this.add(topPanel, BorderLayout.NORTH);
            this.add(tableScrollPane, BorderLayout.CENTER);
            this.add(detailsPanel, BorderLayout.SOUTH);

            comboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedCategory = (String) comboBox.getSelectedItem();
                    updateTableData(selectedCategory);
                }
            });

            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0) {
                        String productId = (String) tableModel.getValueAt(selectedRow, 0);
                        Product selectedProduct = getProductById(productId);

                        if (selectedProduct != null) {
                            StringBuilder details = new StringBuilder("<html><b><font size='4'>Selected Product - Details:</font></b><br><br>");
                            details.append("Product ID: ").append(selectedProduct.getProductId()).append("<br><br>");
                            details.append("Category: ").append(tableModel.getValueAt(selectedRow, 2)).append("<br><br>");
                            details.append("Name: ").append(selectedProduct.getProductName()).append("<br><br>");

                            if (selectedProduct instanceof Electronic) {
                                Electronic electronic = (Electronic) selectedProduct;
                                details.append("Brand: ").append(electronic.getBrand()).append("<br><br>");
                                details.append("Warranty Period: ").append(electronic.getWarrantyPeriod()).append("<br><br>");
                            } else if (selectedProduct instanceof Clothing) {
                                Clothing clothing = (Clothing) selectedProduct;
                                details.append("Size: ").append(clothing.getSize()).append("<br><br>");
                                details.append("Color: ").append(clothing.getColor()).append("<br><br>");
                            }

                            details.append("Items Available: ").append(selectedProduct.getAvailableItems()).append("<br><br>");
                            details.append("</html>");
                            productDetailsLabel.setText(details.toString());
                            detailsPanel.setVisible(true);

                            // Ensure only one action listener is added to the "Add to Cart" button
                            for (ActionListener al : addToCartButton.getActionListeners()) {
                                addToCartButton.removeActionListener(al);
                            }

                            addToCartButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (selectedProduct.getAvailableItems() > 0) {
                                        selectedProduct.setAvailableItems(selectedProduct.getAvailableItems() - 1);
                                        updateProductDetails(selectedProduct);
                                        updateTableData((String) comboBox.getSelectedItem());
                                        shoppingCartInstance.addProduct(selectedProduct); // Add product to shopping cart
                                        if (shoppingCartFrame != null) {
                                            shoppingCartFrame.updateTable(); // Update the shopping cart frame
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(null, "No more items available for this product.", "Out of Stock", JOptionPane.WARNING_MESSAGE);
                                    }
                                }
                            });
                        }
                    }
                }
            });

            shoppingCart.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (shoppingCartFrame == null) {
                        shoppingCartFrame = new ShoppingCartFrame(shoppingCartInstance,user);
                    }
                    shoppingCartFrame.setVisible(true);
                    shoppingCartFrame.updateTable(); // Update the table when opening the cart
                }
            });

        }

        private Product getProductById(String productId) {
            List<Product> products = manager.getProductList();
            for (Product product : products) {
                if (product.getProductId().equals(productId)) {
                    return product;
                }
            }
            return null;
        }

        private void updateTableData(String category) {
            List<Product> products = manager.getProductList();
            tableModel.setRowCount(0);

            for (Product product : products) {
                if (category.equals("All") || (category.equals("Electronics") && product instanceof Electronic)
                        || (category.equals("Clothing") && product instanceof Clothing)) {

                    String info;
                    if (product instanceof Electronic) {
                        Electronic electronic = (Electronic) product;
                        info = electronic.getBrand() + " , " + electronic.getWarrantyPeriod() + " Warranty ";
                    } else {
                        Clothing clothing = (Clothing) product;
                        info = clothing.getSize() + " , " + clothing.getColor();
                    }

                    tableModel.addRow(new Object[]{
                            product.getProductId(),
                            product.getProductName(),
                            product instanceof Electronic ? "Electronics" : "Clothing",
                            product.getPrice(),
                            info
                    });
                }
            }
        }

        public void updateProductDetails(Product product) {
            StringBuilder details = new StringBuilder("<html><b><font size='4'>Selected Product - Details:</font></b><br><br>");
            details.append("Product ID: ").append(product.getProductId()).append("<br><br>");
            details.append("Category: ").append(product instanceof Electronic ? "Electronics" : "Clothing").append("<br><br>");
            details.append("Name: ").append(product.getProductName()).append("<br><br>");

            if (product instanceof Electronic) {
                Electronic electronic = (Electronic) product;
                details.append("Brand: ").append(electronic.getBrand()).append("<br><br>");
                details.append("Warranty Period: ").append(electronic.getWarrantyPeriod()).append("<br><br>");
            } else if (product instanceof Clothing) {
                Clothing clothing = (Clothing) product;
                details.append("Size: ").append(clothing.getSize()).append("<br><br>");
                details.append("Color: ").append(clothing.getColor()).append("<br><br>");
            }

            details.append("Items Available: ").append(product.getAvailableItems()).append("<br><br>");
            details.append("</html>");
            productDetailsLabel.setText(details.toString());
        }
    }
}
