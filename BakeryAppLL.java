import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.File;
import java.awt.Dimension;

public class BakeryAppLL {
    public static void main(String[] args) {
        LinkedList bakeryList = new LinkedList(); // LinkedList declaration
        
        //STORE CUSTOMER ORDER DETAILS IN SYSTEM
        File file = new File("bakeryList.txt");
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
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
                  
                Bakery bkr = new Bakery(mn, c, cn, cpn, p, d, ps);
                bakeryList.insertAtFront(bkr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] options = {
            "X display all orders X", 
            "X search for order X", 
            "X paid/unpaid orders X", 
            "X remove an order X", 
            "X generate daily sales report X", 
            "X exit X"
        };
        int choice;

        do {
            choice = JOptionPane.showOptionDialog(null, "choose an option!", "<3 bakery management system <3",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0: // choice to display all orders
                   String initialOrders = "current list of orders:\n";
                   
                   initialOrders = bakeryList.display(); 
                   Node current = bakeryList.first;
                   
                   JTextArea textArea = new JTextArea();
                   textArea.setText(initialOrders); 
                   textArea.setEditable(false);
                   JScrollPane scrollPane = new JScrollPane(textArea);
                   scrollPane.setPreferredSize(new Dimension(400, 300));
                   JOptionPane.showMessageDialog(null, scrollPane, "ALL ORDERS", JOptionPane.INFORMATION_MESSAGE);
                   
                   break;

                case 1: // search for a specific order
                    String searchName = JOptionPane.showInputDialog("enter CUSTOMER NAME to SEARCH:");
                    Bakery foundOrder = bakeryList.searchByCustomerName(searchName);
                    if (foundOrder != null) {
                        JOptionPane.showMessageDialog(null, "1 ORDER FOUND FOR:\n" + foundOrder);
                    } else {
                        JOptionPane.showMessageDialog(null, "NO ORDER FOUND FOR " + searchName);
                    }
                    break;

                case 2: // Display paid/unpaid orders
                   String paymentChoice = JOptionPane.showInputDialog("would you like to see PAID (P) or UNPAID(UP) ORDERS?: ").toUpperCase();
                   String orders = " "; 
                   current = bakeryList.first; 
               
                   while (current != null) {
                       if ((paymentChoice.equals("P") && current.data.getPaymentStatus()) ||
                           (paymentChoice.equals("UP") && !current.data.getPaymentStatus())) {
                           orders += current.data + "\n";
                       }
                       current = current.next; 
                   }
               
                   String title; // Declare a variable for the title
                   if (paymentChoice.equals("P")) {
                       title = "PAID ORDERS:";
                   } 
                   else{
                       title = "UNPAID ORDERS:";
                   }
               
                   if (!orders.isEmpty()) { 
                       JOptionPane.showMessageDialog(null, title + "\n" + orders);
                   }
                   else{
                       JOptionPane.showMessageDialog(null, "NO ORDERS FOUND.");
                   }
                   
                   break;
                case 3: // remove order
                    String removeName = JOptionPane.showInputDialog("enter CUSTOMER NAME to REMOVE:");
                    if (bakeryList.removeOrder(removeName)) {
                        JOptionPane.showMessageDialog(null, "ORDER FOR " + removeName + " HAS BEEN REMOVED.");
                    } else {
                        JOptionPane.showMessageDialog(null, "NO ORDER FOUND FOR " + removeName + " TO REMOVE.");
                    }
                    break;

                case 4: // generate Daily Sales Report
                    double totalSales = bakeryList.generateTotalSales();
                    int size = bakeryList.size();
                    String orderDate;
                    
                        if (size > 0) {
                            orderDate = bakeryList.first.data.getOrderDate();
                        } 
                        else {
                            orderDate = "No orders available";
                        } 
                                
                    String report = "\n-----------------------------------------------------------------------------------" +
                                    "\nDAILY SALES REPORT" +
                                    "\n-----------------------------------------------------------------------------------" +
                                    "\nDate : " + orderDate + "\n" +
                                    "Total sales : RM" + totalSales + "\n" +
                                    "Numbers of orders : " + size;
                    JOptionPane.showMessageDialog(null, report);
                    break;

                case 5: // exit
                    JOptionPane.showMessageDialog(null, "<3 thank you for using bakery management system <3");
                    break;

                default:
                    break;
            }
        } while (choice != 5); // loop until user chooses exit
    }
}