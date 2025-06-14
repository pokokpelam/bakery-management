import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.File;
import java.awt.Dimension;

public class BakeryAppQueue {
    public static void main(String[] args) {
        Queue<Bakery> bakeryQueue = new Queue(); // Queue declaration
        
        // PROCESS 1: STORE CUSTOMER ORDER DETAILS IN SYSTEM
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
                bakeryQueue.enqueue(bkr); // Enqueue order to the queue
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] options = {
            "X Display All Orders X", 
            "X Search for Order X", 
            "X Paid/Unpaid Orders X", 
            "X Remove an Order X", 
            "X Generate Daily Sales Report X", 
            "X Exit X"
        };
        int choice;

        do {
            choice = JOptionPane.showOptionDialog(null, "Choose an option!", "<3 Bakery Management System <3",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0: // Display all orders
                    StringBuilder initialOrders = new StringBuilder("Current list of orders:\n");
                    
                    // Use a temporary queue to display orders without losing them
                    Queue<Bakery> tempQueue = new Queue<>();
                    
                    while (!bakeryQueue.isEmpty()) {
                        Bakery order = bakeryQueue.dequeue();
                        initialOrders.append(order).append("\n");
                        tempQueue.enqueue(order); // Store back in temp queue
                    }
                    
                    // Restore original queue from temporary queue
                    while (!tempQueue.isEmpty()) {
                        bakeryQueue.enqueue(tempQueue.dequeue());
                    }
                    
                    JTextArea textArea = new JTextArea();
                    textArea.setText(initialOrders.toString());
                    textArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(400, 300));
                    JOptionPane.showMessageDialog(null, scrollPane, "ALL ORDERS", JOptionPane.INFORMATION_MESSAGE);
                    
                    break;

                case 1: // Search for a specific order
                    String searchName = JOptionPane.showInputDialog("Enter CUSTOMER NAME to SEARCH:");
                    Bakery foundOrder = null;
                    
                    // Use a temporary queue for searching
                    tempQueue = new Queue<>();
                    
                    while (!bakeryQueue.isEmpty()) {
                        Bakery order = bakeryQueue.dequeue();
                        if (order.getCustName().equalsIgnoreCase(searchName)) {
                            foundOrder = order; // Found the order
                        }
                        tempQueue.enqueue(order); // Keep in temporary queue
                    }
                    
                    // Restore original queue from temporary queue
                    while (!tempQueue.isEmpty()) {
                        bakeryQueue.enqueue(tempQueue.dequeue());
                    }

                    if (foundOrder != null) {
                        JOptionPane.showMessageDialog(null, "1 ORDER FOUND FOR:\n" + foundOrder);
                    } else {
                        JOptionPane.showMessageDialog(null, "NO ORDER FOUND FOR " + searchName);
                    }
                    break;

                case 2: // Display paid/unpaid orders
                   String paymentChoice = JOptionPane.showInputDialog("Would you like to see PAID (P) or UNPAID(UP) ORDERS?: ").toUpperCase();
                   StringBuilder orders = new StringBuilder(); 
                   tempQueue = new Queue<>(); 
               
                   while (!bakeryQueue.isEmpty()) {
                       Bakery currentOrder = bakeryQueue.dequeue();
                       if ((paymentChoice.equals("P") && currentOrder.getPaymentStatus()) ||
                           (paymentChoice.equals("UP") && !currentOrder.getPaymentStatus())) {
                           orders.append(currentOrder).append("\n");
                       }
                       tempQueue.enqueue(currentOrder); // Keep in temporary queue
                   }
               
                   // Restore original queue from temporary queue
                   while (!tempQueue.isEmpty()) {
                       bakeryQueue.enqueue(tempQueue.dequeue());
                   }
               
                   String title; 
                   if (paymentChoice.equals("P")) {
                       title = "PAID ORDERS:";
                   } else {
                       title = "UNPAID ORDERS:";
                   }
               
                   if (orders.length() > 0) { 
                       JOptionPane.showMessageDialog(null, title + "\n" + orders);
                   } else {
                       JOptionPane.showMessageDialog(null, "NO ORDERS FOUND.");
                   }
                   
                   break;

                case 3: // Remove order
                    String removeName = JOptionPane.showInputDialog("Enter CUSTOMER NAME to REMOVE:");
                    boolean removed = false;
                    
                    tempQueue = new Queue<>(); 

                    while (!bakeryQueue.isEmpty()) {
                        Bakery currentOrder = bakeryQueue.dequeue();
                        if (currentOrder.getCustName().equalsIgnoreCase(removeName)) {
                            removed = true; // Mark as removed
                        } else {
                            tempQueue.enqueue(currentOrder); // Keep in temporary queue
                        }
                    }

                    // Restore original queue from temporary queue
                    while (!tempQueue.isEmpty()) {
                        bakeryQueue.enqueue(tempQueue.dequeue());
                    }

                    if (removed) {
                        JOptionPane.showMessageDialog(null, "ORDER FOR " + removeName + " HAS BEEN REMOVED.");
                    } else {
                        JOptionPane.showMessageDialog(null, "NO ORDER FOUND FOR " + removeName + " TO REMOVE.");
                    }
                    
                    break;

                case 4: // Generate Daily Sales Report
                    double totalSales = 0.0;
                    int size = 0;
                    
                    tempQueue = new Queue<>(); 

                    while (!bakeryQueue.isEmpty()) {
                        Bakery currentOrder = bakeryQueue.dequeue();
                        totalSales += currentOrder.getOrderPrice(); // Sum up the order prices
                        size++;
                        tempQueue.enqueue(currentOrder); // Keep in temporary queue
                    }

                    // Restore original queue from temporary queue
                    while (!tempQueue.isEmpty()) {
                        bakeryQueue.enqueue(tempQueue.dequeue());
                    }

                    String orderDate;
                    
                    if (size > 0) {
                        orderDate = bakeryQueue.front().getOrderDate();
                    } else {
                        orderDate = "No orders available";
                    } 
                                
                    String report =
                                    "\n-----------------------------------------------------------------------------------" +
                                    "\nDAILY SALES REPORT" +
                                    "\n-----------------------------------------------------------------------------------" +
                                    "\nDate : " + orderDate + "\n" +
                                    "Total sales : RM" + totalSales + "\n" +
                                    "Numbers of orders : " + size;
                    
                    JOptionPane.showMessageDialog(null, report);
                    
                    break;

                case 5: // Exit
                    JOptionPane.showMessageDialog(null, "<3 Thank you for using Bakery Management System <3");
                    break;

                default:
                    break;
            }
        } while (choice != 5); // Loop until user chooses exit
    }
}
