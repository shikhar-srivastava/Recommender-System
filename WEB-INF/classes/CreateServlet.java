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

/*Some Documentation:
        
            ERRORMESSAGEs in Cookies Types:
                        
                        1) "loginfailed" 
*/

public class CreateServlet extends HttpServlet {  
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  
                           throws ServletException, IOException {  
        response.setContentType("text/html");  
        
        // Incase there is already a LOGIN-FAILED Cookie. It is deleted
        Cookie ck = new Cookie("loginfailed", "");
        ck.setMaxAge(0); 
        response.addCookie(ck);

        String name=request.getParameter("username");  
        String password=request.getParameter("password"); 
        String age=request.getParameter("username");
        String gender=request.getParameter("username"); 
        String errorMsg = null;
        
        if((name!=null)&(password!=null)&(age!null)&(gender!null)) {
          // JDBC driver name and database URL
           String JDBC_DRIVER="oracle.jdbc.driver.OracleDriver";  
            String DB_URL="jdbc:oracle:thin:@localhost:1521:XE";  //--___REQUIRES TESTING!!!____---
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
                 
                if(rs != null && rs.next()) { 
                      errorMsg="Username and Password do not match";  //changed to use this directly as the error message
                    Cookie cookey = new Cookie("signupfailed", errorMsg);
                    cookey.setMaxAge(60); 
                    response.addCookie(cookey);
                    response.sendRedirect(request.getContextPath()+"/index.html");
                    //For testing purposer->
                    //System.out.println("User and password invalid!");
                }
                else {
                  
                     Cookie cookey = new Cookie("username",name);  
                    response.addCookie(cookey); 
                    cookey.setMaxAge(25*60);             // 25 minutes.
                    response.sendRedirect(request.getContextPath()+"/index.html");
                    //System.out.println("User and password VALID!");
                }
           }
            catch(Exception e) {
                e.printStackTrace();
            }

            finally {
                try
                 {
                    if(rs!=null) rs.close();
                    if(ps!=null) ps.close();
                  }catch(Exception e){e.printStackTrace();}
            } 
        }
        else {
            errorMsg="Unknown Error";  //because username and password cannot be null
            Cookie cookey = new Cookie("loginfailed", errorMsg);
            cookey.setMaxAge(60); 
            response.addCookie(cookey);
            response.sendRedirect(request.getContextPath()+"/index.html");
        }
    }  
  
}

