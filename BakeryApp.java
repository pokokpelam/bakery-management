import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BakeryApp {
    public static void main(String[] args) {
        LinkedList bakeryList = new LinkedList(); // Create a new linked list for bakery orders
        Scanner scanner = new Scanner(System.in); // Create a Scanner object for user input

        // Load orders from a file
        loadOrdersFromFile(bakeryList, "bakeryList.txt");

        // Display the current orders for debugging
        System.out.println("Current orders in the bakery:");
        bakeryList.display();

        // Prompt the user for a customer name to search
        System.out.print("Enter the customer's name to search for: ");
        String customerName = scanner.nextLine().trim(); // Trim whitespace

        // Search for the order
        Bakery foundOrder = bakeryList.searchByCustomerName(customerName);
        if (foundOrder != null) {
            System.out.println("Found order: " + foundOrder);
        } else {
            System.out.println("No order found for " + customerName);
        }

        // Close the scanner
        scanner.close();
    }

    private static void loadOrdersFromFile(LinkedList bakeryList, String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader("bakeryList.txt"))){
               String line;
   
               while ((line = br.readLine()) != null) {
              
                   // Create a StringTokenizer to parse the line
                   StringTokenizer tokenizer = new StringTokenizer(line, ";");
                   
                   String mn = tokenizer.nextToken(); // Read the first token
                   char c = tokenizer.nextToken().charAt(0); // Read the first character of the second token
                   String cn = tokenizer.nextToken(); // Read the third token
                   int cpn = Integer.parseInt(tokenizer.nextToken()); // Parse integer from the fourth token
                   double p = Double.parseDouble(tokenizer.nextToken()); // Parse double from the fifth token
                   String d = tokenizer.nextToken(); // Read the sixth token
                   boolean ps = Boolean.parseBoolean(tokenizer.nextToken()); // Parse boolean from the seventh token
                      
                   // Create a new Item and add it to the linked list
                   Bakery bkr = new Bakery(mn, c, cn, cpn, p, d, ps);
                   bakeryList.insertAtFront(bkr);
               }        
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Error parsing price from the file.");
        }
    }
}