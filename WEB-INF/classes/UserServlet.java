import java.util.*;
import java.io.IOException;  
import java.io.PrintWriter;  
import javax.servlet.ServletException;  
import javax.servlet.http.Cookie;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;
import java.sql.*;  

public class UserServlet extends HttpServlet { 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException 
    {
        response.setContentType("text/plain");

        String cType_list[]= new String[3];
        cType_list[0]="movie";
        cType_list[1]="music";
        cType_list[2]="book";
        Cookie[] cookies= request.getCookies();
        String name=null;
        
        for (Cookie ck: cookies) {
            if("username".equals(ck.getName()))
                name=ck.getValue();
        }

        String ratings="";
        String titles="";
        String details="";

        String JDBC_DRIVER="oracle.jdbc.driver.OracleDriver";  
        String DB_URL="jdbc:oracle:thin:@localhost:1521:XE"; 
        String USER = "system";
        String PASS = "2710";
        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement ps_user = null;
        ResultSet rs_user = null;

    try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
            //Open the Connection
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //Preparing Query
            if (conn != null)   System.out.println("Connected");
            else    System.out.println("Couldn't connect to Database");
            int i=-1;
            System.out.println("Inside UserServlet");
            do
            {
                i++;
                if(i>=3)break;
                ps = conn.prepareStatement("select title,rating from user_"+cType_list[i]+" natural join "+cType_list[i]+" where user_id='"+name+"'");
                rs = ps.executeQuery();    
                while(rs.next())
                {
                    titles+=rs.getString("title")+"|";
                    ratings+=rs.getString("rating")+"|";
                }
                System.out.println("titles: "+ titles);
                System.out.println("rating: "+ ratings);
                titles=titles.substring(0,titles.length()-1);
                ratings=ratings.substring(0,ratings.length()-1);
                titles+=",";
                ratings+=",";

            }while(true);
            titles=titles.substring(0,titles.length()-1);
            ratings=ratings.substring(0,ratings.length()-1);
            System.out.println("Final: titles: "+ titles);
            System.out.println("Final: rating: "+ ratings);

            ps_user = conn.prepareStatement("select user_id,age,gender from user_main where user_id='"+name+"'");
            rs_user = ps_user.executeQuery();
            
            i=0;
            while(rs_user.next())
            {
                details+=rs_user.getString("user_id")+",";
                details+=rs_user.getString("age")+",";
                details+=rs_user.getString("gender");
                break;
            }

            Cookie cookey = new Cookie("pref",titles);
            cookey.setMaxAge(60*2);       //2 mins
            response.addCookie(cookey); 
            Cookie cookey1 = new Cookie("ratings",ratings);
            cookey1.setMaxAge(60*2);       //2 mins
            response.addCookie(cookey1); 
            Cookie cookey2 = new Cookie("details",details);
            cookey2.setMaxAge(60*2);       //2 mins
            response.addCookie(cookey2); 
        response.sendRedirect(request.getContextPath()+"/profile.html");

        }
            catch(Exception e) {
                e.printStackTrace();
            }

         finally {
                try {
                    if(rs!=null) rs.close();
                    if(ps!=null) ps.close();
                    if(rs_user!=null) rs.close();
                    if(ps_user!=null) ps.close();
                    }catch(Exception e){e.printStackTrace();}
                } 
    }
}
