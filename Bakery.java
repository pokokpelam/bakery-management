public class Bakery
{
   private String menuName; // name of the menu item
   private char menuCategory; //i.e. P-pastries, D-drinks
   private String custName;
   private int custPhoneNo;
   private double orderPrice; 
   private String orderDate; // date in DD/MM/YYYY format
   private boolean paymentStatus; // payment completed-true, payment incomplete-false
   
   //normal constructor
   public Bakery (String mn, char c, String cn, int cpn, 
                  double p, String d, boolean ps)
   {
      menuName = mn;
      menuCategory = c;
      custName = cn;
      custPhoneNo = cpn;
      orderPrice = p;
      orderDate = d;
      paymentStatus = ps;
   }
   
   //accessors
   public String getMenuName(){return menuName;}
   public char getMenuCategory(){return menuCategory;}
   public String getCustName(){return custName;}
   public int getCustPhoneNo(){return custPhoneNo;}
   public double getOrderPrice(){return orderPrice;}
   public String getOrderDate(){return orderDate;}
   public boolean getPaymentStatus(){return paymentStatus;}
   
   public String statusPayment()
   {
      String status = " ";
      
      if (paymentStatus == true){
         status = "Payment Complete";
      }
      
      else if (paymentStatus == false){
         status = "Payment Incomplete";
      }
      
      return status;
   }
   
   public String categoryMenu()
   {
      String category = " ";
      
      if (menuCategory == 'P'){
         category = "Pastries";
      }
      
      else if (menuCategory == 'D'){
         category = "Drinks";
      }
      
      return category;
   }
   
   //printer 
   public String toString(){
       return 
       "\n-----------------------------------------------------------------------------------"
       +"\n ORDER INFORMATION"
       +"\n-----------------------------------------------------------------------------------"
       +"\n Name : "+getCustName()
       +"\n Phone number : "+getCustPhoneNo()
       +"\n Menu Item : "+getMenuName()
       +"\n Menu Category : "+categoryMenu()
       +"\n Price : RM"+String.format("%.2f", getOrderPrice())
       +"\n Date Ordered : "+getOrderDate()
       +"\n Payment Status : "+statusPayment()
       +"\n----------------------------------------------------------------------------------\n";
   }
}