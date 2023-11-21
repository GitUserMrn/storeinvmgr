import java.io.*;

class Product implements Serializable {
    private String p_name;
    private String p_price;
    private String p_quantity;

    public Product(String p_name, String p_price, String p_quantity) {
        this.p_name = p_name;
        this.p_price = p_price;
        this.p_quantity = p_quantity;
    }

    public String getp_name() {
        return p_name;
    }

    public String getp_price() {
        return p_price;
    }

    public String getp_quantity() {
        return p_quantity;
    }

    @Override
    public String toString() {
        return "Product: " + p_name + "| " + p_price +" ron | x" + p_quantity;
    }
}