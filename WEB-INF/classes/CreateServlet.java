import java.util.*;
import java.io.IOException;  
import java.io.PrintWriter;  
import javax.servlet.ServletException;  
import javax.servlet.http.Cookie;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;
// Imports need to be checked
import java.sql.*;  
import java.io.*;
/*Some Documentation:
        
            ERRORMESSAGEs in Cookies Types:
                        
                        1) "signupfailed" 
            SIGNUPSUCESS:
                        1) "signupsuccess"
*/

public class CreateServlet extends HttpServlet 
{  
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException 
    {
       
        response.setContentType("text/html");  
        System.out.println("In CreateServlet");
        String name=request.getParameter("username");  
        String password=request.getParameter("password"); 
        String age=request.getParameter("age");
        String gender=request.getParameter("gender"); 
        if(age==null)
        {
            age="18";
        }
        else if(gender==null || gender=="")
        {
            Random r = new Random();
            if(r.nextInt(2)==0) gender="M";
            else gender="F";
        }
        String errorMsg = null;
        String successMsg="Signup sucess!";
        
          // JDBC driver name and database URL
           String JDBC_DRIVER="oracle.jdbc.driver.OracleDriver";  
            String DB_URL="jdbc:oracle:thin:@localhost:1521:XE"; 
               //  Database credentials
            String USER = "system";
            String PASS = "2710";   //JAWAHAR: You will have to change this for your own database during testing
              //Change this BELOW to change the HTML file Format
            PreparedStatement ps = null;
            ResultSet rs = null;
            PreparedStatement ps_main = null;
            ResultSet rs_main = null;
        try{
                // Register JDBC driver
                Class.forName(JDBC_DRIVER);
                //Open the Connection
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                //Preparing Query
                if (conn != null) 
                        {
                            System.out.println("Connected");
                        }
                else
                {
                    System.out.println("Couldn't connect to Database");
                }
                ps = conn.prepareStatement("select * from USER_MAIN where USER_ID=?");
                ps.setString(1, name);
                rs = ps.executeQuery();
                System.out.println("After Query Execution"); 
                if(rs != null && rs.next()) { 
                    errorMsg="User with given UserName already exists.";  //changed to use this directly as the error message
                    Cookie cookey = new Cookie("signup", errorMsg);
                    cookey.setMaxAge(60); 
                    response.addCookie(cookey);
                    response.sendRedirect(request.getContextPath()+"/index.html");
                    //For testing purposer->
                    //System.out.println("User and password invalid!");
                }
                else
                {
                        ps_main = conn.prepareStatement("insert into USER_MAIN values(?,?,?,?)");
                        ps_main.setString(1,name);
                        ps_main.setString(3,password);
                        ps_main.setString(4,age);
                        ps_main.setString(2,String.valueOf(gender.charAt(0)));
                        rs_main = ps_main.executeQuery();
                        if(rs_main==null) { 
                            errorMsg="Insertion into Table failed";  //changed to use this directly as the error message
                            Cookie cookey = new Cookie("signup", errorMsg);
                            cookey.setMaxAge(60); 
                            response.addCookie(cookey);
                            response.sendRedirect(request.getContextPath()+"/index.html");
                        }
                       else if(errorMsg==null)
                       {
                        Cookie cookey = new Cookie("signup", successMsg);
                        cookey.setMaxAge(60*2); 
                        response.addCookie(cookey);
                        response.sendRedirect(request.getContextPath()+"/index.html");
                       }

                }
           }
            catch(Exception e) {
                e.printStackTrace();
            }

            finally 
            {
                try
                 {
                    if(rs!=null) rs.close();
                    if(ps!=null) ps.close();
                    if(rs_main!=null) rs_main.close();
                    if(ps_main!=null) ps_main.close();
                  }catch(Exception e){e.printStackTrace();}
            } 
    }
}
