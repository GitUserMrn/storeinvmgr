import java.io.IOException;
import java.util.ArrayList;

interface Readable {
    ArrayList<Product> readData() throws IOException;
}