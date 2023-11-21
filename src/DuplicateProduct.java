public class DuplicateProduct extends Exception {
    public DuplicateProduct(String p_name) {
        super("Product " + p_name + " already exists.");
    }
}

