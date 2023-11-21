import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IMain extends JFrame {
    private JButton displayButton;
    private JButton addButton;
    private JButton removeButton;

    private JTextArea productTextArea;

    private P_LIST p_list;

    public IMain() {
        setTitle("Gui Store");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(550, 800);
        setLocationRelativeTo(null);

        p_list = new P_LIST();

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {displayProducts();}
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeProduct();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(displayButton);
        panel.add(addButton);
        panel.add(removeButton);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(productTextArea), BorderLayout.CENTER);
        setVisible(true);
    }

    private void displayProducts() {
        productTextArea.setText(""); //clean the text before displaying products
        for (Product product : p_list.getProducts()) {
            productTextArea.append(product.toString() + "\n"); // this appends each product to the text box
        }
    }


    private void addProduct() {
        String name = JOptionPane.showInputDialog("Enter product name:");
        String price = JOptionPane.showInputDialog("Enter product price:");
        String quantity = JOptionPane.showInputDialog("Enter product quantity:");

        try {
            Product product = new Product(name, price, quantity);
            p_list.addProduct(product);
            JOptionPane.showMessageDialog(this, "Product added successfully.");
        } catch (DuplicateProduct e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Duplicate Product Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeProduct() {
        String name = JOptionPane.showInputDialog("Enter product name to remove:");
        try {
            p_list.removeProduct(name);
        } catch (MissingProduct missingProduct) {
            JOptionPane.showMessageDialog(this, "Product not found: " + missingProduct.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new IMain();
            }});
    }
}
