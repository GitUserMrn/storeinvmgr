import java.util.ArrayList; //nomore useful
import java.io.*;

class FileManager implements Readable, Writeable {
    private final String filePath = "p_list.txt";

    @Override
    public ArrayList<Product> readData() throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (ArrayList<Product>) ois.readObject();
        } catch (ClassNotFoundException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        throw new IOException("Error reading file: Class not found."); //handle crash
    }

    @Override
    public void writeData(ArrayList<Product> products) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(products);
            System.out.println("Data saved successfully.");
        }
    }
}