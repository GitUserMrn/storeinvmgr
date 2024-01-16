import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class P_LISTTest {

    private P_LIST pList;

    @Before
    public void setUp() {
        pList=new P_LIST();  //p,list instance before each test
    }

    @Test
    public void addProductTest() { //ut
        try {
            Product milk=new Product(1, "Milk", "9", "53");
            pList.addProduct(milk);
            assertTrue(pList.getP_Map().containsKey("Milk")); ///Check if in the map using the getter
            assertEquals(milk, pList.getP_Map().get("Milk"));

        } catch (DuplicateProduct e) {
            fail("Exception not expected: " + e.getMessage());
        }
    }


    @Test(expected=DuplicateProduct.class)  //ft
    public void addDuplicateProductTest() throws DuplicateProduct {
        Product milk=new Product(1, "Milk", "9", "53");
        pList.addProduct(milk);
        pList.addProduct(milk);
    }


    @Test
    public void removeExistingProductTest() {
        try {
            Product milk=new Product(1, "Milk", "9", "53");
            pList.addProduct(milk);
            pList.removeProduct("Milk");
            assertFalse(pList.getP_Map().containsKey("Milk"));

        } catch (MissingProduct | DuplicateProduct e) {
            fail("Exception not expected: " + e.getMessage());
        }
    }

    @Test(expected=MissingProduct.class)
    public void removeNonExistingProductTest() throws MissingProduct {
        pList.removeProduct("NonExistingProduct");
    }

    @Test
    public void writeAndReadProductsListTest() {
        P_LIST pList=new P_LIST();
        Product milk=new Product(1, "Milk", "9", "53");
        pList.addProductAsync(milk);
        pList.writeProductsFile();
        P_LIST readList=new P_LIST();
        readList.readProductsFile();


        assertEquals(pList.getP_Map().size(), readList.getP_Map().size());
        assertTrue(readList.getP_Map().containsKey("Milk"));
        Product readmilk=readList.getP_Map().get("Milk");

        assertEquals(milk.getp_name(), readmilk.getp_name());
        assertEquals(milk.getp_price(), readmilk.getp_price());
        assertEquals(milk.getp_quantity(), readmilk.getp_quantity());
    }

}