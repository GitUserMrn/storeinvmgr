import org.junit.Test;
import static org.junit.Assert.*;

public class ProductTest {
    @Test
    public void testConstructorAndGetters() {
        Integer id=1;
        String name="TestProduct";
        String price="10.01";
        String quantity="5";
        Product product=new Product(id, name, price, quantity);


        assertEquals((int)id, product.get_id());
        assertEquals(name, product.getp_name());
        assertEquals(price, product.getp_price());
        assertEquals(quantity, product.getp_quantity());
    }

    @Test
    public void testToString() {
        Integer id=1;
        String name="TestProduct";
        String price="10.00";
        String quantity="5";
        Product product=new Product(id, name, price, quantity);
        String expectedString=id + ")Product: " + name + "| " + price + " ron | x" + quantity;
        assertEquals(expectedString, product.toString());
    }
    @Test
    public void testSetId() {
        Product product=new Product(null,"TestProduct","10.00","5");
        assertEquals(null, product.get_id());
        int newId=1;
        product.setId(newId);
        assertEquals(newId, (int)product.get_id());}
}