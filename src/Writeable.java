import java.util.ArrayList;
import java.io.*;

interface Writeable {
    void writeData(ArrayList<Product> products) throws IOException;
}