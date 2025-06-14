import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.File;

public class BakeryApp1 {
    public static void main(String[] args) {
        

        LinkedList bakeryList = new LinkedList(); //linkedlist declaration
        
        // PROCESS 1: STORE CUSTOMER ORDER DETAILS IN SYSTEM
        Scanner sc = new Scanner(System.in);    
        File file = new File("bakeryList.txt");
        
        try (BufferedReader br = new BufferedReader(new FileReader("bakeryList.txt"))) {
        
            String line;
            while ((line = br.readLine()) != null) {
            
                StringTokenizer tokenizer = new StringTokenizer(line, ";");
                
                String mn = tokenizer.nextToken(); 
                char c = tokenizer.nextToken().charAt(0);
                String cn = tokenizer.nextToken(); 
                int cpn = Integer.parseInt(tokenizer.nextToken()); 
                double p = Double.parseDouble(tokenizer.nextToken()); 
                String d = tokenizer.nextToken();
                boolean ps = Boolean.parseBoolean(tokenizer.nextToken()); 
                  
                //create a new Bakery object and add to the linked list bakeryList
                Bakery bkr = new Bakery(mn, c, cn, cpn, p, d, ps);
                bakeryList.insertAtFront(bkr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Display all orders
        System.out.println("CURRENT LIST OF ORDERS:");
        bakeryList.display();
        
        
        // PROCESS 2: SEARCH FOR SPECIFIC ORDERS BY CUSTOMER NAME
        System.out.print("SEARCH FOR CUSTOMER NAME: ");
        String searchName = sc.nextLine();
        
        Bakery foundOrder = bakeryList.searchByCustomerName(searchName);
        
        if (foundOrder != null) {
            System.out.println("1 ORDER FOUND FOR " + foundOrder);
        }
        else {
            System.out.println("NO ORDER FOUND FOR " + searchName);
        }
        
        // PROCESS 3: DISPLAY LIST OF PAID AND UNPAID ORDERS
        System.out.print("WOULD YOU LIKE TO SEE PAID OR UNPAID ORDERS? (P - PAID & UP - UNPAID): ");
        String paymentChoice = sc.nextLine().toUpperCase(); // Convert to uppercase for consistency
        
        if (paymentChoice.equals("P")) {
            System.out.println("PAID ORDERS:");
        } 
        
        else {
            System.out.println("UNPAID ORDERS:");
        }
        
        Node current = bakeryList.first; 
        while (current != null) {
            if ((paymentChoice.equals("P") && current.data.getPaymentStatus()) ||
                (paymentChoice.equals("UP") && !current.data.getPaymentStatus())) {
                System.out.println(current.data); 
            }
            current = current.next; 
        }
        
        // PROCESS 4: REMOVE COMPLETED ORDERS AFTER PAYMENT SETTLEMENT
        System.out.print("ENTER CUSTOMER NAME TO REMOVE: ");
        String removeName = sc.nextLine();
        
        if (bakeryList.removeOrder(removeName)) {
            System.out.println("ORDER FOR " + removeName + " HAS BEEN REMOVED.");
        } else {
            System.out.println("NO ORDER FOUND FOR " + removeName + " TO REMOVE.");
        }

        // Display updated orders
        System.out.println("UPDATED LIST OF ORDERS: ");
        bakeryList.display();

        // PROCESS 5: GENERATE A DAILY SALES REPORT
        double totalSales = bakeryList.generateTotalSales();
        int size = bakeryList.size();
        String orderDate = bakeryList.first.data.getOrderDate();
        
        System.out.println("\n-----------------------------------------------------------------------------------"
                           +"\n DAILY SALES REPORT"
                           +"\n-----------------------------------------------------------------------------------"
                           + "\nDate              : " + orderDate 
                           + "\nTotal sales       : RM" + totalSales
                           + "\nNumbers of orders : " + size
                           +"\n-----------------------------------------------------------------------------------");
        
        sc.close();
    }
}