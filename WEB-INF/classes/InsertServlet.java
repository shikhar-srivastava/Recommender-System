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
        for(int j=0;j<10;j++){preferences[j]=-1;cName[j]=null;}
        int i=-1;
        do
        {
            i++;
            if(i>=10)break;
            cName[i]=request.getParameter("con-field-"+Integer.toString(i+1));
/*Exception here*/
            preferences[i]=Integer.parseInt(request.getParameter("rating-"+Integer.toString(i+1)+"")); //To check for NULL pointers
       
        }while(preferences[i]!=-1 && cName[i]!=null);
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
            //For our poor kinky ass brother. 
                System.out.println("Exiting 'Kinkax' ;-)");
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
            Statement ins_pref = null;
            PreparedStatement ps_ins = null;
            ResultSet rs_ins = null;
            PreparedStatement ps_get = null;
            ResultSet rs_get = null;
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
                i=0;

                while(i<pref_count)
                {
                    ps = conn.prepareStatement("select count(*) as count_val from "+cType+" where title = '?'");
                    ps.setString(1,cName[i]);
                    rs = ps.executeQuery();
                       // System.out.println("After Query Execution"); 
                    int count_val;
                    String cType_id=null;  
/*MIGHT CAUSE ERROR*/       if(rs== null ||((count_val=rs.getInt("count_val"))==0)) { 
/*Change Error output?*/    errorMsg="Your choice of "+cType+"("+Integer.toString(i+1)+")"+" is unique Sir! It doesn't exist in our database.";  //changed to use this directly as the error message
                            Cookie cookey = new Cookie("insertfailed", errorMsg);
                            cookey.setMaxAge(60); 
                            response.addCookie(cookey);
                            response.sendRedirect(request.getContextPath()+"/"+cType+".html");
                                                
                        }

                       else if(count_val>1)
                         { 
                            errorMsg="The "+cType+"("+Integer.toString(i+1)+")"+" name used is ambiguous!";  //changed to use this directly as the error message
                            Cookie cookey = new Cookie("insertfailed", errorMsg);
                            cookey.setMaxAge(60); 
                            response.addCookie(cookey);
                            response.sendRedirect(request.getContextPath()+"/"+cType+".html");
                            
                        }
                        else 
                        {
                             ps_get= conn.prepareStatement("select distinct("+cType+"_id) from "+cType+" where title= '?'");
                             ps_get.setString(1,cName[i]);
                             rs_get = ps_get.executeQuery();    
                             cType_id= rs_get.getString("distinct("+cType+"_id)");  


                              ps_check= conn.prepareStatement("select count(*) as count_check from user_"+cType+" where "+cType+"_id = '?' and user_id='?'");
                              ps_check.setString(1,cType_id);
                              ps_check.setString(2,name);
                              rs_check = ps_check.executeQuery();    
                              int val_check= rs_check.getInt("count_check");
                                 if(val_check!=0)
                                 {
                                   /*errorMsg="You have already rated "+cType+"("+Integer.toString(i+1)+")"+" before!";  //changed to use this directly as the error message
                                    Cookie cookey = new Cookie("insertfailed", errorMsg);
                                    cookey.setMaxAge(60); 
                                    response.addCookie(cookey);
                                    response.sendRedirect(request.getContextPath()+"/"+cType+".html");*/

                                    ins_pref= conn.createStatement();
                                    String sql= "delete from user_"+cType+" where user_id='"+name+"' and "+cType+"_id ='"+cType_id+"'";
                                    ins_pref.executeUpdate(sql);
                                }
                            
                            ins_pref=null;
                            ins_pref=conn.createStatement();
                            String sql= "insert into user_"+cType+" values('"+name+"','"+cType_id+"',"+preferences[i]+"";
                            ins_pref.executeUpdate(sql);

                          }

                     i++;
                 }  //While loop ends here
                                
                // If control reaches here, then NO errors in queries. Hence, **Redirecting....
                
                    Cookie cookey = new Cookie("cType", cType);
                    cookey.setMaxAge(60*2); 
                    response.addCookie(cookey);
                    response.sendRedirect(request.getContextPath()+"/redirect.html");
                    

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
                if(rs_check!=null) rs_check.close();
                if(ps_check!=null) ps_check.close();
                if(rs_get!=null) rs_get.close();
                if(ps_get!=null) ps_get.close();
                if(rs_ins!=null) rs_ins.close();
                if(ps_ins!=null) ps_ins.close();
              }catch(Exception e){e.printStackTrace();}
        } 
    }
}
