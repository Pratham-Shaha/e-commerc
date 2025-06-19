import java.sql.*;
import java.util.*;
public class Main
{
    public static void main(String[] args) throws ClassNotFoundException
    {
        String name;
        String url="jdbc:mysql://localhost:3306/Shopping_Cart";
        String username="root";
        String password="pratham@b38";
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        try
        {
            Connection con = DriverManager.getConnection(url, username, password);
            //Using con we establish connection with mysql, It holds that particular connection
            Statement stmt = con.createStatement();
            Scanner sc = new Scanner(System.in);
            System.out.println("Choose the method to interact :- \n1)Shopkeeper \n2)Customer");
            int choice=sc.nextInt();
            if(choice==1)
            {
                while(true)
                {
                    System.out.println("Enter operations to perform :- ");
                    System.out.println("1)View Items");
                    System.out.println("2)Insert New Item");
                    System.out.println("3)Delete Item ");
                    System.out.println("4)Update The Price");
                    System.out.println("0)Exit");
                    int ch = sc.nextInt();
                    switch(ch)
                    {
                        case 1:
                                View_Items(stmt);
                                break;
                         case 2:
                                Insert_Items(sc,stmt);
                                break;
                          case 3:
                                Delete_Items(sc,stmt);
                                break;
                        case 4:
                                Update_Price(sc,stmt);
                                break;
                       case 0:
                                Exit_1();
                                return;
                        default:
                            System.out.println("Invalid choice");
                    }
                }
            }
            else if(choice==2)
            {
                System.out.println("Dear Customer ! You are welcome.......");
                sc.nextLine();
                System.out.print("Please enter your name:- ");
                name=sc.nextLine();
                System.out.println();
                while(true)
                {
                    System.out.println("Enter operations to perform :- ");
                    System.out.println("1)View Products available in Shop");
                    System.out.println("2)Insert Product in Cart");
                    System.out.println("3)Delete Product from Cart");
                    System.out.println("4)View Your Cart");
                    System.out.println("0)Exit");
                    int ch = sc.nextInt();
                    switch(ch)
                    {
                        case 1:
                            View_Items(stmt);
                            break;
                        case 2:
                            Insert_in_cart(sc,stmt);
                            break;
                        case 3:
                            Delete_from_cart(sc,stmt);
                            break;
                        case 4:
                            View_cart(stmt);
                            break;
                        case 0:
                            Exit_2(stmt,name);
                            return;
                        default:
                            System.out.println("Invalid choice");
                    }
                }
            }
            else
            {
              System.out.println("Please enter valid choice...");
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    static void View_Items(Statement stmt)
    {
        try
        {
            String query1 = "SELECT * FROM Products;";
            ResultSet rs1 = stmt.executeQuery(query1);
            System.out.println("+----------------+-------------------------+----------+");
            System.out.println("|P_id            |P_nm                     |Price     |");
            System.out.println("+----------------+-------------------------+----------+");
            while (rs1.next()) {
                int pid1 = rs1.getInt("P_id");
                String pnm1 = rs1.getString("P_nm");
                int price1 = rs1.getInt("Price");
                System.out.printf("|%-16d|%-25s|%-10d|%n",
                        pid1, pnm1, price1);
            }

            System.out.println("+----------------+-------------------------+----------+");
            System.out.println();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    static void Insert_Items(Scanner sc,Statement stmt)
    {
       try
       {
           System.out.print("Enter the product id:-");
           int pid2=sc.nextInt();
           sc.nextLine();
           System.out.print("Enter the product name:-");
           String pnm2=sc.nextLine();
           if(!pnm2.equals(""))
           {
               System.out.print("Enter the product price:-");
               int price2 = sc.nextInt();
               String query2 = "INSERT INTO products " + "VALUES(" + pid2 + ",'" + pnm2 + "'," + price2 + ");";
               int rowsaffect1 = stmt.executeUpdate(query2);
               if (rowsaffect1 > 0)
               {
                   System.out.println("Successfully inserted the product");
                   System.out.println();
               }
               else
               {
                   System.out.println("Unsuccessful to insert the product");
                   System.out.println();
               }
           }
           else
           {
               System.out.println("Unable to insert");
               System.out.println();
           }
       }
       catch(SQLException e)
       {
           System.out.println(e.getMessage());
       }
    }
    static void Delete_Items(Scanner sc,Statement stmt)
    {
      try
      {
          System.out.print("Enter the product id :");
          int pid3 = sc.nextInt();
          String query3 = "DELETE FROM products " + "WHERE P_id=" + pid3 + ";";
          int rowsaffect2 = stmt.executeUpdate(query3);
          if (rowsaffect2 > 0)
          {
              System.out.println("Successfully deleted the product");
              System.out.println();
          }
          else
          {
              System.out.println("INVALID PRODUCT ID !!! ");
              System.out.println();
          }
      }
      catch(SQLException e)
      {
          System.out.println(e.getMessage());
      }
    }
    static void Update_Price(Scanner sc,Statement stmt)
    {
        try
        {
            System.out.print("Enter the Product id whose price should be updated :");
            int pid4=sc.nextInt();
            String query4="SELECT Price "+"FROM products "+"WHERE P_id="+pid4+";";
            ResultSet rs2=stmt.executeQuery(query4);
            if(rs2.next())
            {
                int temp1=rs2.getInt("Price");
                System.out.println("Previous Price was: "+temp1);
                System.out.print("Enter new Price: ");
                int price3=sc.nextInt();
                String query5="UPDATE products "+"SET Price="+price3+" WHERE P_id="+pid4+";";
                int rowsaffect3 = stmt.executeUpdate(query5);
                if (rowsaffect3 > 0)
                {
                    System.out.println("Successfully Updated the Product Price");
                    System.out.println();
                }
                else
                {
                    System.out.println("Unsuccessful Price Updation ");
                    System.out.println();
                }
            }
            else
            {
                  System.out.println("INVALID PRODUCT ID !!! ");
                  System.out.println();
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    static void Exit_1()
    {
        try
        {
            System.out.print("Exiting System ");
            int i = 5;
            while (i != 0) {
                System.out.print(".");
                Thread.sleep(400);
                i--;
            }
            System.out.println();
            System.out.println("Thank you 🙏🏻" );
        }
        catch(InterruptedException e)
        {
            System.out.println(e.getMessage());
        }
    }
    static void Insert_in_cart(Scanner sc,Statement stmt)
    {
        try
        {
            System.out.print("Enter the id of product which you want to buy: ");
            int pid2 = sc.nextInt();
            String query2 = "SELECT * FROM products WHERE P_id="+pid2+";";
            ResultSet rs2 = stmt.executeQuery(query2);
            if(rs2.next())
            {
                int pid3 = rs2.getInt("P_id");
                String pnm3 = rs2.getString("P_nm");
                int price3 = rs2.getInt("Price");
                System.out.print("Enter the quantity of product: ");
                int quant=sc.nextInt();
                if(quant!=0)
                {
                    String query3 = "INSERT INTO cart " + "VALUES(" + pid3 + ",'" + pnm3 + "'," +quant+","+ price3 * quant + ");";
                    int rowsaffect3 = stmt.executeUpdate(query3);
                    if (rowsaffect3 > 0)
                    {
                        System.out.println("Added to cart");
                        System.out.println();
                    }
                    else
                    {
                        System.out.println("Unable to add in cart!!!!");
                        System.out.println();
                    }
                }
                else
                {
                    System.out.println("Please enter valid quantity");
                    System.out.println();
                }
            }
            else
            {
                System.out.println("PRODUCT UNAVAILABLE!!!");
                System.out.println();
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    static void Delete_from_cart(Scanner sc,Statement stmt)
    {
        try
        {
            String query66 = "SELECT SUM(Price) AS Total_Price FROM cart;"; //Introduced here to avoid the case where cart is empty and user try to delete from cart
            ResultSet rs44 = stmt.executeQuery(query66);
            if(rs44.next())
            {
                int a = rs44.getInt("Total_Price");
                if (a != 0) {
                    System.out.print("Enter the id of the product which you want to Delete from cart : ");
                    int pid4 = sc.nextInt();
                    String query4 = "DELETE FROM cart WHERE P_id=" + pid4 + ";";
                    int rowsaffect4 = stmt.executeUpdate(query4);
                    if (rowsaffect4 > 0)
                    {
                        System.out.println("Deleted from cart ");
                        System.out.println();
                    }
                    else
                    {
                        System.out.println("Please enter valid product id");
                        System.out.println();
                    }
                }
                else
                {
                    System.out.println("No data found in cart. Cant Delete");
                    System.out.println();
                }
            }
            else
            {
                System.out.println("No data found in the cart.");
                System.out.println();
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    static void View_cart(Statement stmt)
    {
        try
        {
            String query5 = "SELECT * FROM cart;";
            ResultSet rs3 = stmt.executeQuery(query5);
            System.out.println("+----------------+-------------------------+----------+--------------+");
            System.out.println("|P_id            |P_nm                     |Qty       |Price         |");
            System.out.println("+----------------+-------------------------+----------+--------------+");
            while (rs3.next()) {
                int pid5 = rs3.getInt("P_id");
                String pnm5 = rs3.getString("P_nm");
                int q1=rs3.getInt("Qty");
                int price5 = rs3.getInt("Price");
                System.out.printf("|%-16d|%-25s|%-10d|%-14d|%n",
                        pid5, pnm5,q1,price5);
            }

            System.out.println("+----------------+-------------------------+----------+--------------+");
            System.out.println();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    static void Exit_2(Statement stmt,String name)
    {
        try
        {
            String query6 = "SELECT SUM(Price) AS Total_Price FROM cart;";
            ResultSet rs4 = stmt.executeQuery(query6);
            if (rs4.next()) //Always chk whether is there any content present in resultset object
            {
                // Retrieve the sum using the alias "Total_Price"
                int total = rs4.getInt("Total_Price");
                if(total!=0) {
                    View_cart(stmt);
                    System.out.println("+----------------+-------------------------+----------+--------------+");
                    System.out.println("|Total           |                         |          |"+total+"           |");
                    System.out.println("+----------------+-------------------------+----------+--------------+");
                }
                else
                {
                    System.out.println("No data found in cart");
                }
            }
            else
            {
                System.out.println("No data found in the cart.");
            }
            System.out.println("Thankyou "+name+" for visiting\nVisit Again 🙏🏻");
            String query7="TRUNCATE TABLE cart;";
            int rowsaffect7=stmt.executeUpdate(query7);
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}