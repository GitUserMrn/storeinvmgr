import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

class P_LIST {
    private ArrayList<Product> p_List;
    private HashMap<String, Product> p_Map;
    private FileManager fileManager;
    public P_LIST() {
        p_List = new ArrayList<>();
        p_Map = new HashMap<>();
        fileManager = new FileManager();
    }
    public void addProduct(Product product) throws DuplicateProduct {
        if (p_Map.containsKey(product.getp_name())) {
            throw new DuplicateProduct(product.getp_name());}
        p_List.add(product);
        p_Map.put(product.getp_name(), product);
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
    public ArrayList<Product> getProducts() {
        return p_List; }  // for gui which is not ready YET
    public HashMap<String, Product> getP_Map() {
        return p_Map;}
}