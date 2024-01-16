import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;

public class IMain extends JFrame {
    private JButton displayButton;
    private JButton addButton;
    private JButton removeButton;

    private JTextArea productTextArea;
    private JButton Load;

    private P_LIST p_list;

    boolean schema=false;
    private void readProductsDb() {
        p_list.getProducts().clear();
        p_list.getP_Map().clear();
        p_list.readProductsFromDatabase();
        displayProducts();}

    private void writeProductsDb() {
        p_list.writeProductsToDatabase();
    }


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
            public void actionPerformed(ActionEvent e){ addProduct();writeProductsDb();}
        });

        Load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {readProductsDb();}
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
        panel.add(Load);

        add(panel, BorderLayout.SOUTH);
        add(new JScrollPane(productTextArea), BorderLayout.CENTER);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmExit();
            }
        });
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private void readProductsFile() {  //asta nu mai
        ArrayList<Product> products;
        String filePath= "C:\\Users\\mtarn\\IdeaProjects\\p3_final\\p_list.txt";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            products = (ArrayList<Product>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this,"Not a Valid Product List"); //error if nvalid
            filePath = JOptionPane.showInputDialog("Please Enter a Valid File Path:");
            throw new RuntimeException(e);
        }

        if (!products.isEmpty()) {
            p_list.getProducts().clear();
            p_list.getProducts().addAll(products);
            p_list.getP_Map().clear();
            for (Product product : p_list.getProducts()) {
                p_list.getP_Map().put(product.getp_name(), product);
            }
            displayProducts();
        }
    }

    private void writeProductsFile() {
        // Specify the full path to your serialized file
        String filePath = "C:\\Users\\mtarn\\IdeaProjects\\p3_final\\p_list.txt";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            // Write the ArrayList<Product> to the file
            oos.writeObject(p_list.getProducts());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,"Save Was Not Successful");
            throw new RuntimeException(e);
        }
    }

    private void displayProducts() {
        productTextArea.setText(""); //clean the text before displaying products
        for (Product product : p_list.getProducts()) {
            productTextArea.append(product.toString() + "\n"); // this appends each product to the text box
        }
    }


    private void addProduct() {
        if(schema==true){dispose();p_list.restartIdsInDatabase();}
        else{
            String name;
            do {
                name=JOptionPane.showInputDialog("Enter product name:");
                if (name == null) {
                    return;
                }
                if (name.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Product name is empty.");
                }
            } while (name.trim().isEmpty());
            String price;
            do {
                price = JOptionPane.showInputDialog("Enter product price");
                if (price == null) {
                    return;
                }
                if (!price.matches("\\d+(\\.\\d+)?")) {
                    JOptionPane.showMessageDialog(this, "Not valid price");
                }
            } while (!price.matches("\\d+(\\.\\d+)?"));

            String quantity;
            do {
                quantity = JOptionPane.showInputDialog("Enter quantity");
                if (!quantity.matches("\\d+")) {
                    JOptionPane.showMessageDialog(this, "Not valid quantity");
                }
            } while (!quantity.matches("\\d+"));
            Integer id=null;
            try {
                Product product = new Product(id, name, price, quantity);
                p_list.addProduct(product);
                JOptionPane.showMessageDialog(this, "Product added successfully.");
            } catch (DuplicateProduct e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Duplicate Product Error", JOptionPane.ERROR_MESSAGE);
            }
            productTextArea.setText("");
            displayProducts();
            p_list.restartIdsInDatabase();
        }}

    private void removeProduct() {
        String name = JOptionPane.showInputDialog("Enter product name to remove:");
        try {
            p_list.removeProduct(name);
        } catch (MissingProduct missingProduct) {
            JOptionPane.showMessageDialog(this, "Product not found: " + missingProduct.getMessage());
        }
        productTextArea.setText("");
        displayProducts();
    }

    private void confirmExit() {
        int result=JOptionPane.showConfirmDialog(this,
                "Would you like to save before exiting?", "Exit Confir",
                JOptionPane.YES_NO_CANCEL_OPTION);

        if (result==JOptionPane.YES_OPTION) {
            schema=true;
            addProduct();
            writeProductsDb();
            dispose();
        }else if (result==JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(this, "Exiting...");
            dispose();
        }

        else if (result==JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
            return;
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