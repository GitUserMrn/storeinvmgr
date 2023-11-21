import java.util.Scanner;

public class StoreInventory {
    public static void main(String[] args) {
        P_LIST p_list = new P_LIST();
        int choice = 0;
        int choice1;
        int verif_save=0;
        if (args.length != 0) {
            if(args[0].equals("load")){
                // read prods from file by using program arguments
                p_list.readProductsFile();
            }
        }
        while (choice != 99) {
            Scanner myObj = new Scanner(System.in);
            System.out.print("\nPlease choose:\n" +
                    "1.Read From File\n" +
                    "2.Write to file\n" +
                    "3.Add product\n" +
                    "4.Remove product\n" +
                    "5.Display products\n" +
                    "99.Terminate\n> ");
            choice = myObj.nextInt();
            switch (choice) {
                case 1:
                    p_list.readProductsFile();
                    System.out.println("\nSuccessfully read the data\n");
                    System.out.println("\nAlso show the products?\n" +
                            "1)Y\n" +
                            "2)N\n");
                    choice1 = myObj.nextInt();
                    switch(choice1) {
                        case 1:
                            p_list.displayProducts();
                            break;
                        case 2:
                            break;
                        default:
                            System.out.println("\nEnter valid choice");
                            break;
                    }
                    break;
                case 2:
                    p_list.writeProductsFile();
                    System.out.println("\nSuccessfully saved the data\n");
                    verif_save=1;
                    break;
                case 3:
                    myObj = new Scanner(System.in);

                    String name;
                    do {
                        System.out.print("\nEnter the name of the product:\n> ");
                        name = myObj.nextLine();
                        if (name.trim().isEmpty()) {
                            System.out.println("\nerror: product name can't be empty");
                        }
                    } while (name.trim().isEmpty());

                    String price;
                    do {
                        System.out.print("\nEnter the price of the product\n> ");
                        price = myObj.nextLine();
                        if (!price.matches("\\d+(\\.\\d+)?")) {
                            System.out.println("\nerror: Invalid price");
                        }
                    } while (!price.matches("\\d+(\\.\\d+)?"));

                    String quantity;
                    do {
                        System.out.print("\nEnter the quantity:\n> ");
                        quantity = myObj.nextLine();
                        if (!quantity.matches("\\d+")) {
                            System.out.println("error: Invalid quantity");
                        }
                    } while (!quantity.matches("\\d+"));

                    Product product1 = new Product(name, price, quantity);
                    try {
                        p_list.addProduct(product1);
                        System.out.println("Product added successfully.");
                    } catch (DuplicateProduct e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 4:
                    myObj = new Scanner(System.in);
                    System.out.print("\nEnter the product's name:\n> ");
                    String delete = myObj.nextLine();
                    try {
                        p_list.removeProduct(delete);
                    } catch (MissingProduct e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 5:
                    System.out.println("\nAvailable Products:");
                    p_list.displayProducts();
                    break;
                case 99:
                    if(verif_save==0){
                        System.out.println("\nWould you like to save before exiting?\n" +
                                "1)Y\n" +
                                "2)N\n");
                        choice1 = myObj.nextInt();
                        switch(choice1) {
                            case 1:
                                p_list.writeProductsFile();
                                break;
                            default:
                                break;
                        }
                    }
                    break;
                default:
                    System.out.println("\nPlease add a valid choice");
            }
        }
    }
}