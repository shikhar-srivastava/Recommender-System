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
                        1) "UsernameNull"
                        2) "PasswordNull"
                        3) "UserOrPasswordNotValid" 
*/

public class LoginServlet extends HttpServlet {  
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  
                           throws ServletException, IOException {  
        response.setContentType("text/html");  
        
        // Incase there is already a LOGIN-FAILED Cookie. It is deleted
        Cookie ck = new Cookie("loginfailed", "");
        ck.setMaxAge(0); 
        response.addCookie(ck);

        String name=request.getParameter("username");  
        String password=request.getParameter("password");  
        String errorMsg = null;
        
        if((name!=null)&(password!=null)) {
          // JDBC driver name and database URL
            String JDBC_DRIVER="oracle.jdbc.driver.OracleDriver";  
            String DB_URL="system@//localhost:1521/XE";  //--___REQUIRES TESTING!!!____---
               //  Database credentials
            String USER = "sys";
            String PASS = "2710";   //JAWAHAR: You will have to change this for your own database during testing
              //Change this BELOW to change the HTML file Format
            PreparedStatement ps = null;
            ResultSet rs = null;
            try{
                // Register JDBC driver
                Class.forName(JDBC_DRIVER);
                //Open the Connection
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                //Preparing Query
                ps = conn.prepareStatement("select * from USER_MAIN where USER_ID=? and PASSWORD=?");
                ps.setString(1, name);
                ps.setString(2, password);
                rs = ps.executeQuery();
                 
                if(rs != null && rs.next()) { 
                    Cookie cookey = new Cookie("username",name);  
                    response.addCookie(cookey); 
                    cookey.setMaxAge(25*60);             // 25 minutes.
                    response.sendRedirect("/index.html");
                }
                else {
                    errorMsg="Username and Password do not match";  //changed to use this directly as the error message
                    Cookie cookey = new Cookie("loginfailed", errorMsg);
                    cookey.setMaxAge(60); 
                    response.addCookie(cookey);
                    response.sendRedirect("/index.html");
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
            response.sendRedirect("/index.html");   
        }
    }  
  
}

