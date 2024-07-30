import java.util.*;

public class Main {
    public static void main(String[] args) {

        WestminsterShoppingManager westminsterShoppingManager = new WestminsterShoppingManager();
        Gui gui = new Gui();
        boolean quit = false;
        Scanner scanner = new Scanner(System.in);
        westminsterShoppingManager.loadUsersFromFile("userDataFile.txt");

        do {
            System.out.println();
            System.out.println("1. Add a new product");
            System.out.println("2. Delete a product");
            System.out.println("3. Print the list ");
            System.out.println("4. Save in a file");
            System.out.println("5. Load from a file");
            System.out.println("6. Go to the Gui ");
            System.out.println("7. Quit");
            System.out.println("8.test");
            System.out.println("Enter your choice");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice) {

                case 1:
                    System.out.println("Do you want to add Electric product or Clothing product? Enter 'E' for Electric, 'C' for Clothing: ");
                    String selection = scanner.nextLine();

                    if(selection.equals("E") || selection.equals("C")){

                        System.out.println("Enter product Id: ");
                        String proId = scanner.nextLine();

                        System.out.println("Enter product Name: ");
                        String proName = scanner.nextLine();

                        System.out.println("Enter available Items: ");
                        int proItems = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Enter Price: ");
                        double proPrice = scanner.nextDouble();
                        scanner.nextLine();

                        if(selection.equals("E")) {

                            String category="Electronics";

                            System.out.println("Enter your Brand: ");
                            String proBrand = scanner.nextLine();

                            System.out.println("Enter your Warranty period: ");
                            String proWarranty = scanner.nextLine();

                            Electronic electronic = new Electronic(proId, proName, proItems, proPrice, proBrand, proWarranty,category);
                            westminsterShoppingManager.addProduct(electronic);

                        }else if (selection.equals("C")) {

                            String category = "Clothing";

                            System.out.println("Enter your Size: ");
                            String proSize = scanner.nextLine();

                            System.out.println("Enter your Color: ");
                            String proColor = scanner.nextLine();

                            Clothing clothing = new Clothing(proId, proName, proItems, proPrice, proSize, proColor,category);
                            westminsterShoppingManager.addProduct(clothing);
                        }
                    }
                    else {
                        System.out.println("Incorrect option");
                    }
                    break;

                case 2:
                    System.out.println("Enter the Id of deleting product: ");
                    String deleteId = scanner.nextLine();
                    westminsterShoppingManager.deleteProduct(deleteId);
                    break;

                case 3:
                    westminsterShoppingManager.printList();
                    break;

                case 4:
                    System.out.println("Enter the filename to save the product list: ");
                    String filenameSave = scanner.nextLine();
                    westminsterShoppingManager.saveToFile(filenameSave);
                    westminsterShoppingManager.saveUsersToFile("userFile.txt");
                    break;

                case 5:
                    System.out.println("Enter the filename to load the product list: ");
                    String filenameLoad = scanner.nextLine();
                    westminsterShoppingManager.loadFromFile(filenameLoad);
                    westminsterShoppingManager.loadUsersFromFile("userFile.txt");
                    break;

                case 6:

                    System.out.println("Enter your User Name: ");
                    String systemUserName = scanner.nextLine();
                    System.out.println("Enter your Password: ");
                    String systemPassWord = scanner.nextLine();

                    User user = new User(systemUserName,systemPassWord);
                    User findUser = westminsterShoppingManager.getUser(user);

                    if(findUser==null){
                        westminsterShoppingManager.addUser(user);
                        gui.main(user);
                        System.out.println("New user created and logged in");
                    }
                    else{
                        gui.main(findUser);
                        System.out.println("Existing user logged in ");
                    }

                    break;

                case 7:
                    quit = true;
                    System.out.println("Quitting...");
                    westminsterShoppingManager.saveUsersToFile("userDataFile.txt");
                    break;

                case 8:
                    Test test = new Test();
                    test.
                    break;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }

        } while(!quit);

        scanner.close();
    }
}
