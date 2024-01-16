import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.SwingUtilities;

class P_LIST {
    private ArrayList<Product> p_List;
    private HashMap<String, Product> p_Map;
    private FileManager fileManager;
    private ExecutorService executorService = Executors.newFixedThreadPool(4);
    private ArrayList<User> users;

    class User {
        private String username;
        private String password;
        private String role;

        public User(String username, String password, String role) {
            this.username = username;
            this.password = password;
            this.role = role;
        }

        public String getUsername() {
            return username;
        }
        public String getPassword() {
            return password;
        }
        public String getRole() {
            return role;
        }

    }

    public P_LIST() {
        p_List=new ArrayList<>();
        p_Map= new HashMap<>();
        fileManager=new FileManager();
        users=new ArrayList<>();
        users.add(new User("admin", "123", "Admin"));
        users.add(new User("user", "123", "User"));
    }

    public void addProduct(Product product) throws DuplicateProduct {
        if (p_Map.containsKey(product.getp_name())) {
            throw new DuplicateProduct(product.getp_name());}
        p_List.add(product);
        p_Map.put(product.getp_name(), product);
    }
    public void addProductAsync(Product product){
        executorService.submit(() -> {
            try {
                addProduct(product);
            } catch (DuplicateProduct e) {
                e.printStackTrace();
            }
        });}
    public void removeProductAsync(String p_name, Runnable callback){
        executorService.submit(() -> {
            try {
                removeProduct(p_name);
                SwingUtilities.invokeLater(callback);
            } catch (MissingProduct e) {
                e.printStackTrace();
            }
        });

    }
    public void shutdownThreadPool() {
        executorService.shutdown();
    }

    public void removeProduct(String p_name) throws MissingProduct {
        Product removedProduct = p_Map.remove(p_name);
        if (removedProduct == null) {
            throw new MissingProduct(p_name);
        }
        p_List.remove(removedProduct);
    }
    public void displayProducts() {
        int i = 0;
        for (Product product : p_List) {
            System.out.println(i + ")" + product);
            i++;
        }
    }
    public void writeProductsFile() {
        try {
            fileManager.writeData(p_List);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void readProductsFile() {
        ArrayList<Product> products;
        try {
            products = fileManager.readData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (products != null) {
            p_List = products;
            p_Map.clear();
            for (Product product : p_List) {
                p_Map.put(product.getp_name(), product);
            }
        }
    }

    public void readProductsFromDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/trueprods";
            String username = "root";
            String password = "1234";

            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM prods");

            p_List.clear();
            p_Map.clear();

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String price = resultSet.getString("price");
                String quantity = resultSet.getString("quantity");

                Product product = new Product(id, name, price, quantity);
                p_List.add(product);
                p_Map.put(product.getp_name(), product);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeProductsToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/trueprods";
            String username = "root";
            String password = "1234";

            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM prods");

            for (Product product : p_List) {
                String query = String.format(
                        "INSERT INTO prods (name, price, quantity) VALUES ('%s', '%s', '%s')",
                        product.getp_name(), product.getp_price(), product.getp_quantity());

                statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
                ResultSet generatedKeys = statement.getGeneratedKeys();

                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    product.setId(id);}
            }

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void restartIdsInDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/trueprods";
            String username = "root";
            String password = "1234";
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE TABLE prods");// to restart the id
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Product> getProducts() {
        return p_List; }
    public HashMap<String, Product> getP_Map() {
        return p_Map;}
}