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
             1) insertfailed: NEED TO BE HANDLED IN BOTH CTYPE.HTML and INDEX.HTML!!!**__**__**__**     
             
*/

public class InsertServlet extends HttpServlet 
{  
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException 
    {
       
        response.setContentType("text/html");  
        System.out.println("In InsertServlet");
        String cType=request.getParameter("cType");  
        int preferences[]= new int[10];
        String cName[]= new String[10];
        for(int j=0;j<10;j++){prefernces[j]=-1;cName[j]=null;}
        int i=-1;
        do
        {
            i++;
            cName[i]=request.getParameter("con-field-"+String.toString(i+1));
            preferences[i]=Integer.parseInt(request.getParameter("rating-"+String.toString(i+1)));
        }while(preferences[i]!=-1 && cName[i]!=null && i<10);
        // i= 1 more than no. of ratings
        int pref_count=i;
        String errorMsg = null;
        Cookie[] cookies= request.getCookies();
        String name=null;


        for (Cookie ck: cookies) {
            if("username".equals(ck.getName()))
                name=ck.getValue();
        }
        if(name==null) {
            errorMsg="No User Logged In!";  //changed to use this directly as the error message
            Cookie cookey = new Cookie("insertfailed", errorMsg);
            cookey.setMaxAge(60*2); 
            response.addCookie(cookey);
            response.sendRedirect(request.getContextPath()+"/index.html");
        }
        if(name=="kinkax") {
    
                System.out.println("Exiting 'Kinkax'");
            response.sendRedirect(request.getContextPath()+"/results.html");
           //return;  
        }

          // JDBC driver name and database URL
           String JDBC_DRIVER="oracle.jdbc.driver.OracleDriver";  
            String DB_URL="jdbc:oracle:thin:@localhost:1521:XE"; 
               //  Database credentials
            String USER = "system";
            String PASS = "2710";   //JAWAHAR: You will have to change this for your own database during testing
              //Change this BELOW to change the HTML file Format
            PreparedStatement ps = null;
            ResultSet rs = null;
            PreparedStatement ps_check = null;
            ResultSet rs_check = null; 
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
                i=0
                while(i<pref_count)
                {
                    ps = conn.prepareStatement("select count(*) as count_val from "+cType+" where title = ?";
                    ps.setString(1,cName[i]);
                    rs = ps.executeQuery();
                       // System.out.println("After Query Execution"); 
                    int count_val;  
/*MIGHT CAUSE ERROR*/       if(rs== null ||((count_val=rs.getInt("count_val"))==0)) { 
/*Change Error output?*/    errorMsg="Your choice of "+cType+"("+String.toString(i+1)+")"+" is unique Sir! It doesn't exist in our database.";  //changed to use this directly as the error message
                            Cookie cookey = new Cookie("insertfailed", errorMsg);
                            cookey.setMaxAge(60); 
                            response.addCookie(cookey);
                            response.sendRedirect(request.getContextPath()+"/"+cType+".html");
                                                
                        }

                       else if(count_val>1)
                         { 
                            errorMsg="The "+cType+"("+String.toString(i+1)+")"+" name used is ambiguous!";  //changed to use this directly as the error message
                            Cookie cookey = new Cookie("insertfailed", errorMsg);
                            cookey.setMaxAge(60); 
                            response.addCookie(cookey);
                            response.sendRedirect(request.getContextPath()+"/"+cType+".html");
                            
                        }
                        else 
                        {
                              ps_check= conn.prepareStatement("select count(*) as count_check from user_"+cType+" natural join "+cType+" where title =? and user_id=?";
                              ps_check.setString(1,cName[i]);
                              ps_check.setString(2,name);
                              rs_check = ps_check.executeQuery();    
                              int val_check= rs_check.getInt("count_check");
                              if(val_check!=0)
                              {
                                   errorMsg="You have already rated "+cType+"("+String.toString(i+1)+")"+" before!";  //changed to use this directly as the error message
                                    Cookie cookey = new Cookie("insertfailed", errorMsg);
                                    cookey.setMaxAge(60); 
                                    response.addCookie(cookey);
                                    response.sendRedirect(request.getContextPath()+"/"+cType+".html");
                              }

                                ps_main = conn.prepareStatement("insert into USER_"+cType+" values(?,?,?,?)");
                                ps_main.setString(1,name);
                                ps_main.setString(3,password);
                                ps_main.setString(4,age);
                                ps_main.setString(2,String.valueOf(gender.charAt(0)));
                                rs_main = ps_main.executeQuery();
                                if(rs_main==null) { 
                                    errorMsg="Insertion into Table failed";  //changed to use this directly as the error message
                                    Cookie cookey = new Cookie("insertfailed", errorMsg);
                                    cookey.setMaxAge(60); 
                                    response.addCookie(cookey);
                                    response.sendRedirect(request.getContextPath()+"/index.html");
                                }
                               else if(errorMsg==null)
                               {
                                Cookie cookey = new Cookie("signup", succe);
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
